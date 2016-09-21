package com.besprout.voip.bean.rest;

import java.util.List;

public class VoipBean {
	
	List<VoipStoreBean> storeList = null;

	public List<VoipStoreBean> getStoreList() {
		return storeList;
	}
	public void setStoreList(List<VoipStoreBean> storeList) {
		this.storeList = storeList;
	}
	
}
