package com.besprout.voip.service.action;

import java.util.List;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.bean.order.MenuBean;
import com.besprout.voip.bean.order.MenuOptionBean;
import com.besprout.voip.common.Constant;
import com.besprout.voip.util.StringUtil;
import com.besprout.voip.util.WatsonUtil;

public class ConfirmRemoveOptionAction extends AbsAction{

	@Override
	public ActionResult process(DialogBean dialogBean, DialogResultBean resultBean) {
		dialogBean.getOrderBean().setLastModule(Constant.Module.MODULE_MENU);
		String text = null;
		if(resultBean.getValue().equals("YES")){
			if(StringUtil.isEmpty(dialogBean.getOrderBean().getLastMenuId())){
				//没有指定哪个菜品
				String toWatson = getSystemAction("ASK_REMOVE_MENU");
				text = WatsonUtil.doConverse(dialogBean.getDialogId(), dialogBean.getClientId(), dialogBean.getConversationId(), toWatson);
				text = WatsonUtil.getResponse(text);
				
				ActionResult ar = new ActionResult(ActionResult.SYSTEM);
				ar.setEcho(text);
				return ar;
			}
			
			
			//删除option
			List<MenuBean> menuList = dialogBean.getOrderBean().getMenuBean();
			menu: for (MenuBean bean : menuList) {
				if(bean.getMenuId() == dialogBean.getOrderBean().getLastMenuId()){
					List<MenuOptionBean> optionList = bean.getOptionList();
					for (MenuOptionBean menuOptionBean : optionList) {
						if(menuOptionBean.getOptionId() == dialogBean.getOrderBean().getRemoveOptionId()){
							optionList.remove(menuOptionBean);
							
							String toWatson = getSystemAction("ASK_MENU_AGAIN");
							String result = WatsonUtil.doConverse(dialogBean.getDialogId(), dialogBean.getClientId(), dialogBean.getConversationId(), toWatson);
							result = WatsonUtil.getResponse(result);
							
							break menu;
						}
					}
				}
			}
			
			ActionResult ar = new ActionResult(ActionResult.SYSTEM);
			ar.setEcho(text);
			return ar;
		}
		
		
		
		
		return getNextActionResult();
	}
	
}
