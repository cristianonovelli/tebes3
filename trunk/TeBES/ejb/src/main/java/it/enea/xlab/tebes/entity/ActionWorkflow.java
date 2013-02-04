package it.enea.xlab.tebes.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class ActionWorkflow implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	private String commnet;
	
	@OneToOne
	TestPlan testPlan;
	
	@OneToMany(mappedBy="workflow", 
			cascade = {CascadeType.ALL,CascadeType.MERGE },fetch = FetchType.EAGER)
	private List<Action> actions;


	public ActionWorkflow() {

	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public List<Action> getActions() {
		return actions;
	}


	public void setActions(List<Action> actions) {
		this.actions = actions;
	}


	public String getCommnet() {
		return commnet;
	}


	public void setCommnet(String commnet) {
		this.commnet = commnet;
	}


	public void addToTestPlan(TestPlan tp) {
		
		this.testPlan = tp;
	}


	public TestPlan getTestPlan() {
		return testPlan;
	}


	public void setTestPlan(TestPlan testPlan) {
		this.testPlan = testPlan;
	}


	
}




