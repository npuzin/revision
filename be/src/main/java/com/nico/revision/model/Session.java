package com.nico.revision.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sessions")
public class Session {

	private String sessionId;
	private int userId;
	private Timestamp creationDate;
	
	
  	@Id	
  	@Column(name="session_id")
  	public String getSessionId() {
		return this.sessionId;
	}
  	public void setSessionId(String sessionId) {
  		this.sessionId = sessionId;
  	}

	@Column(name="user_id")
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Column(name="creation_date")
	public Timestamp getCreationDate() {
		return this.creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
		
}
