package it.enea.xlab.tebes.session;

import it.enea.xlab.tebes.action.ActionManagerRemote;
import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.report.ReportDOM;
import it.enea.xlab.tebes.report.ReportManagerRemote;
import it.enea.xlab.tebes.sut.SUTManagerRemote;
import it.enea.xlab.tebes.testplan.TestPlanDOM;
import it.enea.xlab.tebes.testplan.TestPlanManagerRemote;
import it.enea.xlab.tebes.users.UserManagerRemote;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.w3c.dom.Element;
import org.xlab.utilities.XLabDates;


@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SessionManagerImpl implements SessionManagerRemote {

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
	 * RUN / CREATE Session
	 * 
	 * @return	sessionId > 0
	 * 			-1 one or more ID isn't a valid identifier
	 * 			-2 an unexpected exception happened
	 * 			-3 report null
	 * 			-4 reportDOM null
	 */
	public Long run(Long userId, Long sutId, Long testPlanId) {
		
		User user = userManager.readUser(userId);
		SUT sut = sutManager.readSUT(sutId);
		TestPlan testPlan = testPlanManager.readTestPlan(testPlanId);

		if ( (user != null) && (sut != null) && (testPlan != null) ) {
		

			
			try {

				// CREATE Session
				Session session = new Session(userId, sutId, testPlanId);
				session.setStarteDateTime(XLabDates.getCurrentUTC());
				session.setLastDateTime(XLabDates.getCurrentUTC());
				
				Long sessionId = this.createSession(session);
				session = this.readSession(sessionId);
				
				
				// CREATE Report Structure (DRAFT state by default)
				Report report = reportManager.createReportForNewSession(session);				
				if (report == null)
					return new Long(-3);
				
				
				// When a Report empty is created
				// the system set up first information
				// and adjust the XML
				String xmlReportPathName = reportManager.getSystemXMLReportAbsPathName();
				
				ReportDOM reportDOM = null;
				
				try {

					// Get ReportDOM
					reportDOM = new ReportDOM(xmlReportPathName);			
					Element rootElement = reportDOM.root;
					
					if ( reportDOM.root != null ) {
						
						// Aggiorno XML Root
						reportDOM.setIdAttribute(rootElement, report.getId().toString());
						reportDOM.setNameAttribute(rootElement, report.getName());
						reportDOM.setDescriptionAttribute(rootElement, report.getDescription());
						reportDOM.setSessionIDAttribute(rootElement, report.getSessionID().toString());
						reportDOM.setStateAttribute(rootElement, report.getState());
						reportDOM.setDatetimeAttribute(rootElement, report.getDatetime());
						
						// Aggiorno XML Session
						reportDOM.setIdAttribute(reportDOM.getSessionElement(), report.getSessionID().toString());						
						reportDOM.setSessionStartDateTime(session.getStarteDateTime());
						reportDOM.setSessionLastDateTime(session.getLastDateTime());
						
						// Aggiorno XML User
						reportDOM.setIdAttribute(reportDOM.getUserElement(), session.getUserId().toString());
						reportDOM.setUserName(user.getName());
						reportDOM.setUserSurname(user.getSurname());

						// Aggiorno XML SUT
						reportDOM.setSUTId(session.getSutId());
						reportDOM.setSUTName(sut.getName());
						reportDOM.setSUTType(sut.getType());
						reportDOM.setSUTLanguage(sut.getLanguage());
						reportDOM.setSUTReference(sut.getReference());
						reportDOM.setSUTInteraction(sut.getInteraction().getType());						
						reportDOM.setSUTDescription(sut.getDescription());
								
						// Aggiorno XML TestPlan
						reportDOM.setTestPlanId(session.getTestPlanId());
						reportDOM.setTestPlanDatetime(testPlan.getDatetime());
						reportDOM.setTestPlanState(testPlan.getState());
						
						// TODO location deve contenere la posizione (relativa o assoluta del TP utente)
						// questo vuol dire che deve essere stato salvato da qualche parte nel momento dell'importazione
						// TODO import su file, creazione cartella utente ecc.
						reportDOM.setTestPlanReference(testPlan.getLocation());
						//reportDOM.setTestPlanReference("TEMP");
						
						reportDOM.setTestPlanDescription(testPlan.getDescription());
						

						report.setXml(reportDOM.getXMLString());
						reportManager.updateReport(report);
					}
					else
						System.out.println("XReport: " + reportDOM.getReport().getErrorMessage());						
					
				} catch (Exception e) {
					
					e.printStackTrace();
				} 

				if (reportDOM == null)
					return new Long(-4);
				
				// ADD Report To Session
				this.addReportToSession(report.getId(), session.getId());

				
				return sessionId;
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
				// @return -2 an unexpected exception happened
				return new Long(-2);
			}	

		}
		else
			// @return -1 one or more ID isn't a valid identifier
			return new Long(-1);
	}
	
	
	/**
	 * CREATE Session
	 * If there isn't Session with these user, testplan and sut, it creates the new Session
	 * @return 	sessionID if created
	 * 			-1 otherwise
	 */
	private Long createSession(Session session) {

		Session existingSession = this.readSessionByUserTestPlanAndSUT(session.getUserId(), session.getTestPlanId(), session.getSutId());
		
		if (existingSession == null) {
			eM.persist(session);		
			return session.getId();
		}
		else 
			return new Long(-1);
	}
	
	
	/**
	 * READ Session
	 */	
	public Session readSession(Long id) {
		
		return eM.find(Session.class, id);
	}	
		
	
	/**
	 * READ Session by userId, testplanId and sutId
	 * @return 	session if present
	 * 			null otherwise
	 */	
	private Session readSessionByUserTestPlanAndSUT(Long userId, Long testPlanId, Long sutId) {

        String queryString = "SELECT s FROM Session AS s WHERE s.userId = ?1 AND s.testPlanId = ?2 AND s.sutId = ?3";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, userId);
        query.setParameter(2, testPlanId);
        query.setParameter(3, sutId);
        
        @SuppressWarnings("unchecked")
		List<Session> resultList = query.getResultList();
        if ((resultList != null) && (resultList.size() > 0))
        	return (Session) resultList.get(0);
        else
        	return null;
	}
		
	
	/**
	 * READ Session List by userId
	 * @return 	Session List
	 */	
	@SuppressWarnings("unchecked")
	public List<Session> readSessionListbyUserId(Long userId) {
		
        String queryString = "SELECT s FROM Session AS s WHERE s.userId = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, userId);

        return query.getResultList();
	}
		
	
	/**
	 * DELETE Session
	 */
	public Boolean deleteSession(Long sessionID) {

		Session session = this.readSession(sessionID);
		
		if (session == null)
			return false;
		
		try {
			eM.remove(session);
		} catch (IllegalArgumentException e) {
			return false;
		} catch (Exception e2) {
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


	public List<Long> getSessionIdList() {

        String queryString = "SELECT s.id FROM Session AS s";   
        Query query = eM.createQuery(queryString);
        
        @SuppressWarnings("unchecked")
		List<Long> sessionIdList = query.getResultList();

        return sessionIdList;
	}

	
}



