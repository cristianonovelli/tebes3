package it.enea.xlab.tebes.testplan;



import it.enea.xlab.tebes.common.ContextUtils;
import it.enea.xlab.tebes.common.Paths;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.testaction.TestActionManagerImpl;

import java.util.List;

import javax.naming.InitialContext;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class TestPlanManagerImplITCase {

	// Interface Declaration
	TestPlanManagerRemote testPlanManagerBean;
	

	
	/**
	 * Before (Test1): Setup 
	 */
	@Before
	public void before() throws Exception {

		// Create EJB linked to interface UserManagerRemote
		InitialContext ctx = new InitialContext();
		//InitialContext ctx = ContextUtils.getInitialContext("http://winter.bologna.enea.it:8081");
		testPlanManagerBean = (TestPlanManagerRemote) ctx.lookup("TeBES-ear/TestPlanManagerImpl/remote");	
	}
	

	
	/**
	 * Test1: User Registration
	 */
	@Test
	public void t1_check() {
		
		Assert.assertNotNull(testPlanManagerBean);	
	}

	
	/**
	 * Test1: User Registration
	 */
	@Test
	public void t2_setupTestPlan() {
		
		Assert.assertEquals(Paths.TeBES_ARTIFACTS_LOCAL_HOME + "users/1/testplans/TP-1.xml", 
				Paths.TeBES_TESTPLAN_ABSFILENAME);
	}
	

	@Test
	public void t3_setupTestPlan() {	
		
		// Get TeBES Test Plan	
		TestPlan testPlan = testPlanManagerBean.getTestPlanFromXML(Paths.TeBES_TESTPLAN_ABSFILENAME);
		
		Assert.assertNotNull(testPlan);
		Assert.assertEquals("2012-06-13T18:43:00", testPlan.getDatetime());
		Assert.assertEquals("draft", testPlan.getState());
		
		// Create TODO da modificare per ritornare -1
		Long testPlanId = testPlanManagerBean.createTestPlan(testPlan);
		
		// se è stato creato, testPlanId è nuovo
		// altrimenti è -1. in ogni caso deve essere diverso maggiore o minore di zero
		Assert.assertNotNull(testPlanId);
		Assert.assertTrue(testPlanId < 0 || testPlanId > 0);		
	}


	@Test
	public void t4_importTestPlan() {	
		
		Long testPlanId = Paths.TeBES_TESTPLANID;		
		Assert.assertNotNull(testPlanId);
		
		// Read TestPlan
		TestPlan testPlan = testPlanManagerBean.readTestPlan(testPlanId);
		Assert.assertEquals(testPlanId, testPlan.getId());
		
		ActionWorkflow workflow = new ActionWorkflow();
		workflow.setCommnet("from create-drop, 1st round of update");
		Long workflowId = testPlanManagerBean.insertWorkflow(workflow, testPlan.getId());
		Assert.assertNotNull(workflowId);
		
		if (workflowId > 0) {
			workflow = testPlanManagerBean.readWorkflow(workflowId);
		} else
			workflow = testPlan.getWorkflow();
		
		Assert.assertNotNull(workflow);
		
		// Get Actions from XML source (anche se il testplan è già stato importato)
		List<Action> actionList = testPlanManagerBean.getActionsFromXML(testPlan.getId());
		Assert.assertTrue(actionList.size() > 0);	
		
		// Per ogni Action dovrei persisterla e attaccarla al workflow per poi persistere il tutto
		// così come con il SUT
		Long id_action;
		for (int k=0;k<actionList.size();k++) {
			
			id_action = testPlanManagerBean.createAction(actionList.get(k));
			if (id_action > 0) {
				
				Action action = testPlanManagerBean.readAction(id_action);
				Assert.assertNotNull(action);
		
				Assert.assertTrue(workflow.getId() > 0);
				
				testPlanManagerBean.addActionToWorkflow(action.getId(), workflow.getId());
			}
		}
		
		// dal secondo giro in update
		if (testPlan.getWorkflow() != null) {
		
			// Update workflow
			workflow.setCommnet("2 round of update or more");
			Boolean updating = testPlanManagerBean.updateWorkflow(workflow);
			Assert.assertTrue(updating);
		}
		
		// aggiunge il workflow al test plan se non esiste, altrimenti lo aggiorna
		testPlanManagerBean.addWorkflowToTestPlan(workflow.getId(), testPlan.getId());
		
		testPlan = testPlanManagerBean.readTestPlan(testPlan.getId());
		Assert.assertTrue(testPlan.getWorkflow().getActions().size() > 0);
	}
	
		
	@Test
	public void t5_readTestPlan() {	

		// Read TestPlan
		TestPlan testPlan = testPlanManagerBean.readTestPlan(Paths.TeBES_TESTPLANID);
		//findTestPlanByTestPlanId(Properties.TeBES_TESTPLANID);
		Assert.assertNotNull(testPlan);
		
		// questo UserId è stato inserito esplicitamente e non è un id di collegamento come dovrebbe essere
		Assert.assertNotNull(testPlan.getUserId());
		Assert.assertNotNull(testPlan.getDatetime());
		Assert.assertNotNull(testPlan.getState());
		Assert.assertNotNull(testPlan.getWorkflow().getActions());
	}
	
	
	@Test
	public void t6_execution() {	
	
		TestPlan testPlan = testPlanManagerBean.readTestPlan(Paths.TeBES_TESTPLANID);
		TestActionManagerImpl actionManager = new TestActionManagerImpl(); 
		
		Assert.assertNotNull(actionManager);
		
		// forse dovrebbe ritornare qualcos'altro? tipo l'id del report
		boolean actionWorkflowExecutionResult = actionManager.executeActionWorkflow(testPlan.getWorkflow());
		Assert.assertNotNull(actionWorkflowExecutionResult);
		
	}

	
	@Test
	public void t7_report() {	
		
	}
	
}






