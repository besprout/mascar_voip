package com.besprout.voip.bean.rest;

import java.util.List;

public class VoipMenu {
	
	private long productId = 0;
	private String name = null;
	private List<VoipMenuAddition> additionList = null;

	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<VoipMenuAddition> getAdditionList() {
		return additionList;
	}
	public void setAdditionList(List<VoipMenuAddition> additionList) {
		this.additionList = additionList;
	}
	
}
