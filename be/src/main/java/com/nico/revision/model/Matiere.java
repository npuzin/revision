package com.nico.revision.model;

import java.util.List;

public class Matiere {

	private int id;
	private String name;	
	private User user;
	private String color;
	private List<Fiche> fiches;

	public Matiere(){
		
	}
	public Matiere(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
  	public void setId(int id) {
  		this.id = id;
  	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public User getUser() {
		return this.user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public List<Fiche> getFiches() {
	
		return this.fiches;
	}
	public void setFiches(List<Fiche> fiches) {
		
		this.fiches = fiches;
	}
	
}
