package it.enea.xlab.tebes.session;

import it.enea.xlab.tebes.action.ActionManagerRemote;
import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.Input;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.report.ReportManagerRemote;
import it.enea.xlab.tebes.sut.SUTManagerRemote;
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

	 * 			-2 an unexpected exception happened
	 * 			-3 report null
	 */
	public Long createSession(Long userId, Long sutId, Long testPlanId) {
		
		User user = userManager.readUser(userId);
		SUT sut = sutManager.readSUT(sutId);
		TestPlan testPlan = testPlanManager.readTestPlan(testPlanId);

			// CREATE Session
			Session session = new Session(user, testPlan, sut);
			session.setCreationDateTime(XLabDates.getCurrentUTC());
			session.setLastUpdateDateTime(XLabDates.getCurrentUTC());
			
			Long sessionId = this.createSession(session);
			session = this.readSession(sessionId);
			
			
			// CREATE Report Structure (DRAFT state by default)
			Report report;
			try {
				
				report = reportManager.createReportForNewSession(session);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new Long(-2);
			}				
			
			
			if (report == null)
				return new Long(-3);

			// ADD Report To Session
			this.addReportToSession(report.getId(), session.getId());

			
			return sessionId;

	}
	
	
	private boolean matchTestPlanSUT(TestPlan testPlan, SUT sut) {
		
		System.out.println("START matchTestPlanSUT");
		
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
				
				/*System.out.println("matchTestPlanSUT - input.getType(): " + input.getType());
				System.out.println("matchTestPlanSUT - sut.getType(): " + sut.getType());
				System.out.println("matchTestPlanSUT - input.getInteraction(): " + input.getInteraction());
				System.out.println("matchTestPlanSUT - sut.getInteraction().getType(): " + sut.getInteraction().getType());*/
				
				if ( 	( !input.getType().equals(sut.getType() ) ) ||
						( !input.getInteraction().equals(sut.getInteraction().getType() ) )  ) {
							
						match = false;
				}
				else {
					input.setInteractionOK(true);
					boolean updating = actionManager.updateInput(input);
					if (!updating) {
						System.out.println("INPUT UPDATING ERROR!");
						match = false;
					}
				}
			}
		}
		
		System.out.println("END matchTestPlanSUT");
		
		return match;
	}


	/**
	 * CREATE Session
	 * If there isn't Session with these user, testplan and sut, it creates the new Session
	 * @return 	sessionID if created
	 * 			-1 otherwise
	 */
	private Long createSession(Session session) {

		// una sessione con questi 3 parametri è possibile
		//Session existingSession = this.readSessionByUserTestPlanAndSUT(session.getUserId(), session.getTestPlanId(), session.getSutId());
		
		//if (existingSession == null) {
			eM.persist(session);		
			return session.getId();
		//}
		//else 
		//	return new Long(-1);
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



