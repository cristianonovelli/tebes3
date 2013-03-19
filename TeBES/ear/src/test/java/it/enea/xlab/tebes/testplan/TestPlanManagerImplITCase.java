package it.enea.xlab.tebes.testplan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

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

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.xlab.file.XLabFileManager;
import org.xlab.utilities.XLabDates;
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
		// 7. in particolare, per ogni action, deve specificare il sut o inserirne uno nuovo
		// (per il momento facciamo che inserisce 1 sut per tutte le action)
		// 8. il testplan viene scritto in memoria modificando i campi dove occorre
		
		// N.B. l'id dello superuser nei file XML è zero ma quando viene creato, 
		// questo id cambia a quello che la bancadati gli da'
		// la cartella 0 rimane l'utente superuser per evitare errori
		
		// 1. IMPORT system TPs from XML
		
		// Initialize superuser
		String superUserEmail = PropertiesUtil.getUser1Email();
		String superUserPassword = PropertiesUtil.getUser1Password();
		User superUser = new User("Cristiano", "Novelli", superUserEmail, superUserPassword);
		Long superUserId = userAdminController.createUser(superUser, role4_superuser);
		superUser = userProfileController.login(superUserEmail, superUserPassword);
		superUserId = superUser.getId();
		Assert.assertTrue(superUserId.intValue() > 0);
		superUser = userAdminController.readUser(superUserId);
		Assert.assertNotNull(superUser);	
		
		// Import System Test Plan from XML
		// TODO è consigliato usare arraylist perchè più performanti e immediatamente "persistibili" da jpa
		Vector<String> systemTestPlanList = testPlanController.getSystemXMLTestPlanList();
		Assert.assertTrue(systemTestPlanList.size() == 2);
		TestPlan testPlan = null;
		Long testPlanId;
		String testPlanAbsPathName;
		String superUserTestPlanDir = PropertiesUtil.getSuperUserTestPlanDir();
		Boolean updating;
		
		for (int i=0; i<systemTestPlanList.size();i++) {

			testPlanAbsPathName = superUserTestPlanDir.concat(systemTestPlanList.elementAt(i));
			testPlan = testPlanController.getTestPlanFromXML(testPlanAbsPathName);
			Assert.assertNotNull(testPlan);
			
			// Create TestPlan Entity
			testPlanId = testPlanController.createTestPlan(testPlan, superUserId);

			Assert.assertTrue(testPlanId.intValue() > 0);

			// Test the XML adjustment (done into the create)
			testPlan = testPlanController.readTestPlan(testPlanId);
			TestPlanDOM tpDOM = new TestPlanDOM();
			tpDOM.setContent(testPlan.getXml());
			
			
/*			String userIdXML = tpDOM.getRootElement().getAttribute(Constants.USERID_XMLATTRIBUTE_LABEL);

			Assert.assertTrue(userIdXML.equals(superUserId.toString()));

			String idXML = tpDOM.getRootElement().getAttribute(Constants.ID_XMLATTRIBUTE_LABEL);
			Assert.assertTrue(idXML.equals(testPlan.getId().toString()));
			
			String datetimeXML = tpDOM.getRootElement().getAttribute(Constants.DATETIME_XMLATTRIBUTE_LABEL);
			Assert.assertTrue(datetimeXML.equals(testPlan.getDatetime()));	*/		
			
		}
		
		
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
		superUserTestPlanList = testPlanController.readSystemTestPlanList();
		Assert.assertTrue(systemTestPlanList.size() == 2);
		
		
		
		// 5. User CHOOSES a System Test Plan (the first)
		TestPlan selectedTestPlan = superUserTestPlanList.get(0);

		// TODO 6. Vengono modificati alcuni campi (anche dentro l'XML!!!)
		selectedTestPlan.setState("draft");

		
		// TODO 7. Viene impostato per ogni action il sut 
		
		// 8. SAVE TestPlan for the current user
		// testPlan.setUser(currentUser);
		
		// TODO inglobare nella createTestPlan il current user in modo da spostare lì la join tra testplan e user
		// e risolvere anche il problema del doppio user
		
		Long importedTestPlanId = testPlanController.createTestPlan(selectedTestPlan, currentUserId);	
		Assert.assertTrue(importedTestPlanId.intValue() > selectedTestPlan.getId().intValue());		
		
		TestPlan importedTestPlan = testPlanController.readTestPlan(importedTestPlanId);
		Assert.assertTrue(importedTestPlan.getId().intValue() > 0);

		TestPlanDOM tpDOM2 = new TestPlanDOM();
		tpDOM2.setContent(importedTestPlan.getXml());
		
		
		// Last Check
		currentUser = userProfileController.login(Constants.USER1_EMAIL, Constants.USER1_PASSWORD);
		userTestPlanList = testPlanController.readUserTestPlanList(currentUser);
		Assert.assertNotNull(userTestPlanList);
		Assert.assertTrue(userTestPlanList.size() == 1);
		
		superUserTestPlanList = testPlanController.readUserTestPlanList(superUser);
		Assert.assertNotNull(superUserTestPlanList);
		Assert.assertTrue(superUserTestPlanList.size() == 2);		
		
		
		
		
		
		// UPDATE TestPlan
		importedTestPlan.setState(Constants.STATE_FINAL);
		updating = testPlanController.updateTestPlan(importedTestPlan);
		Assert.assertTrue(updating);	

		importedTestPlan = testPlanController.readTestPlan(importedTestPlan.getId());
		Assert.assertTrue(importedTestPlan.getState().equals(Constants.STATE_FINAL));

		
		// DELETING TestPlan
		/*Boolean deleting = testPlanController.deleteTestPlan(importedTestPlan.getId());
		Assert.assertTrue(deleting);	
		
		userTestPlanList = testPlanController.readUserTestPlanList(currentUser);
		Assert.assertTrue(userTestPlanList.size() == 0);	
		

		Boolean b = userAdminController.deleteUser(currentUser.getId());
		Assert.assertTrue(b);*/
	}
		

	@AfterClass
	public static void after_testPlanManager() throws Exception {

		Boolean deleting;
		
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






