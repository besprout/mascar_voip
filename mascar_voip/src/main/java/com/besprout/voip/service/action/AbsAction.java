package com.besprout.voip.service.action;

import com.besprout.voip.common.Constant;

public abstract class AbsAction implements IAction{
	
	protected ActionResult getNextActionResult(){
		ActionResult ar = new ActionResult();
		ar.setType(ActionResult.WATSON);
		ar.setOperate(Constant.ResultType.ACTION_S + Constant.ActionType.NEXT_QUESTION + Constant.ResultType.ACTION_E);
		return ar;
	}
	
	protected String getSystemAction(String text){
		return Constant.ResultType.SYSTEM_S + text + Constant.ResultType.SYSTEM_E;
	}
	
}
