package it.enea.xlab.tebes.entity;

import javax.persistence.Entity;

@Entity
public class InteractionEmail extends Interaction {

	private static final long serialVersionUID = 1L;

	private String endpoint;
	
	public InteractionEmail() {
		super(Interaction.INTERACTION_WEBSITE);
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

}
