package it.enea.xlab.tebes.model;

import it.enea.xlab.tebes.common.Constants;

public class Action {

	private int actionNumber;
	private String actionName;
	private String testLanguage;
	private String testType;
	private String testLocation;
	private String testValue;
	private boolean jumpTurnedON;
	private String Description;
	
	
	public Action(int number, String name, String lg, String type, String location, String value, boolean jumpTurnedON, String description) {
		
		this.setActionNumber(number);
		this.setActionName(name);
		
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
		
		result = result.concat("------------------------");
		
		
		return result;
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

	
}
