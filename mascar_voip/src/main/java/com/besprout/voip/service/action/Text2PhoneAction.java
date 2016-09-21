package com.besprout.voip.service.action;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;

public class Text2PhoneAction extends AbsAction{

	@Override
	public ActionResult process(DialogBean dialoBean, DialogResultBean resultBean) {
		dialoBean.getOrderBean().setPhoneNumber(resultBean.getValue());
		dialoBean.getOrderBean().setReadOrText(true);
		return getNextActionResult();
	}
	
}
