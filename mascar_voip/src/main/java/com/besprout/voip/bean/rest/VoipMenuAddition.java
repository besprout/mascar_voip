package com.besprout.voip.bean.rest;

import java.util.List;

public class VoipMenuAddition {
	
	private long additionId = 0l;
	private String name = null;
	private int isMultiple;
	private int maxChoice;
	private int allowAllocateQuantities;
	private int freeOfSelections;
	private int type;
	private int isNeedChoice = 0;
	
	private List<VoipMenuAdditionDetail> detailList = null;
	
	public long getAdditionId() {
		return additionId;
	}
	public void setAdditionId(long additionId) {
		this.additionId = additionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIsMultiple() {
		return isMultiple;
	}
	public void setIsMultiple(int isMultiple) {
		this.isMultiple = isMultiple;
	}
	public int getMaxChoice() {
		return maxChoice;
	}
	public void setMaxChoice(int maxChoice) {
		this.maxChoice = maxChoice;
	}
	public int getAllowAllocateQuantities() {
		return allowAllocateQuantities;
	}
	public void setAllowAllocateQuantities(int allowAllocateQuantities) {
		this.allowAllocateQuantities = allowAllocateQuantities;
	}
	public int getFreeOfSelections() {
		return freeOfSelections;
	}
	public void setFreeOfSelections(int freeOfSelections) {
		this.freeOfSelections = freeOfSelections;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<VoipMenuAdditionDetail> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<VoipMenuAdditionDetail> detailList) {
		this.detailList = detailList;
	}
	public int getIsNeedChoice() {
		return isNeedChoice;
	}
	public void setIsNeedChoice(int isNeedChoice) {
		this.isNeedChoice = isNeedChoice;
	}
	
}
