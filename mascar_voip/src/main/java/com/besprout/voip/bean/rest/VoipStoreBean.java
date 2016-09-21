package com.besprout.voip.bean.rest;

import java.util.List;

public class VoipStoreBean {
	
	private String respCode;
	private String respDesc;
	
	private long brandId = 0;
	private String phone = null;
	private String brandName = null;
	private long storeId = 0;
	private String storeName =  null;
	private String address = null;
	private String timeZone = null;
	private List<VoipStoreCloseTimeBean> closeTime = null;
	private List<VoipMenu> menuList = null;
	private List<String> specialList = null;
	
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public long getStoreId() {
		return storeId;
	}
	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public List<VoipStoreCloseTimeBean> getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(List<VoipStoreCloseTimeBean> closeTime) {
		this.closeTime = closeTime;
	}
	public List<VoipMenu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<VoipMenu> menuList) {
		this.menuList = menuList;
	}
	public List<String> getSpecialList() {
		return specialList;
	}
	public void setSpecialList(List<String> specialList) {
		this.specialList = specialList;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
