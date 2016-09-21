package com.besprout.voip.service.action;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.bean.order.OrderBean;
import com.besprout.voip.common.Constant;

public class YesTakeoutInsteadAction implements IAction{

	@Override
	public ActionResult process(DialogBean dialoBean, DialogResultBean resultBean) {
		OrderBean orderBean = dialoBean.getOrderBean();
		orderBean.setOrderMethod(Constant.ORDER_METHOD_PICKUP);
		return null;
	}
	
	
}
