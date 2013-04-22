package it.enea.xlab.tebes.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class SUT implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	// tipi possibili: document, transport, process
	private String type;
	
	// linguaggi possibili:
	// xml: se sto effettuando validazione schema o schematron
	// ebms: se sto effettuando validazione sulla transazione
	// ebbp: se sto effettuando validazione sul processo
	private String language;
	
	// riferimento alla descrizione delle regole che descrivono il linguaggio usato
	// (p.es. schema ubl, schema ebms, schema ebbp, profilo, ecc.)
	private String reference;
	
	// interazioni possibili: website (upload), email, ws, cpa
	//@OneToOne
	//@PrimaryKeyJoinColumn(name="id", referencedColumnName="interaction_sut_id")
	@OneToOne(mappedBy="sut",cascade = CascadeType.ALL)
	private Interaction interaction;
	
	// descrizione testuale
	private String description;


	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="user_id")
	private User user;
	
	public SUT() {
		
	}
	
	
	/**
	 * Constructor without id.
	 */
	public SUT(String name, String type, String language, String reference, Interaction interaction, String description) {

		this.setName(name);
		this.setType(type);
		this.setLanguage(language);
		this.setReference(reference);
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


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getReference() {
		return reference;
	}


	public void setReference(String reference) {
		this.reference = reference;
	}


	public Interaction getInteraction() {
		return interaction;
	}


	public void setInteraction(Interaction interaction) {
		this.interaction = interaction;
	}

}

