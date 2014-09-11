package it.enea.xlab.tebes.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sutinteraction")
public class SUTInteraction implements Serializable {

	// SUT Interactions
	public static final String WEBSITE = "website";
	public static final String EMAIL = "email";
	public static final String WS_SERVER = "webservice-server";
	public static final String WS_CLIENT = "webservice-client";
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	// 
	private String type;

	private String endpoint;	
	private String operation;
	private String port;

	@OneToOne
	private SUT sut;

	
	public SUTInteraction() {

	}	
	
	public SUTInteraction(String type) {

		this.setType(type);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public SUT getSut() {
		return sut;
	}

	public void setSut(SUT sut) {
		this.sut = sut;
	}

	public void addToSUT(SUT s) {
		
		this.sut = s;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	
}


