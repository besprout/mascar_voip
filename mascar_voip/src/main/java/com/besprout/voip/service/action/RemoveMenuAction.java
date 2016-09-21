package com.besprout.voip.service.action;

import java.util.List;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.bean.order.MenuBean;
import com.besprout.voip.bean.rest.VoipMenu;
import com.besprout.voip.common.Constant;
import com.besprout.voip.common.spring.ServiceLocator;
import com.besprout.voip.service.DialogService;
import com.besprout.voip.util.WatsonUtil;

public class RemoveMenuAction extends AbsAction {

	@Override
	public ActionResult process(DialogBean dialogBean, DialogResultBean resultBean) {
		dialogBean.getOrderBean().setLastModule(Constant.Module.MODULE_MENU);
		DialogService dialogService = ServiceLocator.getBean(DialogService.class);
		long productId =  Long.parseLong(resultBean.getValue());
		
		MenuBean menu = null;
		List<MenuBean> menuList = dialogBean.getOrderBean().getMenuBean();
		for (MenuBean menuBean : menuList) {
			if(menuBean.getMenuId() == productId){
				menu = menuBean;
				break;
			}
		}
		
		String text = null;
		VoipMenu voipMenu = dialogService.getVoipMenu(dialogBean.getStoreId(), productId);
		if(menu != null){
			dialogBean.getOrderBean().setRemoveMenuId(productId);
			String toWatson = getSystemAction("REMOVE_MENU");
			WatsonUtil.doConverse(dialogBean.getDialogId(), dialogBean.getClientId(), dialogBean.getConversationId(), toWatson);
			text =  "Okay. You want to cancel " + voipMenu.getName() + ". Are you sure?";
		}else{
			text =  "Sorry. You don't have " + voipMenu.getName();
		}
		
		ActionResult ar = new ActionResult(ActionResult.SYSTEM);
		ar.setEcho(text);
		return null;
	}
	
	
	
}
