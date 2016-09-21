package com.besprout.voip.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.besprout.voip.bean.ConversationBean;
import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResponse;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.bean.order.MenuBean;
import com.besprout.voip.bean.order.MenuOptionBean;
import com.besprout.voip.bean.order.OrderBean;
import com.besprout.voip.bean.rest.VoipBean;
import com.besprout.voip.bean.rest.VoipMenu;
import com.besprout.voip.bean.rest.VoipMenuAddition;
import com.besprout.voip.bean.rest.VoipMenuAdditionDetail;
import com.besprout.voip.bean.rest.VoipStoreBean;
import com.besprout.voip.bean.rest.VoipStoreCloseTimeBean;
import com.besprout.voip.common.Constant;
import com.besprout.voip.common.Extra;
import com.besprout.voip.common.spring.ServiceLocator;
import com.besprout.voip.controller.DialogController;
import com.besprout.voip.service.action.ActionFactory;
import com.besprout.voip.service.action.ActionResult;
import com.besprout.voip.util.DateUtil;
import com.besprout.voip.util.FileUtil;
import com.besprout.voip.util.JsonUtil;
import com.besprout.voip.util.StringUtil;
import com.besprout.voip.util.WatsonUtil;

@Service
public class DialogService {
	
	/*@Autowired
	private MemcachedFactory memcachedFactory;*/
	
	
	public void loadParam(){
		RestTemplate restTemplate = ServiceLocator.getBean(RestTemplate.class);
		Extra.voipBean = restTemplate.getForEntity(Extra.rest_voip, VoipBean.class).getBody();
	}
	
	public void initDialog(){
//		List<VoipStoreBean> store = Extra.voipBean.getStoreList();
//		for (VoipStoreBean voipStoreBean : store) {
//			uploateStoreDialog(voipStoreBean);
//		}
	}
	
	private void uploateStoreDialog(VoipStoreBean storeBean){
		StringBuffer sb = new StringBuffer();
		StringBuffer opSb = new StringBuffer();
		List<VoipMenu> menuList = storeBean.getMenuList();
		for (VoipMenu voipMenu : menuList) {
			sb.append(getValue(voipMenu.getName(), String.valueOf(voipMenu.getProductId()), voipMenu.getName()));
			
			List<VoipMenuAddition> additionList = voipMenu.getAdditionList();
			for (VoipMenuAddition voipMenuAddition : additionList) {
				List<VoipMenuAdditionDetail> detailList = voipMenuAddition.getDetailList();
				for (VoipMenuAdditionDetail voipMenuAdditionDetail : detailList) {
					opSb.append(getValue(voipMenuAdditionDetail.getName(), String.valueOf(voipMenuAdditionDetail.getAdditionId()), voipMenuAdditionDetail.getName()));
				}
			}
		}
		
		String content = FileUtil.readFile(Extra.dialogTemplateFile);
		content = content.replace("[menu_entity]", sb.toString());
		content = content.replace("[option_entity]", opSb.toString());
		String fileName = Extra.dialogTempDir + "dialog_" + storeBean.getStoreId() + ".xml";
		FileUtil.writeToFile(fileName, content);
		WatsonUtil.updateDialog(fileName, DialogController.DIALOG_ID);
	}
	
	private String getValue(String name, String value, String... items){
		StringBuffer sb = new StringBuffer();
		sb.append("<value name=\"").append(name).append("\" value=\"").append(value).append("\">\n");
		sb.append("\t<grammar>\n");
		for (String item : items) {
			sb.append("\t\t<item>").append(item).append("</item>\n");
		}
		sb.append("\t</grammar>\n");
		sb.append("</value>\n");
		return sb.toString();
	}
	
	public DialogResponse conversation(String text, DialogBean bean){
		String result = WatsonUtil.doConverse(bean.getDialogId(), bean.getClientId(), bean.getConversationId(), text);
		System.out.println(result);
		ConversationBean cvs = JsonUtil.readJsonEntity(result, ConversationBean.class);
		for (String response : cvs.getResponse()) {
			if(response != null && !response.equals("")){
				
				bean.setClientId(String.valueOf(cvs.getClient_id()));
				bean.setConversationId(String.valueOf(cvs.getConversation_id()));
				DialogResultBean dr = parseDialogResult(response);
				
				if(bean.getOrderBean() == null){
					bean.setOrderBean(new OrderBean());
				}
				
				
				return processDialog(bean, dr);
			}
		}
		return new DialogResponse();
	}
	
	public DialogResponse processDialog(DialogBean bean, DialogResultBean result){
		if(result.getAction() == null){
			String echo = getEcho(result.getEcho(), bean);
			DialogResponse response = new DialogResponse(echo);
			response.setClientId(bean.getClientId());
			response.setConversationId(bean.getConversationId());
			return response;
		}else{
			return processAction(bean, result);
		}
	}
	
	private DialogResponse processAction(DialogBean bean, DialogResultBean result){
		String action = result.getAction();
		bean.setLastAction(action);
		
		ActionResult ar = ActionFactory.getInstance().processAction(bean, result);
		if(ar.getType() == ActionResult.WATSON){
			DialogResultBean result2 = parseDialogResult(ar.getOperate());
			return processDialog(bean, result2);
			
		}else{
			DialogResponse response = new DialogResponse(DialogResponse.CALL_HANDING);
			response.setClientId(bean.getClientId());
			response.setConversationId(bean.getConversationId());
			response.setContent(getEcho(ar.getEcho(), bean));
			if(!StringUtil.isEmpty(ar.getOperate()) && ar.getOperate().equals(Constant.ActionType.CLOSE)){
				response.setCallType(DialogResponse.CALL_HANDUP);
			}
			return response;
			
		}
		
	}
	
	private String getEcho(String echo, DialogBean dialogBean){
		VoipStoreBean storeBean = getVoipStoreBean(dialogBean.getStoreId());
		
		String result = echo;
		if(echo.indexOf(Constant.EchoType.ADDRESS.replace("\\", "")) != -1){
			result = echo.replaceAll(Constant.EchoType.ADDRESS, storeBean.getAddress());
		}
		if(echo.indexOf(Constant.EchoType.BRAND_NAME.replace("\\", "")) != -1){
			result = echo.replaceAll(Constant.EchoType.BRAND_NAME, storeBean.getBrandName());
		}
		if(echo.indexOf(Constant.EchoType.CLOSETIME.replace("\\", "")) != -1){
			VoipStoreCloseTimeBean closeBean = getVoipStoreCloseTimeBean(storeBean);
			if(closeBean.isOpen()){
				result = echo.replaceAll(Constant.EchoType.CLOSETIME, closeBean.getWorkingTo());
			}else{
				result = "Closed!";
			}
		}
		if(echo.indexOf(Constant.EchoType.SPECIAL.replace("\\", "")) != -1){
			String special = getSpecial(storeBean);
			if(special == null){
				result = "sorry, nothing special!";
			}else{
				result = echo.replaceAll(Constant.EchoType.SPECIAL, getSpecial(storeBean));
			}
		}
		
		if(echo.indexOf(Constant.EchoType.PHONE.replace("\\", "")) != -1){
			result = echo.replaceAll(Constant.EchoType.PHONE, storeBean.getPhone());
		}
		
		return result;
	}
	
	private DialogResultBean parseDialogResult(String text){
		boolean isSet = false;
		DialogResultBean bean = new DialogResultBean();
		int actionIndex = text.indexOf(Constant.ResultType.ACTION_S);
		if(actionIndex != -1){
			String action = text.substring(actionIndex + Constant.ResultType.ACTION_S.length(), text.indexOf(Constant.ResultType.ACTION_E));
			bean.setAction(action);
			isSet = true;
		}

		int valueIndex = text.indexOf(Constant.ResultType.VALUE_S);
		if(valueIndex != -1){
			String value = text.substring(valueIndex + Constant.ResultType.VALUE_S.length(), text.indexOf(Constant.ResultType.VALUE_E));
			bean.setValue(value);
			isSet = true;
		}
		
		int echoIndex = text.indexOf(Constant.ResultType.ECHO_S);
		if(echoIndex != -1){
			String echo = text.substring(echoIndex + Constant.ResultType.ECHO_S.length(), text.indexOf(Constant.ResultType.ECHO_E));
			bean.setEcho(echo);
			isSet = true;
		}
		
		if(!isSet){
			bean.setEcho(text);
		}
		
		return bean;
	}
	
	
	private DialogBean getOrder(String phoneNumber){
		DialogBean bean = getCacheDialog(phoneNumber);
		if(bean == null){
			bean = new DialogBean();
			putCacheDialog(phoneNumber, bean);
		}
		
		return bean;
	}
	
	
	
	private VoipStoreCloseTimeBean getVoipStoreCloseTimeBean(VoipStoreBean storeBean){
		String week = DateUtil.getCurrentWeekDisplay(storeBean.getTimeZone());
		List<VoipStoreCloseTimeBean> closeList = storeBean.getCloseTime();
		for (VoipStoreCloseTimeBean voipStoreCloseTimeBean : closeList) {
			if(voipStoreCloseTimeBean.getWeek().equals(week)){
				return voipStoreCloseTimeBean;
			}
		}
		return null;
	}
	
	public String getOrder2Text(OrderBean orderBean){
		StringBuffer sb = new StringBuffer();
		if(!StringUtil.isEmpty(orderBean.getPhoneNumber())){
			sb.append("phone: ").append(orderBean.getPhoneNumber()).append("<br>");
		}
		
		if(orderBean.getOrderMethod() != Constant.ORDER_METHOD_NONE){
			sb.append(orderBean.getOrderMethod() == Constant.ORDER_METHOD_DELIVERY ? "delivery" : "pickup").append("<br>");
		}
		
		if(orderBean.getOrderMethod() == Constant.ORDER_METHOD_DELIVERY){
			if(orderBean.getAddressBean() != null && !StringUtil.isEmpty(orderBean.getAddressBean().getAddress()) && !StringUtil.isEmpty(orderBean.getAddressBean().getZipCode())){
				sb.append("address: ").append(orderBean.getAddressBean().getAddress()).append(", ").append(orderBean.getAddressBean().getZipCode()).append("<br>");
			}
		}
		
		List<MenuBean> menuList = orderBean.getMenuBean();
		if(menuList != null ){
			for (MenuBean menuBean : menuList) {
				sb.append(menuBean.getMenuName()).append("<br>");
				List<MenuOptionBean> optionList = menuBean.getOptionList();
				for (MenuOptionBean menuOptionBean : optionList) {
					sb.append("    ").append(menuOptionBean.getOptionName()).append("<br>");
				}
			}
		}
		
		return sb.toString();
	}
	
	public VoipStoreBean getVoipStoreBean(long storeId){
		List<VoipStoreBean> list = Extra.voipBean.getStoreList();
		for (VoipStoreBean voipStoreBean : list) {
			if(voipStoreBean.getStoreId() == storeId){
				return voipStoreBean;
			}
		}
		return null;
	}
	
	public VoipMenu getVoipMenu(long storeId, long productId){
		VoipStoreBean voipStoreBean = getVoipStoreBean(storeId);
		List<VoipMenu> menuList = voipStoreBean.getMenuList();
		for (VoipMenu voipMenu : menuList) {
			if(voipMenu.getProductId() == productId){
				return voipMenu;
			}
		}
		return null;
	}
	
	public VoipMenuAddition getVoipMenuAddition(long storeId, long productId, long detailId){
		VoipMenu voipMenu = getVoipMenu(storeId, productId);
		List<VoipMenuAddition> additionList = voipMenu.getAdditionList();
		for (VoipMenuAddition voipMenuAddition : additionList) {
			List<VoipMenuAdditionDetail> detailList = voipMenuAddition.getDetailList();
			for (VoipMenuAdditionDetail voipMenuAdditionDetail : detailList) {
				if(voipMenuAdditionDetail.getDetailId() == detailId){
					return voipMenuAddition;
				}
			}
		}
		return null;
	
	}
	
	public VoipMenuAdditionDetail getVoipMenuAddtionDetail(long storeId, long detailId){
		VoipStoreBean voipStoreBean = getVoipStoreBean(storeId);
		List<VoipMenu> menuList = voipStoreBean.getMenuList();
		for (VoipMenu voipMenu : menuList) {
			List<VoipMenuAddition> additionList = voipMenu.getAdditionList();
			for (VoipMenuAddition voipMenuAddition : additionList) {
				List<VoipMenuAdditionDetail> detailList = voipMenuAddition.getDetailList();
				for (VoipMenuAdditionDetail voipMenuAdditionDetail : detailList) {
					if(voipMenuAdditionDetail.getDetailId() == detailId){
						return voipMenuAdditionDetail;
					}
				}
			}
		}
		
		return null;
	}
	
	public VoipMenuAdditionDetail getVoipMenuAddtionDetail(long storeId, long productId, long detailId){
		VoipMenu voipMenu = getVoipMenu(storeId, productId);
		List<VoipMenuAddition> additionList = voipMenu.getAdditionList();
		for (VoipMenuAddition voipMenuAddition : additionList) {
			List<VoipMenuAdditionDetail> detailList = voipMenuAddition.getDetailList();
			for (VoipMenuAdditionDetail voipMenuAdditionDetail : detailList) {
				if(voipMenuAdditionDetail.getDetailId() == detailId){
					return voipMenuAdditionDetail;
				}
			}
		}
		return null;
	} 
	
	public List<VoipMenuAdditionDetail> getVoipMenuAddtionDetailList(long storeId, long productId){
		List<VoipMenuAdditionDetail> list = new ArrayList<VoipMenuAdditionDetail>();
		
		VoipMenu voipMenu = getVoipMenu(storeId, productId);
		List<VoipMenuAddition> additionList = voipMenu.getAdditionList();
		for (VoipMenuAddition voipMenuAddition : additionList) {
			List<VoipMenuAdditionDetail> detailList = voipMenuAddition.getDetailList();
			list.addAll(detailList);
		}
		return list;
	}
	
	private String getSpecial(VoipStoreBean storeBean){
		String special = null;
		List<String> specialList = storeBean.getSpecialList();
		for (String temp : specialList) {
			if(special == null){
				special = temp;
			}else{
				special += " and " + temp;
			}
		}
		return special;
	}
	
	public void putCacheDialog(String phoneNumber, DialogBean bean){
		//memcachedFactory.setPerpetual(phoneNumber, bean);
	}
	
	public DialogBean getCacheDialog(String phoneNumber){
		//Object obj = memcachedFactory.get(phoneNumber);
		//return obj == null ? null : (DialogBean)obj;
		return null;
	}
	
	public void removeCacheDialog(String phoneNumber){
		//memcachedFactory.delete(phoneNumber);
	}
	
	public void removeCacheAllDialog(){
		
	}
	
}
