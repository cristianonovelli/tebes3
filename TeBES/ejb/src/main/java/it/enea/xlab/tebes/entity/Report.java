package it.enea.xlab.tebes.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Report implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final String DRAFT_STATE = "draft";
	private static final String FINAL_STATE = "final";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	private String state;

	@Column(length=99999) 
	private String fullDescription;
	
	
	private boolean partialResultSuccessfully;
	private boolean finalResultSuccessfully;
	
	
	
	public Report() {
		
		this.setState(DRAFT_STATE);
		this.setFullDescription("");
		this.setFinalResultSuccessfully(true);
	}

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public static String getDraftState() {
		return DRAFT_STATE;
	}


	public static String getFinalState() {
		return FINAL_STATE;
	}


	public String getTestResult() {
		return fullDescription;
	}


	public String getFullDescription() {
		return fullDescription;
	}


	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	public void addToFullDescription(String furtherDescription) {
		this.fullDescription = fullDescription.concat(furtherDescription);
	}


	public boolean isPartialResultSuccessfully() {
		return partialResultSuccessfully;
	}


	public void setPartialResultSuccessfully(boolean partialResultSuccessfully) {
		this.partialResultSuccessfully = partialResultSuccessfully;
	}


	public boolean isFinalResultSuccessfully() {
		return finalResultSuccessfully;
	}


	public void setFinalResultSuccessfully(boolean finalResultSuccessfully) {
		this.finalResultSuccessfully = finalResultSuccessfully;
	}

	
}
