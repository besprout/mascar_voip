package com.besprout.voip.bean.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.besprout.voip.common.Constant;

public class OrderBean implements java.io.Serializable{
	
	private List<MenuBean> menuBean = null;
	private AddressBean addressBean = null;
	private int orderMethod = Constant.ORDER_METHOD_NONE;
	private String phoneNumber = null;
	private String userName = null;
	private boolean isConfirm = false;
	private String lastModule = null;
	private long lastMenuId = 0l;
	private boolean isReadOrText = false;
	
	private long removeMenuId = 0l;
	private long removeOptionId = 0l;
	
	public Map<String, Boolean> moduleStatus = new HashMap<String, Boolean>();
	
	public OrderBean(){
		init();
	}
	
	private void init(){
		moduleStatus.put(Constant.Module.MODULE_ADDRESS, false);
		moduleStatus.put(Constant.Module.MODULE_MENU, false);
		moduleStatus.put(Constant.Module.MODULE_ORDER, false);
	}
	
	
	public List<MenuBean> getMenuBean() {
		return menuBean;
	}
	public void setMenuBean(List<MenuBean> menuBean) {
		this.menuBean = menuBean;
	}
	public AddressBean getAddressBean() {
		return addressBean;
	}
	public void setAddressBean(AddressBean addressBean) {
		this.addressBean = addressBean;
	}
	public int getOrderMethod() {
		return orderMethod;
	}
	public void setOrderMethod(int orderMethod) {
		this.orderMethod = orderMethod;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isConfirm() {
		return isConfirm;
	}
	public void setConfirm(boolean isConfirm) {
		this.isConfirm = isConfirm;
	}
	public Map<String, Boolean> getModuleStatus() {
		return moduleStatus;
	}
	public void setModuleStatus(Map<String, Boolean> moduleStatus) {
		this.moduleStatus = moduleStatus;
	}

	public String getLastModule() {
		return lastModule;
	}

	public void setLastModule(String lastModule) {
		this.lastModule = lastModule;
	}

	public long getLastMenuId() {
		return lastMenuId;
	}

	public void setLastMenuId(long lastMenuId) {
		this.lastMenuId = lastMenuId;
	}

	public boolean isReadOrText() {
		return isReadOrText;
	}

	public void setReadOrText(boolean isReadOrText) {
		this.isReadOrText = isReadOrText;
	}

	public long getRemoveMenuId() {
		return removeMenuId;
	}

	public void setRemoveMenuId(long removeMenuId) {
		this.removeMenuId = removeMenuId;
	}

	public long getRemoveOptionId() {
		return removeOptionId;
	}

	public void setRemoveOptionId(long removeOptionId) {
		this.removeOptionId = removeOptionId;
	}
	
	
	
}
