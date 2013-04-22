package it.enea.xlab.tebes.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	@Column(length=9999) 
	private String xml;
	
	// TODO avendo collegato tramite JPA questa classe a user
	// si è venuto a creare il campo user_id e quindi questo non ha motivo di esistere
	// cancellarlo assicurandosi che l'importazione XML rimanga consistente
	//private Long userIdXML;
	
	private String datetime;
	private String state;
	private String location;
		

	/**
	 * USER del TestPlan
	 * Molti TestPlan hanno lo stesso User => ManyToOne 
	 */
	// , fetch = FetchType.LAZY questo fa in modo che non tira su lo User
	@ManyToOne(cascade=CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	
	/**
	 * WORKFLOW del TestPlan
	 * Ogni TestPlan ha un workflow di actions => OneToOne
	 * , fetch = FetchType.EAGER 
	 */
	@OneToOne(cascade=CascadeType.ALL)
	ActionWorkflow workflow;


	public TestPlan() {

	}
	
	public TestPlan(String xml, String datetime, String state, String location, ActionWorkflow workflow) {

		this.setXml(xml);
		this.setDatetime(datetime);
		this.setState(state);
		this.setLocation(location);		
		this.setWorkflow(workflow);
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

/*	public Long getUserIdXML() {
		return userIdXML;
	}

	public void setUserIdXML(Long userId) {
		this.userIdXML = userId;
	}*/

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
	

	
}

