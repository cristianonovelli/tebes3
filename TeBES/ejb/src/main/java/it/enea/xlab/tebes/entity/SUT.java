package it.enea.xlab.tebes.entity;

import it.enea.xlab.tebes.common.SUTConstants;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "sut")
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
	private SUTInteraction interaction;



	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="user_id")
	private User user;

	
	@OneToMany(mappedBy="sut")
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<Session> sessions;
	
	
	
	public SUT() {
		
	}
	
	
	/**
	 * Constructor without id.
	 */
	public SUT(String name, String type, SUTInteraction interaction, String description) {

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

	public SUTInteraction getInteraction() {
		return interaction;
	}

	public void setInteraction(SUTInteraction interaction) {
		this.interaction = interaction;
	}


	public static String[] getSUTTypeList() {

		return new String[]{SUTConstants.SUT_TYPE1_DOCUMENT, SUTConstants.SUT_TYPE2_TRANSPORT, SUTConstants.SUT_TYPE3_PROCESS};
	}


	public List<Session> getSessions() {
		return sessions;
	}


	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

}


