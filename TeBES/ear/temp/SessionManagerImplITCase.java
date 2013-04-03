package it.enea.xlab.tebes.session;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.Interaction;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.model.Report;
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
	
	static Long superUserId;
	
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
	public void test_executeTestPlan() {
		
		////
		// IL SEGUENTE BLOCCO ESEGUE IL BLOCCO DEL TEST CONTENUTO IN TestPlanManagerImplITCase
		// PER PREPARARE USER E TESTPLAN
		////
		String superUserEmail = PropertiesUtil.getUser1Email();
		String superUserPassword = PropertiesUtil.getUser1Password();
		User superUser = userProfileController.login(superUserEmail, superUserPassword);
		superUserId = superUser.getId();
		superUser = userAdminController.readUser(superUserId);
		Vector<String> systemTestPlanList = testPlanController.getSystemXMLTestPlanList();
		TestPlan testPlan = null;
		Long testPlanId;
		String testPlanAbsPathName;
		String superUserTestPlanDir = PropertiesUtil.getSuperUserTestPlanDir();
		Boolean updating;		
		for (int i=0; i<systemTestPlanList.size();i++) {
			testPlanAbsPathName = superUserTestPlanDir.concat(systemTestPlanList.elementAt(i));
			testPlan = testPlanController.getTestPlanFromXML(testPlanAbsPathName);
			Assert.assertNotNull(testPlan);
			Assert.assertNotNull(testPlan.getWorkflow());
			Assert.assertNotNull(testPlan.getWorkflow().getActions());
			Assert.assertNotNull(testPlan.getWorkflow().getActions().get(0));	
			testPlanId = testPlanController.createTestPlan(testPlan, superUserId);
		}	
		List<TestPlan> superUserTestPlanList = testPlanController.readUserTestPlanList(superUser);
		User currentUser = userProfileController.login(Constants.USER1_EMAIL, Constants.USER1_PASSWORD);
		Long currentUserId = currentUser.getId();
		superUserTestPlanList = testPlanController.readSystemTestPlanList();
		TestPlan selectedTestPlan = superUserTestPlanList.get(0);
		Assert.assertNotNull(selectedTestPlan);
		Assert.assertNotNull(selectedTestPlan.getWorkflow());
		Assert.assertNotNull(selectedTestPlan.getWorkflow().getActions());
		Assert.assertNotNull(selectedTestPlan.getWorkflow().getActions().get(0));
		selectedTestPlan.setState("draft");
		Long importedTestPlanId = testPlanController.createTestPlan(selectedTestPlan, currentUserId);		
		TestPlan importedTestPlan = testPlanController.readTestPlan(importedTestPlanId);
		Assert.assertNotNull(importedTestPlan);
		Assert.assertNotNull(importedTestPlan.getWorkflow());
		Assert.assertNotNull(importedTestPlan.getWorkflow().getActions());
		Assert.assertNotNull(importedTestPlan.getWorkflow().getActions().get(0));		
		
		
		
		////
		// FINE BLOCCO
		////
		
		// SUMMARY
		// COSA FA IL SESSION MANAGER? 
		// EFFETTUIAMO QUESTO PERCORSO DELLE FUNZIONALITA'

		// 1. UTENTE SCEGLIE TESTPLAN e SUT
		// 2. UTENTE AVVIA ESECUZIONE DEL PROPRIO TESTPLAN
		// 3. PER OGNI ACTION L'UTENTE HA UN FEEDBACK E (EVENTUALMENTE) UNA RICHIESTA DI INTERAZIONE
		// 4. DOPO LA PRIMA ACTION, L'UTENTE SALVA LA SESSIONE ED ESCE
		// 5. L'UTENTE RIENTRA, VEDE LISTA SESSIONI
		// 6. RIAVVIA QUELLA IN SOSPESO CHE TERMINA
		// 7. L'UTENTE VEDE LISTA SESSIONI
		// 8. AVVIA NUOVA SESSIONE STESSO TESTPLAN E LA SOSPENDE
		// 9. CANCELLA LA PRIMA (IN TEORIA DOVREBBERO RIMANERE TUTTE MA VOLENDO PUO' CANCELLARLE?)
		// 10. RIPRENDE LA SECONDA E LA TERMINA	
		
		// 1. UTENTE SCEGLIE TESTPLAN e SUT
		List<TestPlan> currentUserTestPlanList = testPlanController.readUserTestPlanList(currentUser);
		Assert.assertTrue(currentUserTestPlanList.size() == 1);

		// Per avviare una sessione sono neccessarie 3 ID:
		 
		// 1.1 userID
		currentUserId = currentUser.getId();
		Assert.assertTrue(currentUserId.intValue()>0);
		
		// 1.2 sutID
		// Creazione di un SUT
		Interaction interaction = new Interaction(Constants.INTERACTION_WEBSITE);
		SUT sut = new SUT("sut1", Constants.SUT_TYPE1_DOCUMENT, Constants.UBL, Constants.UBLSCHEMA, interaction, "XML document1 uploaded by web interface");
		Long sutId = sutController.createSUT(sut, currentUser);
		Assert.assertNotNull(sutId);		
		
		// 1.3 TestPlan
		selectedTestPlan = currentUserTestPlanList.get(0);
		
		Long selectedTestPlanId = selectedTestPlan.getId();
		Assert.assertTrue(selectedTestPlan.getId().intValue()>0);
		
		
		// 2. UTENTE AVVIA ESECUZIONE DEL PROPRIO TESTPLAN CREANDO UNA NUOVA SESSIONE
		
/*		Long sessionId = sessionController.run(currentUserId, sutId, selectedTestPlanId);
		Assert.assertNotNull(sessionId);
		Assert.assertTrue(sessionId.intValue()>0);*/
		
		// N.B. 
		// PER IL MOMENTO VIENE AVVIATO IL TESTPLAN E NON C'E' INTERAZIONE
		// SUCCESSIVAMENTE
		// DA QUESTO MOMENTO IN POI IL SISTEMA AVVIA IL TEST E IL CLIENT ATTENDE 
		// 2.1. RICHIESTE DI INTERAZIONE
		// OPPURE
		// 2.2. RISULTATO FINALE
		// IN CHE MODO? TRAMITE IL SESSION ID, ATTRAVERSO IL QUALE E' POSSIBILE SAPERE COSA STA SUCCEDENDO.
		// PER ORA SALTIAMO I SEGUENTI PUNTI
		// 3. PER OGNI ACTION L'UTENTE HA UN FEEDBACK E (EVENTUALMENTE) UNA RICHIESTA DI INTERAZIONE
		// 4. DOPO LA PRIMA ACTION, L'UTENTE SALVA LA SESSIONE ED ESCE
		// 5. L'UTENTE RIENTRA, VEDE LISTA SESSIONI
		// 6. RIAVVIA QUELLA IN SOSPESO CHE TERMINA		
		
		// E PASSIAMO A LEGGERE LA SESSIONE
/*		Session session = sessionController.readSession(sessionId);
		Assert.assertNotNull(session);
		Assert.assertEquals(currentUserId, session.getUserId());
		Assert.assertEquals(selectedTestPlanId, session.getTestPlanId());
		Assert.assertEquals(sutId, session.getSutId());*/
		
		// E IL REPORT GENERATO
		//Report currentReport = sessionController.getReport(sessionId);
		
		
		
		
		

		
		
		// SALTIAMO AL PUNTO 7
		// 7. L'UTENTE VEDE LISTA SESSIONI
		// 8. AVVIA NUOVA SESSIONE STESSO TESTPLAN E LA SOSPENDE
		// 9. CANCELLA LA PRIMA (IN TEORIA DOVREBBERO RIMANERE TUTTE MA VOLENDO PUO' CANCELLARLE)
		// 10. RIPRENDE LA SECONDA E LA TERMINA	
		
		// CREATE REPORT --> REPORT MANAGER
		// (DI FIANCO AD OGNI SESSIONE DOVREBBE ESSERE POSSIBILE VEDERE IL REPORT PARZIALE O FINALE)
		
		
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

