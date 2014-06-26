package it.enea.xlab.tebes.session;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.common.SUTConstants;
import it.enea.xlab.tebes.controllers.file.FileManagerController;
import it.enea.xlab.tebes.controllers.session.SessionManagerController;
import it.enea.xlab.tebes.controllers.sut.SUTManagerController;
import it.enea.xlab.tebes.controllers.testplan.TestPlanManagerController;
import it.enea.xlab.tebes.controllers.users.UserAdminController;
import it.enea.xlab.tebes.controllers.users.UserProfileController;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.FileStore;
import it.enea.xlab.tebes.entity.Input;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.SUTInteraction;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.utilities.WebControllersUtilities;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xlab.net.XURL;


public class SessionManagerImplITCase {

	// Logger
	private static Logger logger = Logger.getLogger(SessionManagerImplITCase.class);
	
	// Interface Declarations
	static SessionManagerController sessionController;
	static TestPlanManagerController testPlanController;
	static UserAdminController userAdminController;
	static UserProfileController userProfileController;
	static SUTManagerController sutController;
	static FileManagerController fileController;
	
	
	private static String TESTPLAN_91 = "TP-91";
	private static String TESTPLAN_1 = "TP-1";
	private static String TESTPLAN_2 = "TP-2";
	
	
	static Role role1_standard, role2_advanced, role3_admin, role4_superuser;
	
	static User superUser, currentUser1, currentUser2;
	
	static boolean beforeOK = false;
	 

	
	public static void create_controllers() throws Exception {
		
		sessionController = (SessionManagerController) WebControllersUtilities.getManager(SessionManagerController.CONTROLLER_NAME);
		Assert.assertNotNull(sessionController);
		
		testPlanController = (TestPlanManagerController) WebControllersUtilities.getManager(TestPlanManagerController.CONTROLLER_NAME);
		Assert.assertNotNull(testPlanController);	
		
		userAdminController = (UserAdminController) WebControllersUtilities.getManager(UserAdminController.CONTROLLER_NAME);
		Assert.assertNotNull(userAdminController);
	
		userProfileController = (UserProfileController) WebControllersUtilities.getManager(UserProfileController.CONTROLLER_NAME);
		Assert.assertNotNull(userProfileController);		
	
		sutController = (SUTManagerController) WebControllersUtilities.getManager(SUTManagerController.CONTROLLER_NAME);
		Assert.assertNotNull(sutController);
	
		fileController = (FileManagerController) WebControllersUtilities.getManager(FileManagerController.CONTROLLER_NAME);
		Assert.assertNotNull(fileController);	
		
	}
	
	@BeforeClass
	public static void before_testPlanManager() throws Exception {
		
		LogPrinter.before_print1();
		

		
		logger.info("************ TEST @BeforeClass ************");				
		logger.info("1) CONTROLLERS creating...");
		
		create_controllers();
		
		logger.info("OK! CONTROLLERS created!");

		
		
		// Prepare Roles 		
		logger.info("2) ROLES creating...");
		List<Long> roleIdList = userAdminController.getRoleIdList();
		Assert.assertTrue(roleIdList.size() == 0);		

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
		Assert.assertNotNull(role1_standard);
		Assert.assertNotNull(role2_advanced);
		Assert.assertNotNull(role3_admin);
		Assert.assertNotNull(role4_superuser);
		logger.info("OK! ROLES: " 
				+ role1_standard.getName() + ", " 
				+ role2_advanced.getName() + ", " 
				+ role3_admin.getName() + ", " 
				+ role4_superuser.getName() + " created!");

		
		// Create superuser
		logger.info("3) SUPERUSER creating...");
		String superUserName = PropertiesUtil.getSuperUserNameProperty();
		String superUserSurname = PropertiesUtil.getSuperUserSurnameProperty();
		String superUserEmail = PropertiesUtil.getSuperUserEmailProperty();
		String superUserPassword = PropertiesUtil.getSuperUserPasswordProperty();
		logger.info("Superuser properties from file tebes.proprties: " + superUserEmail + " " + superUserPassword);
		superUser = new User(superUserName, superUserSurname, superUserEmail, superUserPassword);
		Long superUserId = userAdminController.createUser(superUser, role4_superuser);
		superUser = userAdminController.readUser(superUserId);
		Assert.assertTrue(superUserId.intValue()>0);			
		logger.info("OK! SUPERUSER created for " + superUserName + " " + superUserSurname + " !");
		
		
		
		// Create superuser SUTs (SUTs supported by TeBES)
		logger.info("4) SUT creating...");
		SUTInteraction interactionWebSite = new SUTInteraction(SUTConstants.INTERACTION_WEBSITE);
		SUTInteraction interactionEmail = new SUTInteraction(SUTConstants.INTERACTION_EMAIL);
		SUTInteraction interactionWSClient = new SUTInteraction(SUTConstants.INTERACTION_WS_CLIENT);
		SUTInteraction interactionWSServer = new SUTInteraction(SUTConstants.INTERACTION_WS_SERVER);
			
		ArrayList<SUT> sutList = new ArrayList<SUT>();
		
		// SUT supportati per il tipo "document"
		//sutList.add( new SUT("SystemSUT1", SUTConstants.SUT_TYPE1_DOCUMENT, interactionWebSite, "System SUT 1: Document - WebSite") );
		sutList.add( new SUT("SystemSUT2", SUTConstants.SUT_TYPE1_DOCUMENT, interactionEmail, "System SUT 2: Document - email") );
		sutList.add( new SUT("SystemSUT3", SUTConstants.SUT_TYPE1_DOCUMENT, interactionWSClient, "System SUT 3: Document - Web Service Client") );
		sutList.add( new SUT("SystemSUT4", SUTConstants.SUT_TYPE1_DOCUMENT, interactionWSServer, "System SUT 4: Document - Web Service Server") );
		
		// SUT supportati per il tipo "transport"
		sutList.add( new SUT("SystemSUT5", SUTConstants.SUT_TYPE2_TRANSPORT, interactionEmail, "System SUT 5: Transport - email") );
		sutList.add( new SUT("SystemSUT6", SUTConstants.SUT_TYPE2_TRANSPORT, interactionWSClient, "System SUT 6: Transport - Web Service Client") );
		sutList.add( new SUT("SystemSUT7", SUTConstants.SUT_TYPE2_TRANSPORT, interactionWSServer, "System SUT 7: Transport - Web Service Server") );
		
		// SUT supportati per il tipo "process"
		sutList.add( new SUT("SystemSUT8", SUTConstants.SUT_TYPE3_PROCESS, interactionWebSite, "System SUT 8: Process - WebSite") );
		sutList.add( new SUT("SystemSUT9", SUTConstants.SUT_TYPE3_PROCESS, interactionEmail, "System SUT 9: Process - email") ) ;
		sutList.add( new SUT("SystemSUT10", SUTConstants.SUT_TYPE3_PROCESS, interactionWSClient, "System SUT 10: Process - Web Service Client") );
		sutList.add( new SUT("SystemSUT11", SUTConstants.SUT_TYPE3_PROCESS, interactionWSServer, "System SUT 11: Process - Web Service Server") );
						
		Long sutId;
		//String sutString = "";
		//SUT sutTemp;
		for (int i=0; i<sutList.size();i++) {
		
			sutId = sutController.createSUT(sutList.get(i), superUser);
			Assert.assertNotNull(sutId);	
			Assert.assertTrue(sutId.intValue()>0);	
			
			//sutTemp = sutController.readSUT(sutId);
			//sutString.concat(sutTemp.getName() + ", ");
		}
		logger.info("OK! SUT: " + sutList.size() + " created!");
		
		
		
		// Create two generic Users
		logger.info("5) Three STANDARD USERS creating...");
		User currentUser = new User(Constants.USER1_NAME, Constants.USER1_SURNAME, Constants.USER1_EMAIL, Constants.USER1_PASSWORD);	
		Long idTempUser1 = userProfileController.registration(currentUser, role1_standard);
		Assert.assertNotNull(currentUser);
		Assert.assertTrue(idTempUser1.intValue()>0);
		User otherUser = new User(Constants.USER2_NAME, Constants.USER2_SURNAME, Constants.USER2_EMAIL, Constants.USER2_PASSWORD);
		Long idTempUser2 = userProfileController.registration(otherUser, role1_standard);
		Assert.assertNotNull(otherUser);
		Assert.assertTrue(idTempUser2.intValue()>0);
		logger.info("OK! Two STANDARD USERS for " + currentUser.getName() + " and " + otherUser.getName() + " created!");
		User user3 = new User(Constants.USER3_NAME, Constants.USER3_SURNAME, Constants.USER3_EMAIL, Constants.USER3_PASSWORD);	
		Long idTempUser3 = userProfileController.registration(user3, role1_standard);
		Assert.assertNotNull(user3);
		Assert.assertTrue(idTempUser3.intValue()>0);		
		
		
		// Login SuperUser
		logger.info("6) SUPERUSER LOGIN");
		superUserEmail = PropertiesUtil.getSuperUserEmailProperty();
		superUserPassword = PropertiesUtil.getSuperUserPasswordProperty();
		superUser = userProfileController.login(superUserEmail, superUserPassword);
		superUserId = superUser.getId();
		superUser = userAdminController.readUser(superUserId);
		logger.info("OK! SUPERUSER SIGNED!");
		
		
		// Importazione dei Test Plan XML per lo SuperUser (gli utenti li importeranno poi da lui)
		// N.B. questa operazione viene fatta in fase di setup del sistema
		logger.info("7) IMPORTING system Test Plans");
		boolean importing = testPlanController.importSystemTestPlanFile(superUser);
		Assert.assertTrue(importing);
		
		TestPlan testPlan = testPlanController.getSystemTestPlanList().get(0);
		Assert.assertNotNull(testPlan.getWorkflow());
		Assert.assertNotNull(testPlan.getWorkflow().getActions());
		Assert.assertNotNull(testPlan.getWorkflow().getActions().get(0));
		logger.info("OK! IMPORTING system Test Plans");
		
		logger.info("8) VALIDATION service CHECKING...");
		if( userAdminController.checkValidation() )
			logger.info("OK! CHECKED OUT VALIDATION service!");
		else
			logger.info("NO. VALIDATION service is disabled or doesn't work");
		

		logger.info("9) URL existing CHECKING..."); 
		beforeOK = checkTestSuites();		
		if (beforeOK) 
			logger.info("OK! URLs checking SUCCESSFUL!");	
		else
			logger.info("NO. @BEFORE Test has identified inconsistent URL in the Test Suites. See checkTestSuites() method in SessionManagerImpl.");
		
		logger.info("");
		logger.info("*******************************************");
		logger.info("");
		

		
	}
	
	
	private static boolean checkTestSuites() {
		
		boolean result;
		
		// TODO verificare ogni URL di ogni Test Suite
		String url = "http://winter.bologna.enea.it/peppol_schema_rep/schematron/sdi/SDI-UBL-T10.sch";
		//String url = "https://dl.dropboxusercontent.com/u/7198633/TS-001_UBL.xml";
		try {
			result = XURL.isURLExistent(url);
		} catch (Exception e) {
			result = false;
			
		}
		
		return result;
	}

	
	
	@Test
	public void test_session() {

		if (beforeOK) {
		
			logger.info("**************** TEST @Test ****************");			
			logger.info("********** Part 1: Configuration ***********");	
			
			// USERS 
			logger.info("1) LOGIN USERS");
			currentUser1 = userProfileController.login(Constants.USER1_EMAIL, Constants.USER1_PASSWORD);
			currentUser2 = userProfileController.login(Constants.USER2_EMAIL, Constants.USER2_PASSWORD);
			Assert.assertTrue(currentUser1 != null);
			Assert.assertTrue(currentUser2 != null);
			
			Long currentUser91Id = currentUser1.getId();
			Long currentUser1Id = currentUser2.getId();
			Assert.assertTrue(currentUser1.getId().intValue() > 0);
			Assert.assertTrue(currentUser2.getId().intValue() > 0);
			logger.info("OK! Login of user " + currentUser1.getName() + " " + currentUser1.getSurname() + " with id: " + currentUser91Id);
			logger.info("OK! Login of user " + currentUser2.getName() + " " + currentUser2.getSurname() + " with id: " + currentUser1Id);
			
			
			// TESTPLAN
			// Lista dei TestPlan disponibili nel sistema	
			logger.info("2) GET system TEST PLANS");
			List<TestPlan> systemTestPlanList = testPlanController.getSystemTestPlanList();
			Assert.assertTrue(systemTestPlanList.size()>0);
			
			TestPlan testPlan = null;
			Long testPlan91Id, testPlan1Id;
			String tpString = "";
			
			Hashtable<String, TestPlan> tpTable = new Hashtable<String, TestPlan>();
			
			// Per ogni TestPlan del sistema... 
			// lo inserisco in una hashtable e lo verifico
			for (int i=0; i<systemTestPlanList.size();i++) {
	
				testPlan = systemTestPlanList.get(i);
				Assert.assertNotNull(testPlan);
				logger.info("TestPlan name: " + testPlan.getName() + " saved in the Hashtable."); 
							
				tpTable.put(testPlan.getName(), testPlan);
				
				tpString.concat(testPlan.getName() + " ");
				
				Assert.assertNotNull(testPlan.getWorkflow());
				Assert.assertNotNull(testPlan.getWorkflow().getActions());
				Assert.assertNotNull(testPlan.getWorkflow().getActions().get(0));	
				
				testPlan91Id = testPlan.getId();
				Assert.assertTrue(testPlan91Id.intValue()>0);	
				
				// Check it
				testPlan = testPlanController.readTestPlan(testPlan91Id);
				Assert.assertNotNull(testPlan.getWorkflow());
				
				
				List<Action> tempActionList = testPlan.getWorkflow().getActions(); 
				Assert.assertNotNull(tempActionList);
				Action tempAction;
				Input tempInput;
				for (int ii=0; ii<tempActionList.size();ii++) {
					tempAction = tempActionList.get(ii);
					Assert.assertNotNull(tempAction);
					logger.info("Action Id: " + tempAction.getActionId()); 
				
					List<Input> tempInputList = tempAction.getInputs(); 
					Assert.assertNotNull(tempInputList);
					for (int iii=0; iii<tempInputList.size();iii++) {
						tempInput = tempInputList.get(iii);
						Assert.assertNotNull(tempInput);
						logger.info("Input name: " + tempInput.getName()); 				
					}
				}
					
				
			}	
			logger.info("OK! GOT TEST PLANS.");
			
			
			
			// Selezione di un TestPlan generico per l'utente (currentUser)
			logger.info("3) SELECT and IMPORT TESTPLANS");
			TestPlan testPlan91 = tpTable.get(TESTPLAN_91);
			TestPlan testPlan1 = tpTable.get(TESTPLAN_1);
					//systemTestPlanList.get(SELECTED_TESTPLAN);
			Assert.assertNotNull(testPlan91);
			Assert.assertNotNull(testPlan1);
	
			// Copia e importazione del TestPlan scelto per l'utente (currentUser)
			testPlan91Id = testPlanController.cloneTestPlan(testPlan91, currentUser91Id);
			testPlan1Id = testPlanController.cloneTestPlan(testPlan1, currentUser1Id);
			Assert.assertTrue(testPlan91Id.intValue()>0);
			Assert.assertTrue(testPlan1Id.intValue()>0);
			
			// Il testPlan selezionato diventa quello clonato per lo User
			testPlan91 = testPlanController.readTestPlan(testPlan91Id);
			testPlan1 = testPlanController.readTestPlan(testPlan1Id);
			Assert.assertNotNull(testPlan91.getWorkflow());
			Assert.assertNotNull(testPlan91.getWorkflow().getActions());
			Assert.assertNotNull(testPlan91.getWorkflow().getActions().get(0));
			
			Assert.assertNotNull(testPlan91.getWorkflow().getActions().get(0).getInputs());
			Assert.assertNotNull(testPlan91.getWorkflow().getActions().get(0).getInputs().get(0));		
	
			logger.info("OK! IMPORTED selected TestPlans " + testPlan91.getName() + " and " + testPlan1.getName() +
					" for users " + currentUser1.getName() + " and " + currentUser2.getName());
			
			
			//  SUT
			logger.info("4) Select SUT TYPE/INTERACTION and Create SUT for User");
			
			// 1. Recupero lista dei SUT Type direttamente dall'oggetto SUT
			ArrayList<String> systemSUTTypeList = sutController.getSUTTypeList();
			
			// 2. L'utente seleziona un SUT type
			String defaultSUTType = systemSUTTypeList.get(0);
			Assert.assertTrue(defaultSUTType.equals(SUTConstants.SUT_TYPE1_DOCUMENT));
	
			// 3. richiamo servizio che dato il tipo mi restituisce una lista di possibili interazioni
			// Get supported Interaction for the default SUT Type
			List<SUTInteraction> sutInteractionList = sutController.getSUTInteractionList(defaultSUTType);
			Assert.assertTrue(sutInteractionList.size() == 4);		
			
			// 4. l'utente sceglie un'interazione tra quelle disponibili, deve avvenire un cast
			SUTInteraction defaultInteraction = (SUTInteraction) sutInteractionList.get(0);
			Assert.assertTrue(defaultInteraction.getType().equals(SUTConstants.INTERACTION_WEBSITE));
	
			// 5. creo l'interazione utente con il tipo scelto
			SUTInteraction interaction4User= new SUTInteraction(defaultInteraction.getType());
			interaction4User.setEndpoint(null);
			SUTInteraction interaction4User2= new SUTInteraction(defaultInteraction.getType());
			interaction4User2.setEndpoint(null);
			
			// 6. inserisce una descrizione
			String sutDescription = "XML document1 uploaded by email";
	
			// 7. Creo SUT e lo persisto per l'utente corrente
			SUT sut = new SUT("SystemSUT1-1", defaultSUTType, interaction4User, sutDescription);
			SUT sut2 = new SUT("SystemSUT2-2", defaultSUTType, interaction4User2, sutDescription);
			Long sutId = sutController.createSUT(sut, currentUser1);
			Long sut2Id = sutController.createSUT(sut2, currentUser2);
			Assert.assertNotNull(sutId);				
			Assert.assertTrue(sutId.intValue()>0);	
			logger.info("OK! CREATED SUT1 " + sut.getName() + " with type: " + sut.getType() + " and interaction: " + sut.getInteraction().getType());
			logger.info("OK! CREATED SUT2 " + sut2.getName() + " with type: " + sut2.getType() + " and interaction: " + sut2.getInteraction().getType());
	
			
			// Nel momento in cui l'utente avvia il test
			// il sistema, nel controller, effettua prima una verifica di consistenza ( check() )
			// verificando che Test Plan selezionato e SUT di default siano compatibili
			// 1. tra loro
			// 2. con quanto supportato dal sistema
			// SE questa funzione ha successo si passa alla createSession()
			// ALTRIMENTI � necessario specificare/creare un altro SUT da abbinare gli input "inconsistenti"
			logger.info("5) CREATING SESSION...");
			
			Long session91Id = sessionController.createSession(currentUser91Id, sutId, testPlan91Id);
			Assert.assertNotNull(session91Id);
			System.out.println("sessionId:" + session91Id);
			Assert.assertTrue(session91Id.intValue()>0);		
			logger.info("OK! CREATE SESSION with id " + session91Id);
	
			Long session1Id = sessionController.createSession(currentUser1Id, sut2Id, testPlan1Id);
			Assert.assertNotNull(session1Id);
			System.out.println("sessionId:" + session1Id);
			Assert.assertTrue(session1Id.intValue()>0);		
			logger.info("OK! CREATE SESSION2 with id " + session1Id);
	

			
			// TODO PREPARARE UNA HASHTABLE CHE ASSOCIA AI REFID I NOMI DEI FILE
			
			
			
			// Preparo una lista di file per ogni sessione di test
			String[] fileList91 = {"ubl-invoice.xml", "ubl-invoice_withError.xml"};
			
			// il terzo non sarebbe necessario, non dovrebbe venire richiesto, essendo l'idRef lo stesso
			String[] fileList1 = {"ubl-invoice.xml", "ubl-invoice_withError.xml", "ubl-invoice.xml"};
			
			
			String[] fileList2 = {"ubl-invoice.xml"};
			
			
			// SESSION EXECUTION
			execution(session91Id, fileList91);
			
			execution(session1Id, fileList1);
			
			
			
			
			
			
			
			
			
			
			
		
		
		
		
		}

		
	}
		
	

	
	
	
	@AfterClass
	public static void after_testPlanManager() throws Exception {

		if (beforeOK) {
		
			Boolean deleting;
	
			// TODO N.B.
			// � necessario cancellare i workflow prima di cancellare Role > User > TestPlan
			// sarebbe pi� logico che le cancellazioni fossero a cascata (ovvero, cancello una di queste entity e vengono cancellate quelle successive)
			// Role > User > TestPlan > workflow > actions
			List<Long> sessionIdList = sessionController.getSessionIdList(currentUser1);
			Assert.assertTrue(sessionIdList.size() == 1);	
			
			//boolean deletingSession = sessionController.deleteSession(sessionIdList.get(0)); 
			//Assert.assertTrue(deletingSession);
			
			
			
			
			
			// Get Role List
			List<Long> roleIdList = userAdminController.getRoleIdList();
			Assert.assertTrue(roleIdList.size() == 4);
			
			//Role tempRole;
			//Long tempRoleId;
			
			
			
			
			// Cancello ogni ruolo
	/*		for (int u=0;u<roleIdList.size();u++) {
				
				tempRoleId = (Long) roleIdList.get(u);
				Assert.assertTrue(tempRoleId.intValue() > 0);
	
				tempRole = userAdminController.readRole(tempRoleId);			
				Assert.assertNotNull(tempRole);
							
				// DELETE Role
				deleting = userAdminController.deleteRole(tempRole.getId());
				Assert.assertTrue(deleting);			
			}		*/
			
			List<Long> userIdList = userAdminController.getUserIdList(superUser);
			
			User tempUser;
			Long tempUserId;
			for (int u=0;u<userIdList.size();u++) {
				
				tempUserId = (Long) userIdList.get(u);
				Assert.assertTrue(tempUserId.intValue() > 0);
	
				tempUser = userAdminController.readUser(tempUserId);			
				Assert.assertNotNull(tempUser);
							
				// DELETE User
				if (tempUser.getRole().getLevel() != role4_superuser.getLevel() ) {			
					//deleting = userAdminController.deleteUser(tempUser.getId());
					logger.info("NO Deleting User with ID: " + tempUser.getId());
					//Assert.assertTrue(deleting);			
				}
			}
	
			
			// Get Role List
			//roleIdList = userAdminController.getRoleIdList();
			//Assert.assertTrue(roleIdList.size() == 0);
			
			// Last Check
			// Sono stati eliminati tutti gli utenti (a cascata)?
			userIdList = userAdminController.getUserIdList(superUser);
			//Assert.assertTrue(userIdList.size() == 1);
			
			
			// Get Session List
			sessionIdList = sessionController.getSessionIdList(currentUser1);
			//Assert.assertTrue(sessionIdList.size() == 0);
			
	
			List<FileStore> documentList = fileController.getFileListByType("document");
			//Assert.assertTrue(documentList.size() == 0);
			
			// Cancello ogni ruolo
			/*for (int s=0;s<sessionIdList.size();s++) {
	
							
				// DELETE Role
				deleting = sessionController.deleteSession(sessionIdList.get(s));
				Assert.assertTrue(deleting);			
			}	*/	
			
		}
	}
	
	// TODO da inserire nelle xlab-common
	public byte[] convertInputStreamToByteArray(InputStream is) throws IOException { 
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		
		int reads = is.read(); 
		while(reads != -1){ 
			baos.write(reads); 
			reads = is.read(); 
		} 
		
		return baos.toByteArray(); 
		
	}
	
	
	
	private void execution(Long sessionId, String[] fileList) {
		
		
		logger.info("********************************************");
		logger.info("");
		logger.info("********** Part 2: Pre-Execution ***********");	
		
		
		// A questo punto, ho avviato la sessione di test per la tripla (utente, sut, testplan)
		// L'esecuzione del workflow non � ancora partita
		
		// L' UTENTE, CREATA LA SESSIONE, PUO' FARE POLLING PER  
		// 1 MONITORARE ESECUZIONE ACTIONS
		// 2 RISPONDERE A UNA RICHIESTA DI INTERAZIONE
		// 3 OTTENERE IL REPORT DI OUTPUT (totale o parziale) DEI TEST 
		
		logger.info("1) Retrieve SESSION...");
		
		// GET Current Session
		Session currentSession = sessionController.readSession(sessionId);
		Assert.assertNotNull(currentSession);
		Assert.assertTrue(currentSession.getId().intValue() > 0);
		logger.info("Session ID: " + currentSession.getId().intValue());
		logger.info("User ID: " + currentSession.getUser().getId().intValue());
		
		TestPlan testPlan = currentSession.getTestPlan();
		Assert.assertNotNull(testPlan);
		Assert.assertTrue(testPlan.getId().intValue() > 0);
		logger.info("TestPlan ID: " + testPlan.getId().intValue());
		
		SUT sut = currentSession.getSut();
		
		// Check, provo a prendere un valore dall'oggetto sut contenuto in session
		Assert.assertTrue(currentSession.getSut().getInteraction().getType().equals(SUTConstants.INTERACTION_WEBSITE));
		logger.info("SUT ID: " + currentSession.getSut().getId().intValue());		
		
		// Othe Information
		logger.info("STATE of Session: " + currentSession.getState());
		//logger.info("Required USER INTERACTION: " + currentSession.getUserInteractions().size());
		logger.info("OK! SESSION Retrieved!");
		
		
		List<FileStore> documentList = new ArrayList<FileStore>();
		
		
		// GET Workflow
		logger.info("2) Retrieve WORKFLOW of ACTIONS...");
		ActionWorkflow workflow = currentSession.getTestPlan().getWorkflow();	
		int actionsNumber = workflow.getActions().size();
		logger.info("Actions: " + actionsNumber);
		
		// Definisco due actionMark:
		// actionMark aggiornato
		int actionMark = currentSession.getActionMark();
		logger.info("Action Mark: " + actionMark);
		// actionMarkPreRun continua a conservare lo stato prima dell'esecuzione della action
		// questo ci serve per capire se � variato o meno (varia se l'action � stata eseguita)
		int actionMarkPreRun;
		// all'inizio entrambi i marker sono settati a 1 (prima action)
		Assert.assertTrue(actionMark == 1);
		
		// Get First Action to Execute
		logger.info("First Action Name: " + workflow.getActions().get(0).getActionName());
		logger.info("OK! Retrieved WORKFLOW of ACTIONS.");
				
		
		logger.info("****************************************");
		logger.info("");
		logger.info("********** Part 3: Execution ***********");	
		
		
		// START EXUCUTION WORKFLOW CYCLE
		// CICLO finch�:
		// 1. le azioni da eseguire non sono finite
		// 2. l'utente non decide di sospendere la sessione di test
		// 3. l'utente non decide di annullare la sessione di test
		// 4. non raggiungo il numero massimo di fallimenti per la stessa action
		
		List<Input> inputList;
		String fileIdRef;
		
		String absSuperUserDocFilePath = PropertiesUtil.getSuperUserDocsDirPath();
		
		
		
		// Definisco un contatore per evitare che la stessa Action si ripeta all'infinito
		int failuresForAction = 0;
		Report report = null;
		String fileName;
		Action currentAction;
		
		
		boolean running = true;
		
		
		// La variabile di controllo "running" diventa false in uno di questi 5 casi:
		// 		report.getState().equals(Report.getFinalState()) ||
		//		currentSession.getState().equals(Session.getSuspendedState()) ||
		//		currentSession.getState().equals(Session.getAbortedState()) ||
		//		currentSession.getState().equals(Session.getDoneState()) ||
		//		(failuresForAction == Constants.COUNTER_MAX)		
		// TODO quello del report si potr� togliere poich� ridondante nella sessione	
		Boolean updating;
		int inputCounter = 0;
		while (running) {

			
			// Sincronizzo i due marker (servono per recuperare l'action e capire se il workflow � incrementato dopo la run)
			actionMarkPreRun = actionMark;

			logger.info("Pre-Running Workflow...SESSION STATE: " + currentSession.getState());
			
			
			//////////////////
			// RUN WORKFLOW //
			//////////////////			
			currentSession = sessionController.runWorkflow(workflow, currentSession);

			// REFRESH workflow, actionMark and report
			workflow = testPlanController.readTestPlan(testPlan.getId()).getWorkflow();		
			actionMark = currentSession.getActionMark();		
			report = currentSession.getReport();
			
			logger.info("Post-Running Workflow...SESSION STATE: " + currentSession.getState());
			logger.info("Action Marker:" + actionMark);
			logger.info("Report State " + report.getState());

			
			
			Assert.assertTrue(report.getName().contains(Report.getReportnamePrefix()));			
			Assert.assertTrue(report.getXml().getBytes().length > 1000);

			
			// 1. prendo l'action corrente 
			// 2. 1 se l'action corrente � NEW, Session diventa WAITING, risolvo gli input, poi avvio il workflow, Session WORKING
			//    2 se l'action corrente � READY, avvio il workflow, Session WORKING
			//    3 se l'action � DONE, Session WORKING, incremento marker
			// 3. dopo aver avviato il workflow controllo la sessione
			//	  se SESSION WAITING, non incremento il marker
			// 	  se SESSION WORKING, incremento il marker

			// Prendo l'action da eseguire e stampo il summary dell'action nel file di log
			currentAction = workflow.getCurrentAction(actionMark);
			Assert.assertNotNull(currentAction);
			
			logger.info("ACTION " + actionMark + " OF " + actionsNumber + " ***********************");	
			logger.info(currentAction.getActionSummaryString());
						
			// se l'action corrente � NEW, Session diventa WAITING, risolvo gli input, poi avvio il workflow
			if ( currentAction.isStateNew() ) {
				
				logger.info("Action State: NEW");	

				// Risolvo gli input, poi ripasso a working
				inputList =	currentAction.getInputs();
				logger.info("Required Inputs: " + inputList.size());	
				
				Input inputTemp = null;
				// PER OGNI INPUT
				for (int i=0; i<inputList.size(); i++) {
				
					logger.info("Input i: " + i);
					
					inputTemp = inputList.get(i);
					
					fileIdRef = inputTemp.getFileIdRef();
					
					logger.info("Input fileIdRef: " + fileIdRef);
					
					
					if (!inputTemp.isInputSolved()) {
					
						logger.info("Input by UPLOAD...");
					
						// 1. UPLOAD
						// a livello di Test passo il file al FileController 
						// questo file viene copiato dalla cartella TeBES_Artifacts/users/0/docs/
						/*if ( !currentAction.getActionName().equals("Wrong XMLSchema-UBL-T10") )
							fileName = "ubl-invoice.xml";
						else
							fileName = "ubl-invoice_withError.xml";*/
						
						fileName = fileList[inputCounter];
						inputCounter++;
						
						// TODO il controller si dovrebbe occupare di aprire il file e passarlo al metodo
						// per ora assumo che venga estratto l'array di byte e gli venga passata quello
						InputStream fileInputStream = null;
						byte[] fileByteArray = null;
						try {

							fileInputStream = new FileInputStream(absSuperUserDocFilePath.concat(fileName));
							fileByteArray = this.convertInputStreamToByteArray(fileInputStream);

							logger.info("File to UPLOAD: " + absSuperUserDocFilePath.concat(fileName));
							
							currentSession = fileController.upload(inputTemp, fileName, sut.getType(), fileByteArray, currentSession);
							logger.info("OK Input by UPLOAD");

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						
						Assert.assertNotNull(fileInputStream);
	
	
						
					} // End if (!isInputSolved)
					
				
				} // End for inputList.size()
			

				
				// Una volta che tutti gli input sono risolti
				// Lo stato dell'Action corrente � diventato READY
				// Lo stato della Session � tornato su WORKING

				
				logger.info("Session State: " + currentSession.getState());
				logger.info("Inputs Loaded > runWorkflow...");
				
				currentSession = sessionController.runWorkflow(workflow, currentSession);
				
				// REFRESH Workflow, actionMark and current Action
				workflow = currentSession.getTestPlan().getWorkflow();		
				actionMark = currentSession.getActionMark();		
				report = currentSession.getReport();
				
				logger.info("After runWorkflow - Session State: " + currentSession.getState());
				
			}	// End if NEW_STATE
			
			logger.info("After if NEW_STATE - Session State: " + currentSession.getState());
			
			
			if ( actionMark > actionMarkPreRun) {
				logger.info("END ACTION: "  + actionMarkPreRun + " OF " + workflow.getActions().size()  + " *********************");
				logger.info("");
				failuresForAction=0;
			}
			// Se actionMark == actionMarkPreRun sono ancora sulla stessa action
			else {
				
				failuresForAction++;	
				
				logger.warn("Action Failed or NOT Finished. Counter: " + failuresForAction);				
				logger.warn("REQUEST INPUT TYPE TO PUT...");
				logger.warn("");
			}
			


			// A QUESTO PUNTO L'UTENTE: 		
			
			// 1. continua con le azioni da eseguire che non sono finite		
			
			// 2. decide di sospendere la sessione di test 
			// 		(lo stato passa in SUSPENDED solo se NON era DONE o CANCELLED)
			currentSession = sessionController.suspendSession(currentSession);	
			
			
			// 3. decide di annullare la sessione di test 
			// 		(lo stato passa in CANCELLED solo se NON era DONE o gi� in CANCELLED)
			//currentSession = sessionController.annulSession(currentSession);		
			
			// 4. decidere di riprendere una sessione precedentemente sospesa
			// 		(lo stato torna in WORKING solo se era in SUSPENDED)
			currentSession = sessionController.resumeSession(currentSession);	
			
			// 5. raggiungo il numero massimo di fallimenti per la stessa action
			// TODO da spostare nel backend
			
			
			if ( 	currentSession.isStateDone() ||
					currentSession.isStateSuspended() ||
					currentSession.isStateCancelled() ||
					(failuresForAction == Constants.COUNTER_MAX)	) {	
				running = false;
			}
			else
				currentAction = workflow.getCurrentAction(actionMark);
			
			System.out.println();
			
		} // End while (running)
		
		logger.info("After while(running) - Session State: " + currentSession.getState());
		
		Assert.assertTrue(failuresForAction < Constants.COUNTER_MAX);	
		// la gestione del counter � necessaria solo se non c'� interazione con l'utente
		// oppure se l'interazione � automatica
		// in ogni caso un controllo di questo tipo per evitare loop infiniti dovrebbe esserci
		
		// TODO quando si chiude la sessione di test?
		// TODO closeSession
		// TODO session.setEndDateTime(endDateTime);
		//sessionController.setState(Session.getDoneState());
			
			
		// TODO manca il filtro per utente, per ora cos� agisco su i file di TUTTI
		documentList = fileController.getFileListByType(sut.getType());
		//Assert.assertTrue(documentList.size() == 2);
		logger.info("Documenti caricati: " + documentList.size());
		

		
		// il report NON deve venire salvato su file
		// se l'utente lo vuole scaricare verr� creato in una location temporanea
		// lo salvo ora per monitorare l'output pi� agevolmente
		
		String reportURL = sessionController.getReportURL(currentSession);
		
		logger.info("Report URL: " + reportURL);
		
		logger.info("***************");
		logger.info("Report Full Description: ");
		if (report != null)
			logger.info(report.getFullDescription());
		else
			logger.info("Report NULL!");
		logger.info("***************");
	}
}

