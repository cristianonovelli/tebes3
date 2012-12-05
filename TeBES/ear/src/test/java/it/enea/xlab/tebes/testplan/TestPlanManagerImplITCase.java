package it.enea.xlab.tebes.testplan;



import java.util.List;

import it.enea.xlab.tebes.common.Properties;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.testaction.TestActionManagerImpl;

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
		
		Assert.assertEquals(Properties.TeBES_ARTIFACTS_LOCAL_HOME + "users/1/testplans/TP-1.xml", 
				Properties.TeBES_TESTPLAN_ABSFILENAME);
	}
	

	@Test
	public void t3_setupTestPlan() {	
		
		// Get TeBES Test Plan	
		TestPlan testPlan = testPlanManagerBean.getTestPlanFromXML(Properties.TeBES_TESTPLAN_ABSFILENAME);
		
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
	
	
	// t4_readTestPlan
	// prendo il workflow e salvo le action
	


	@Test
	public void t4_importTestPlan() {	
		
		Long testPlanId = Properties.TeBES_TESTPLANID;		
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
		
		// Per ogni Action dovrei persisterla e attaccarla al workflow con addToWorkflow per poi persistere il tutto
		// così come con il SUT
		Long id_action;
		for (int k=0;k<actionList.size();k++) {
			
			id_action = testPlanManagerBean.createAction(actionList.get(k));
			if (id_action > 0) {
				
				Action action = testPlanManagerBean.readAction(id_action);
				Assert.assertNotNull(action);
		
				Assert.assertTrue(workflow.getId() > 0);
				
				testPlanManagerBean.addAction(workflow.getId(), action.getId());
			}
		}
		
		// dal secondo giro in update
		if (testPlan.getWorkflow() != null) {
		
			// Update workflow
			workflow.setCommnet("2 round of update or more");
			Boolean updating = testPlanManagerBean.updateWorkflow(workflow);
			Assert.assertTrue(updating);
		}
		
		// TODO questo forse può spostarsi sopra, quando inserisco il wf
		// link workflow to TestPlan
		// Essendo uno solo, se già è presente devo aggiornarlo
		testPlanManagerBean.addWorkflow(testPlan.getId(), workflow.getId());
		
		testPlan = testPlanManagerBean.readTestPlan(testPlan.getId());
		Assert.assertTrue(testPlan.getWorkflow().getActions().size() > 0);
	}
	
		
	@Test
	public void t5_readTestPlan() {	

		// Read TestPlan
		TestPlan testPlan = testPlanManagerBean.readTestPlan(Properties.TeBES_TESTPLANID);
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
	
		TestPlan testPlan = testPlanManagerBean.readTestPlan(Properties.TeBES_TESTPLANID);
		TestActionManagerImpl actionManager = new TestActionManagerImpl(); 
		
		Assert.assertNotNull(actionManager);
		
		// forse dovrebbe ritornare qualcos'altro
		boolean actionWorkflowExecutionResult = actionManager.executeActionWorkflow(testPlan.getWorkflow());
		Assert.assertNotNull(actionWorkflowExecutionResult);
		
		
		/*System.out.println();
		
		// 5. utente può interagire durante esecuzione (monitoraggio, interventi)
		System.out.println("TODO: Interazione Utente");
		System.out.println();
		if ( actionWorkflowExecutionResult )
			System.out.println(">>>> Test Plan Execution Successful! <<<<");
		else
			System.out.println(">>>> Test Plan Execution Failure! <<<<");
		System.out.println();
		
		System.out.println("-----------------------------------");
		System.out.println();
		
		// 6. utente preleva report finale
		System.out.println("-----------------------------------");
		System.out.println("--------------REPORT---------------");
		System.out.println("TODO: REPORT");
		System.out.println("-----------------------------------");
		System.out.println(); */
	}

	
	@Test
	public void t7_report() {	
		
	}
	
}






