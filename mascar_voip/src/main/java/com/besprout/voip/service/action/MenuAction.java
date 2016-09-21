package com.besprout.voip.service.action;

import java.util.ArrayList;
import java.util.List;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.bean.order.MenuBean;
import com.besprout.voip.bean.rest.VoipMenu;
import com.besprout.voip.common.Constant;
import com.besprout.voip.common.spring.ServiceLocator;
import com.besprout.voip.service.DialogService;
import com.besprout.voip.util.WatsonUtil;

class MenuAction extends AbsAction{

	@Override
	public ActionResult process(DialogBean dialoBean, DialogResultBean resultBean) {
		dialoBean.getOrderBean().setLastModule(Constant.Module.MODULE_MENU);
		DialogService dialogService = ServiceLocator.getBean(DialogService.class);
		if(dialoBean.getOrderBean().getMenuBean() == null){
			dialoBean.getOrderBean().setMenuBean(new ArrayList<MenuBean>());
		}
		long productId =  Long.parseLong(resultBean.getValue());
		
		MenuBean menuBean = null;
		List<MenuBean> menuList = dialoBean.getOrderBean().getMenuBean();
		for (MenuBean bean : menuList) {
			if(bean.getMenuId() == productId){
				menuBean = bean;
				break;
			}
		}
		
		String text = "";
		VoipMenu voipMenu = dialogService.getVoipMenu(dialoBean.getStoreId(), productId);
		if(menuBean != null){
			text = "Sorry, you have " + voipMenu.getName();
			String toWatson = getSystemAction("ASK_MENU");
			String result = WatsonUtil.doConverse(dialoBean.getDialogId(), dialoBean.getClientId(), dialoBean.getConversationId(), toWatson);
			text += ", " + WatsonUtil.getResponse(result);
			
		}else{
			menuBean = new MenuBean();
			menuBean.setMenuId(productId);
			menuBean.setMenuName(voipMenu.getName());
			dialoBean.getOrderBean().getMenuBean().add(menuBean);
			dialoBean.getOrderBean().setLastMenuId(productId);
			
			String toWatson = getSystemAction("ASK_OPTION");
			text = WatsonUtil.doConverse(dialoBean.getDialogId(), dialoBean.getClientId(), dialoBean.getConversationId(), toWatson);
			text = WatsonUtil.getResponse(text);
		}
		
		ActionResult ar = new ActionResult(ActionResult.SYSTEM);
		ar.setEcho(text);
		return ar;
	}
	
	
}
