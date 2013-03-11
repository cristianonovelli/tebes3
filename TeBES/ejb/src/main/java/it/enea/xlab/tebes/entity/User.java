package it.enea.xlab.tebes.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	private String name;
	private String surname;
	private String eMail;
	private String password;
	
	
	/**
	 * Lista dei SUT dello User 
	 * Ogni User ha uno o più SUT => OneToMany
	 */
	//,CascadeType.MERGE,CascadeType.REMOVE
	// , fetch = FetchType.EAGER
	// @OneToMany(mappedBy="user",cascade = {CascadeType.ALL})
	@OneToMany(mappedBy="user")
	private List<SUT> userSut;
	
	
	/**
	 * ROLE dello User
	 * Molti User hanno lo stesso Role => ManyToOne 
	 */
	// (cascade=CascadeType.MERGE)
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;

	
	/**
	 * GROUP dello User
	 * Molti User hanno lo stesso Group => ManyToOne 
	 */
	// (cascade=CascadeType.MERGE)
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;
	
	
	/**
	 * Lista dei TEST PLANS dello User 
	 * Ogni User ha zero o più TestPlans => OneToMany
	 */
	// , cascade = CascadeType.MERGE)
	@OneToMany(mappedBy="user")
	private List<TestPlan> testPlans;
	
	
	
	public User() {
		
	}

	
	/**
	 * Constructor without id.
	 * Si lascia la generazione dello userId al motore di persistenza (ORM - Object Relation Mapping)
	 * Il SUT si aggiunge in un secondo momento
	 */
	public User(String userName, String userSurname, String userEMail, String userPassword) {
		
		//this.userId = userId;
		this.name = userName;
		this.surname = userSurname;
		this.eMail = userEMail;
		this.password = userPassword;
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


	public List<SUT> getUserSut() {
		return userSut;
	}


	public void setUserSut(List<SUT> userSut) {
		this.userSut = userSut;
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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public List<SUT> getSutList() {
		return userSut;
	}


	public void setSutList(List<SUT> userSut) {
		this.userSut = userSut;
	}
	

	public Role getRole() {
		return role;
	}

	
	public void setRole(Role role) {
		this.role = role;
	}

	public Group getGroup() {
		return group;
	}


	public void setGroup(Group group) {
		this.group = group;
	}


	public List<TestPlan> getTestPlans() {
		return testPlans;
	}


	public void setTestPlans(List<TestPlan> testPlans) {
		this.testPlans = testPlans;
	}


}



