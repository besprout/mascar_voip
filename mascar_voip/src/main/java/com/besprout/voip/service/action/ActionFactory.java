package com.besprout.voip.service.action;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResultBean;
import com.besprout.voip.common.Constant;

public class ActionFactory {
	
	private static ActionFactory factory = null;
	
	private ActionFactory(){
		
	}
	
	public static final ActionFactory getInstance(){
		if(factory == null){
			factory = new ActionFactory();
		}
		return factory;
	}
	
	
	public ActionResult processAction(DialogBean dialoBean, DialogResultBean resultBean){
		IAction act = null;
		String action = resultBean.getAction();
		
		if(action.equals(Constant.ActionType.ADDRESS)){
			act = new AddressAction();
		}else if(action.equals(Constant.ActionType.CALL_REAL_PERSON)){
			
		}else if(action.equals(Constant.ActionType.CHOOSE_ADDRESS)){
			act = new ChooseAddressAction();
		}else if(action.equals(Constant.ActionType.CLOSE)){
			
		}else if(action.equals(Constant.ActionType.IS_EXISTS_MENU)){
			act = new IsExistsMenuAction();
		}else if(action.equals(Constant.ActionType.IS_TAKE_OUT_INSTEAD)){
			act = new IsTakeoutInsteadAction();
		}else if(action.equals(Constant.ActionType.MENU)){
			act = new MenuAction();
		}else if(action.equals(Constant.ActionType.NEXT_QUESTION)){
			act = new NextQuestionAction();
		}else if(action.equals(Constant.ActionType.ORDER_METHOD)){
			act = new OrderMethodAction();
		}else if(action.equals(Constant.ActionType.PHONE_NUMBER)){
			act = new PhoneNumberAction();
		}else if(action.equals(Constant.ActionType.READ_ORDER)){
			act = new ReadOrderAction();
		}else if(action.equals(Constant.ActionType.YES_TAKE_OUT_INSTEAD)){
			act = new YesTakeoutInsteadAction();
		}else if(action.equals(Constant.ActionType.ZIP_CODE)){
			act = new ZipCodeAction();
		}else if(action.equals(Constant.ActionType.CONFIRM_ADDRESS)){
			act = new ConfirmAddressAction();
		}else if(action.equals(Constant.ActionType.ASK_MENU)){
			act = new AskMenuAction();
		}else if(action.equals(Constant.ActionType.OPTION)){
			act = new OptionAction();
		}else if(action.equals(Constant.ActionType.TEXT_TO_PHONE)){
			act = new Text2PhoneAction();
		}else if(action.equals(Constant.ActionType.HAVE_MENU)){
			act = new HaveMenuAction();
		}else if(action.equals(Constant.ActionType.REMOVE_MENU)){
			act = new RemoveMenuAction();
		}else if(action.equals(Constant.ActionType.REMOVE_OPTION)){
			act = new RemoveOptionAction();
		}else if(action.equals(Constant.ActionType.CONFIRM_REMOVE_MENU)){
			act = new ConfirmRemoveMenuAction();
		}else if(action.equals(Constant.ActionType.CONFIRM_REMOVE_OPTION)){
			act = new ConfirmRemoveOptionAction();
		}
		
		return act.process(dialoBean, resultBean);
	}
	
}
