package com.ecloud.deploy;

import java.io.Serializable;

public class EventBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String eventName;
	private String eventContent;
	
	public EventBean() {
		super();
	}

	public EventBean(String eventName, String eventContent) {
		super();
		this.eventName = eventName;
		this.eventContent = eventContent;
	}
	
	
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventContent() {
		return eventContent;
	}
	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}
	
	

}
