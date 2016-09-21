package com.besprout.voip.service.action;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.bean.order.AddressBean;

class ChooseAddressAction extends AbsAction{

	@Override
	public ActionResult process(DialogBean dialoBean, DialogResultBean resultBean) {
		AddressBean addressBean = dialoBean.getOrderBean().getAddressBean();
		if(resultBean.getValue().equals("first")){
			addressBean.setConfirmAddress(addressBean.getAddress1());
			addressBean.setConfirm(true);
		}else if(resultBean.getValue().equals("second")){
			addressBean.setConfirmAddress(addressBean.getAddress2());
			addressBean.setConfirm(true);
		}
		return getNextActionResult();
	}

}
