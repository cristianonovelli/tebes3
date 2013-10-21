package it.enea.xlab.tebes.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "userinteraction")
public class UserInteraction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	
	// Message
	private String message;
	
	// Tipo del comportamento che segue il messaggio e che l'interfaccia deve presentare all'utente
	// 1. "message", due bottoni "continua", "annulla"
	// 2. "upload": form per l'upload
	// 3. "text": form per introdurre testo
	private String inputInterfaceType;

	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="session_id")
	private Session session;
	
	
	
	/*
	 * CONSTRUCTORS
	 */
	public UserInteraction() {

	}

	public UserInteraction(String message, String inputInterfaceType) {
		
		this.message = message;
		this.inputInterfaceType = inputInterfaceType;
	}

	
	
	/*
	 * GETTERS AN SETTERS
	 */
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInputInterfaceType() {
		return inputInterfaceType;
	}

	public void setInputInterfaceType(String inputInterfaceType) {
		this.inputInterfaceType = inputInterfaceType;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}

