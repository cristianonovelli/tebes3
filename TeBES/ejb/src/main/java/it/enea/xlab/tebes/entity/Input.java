package it.enea.xlab.tebes.entity;

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
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "userinput")
public class Input implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String REACTION_UPLOAD = "upload";
	public static final String REACTION_TEXT = "text";
	public static final String REACTION_MESSAGE = "message";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	// descrizione testuale
	private String description;

	@OneToMany(mappedBy="userInput", cascade=CascadeType.REMOVE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<InputDescription> inputDescriptions;		

	// tipi possibili: document, transport, process
	private String type;

	// tipi possibili: xml, ...
	private String lg;
	
	private String interaction;
	// Set to "true" when SUT's User checked
	private boolean isInteractionOK;
	
	
	private String fileIdRef;
	// Set to "true" when Input is uploaded
	private boolean isInputSolved;
	
	// reaction and message of GUI user interface
	// reaction can take values: "upload", "text", "message"
	private String guiReaction;
	private String guiMessage;

	@OneToMany(mappedBy="userInput", cascade=CascadeType.REMOVE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<GUIDescription> guiDescriptions;	
	
	@ManyToOne
	@JoinColumn(name="testaction_id")
	Action testAction;
	
	
	public Input() {
		
	}
	
	
	/**
	 * Constructor without id.
	 */
/*	public Input(String name, String description, String type,
			String interaction, String fileIdRef, boolean interactionOK) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.interaction = interaction;
		this.setFileIdRef(fileIdRef);
		this.setFileStored(false);
		this.setInteractionOK(interactionOK);
	}*/

	public Input(String name, String description, String type, String lg,
			String interaction, String fileIdRef, String guiReaction, String guiMessage, boolean interactionOK) {
		
		this.name = name;
		this.description = description;
		this.type = type;
		this.lg = lg;
		
		this.interaction = interaction;
		this.setFileIdRef(fileIdRef);
		this.setInputSolved(false);
		
		this.guiReaction = guiReaction;
		this.guiMessage = guiMessage;
		
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


	public boolean isInputSolved() {
		return isInputSolved;
	}


	public void setInputSolved(boolean solved) {
		this.isInputSolved = solved;
	}


	public String getLg() {
		return lg;
	}


	public void setLg(String lg) {
		this.lg = lg;
	}


	public String getGuiReaction() {
		return guiReaction;
	}


	public void setGuiReaction(String guiReaction) {
		this.guiReaction = guiReaction;
	}


	public String getGuiMessage() {
		return guiMessage;
	}


	public void setGuiMessage(String guiMessage) {
		this.guiMessage = guiMessage;
	}


	public List<InputDescription> getInputDescriptions() {
		return inputDescriptions;
	}


	public void setInputDescriptions(List<InputDescription> inputDescriptions) {
		this.inputDescriptions = inputDescriptions;
	}


	public List<GUIDescription> getGuiDescriptions() {
		return guiDescriptions;
	}


	public void setGuiDescriptions(List<GUIDescription> guiDescriptions) {
		this.guiDescriptions = guiDescriptions;
	}






}


