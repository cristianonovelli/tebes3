package it.enea.xlab.tebes.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TestPlan implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//private String userId;
	//private String userSUT;
	
	private String xml;
	private String datetime;
	private String state;
	private String location;
		
	// private Header header;
	//private List<Action> workflow;


	public TestPlan() {

	}

	public TestPlan(String xml, String datetime, String state, String location) {

		this.xml = xml;
		this.datetime = datetime;
		this.state = state;
		this.location = location;
	}

	public TestPlan(String xml) {

		this.xml = xml;
	}

	
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}	
	
	
	
	
}

