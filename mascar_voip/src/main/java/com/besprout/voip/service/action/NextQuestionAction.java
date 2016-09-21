package com.besprout.voip.service.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.besprout.voip.bean.Address;
import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.bean.order.MenuBean;
import com.besprout.voip.bean.order.MenuOptionBean;
import com.besprout.voip.bean.order.OrderBean;
import com.besprout.voip.bean.rest.VoipMenu;
import com.besprout.voip.bean.rest.VoipMenuAddition;
import com.besprout.voip.common.Constant;
import com.besprout.voip.common.spring.ServiceLocator;
import com.besprout.voip.service.DialogService;
import com.besprout.voip.util.MyUtil;
import com.besprout.voip.util.StringUtil;
import com.besprout.voip.util.WatsonUtil;

public class NextQuestionAction extends AbsAction{
	
	private DialogBean dialoBean = null;
	private DialogResultBean resultBean = null;
	private OrderBean orderBean = null;
	
	@Override
	public ActionResult process(DialogBean dialoBean, DialogResultBean resultBean) {
		this.dialoBean = dialoBean;
		this.resultBean = resultBean;
		this.orderBean = dialoBean.getOrderBean();
		
		String text = null;
		String lastModule = orderBean.getLastModule();
		if(!StringUtil.isEmpty(lastModule)){
			text = checkModule(lastModule);
		}
		
		if(text == null){
			text = checkAllModule();
		}
		
		ActionResult ar = new ActionResult(ActionResult.SYSTEM);;
		if(text == null){
			ar.setOperate(Constant.ActionType.CLOSE);
			ar.setEcho("Okay. It’s been texted to you. If you see anything needs to be changed, please call {PHONE} to speak with a real person.");
		}else{
			ar.setEcho(text);
		}
		
		return ar;
	}
	
	private String checkModule(String module){
		if(module.equals(Constant.Module.MODULE_ADDRESS)){
			return checkAddress();
		}else if(module.equals(Constant.Module.MODULE_MENU)){
			return checkMenu();
		}else if(module.equals(Constant.Module.MODULE_ORDER)){
			return checkOrder();
		}else{
			return null;
		}
	}
	
	private String checkAllModule(){
		String text = null;
		if(text == null){
			text = checkAddress();
		}
		
		if(text == null){
			text = checkMenu();
		}
		
		if(text == null){
			text = checkOrder();
		}
		return text;
	}
	
	private String checkAddress(){
		if(orderBean.getModuleStatus().get(Constant.Module.MODULE_ADDRESS)){
			return null;
		}
		
		if(orderBean.getOrderMethod() == Constant.ORDER_METHOD_NONE){
			orderBean.setLastModule(Constant.ACTION_MODULE.get(Constant.Module.MODULE_ADDRESS));
			String toWatson = getSystemAction(Constant.ActionType.ORDER_METHOD);
			String result = WatsonUtil.doConverse(dialoBean.getDialogId(), dialoBean.getClientId(), dialoBean.getConversationId(), toWatson);
			return WatsonUtil.getResponse(result);
		}
		
		if(orderBean.getAddressBean() != null && orderBean.getAddressBean().isConfirm()){
			return null;
		}else{
			if(orderBean.getOrderMethod() == Constant.ORDER_METHOD_DELIVERY){
				if(orderBean.getAddressBean() == null || StringUtil.isEmpty(orderBean.getAddressBean().getAddress())){
					orderBean.setLastModule(Constant.ACTION_MODULE.get(Constant.Module.MODULE_ADDRESS));
					String toWatson = getSystemAction(Constant.ActionType.ADDRESS);
					String result = WatsonUtil.doConverse(dialoBean.getDialogId(), dialoBean.getClientId(), dialoBean.getConversationId(), toWatson);
					
					return WatsonUtil.getResponse(result);
				}else if(StringUtil.isEmpty(orderBean.getAddressBean().getZipCode())){
					orderBean.setLastModule(Constant.ACTION_MODULE.get(Constant.Module.MODULE_ADDRESS));
					String toWatson = getSystemAction(Constant.ActionType.ZIP_CODE);
					String result = WatsonUtil.doConverse(dialoBean.getDialogId(), dialoBean.getClientId(), dialoBean.getConversationId(), toWatson);
					
					return WatsonUtil.getResponse(result);
				}else{
					orderBean.setLastModule(Constant.ACTION_MODULE.get(Constant.Module.MODULE_ADDRESS));
					
					if(!StringUtil.isEmpty(orderBean.getAddressBean().getZipCode()) && !StringUtil.isEmpty(orderBean.getAddressBean().getAddress())){
						String address = orderBean.getAddressBean().getAddress() + ", " + orderBean.getAddressBean().getZipCode();
						List<Address> list = MyUtil.searchAddress(address);
						if(list != null){
							int num = 0;
							for (int i = 0; i < list.size(); i++) {
								if(num == 0){
									orderBean.getAddressBean().setAddress1(list.get(i));
								}else if(num == 1){
									orderBean.getAddressBean().setAddress2(list.get(i));
								}
								
								num ++;
								if(num >= 2){
									break;
								}
							}
							
							if(num == 1){
								String toWatson = getSystemAction("CONFIRM_ADDRESS");
								WatsonUtil.doConverse(dialoBean.getDialogId(), dialoBean.getClientId(), dialoBean.getConversationId(), toWatson);
								return orderBean.getAddressBean().getAddress1().getLongAddress() + ", it is correct?";
								
							}else if(num >= 2){
								String toWatson = getSystemAction("CHOOSE_ADDRESS");
								WatsonUtil.doConverse(dialoBean.getDialogId(), dialoBean.getClientId(), dialoBean.getConversationId(), toWatson);
								return "Which one is your address: " + orderBean.getAddressBean().getAddress1().getLongAddress() + " or " + orderBean.getAddressBean().getAddress2().getLongAddress();
							}
							
						}
					}
					
					return "Sorry, We don't found this address.";
				}
			}
		}
		
		return null;
	}
	
	private String checkMenu(){
		if(orderBean.getModuleStatus().get(Constant.Module.MODULE_MENU)){
			return null;
		}
		
		List<MenuBean> menuList = orderBean.getMenuBean();
		if(menuList == null || menuList.isEmpty()){
			String toWatson = getSystemAction("ASK_MENU");
			String result = WatsonUtil.doConverse(dialoBean.getDialogId(), dialoBean.getClientId(), dialoBean.getConversationId(), toWatson);
			return WatsonUtil.getResponse(result);
			
		}else{
			//判断是否已经完整的菜品和option
			String text = validateMenu();
			if(text != null){
				return text;
			}
		}
		
		if(!StringUtil.isEmpty(resultBean.getValue()) && resultBean.getValue().equals("YES")){
			String toWatson = getSystemAction("ASK_MENU");
			String result = WatsonUtil.doConverse(dialoBean.getDialogId(), dialoBean.getClientId(), dialoBean.getConversationId(), toWatson);
			return WatsonUtil.getResponse(result);
		}
		
		String toWatson = getSystemAction("ASK_MENU_AGAIN");
		String result = WatsonUtil.doConverse(dialoBean.getDialogId(), dialoBean.getClientId(), dialoBean.getConversationId(), toWatson);
		return WatsonUtil.getResponse(result);
	}
	
	private String checkOrder(){
		if(orderBean.getModuleStatus().get(Constant.Module.MODULE_ORDER)){
			return null;
		}
		
		if(!orderBean.isReadOrText()){
			String toWatson = getSystemAction("READ_OR_TEXT");
			String result = WatsonUtil.doConverse(dialoBean.getDialogId(), dialoBean.getClientId(), dialoBean.getConversationId(), toWatson);
			return WatsonUtil.getResponse(result);
		}
		
		
		return null;
	}
	
	private String validateMenu(){
		DialogService dialogService = ServiceLocator.getBean(DialogService.class);
		
		List<MenuBean> menuList = orderBean.getMenuBean();
		Map<MenuBean, Map<VoipMenuAddition, List<MenuOptionBean>>> map = new HashMap<MenuBean, Map<VoipMenuAddition,List<MenuOptionBean>>>();
		for (MenuBean menuBean : menuList) {
			Map<VoipMenuAddition, List<MenuOptionBean>> additionMap = map.get(menuBean);
			if(additionMap == null){
				additionMap = new HashMap<VoipMenuAddition, List<MenuOptionBean>>();
				map.put(menuBean, additionMap);
			}
			
			for (MenuOptionBean menuOptionBean : menuBean.getOptionList()) {
				VoipMenuAddition addition = dialogService.getVoipMenuAddition(dialoBean.getStoreId(), menuBean.getMenuId(), menuOptionBean.getOptionId());
				List<MenuOptionBean> list = additionMap.get(addition);
				if(list == null){
					list = new ArrayList<MenuOptionBean>();
					additionMap.put(addition, list);
				}
				list.add(menuOptionBean);
			}
		}
		
		for (Entry<MenuBean, Map<VoipMenuAddition, List<MenuOptionBean>>> entry : map.entrySet()) {
			MenuBean menuBean = entry.getKey();
			Map<VoipMenuAddition, List<MenuOptionBean>> additionMap = entry.getValue();
			for (Entry<VoipMenuAddition, List<MenuOptionBean>> entry2 : additionMap.entrySet()) {
				VoipMenuAddition addition = entry2.getKey();
				List<MenuOptionBean> list = entry2.getValue();
				
				if(addition.getIsNeedChoice() == 1 && list.isEmpty()){
					VoipMenu voipMenu = dialogService.getVoipMenu(dialoBean.getStoreId(), menuBean.getMenuId());
					String toWatson = getSystemAction("ASK_OPTION");
					String result = WatsonUtil.doConverse(dialoBean.getDialogId(), dialoBean.getClientId(), dialoBean.getConversationId(), toWatson);
					return WatsonUtil.getResponse(result);
				}
				
			}
			
		}
		
		return null;
	}
	
	
	
	
}
