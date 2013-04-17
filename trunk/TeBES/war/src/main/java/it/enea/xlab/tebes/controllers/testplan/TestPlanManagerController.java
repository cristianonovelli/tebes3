package it.enea.xlab.tebes.controllers.testplan;

import it.enea.xlab.tebes.action.ActionManagerRemote;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.testplan.TestPlanManagerRemote;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.List;
import java.util.Vector;

import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class TestPlanManagerController extends WebController {

	public static final String CONTROLLER_NAME = "TestPlanManagerController";
	
	private TestPlanManagerRemote testPlanManagerService;
	private ActionManagerRemote actionManagerService;

	
	// CONTROLLER Constructor
	public TestPlanManagerController() {
		
	}
		
	
	public void initContext() throws NotBoundException, NamingException {
		
		testPlanManagerService = JNDIServices.getTestPlanManagerService(); 
		
		actionManagerService = JNDIServices.getActionManagerService();
	}


	
	public TestPlan getTestPlanFromXML(String testPlanAbsPathName) {
		
		return testPlanManagerService.getTestPlanFromXML(testPlanAbsPathName);
	}

	public Long createTestPlan(TestPlan testPlan, Long userId) {
		
		return testPlanManagerService.createTestPlan(testPlan, userId);
	}

	public Boolean deleteTestPlan(Long testPlanId) {
		
		return testPlanManagerService.deleteTestPlan(testPlanId);
	}
	
	public List<TestPlan> readTestPlanByUserIdAndDatetime(String datetime, Long userId) {
		
		return testPlanManagerService.readTestPlanByDatetimeAndUserId(datetime, userId);
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

	public Long createAction(Action action, Long workflowId) {
		
		return actionManagerService.createAction(action, workflowId);
	}

	/*public Action readAction(Long actionId) {
		
		return testPlanManagerService.readAction(actionId);
	}*/

/*	public void addActionToWorkflow(Long actionId, Long workflowId) {
		
		testPlanManagerService.addActionToWorkflow(actionId, workflowId);		
	}*/

	public Boolean updateWorkflow(ActionWorkflow workflow) {
		
		return testPlanManagerService.updateWorkflow(workflow);
	}

	public void addWorkflowToTestPlan(Long workflowId, Long testPlanId) {
		
		testPlanManagerService.addWorkflowToTestPlan(workflowId, testPlanId);
	}


	public Vector<String> getSystemXMLTestPlanList() {
		
		return testPlanManagerService.getSystemXMLTestPlanList();
	}



	public List<TestPlan> readUserTestPlanList(User user) {
		
		return testPlanManagerService.readUserTestPlanList(user);
	}



	public Long addTestPlanToUser(Long testPlanId, Long userId) {
		
		return testPlanManagerService.addTestPlanToUser(testPlanId, userId);
	}



	public List<TestPlan> readSystemTestPlanList() {

		return testPlanManagerService.readSystemTestPlanList();
	}



	public Long createWorkflow(ActionWorkflow wf) {
		
		return actionManagerService.createWorkflow(wf);
	}



	public void addActionToWorkflow(Long actionId, Long workflowId) {
		
		actionManagerService.addActionToWorkflow(actionId, workflowId);
		return;
	}



	/*public Long readWorkflowByTestPlan(TestPlan tp) {
		
		return actionManagerService.readWorkflowByTestPlan(tp);
	}*/



	public Boolean deleteAction(Long id) {
		
		return actionManagerService.deleteAction(id);
	}



	public Boolean deleteWorkflow(Long id) {
		return actionManagerService.deleteWorkflow(id);
	}




	public Action readAction(Long actionId) {
		// TODO Auto-generated method stub
		return actionManagerService.readAction(actionId);
	}



	
	
	
}
