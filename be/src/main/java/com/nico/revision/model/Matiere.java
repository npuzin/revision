package com.nico.revision.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="matieres")
public class Matiere {

	private int id;
	private String name;	
	private User user;
	private String color;

	public Matiere(){
		
	}
	
	public Matiere(int id) {
		this.id = id;
	}
  	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
  	public void setId(int id) {
  		this.id = id;
  	}

	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return this.user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name="color")
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
}
