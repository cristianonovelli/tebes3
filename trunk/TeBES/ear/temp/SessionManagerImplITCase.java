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
import it.enea.xlab.tebes.external.jolie.gjs.GJS;
import it.enea.xlab.tebes.external.jolie.gjs.GJSResult;
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
	private static String TESTPLAN_80 = "TP-80";
	private static String TESTPLAN_81 = "TP-81";
	
	static Role role1_standard, role2_advanced, role3_admin, role4_superuser;
	
	static User superUser, user1, user2, user3;
	
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
		SUTInteraction interactionWebSite = new SUTInteraction(SUTInteraction.WEBSITE);
		SUTInteraction interactionEmail = new SUTInteraction(SUTInteraction.EMAIL);
		SUTInteraction interactionWSClient = new SUTInteraction(SUTInteraction.WS_CLIENT);
		SUTInteraction interactionWSServer = new SUTInteraction(SUTInteraction.WS_SERVER);
		interactionWSServer.setEndpoint("http://www.webservicex.net/globalweather.asmx?WSDL");
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
		user1 = new User(Constants.USER1_NAME, Constants.USER1_SURNAME, Constants.USER1_EMAIL, Constants.USER1_PASSWORD);	
		Long idUser1 = userProfileController.registration(user1, role1_standard);
		Assert.assertNotNull(user1);
		Assert.assertTrue(idUser1.intValue()>0);
		user2 = new User(Constants.USER2_NAME, Constants.USER2_SURNAME, Constants.USER2_EMAIL, Constants.USER2_PASSWORD);
		Long idUser2 = userProfileController.registration(user2, role1_standard);
		Assert.assertNotNull(user2);
		Assert.assertTrue(idUser2.intValue()>0);
		user3 = new User(Constants.USER3_NAME, Constants.USER3_SURNAME, Constants.USER3_EMAIL, Constants.USER3_PASSWORD);	
		Long idUser3 = userProfileController.registration(user3, role1_standard);
		Assert.assertNotNull(user3);
		Assert.assertTrue(idUser3.intValue()>0);		
		logger.info("OK! 3 STANDARD USERS for " + user1.getName() + " , " + user2.getName() + " and " + user3.getName()  + " created!");
		
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
	public void test_session() throws Exception {

		if (beforeOK) {
		
			logger.info("**************** TEST @Test ****************");			
			logger.info("********** Part 1: Configuration ***********");	
			
			// USERS 
			logger.info("1) LOGIN USERS");
			user1 = userProfileController.login(Constants.USER1_EMAIL, Constants.USER1_PASSWORD);
			user2 = userProfileController.login(Constants.USER2_EMAIL, Constants.USER2_PASSWORD);
			user3 = userProfileController.login(Constants.USER3_EMAIL, Constants.USER3_PASSWORD);
			
			Assert.assertTrue(user1 != null);
			Assert.assertTrue(user2 != null);
			Assert.assertTrue(user3 != null);
			
			Long user1Id = user1.getId();
			Long user2Id = user2.getId();
			Long user3Id = user3.getId();
			Assert.assertTrue(user1.getId().intValue() > 0);
			Assert.assertTrue(user2.getId().intValue() > 0);
			Assert.assertTrue(user3.getId().intValue() > 0);
			logger.info("OK! Login of user " + user1.getName() + " " + user1.getSurname() + " with id: " + user1Id);
			logger.info("OK! Login of user " + user2.getName() + " " + user2.getSurname() + " with id: " + user2Id);
			logger.info("OK! Login of user " + user3.getName() + " " + user3.getSurname() + " with id: " + user3Id);
			
			
			// TESTPLAN
			// Lista dei TestPlan disponibili nel sistema	
			logger.info("2) GET system TEST PLANS");
			List<TestPlan> systemTestPlanList = testPlanController.getSystemTestPlanList();
			Assert.assertTrue(systemTestPlanList.size()>0);
			
			TestPlan testPlan = null;
			Long testPlan91Id, testPlan1Id, testPlan80Id, testPlan81Id, testPlanIdTemp;
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
				
				testPlanIdTemp = testPlan.getId();
				Assert.assertTrue(testPlanIdTemp.intValue()>0);	
				
				// Check it
				testPlan = testPlanController.readTestPlan(testPlanIdTemp);
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
	
			TestPlan testPlan80 = tpTable.get(TESTPLAN_80);
			Assert.assertNotNull(testPlan80);

			TestPlan testPlan81 = tpTable.get(TESTPLAN_81);
			Assert.assertNotNull(testPlan81);
			
			// Copia e importazione del TestPlan scelto per l'utente (currentUser)
			testPlan91Id = testPlanController.cloneTestPlan(testPlan91, user1Id);
			testPlan1Id = testPlanController.cloneTestPlan(testPlan1, user2Id);
			testPlan80Id = testPlanController.cloneTestPlan(testPlan80, user3Id);
			testPlan81Id = testPlanController.cloneTestPlan(testPlan81, user3Id);
			Assert.assertTrue(testPlan91Id.intValue()>0);
			Assert.assertTrue(testPlan1Id.intValue()>0);
			Assert.assertTrue(testPlan80Id.intValue()>0);
			Assert.assertTrue(testPlan81Id.intValue()>0);
			
			// Il testPlan selezionato diventa quello clonato per lo User
			testPlan91 = testPlanController.readTestPlan(testPlan91Id);
			testPlan1 = testPlanController.readTestPlan(testPlan1Id);
			testPlan80 = testPlanController.readTestPlan(testPlan80Id);
			testPlan81 = testPlanController.readTestPlan(testPlan81Id);
			Assert.assertNotNull(testPlan91.getWorkflow());
			Assert.assertNotNull(testPlan91.getWorkflow().getActions());
			Assert.assertNotNull(testPlan91.getWorkflow().getActions().get(0));
			
			Assert.assertNotNull(testPlan91.getWorkflow().getActions().get(0).getInputs());
			Assert.assertNotNull(testPlan91.getWorkflow().getActions().get(0).getInputs().get(0));
			//Assert.assertNotNull(testPlan81.getWorkflow().getActions().get(0).getInputs().get(0));	
	
			logger.info("OK! IMPORTED selected TestPlans " + 
					testPlan91.getName() + ", " + testPlan1.getName() + ", " + testPlan80.getName() + ", " + testPlan81.getName() +
					" for users " + user1.getName() + ", " + user2.getName() + " and " + user3.getName());
			
			
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
			Assert.assertTrue(defaultInteraction.getType().equals(SUTInteraction.WEBSITE));
	
			// 5. creo l'interazione utente con il tipo scelto
			SUTInteraction interaction4User= new SUTInteraction(defaultInteraction.getType());
			interaction4User.setEndpoint(null);
			SUTInteraction interaction4User2= new SUTInteraction(defaultInteraction.getType());
			interaction4User2.setEndpoint(null);
			SUTInteraction interaction4User3= new SUTInteraction(defaultInteraction.getType());
			interaction4User3.setEndpoint(null);
			
			// 6. inserisce una descrizione
			String sutDescription = "XML document uploaded by email";
	
			// 7. Creo SUT e lo persisto per l'utente corrente
			SUT sut = new SUT("SystemSUT1", defaultSUTType, interaction4User, sutDescription);
			SUT sut2 = new SUT("SystemSUT2", defaultSUTType, interaction4User2, sutDescription);
			SUT sut3 = new SUT("SystemSUT3", defaultSUTType, interaction4User3, sutDescription);
			Long sutId = sutController.createSUT(sut, user1);
			Long sut2Id = sutController.createSUT(sut2, user2);
			Long sut3Id = sutController.createSUT(sut3, user3);
			Assert.assertNotNull(sutId);				
			Assert.assertTrue(sutId.intValue()>0);	
			logger.info("OK! CREATED SUT1 " + sut.getName() + " with type: " + sut.getType() + " and interaction: " + sut.getInteraction().getType());
			logger.info("OK! CREATED SUT2 " + sut2.getName() + " with type: " + sut2.getType() + " and interaction: " + sut2.getInteraction().getType());
			logger.info("OK! CREATED SUT3 " + sut3.getName() + " with type: " + sut3.getType() + " and interaction: " + sut3.getInteraction().getType());
	
	
			// --- DEFINISCO SUT WS-CLIENT ---
			// Transport
			String transportSUTType = systemSUTTypeList.get(1);
			logger.info("transportSUTType:" + transportSUTType);
			Assert.assertTrue(transportSUTType.equals(SUTConstants.SUT_TYPE2_TRANSPORT));			

			// interazioni per transoport
			sutInteractionList = sutController.getSUTInteractionList(transportSUTType);
			Assert.assertTrue(sutInteractionList.size() == 3);	
			
			/*SUTInteraction wsClientInteraction = (SUTInteraction) sutInteractionList.get(1);
			Assert.assertTrue(wsClientInteraction.getType().equals(SUTConstants.INTERACTION_WS_CLIENT));
			
			SUTInteraction interaction4User3WS= new SUTInteraction(wsClientInteraction.getType());
			interaction4User3WS.setEndpoint("http://endpoint/totest/wsclient/");
			
			SUT sut4 = new SUT("WS-SUT4", transportSUTType, interaction4User3WS, "sut ws-client test");
			Long sut4Id = sutController.createSUT(sut4, user3);*/
			// --- fine DEFINE SUT WS-CLIENT ---
			
			// --- DEFINISCO SUT WS-SERVER ---
			// interazioni per transoport
			
			SUTInteraction wsServerInteraction = (SUTInteraction) sutInteractionList.get(2);
			Assert.assertTrue(wsServerInteraction.getType().equals(SUTInteraction.WS_SERVER));
			
			SUTInteraction interactionWSServer= new SUTInteraction(wsServerInteraction.getType());
			interactionWSServer.setEndpoint("http://www.webservicex.net/globalweather.asmx?WSDL");
			interactionWSServer.setOperation("GetWeather");
			interactionWSServer.setPort("GlobalWeatherSoap");
			interactionWSServer.setTimeout(10000);
			
			SUT sutWSServer = new SUT("WS-Server", transportSUTType, interactionWSServer, "sut ws-server");
			Long sutServerId = sutController.createSUT(sutWSServer, user3);
			// --- fine DEFINE SUT WS-SERVER ---		
			
			
			
			// --- DEFINISCO SUT WS-CLIENT ---
			// interazioni per transoport
			
			SUTInteraction wsClientInteraction = (SUTInteraction) sutInteractionList.get(1);
			Assert.assertTrue(wsClientInteraction.getType().equals(SUTInteraction.WS_CLIENT));
			
			SUTInteraction interactionWSClient= new SUTInteraction(wsClientInteraction.getType());
			interactionWSClient.setEndpoint("http://www.webservicex.net/globalweather.asmx?WSDL");
			interactionWSClient.setOperation("GetWeather");
			interactionWSClient.setPort("GlobalWeatherSoap");
			interactionWSServer.setTimeout(10000);
			
			SUT sutWSClient = new SUT("WS-Client", transportSUTType, interactionWSClient, "sut ws-client");
			Long sutClientId = sutController.createSUT(sutWSClient, user3);
			// --- fine DEFINE SUT WS-CLIENT ---		
						
			
			
			
			// Nel momento in cui l'utente avvia il test
			// il sistema, nel controller, effettua prima una verifica di consistenza ( check() )
			// verificando che Test Plan selezionato e SUT di default siano compatibili
			// 1. tra loro
			// 2. con quanto supportato dal sistema
			// SE questa funzione ha successo si passa alla createSession()
			// ALTRIMENTI è necessario specificare/creare un altro SUT da abbinare gli input "inconsistenti"
			logger.info("5) CREATING SESSION...");
			
			
			
		
			// SESSION1: 2 actions for correct UBL invoice and wrong UBL invoice
			Long session91Id = sessionController.createSession(user1Id, sutId, testPlan91Id);
			Assert.assertNotNull(session91Id);
			logger.info("sessionId:" + session91Id);
			Assert.assertTrue(session91Id.intValue()>0);		
			logger.info("OK! CREATE SESSION with id " + session91Id);
			
			// Preparo una lista di file per ogni sessione di test
			String[] fileList91 = {"ubl-invoice.xml", "ubl-invoice_withError.xml"};
			
	
			
			
			/*
			 * SESSION2: 2 actions for UBL schema and schematron
			 * Long session1Id = sessionController.createSession(user2Id, sut2Id, testPlan1Id);
			Assert.assertNotNull(session1Id);
			logger.info("sessionId:" + session1Id);
			Assert.assertTrue(session1Id.intValue()>0);		
			logger.info("OK! CREATE SESSION2 with id " + session1Id);	
			
			// il terzo non sarebbe necessario, non dovrebbe venire richiesto, essendo l'idRef lo stesso
			String[] fileList1 = {"ubl-invoice.xml", "ubl-invoice_withError.xml", "ubl-invoice.xml"};
			*/	

			
			// SESSION3: 1 actions for Global Weather Web Service (User-ServerWS and TeBES-ClientWS)
			Long session80Id = sessionController.createSession(user3Id, sutServerId, testPlan80Id);
			Assert.assertNotNull(session80Id);
			logger.info("sessionId:" + session80Id);
			Assert.assertTrue(session80Id.intValue()>0);		
			logger.info("OK! CREATE SESSION3 with id " + session80Id);	
			logger.info("");	
			
			
			
			// SESSION4: 1 actions for Global Weather Web Service (User-ClientWS and TeBES-ServerWS)
			Long session81Id = sessionController.createSession(user3Id, sutClientId, testPlan81Id);
			Assert.assertNotNull(session81Id);
			logger.info("sessionId:" + session81Id);
			Assert.assertTrue(session81Id.intValue()>0);		
			logger.info("OK! CREATE SESSION4 with id " + session81Id);	
			logger.info("");	
			
			
			

			
			
			//String[] fileList2 = {"ubl-invoice.xml"};
			
			
			// List of Input fot the Global Weather case = 0 Input required
			//String[] fileList80 = {"Bologna", "Italy"};
			
			String[] fileList81 = {"ok"};
			
			
			// UBL 
			execution(session91Id, fileList91);
			
			//execution(session1Id, fileList1);
			
			// Global Weather Web Service
			//execution(session80Id, fileList80);
			
			// UserClient-TeBESWS
			//execution(session81Id, fileList81);			
			
		
		
		}
		
		
		logger.info("");
		logger.info("*******************************************");
		logger.info("");
	}
		
	

	
	
	
	@AfterClass
	public static void after_testPlanManager() throws Exception {

		
		logger.info("\n");
		logger.info("**************** TEST @AfterClass ****************");			

		
		
		
		if (beforeOK) {
		
			Boolean deleting;
	
			// TODO N.B.
			// è necessario cancellare i workflow prima di cancellare Role > User > TestPlan
			// sarebbe più logico che le cancellazioni fossero a cascata (ovvero, cancello una di queste entity e vengono cancellate quelle successive)
			// Role > User > TestPlan > workflow > actions
			List<Long> sessionIdList = sessionController.getSessionIdList(user3);
			logger.info("sessionIdList.size(): " + sessionIdList.size());

			
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
			/*for (int u=0;u<userIdList.size();u++) {
				
				tempUserId = (Long) userIdList.get(u);
				Assert.assertTrue(tempUserId.intValue() > 0);
	
				tempUser = userAdminController.readUser(tempUserId);			
				Assert.assertNotNull(tempUser);
							
				// DELETE User
				if (tempUser.getRole().getLevel() != role4_superuser.getLevel() ) {			
					deleting = userAdminController.deleteUser(tempUser.getId());
					Assert.assertTrue(deleting);			
					logger.info("Deleting User with ID: " + tempUser.getId());
				}
			}
	
			
			// Get Role List
			//roleIdList = userAdminController.getRoleIdList();
			//Assert.assertTrue(roleIdList.size() == 0);
			
			// Last Check
			// Sono stati eliminati tutti gli utenti (a cascata)?
			userIdList = userAdminController.getUserIdList(superUser);
			Assert.assertTrue(userIdList.size() == 1);
			
		
			
	
			List<FileStore> documentList = fileController.getFileListByType("document");*/
			//Assert.assertTrue(documentList.size() == 0);
			
			// Cancello ogni ruolo
			/*for (int s=0;s<sessionIdList.size();s++) {
	
							
				// DELETE Role
				deleting = sessionController.deleteSession(sessionIdList.get(s));
				Assert.assertTrue(deleting);			
			}	*/	
			
		}
		
		logger.info("");
		logger.info("*******************************************");
		logger.info("");
		
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
	
	
	
	private void execution(Long sessionId, String[] userInputList) throws Exception {
		
		
		logger.info("********************************************");
		logger.info("");
		logger.info("********** Part 2: Pre-Execution ***********");	
		
		
		// A questo punto, ho avviato la sessione di test per la tripla (utente, sut, testplan)
		// L'esecuzione del workflow non è ancora partita
		
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
		logger.info("TestPlan Name: " + testPlan.getName());
		
		
		SUT sut = currentSession.getSut();
		logger.info("SUT ID: " + currentSession.getSut().getId().intValue());
		logger.info("SUT Interaction: " + currentSession.getSut().getInteraction().getType());
		logger.info("SUT Description: " + currentSession.getSut().getDescription());
		
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
		// questo ci serve per capire se è variato o meno (varia se l'action è stata eseguita)
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
		// CICLO finchè:
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
		String fileName, textValue;
		Action currentAction;
		
		
		boolean running = true;
		
		
		// La variabile di controllo "running" diventa false in uno di questi 5 casi:
		// 		report.getState().equals(Report.getFinalState()) ||
		//		currentSession.getState().equals(Session.getSuspendedState()) ||
		//		currentSession.getState().equals(Session.getAbortedState()) ||
		//		currentSession.getState().equals(Session.getDoneState()) ||
		//		(failuresForAction == Constants.COUNTER_MAX)		
		// TODO quello del report si potrà togliere poiché ridondante nella sessione	
		Boolean updating;
		int inputCounter = 0;
		while (running) {

			
			// Sincronizzo i due marker (servono per recuperare l'action e capire se il workflow è incrementato dopo la run)
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

			
			
			Assert.assertTrue(report.getName().contains(Constants.REPORTNAME_PREFIX));			
			Assert.assertTrue(report.getXml().getBytes().length > 1000);

			
			// 1. prendo l'action corrente 
			// 2. 1 se l'action corrente è NEW, Session diventa WAITING, risolvo gli input, poi avvio il workflow, Session WORKING
			//    2 se l'action corrente è READY, avvio il workflow, Session WORKING
			//    3 se l'action è DONE, Session WORKING, incremento marker
			// 3. dopo aver avviato il workflow controllo la sessione
			//	  se SESSION WAITING, non incremento il marker
			// 	  se SESSION WORKING, incremento il marker

			// Prendo l'action da eseguire e stampo il summary dell'action nel file di log
			currentAction = workflow.getCurrentAction(actionMark);
			
			if (currentAction != null) {
			
				logger.info("ACTION " + actionMark + " OF " + actionsNumber + " ***********************");	
				
	
							
				// se l'action corrente è NEW, Session diventa WAITING, risolvo gli input, poi avvio il workflow
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
						
							// 1. UPLOAD	
							if (inputTemp.getGuiReaction().equals(Input.REACTION_UPLOAD)) {
						
														
								logger.info("Input by UPLOAD...");
								
								fileName = userInputList[inputCounter];
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
									logger.info("OK Input " + inputTemp.getName() + " by UPLOAD");
		
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
								
								Assert.assertNotNull(fileInputStream);
		
							} // END if REACTION_UPLOAD
							
							// 2. TEXT	
							if (inputTemp.getGuiReaction().equals(Input.REACTION_TEXT)) {
								
								logger.info("Input by TEXT...");
								
								textValue = userInputList[inputCounter];
								inputCounter++;
								
								logger.info("Text Label: " + inputTemp.getName()); 
								logger.info("Text Value: " + textValue);
								
								currentSession = fileController.textUpload(inputTemp, textValue, currentSession);

								logger.info("OK Input " + inputTemp.getName() + " by TEXT");
								
							} // END if REACTION_TEXT
							
							// 3. MESSAGE	
							if (inputTemp.getGuiReaction().equals(Input.REACTION_MESSAGE)) {
								
								logger.info("No Input required but TEXT MESSAGE displayed.");

								
								////////////////
								// TODO nell'input by message non c'è un valore di input MA
								// c'è quello che l'utente preme: OK o ANNULLA
								// SE OK vado a vedere il risultato della chiamata client-WS
								// SE ANNULLA inserisco nel report un messaggio di test annullato per questa Action.

								inputCounter++;
								
								logger.info("WS URL: " + inputTemp.getGuiMessage());
								
								System.out.println("");
								System.out.println("");
								System.out.println("WS URL: " + inputTemp.getGuiMessage());
								
								
								
								/*
								 * client jolie per richiamare ws Global Weather creato nel test
								 * String[][] parameters2 = new String[1][2];
								
								parameters2[0][0] = "msg";
								parameters2[0][1] = "document";
						
								GJSResult myHandler2 = new GJSResult();								
								GJS.generateClientWS(
										inputTemp.getGuiMessage(), 
										currentSession.getSut().getInteraction().getOperation(), 
										currentSession.getSut().getInteraction().getPort(), 
										"temporary-client", 
										"C:/Temp/temporary-client",
										"report.xml",
										"standard",
										parameters2, 
										myHandler2);*/

								
								
								
								int ii=0;
								while( ii<30 ) {
									try {

										
										
										
										
										Thread.sleep(1000);
										System.out.print(++ii + " ");

									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								System.out.println("");
								
								currentSession = fileController.message(inputTemp, "OK", currentSession);

								logger.info("OK Input by MESSAGE");
								
							} // END if REACTION_MESSAGE
							
							// 4. NO PREVIOUS CASE -> EXCEPTION 
							if ( !inputTemp.getGuiReaction().equals(Input.REACTION_TEXT) && 
									!inputTemp.getGuiReaction().equals(Input.REACTION_UPLOAD) &&
										!inputTemp.getGuiReaction().equals(Input.REACTION_MESSAGE) ) {
								
										throw new Exception("Test Exception: Invalid GUI REACTION in Input: " + inputTemp.getName()); 
							}
							
						} // End if (!isInputSolved)
						
					
					} // End for inputList.size()
				
	
					
					// Una volta che tutti gli input sono risolti
					// Lo stato dell'Action corrente è diventato READY
					// Lo stato della Session è tornato su WORKING
	
					
					logger.info("Session State: " + currentSession.getState());
					logger.info("Inputs Loaded > runWorkflow...");
					
					currentSession = sessionController.runWorkflow(workflow, currentSession);
					
					if ( currentSession != null ) {
						// REFRESH Workflow, actionMark and current Action
						workflow = currentSession.getTestPlan().getWorkflow();		
						actionMark = currentSession.getActionMark();		
						report = currentSession.getReport();
						
						logger.info("After runWorkflow - Session State: " + currentSession.getState());
					}
					else {
						logger.error("Session NULL!");
						return;
					}
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
				// 		(lo stato passa in CANCELLED solo se NON era DONE o già in CANCELLED)
				//currentSession = sessionController.annulSession(currentSession);		
				
				// 4. decidere di riprendere una sessione precedentemente sospesa
				// 		(lo stato torna in WORKING solo se era in SUSPENDED)
				currentSession = sessionController.resumeSession(currentSession);	
				
				// 5. raggiungo il numero massimo di fallimenti per la stessa action
				// TODO da spostare nel backend
				
			} // End if currentAction != null
			
			
			if ( 	currentSession.isStateDone() ||
					currentSession.isStateSuspended() ||
					currentSession.isStateCancelled() ||
					(failuresForAction == Constants.COUNTER_MAX)	) {	
				running = false;
			}
			else
				currentAction = workflow.getCurrentAction(actionMark);
			
		} // End while (running)
		
		logger.info("After while(running) - Session State: " + currentSession.getState());
		
		Assert.assertTrue(failuresForAction < Constants.COUNTER_MAX);	
		// la gestione del counter è necessaria solo se non c'è interazione con l'utente
		// oppure se l'interazione è automatica
		// in ogni caso un controllo di questo tipo per evitare loop infiniti dovrebbe esserci
		
		// TODO quando si chiude la sessione di test?
		// TODO closeSession
		// TODO session.setEndDateTime(endDateTime);
		//sessionController.setState(Session.getDoneState());
			
			
		// TODO manca il filtro per utente, per ora così agisco su i file di TUTTI
		documentList = fileController.getFileListByType(sut.getType());
		//Assert.assertTrue(documentList.size() == 2);
		logger.info("Documenti caricati: " + documentList.size());
		

		
		// il report NON deve venire salvato su file
		// se l'utente lo vuole scaricare verrà creato in una location temporanea
		// lo salvo ora per monitorare l'output più agevolmente
		
		String reportURL = sessionController.getReportURL(currentSession);
		
		logger.info("Report URL: " + reportURL);
		
		
		
		/*try {
			XLabFileManager.append(report.getFullDescription(), report.getLogLocation());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*logger.info("***************");
		logger.info("Report Full Description: ");
		if (report != null)
			logger.info(report.getFullDescription());
		else
			logger.info("Report NULL!");
		logger.info("***************");*/
	}
}

