package com.besprout.voip.bean;

import java.util.List;

public class ConversationBean {
	
	private String input = null;
	private long conversation_id = 0l;
	private long client_id = 0l;
	private List<String> response = null;
	private double confidence = 0d;
	
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public long getConversation_id() {
		return conversation_id;
	}
	public void setConversation_id(long conversation_id) {
		this.conversation_id = conversation_id;
	}
	public long getClient_id() {
		return client_id;
	}
	public void setClient_id(long client_id) {
		this.client_id = client_id;
	}
	public List<String> getResponse() {
		return response;
	}
	public void setResponse(List<String> response) {
		this.response = response;
	}
	public double getConfidence() {
		return confidence;
	}
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}
	
}
