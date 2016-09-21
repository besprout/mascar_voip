package com.besprout.voip.bean.order;

public class MenuOptionBean implements java.io.Serializable{
	
	private long optionId = 0L;
	private String optionName = null;
	
	public long getOptionId() {
		return optionId;
	}
	public void setOptionId(long optionId) {
		this.optionId = optionId;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	
	
}
