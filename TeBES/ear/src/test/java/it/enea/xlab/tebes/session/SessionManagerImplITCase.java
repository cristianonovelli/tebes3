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
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.SUTInteraction;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.utilities.WebControllersUtilities;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xlab.file.XLabFileManager;
import org.xlab.xml.JXLabDOM;


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

		fileController = (FileManagerController) WebControllersUtilities.getManager(FileManagerController.CONTROLLER_NAME);
		Assert.assertNotNull(fileController);
		
		
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
		superUser = userAdminController.readUser(superUserId);
		Assert.assertTrue(superUserId.intValue()>0);	
		
		// Create superuser SUTs (SUTs supported by TeBES)
		SUTInteraction interactionWebSite = new SUTInteraction(SUTConstants.INTERACTION_WEBSITE);
		SUTInteraction interactionEmail = new SUTInteraction(SUTConstants.INTERACTION_EMAIL);
		SUTInteraction interactionWSClient = new SUTInteraction(SUTConstants.INTERACTION_WS_CLIENT);
		SUTInteraction interactionWSServer = new SUTInteraction(SUTConstants.INTERACTION_WS_SERVER);
		
		
		Vector<SUT> sutList = new Vector<SUT>();
		
		// SUT supportati per il tipo "document"
		sutList.add( new SUT("SystemSUT1", SUTConstants.SUT_TYPE1_DOCUMENT, interactionWebSite, "System SUT 1: Document - WebSite") );
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
		for (int i=0; i<sutList.size();i++) {
		
			sutId = sutController.createSUT(sutList.elementAt(i), superUser);
			Assert.assertNotNull(sutId);	
			Assert.assertTrue(sutId.intValue()>0);			
		}
		
		// Create 2 temporary Users
		User currentUser = new User(Constants.USER1_NAME, Constants.USER1_SURNAME, Constants.USER1_EMAIL, Constants.USER1_PASSWORD);	
		Long idTempUser1 = userProfileController.registration(currentUser, role1_standard);
		Assert.assertNotNull(currentUser);
		Assert.assertTrue(idTempUser1.intValue()>0);
		User otherUser = new User(Constants.USER2_NAME, Constants.USER2_SURNAME, Constants.USER2_EMAIL1, Constants.USER2_EMAIL1);
		Long idTempUser2 = userProfileController.registration(otherUser, role1_standard);
		Assert.assertNotNull(currentUser);
		Assert.assertTrue(idTempUser2.intValue()>0);
		
		// Login SuperUser
		superUserEmail = PropertiesUtil.getUser1Email();
		superUserPassword = PropertiesUtil.getUser1Password();
		superUser = userProfileController.login(superUserEmail, superUserPassword);
		superUserId = superUser.getId();
		superUser = userAdminController.readUser(superUserId);
		
		// Importazione dei Test Plan XML per lo SuperUser (gli utenti li importeranno poi da lui)
		// N.B. questa operazione viene fatta in fase di setup del sistema
		boolean importing = testPlanController.importSystemTestPlanFile(superUser);
		Assert.assertTrue(importing);
	}
	
	
	
	
	
	
	@Test
	public void test_session() {

		
		// USER 
		User currentUser = userProfileController.login(Constants.USER1_EMAIL, Constants.USER1_PASSWORD);
		Long currentUserId = currentUser.getId();
		Assert.assertTrue(currentUser != null);
		Assert.assertTrue(currentUser.getId().intValue() > 0);
		logger.info("Login of user " + currentUser.getName() + " " + currentUser.getSurname() + " with id: " + currentUserId);
		
		
		// TESTPLAN
		
		// Lista dei TestPlan disponibili nel sistema	
		List<TestPlan> systemTestPlanList = testPlanController.getSystemTestPlanList();
		Assert.assertTrue(systemTestPlanList.size()>0);
		
		TestPlan testPlan = null;
		Long testPlanId;
		
		// Per ogni TestPlan del sistema... lo verifico
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
		
		// Selezione di un TestPlan generico per l'utente (currentUser)
		TestPlan selectedTestPlan = systemTestPlanList.get(0);
		Assert.assertNotNull(selectedTestPlan);

		// Copia e importazione del TestPlan scelto per l'utente (currentUser)
		testPlanId = testPlanController.cloneTestPlan(selectedTestPlan, currentUserId);
		Assert.assertTrue(testPlanId.intValue()>0);			
		logger.info("Selected TestPlan " + selectedTestPlan.getName() + " for user " + currentUser.getName() + " " + currentUser.getSurname());
		
		
		//  SUT
		
		// 1. Recupero lista dei SUT Type direttamente dall'oggetto SUT
		Vector<String> systemSUTTypeList = sutController.getSUTTypeList();
		
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
			
		// 6. inserisce una descrizione
		String sutDescription = "XML document1 uploaded by email";

		// 7. Creo SUT e lo persisto per l'utente corrente
		SUT sut = new SUT("SystemSUT1-1", defaultSUTType, interaction4User, sutDescription);
		Long sutId = sutController.createSUT(sut, currentUser);
		Assert.assertNotNull(sutId);				
		Assert.assertTrue(sutId.intValue()>0);	
		logger.info("Selected SUT " + sut.getName() + " for user " + currentUser.getName() + " " + currentUser.getSurname());

		

		// Nel momento in cui l'utente avvia il test
		// il sistema, nel controller, effettua prima una verifica di consistenza ( check() )
		// verificando che Test Plan selezionato e SUT di default siano compatibili
		// 1. tra loro
		// 2. con quanto supportato dal sistema
		// SE questa funzione ha successo si passa alla createSession()
		// ALTRIMENTI è necessario specificare/creare un altro SUT da abbinare gli input "inconsistenti"
		
		Long sessionId = sessionController.createSession(currentUserId, sutId, testPlanId);
		Assert.assertNotNull(sessionId);
		System.out.println("sessionId:" + sessionId);
		Assert.assertTrue(sessionId.intValue()>0);		
		logger.info("Create Session with id " + sessionId + " for user " + currentUser.getName() + " " + currentUser.getSurname());
	

		// A questo punto, ho avviato la sessione di test per la tripla (utente, sut, testplan)
		// L'esecuzione del workflow non è ancora partita
		
		// L' UTENTE, AVVIATA LA SESSIONE, PUO' FARE POLLING PER  
		// 1 MONITORARE ESECUZIONE ACTIONS
		// 2 RISPONDERE A UNA RICHIESTA DI INTERAZIONE
		// 3 OTTENERE IL REPORT DI OUTPUT (totale o parziale) DEI TEST 
		
		
		// GET Current Session
		Session currentSession = sessionController.readSession(sessionId);
		Assert.assertNotNull(currentSession);
		Assert.assertTrue(currentSession.getId().intValue() > 0);

		Assert.assertNotNull(selectedTestPlan);
		Assert.assertTrue(selectedTestPlan.getId().intValue() > 0);
		
		// Check, provo a prendere un valore dall'oggetto sut contenuto in session
		Assert.assertTrue(currentSession.getSut().getInteraction().getType().equals(SUTConstants.INTERACTION_WEBSITE));
		
		

		
		// Definisco un contatore per evitare che la stessa Action si ripeta all'infinito
		int failuresForAction = 0;
		
		Report report;
		ActionWorkflow workflow;
		Action currentAction;
		
		
		
		// GET Workflow
		workflow = testPlanController.readTestPlan(testPlanId).getWorkflow();	
		
		int actionsNumber = workflow.getActions().size();
		int indexTemp;
		
		// Definisco due actionMark:
		// actionMark aggiornato
		int actionMark = workflow.getActionMark();
		// actionMarkPreRun continua a conservare lo stato prima dell'esecuzione della action
		// questo ci serve per capire se è variato o meno (varia se l'action è stata eseguita)
		int actionMarkPreRun;
		// all'inizio entrambi i marker sono settati a 1 (prima action)
		Assert.assertTrue(actionMark == 1);
		
		// Get First Action to Execute
		currentAction = workflow.getActions().get(actionMark - 1);
		
		
		
		// START EXUCUTION WORKFLOW CYCLE
		// CICLO finchè:
		// 1. le azioni da eseguire non sono finite
		// 2. l'utente non decide di sospendere la sessione di test
		// 3. l'utente non decide di annullare la sessione di test
		// 4. non raggiungo il numero massimo di fallimenti per la stessa action
		boolean running = true;
		while (running) {
		
			actionMarkPreRun = actionMark;
			
			logger.info("Running Workflow... ACTION " + actionMark + " OF " + actionsNumber);	
			logger.info(currentAction.getActionSummaryString());
			
			
			

			// PER ORA FACCIO
			// 1. Faccio l'upload
			// 2. salvo il file nel FileManager
			// 3. lo uso nel mio test
			
			// POI FARO'
			// 1. vedo quanti input ho per questa action
			// 2. carico gli input necessari
			// 3. li uso nel mio test
			
			
			
			
			// 1. UPLOAD
			// a livello di Test passo il file al FileController 
			// che verrà modificato in sede di definizione dell'interfaccia da EPOCA che deciderà il modo migliore per caricarlo
			// TODO il file deve essere salvato nella locazione utente
			String absUsersDirPath = PropertiesUtil.getUsersDir();
			
			String absSuperUserDocFilePath = absUsersDirPath.concat("0/docs/");
			String fileName = "ubl-invoice.xml";
			
			// TODO il controller si dovrebbe occupare di aprire il file e passarlo al metodo
			// per ora assumo che venga estratta la stringa e gli venga passata quella
			// 1. apro il file, prendo la stringa			
			// 2. Boolean uploading = fileController.upload(filename, fileContentString);
			// 3. il file viene salvato, prosegue il test con questo file impostato nella session
			// 4. alla prossima action chiedo all'utente se questo file va bene
			JXLabDOM fileXML = null;
			String fileXMLString = null;
			
			try {
				// Get XML DOM (è solo un modo per me comodo di ottenere lo "stringone")
				fileXML = new JXLabDOM(absSuperUserDocFilePath.concat(fileName), false, false);
				// Get File String
				fileXMLString = fileXML.getXMLString();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerFactoryConfigurationError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Assert.assertNotNull(fileXMLString);

			if (fileXMLString != null)
				currentSession = fileController.upload(fileName, sut.getType(), fileXMLString, currentSession);
			
			
			
			
			// TODO prima di chiamare il runworkflow è stato fatto l' upload
			// PERO' questo dovrebbe  richiederlo il sistema dopo aver avviato l'action in questo modo:
			// 1. avvio il workflow
			// 2. il sistema vede che l'action ha bisogno di un file di input (nella tabella di compatibilità sutType-inputAction)
			// 	 il sistema vede nel messagestore di questa sessione se c'è un file di input, non c'è
			// il sistema setta Session.interaction con FILE (il typo di sut in ballo)
			// 3. a questo punto, se Session.interaction = SUTRequired
			// effettuo l'upload, richiamo la stessa action e setto l'interaction a null
			
			// TODO discorso diverso quando si hanno più file
			// ci vorrebbe un file manager in cui precaricare i file e assegnarli agli input del test plan
			
			
			

			

			
			// RUN Workflow
			currentSession = testPlanController.runWorkflow(workflow, currentSession);

			
			
			
			
			
			// REFRESH Workflow, actionMark and current Action
			workflow = testPlanController.readTestPlan(testPlanId).getWorkflow();		
			actionMark = workflow.getActionMark();
			
					
			report = currentSession.getReport();
			Assert.assertTrue(report.getName().contains(Report.getReportnamePrefix()));			
			Assert.assertTrue(report.getXml().getBytes().length > 1000);
			
			
			// il report NON deve venire salvato su file
			// se l'utente lo vuole scaricare verrà creato in una location temporanea
			// lo salvo ora per monitorare l'output più agevolmente
			String absUserReportsPath = absUsersDirPath.concat("1/reports/");			
			String reportFileName = report.getName().concat(".xml");

			
			try {
				
				XLabFileManager.create(absUserReportsPath.concat(reportFileName), report.getXml());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			// TODO se lo stato della action corrente è working... interrogo la strttura dati
			if ( actionMark > actionMarkPreRun) {
				logger.info("END ACTION: "  + actionMarkPreRun + " OF " + workflow.getActions().size());
				logger.info("");
				failuresForAction=0;
			}
			// Se actionMark == actionMarkPreRun alora la action ha fallito
			else {
				
				// TODO leggo che devo caricare QUALCOSA nel MessageStore
				// questo QUALCOSA dovrebbe essere indicato in una struttura temporanea da interrogare
				
				failuresForAction++;	
				
				logger.warn("Action Failed or NOT Finished. Counter: " + failuresForAction);				
				logger.warn("REQUEST INPUT TYPE TO PUT...");
				logger.warn("");
			}
			


			// TODO
			
			// 1. le azioni da eseguire non sono finite
			
			// 2. l'utente  decide di sospendere la sessione di test
			
			// 3. l'utente  decide di annullare la sessione di test
			
			// 4. raggiungo il numero massimo di fallimenti per la stessa action
			
			
			// in ogni caso devo aggiornate gli stati di session e report
			
			

			
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

		
		// Get Role List
		//roleIdList = userAdminController.getRoleIdList();
		//Assert.assertTrue(roleIdList.size() == 0);
		
		// Last Check
		// Sono stati eliminati tutti gli utenti (a cascata)?
		userIdList = userAdminController.getUserIdList();
		//Assert.assertTrue(userIdList.size() == 1);
		
		
		// Get Session List
		List<Long> sessionIdList = sessionController.getSessionIdList();
		Assert.assertTrue(sessionIdList.size() == 0);
		

		
		// Cancello ogni ruolo
		/*for (int s=0;s<sessionIdList.size();s++) {

						
			// DELETE Role
			deleting = sessionController.deleteSession(sessionIdList.get(s));
			Assert.assertTrue(deleting);			
		}	*/	
	}
	
	
	
}

