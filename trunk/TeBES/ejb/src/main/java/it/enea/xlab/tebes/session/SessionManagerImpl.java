package it.enea.xlab.tebes.session;

import it.enea.xlab.tebes.action.ActionManagerRemote;
import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.common.SQLQuery;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.Input;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.report.ReportDOM;
import it.enea.xlab.tebes.report.ReportManagerRemote;
import it.enea.xlab.tebes.sut.SUTManagerRemote;
import it.enea.xlab.tebes.testplan.TestPlanManagerRemote;
import it.enea.xlab.tebes.users.UserManagerRemote;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.xlab.file.XLabFileManager;
import org.xlab.utilities.XLabDates;
import org.xml.sax.SAXException;


@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SessionManagerImpl implements SessionManagerRemote {

	// Logger
	private static Logger logger = Logger.getLogger(SessionManagerImpl.class);
	
	@PersistenceContext(unitName=Constants.PERSISTENCE_CONTEXT)
	private EntityManager eM; 
	
	@EJB
	private ReportManagerRemote reportManager; 

	
	@EJB
	private UserManagerRemote userManager; 

	@EJB
	private SUTManagerRemote sutManager; 
	
	@EJB
	private TestPlanManagerRemote testPlanManager; 
	
	@EJB
	private ActionManagerRemote actionManager; 
	
	
	/**
	 * CHECK
	 * 
	 * @return	 1 if OK
	 * 			-1 one or more ID isn't a valid identifier
	 * 			-2 there isn't match testplan-sut of user
	 * 			-3 there is match testplan-sut of user but it isn't supported in tebes
	 */
	public Long check(Long userId, Long sutId, Long testPlanId) {
		
		User user = userManager.readUser(userId);
		SUT sut = sutManager.readSUT(sutId);
		TestPlan testPlan = testPlanManager.readTestPlan(testPlanId);

		if ( (user != null) && (sut != null) && (testPlan != null) ) {
			

			// Check match testplan-sut of user
			boolean matching1 = this.matchTestPlanSUT(testPlan, sut);
			
			if (!matching1)
				return new Long(-2);
			
			// Check match testplan-sut of system
			// Ciclo per ogni SUT del system alla ricerca di almeno uno compatibile
			List<SUT> systemSUTSupported = sutManager.getSystemSUTSupported();
			
			boolean matching2 = false;
			for (int i=0; i<systemSUTSupported.size();i++) {
				
				if ( this.matchTestPlanSUT(testPlan, systemSUTSupported.get(i)) )
					matching2 = true;
			}
			
			if (!matching2)
				return new Long(-3);
			
			return new Long(1);
		}
		else
			// @return -1 one or more ID isn't a valid identifier
			return new Long(-1);
	}
	
	/**
	 * CREATE Session
	 * 
	 * @return	sessionId > 0

	 * 			-4 an unexpected exception happened
	 * 			-5 report null
	 */
	public Long createSession(Long userId, Long sutId, Long testPlanId, String localization) {
		
		System.out.println("createSession ENTER");
		
		
		
		
		User user = userManager.readUser(userId);
		SUT sut = sutManager.readSUT(sutId);
		TestPlan testPlan = testPlanManager.readTestPlan(testPlanId);

			// CREATE Session
			Session session = new Session(user, testPlan, sut, localization);
			session.setCreationDateTime(XLabDates.getCurrentUTC());
			session.setLastUpdateDateTime(XLabDates.getCurrentUTC());
			session.setLocalization(localization);
			
			Long sessionId = this.createSession(session);
			session = this.readSession(sessionId);
			
			
			// CREATE Report Structure (DRAFT state by default)
			Report report;
			try {
				
				System.out.println("createSession PRE createReportForNewSession");
				
				report = reportManager.createReportForNewSession(session);
				
				System.out.println("createSession POST createReportForNewSession");
				System.out.println(report.getFullDescription());

				
			} catch (Exception e) {
				e.printStackTrace();
				return new Long(-4);
			}				
			
			
			if (report == null)
				return new Long(-5);

			// ADD Report To Session
			this.addReportToSession(report.getId(), session.getId());

			System.out.println("createSession EXIT");
			return sessionId;

	}
	
	
	private boolean matchTestPlanSUT(TestPlan testPlan, SUT sut) {
		
		boolean match = true;
		
		// The match is "true" if for each input of each action of test plan there is a sut with same:
		// 1. type 
		// 2. interaction type
		
		List<Action> actionList = testPlan.getWorkflow().getActions();
		
		Action a;
		Input input;
		List<Input> inputList;
		for (int i=0; i<actionList.size(); i++) {				
			
			a = actionList.get(i);
			inputList = a.getInputs();
			
			for (int j=0; j<inputList.size(); j++) {	
			
				input = inputList.get(j);
				
				if ( 	( !input.getType().equals(sut.getType() ) ) ||
						( !input.getInteraction().equals(sut.getInteraction().getType() ) )  ) {
							
						match = false;
				}
				else {
					input.setInteractionOK(true);
					boolean updating = actionManager.updateInput(input);
					if (!updating) {
						System.out.println("matchTestPlanSUT: INPUT UPDATING ERROR!");
						match = false;
					}
				}
			}
		}
		
		return match;
	}


	/**
	 * CREATE Session
	 * If there isn't Session with these user, testplan and sut, it creates the new Session
	 * @return 	sessionID if created
	 * 			-1 otherwise
	 */
	private Long createSession(Session session) {

			eM.persist(session);		
			
			return session.getId();
	}
	
	
	/**
	 * READ Session
	 */	
	public Session readSession(Long id) {
		
		return eM.find(Session.class, id);
	}	
		
		
	
	/**
	 * READ Session List by userId
	 * @return 	Session List
	 */	
	@SuppressWarnings("unchecked")
	public List<Session> readSessionListbyUserId(Long userId) {
		
        String param1 = "1";
        String queryString = SQLQuery.SELECT_SESSIONS.concat(SQLQuery.WHERE_USERID).concat(param1);
        		
        Query query = eM.createQuery(queryString);
        query.setParameter(param1, userId);

        return query.getResultList();
	}
		
	
	/**
	 * DELETE Session
	 */
	public Boolean deleteSession(Long sessionID) {

		
		System.out.println("SessionManagerImpl:" + sessionID.toString());
		Session session = this.readSession(sessionID);
		
		if (session == null)
			return false;
		
		try {
			System.out.println("SessionManagerImpl:" + session.getCreationDateTime());
			
			session.getUser().getSessions().remove(session);
			
			eM.remove(session);
			System.out.println("SessionManagerImpl: post remove");
		} catch (IllegalArgumentException e) {
			return false;
		} catch (Exception e2) {
			System.out.println("SessionManagerImpl: Exc e2");
			e2.printStackTrace();
			return null;
		}
		
		return true;
	}

	
	public void addReportToSession(Long reportId, Long sessionId) {
		
		Report report = reportManager.readReport(reportId);
		Session session = this.readSession(sessionId);

		session.setReport(report);
		
		eM.merge(session);
		
		return;
	}


	public List<Long> getSessionIdList(User user) {

        String param1 = "1";
        String queryString = SQLQuery.SELECT_SESSIONID.concat(SQLQuery.WHERE_USER).concat(param1);
          
        Query query = eM.createQuery(queryString);
        query.setParameter(param1, user);
        
        @SuppressWarnings("unchecked")
		List<Long> sessionIdList = query.getResultList();

        return sessionIdList;
	}

	public Boolean updateSession(Session session) {

		Boolean result = false;
		
		 if ( (session != null) && (session.getId() > 0) ) {
			 
			 session = eM.merge(session);
			 
			 if (session != null) {
				 result = true;
				 logger.debug("UPDATED session with ID " + session.getId() + " and state: " + session.getState());
			 }
		 }
		
		return result;
	}

	
	// RUN WORKFLOW
		// Il controllo sui cambiamenti di stato della sessione, sono qui
		public Session runWorkflow(ActionWorkflow workflow, Session session) {
			
			
			logger.info("START runWorkflow - Session State: " + session.getState());	
			
			boolean updating;
			
			// Session NEW diventa WORKING
			if (session.isStateNew()) {
				
				session.setStateToWorking();
				updating = this.updateSession(session);
				if (updating)
					logger.info("Session State changed to WORKING");					
				else
					logger.error("ERROR in the Session State updating to WORKING");
			}			
			
			
			
			
			Report report = session.getReport();
			
					
			// io credo dovrei fare una ricerca del tipo readActionByWorkflowId
			// eseguire questa action e una volta eseguita, eliminarla dal DB o settarla come "done"
			List<Action> actionList = workflow.getActions();
			int actionListSize = workflow.getActions().size();

			
			int actionMark = workflow.getActionMark(); 
			logger.info("START runWorkflow - actionMark: " + actionMark);
			
			Action currentAction = (Action) actionList.get(actionMark-1);
			logger.info("START runWorkflow - actionList.get: " + currentAction.getActionId());
			
			// Se l'action è nello stato NEW
			Boolean isReady = false;
			if ( currentAction.isStateNew() ) {	
				isReady = actionManager.checkActionReady(currentAction);
				
				if (isReady) {
					
					currentAction = actionManager.readAction(currentAction.getId());
				}
				else {
					session.setStateToWaiting();
					updating = this.updateSession(session);
					if (updating)
						logger.info("Session State changed to WAITING");					
					else
						logger.error("ERROR in the Session State updating to WAITING");	
				}
			}
				
				
			// Se l'action è nello stato READY
			if ( currentAction.isStateReady() ) {

				// Se la sessione non è settata a Working la setto
				if ( !session.isStateWorking() ) {
					
					session.setStateToWorking();
					updating = this.updateSession(session);
								
					if (updating)
						logger.info("Session State changed to WORKING");					
					else
						logger.error("ERROR in the Session State updating to WORKING");
				}
				
				
				//////////////////
				/// RUN ACTION ///
				//////////////////
				report = actionManager.runAction(currentAction, session);
				report.setFinalResultSuccessfully(report.isFinalResultSuccessfully() && report.isPartialResultSuccessfully());
				
				/* 
				 * questo IF in questo punto implica che l'Action deve per forza finire in uno stato di successo
				 * altrimenti non è possibile passare alla successiva 
				 * 
				 * if ( report.isPartialResultSuccessfully() ) {
					
					actionMark++;				
					workflow.setActionMark(actionMark);
				}
				*/
				actionMark++;				
				workflow.setActionMark(actionMark);
				
				// Se questa era l'ultima azione il Report è concluso
				// e la sessione di Test termina con state DONE
				if (actionMark > actionListSize) {
					
					// Report state
					report.setState(Report.getFinalState());	
					
					try {
						
						ReportDOM reportDOM = new ReportDOM(report.getLocation());
						reportDOM.setHeaderState(Report.getFinalState());
						reportDOM.setSessionLastUpdateDateTime(XLabDates.getCurrentUTC());
						reportDOM.save();
						report.setXml(reportDOM.getXMLString());
						session.setReport(report);
										
						// Log
						String log = "*** LOG FILE FOR SESSION: " + session.getId() + " ***\n";
						XLabFileManager.create(report.getLogLocation(), log.concat(report.getFullDescription()));
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
					// Session state
					session.setStateToDone();
					updating = this.updateSession(session);
								
					if (updating)
						logger.info("Session State changed to DONE");					
					else
						logger.error("ERROR in the Session State updating to DONE");
					
				}
				
				// Updating workflow and Session
				updating = actionManager.updateWorkflow(workflow);	
				//updating = updating && reportManager.updateReport(report);
	
				session.setReport(report);
				updating = updating && updateSession(session);				
				
				session = readSession(session.getId());
				
			} // End if READY_STATE
			
			return session;
		}

		
		// Suspend Session
		public Session suspendSession(Session session) {
		
			// If session state ISN'T CANCELLED or DONE			
			if (!session.isStateCancelled() && !session.isStateDone()) { 
				
				// Set Report state to Draft
				// non serve!!! è già draft!
				//session.getReport().setState(Report.getDraftState());	
				
				// Set Session state to SUSPENDED
				session.setStateToSuspended();
				
				// Updating
				boolean updating = this.updateSession(session);						
				if (updating)
					logger.info("Session State changed to SUSPEND");					
				else
					logger.error("ERROR in the Session State updating to SUSPEND");
				
				// Refresh Session
				session = readSession(session.getId());
			}
			
			return session;
		}

		public Session annulSession(Session session) {
			
			// If session state ISN'T CANCELLED or DONE			
			if (!session.isStateCancelled() && !session.isStateDone()) { 
			
				// Report state
				Report report = session.getReport(); 
				report.setState(Report.getFinalState());
				
				
				

				try {
					// Report
					ReportDOM reportDOM = new ReportDOM(report.getLocation());
					reportDOM.setHeaderState(Report.getFinalState());
					reportDOM.setSessionLastUpdateDateTime(XLabDates.getCurrentUTC());
					reportDOM.save();
					report.setXml(reportDOM.getXMLString());
					session.setReport(report);
					
					// Log
					XLabFileManager.create(report.getLogLocation(), report.getFullDescription());
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				// Session state
				session.setStateToCancelled();
				
				// Updating
				boolean updating = this.updateSession(session);						
				if (updating)
					logger.info("Session State changed to CANCELLED");					
				else
					logger.error("ERROR in the Session State updating to CANCELLED");
				
				// Refresh Session
				session = readSession(session.getId());
			}
			return session;
		}

		
		public Session resumeSession(Session session) {
			
			// If session state is SUSPENDED			
			if (session.isStateSuspended()) {
				
				// Set Session State to WORKING
				session.setStateToWorking();
			
				// Updating
				boolean updating = this.updateSession(session);						
				if (updating)
					logger.info("Session State changed to WORKING");					
				else
					logger.error("ERROR in the Session State updating to WORKING");
				
				// Refresh Session
				session = readSession(session.getId());
			}
			
			return session;
		}

		// GET Report URL
		public String getReportURL(Session session) {

			String reportFileName = session.getReport().getName().concat(Constants.XML_EXTENSION);	

			return PropertiesUtil.getUserReportURL(session.getUser().getId(), reportFileName);
		}
		
}




