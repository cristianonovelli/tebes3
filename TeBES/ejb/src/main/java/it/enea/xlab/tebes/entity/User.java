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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	 * Ogni User ha uno o pi˘ SUT => OneToMany
	 */
	@OneToMany(mappedBy="user", 
			cascade = {CascadeType.ALL,CascadeType.MERGE },fetch = FetchType.EAGER)
	private List<SUT> userSut;
	
	
	/**
	 * Molti User hanno lo stesso Role => ManyToOne 
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="role_id")
	private Role role;

/*	*//**
	 * Molti User possono appartenere a molti Group => ManyToMany 
	 * PER ORA PROÏVIAMO MANYTOONE
	 *//**/
	//@ManyToOne(cascade=CascadeType.ALL)
	//@JoinColumn(name="group_id")
	//private Group group;
	
	
	public User() {
		
	}

	
	/**
	 * Constructor without id.
	 * Si lascia la generazione dello userId al motore di persistenza (ORM - Object Relation Mapping)
	 * Il SUT si aggiunge in un secondo momento
	 */
	public User(String userName, String userSurname, String userEMail, String userPassword) {
		
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


/*	public Group getGroup() {
		return group;
	}


	public void setGroup(Group group) {
		this.group = group;
	}*/


}



