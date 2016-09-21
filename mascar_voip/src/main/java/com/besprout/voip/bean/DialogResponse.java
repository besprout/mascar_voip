package com.besprout.voip.bean;

public class DialogResponse {
	
	public static final int CALL_HANDUP = 0;	//挂断
	public static final int CALL_HANDING = 1;	//保持
	
	private int callType = CALL_HANDING;
	private String content = null;
	private String clientId = null;
	private String conversationId = null;
	private String orderText = null;
	
	public DialogResponse(){
		
	}
	
	public DialogResponse(int callType){
		this(callType, null);
	}
	
	public DialogResponse(String content){
		this(CALL_HANDING, content);
	}
	
	public DialogResponse(int callType, String content){
		this.callType = callType;
		this.content = content;
	}

	public int getCallType() {
		return callType;
	}
	public void setCallType(int callType) {
		this.callType = callType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public String getOrderText() {
		return orderText;
	}

	public void setOrderText(String orderText) {
		this.orderText = orderText;
	}
	
}
