package com.besprout.voip.service.action;

import java.util.List;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.bean.order.MenuBean;
import com.besprout.voip.bean.order.MenuOptionBean;
import com.besprout.voip.bean.rest.VoipMenuAddition;
import com.besprout.voip.bean.rest.VoipMenuAdditionDetail;
import com.besprout.voip.common.Constant;
import com.besprout.voip.common.spring.ServiceLocator;
import com.besprout.voip.service.DialogService;
import com.besprout.voip.util.StringUtil;
import com.besprout.voip.util.WatsonUtil;

public class OptionAction extends AbsAction{

	@Override
	public ActionResult process(DialogBean dialogBean, DialogResultBean resultBean) {
		dialogBean.getOrderBean().setLastModule(Constant.Module.MODULE_MENU);
		DialogService dialogService = ServiceLocator.getBean(DialogService.class);
		
		if(StringUtil.isEmpty(dialogBean.getOrderBean().getLastMenuId())){
			String toWatson = getSystemAction("ASK_MENU");
			String text = WatsonUtil.doConverse(dialogBean.getDialogId(), dialogBean.getClientId(), dialogBean.getConversationId(), toWatson);
			text = WatsonUtil.getResponse(text);
			
			ActionResult ar = new ActionResult(ActionResult.SYSTEM);
			ar.setEcho(text);
			return ar;
		}
		
		
		String option = resultBean.getValue();
		long detailId = Long.parseLong(option);
		VoipMenuAdditionDetail voipMenudAddtionDetail = dialogService.getVoipMenuAddtionDetail(dialogBean.getStoreId(), dialogBean.getOrderBean().getLastMenuId(), detailId);
		if(voipMenudAddtionDetail == null){
			//菜品没有指定的小类
			VoipMenuAdditionDetail detail = dialogService.getVoipMenuAddtionDetail(dialogBean.getStoreId(), detailId);
			List<VoipMenuAdditionDetail> detailList = dialogService.getVoipMenuAddtionDetailList(dialogBean.getStoreId(), dialogBean.getOrderBean().getLastMenuId());
			
			StringBuffer sb = new StringBuffer();
			sb.append("Sorry, we don't have ").append(detail.getName()).append(". we have ");
			for (VoipMenuAdditionDetail voipMenuAdditionDetail : detailList) {
				sb.append(voipMenuAdditionDetail.getName()).append(", ");
			}
			ActionResult ar = new ActionResult(ActionResult.SYSTEM);
			ar.setEcho(sb.toString());
			return ar;
		}
		
		
		boolean exists = false;
		List<MenuOptionBean> optionList = getMenuOptionList(dialogBean.getOrderBean().getLastMenuId(), dialogBean);
		for (MenuOptionBean menuOptionBean : optionList) {
			if(menuOptionBean.getOptionId() == detailId){
				exists = true;
				break;
			}
		}
		
		
		if(exists){
			String toWatson = getSystemAction("ASK_OPTION");
			WatsonUtil.doConverse(dialogBean.getDialogId(), dialogBean.getClientId(), dialogBean.getConversationId(), toWatson);
			
			ActionResult ar = new ActionResult(ActionResult.SYSTEM);
			ar.setEcho("You've already picked " + voipMenudAddtionDetail.getName() + ". Do you want to pick another one instead?");
			return ar;
		}else{
			VoipMenuAddition menuAddition = dialogService.getVoipMenuAddition(dialogBean.getStoreId(), dialogBean.getOrderBean().getLastMenuId(), detailId);
			if(menuAddition.getIsMultiple() == 1){
				if(menuAddition.getMaxChoice() > optionList.size()){
					MenuOptionBean optionBean = new MenuOptionBean();
					optionBean.setOptionId(Long.parseLong(option));
					optionBean.setOptionName(voipMenudAddtionDetail.getName());
					optionList.add(optionBean);
					
				}else{
					ActionResult ar = new ActionResult(ActionResult.SYSTEM);
					ar.setEcho("Sorry, options than max number");
					return ar;
				}
			}else{
				if(optionList.size() == 0){
					MenuOptionBean optionBean = new MenuOptionBean();
					optionBean.setOptionId(Long.parseLong(option));
					optionBean.setOptionName(voipMenudAddtionDetail.getName());
					optionList.add(optionBean);
				}else{
					ActionResult ar = new ActionResult(ActionResult.SYSTEM);
					ar.setEcho("Sorry, options than max number");
					return ar;
				}
			}
			
		}
		
		return getNextActionResult();
	}
	
	
	private List<MenuOptionBean> getMenuOptionList(long productId, DialogBean dialogBean){
		List<MenuBean> menuList = dialogBean.getOrderBean().getMenuBean();
		for (MenuBean bean : menuList) {
			if(bean.getMenuId() == dialogBean.getOrderBean().getLastMenuId()){
				return bean.getOptionList();
			}
		}
		return null;
	}
}
