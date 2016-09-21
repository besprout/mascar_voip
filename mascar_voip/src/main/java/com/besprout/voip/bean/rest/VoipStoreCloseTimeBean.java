package com.besprout.voip.bean.rest;

public class VoipStoreCloseTimeBean {
	
	private String week;
	private String workingFrom;
	private String workingTo;
	private boolean open = false;
	
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getWorkingFrom() {
		return workingFrom;
	}
	public void setWorkingFrom(String workingFrom) {
		this.workingFrom = workingFrom;
	}
	public String getWorkingTo() {
		return workingTo;
	}
	public void setWorkingTo(String workingTo) {
		this.workingTo = workingTo;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	
}
