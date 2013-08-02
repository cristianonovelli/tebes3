package it.enea.xlab.tebes.entity;

import it.enea.xlab.tebes.common.SUTConstants;

import javax.persistence.Entity;

@Entity
public class InteractionEmail extends Interaction {

	private static final long serialVersionUID = 1L;

	// In the e-mail interaction the endpoint is the email address
	private String endpoint;
	
	public InteractionEmail() {
		super(SUTConstants.INTERACTION_EMAIL);
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

}