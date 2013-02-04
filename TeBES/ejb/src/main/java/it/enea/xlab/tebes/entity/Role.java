package it.enea.xlab.tebes.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;
	private int level;

	/**
	 * Ogni gruppo ha molti utenti => OneToMany 
	 */
	@OneToMany(mappedBy="role",
			cascade = {CascadeType.ALL,CascadeType.MERGE})
	private List<User> users;

	
	public Role() {
		
	}
	
	/**
	 * Constructor without id.
	 */
	public Role(String name, String description, int level) {
		
		this.name = name;
		this.description = description;
		this.level = level;
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

	public List<User> getUsers() {
		return users;
	}

	public void setUser(List<User> users) {
		this.users = users;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}



}

