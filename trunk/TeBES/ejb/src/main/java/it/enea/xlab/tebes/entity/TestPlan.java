package it.enea.xlab.tebes.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="testplan")
public class TestPlan implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;	
	//private String description;	
	
	@OneToMany(mappedBy="testPlan", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TestPlanDescription> testPlanDescriptions;	
	
	
	private String creationDatetime;
	private String lastUpdateDatetime;
	
	// "state" can take 2 values: "draft" or "final"
	private String state;
	
	private String location;
	private String publication;
	

	/**
	 * USER proprietario del TestPlan
	 * Molti TestPlan hanno lo stesso User => ManyToOne 
	 */
	@ManyToOne(cascade=CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	// fetch = FetchType.LAZY questo fa in modo che lo User non venga caricato assieme al TestPlan
	
	/**
	 * WORKFLOW del TestPlan
	 * Ogni TestPlan ha un workflow di actions => OneToOne
	 */
	@OneToOne(cascade=CascadeType.ALL)
	ActionWorkflow workflow;

	/**
	 * TestPlan completo in formato XML
	 */
	@OneToOne(cascade=CascadeType.ALL)
	TestPlanXML testplanxml;

	@OneToMany(mappedBy="testPlan")
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<Session> sessions;
	
	
	public TestPlan() {

	}
	
	public TestPlan(String name, String creationDatetime, String lastUpdateDatetime, String state, String location, String publication, ActionWorkflow workflow, TestPlanXML testPlanXML) {

		this.setName(name);	
		//this.setDescription(description);	
		this.setCreationDatetime(creationDatetime);
		this.setLastUpdateDatetime(lastUpdateDatetime);
		this.setState(state);
		this.setLocation(location);	
		this.setPublication(publication);
		this.setWorkflow(workflow);
		this.setTestplanxml(testPlanXML);
	
		
		
	}
	
	
	
	/**
	 * GETTERS and SETTERS
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

	/*public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}*/

	public String getCreationDatetime() {
		return creationDatetime;
	}

	public void setCreationDatetime(String datetime) {
		this.creationDatetime = datetime;
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

	public ActionWorkflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(ActionWorkflow workflow) {
		this.workflow = workflow;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TestPlanXML getTestplanxml() {
		return testplanxml;
	}

	public void setTestplanxml(TestPlanXML testplanxml) {
		this.testplanxml = testplanxml;
	}

	public String getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateDatetime(String lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public String getPublication() {
		return publication;
	}

	public void setPublication(String publication) {
		this.publication = publication;
	}

	public List<TestPlanDescription> getTestPlanDescriptions() {
		return testPlanDescriptions;
	}

	public void setTestPlanDescriptions(List<TestPlanDescription> testPlanDescriptions) {
		this.testPlanDescriptions = testPlanDescriptions;
	}



	
}

