package com.besprout.voip.common;

import java.util.HashMap;
import java.util.Map;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.rest.VoipBean;

public class Extra {
	
	public static Map<String, DialogBean> dialogMap = new HashMap<String, DialogBean>();
	public static VoipBean voipBean = null;
	public static String rest_voip = null;
	public static String dialogTemplateFile = null;
	public static String dialogTempDir = null;
}
