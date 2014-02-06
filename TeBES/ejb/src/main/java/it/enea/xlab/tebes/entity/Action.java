package it.enea.xlab.tebes.entity;



import it.enea.xlab.tebes.common.Constants;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="testaction")
public class Action implements Serializable {

	public static final long serialVersionUID = 1L;
	
	private static final String NEW_STATE = "new";
	private static final String READY_STATE = "ready";
	private static final String DONE_STATE = "done";
	
	private String state;
		
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private boolean prerequisite;
	private int actionNumber;
	private String actionName;
	private String actionId;
	
	private String testLanguage;
	private String testType;
	private String testLocation;
	private String testValue;
	private boolean skipTurnedON;
	// private String description;

	@OneToMany(mappedBy="testAction", cascade=CascadeType.REMOVE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ActionDescription> actionDescriptions;		
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="workflow_id")
	ActionWorkflow workflow;
	
	// cascade=CascadeType.ALL, fetch = FetchType.EAGER
	@OneToMany(mappedBy="testAction", cascade=CascadeType.REMOVE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Input> inputs;
	
	
	public Action() {
		
	}
	
	public Action(boolean prerequisite, int number, String name, String actionId, String state, 
			String lg, String type, String location, String value, boolean skipTurnedON) {
		
		this.setPrerequisite(prerequisite);
		
		this.setActionNumber(number);
		this.setActionName(name);
		this.setActionId(actionId);
		
		this.setState(state);
		
		this.setTestLanguage(lg);
		this.setTestLocation(location);
		
		if (type.equals(Constants.TA) || type.equals(Constants.TS) || type.equals(Constants.TC))
			this.setTestType(type);
		else
			this.setTestType(null);
		
		this.setTestValue(value);	
		this.setSkipTurnedON(skipTurnedON);
		//this.setDescription(description);
		
		this.setActionDescriptions(new Vector<ActionDescription>());
		this.setInputs(new Vector<Input>());
	}

	
	public String getActionSummaryString() {

		String result = "\n--- Action Number: " + this.getActionNumber() +" ---\n";
		result = result.concat("STATE: " + this.getState() +"\n");
		result = result.concat("is a Prerequisite: " + this.isPrerequisite() + "\n");
		result = result.concat("Action Name: " + this.getActionName() +"\n");
		//result = result.concat("Action Description: " + this.getDescription() +"\n");
		
		result = result.concat("Test Language: " + this.getTestLanguage() +"\n");
		result = result.concat("Test Type: " + this.getTestType() +"\n");	
		result = result.concat("Test Value: " + this.getTestValue() +"\n");
		result = result.concat("Test Skip Prerequisites: " + this.isSkipTurnedON() +"\n");
		result = result.concat("Test Location: " + this.getTestLocation() +"\n");		
		result = result.concat("Inputs: " + this.getInputs().size() +"\n");
			
		result = result.concat("------------------------");
		
		return result;
	}


	/////////////////////////////////
	/// STATE Getters and Setters ///
	/////////////////////////////////
	public static String getNewState() {
		return NEW_STATE;
	}	

	public void setStateToNew() {
		this.setState(NEW_STATE);
	}	
	
	public boolean isStateNew() {

		if ( getState().equals(NEW_STATE) )
			return true;
		else
			return false;
	}	
	
	public static String getReadyState() {
		return READY_STATE;
	}

	public void setStateToReady() {
		this.setState(READY_STATE);
	}	
	
	public boolean isStateReady() {

		if ( getState().equals(READY_STATE) )
			return true;
		else
			return false;
	}
	
	public static String getDoneState() {
		return DONE_STATE;
	}

	public void setStateToDone() {
		this.setState(DONE_STATE);
	}	
	
	public boolean isStateDone() {

		if ( getState().equals(DONE_STATE) )
			return true;
		else
			return false;
	}	
	
	
	
	
	
	
	public void addToWorkflow(ActionWorkflow workflow) {
		
		this.workflow = workflow;
	}


	
	
	
	
	/////////////////////////////////
	/// STATE Getters and Setters ///
	/////////////////////////////////

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getActionNumber() {
		return actionNumber;
	}

	public void setActionNumber(int actionNumber) {
		this.actionNumber = actionNumber;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getTestLanguage() {
		return testLanguage;
	}

	public void setTestLanguage(String testLanguage) {
		this.testLanguage = testLanguage;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getTestLocation() {
		return testLocation;
	}

	public void setTestLocation(String testLocation) {
		this.testLocation = testLocation;
	}

	public String getTestValue() {
		return testValue;
	}

	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}

	public boolean isSkipTurnedON() {
		return skipTurnedON;
	}

	public void setSkipTurnedON(boolean skipTurnedON) {
		this.skipTurnedON = skipTurnedON;
	}


	public ActionWorkflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(ActionWorkflow workflow) {
		this.workflow = workflow;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + actionNumber;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		if (actionNumber != other.actionNumber)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	




	public List<Input> getInputs() {
		return inputs;
	}

	public void setInputs(List<Input> inputs) {
		this.inputs = inputs;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public boolean isPrerequisite() {
		return prerequisite;
	}

	public void setPrerequisite(boolean prerequisite) {
		this.prerequisite = prerequisite;
	}

	public List<ActionDescription> getActionDescriptions() {
		return actionDescriptions;
	}

	public void setActionDescriptions(List<ActionDescription> actionDescriptions) {
		this.actionDescriptions = actionDescriptions;
	}




	
}
