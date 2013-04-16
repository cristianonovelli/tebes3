package it.enea.xlab.tebes.session;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.Interaction;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.sut.SUTManagerController;
import it.enea.xlab.tebes.testplan.TestPlanManagerController;
import it.enea.xlab.tebes.users.UserAdminController;
import it.enea.xlab.tebes.users.UserProfileController;

import java.util.List;
import java.util.Vector;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SessionManagerImplITCase {

	// Logger
	private static Logger logger = Logger.getLogger(SessionManagerImplITCase.class);
	
	// Interface Declaration
	static SessionManagerController sessionController;
	
	static TestPlanManagerController testPlanController;
	static UserAdminController userAdminController;
	static UserProfileController userProfileController;
	static SUTManagerController sutController;
	
	static Role role1_standard, role2_advanced, role3_admin, role4_superuser;
	
	//static Long superUserId;
	
	@BeforeClass
	public static void before_testPlanManager() throws Exception {
		
		
		sessionController = new SessionManagerController();
		Assert.assertNotNull(sessionController);
		
		testPlanController = new TestPlanManagerController();
		Assert.assertNotNull(testPlanController);	
		
		// Get UserAdmin Service
		userAdminController = new UserAdminController();
		Assert.assertNotNull(userAdminController);

		// Get UserProfile service for the Test
		userProfileController = new UserProfileController();
		Assert.assertNotNull(userProfileController);		

		sutController = new SUTManagerController();
		Assert.assertNotNull(sutController);
		
		
		List<Long> roleIdList = userAdminController.getRoleIdList();
		Assert.assertTrue(roleIdList.size() == 0);
		
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
		
		// Create superuser
		String superUserEmail = PropertiesUtil.getUser1Email();
		String superUserPassword = PropertiesUtil.getUser1Password();
		User superUser = new User("Cristiano", "Novelli", superUserEmail, superUserPassword);
		Long superUserId = userAdminController.createUser(superUser, role4_superuser);
		
		// Create 2 temporary Users
		User currentUser = new User("Temp1", "User1", Constants.USER1_EMAIL, Constants.USER1_PASSWORD);	
		Long idTempUser1 = userProfileController.registration(currentUser, role1_standard);
		Assert.assertNotNull(currentUser);
		Assert.assertTrue(idTempUser1.intValue()>0);
		User otherUser = new User("Temp2", "User2", Constants.USER2_EMAIL1, Constants.USER2_EMAIL1);
		Long idTempUser2 = userProfileController.registration(otherUser, role1_standard);
		Assert.assertNotNull(currentUser);
		Assert.assertTrue(idTempUser2.intValue()>0);
	}
	
	
	@Test
	public void test1_manualCreation() {
		
		//String superUserEmail = PropertiesUtil.getUser1Email();
		//String superUserPassword = PropertiesUtil.getUser1Password();
		//User superUser = userProfileController.login(superUserEmail, superUserPassword);
		//superUserId = superUser.getId();
	
		User currentUser = userProfileController.login(Constants.USER1_EMAIL, Constants.USER1_PASSWORD);
		Long currentUserId = currentUser.getId();
		

		

		// CREATE ActionWorkflow
		ActionWorkflow wf = new ActionWorkflow(new Vector<Action>());
		wf.setComment("mycomment");		
		//Long workflowId = testPlanController.createWorkflow(wf);
		//wf = testPlanController.readWorkflow(workflowId);
		//Assert.assertTrue(workflowId.intValue()>0);	
		

		// CREATE Actions
		Action a = new Action(1, "nome", "taml", "tc", "www.ciao.it", "3<2", false, "descrizione");
		Action a2 = new Action(2, "nome2", "taml", "tc", "www.ciao.it", "3<2", false, "descrizione");	
		/*Long actionId = testPlanController.createAction(a, workflowId);
		Assert.assertTrue(actionId.intValue()>0);	
		Long actionId2 = testPlanController.createAction(a2, workflowId);
		Assert.assertTrue(actionId2.intValue()>0);	*/	
		wf.getActions().add(a);
		wf.getActions().add(a2);
		
		// CREATE TestPlan
		// TODO la persistenza di questo TestPlan con questo workflow, non gli piace
		// dovrei farne la persistenza senza e poi attaccarlo!
		TestPlan tp = new TestPlan("xml", "datetime", "state", "location", wf);
		Long tpid = testPlanController.createTestPlan(tp, currentUserId);
		Assert.assertTrue(tpid.intValue()>0);			
		
		// ADD Workflow to TestPlan
		//testPlanController.addWorkflowToTestPlan(workflowId, tpid);
		
		// ADD TestPlan to User
		Long adding = testPlanController.addTestPlanToUser(tpid, currentUserId);
		Assert.assertTrue(adding.intValue()>0);	
		
		
		
		currentUser = userProfileController.login(currentUser.geteMail(), currentUser.getPassword());
		
		// TODO se questo funziona non c'è bisogno del metodo per prelevare il testplan dato lo user
		tp = currentUser.getTestPlans().get(0);
		Assert.assertNotNull(tp);
		System.out.println("AAAAA:" + tp.getDatetime());
		
		
		//workflowId = testPlanController.readWorkflowByTestPlan(tp);
		//Assert.assertTrue(workflowId.intValue()>0);
		
		wf = tp.getWorkflow();
				//testPlanController.readWorkflow(workflowId);
		Assert.assertNotNull(wf);
		
		List<Action> actionList = wf.getActions();
		Assert.assertNotNull(actionList);
		Assert.assertTrue(actionList.size()>0);
		
		
		// Creazione di un SUT
		Interaction interaction = new Interaction(Constants.INTERACTION_WEBSITE);
		SUT sut = new SUT("sut1", Constants.SUT_TYPE1_DOCUMENT, Constants.UBL, Constants.UBLSCHEMA, interaction, "XML document1 uploaded by web interface");
		Long sutId = sutController.createSUT(sut, currentUser);
		Assert.assertNotNull(sutId);	
		
		
		// CREATE SESSION
		Long sessionId = sessionController.run(currentUserId, sutId, tp.getId());
		Assert.assertNotNull(sessionId);
		Assert.assertTrue(sessionId.intValue()>0);
		
		// TODO LA SESSIONE AL MOMENTO NON FA NULLA E INVECE DOVREBBE:
		// 1. ESEGUIRE IL TESTPLAN CARICANDOLO IN MEMORIA,
		// 2. INVOCARE I VALIDATORI NECESSARI A ESEGUIRE I TEST E
		// 3. CREARE IL RELATIVO REPORT
		
		Boolean deleting;
		

		TestPlan tpFulvio = testPlanController.readTestPlan(tp.getId());
		Assert.assertNotNull(tpFulvio);
		
		ActionWorkflow wfFulvio = tpFulvio.getWorkflow();
		Assert.assertNotNull(wfFulvio);
		
		Long actionIdFulvio = wfFulvio.getActions().get(0).getId();
		Action aFulvio = testPlanController.readAction(actionIdFulvio);
		Assert.assertNotNull(aFulvio);
		
		/*if ( wfFulvio.getActions().contains(aFulvio) ) {
			System.out.println("AAAAAAAA" + aFulvio.getId());
			wfFulvio.getActions().remove(aFulvio);
			Assert.assertTrue(testPlanController.updateWorkflow(wfFulvio));
		}*/
		
		aFulvio = testPlanController.readAction(actionIdFulvio);
		System.out.println("AAAAAAAAAAAAAA:" + aFulvio.getId());
		deleting = testPlanController.deleteAction(aFulvio.getId());
		Action aFulvio2 = testPlanController.readAction(actionIdFulvio);
		Assert.assertNull(aFulvio2);
		
		//System.out.println("FFFFFFFF: " + aFulvio.getId());
		//deleting = testPlanController.deleteAction(aFulvio.getId());
		//Assert.assertTrue(deleting);		
		
		
		// DELETE Workflow > DELETE relative Actions
		//deleting = testPlanController.deleteWorkflow(wf.getId());
		//Assert.assertTrue(deleting);

		// TODO PROBLEMA CON CANCELLAZIONE ACTION, PERCHE'?
		//a = testPlanController.readAction(actionId);
		//deleting = testPlanController.deleteAction(a.getId());
		//Assert.assertTrue(deleting);
		
		// TODO IL SEGUENTE TEST HA SUCCESSO MA IN REALTA' LE ACTION CI SONO, SOLO NON SONO LINKATE AL WF!
		
		// Get Action List (only superuser)
		//List<Long> actionIdList = userAdminController.getActionIdList();
		//Assert.assertTrue(actionIdList.size()==0);
		
		// TODO N.B. the metod deleteAction is useless, doesn't work...
	}
	
	
	
	//@Test
	public void test2_autoCreation() {
		
		String superUserEmail = PropertiesUtil.getUser1Email();
		String superUserPassword = PropertiesUtil.getUser1Password();
		User superUser = userProfileController.login(superUserEmail, superUserPassword);
		Long superUserId = superUser.getId();
		superUser = userAdminController.readUser(superUserId);
		Vector<String> systemTestPlanList = testPlanController.getSystemXMLTestPlanList();
		TestPlan testPlan = null;
		Long testPlanId;
		String testPlanAbsPathName;
		String superUserTestPlanDir = PropertiesUtil.getSuperUserTestPlanDir();
		Boolean updating;	
		
		
		
		
		Long adding;
		for (int i=0; i<systemTestPlanList.size();i++) {
			
			// GET TestPlan structure from XML
			testPlanAbsPathName = superUserTestPlanDir.concat(systemTestPlanList.elementAt(i));
			testPlan = testPlanController.getTestPlanFromXML(testPlanAbsPathName);
			Assert.assertNotNull(testPlan);
			
			Assert.assertNotNull(testPlan.getWorkflow());
			Assert.assertNotNull(testPlan.getWorkflow().getActions());
			Assert.assertNotNull(testPlan.getWorkflow().getActions().get(0));	
			
			// Persist TestPlan structure from XML
			testPlanId = testPlanController.createTestPlan(testPlan, superUserId);
			Assert.assertTrue(testPlanId.intValue()>0);	
			adding = testPlanController.addTestPlanToUser(testPlanId, superUserId);
			Assert.assertTrue(adding.intValue()>0);	
			
			// Check it
			testPlan = testPlanController.readTestPlan(testPlanId);
			Assert.assertNotNull(testPlan.getWorkflow());
			Assert.assertNotNull(testPlan.getWorkflow().getActions());
			Assert.assertNotNull(testPlan.getWorkflow().getActions().get(0));			
		}	
		
		
/*		List<TestPlan> superUserTestPlanList = testPlanController.readUserTestPlanList(superUser);
		
		User currentUser = userProfileController.login(Constants.USER1_EMAIL, Constants.USER1_PASSWORD);
		Long currentUserId = currentUser.getId();
		
		superUserTestPlanList = testPlanController.readSystemTestPlanList();
		TestPlan selectedTestPlan = superUserTestPlanList.get(0);
		Assert.assertNotNull(selectedTestPlan);
		
		Long wfId = testPlanController.readWorkflowByTestPlan(selectedTestPlan);
		Assert.assertTrue(wfId.intValue()>0);
		
		ActionWorkflow wf = testPlanController.readWorkflow(wfId);		
		Assert.assertNotNull(wf);
		
		List<Action> actionList = wf.getActions();
		Assert.assertNotNull(actionList);
		
		Assert.assertNotNull(actionList.get(0));
		Assert.assertTrue(actionList.get(0).getId().intValue()>0);*/
		
		
		
	
	}
		

	//@AfterClass
	public static void after_testPlanManager() throws Exception {

		Boolean deleting;

		// TODO N.B.
		// è necessario cancellare i workflow prima di cancellare Role > User > TestPlan
		// sarebbe più logico che le cancellazioni fossero a cascata (ovvero, cancello una di queste entity e vengono cancellate quelle successive)
		// Role > User > TestPlan > workflow > actions
		
		
		// Get Role List
		List<Long> roleIdList = userAdminController.getRoleIdList();
		Assert.assertTrue(roleIdList.size() == 4);
		
		Role tempRole;
		Long tempRoleId;
		
		// Cancello ogni ruolo
		for (int u=0;u<roleIdList.size();u++) {
			
			tempRoleId = (Long) roleIdList.get(u);
			Assert.assertTrue(tempRoleId.intValue() > 0);

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
		// Sono stati eliminati tutti gli utenti (a cascata)?
		List<Long> userIdList = userAdminController.getUserIdList();
		userIdList = userAdminController.getUserIdList();
		Assert.assertTrue(userIdList.size() == 0);
	}
	
}

