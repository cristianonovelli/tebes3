package it.enea.xlab.tebes.session;

import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.Session;

import java.util.List;

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

	@PersistenceContext(unitName="TeBESPersistenceLayer")
	private EntityManager eM; 
	
	
	/**
	 * CREATE Session
	 * If there isn't Session with these user, testplan and sut, it creates the new Session
	 * @return 	sessionID if created
	 * 			-1 otherwise
	 */
	public Long createSession(Session session) {

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
	 * UPDATE Session
	 */
	public Boolean updateSession(Long sessionID) {
		
		Boolean result = false;
		
		Session session = readSession(sessionID);
		
		 try {
			 if ( (session != null) && (session.getId() != null) ) {
				 session = eM.merge(session);
				 
				 if (session != null)
					 result = true;
			 }
			 
		} catch (IllegalArgumentException e) {
			result = false;
		} catch (Exception e2) {
			result = null;
		}
		 
		 return result;
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
