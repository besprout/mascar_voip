package com.besprout.voip.service.action;

import java.util.List;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.bean.rest.VoipMenu;
import com.besprout.voip.bean.rest.VoipStoreBean;
import com.besprout.voip.common.Constant;
import com.besprout.voip.common.spring.ServiceLocator;
import com.besprout.voip.service.DialogService;

public class HaveMenuAction extends AbsAction{

	@Override
	public ActionResult process(DialogBean dialogBean, DialogResultBean resultBean) {
		dialogBean.getOrderBean().setLastModule(Constant.Module.MODULE_MENU);
		DialogService dialogService = ServiceLocator.getBean(DialogService.class);
		VoipStoreBean storeBean = dialogService.getVoipStoreBean(dialogBean.getStoreId());
		
		
		VoipMenu menu = null;
		boolean exists = false;
		List<VoipMenu> menuList = storeBean.getMenuList();
		for (VoipMenu voipMenu : menuList) {
			if(voipMenu.equals(resultBean.getValue())){
				menu = voipMenu;
				exists = true;
				break;
			}
		}
		
		ActionResult ar = new ActionResult(ActionResult.SYSTEM);
		if(exists){
			ar.setEcho("Yes, we have " + menu.getName());
		}else{
			ar.setEcho("Sorry, we don't have " + resultBean.getValue() + ". Can I get you something else");
		}
		
		return ar;
	}
	
}
