package it.enea.xlab.tebes.testplan;

import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.testaction.TestActionManagerImpl;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.xlab.file.XLabFileManager;


/**
 * Sicuramente c'è una fase di importazione dei Test Plan da XML. XML SOLO PER TEST PLAN SYSTEMS?
 * Lo User 1 è uno super User che deve essere presente nel momento in cui succede l'import.
 * 
 * @before
 * 1. Creazione Ruoli
 * 2. Creazione superuser
 * 3. import testplans
 * 4. creazione tempuser
 * 
 * 
 * Test1
 * CREATE (che sia da form o da esistente ottengo sempre una struttura dati con cui faccio la CREATE!!!
 * 1. lista SUT System disponibili
 * 2. creazione da copia
 * 3 creazione da form?
 * 4. specificazione SUT compatibile per ogni action? (caso del documento, dove lo metto?)
 * 
 * Test2 - CRUD per TestPlanManager:
 * 1. LIST
 * 3. READ
 * 4. UPDATE
 * 5. DELETE generic user (the superuser non è cancellabile)
 * 6. DELETE come conseguenza
 * 
 * @author Cristiano
 *
 */
public class TestPlanManagerImplITCase {

	// Interface Declaration
	//TestPlanManagerRemote testPlanManagerBean;
	TestPlanManagerController testPlanController;

	
	@Before
	public void before() throws Exception {
		
		testPlanController = new TestPlanManagerController();
	}
	
	@Test
	public void t1_check() {
		
		Assert.assertNotNull(testPlanController);	
/*	}

	
	@Test
	public void t2_setupTestPlan() {*/
		
		try {
			Assert.assertTrue(XLabFileManager.isFileOrDirectoryPresent(PropertiesUtil.getTestPlan1AbsPathName()));
		} catch (Exception e) {
			
			e.printStackTrace();
		}	
/*	}
	

	@Test
	public void t3_setupTestPlan() {*/	
		
		// Get TeBES Test Plan	
		TestPlan testPlan = null;
		try {
			testPlan = testPlanController.getTestPlanFromXML(PropertiesUtil.getTestPlan1AbsPathName());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		Assert.assertNotNull(testPlan);
		
		
		Assert.assertEquals("2012-06-13T18:43:00", testPlan.getDatetime());
		Assert.assertEquals("draft", testPlan.getState());
		
		// Create TestPlan
		Long testPlanId = testPlanController.createTestPlan(testPlan);
		
		
		
		Assert.assertNotNull(testPlanId);
		
		Boolean updating = false;
		
		// Update TestPlan
		if (testPlanId < 0) {
			testPlan.setState("updated");
			List<TestPlan> testPlanList = testPlanController.readTestPlanByUserIdAndDatetime(testPlan.getUserId(), testPlan.getDatetime());
			
			
			if ( (testPlanList != null) && (testPlanList.size() > 0) ) {
				
				// setto al nuovo test plan l'id del vecchio
				TestPlan updatedTP = testPlanList.get(0);
				updatedTP.setUserId(testPlan.getUserId());
				updatedTP.setXml(testPlan.getXml());
				updatedTP.setDatetime(testPlan.getDatetime());
				updatedTP.setState(testPlan.getState());
				updatedTP.setLocation(testPlan.getLocation());

				updating = testPlanController.updateTestPlan(updatedTP);
				
				//testPlanId = updatedTP.getId();
			}
		}
		
		// se è stato creato, testPlanId è nuovo e > 0
		// se è stato aggiornato updating è true
		Assert.assertTrue((testPlanId > 0) || updating);		
/*	}


	@Test
	public void t4_importTestPlan() throws NumberFormatException, FileNotFoundException {	*/
		
		
		
		
		testPlanId = PropertiesUtil.getTestPlanIdOfUser1();


		
		Assert.assertNotNull(testPlanId);
		
		// Read TestPlan
		testPlan = testPlanController.readTestPlan(testPlanId);
		Assert.assertEquals(testPlanId, testPlan.getId());
		
		ActionWorkflow workflow = new ActionWorkflow();
		workflow.setCommnet("from create-drop, 1st round of update");
		Long workflowId = testPlanController.insertWorkflow(workflow, testPlan.getId());
		Assert.assertNotNull(workflowId);
		
		if (workflowId > 0) {
			workflow = testPlanController.readWorkflow(workflowId);
		} else
			workflow = testPlan.getWorkflow();
		
		Assert.assertNotNull(workflow);
		
		// Get Actions from XML source (anche se il testplan è già stato importato)
		List<Action> actionList;
		
		try {
			actionList = testPlanController.getActionsFromXML(testPlan.getId());
		} catch (Exception e) {
			
			e.printStackTrace();
			actionList = null;
		} 
		
		Assert.assertNotNull(actionList);
		Assert.assertTrue(actionList.size() > 0);	
		
		// Per ogni Action dovrei persisterla e attaccarla al workflow per poi persistere il tutto
		// così come con il SUT
		Long id_action;
		for (int k=0;k<actionList.size();k++) {
			
			id_action = testPlanController.createAction(actionList.get(k));
			if (id_action > 0) {
				
				Action action = testPlanController.readAction(id_action);
				Assert.assertNotNull(action);
		
				Assert.assertTrue(workflow.getId() > 0);
				
				testPlanController.addActionToWorkflow(action.getId(), workflow.getId());
			}
		}
		
		// dal secondo giro in update
		if (testPlan.getWorkflow() != null) {
		
			// Update workflow
			workflow.setCommnet("2 round of update or more");
			updating = testPlanController.updateWorkflow(workflow);
			Assert.assertTrue(updating);
		}
		
		// aggiunge il workflow al test plan se non esiste, altrimenti lo aggiorna
		testPlanController.addWorkflowToTestPlan(workflow.getId(), testPlan.getId());
		
		testPlan = testPlanController.readTestPlan(testPlan.getId());
		Assert.assertTrue(testPlan.getWorkflow().getActions().size() > 0);
/*	}
	
		
	@Test
	public void t5_readTestPlan() throws NumberFormatException, FileNotFoundException {	*/

		// Read TestPlan
		testPlan = testPlanController.readTestPlan(new Long(PropertiesUtil.getTestPlanIdOfUser1()));
		//findTestPlanByTestPlanId(Properties.TeBES_TESTPLANID);
		Assert.assertNotNull(testPlan);
		
		// questo UserId è stato inserito esplicitamente e non è un id di collegamento come dovrebbe essere
		Assert.assertNotNull(testPlan.getUserId());
		Assert.assertNotNull(testPlan.getDatetime());
		Assert.assertNotNull(testPlan.getState());
		Assert.assertNotNull(testPlan.getWorkflow().getActions());
	
		
		Action a = testPlan.getWorkflow().getActions().get(0);
		Assert.assertNotNull(a);
		
		Assert.assertTrue(a.isJumpTurnedON());
/*	}
	
	
	@Test
	public void t6_execution() throws NumberFormatException, FileNotFoundException {*/	
	
		testPlan = testPlanController.readTestPlan(new Long(PropertiesUtil.getTestPlanIdOfUser1()));
		TestActionManagerImpl actionManager = new TestActionManagerImpl(); 
		
		Assert.assertNotNull(actionManager);
		
		// forse dovrebbe ritornare qualcos'altro? tipo l'id del report
		boolean actionWorkflowExecutionResult = actionManager.executeActionWorkflow(testPlan.getWorkflow());
		Assert.assertNotNull(actionWorkflowExecutionResult);
		
	}


}






