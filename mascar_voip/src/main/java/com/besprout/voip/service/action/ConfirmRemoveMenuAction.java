package com.besprout.voip.service.action;

import java.util.List;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.bean.order.MenuBean;
import com.besprout.voip.util.WatsonUtil;

public class ConfirmRemoveMenuAction extends AbsAction{

	@Override
	public ActionResult process(DialogBean dialogBean, DialogResultBean resultBean) {
		String value = resultBean.getValue();
		if(value.equals("YES")){
			MenuBean menu = null;
			List<MenuBean> menuList = dialogBean.getOrderBean().getMenuBean();
			for (MenuBean menuBean : menuList) {
				if(menuBean.getMenuId() == dialogBean.getOrderBean().getRemoveMenuId()){
					menu = menuBean;
					break;
				}
			}
			
			if(menu != null){
				menuList.remove(menuList);
				
				String toWatson = getSystemAction("ASK_MENU_AGAIN");
				String result = WatsonUtil.doConverse(dialogBean.getDialogId(), dialogBean.getClientId(), dialogBean.getConversationId(), toWatson);
				result = WatsonUtil.getResponse(result);
				ActionResult ar = new ActionResult(ActionResult.SYSTEM);
				ar.setEcho(result);
				return ar;
			}
		}
		
		return getNextActionResult();
	}
	
}
