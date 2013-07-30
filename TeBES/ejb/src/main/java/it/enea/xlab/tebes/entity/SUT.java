package it.enea.xlab.tebes.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class SUT implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	// descrizione testuale
	private String description;
	
	// tipi possibili: document, transport, process
	private String type;
	
	// linguaggi possibili:
	// xml: se sto effettuando validazione schema o schematron generica
	//private String language;
	
	// riferimento alla descrizione delle regole che descrivono il linguaggio usato
	// (p.es. schema ubl, schema ebms, schema ebbp, profilo, ecc.)
	//private String reference;
	
	// interazioni possibili: website (upload), email, ws, cpa
	@OneToOne(mappedBy="sut",cascade = CascadeType.ALL)
	private Interaction interaction;



	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="user_id")
	private User user;
	
	public SUT() {
		
	}
	
	
	/**
	 * Constructor without id.
	 */
	public SUT(String name, String type, Interaction interaction, String description) {

		this.setName(name);
		this.setType(type);
		this.setInteraction(interaction);
		this.setDescription(description);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Interaction getInteraction() {
		return interaction;
	}

	public void setInteraction(Interaction interaction) {
		this.interaction = interaction;
	}

}


