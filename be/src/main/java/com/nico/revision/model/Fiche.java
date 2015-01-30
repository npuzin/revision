package com.nico.revision.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fiches")
public class Fiche {

	private String guid;
	private String name;	
	private int matiereId;

  	@Id	
  	@Column(name="guid")
  	public String getGuid() {
		return this.guid;
	}
  	public void setGuid(String guid) {
  		this.guid = guid;
  	}

	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="matiere_id")
	public int getMatiereId() {
		return this.matiereId;
	}
	public void setMatiereId(int matiereId) {
		this.matiereId = matiereId;
	}
	
	
}