package it.enea.xlab.tebes.entity;



import it.enea.xlab.tebes.common.Constants;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Action implements Serializable {

	public static final long serialVersionUID = 1L;
	
	private static final String TODO_STATE = "todo";
	private static final String DONE_STATE = "done";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//private String actionId;
	private int actionNumber;
	private String actionName;
	private String state;
	private String testLanguage;
	private String testType;
	private String testLocation;
	private String testValue;
	private boolean jumpTurnedON;
	private String Description;
	private Long sutId;
	
	//@ManyToOne
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="workflow_id")
	ActionWorkflow workflow;
	
	
	public Action() {
		
	}
	
	public Action(int number, String name, String state, String lg, String type, String location, String value, boolean jumpTurnedON, String description) {
		
		//this.setActionId(actionId);
		this.setActionNumber(number);
		this.setActionName(name);
		
		if (state.equals(Action.getDoneState()))
			this.setState(Action.getDoneState());
		else
			this.setState(Action.getTodoState());
		
		this.setTestLanguage(lg);
		this.setTestLocation(location);
		
		if (type.equals(Constants.TA) || type.equals(Constants.TS) || type.equals(Constants.TC))
			this.setTestType(type);
		else
			this.setTestType(null);
		
		this.setTestValue(value);	
		this.setJumpTurnedON(jumpTurnedON);
		this.setDescription(description);
		
		
	}

	
	public String getActionSummaryString() {

		String result = "--- Action Number: " + this.getActionNumber() +" ---\n";
		result = result.concat("Action Name: " + this.getActionName() +"\n");
		result = result.concat("Action Description: " + this.getDescription() +"\n");
		
		result = result.concat("Test Language: " + this.getTestLanguage() +"\n");
		result = result.concat("Test Type: " + this.getTestType() +"\n");	
		result = result.concat("Test Value: " + this.getTestValue() +"\n");
		result = result.concat("Test Jump Prerequisites: " + this.isJumpTurnedON() +"\n");
		result = result.concat("Test Location: " + this.getTestLocation() +"\n");
		result = result.concat("Test Location: " + this.getTestLocation() +"\n");
		
		if (this.getSutId() == null) 			
			result = result.concat("User SUT: MISSING!\n");
		else
			result = result.concat("User SUT: " + this.getSutId() +"\n");
		
		result = result.concat("------------------------");
		
		return result;
	}

	
	public void addToWorkflow(ActionWorkflow workflow) {
		
		this.workflow = workflow;
	}

	public void removeFromWorkflow() {
		
		this.workflow = null;
	}
	
/*	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}*/

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

	public boolean isJumpTurnedON() {
		return jumpTurnedON;
	}

	public void setJumpTurnedON(boolean jumpTurnedON) {
		this.jumpTurnedON = jumpTurnedON;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
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

	public static String getDoneState() {
		return DONE_STATE;
	}

	public static String getTodoState() {
		return TODO_STATE;
	}

	public Long getSutId() {
		return sutId;
	}

	public void setSutId(Long sutId) {
		this.sutId = sutId;
	}
	
}
