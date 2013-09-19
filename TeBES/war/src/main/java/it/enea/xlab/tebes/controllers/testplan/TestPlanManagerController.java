package it.enea.xlab.tebes.controllers.testplan;

import it.enea.xlab.tebes.action.ActionManagerRemote;
import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.dao.NestedCriterion;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.testplan.TestPlanManagerRemote;
import it.enea.xlab.tebes.users.UserManagerRemote;

import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class TestPlanManagerController extends WebController<TestPlan> {

	public static final String CONTROLLER_NAME = "TestPlanManagerController";
	
	private TestPlanManagerRemote testPlanManagerService;
	private ActionManagerRemote actionManagerService;
	private UserManagerRemote userManagerService;

	
	// CONTROLLER Constructor
	public TestPlanManagerController() throws NamingException {


		testPlanManagerService = JNDIServices.getTestPlanManagerService(); 
		actionManagerService = JNDIServices.getActionManagerService();
		userManagerService = JNDIServices.getUserManagerService();
	}
		
	
	public void initContext() throws NotBoundException, NamingException {
		
		testPlanManagerService = JNDIServices.getTestPlanManagerService(); 
		
		actionManagerService = JNDIServices.getActionManagerService();
	}


	
	/*public TestPlan getTestPlanFromXML(String testPlanAbsPathName) {
		
		return testPlanManagerService.getTestPlanFromXML(testPlanAbsPathName);
	}*/

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

//	public List<Action> getActionsFromXML(Long id) throws FileNotFoundException, SAXException, ParserConfigurationException, IOException {
//		
//		return testPlanManagerService.getActionsFromXML(id);
//	}

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
		
		return actionManagerService.updateWorkflow(workflow);
	}

	public void addWorkflowToTestPlan(Long workflowId, Long testPlanId) {
		
		testPlanManagerService.addWorkflowToTestPlan(workflowId, testPlanId);
	}

/*	public Vector<String> getSystemXMLTestPlanList() {
		
		return testPlanManagerService.getSystemTestPlanFileList();
	}*/

	public List<TestPlan> getSystemTestPlanList() {
		
		return testPlanManagerService.getSystemTestPlanList();
	}

	public List<TestPlan> readUserTestPlanList(User user) {
		
		return testPlanManagerService.readUserTestPlanList(user);
	}
	
	public Long addTestPlanToUser(Long testPlanId, Long userId) {
		
		return testPlanManagerService.addTestPlanToUser(testPlanId, userId);
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

	public Long cloneTestPlan(TestPlan testPlan, Long userId) {
		
		return testPlanManagerService.cloneTestPlan(testPlan, userId);
	}



	public boolean importSystemTestPlanFile(User superUser) {
		
		return testPlanManagerService.importSystemTestPlanFiles(superUser);
	}

	@Override
	public void resetSearchParameters() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<Criterion> determineRestrictions() {
		List<Criterion> criterions = new ArrayList<Criterion>();
		NestedCriterion userCriterion = new NestedCriterion("user", new NestedCriterion("role", Restrictions.eq("name", Constants.SUPERUSER_ROLE_NAME)));
        criterions.add(userCriterion);
		return criterions;
	}

	@Override
	protected List<Order> determineOrder() {
		// TODO Auto-generated method stub
		return null;
	}
}
