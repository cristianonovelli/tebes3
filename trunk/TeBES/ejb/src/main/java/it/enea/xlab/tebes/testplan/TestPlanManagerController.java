package it.enea.xlab.tebes.testplan;

import java.util.List;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.TestPlan;

public class TestPlanManagerController {

	private TestPlanManagerRemote testPlanManagerService;
	
	// CONTROLLER Constructor
	public TestPlanManagerController() throws Exception {

		testPlanManagerService = JNDIServices.getTestPlanManagerService();
	}

	
	public TestPlan getTestPlanFromXML(String testPlanAbsPathName) {
		
		return testPlanManagerService.getTestPlanFromXML(testPlanAbsPathName);
	}

	public Long createTestPlan(TestPlan testPlan) {
		
		return testPlanManagerService.createTestPlan(testPlan);
	}

	public List<TestPlan> readTestPlanByUserIdAndDatetime(Long userId, String datetime) {
		
		return testPlanManagerService.readTestPlanByUserIdAndDatetime(userId, datetime);
	}

	public Boolean updateTestPlan(TestPlan updatedTP) {
		
		return testPlanManagerService.updateTestPlan(updatedTP);
	}

	public TestPlan readTestPlan(Long testPlanId) {
		return testPlanManagerService.readTestPlan(testPlanId);
	}

	public Long insertWorkflow(ActionWorkflow workflow, Long id) {
		
		return testPlanManagerService.insertWorkflow(workflow, id);
	}

	public ActionWorkflow readWorkflow(Long workflowId) {
		
		return testPlanManagerService.readWorkflow(workflowId);
	}

	public List<Action> getActionsFromXML(Long id) {
		
		return testPlanManagerService.getActionsFromXML(id);
	}

	public Long createAction(Action action) {
		
		return testPlanManagerService.createAction(action);
	}

	public Action readAction(Long actionId) {
		
		return testPlanManagerService.readAction(actionId);
	}

	public void addActionToWorkflow(Long actionId, Long workflowId) {
		
		testPlanManagerService.addActionToWorkflow(actionId, workflowId);		
	}

	public Boolean updateWorkflow(ActionWorkflow workflow) {
		
		return testPlanManagerService.updateWorkflow(workflow);
	}

	public void addWorkflowToTestPlan(Long workflowId, Long testPlanId) {
		
		testPlanManagerService.addWorkflowToTestPlan(workflowId, testPlanId);
	}
	
	
	
}
