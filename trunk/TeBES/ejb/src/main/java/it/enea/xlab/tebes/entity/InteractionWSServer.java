package it.enea.xlab.tebes.entity;

import it.enea.xlab.tebes.common.SUTConstants;

import javax.persistence.Entity;

@Entity
public class InteractionWSServer extends Interaction {

	private static final long serialVersionUID = 1L;

	// In the WebService interaction the endpoint is the URL address of WSDL
	private String endpoint;
	
	public InteractionWSServer() {
		super(SUTConstants.INTERACTION_WS_SERVER);
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

}
