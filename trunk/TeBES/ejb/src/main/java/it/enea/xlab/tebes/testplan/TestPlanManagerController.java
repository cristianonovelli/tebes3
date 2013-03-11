package it.enea.xlab.tebes.testplan;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;

public class TestPlanManagerController {

	private TestPlanManagerRemote testPlanManagerService;
	
	// CONTROLLER Constructor
	public TestPlanManagerController() throws Exception {
		
		int guard = 0;
		
		// To avoid Remote Not Bound exception, we retry to lockup for 5 attempts
		// waiting 2 seconds between an attempt and another
		while (guard < 5) {
			try {
				
				// GET SERVICE
				testPlanManagerService = JNDIServices.getTestPlanManagerService();
				
				guard = 5;
			} catch (Exception e) {
				
				guard++;
				System.out.println("REMOTE NOT BOUND for TestPlanManagerController. Try " + guard + " of 5");				
				wait(2000);
			}
		}	
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

	public List<Action> getActionsFromXML(Long id) throws FileNotFoundException, SAXException, ParserConfigurationException, IOException {
		
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


	public Vector<String> getSystemTestPlanList() {
		
		return testPlanManagerService.getSystemTestPlanList();
	}



	public List<TestPlan> readUserTestPlanList(User user) {
		
		return testPlanManagerService.readUserTestPlanList(user);
	}
	
	
	
}
