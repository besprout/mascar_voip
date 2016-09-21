package com.besprout.voip.service.action;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;

public interface IAction {

	public ActionResult process(DialogBean dialogBean, DialogResultBean resultBean);
}
