package com.nico.revision.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="fiches")
public class Fiche {

	private String guid;
	private String name;	
	private Matiere matiere;
	private String content;
	
	public Fiche()
	{}

	public Fiche(String guid, Matiere matiere, String name) {
		this.guid = guid;
		this.matiere = matiere;
		this.name = name;
	}
	
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
	
	@OneToOne
	@JoinColumn(name="matiere_id")
	public Matiere getMatiere() {
		return this.matiere;
	}
	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}
	
	@Column(name="content")
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
