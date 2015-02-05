package com.nico.revision.model;

import java.io.Serializable;


public class User implements Serializable {

	private int id;
	private String email;		


	public int getId() {
		return id;
	}
  	public void setId(int id) {
  		this.id = id;
  	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
