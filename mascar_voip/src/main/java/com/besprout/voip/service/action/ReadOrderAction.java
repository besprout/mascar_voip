package com.besprout.voip.service.action;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;

public class ReadOrderAction extends AbsAction{

	@Override
	public ActionResult process(DialogBean dialoBean, DialogResultBean resultBean) {
		dialoBean.getOrderBean().setReadOrText(true);
		return getNextActionResult();
	}
	
	
	
}
