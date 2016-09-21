package com.besprout.voip.service.action;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.bean.order.OrderBean;

public class PhoneNumberAction implements IAction{

	@Override
	public ActionResult process(DialogBean dialoBean, DialogResultBean resultBean) {
		OrderBean orderBean = dialoBean.getOrderBean();
		orderBean.setPhoneNumber(resultBean.getValue());
		return null;
	}
	
	
}
