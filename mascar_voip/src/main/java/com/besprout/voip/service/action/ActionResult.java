package com.besprout.voip.service.action;

public class ActionResult {
	
	public static final int WATSON = 1;
	public static final int SYSTEM = 2;
	
	private int type = SYSTEM;
	private String operate = null;
	private String echo = null;
	
	public ActionResult(){
		
	}
	
	public ActionResult(int type){
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getEcho() {
		return echo;
	}
	public void setEcho(String echo) {
		this.echo = echo;
	}
	
	
	
	
}
