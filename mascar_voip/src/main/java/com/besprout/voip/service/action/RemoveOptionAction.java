package com.besprout.voip.service.action;

import java.util.List;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.bean.order.MenuBean;
import com.besprout.voip.bean.order.MenuOptionBean;
import com.besprout.voip.bean.rest.VoipMenuAdditionDetail;
import com.besprout.voip.common.Constant;
import com.besprout.voip.common.spring.ServiceLocator;
import com.besprout.voip.service.DialogService;
import com.besprout.voip.util.StringUtil;
import com.besprout.voip.util.WatsonUtil;

public class RemoveOptionAction extends AbsAction{

	@Override
	public ActionResult process(DialogBean dialogBean, DialogResultBean resultBean) {
		dialogBean.getOrderBean().setLastModule(Constant.Module.MODULE_MENU);
		DialogService dialogService = ServiceLocator.getBean(DialogService.class);
		long detailId =  Long.parseLong(resultBean.getValue());
		
		if(StringUtil.isEmpty(dialogBean.getOrderBean().getLastMenuId())){
			//没有指定哪个菜品
			String toWatson = getSystemAction("ASK_REMOVE_MENU");
			String text = WatsonUtil.doConverse(dialogBean.getDialogId(), dialogBean.getClientId(), dialogBean.getConversationId(), toWatson);
			text = WatsonUtil.getResponse(text);
			
			ActionResult ar = new ActionResult(ActionResult.SYSTEM);
			ar.setEcho(text);
			return ar;
		}
		
		//查找option
		MenuOptionBean menuOption = null;
		List<MenuBean> menuList = dialogBean.getOrderBean().getMenuBean();
		menu: for (MenuBean bean : menuList) {
			if(bean.getMenuId() == dialogBean.getOrderBean().getLastMenuId()){
				List<MenuOptionBean> optionList = bean.getOptionList();
				for (MenuOptionBean menuOptionBean : optionList) {
					if(menuOptionBean.getOptionId() == detailId){
						menuOption = menuOptionBean;
						break menu;
					}
				}
			}
		}
		
		String text = null;
		VoipMenuAdditionDetail voipMenuAdditionDetail = dialogService.getVoipMenuAddtionDetail(detailId, dialogBean.getOrderBean().getLastMenuId(), detailId);
		if(menuOption != null){
			//存在option
			dialogBean.getOrderBean().setRemoveOptionId(detailId);
			String toWatson = getSystemAction("REMOVE_OPTION");
			WatsonUtil.doConverse(dialogBean.getDialogId(), dialogBean.getClientId(), dialogBean.getConversationId(), toWatson);
			
			text =  "Okay. You want to cancel " + voipMenuAdditionDetail.getName() + ". Are you sure?";
		}else{
			//不存在option
			text =  "Sorry. You don't have " + voipMenuAdditionDetail.getName();
		}
		
		ActionResult ar = new ActionResult(ActionResult.SYSTEM);
		ar.setEcho(text);
		return null;
	}
	
}
