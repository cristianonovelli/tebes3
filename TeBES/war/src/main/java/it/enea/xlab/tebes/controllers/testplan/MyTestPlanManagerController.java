package it.enea.xlab.tebes.controllers.testplan;

import it.enea.xlab.tebes.action.ActionManagerRemote;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.dao.NestedCriterion;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.testplan.TestPlanManagerRemote;
import it.enea.xlab.tebes.users.UserManagerRemote;
import it.enea.xlab.tebes.utils.FormMessages;

import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.naming.NamingException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class MyTestPlanManagerController extends WebController<TestPlan> {

	public static final String CONTROLLER_NAME = "MyTestPlanManagerController";
	
	private TestPlanManagerRemote testPlanManagerService;
	private ActionManagerRemote actionManagerService;
	private UserManagerRemote userManagerService;
	
	private String testPlanFormMessage;
	private boolean showTestPlanFormMessage = false;
	
	private List<TestPlan> userTestPlanList;

	private Long selectedTestPlanId;
	private Long testPlanToDelete;
	
	private boolean newItemFormMessageRendered = false;
	private String newItemFormMessage;	
	
	
	
	// CONTROLLER Constructor
	public MyTestPlanManagerController() throws NamingException {

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

	/*public Boolean deleteTestPlan() {
		
		return testPlanManagerService.deleteTestPlan(selectedTestPlanId);
	}*/

	public String deleteTestPlan() {
		
		try {
			
			testPlanManagerService.deleteTestPlan(this.getTestPlanToDelete());
		
		} catch (Exception e) {

			setNewItemFormMessage(FormMessages.getErrorDeleteTestPlan());						
			setNewItemFormMessageRendered(true);
		}
		updateDataModel();
		return "testplan_deleted";
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

/*	public List<Action> getActionsFromXML(Long id) throws FileNotFoundException, SAXException, ParserConfigurationException, IOException {
		
		return testPlanManagerService.getActionsFromXML(id);
	}*/

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
	public void updateDataModel() {
		super.updateDataModel();
	}
	
	@Override
	public void resetSearchParameters() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<Criterion> determineRestrictions() {
		List<Criterion> criterions = new ArrayList<Criterion>();
		NestedCriterion userCriterion = new NestedCriterion("user", Restrictions.eq("eMail", FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName()));
        criterions.add(userCriterion);
		return criterions;
	}

	@Override
	protected List<Order> determineOrder() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String importTestPlan() {

		User currentUser = userManagerService.readUsersByEmail(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName()).get(0);
		TestPlan selectedTestPlan = testPlanManagerService.readTestPlan(selectedTestPlanId);
		
		if(selectedTestPlan != null && currentUser != null) {
			this.cloneTestPlan(selectedTestPlan, currentUser.getId());
			this.updateDataModel();
			this.testPlanFormMessage = FormMessages.getTestPlanImportSuccess();					
			this.showTestPlanFormMessage = true;
			
		} else {
			this.testPlanFormMessage = FormMessages.getTestPlanImportFail();
			this.showTestPlanFormMessage = true;
		}
		return "";
	}
	
	public String showTestPlanManagerView() {
		this.showTestPlanFormMessage = false;
		this.testPlanFormMessage = "";
		return "open_test_plan_view";
	}
	
	public String openCreateSessionViewFromTestPlan() {
		this.showTestPlanFormMessage = false;
		this.testPlanFormMessage = "";
		return "create_session";
	}

	public Long getSelectedTestPlanId() {
		return selectedTestPlanId;
	}

	public void setSelectedTestPlanId(Long selectedTestPlanId) {
		this.selectedTestPlanId = selectedTestPlanId;
	}

	public String getTestPlanFormMessage() {
		return testPlanFormMessage;
	}

	public void setTestPlanFormMessage(String testPlanFormMessage) {
		this.testPlanFormMessage = testPlanFormMessage;
		if(testPlanFormMessage.equals(""))
			this.showTestPlanFormMessage = false;
	}

	public boolean getShowTestPlanFormMessage() {
		return showTestPlanFormMessage;
	}


	public List<TestPlan> getUserTestPlanList() {
		User currentUser = userManagerService.readUsersByEmail(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName()).get(0);
		return testPlanManagerService.readUserTestPlanList(currentUser);
	}


	public void setUserTestPlanList(List<TestPlan> userTestPlanList) {
		this.userTestPlanList = userTestPlanList;
	}


	public Long getTestPlanToDelete() {
		return testPlanToDelete;
	}

	public void setTestPlanToDelete(Long testPlanToDelete) {
		this.testPlanToDelete = testPlanToDelete;
	}

	public boolean isNewItemFormMessageRendered() {
		return newItemFormMessageRendered;
	}

	public void setNewItemFormMessageRendered(boolean newItemFormMessageRendered) {
		this.newItemFormMessageRendered = newItemFormMessageRendered;
	}
	
	public String getNewItemFormMessage() {
		return newItemFormMessage;
	}

	public void setNewItemFormMessage(String newItemFormMessage) {
		this.newItemFormMessage = newItemFormMessage;
	}
}
