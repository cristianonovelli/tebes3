package it.enea.xlab.tebes.entity;

import java.io.Serializable;

import javax.persistence.Column;
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
	
	@Column(length=9999) 
	private String xml;
	
	private String testPlanId;
	private String userId;
	private String datetime;
	private String state;
	private String location;
		
	// private Header header;
	//private List<Action> workflow;


	public TestPlan() {

	}

	public TestPlan(String testPlanId, String userId, String xml, String datetime, String state, String location) {

		this.testPlanId = testPlanId;
		this.userId = userId;
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

	public String getTestPlanId() {
		return testPlanId;
	}

	public void setTestPlanId(String testPlanId) {
		this.testPlanId = testPlanId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}	
	
	
	
	
}

