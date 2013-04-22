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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
public class ActionWorkflow implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String comment;
	
	//@OneToOne
	//TestPlan testPlan;
	
	@OneToMany(mappedBy="workflow", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	//@OneToMany(mappedBy="workflow", cascade=CascadeType.ALL)
	//@LazyCollection(LazyCollectionOption.FALSE)
	private List<Action> actions;

	// Indica la prossima azione da eseguire con un numero che va da 1 a actions.size()
	private int nextActionMark;	
	
	
	
	public ActionWorkflow() {

	}
	
	public ActionWorkflow(List<Action> actions) {

		this.setActions(actions);
		this.setNextActionMark(1);
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




	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getNextActionMark() {
		return nextActionMark;
	}

	public void setNextActionMark(int nextActionMark) {
		this.nextActionMark = nextActionMark;
	}

/*	public void addToTestPlan(TestPlan tp) {
		
		this.testPlan = tp;
	}


	public TestPlan getTestPlan() {
		return testPlan;
	}


	public void setTestPlan(TestPlan testPlan) {
		this.testPlan = testPlan;
	}*/


	
}




