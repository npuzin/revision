package com.nico.revision.model;

import java.io.Serializable;
import java.sql.Timestamp;


public class Session implements Serializable {

	private String sessionId;
	private User user;
	private Timestamp creationDate;
	

  	public String getSessionId() {
		return this.sessionId;
	}
  	public void setSessionId(String sessionId) {
  		this.sessionId = sessionId;
  	}
	
	public User getUser() {
		return this.user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	

	public Timestamp getCreationDate() {
		return this.creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
		
}
