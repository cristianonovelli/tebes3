package it.enea.xlab.tebes.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class UserGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;


	/**
	 * Ogni gruppo ha molti utenti => OneToMany 
	 */
	@OneToMany(mappedBy="userGroup")
	private List<User> user;

	
	/**
	 * Constructor without id.
	 */
	public UserGroup(String name, String description) {

		user = new ArrayList<User>();
		
		this.name = name;
		this.description = description;
	}

	/**
	 * Constructor with id.
	 */
/*	public UserGroup(Long id, String name, String description) {

		this.id = id;
		this.name = name;
		this.description = description;
	}*/

	
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

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	



}

