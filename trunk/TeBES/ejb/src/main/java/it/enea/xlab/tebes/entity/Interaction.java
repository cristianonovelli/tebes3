package it.enea.xlab.tebes.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Interaction implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	//@Column(name="interaction_sut_id")
	//private Long sutId;

	@OneToOne
	private SUT sut;

	/* From XML File
	 * <!-- L'elemento complesso tebes:interaction specifica l'interazione con la piattaforma di test:
		- website: interazione tramite interfaccia del sito web, upload e download;
		- email: interazione tramite protocollo pop/smtp;
		- webservice: interazione tramite servizio web;
		- ebxmlcpa: interazione descritta tramite standard ebXML CPA.
		A seconda del tipo di interazione scelta l'elemento può contenere altri parametri,
		come per esempio tebes:endpoint
	-->*/
	private String type;
	

	

	public Interaction() {

	}

	public Interaction(String type) {

		this.setType(type);
	}


	public SUT getSut() {
		return sut;
	}



	public void setSut(SUT sut) {
		this.sut = sut;
	}





	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
/*	public Long getSutId() {
		return sutId;
	}



	public void setSutId(Long sutId) {
		this.sutId = sutId;
	}*/



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	public void addToSUT(SUT s) {
		
		this.sut = s;
	}


	
	
}


