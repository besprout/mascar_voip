package com.besprout.voip.common;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	
	public static final int ORDER_METHOD_NONE = 0;
	public static final int ORDER_METHOD_PICKUP = 1;		//pickup
	public static final int ORDER_METHOD_DELIVERY = 2;		//delivery
	
	public static final Map<String, String> ACTION_MODULE = new HashMap<String, String>();
	static{
		ACTION_MODULE.put(ActionType.ADDRESS, Module.MODULE_ADDRESS);
		ACTION_MODULE.put(ActionType.ZIP_CODE, Module.MODULE_ADDRESS);
		ACTION_MODULE.put(ActionType.CHOOSE_ADDRESS, Module.MODULE_ADDRESS);
		ACTION_MODULE.put(ActionType.CONFIRM_ADDRESS, Module.MODULE_ADDRESS);
		ACTION_MODULE.put(ActionType.ORDER_METHOD, Module.MODULE_ADDRESS);
		ACTION_MODULE.put(ActionType.MENU, Module.MODULE_MENU);
		ACTION_MODULE.put(ActionType.IS_EXISTS_MENU, Module.MODULE_MENU);
		ACTION_MODULE.put(ActionType.HAVE_MENU, Module.MODULE_MENU);
		ACTION_MODULE.put(ActionType.IS_TAKE_OUT_INSTEAD, Module.MODULE_ORDER);
		ACTION_MODULE.put(ActionType.YES_TAKE_OUT_INSTEAD, Module.MODULE_ORDER);
		ACTION_MODULE.put(ActionType.CLOSE, Module.MODUEL_SYSTEM);
		ACTION_MODULE.put(ActionType.NEXT_QUESTION, Module.MODUEL_SYSTEM);
		ACTION_MODULE.put(ActionType.READ_ORDER, Module.MODUEL_SYSTEM);
		ACTION_MODULE.put(ActionType.PHONE_NUMBER, Module.MODUEL_SYSTEM);
	}
	
	public interface Module{
		public static final String MODULE_ADDRESS = "ADDRESS";
		public static final String MODULE_MENU = "MENU";
		public static final String MODULE_ORDER = "ORDER";
		public static final String MODUEL_SYSTEM = "SYSTEM";
	}
	
	public interface ResultType{
		public static final String ACTION_S = "[action]";
		public static final String ACTION_E = "[/action]";
		public static final String VALUE_S = "[value]";
		public static final String VALUE_E = "[/value]";
		public static final String ECHO_S = "[echo]";
		public static final String ECHO_E = "[/echo]";
		public static final String SYSTEM_S = "[system]";
		public static final String SYSTEM_E = "[/system]";
		public static final String OPERATE_S = "[operate]";
		public static final String OPERATE_E = "[/operate]";
	}
	
	public interface ActionType{
		public static final String CALL_REAL_PERSON = "CALL_REAL_PERSON";
		public static final String CLOSE = "CLOSE";
		public static final String ORDER_METHOD = "ORDER_METHOD";
		public static final String ADDRESS = "ADDRESS";
		public static final String ZIP_CODE = "ZIP_CODE";
		public static final String YES_TAKE_OUT_INSTEAD = "YES_TAKE_OUT_INSTEAD";
		public static final String NEXT_QUESTION = "NEXT_QUESTION";
		public static final String READ_ORDER = "READ_ORDER";
		public static final String PHONE_NUMBER = "PHONE_NUMBER";
		public static final String CHOOSE_ADDRESS = "CHOOSE_ADDRESS";
		public static final String IS_TAKE_OUT_INSTEAD = "IS_TAKE_OUT_INSTEAD";
		public static final String MENU = "MENU";
		public static final String IS_EXISTS_MENU = "IS_EXISTS_MENU";
		public static final String CONFIRM_ADDRESS = "CONFIRM_ADDRESS";
		public static final String ASK_MENU = "ASK_MENU";
		public static final String OPTION = "OPTION";
		public static final String TEXT_TO_PHONE = "TEXT_TO_PHONE";
		public static final String HAVE_MENU = "HAVE_MENU";
		public static final String REMOVE_MENU = "REMOVE_MENU";
		public static final String REMOVE_OPTION = "REMOVE_OPTION";
		public static final String CONFIRM_REMOVE_MENU = "CONFIRM_REMOVE_MENU";
		public static final String CONFIRM_REMOVE_OPTION = "CONFIRM_REMOVE_OPTION";
	}
	
	public interface SystemType{
		public static final String ZIP_CODE = "ZIP_CODE";
		public static final String CHOOSE_ADDRESS = "CHOOSE_ADDRESS";
		public static final String HAVE_MENU = "HAVE_MENU";
		public static final String EXISTS_MENU = "EXISTS_MENU";
		public static final String NO_HAVE_MENU = "NO_HAVE_MENU";
		public static final String ASK_NEED = "ASK_NEED";
	}
	
	public interface EchoType{
		public static final String SPECIAL = "\\{SPECIAL\\}";
		public static final String CLOSETIME = "\\{CLOSE_TIME\\}";
		public static final String ADDRESS = "\\{ADDRESS\\}";
		public static final String BRAND_NAME = "\\{BRAND_NAME\\}";
		public static final String PHONE = "\\{PHONE\\}";
	}
	
}
