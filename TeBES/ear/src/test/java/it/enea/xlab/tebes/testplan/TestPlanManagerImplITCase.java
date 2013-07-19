package it.enea.xlab.tebes.testplan;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.controllers.testplan.TestPlanManagerController;
import it.enea.xlab.tebes.controllers.users.UserAdminController;
import it.enea.xlab.tebes.controllers.users.UserProfileController;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.utilities.WebControllersUtilities;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;


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

	// Logger
	private static Logger logger = Logger.getLogger(TestPlanManagerImplITCase.class);
	
	// Interface Declaration
	static TestPlanManagerController testPlanController;
	static UserAdminController userAdminController;
	static UserProfileController userProfileController;

	static Role role1_standard, role2_advanced, role3_admin, role4_superuser;
	
	static Long superUserId;
	
	@BeforeClass
	public static void before_testPlanManager() throws Exception {
		
		// Get Services
		testPlanController = (TestPlanManagerController) WebControllersUtilities.getManager(TestPlanManagerController.CONTROLLER_NAME);
		Assert.assertNotNull(testPlanController);	
		
		userAdminController = (UserAdminController) WebControllersUtilities.getManager(UserAdminController.CONTROLLER_NAME);
		Assert.assertNotNull(userAdminController);

		userProfileController = (UserProfileController) WebControllersUtilities.getManager(UserProfileController.CONTROLLER_NAME);
		Assert.assertNotNull(userProfileController);	
		

		List<Long> roleIdList = userAdminController.getRoleIdList();
		Assert.assertTrue( (roleIdList.size() == 0) || (roleIdList.size() == 4) );
		
		
		if (roleIdList.size() == 0) {
			
			// Prepare 4 user Roles 
			role1_standard = new Role(Constants.STANDARD_ROLE_NAME, Constants.STANDARD_ROLE_DESCRIPTION, Constants.STANDARD_ROLE_LEVEL);
			role2_advanced = new Role(Constants.ADVANCED_ROLE_NAME, Constants.ADVANCED_ROLE_DESCRIPTION, Constants.ADVANCED_ROLE_LEVEL);
			role3_admin = new Role(Constants.ADMIN_ROLE_NAME, Constants.ADMIN_ROLE_DESCRIPTION, Constants.ADMIN_ROLE_LEVEL);
			role4_superuser = new Role(Constants.SUPERUSER_ROLE_NAME, Constants.SUPERUSER_ROLE_DESCRIPTION, Constants.SUPERUSER_ROLE_LEVEL);
			
			// Create dei Ruoli che devono già essere fissati come setup del sistema
			userAdminController.createRole(role1_standard);
			userAdminController.createRole(role2_advanced);
			userAdminController.createRole(role3_admin);
			userAdminController.createRole(role4_superuser);

			// Get Role List
			roleIdList = userAdminController.getRoleIdList();
			Assert.assertTrue(roleIdList.size() == 4);
		}

		role1_standard = userAdminController.readRole(roleIdList.get(0));
		role2_advanced = userAdminController.readRole(roleIdList.get(1));
		role3_admin = userAdminController.readRole(roleIdList.get(2));
		role4_superuser = userAdminController.readRole(roleIdList.get(3));
		
		
		

		
		// Create superuser
		String superUserEmail = PropertiesUtil.getUser1Email();
		String superUserPassword = PropertiesUtil.getUser1Password();
		User superUser = new User("Cristiano", "Novelli", superUserEmail, superUserPassword);
		superUserId = userAdminController.createUser(superUser, role4_superuser);
		
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
	public void test_testPlanManager() throws SAXException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
		
		
		// Summary
		// 1. import dei testplan dello superuser da xml
		// 2. un utente fa login e va nel testplanmanager
		// 3. può vedere i propri testplan 
		// 4. può vedere i testplan di sistema (quelli dello superuser)
		// 5. sceglie un test plan predefinito di sistema, tramite ID, per "farlo suo"
		// 6. vengono modificati alcuni campi
		// (quali sono i campi da personalizzare? id, nome utente, datetime)
		// 7. in particolare, per ogni action, deve specificare l'EUT o inserirne uno nuovo
		// 8. il testplan viene scritto in memoria modificando i campi dove occorre
		// 9. Update TestPlan
		// 10. Delete TestPlan and user
		
		// N.B. l'id dello superuser nei file XML è zero ma quando viene creato, 
		// questo id cambia a quello che la bancadati gli da'
		// la cartella 0 rimane l'utente superuser per evitare errori
		
		
		// 1. IMPORT system TPs from XML
		

		String superUserEmail = PropertiesUtil.getUser1Email();
		String superUserPassword = PropertiesUtil.getUser1Password();
		User superUser = userProfileController.login(superUserEmail, superUserPassword);
		superUserId = superUser.getId();
		Assert.assertTrue(superUserId.intValue() > 0);
		superUser = userAdminController.readUser(superUserId);
		Assert.assertNotNull(superUser);	
		
		// Import System Test Plan from XML
		// TODO è consigliato usare arraylist perchè più performanti e immediatamente "persistibili" da jpa
		
		boolean importing = testPlanController.importSystemTestPlanFile(superUser);
		Assert.assertTrue(importing);
		
		// Check List of PERSISTED System Test Plans		
		List<TestPlan> superUserTestPlanList = testPlanController.readUserTestPlanList(superUser);
		Assert.assertNotNull(superUserTestPlanList);
		Assert.assertTrue(superUserTestPlanList.size() == 2);	
		// N.B. due, infatti, sono i TestPlan XML contenuti nella dir TeBES/war/src/main/webapp/users/0/testplans/
		
		
		
		// 2. USER LOGIN
		User currentUser = userProfileController.login(Constants.USER1_EMAIL, Constants.USER1_PASSWORD);
		Long currentUserId = currentUser.getId();
		Assert.assertNotNull(currentUser);
		Assert.assertTrue(currentUserId.intValue() > 0);
		
		// 3. READ User List of own Test Plans		
		List<TestPlan> userTestPlanList = testPlanController.readUserTestPlanList(currentUser);
		Assert.assertNotNull(userTestPlanList);
		Assert.assertTrue(userTestPlanList.size() == 0);
		// TODO ogni oggetto TestPlan contiene anche l'XML
		// questa potrebbe dunque diventare un'operazione poco performante
		// forse è meglio spostare il campo "xml" in tabella separata ma connessa
		
		
		// 4. READ System TESTPLAN LIST
		// il seguente metodo è equivalente a readUserTestPlanList(superUser)
		// MA non ha bisogno di avere il superuser al di fuori del servizio
		superUserTestPlanList = testPlanController.getSystemTestPlanList();
		Assert.assertTrue(superUserTestPlanList.size() == 2);
		
		
		
		// 5. User CHOOSES a System Test Plan (the first)
		TestPlan selectedTestPlan = superUserTestPlanList.get(0);

		// TODO 6. Vengono modificati alcuni campi (anche dentro l'XML!!!)
		selectedTestPlan.setState("draft");

		
		// 7. Viene impostato per ogni action l'EUT
		// N.B. al momento quello di sistema contiene già l'EUT document xml con interaction=website (ovvero upload)
		// 		a questo livello non c'è bisogno dunque di modificarlo MA in esecuzione DEVE essere letto e usato
		
		// 8. SAVE TestPlan for the current user
		Long importedTestPlanId = testPlanController.cloneTestPlan(selectedTestPlan, currentUserId);	
		Assert.assertTrue(importedTestPlanId.intValue() > 0);		
		//Long adding2 = testPlanController.addTestPlanToUser(importedTestPlanId, currentUserId);
		//Assert.assertTrue(adding2.intValue()>0);	
		// Read Check
		TestPlan importedTestPlan = testPlanController.readTestPlan(importedTestPlanId);
		Assert.assertTrue(importedTestPlan.getId().intValue() > 0);

		
		
		// Eventuali verifiche sull'XML
		//TestPlanDOM tpDOM2 = new TestPlanDOM();
		//tpDOM2.setContent(importedTestPlan.getXml());
		
		// Check Current User and its TestPlans
		currentUser = userProfileController.login(Constants.USER1_EMAIL, Constants.USER1_PASSWORD);
		userTestPlanList = testPlanController.readUserTestPlanList(currentUser);
		Assert.assertNotNull(userTestPlanList);
		Assert.assertTrue(userTestPlanList.size() == 1);
		
		// Check Super User TestPlans
		superUserTestPlanList = testPlanController.readUserTestPlanList(superUser);
		Assert.assertNotNull(superUserTestPlanList);
		Assert.assertTrue(superUserTestPlanList.size() == 2);		
		

		// 9. UPDATE TestPlan
		importedTestPlan.setState(Constants.STATE_FINAL);
		boolean updating = testPlanController.updateTestPlan(importedTestPlan);
		Assert.assertTrue(updating);	

		importedTestPlan = testPlanController.readTestPlan(importedTestPlan.getId());
		Assert.assertTrue(importedTestPlan.getState().equals(Constants.STATE_FINAL));


		// 10. DELETING TestPlan		
		Boolean deleting = testPlanController.deleteTestPlan(importedTestPlan.getId());
		Assert.assertTrue(deleting);	
		// Check
		userTestPlanList = testPlanController.readUserTestPlanList(currentUser);
		Assert.assertTrue(userTestPlanList.size() == 0);	
		// DELETING Current User
		Boolean b = userAdminController.deleteUser(currentUser.getId());
		Assert.assertTrue(b);
	}
	
	
	@AfterClass
	public static void after_testPlanManager() throws Exception {

		Boolean deleting;
		
		/*// Get Role List
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
		Assert.assertTrue(roleIdList.size() == 0);*/
		
		List<Long> userIdList = userAdminController.getUserIdList();
		
		User tempUser;
		Long tempUserId;
		for (int u=0;u<userIdList.size();u++) {
			
			tempUserId = (Long) userIdList.get(u);
			Assert.assertTrue(tempUserId.intValue() > 0);

			tempUser = userAdminController.readUser(tempUserId);			
			Assert.assertNotNull(tempUser);
						
			// DELETE User
			if (tempUser.getRole().getLevel() != role4_superuser.getLevel() ) {			
				deleting = userAdminController.deleteUser(tempUser.getId());
				Assert.assertTrue(deleting);			
			}
		}
		
		// Last Check
		// Sono stati eliminati tutti gli utenti tranne il superuser?

		userIdList = userAdminController.getUserIdList();
		Assert.assertTrue(userIdList.size() == 1);
	}
}






