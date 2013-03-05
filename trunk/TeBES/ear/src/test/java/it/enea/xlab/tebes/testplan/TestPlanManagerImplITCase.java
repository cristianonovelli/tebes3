package it.enea.xlab.tebes.testplan;

import java.util.List;
import java.util.Vector;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.sut.SUTManagerController;
import it.enea.xlab.tebes.users.UserAdminController;
import it.enea.xlab.tebes.users.UserProfileController;
import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.xlab.file.XLabFileManager;


/**
 * Sicuramente c'è una fase di importazione dei Test Plan da XML. XML SOLO PER TEST PLAN SYSTEMS?
 * Lo User 1 è uno super User che deve essere presente nel momento in cui succede l'import.
 * 
 * @before
 * 1. Creazione Ruoli
 *
 *  * Test1
 *   * 2. Creazione user
 * CREATE (che sia da form o da esistente ottengo sempre una struttura dati con cui faccio la CREATE!!!
 * 1. lista SUT System disponibili
 * 2. creazione da copia
 * 3 creazione da form?
 * 4. specificazione SUT compatibile per ogni action? 
 * (caso del documento, dove lo metto?)
 * 
 * Test2 - CRUD per TestPlanManager:
 * 1. LIST
 * 3. READ
 * 4. UPDATE
 * 5. DELETE generic user (the superuser non è cancellabile)
 * 6. DELETE come conseguenza
 * 
 * 
 * @author Cristiano
 *
 */
public class TestPlanManagerImplITCase {

	// Interface Declaration
	static TestPlanManagerController testPlanController;
	static UserAdminController userAdminController;
	static UserProfileController userProfileController;

	static Role role1_standard, role2_advanced, role3_admin, role4_superuser;
	
	@Before
	public void before_testPlanManager() throws Exception {
		
		testPlanController = new TestPlanManagerController();
		Assert.assertNotNull(testPlanController);	
		
		// Get UserAdmin Service
		userAdminController = new UserAdminController();
		Assert.assertNotNull(userAdminController);

		// Get UserProfile service for the Test
		userProfileController = new UserProfileController();
		Assert.assertNotNull(userProfileController);		

		List<Long> roleIdList = userAdminController.getRoleIdList();
		Assert.assertTrue(roleIdList.size() == 0);
		
		// TODO la creazione e cancellazione dei ruoli possiamo spostarlo nell'EJB come singolo metodo
		
		// Prepare 4 user Roles 
		role1_standard = new Role(Constants.STANDARD_ROLE_NAME, Constants.STANDARD_ROLE_DESCRIPTION, Constants.STANDARD_ROLE_LEVEL);
		role2_advanced = new Role(Constants.ADVANCED_ROLE_NAME, Constants.ADVANCED_ROLE_DESCRIPTION, Constants.ADVANCED_ROLE_LEVEL);
		role3_admin = new Role(Constants.ADMIN_ROLE_NAME, Constants.ADMIN_ROLE_DESCRIPTION, Constants.ADMIN_ROLE_LEVEL);
		role4_superuser = new Role(Constants.SUPERUSER_ROLE_NAME, Constants.SUPERUSER_ROLE_DESCRIPTION, Constants.SUPERUSER_ROLE_LEVEL);
		
		// Create dei Ruoli che devono già essere fissati come setup del sistema
		Long id_role1_standard = userAdminController.createRole(role1_standard);
		Long id_role2_advanced = userAdminController.createRole(role2_advanced);
		Long id_role3_admin = userAdminController.createRole(role3_admin);
		Long id_role4_superuser = userAdminController.createRole(role4_superuser);

		// Get Role List
		roleIdList = userAdminController.getRoleIdList();
		Assert.assertTrue(roleIdList.size() == 4);
		
		role1_standard = userAdminController.readRole(id_role1_standard);
		role2_advanced = userAdminController.readRole(id_role2_advanced);
		role3_admin = userAdminController.readRole(id_role3_admin);
		role4_superuser = userAdminController.readRole(id_role4_superuser);
	}
	
	
	@Test
	public void test1_createTestPlanManager() {
		
		// CREATE (from system Test Plan XML)
		// 1. un utente fa login e va nel testplanmanager
		// 2. può vedere suoi testplan (Test2)
		// 3. vede lista test plan sistema (Test1) per usarli
		// 4. sceglie un test plan predefinito, tramite ID?
		// 5. importa il nuovo testplan da template xml
		// 6. il testplan viene scritto in memoria modificando i campi dove occorre
		// 	(quali sono i campi da personalizzare? id, nome utente, che altro?)
				
		// Create a temporary User	
		User tempUser = new User("Temp", "User4TestPlan", "tempuser.fortestplan@enea.it", "xuser");
		Long idTempUser = userProfileController.registration(tempUser, role1_standard);
		
		// Login
		tempUser = userProfileController.login(tempUser.geteMail(), tempUser.getPassword());
		idTempUser = tempUser.getId();
		
		Assert.assertNotNull(tempUser);
		Assert.assertTrue(idTempUser > 0);
		
		
		// 1. READ SYSTEM TESTPLAN LIST
		Vector<String> systemTestPlanList = testPlanController.getSystemTestPlanList();
		Assert.assertTrue(systemTestPlanList.size() == 2);

		


		/*
		
		// Get TeBES Test Plan from XML
		TestPlan testPlan = null;
		try {
			testPlan = testPlanController.getTestPlanFromXML(testPlan1AbsPathName);
			Assert.assertNotNull(testPlan);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			testPlan = null;
			Assert.assertNotNull(testPlan);		
		}
		Assert.assertNotNull(testPlan);

		
		
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
	
*/
		
		
		// User DELETING
		Boolean b = userAdminController.deleteUser(tempUser.getId());
		Assert.assertTrue(b);
	}
		/*
	@Test
	public void t4_importTestPlan() throws NumberFormatException, FileNotFoundException {	
		
		
		
		
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
	}
	
		
	@Test
	public void t5_readTestPlan() throws NumberFormatException, FileNotFoundException {	

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
	}
	
	
	@Test
	public void t6_execution() throws NumberFormatException, FileNotFoundException {	
	
		testPlan = testPlanController.readTestPlan(new Long(PropertiesUtil.getTestPlanIdOfUser1()));
		TestActionManagerImpl actionManager = new TestActionManagerImpl(); 
		
		Assert.assertNotNull(actionManager);
		
		// forse dovrebbe ritornare qualcos'altro? tipo l'id del report
		boolean actionWorkflowExecutionResult = actionManager.executeActionWorkflow(testPlan.getWorkflow());
		Assert.assertNotNull(actionWorkflowExecutionResult);
		
	}*/

	@AfterClass
	public static void after_sutManager() throws Exception {

		Boolean deleting;
		
		// Get Role List
		List<Long> roleIdList = userAdminController.getRoleIdList();
		Assert.assertTrue(roleIdList.size() == 4);
		
		Role tempRole;
		Long tempRoleId;
		
		// Cancello ogni ruolo
		for (int u=0;u<roleIdList.size();u++) {
			
			tempRoleId = (Long) roleIdList.get(u);
			Assert.assertTrue(tempRoleId > 0);

			tempRole = userAdminController.readRole(tempRoleId);			
			Assert.assertNotNull(tempRole);
						
			// DELETE Role
			deleting = userAdminController.deleteRole(tempRole.getId());
			Assert.assertTrue(deleting);			
		}		

		
		// Get Role List
		roleIdList = userAdminController.getRoleIdList();
		Assert.assertTrue(roleIdList.size() == 0);
		
		// Last Check
		// Sono stati eliminati tutti gli utenti?
		List<Long> userIdList = userAdminController.getUserIdList();
		userIdList = userAdminController.getUserIdList();
		Assert.assertTrue(userIdList.size() == 0);
		
	}
}






