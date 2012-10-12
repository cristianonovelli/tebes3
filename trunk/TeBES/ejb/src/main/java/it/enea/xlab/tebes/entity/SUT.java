package it.enea.xlab.tebes.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SUT implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;


	@ManyToOne
	private User user;
	
	public SUT() {
		
	}
	
	
	/**
	 * Constructor without id.
	 */
	public SUT(String name, String description) {

		this.name = name;
		this.description = description;
	}

	
	/**
	 * Constructor with id.
	 */
	public SUT(Long id, String name, String description) {

		this.id = id;
		this.name = name;
		this.description = description;
	}

	

	/*
	 * Getters and Setters
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public void addToUser(User tempUser) {
		
		this.user = tempUser;
	}

	
}
