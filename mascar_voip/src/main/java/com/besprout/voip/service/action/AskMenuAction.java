package com.besprout.voip.service.action;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.common.Constant;

public class AskMenuAction extends AbsAction{

	@Override
	public ActionResult process(DialogBean dialoBean, DialogResultBean resultBean) {
		if(resultBean.getValue().equals("NO")){
			if(!dialoBean.getOrderBean().getMenuBean().isEmpty()){
				dialoBean.getOrderBean().getModuleStatus().put(Constant.Module.MODULE_MENU, true);
				dialoBean.getOrderBean().setLastModule(null);
			}
			
		}
		
		return getNextActionResult();
	}
	
	
}
