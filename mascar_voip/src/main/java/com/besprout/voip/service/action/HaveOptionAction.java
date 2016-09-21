package com.besprout.voip.service.action;

import java.util.List;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.bean.rest.VoipMenu;
import com.besprout.voip.bean.rest.VoipMenuAddition;
import com.besprout.voip.bean.rest.VoipStoreBean;
import com.besprout.voip.common.Constant;
import com.besprout.voip.common.spring.ServiceLocator;
import com.besprout.voip.service.DialogService;
import com.besprout.voip.util.WatsonUtil;

public class HaveOptionAction extends AbsAction{

	@Override
	public ActionResult process(DialogBean dialoBean, DialogResultBean resultBean) {
		DialogService dialogService = ServiceLocator.getBean(DialogService.class);
		String option = resultBean.getValue();
		
		
		boolean exists = false;
		VoipStoreBean storeBean = dialogService.getVoipStoreBean(dialoBean.getStoreId());
		List<VoipMenu> menuList = storeBean.getMenuList();
		menu: for (VoipMenu voipMenu : menuList) {
			if(voipMenu.getProductId() == dialoBean.getOrderBean().getLastMenuId()){
				List<VoipMenuAddition> additionList = voipMenu.getAdditionList();
				for (VoipMenuAddition voipMenuAddition : additionList) {
					if(voipMenuAddition.getName().equals(option)){
						exists = true;
						break menu;
					}
				}
			}
		}
		
		String text = null;
		if(exists){
			text = "Yes";
		}else{
			String toWatson = getSystemAction("ASK_OPTION");
			WatsonUtil.doConverse(dialoBean.getDialogId(), dialoBean.getClientId(), dialoBean.getConversationId(), toWatson);
			text = "Sorry, we don't have " + option + ". Would you like to pick something else? ";
		}
		
		ActionResult ar = new ActionResult(ActionResult.SYSTEM);
		ar.setEcho(text);
		return ar;
	}

}
