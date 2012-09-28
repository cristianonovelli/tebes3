package it.enea.xlab.tebes.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String code;
	private String name;
	private String surname;
	private String eMail;
	
	/**
	 * Ogni User ha uno o più SUT => OneToMany
	 */
	@OneToMany(mappedBy="user", 
			cascade = {CascadeType.PERSIST,CascadeType.MERGE },fetch = FetchType.EAGER)
	private List<SUT> userSut;
	
	
	/**
	 * Molti User hanno lo stesso Group => ManyToOne 
	 */
	@ManyToOne
	private UserGroup userGroup;

	public User() {
		
	}

	
	/**
	 * Constructor without id.
	 * Si lascia la generazione dello userId al motore di persistenza (ORM - Object Relation Mapping)
	 * Il SUT si aggiunge in un secondo momento
	 */
	public User(String userCode, String userName, String userSurname, String userEMail) {
		
		this.code = userCode;
		this.name = userName;
		this.surname = userSurname;
		this.eMail = userEMail;
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


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String geteMail() {
		return eMail;
	}


	public void seteMail(String eMail) {
		this.eMail = eMail;
	}


	public List<SUT> getUserSut() {
		return userSut;
	}


	public void setUserSut(List<SUT> userSut) {
		this.userSut = userSut;
	}

/*	public void addSut(SUT userSut) {

		this.userSut.add(userSut);
		return;
	}*/

	public UserGroup getUserGroup() {
		return userGroup;
	}


	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	/**
	 * Constructor with id.
	 * Si lascia la generazione dello userId al motore di persistenza (ORM - Object Relation Mapping)
	 */
/*	public User(Long userId, String userCode, String userName, String userSurname,
			String userEMail, SUT userSut, UserGroup userGroup) {
		
		this.userId = userId;
		this.userCode = userCode;
		this.userName = userName;
		this.userSurname = userSurname;
		this.userEMail = userEMail;
		this.userSut.add(userSut);
		this.userGroup = userGroup;
	}*/
	
	

	


}



