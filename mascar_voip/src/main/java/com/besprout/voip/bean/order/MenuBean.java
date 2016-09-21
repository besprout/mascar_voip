package com.besprout.voip.bean.order;

import java.util.ArrayList;
import java.util.List;

public class MenuBean implements java.io.Serializable{
	
	private long menuId = 0L;
	private String menuName = null;
	private List<MenuOptionBean> optionList = new ArrayList<MenuOptionBean>();
	
	public long getMenuId() {
		return menuId;
	}
	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public List<MenuOptionBean> getOptionList() {
		return optionList;
	}
	public void setOptionList(List<MenuOptionBean> optionList) {
		this.optionList = optionList;
	}
	
	
	
}
