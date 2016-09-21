package com.besprout.voip.service.action;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.bean.order.OrderBean;
import com.besprout.voip.common.Constant;

public class OrderMethodAction extends AbsAction{

	@Override
	public ActionResult process(DialogBean dialoBean, DialogResultBean resultBean) {
		OrderBean orderBean = dialoBean.getOrderBean();
		if(resultBean.getValue().equals("pickup")){
			orderBean.setOrderMethod(Constant.ORDER_METHOD_PICKUP);
		}else{
			if(Constant.ORDER_METHOD_DELIVERY != orderBean.getOrderMethod()){
				orderBean.moduleStatus.put(Constant.Module.MODULE_ADDRESS, false);
				orderBean.setOrderMethod(Constant.ORDER_METHOD_DELIVERY);
				if(orderBean.getAddressBean() != null){
					orderBean.getAddressBean().init();
				}
			}
		}
		
		return getNextActionResult();
	}
	
	
	
}
