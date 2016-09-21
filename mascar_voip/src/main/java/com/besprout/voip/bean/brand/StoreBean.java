package com.besprout.voip.bean.brand;

import java.util.List;

import com.besprout.voip.bean.order.MenuBean;

public class StoreBean {
	
	private long storeId = 0L;
	private long brandId = 0L;
	private List<MenuBean> menuList = null;

	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}

	public long getBrandId() {
		return brandId;
	}

	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}

	public List<MenuBean> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<MenuBean> menuList) {
		this.menuList = menuList;
	}
	
	
	
}
