package com.besprout.voip.service.action;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.bean.order.AddressBean;

public class ZipCodeAction extends AbsAction{

	@Override
	public ActionResult process(DialogBean dialoBean, DialogResultBean resultBean) {
		AddressBean addressBean = dialoBean.getOrderBean().getAddressBean();
		if(addressBean == null){
			addressBean = new AddressBean();
			dialoBean.getOrderBean().setAddressBean(addressBean);
		}
		addressBean.setZipCode(resultBean.getValue());
		
		return getNextActionResult();
	}
	
}
