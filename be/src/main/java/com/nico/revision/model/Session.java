package com.nico.revision.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="sessions")
public class Session implements Serializable {

	private String sessionId;
	private User user;
	private Timestamp creationDate;
	
	
  	@Id	
  	@Column(name="session_id")
  	public String getSessionId() {
		return this.sessionId;
	}
  	public void setSessionId(String sessionId) {
  		this.sessionId = sessionId;
  	}
	
	@OneToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return this.user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name="creation_date")
	public Timestamp getCreationDate() {
		return this.creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
		
}
