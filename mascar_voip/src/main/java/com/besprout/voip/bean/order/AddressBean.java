package com.besprout.voip.bean.order;

import com.besprout.voip.bean.Address;

public class AddressBean implements java.io.Serializable{
	
	private String address = null;
	private String zipCode = null;
	
	private Address address1 = null;
	private Address address2 = null;
	
	private Address confirmAddress = null;
	private boolean isConfirm = false;
	
	public void init(){
		this.address = null;
		this.zipCode = null;
		this.address1 = null;
		this.address2 = null;
		this.confirmAddress = null;
		this.isConfirm = false;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public boolean isConfirm() {
		return isConfirm;
	}
	public void setConfirm(boolean isConfirm) {
		this.isConfirm = isConfirm;
	}
	public Address getAddress1() {
		return address1;
	}
	public void setAddress1(Address address1) {
		this.address1 = address1;
	}
	public Address getAddress2() {
		return address2;
	}
	public void setAddress2(Address address2) {
		this.address2 = address2;
	}
	public Address getConfirmAddress() {
		return confirmAddress;
	}
	public void setConfirmAddress(Address confirmAddress) {
		this.confirmAddress = confirmAddress;
	}
	
}
