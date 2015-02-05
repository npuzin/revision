package com.nico.revision.model;


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
	
  	public String getGuid() {
		return this.guid;
	}
  	public void setGuid(String guid) {
  		this.guid = guid;
  	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}		
	public Matiere getMatiere() {
		return this.matiere;
	}
	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
