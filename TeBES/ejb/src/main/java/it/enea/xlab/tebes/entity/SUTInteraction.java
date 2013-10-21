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

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String type;

	private String endpoint;
	

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
	
	
}


