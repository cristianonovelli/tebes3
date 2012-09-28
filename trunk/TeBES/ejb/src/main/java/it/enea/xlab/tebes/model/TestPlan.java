package it.enea.xlab.tebes.model;

import java.util.Vector;

public class TestPlan {

	private String testPlanId;
	private String userId;
	private String userSUT;
	private String datetime;
	private String state;
	private String location;
	
	// private Header header;
	private Vector<Action> workflow;


	// Constructor
	public TestPlan(String testPlanId, String userId) {

		this.testPlanId = testPlanId;
		this.userId = userId;
	}


	public String getUserSUT() {
		return userSUT;
	}

	public void setUserSUT(String userSUT) {
		this.userSUT = userSUT;
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

	public Vector<Action> getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Vector<Action> actionList) {
		this.workflow = actionList;
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


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}
	
	
}

