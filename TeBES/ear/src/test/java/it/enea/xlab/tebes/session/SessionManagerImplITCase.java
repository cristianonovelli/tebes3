package it.enea.xlab.tebes.session;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.controllers.session.SessionManagerController;
import it.enea.xlab.tebes.controllers.session.ValidationController;
import it.enea.xlab.tebes.controllers.sut.SUTManagerController;
import it.enea.xlab.tebes.controllers.testplan.TestPlanManagerController;
import it.enea.xlab.tebes.controllers.users.UserAdminController;
import it.enea.xlab.tebes.controllers.users.UserProfileController;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.Interaction;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.TestPlanXML;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.utilities.WebControllersUtilities;

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
	
	// Interface Declarations
	static SessionManagerController sessionController;
	static TestPlanManagerController testPlanController;
	static UserAdminController userAdminController;
	static UserProfileController userProfileController;
	static SUTManagerController sutController;
	
	static Role role1_standard, role2_advanced, role3_admin, role4_superuser;
	
	

	@BeforeClass
	public static void before_testPlanManager() throws Exception {
		
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
		User currentUser = new User(Constants.USER1_NAME, Constants.USER1_SURNAME, Constants.USER1_EMAIL, Constants.USER1_PASSWORD);	
		Long idTempUser1 = userProfileController.registration(currentUser, role1_standard);
		Assert.assertNotNull(currentUser);
		Assert.assertTrue(idTempUser1.intValue()>0);
		User otherUser = new User(Constants.USER2_NAME, Constants.USER2_SURNAME, Constants.USER2_EMAIL1, Constants.USER2_EMAIL1);
		Long idTempUser2 = userProfileController.registration(otherUser, role1_standard);
		Assert.assertNotNull(currentUser);
		Assert.assertTrue(idTempUser2.intValue()>0);
	}
	
	
	/*//@Test
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
		Action a = new Action(1, "nome", Action.getTodoState(),"taml", "tc", "www.ciao.it", "3<2", false, "descrizione");
		Action a2 = new Action(2, "nome2", Action.getTodoState(), "taml", "tc", "www.ciao.it", "3<2", false, "descrizione");	
		Long actionId = testPlanController.createAction(a, workflowId);
		Assert.assertTrue(actionId.intValue()>0);	
		Long actionId2 = testPlanController.createAction(a2, workflowId);
		Assert.assertTrue(actionId2.intValue()>0);		
		wf.getActions().add(a);
		wf.getActions().add(a2);
		
		TestPlanXML tpXML = new TestPlanXML("xml", "location");
		
		// CREATE TestPlan
		// TODO la persistenza di questo TestPlan con questo workflow, non gli piace
		// dovrei farne la persistenza senza e poi attaccarlo!
		TestPlan tp = new TestPlan("xml", "datetime", "state", "location", "description", wf, tpXML);
		Long tpid = testPlanController.createTestPlan(tp, currentUserId);
		Assert.assertTrue(tpid.intValue()>0);			
		
		// ADD Workflow to TestPlan
		//testPlanController.addWorkflowToTestPlan(workflowId, tpid);
		
		// ADD TestPlan to User
		//Long adding = testPlanController.addTestPlanToUser(tpid, currentUserId);
		//Assert.assertTrue(adding.intValue()>0);	
		
		
		
		currentUser = userProfileController.login(currentUser.geteMail(), currentUser.getPassword());
		
		// TODO se questo funziona non c'è bisogno del metodo per prelevare il testplan dato lo user
		tp = currentUser.getTestPlans().get(0);
		Assert.assertNotNull(tp);
		
		
		
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
		Assert.assertTrue(sutId.intValue()>0);
		
		
		// CREATE SESSION
		Long sessionId = sessionController.run(currentUserId, sutId, tp.getId());
		Assert.assertNotNull(sessionId);
		Assert.assertTrue(sessionId.intValue()>0);
		
		// TODO LA SESSIONE AL MOMENTO NON FA NULLA E INVECE DOVREBBE:
		// 1. ESEGUIRE IL TESTPLAN CARICANDOLO IN MEMORIA,
		// 2. INVOCARE I VALIDATORI NECESSARI A ESEGUIRE I TEST E
		// 3. CREARE IL RELATIVO REPORT
		
		Boolean deleting;
		

		TestPlan tpBis = testPlanController.readTestPlan(tp.getId());
		Assert.assertNotNull(tpBis);
		
		ActionWorkflow wfBis = tpBis.getWorkflow();
		Assert.assertNotNull(wfBis);
		
		Long actionIdBis = wfBis.getActions().get(0).getId();
		Action aBis = testPlanController.readAction(actionIdBis);
		Assert.assertNotNull(aBis);
		
		if ( wfBis.getActions().contains(aBis) ) {
			System.out.println("AAAAAAAA" + aBis.getId());
			wfBis.getActions().remove(aBis);
			Assert.assertTrue(testPlanController.updateWorkflow(wfBis));
		}
		
		aBis = testPlanController.readAction(actionIdBis);
		
		deleting = testPlanController.deleteAction(aBis.getId());
		Action aBis2 = testPlanController.readAction(actionIdBis);
		Assert.assertNull(aBis2);
		
		
		wfBis = tpBis.getWorkflow();
		Assert.assertNotNull(wfBis);
		if ( wfBis.getActions().size()>0 ) {
			aBis = wfBis.getActions().get(1);
			
			deleting = testPlanController.deleteAction(aBis.getId());
			Assert.assertTrue(deleting);
			Action aBis3 = testPlanController.readAction(aBis.getId());
			Assert.assertNull(aBis3);		
		}
		
		
		
		//System.out.println("FFFFFFFF: " + aBis.getId());
		//deleting = testPlanController.deleteAction(aBis.getId());
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
	}*/
	
	
	
	@Test
	public void test2_autoCreation() {
		
		// Login SuperUser
		String superUserEmail = PropertiesUtil.getUser1Email();
		String superUserPassword = PropertiesUtil.getUser1Password();
		User superUser = userProfileController.login(superUserEmail, superUserPassword);
		Long superUserId = superUser.getId();
		superUser = userAdminController.readUser(superUserId);
		
		// Importazione dei Test Plan per lo SuperUser
		// N.B. questa operazione viene fatta in fase di setup del sistema
		// 1. SuperUser Get Test Plan List (list of name files)
		// 2. SuperUser Import TestPlan
		// 3. Get List from User
		boolean importing = testPlanController.importSystemTestPlanFile(superUser);
		Assert.assertTrue(importing);

		
		//Vector<String> systemTestPlanXMLFileList = testPlanController.getSystemXMLTestPlanList();
		//Assert.assertTrue(systemTestPlanXMLFileList.size() == 2);
		TestPlan testPlan = null;
		Long testPlanId;
		//String testPlanAbsPathName;
		//String superUserTestPlanDir = PropertiesUtil.getSuperUserTestPlanDir();
		
		
		// Lista dei TestPlan disponibili nel sistema
		// N.B. l'importazione deve essere stata fatta in fase di Setup della piattaforma		
		List<TestPlan> systemTestPlanList = testPlanController.getSystemTestPlanList();
		Assert.assertTrue(systemTestPlanList.size()>0);
		
		// Per ogni TestPlan... lo verifico
		for (int i=0; i<systemTestPlanList.size();i++) {

			testPlan = systemTestPlanList.get(i);
			Assert.assertNotNull(testPlan);
			
			Assert.assertNotNull(testPlan.getWorkflow());
			Assert.assertNotNull(testPlan.getWorkflow().getActions());
			Assert.assertNotNull(testPlan.getWorkflow().getActions().get(0));	
			
			testPlanId = testPlan.getId();
			Assert.assertTrue(testPlanId.intValue()>0);	
			
			// Check it
			testPlan = testPlanController.readTestPlan(testPlanId);
			Assert.assertNotNull(testPlan.getWorkflow());
			Assert.assertNotNull(testPlan.getWorkflow().getActions());
			Assert.assertNotNull(testPlan.getWorkflow().getActions().get(0));			
		}	
		


		// Login User generico
		User currentUser = userProfileController.login(Constants.USER1_EMAIL, Constants.USER1_PASSWORD);
		Long currentUserId = currentUser.getId();
		
		// Selezione di un TestPlan generico
		TestPlan selectedTestPlan = systemTestPlanList.get(0);
		Assert.assertNotNull(selectedTestPlan);

		// Copia e importazione del TestPlan scelto
		testPlanId = testPlanController.cloneTestPlan(selectedTestPlan, currentUserId);
		Assert.assertTrue(testPlanId.intValue()>0);			
		
		//adding = testPlanController.addTestPlanToUser(testPlanId, currentUserId);
		//Assert.assertTrue(adding.intValue()>0);	
		
		
		
		// Creazione di un SUT
		Interaction interaction = new Interaction(Constants.INTERACTION_WEBSITE);
		SUT sut = new SUT("sut1", Constants.SUT_TYPE1_DOCUMENT, Constants.UBL, Constants.UBLSCHEMA, interaction, "XML document1 uploaded by web interface");
		Long sutId = sutController.createSUT(sut, currentUser);
		Assert.assertNotNull(sutId);	
		Assert.assertTrue(sutId.intValue()>0);	
		
		
		// NEL MOMENTO IN CUI UN UTENTE AVVIA L'ESECUSIONE DI TEST:
		// 1. VIENE AVVIATO IL TEST
		// 2. UTENTE PUO' A QUESTO PUNTO FARE POLLING SULLA PROPRIA SESSIONE PER  
		// 2.1 MONITORARE ESECUZIONE ACTIONS
		// 2.2 RISPONDERE A UNA RICHIESTA DI INTERAZIONE
		// 2.3 OTTENERE L'OUTPUT DEI TEST 
		
		
		// CREATE SESSION
		Long sessionId = sessionController.run(currentUserId, sutId, testPlanId);
		Assert.assertNotNull(sessionId);
		Assert.assertTrue(sessionId.intValue()>0);
		
		
		// GET Current Session
		Session currentSession = sessionController.readSession(sessionId);
		Assert.assertNotNull(currentSession);
		Assert.assertTrue(currentSession.getId().intValue() > 0);
			
		// TODO: PRE-EXECUTION
		//  IL SISTEMA EFFETTUA UNA VERIFICA, C'E' UN SUT COMPATIBILE PER OGNI ACTION? SE NO, SI VA NEL SUT MANAGER
		// Devo collegare un SUT alle action
		// SE C'è SOLO UN SUT DI TIPO DOCUMENT, OK
		// SE CE NE SONO N COME LI COLLEGO ALLE ACTION?
		// MOSTRO LE N ACTION, IL TIPO E UN MENU' A TENDINA CON I SUT DISPONIBILI DI QUEL TIPO PER OGNI ACTION
		// SALVO IL COLLEGAMENTO NELLA ACTION SOTTO LA VOCE SUTID.
		
	
		
			
		Assert.assertNotNull(selectedTestPlan);
		Assert.assertTrue(selectedTestPlan.getId().intValue() > 0);

		
		// WORKFLOW ACTIONS - 
		
		// Definisco un contatore per evitare che la stessa Action si ripeta all'infinito
		int failuresForAction = 0;
		
		Report report;
		ActionWorkflow workflow;
		Action currentAction;
		
		// GET Workflow, actionMark and current Action
		workflow = testPlanController.readTestPlan(testPlanId).getWorkflow();	
		
		int actionsNumber = workflow.getActions().size();
		int indexTemp;
		
		// Definisco due actionMark:
		// actionMark aggiornato post-Run
		int actionMark = workflow.getActionMark();
		// actionMarkPreRun continua a conservare lo stato pre-Run
		int actionMarkPreRun;
		// all'inizio entrambi i marker sono settati a 1
		Assert.assertTrue(actionMark == 1);
		
		// Get First Action to Execute
		currentAction = workflow.getActions().get(actionMark - 1);
		
		// START EXUCUTION WORKFLOW CYCLE
		// CICLO finchè:
		// 1. le azioni da eseguire non sono finite
		// 2. l'utente  decide di sospendere la sessione di test
		// 3. l'utente  decide di annullare la sessione di test
		// 4. raggiungo il numero massimo di fallimenti per la stessa action
		boolean running = true;
		while (running) {
		
			actionMarkPreRun = actionMark;
			
			System.out.println("RUNNING WORKFLOW...");

			System.out.println("YOU ARE EXECUTING THE ACTION " + actionMark + " OF " + actionsNumber);	
			System.out.println(currentAction.getActionSummaryString());
			
			// TODO if action.sutId is NULL the user have to use the sutManager and declare the support for this type		
			//if (action.getSutId() == null) 			
			//	;
			

			// TODO prima di chiamare il runworkflow ora faccio il finto upload
			// PERO' questo dovrebbe eventualmente richiederlo il sistema dopo aver avviato l'action
			/*XLabDOM xml = null;
			try {
				xml = new XLabDOM("C:/Java/workspace-indigo2/TeBES/ejb/ubl-invoice.xml", false, false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			currentSession.setMessageStore(xml.toString());*/
			
			// RUN Workflow
			currentSession = testPlanController.runWorkflow(workflow, currentSession);

			// REFRESH Workflow, actionMark and current Action
			workflow = testPlanController.readTestPlan(testPlanId).getWorkflow();		
			actionMark = workflow.getActionMark();
			
					
			report = currentSession.getReport();
			Assert.assertTrue(report.getName().contains(Report.getReportnamePrefix()));			
			Assert.assertTrue(report.getXml().getBytes().length > 1000);
			
			
			System.out.println(report.getXml());
			
			
			// TODO se lo stato della action corrente è working... interrogo la strttura dati
			if ( actionMark > actionMarkPreRun) {
				System.out.println("END ACTION: "  + actionMarkPreRun + " OF " + workflow.getActions().size());
				System.out.println();
				failuresForAction=0;
			}
			// Se actionMark == actionMarkPreRun alora la action ha fallito
			else {
				
				System.out.println("Action Failed or NOT Finished");				
				System.out.println("REQUEST INPUT TYPE TO PUT...");
				
				// TODO leggo che devo caricare QUALCOSA nel MessageStore
				// questo QUALCOSA dovrebbe essere indicato in una struttura temporanea da interrogare
				
				System.out.println();
				
				failuresForAction++;
				
			}
			


			// 1. le azioni da eseguire non sono finite
			System.out.println("jjj:" + report.getState());
			// 2. l'utente  decide di sospendere la sessione di test
			System.out.println(currentSession.getState());
			// 3. l'utente  decide di annullare la sessione di test
			
			// 4. raggiungo il numero massimo di fallimenti per la stessa action
			
			System.out.println("COUNTER: " + failuresForAction);
			if ( 	report.getState().equals(Report.getFinalState()) ||
					currentSession.getState().equals(Session.getSuspendedState()) ||
					currentSession.getState().equals(Session.getAbortedState()) ||
					(failuresForAction == Constants.COUNTER_MAX)	)	
				running = false;
			else
				currentAction = workflow.getActions().get(actionMark-1);	
			
			System.out.println();
		}
		
		Assert.assertTrue(failuresForAction < Constants.COUNTER_MAX);
		
		// TODO
		// la gestione del counter è necessaria solo se non c'è interazione con l'utente
		// oppure se l'interazione è automatica
		// in ogni caso un controllo di questo tipo per evitare loop infiniti dovrebbe esserci
		
		
		// TODO quando si chiude la sessione di test?
		// TODO closeSession
		// TODO session.setEndDateTime(endDateTime);
		//sessionController.setState(Session.getDoneState());
			
		
		//boolean updating = reportManager.updateReport(report);
			
			



		
		// TODO CICLO PER ATTENDERE RICHIESTA DI INTERAZIONE O FINE DEL WORKFLOW 
		// Prima di far partire il workflow l'utente dovrebbe visualizzare:
		// 1. lista delle action che verranno eseguite;
		// 2. prossima azione (con eventuali requisiti)
		// 3. eventuale interazione richiesta per cominciare
		
		// TODO report.setState(Report.getFinalState());
		
		
		report = sessionController.getReport(sessionId);
		Assert.assertTrue(report.getState().equals(Report.getFinalState()));
		System.out.println("REPORT");
		System.out.println(report.getState());
		System.out.println(report.getTestResult());
		
		
		// TODO chiusura della sessione di test
		// possibilità di download del report
		
		
		
		
	}
		
	
	//@Test
	public void test3_validation() throws Exception {

		ValidationController validationManagerController;
		validationManagerController = (ValidationController) WebControllersUtilities.getManager(ValidationController.CONTROLLER_NAME);
		Assert.assertNotNull(validationManagerController);	
		
		String xmlRelPathFileName = "TeBES_Artifacts/users/1/docs/ubl-invoice.xml";
		String xsdURL = "http://winter.bologna.enea.it/peppol_schema_rep/xsd/maindoc/UBL-Invoice-2.0.xsd";
		
		//ErrorMessage emList[] = null;

		// PROVAAAA
		//ValidationManagerRemote validationManagerService = JNDIServices.getValidationManagerService(); 	
		
		System.out.println("oioioioi:" + validationManagerController.nothing().toString());
		
			
	}

	@AfterClass
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
		
		
		// Get Session List
		List<Long> sessionIdList = sessionController.getSessionIdList();
		Assert.assertTrue(sessionIdList.size() > 0);
		

		
		// Cancello ogni ruolo
		for (int s=0;s<sessionIdList.size();s++) {

						
			// DELETE Role
			deleting = sessionController.deleteSession(sessionIdList.get(s));
			Assert.assertTrue(deleting);			
		}		
	}
	
	
	
}

