package it.enea.xlab.tebes.session;

import it.enea.xlab.tebes.action.ActionManagerRemote;
import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.report.ReportManagerRemote;
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


@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SessionManagerImpl implements SessionManagerRemote {

	@PersistenceContext(unitName=Constants.PERSISTENCE_CONTEXT)
	private EntityManager eM; 
	
	@EJB
	private ReportManagerRemote reportManager; 
	
	@EJB
	private TestPlanManagerRemote testPlanManager; 
	
	@EJB
	private ActionManagerRemote actionManager; 
	
/*	*//**
	 * REACTIVATION (RIPRISTINO) Session
	 *//*
	public Session reactivateSession(Long sessionId) {
		// TODO Auto-generated method stub
		return null;
	}*/

	/**
	 * RUN / CREATE Session
	 * 
	 * @return	sessionId > 0
	 * 			-1 one or more ID isn't a valid identifier
	 * 			-2 an unexpected exception happened
	 */
	public Long run(Long userId, Long sutId, Long testPlanId) {
		
		
		if ( (userId.intValue()>0) && (userId.intValue()>0) && (userId.intValue()>0) ) {
		

			
			try {
				// Create Session JPA objest
				Session currentSession = new Session(userId, sutId, testPlanId);
				Long sessionId = this.createSession(currentSession);
				currentSession = this.readSession(sessionId);
				
				if ( (sessionId != null) && (sessionId > 0) ) {
					
					// TODO create Report
					Long reportId = reportManager.createReport(sessionId);
					
					// TODO EXE TestPlan
					TestPlan testPlan = testPlanManager.readTestPlan(testPlanId);					
					ActionWorkflow workflow = testPlan.getWorkflow();
					Boolean actionWorkflowExecutionResult = actionManager.runWorkflow(workflow, currentSession);
					
					
					
					
					
					return sessionId;
				}
				else		
					// @return -3 createSession error
					return new Long(-3);
				
				
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
	public Session readSession(Long sessionID) {
		
		return eM.find(Session.class, sessionID);
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


	
}
