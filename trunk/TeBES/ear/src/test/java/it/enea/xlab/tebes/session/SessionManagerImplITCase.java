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
		
		// Create dei Ruoli che devono gi� essere fissati come setup del sistema
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
	public void test_manualCreation() {
		
		//String superUserEmail = PropertiesUtil.getUser1Email();
		//String superUserPassword = PropertiesUtil.getUser1Password();
		//User superUser = userProfileController.login(superUserEmail, superUserPassword);
		//superUserId = superUser.getId();
	
		User currentUser = userProfileController.login(Constants.USER1_EMAIL, Constants.USER1_PASSWORD);
		Long currentUserId = currentUser.getId();
		
		TestPlan tp = new TestPlan("xml", "datetime", "state", "location");
		Long tpid = testPlanController.createTestPlan(tp, currentUserId);
		Assert.assertTrue(tpid.intValue()>0);	
		
		Action a = new Action(1, "nome", "taml", "tc", "www.ciao.it", "3<2", false, "descrizione");
		Action a2 = new Action(2, "nome2", "taml", "tc", "www.ciao.it", "3<2", false, "descrizione");
		
		Long actionId = testPlanController.createAction(a);
		Assert.assertTrue(actionId.intValue()>0);	
		Long actionId2 = testPlanController.createAction(a2);
		Assert.assertTrue(actionId2.intValue()>0);			

		
		//Vector<Action> actionList = new Vector<Action>();
		//actionList.add(a);
		//actionList.add(a2);
		ActionWorkflow wf = new ActionWorkflow();
		// ADD A TO WF
		
		
		// queste dovrebbero essere azioni "interne"
		Long workflowId = testPlanController.createWorkflow(wf);
		Assert.assertTrue(workflowId.intValue()>0);	
		
		testPlanController.addActionToWorkflow(actionId, workflowId);
		testPlanController.addActionToWorkflow(actionId2, workflowId);
		
		testPlanController.addWorkflowToTestPlan(workflowId, tpid);
		
		
		Long adding = testPlanController.addTestPlanToUser(tpid, currentUserId);
		Assert.assertTrue(adding.intValue()>0);	
		
		
		currentUser = userProfileController.login(currentUser.geteMail(), currentUser.getPassword());
		tp = currentUser.getTestPlans().get(0);
		Assert.assertNotNull(tp);
		
		workflowId = testPlanController.readWorkflowByTestPlan(tp);
		Assert.assertTrue(workflowId.intValue()>0);
		
		wf = testPlanController.readWorkflow(workflowId);
		Assert.assertNotNull(wf);
		
		List<Action> actionList = wf.getActions();
		Assert.assertNotNull(actionList);
		
		Assert.assertNotNull(actionList.get(1));
		Assert.assertTrue(actionList.get(1).getId().intValue()>0);
		Assert.assertTrue(actionList.get(1).getActionName().equals("nome2"));
		
		
		// Creazione di un SUT
		Interaction interaction = new Interaction(Constants.INTERACTION_WEBSITE);
		SUT sut = new SUT("sut1", Constants.SUT_TYPE1_DOCUMENT, Constants.UBL, Constants.UBLSCHEMA, interaction, "XML document1 uploaded by web interface");
		Long sutId = sutController.createSUT(sut, currentUser);
		Assert.assertNotNull(sutId);	
		
		
		// CREATE SESSION
		Long sessionId = sessionController.run(currentUserId, sutId, tp.getId());
		Assert.assertNotNull(sessionId);
		Assert.assertTrue(sessionId.intValue()>0);
		
		
		// DELETE Workflow > DELETE relative Actions
		Boolean deleting = testPlanController.deleteWorkflow(wf.getId());
		Assert.assertTrue(deleting);
		
		// Get Action List (only superuser)
		List<Long> actionIdList = userAdminController.getActionIdList();
		Assert.assertTrue(actionIdList.size()==0);
		// TODO N.B. the metod deleteAction is useless
	}
	
	

	@AfterClass
	public static void after_testPlanManager() throws Exception {

		Boolean deleting;

		// TODO N.B.
		// � necessario cancellare i workflow prima di cancellare Role > User > TestPlan
		// sarebbe pi� logico che le cancellazioni fossero a cascata (ovvero, cancello una di queste entity e vengono cancellate quelle successive)
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

