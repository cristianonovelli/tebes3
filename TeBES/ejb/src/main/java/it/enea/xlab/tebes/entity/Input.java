package it.enea.xlab.tebes.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Input implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	// descrizione testuale
	private String description;
	
	// tipi possibili: document, transport, process
	private String type;
	
	private String interaction;
	// Set to "true" when SUT's User checked
	private boolean isInteractionOK;
	
	
	private String fileIdRef;
	
	
	

	
	
	@ManyToOne
	@JoinColumn(name="testaction_id")
	Action testAction;
	
	
	public Input() {
		
	}
	
	
	/**
	 * Constructor without id.
	 */
	public Input(String name, String description, String type,
			String interaction, String fileIdRef, boolean interactionOK) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.interaction = interaction;
		this.setFileIdRef(fileIdRef);
		this.setInteractionOK(interactionOK);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getInteraction() {
		return interaction;
	}


	public void setInteraction(String interaction) {
		this.interaction = interaction;
	}





	public Action getTestAction() {
		return testAction;
	}


	public void setTestAction(Action testAction) {
		this.testAction = testAction;
	}


	public void addToAction(Action action) {
		
		this.testAction = action;
	}


	public Action getAction() {
		return this.testAction;
	}


	public void setAction(Action action) {
		this.testAction = action;
	}


	public String getFileIdRef() {
		return fileIdRef;
	}


	public void setFileIdRef(String fileIdRef) {
		this.fileIdRef = fileIdRef;
	}


	public boolean isInteractionOK() {
		return isInteractionOK;
	}


	public void setInteractionOK(boolean isInteractionOK) {
		this.isInteractionOK = isInteractionOK;
	}



}


