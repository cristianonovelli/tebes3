package it.enea.xlab.tebes.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="usergroup")
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;


/*	*//**
	 * Ogni gruppo ha molti utenti => OneToMany 
	 *//*
	@OneToMany(mappedBy="group",
			cascade = {CascadeType.ALL,CascadeType.MERGE})
	private List<User> members;*/
	// ,cascade = {CascadeType.MERGE})
	@OneToMany(mappedBy="group")
	private List<User> users;
	//		cascade = {CascadeType.ALL,CascadeType.MERGE})
	 
	
	
	
	public Group() {
		
	}
	
	/**
	 * Constructor without id.
	 */
	public Group(String name, String description) {
		
		this.name = name;
		this.description = description;

	}

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

/*	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}*/

	public List<User> getUsers() {
		return users;
	}

	public void setUser(List<User> users) {
		this.users = users;
	}
}

