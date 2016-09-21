package com.besprout.voip.service.action;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.common.Constant;

public class ConfirmAddressAction extends AbsAction{

	@Override
	public ActionResult process(DialogBean dialoBean, DialogResultBean resultBean) {
		if(resultBean.getValue().equals("YES")){
			dialoBean.getOrderBean().setLastModule(null);
			dialoBean.getOrderBean().getModuleStatus().put(Constant.Module.MODULE_ADDRESS, true);
			dialoBean.getOrderBean().getAddressBean().setConfirm(true);
		}else{
			dialoBean.getOrderBean().getAddressBean().setConfirm(false);
			dialoBean.getOrderBean().getAddressBean().setAddress(null);
			dialoBean.getOrderBean().getAddressBean().setZipCode(null);
		}
		return getNextActionResult();
	}
	
	
}
