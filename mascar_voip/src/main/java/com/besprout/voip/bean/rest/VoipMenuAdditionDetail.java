package com.besprout.voip.bean.rest;

public class VoipMenuAdditionDetail {
	
	private long detailId;
	private long additionId;
	private String name = null;
	
	public long getDetailId() {
		return detailId;
	}
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getAdditionId() {
		return additionId;
	}
	public void setAdditionId(long additionId) {
		this.additionId = additionId;
	}
	
	
}
