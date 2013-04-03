package it.enea.xlab.tebes.session;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.entity.Session;

import java.util.List;

public class SessionManagerController implements SessionManagerRemote {

	private SessionManagerRemote sessionManagerBean;
	


	public SessionManagerController() throws Exception {

		int guard = 0;
		
		// To avoid Remote Not Bound exception, we retry to lockup for 5 attempts
		// waiting 2 seconds between an attempt and another
		while (guard < 5) {
			try {
				
				// GET SERVICE
				sessionManagerBean = JNDIServices.getSessionManagerService();
				
				guard = 5;
			} catch (Exception e) {
				
				guard++;
				System.out.println("REMOTE NOT BOUND for UserAdminController. Try " + guard + " of 5");				
				wait(2000);
			}
		}
				
	}
	
	public Session readSession(Long sessionId) {
		
		return sessionManagerBean.readSession(sessionId);
	}

	/*public List<Session> readSessionListbyUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}*/

	
/*	public Session reactivateSession(Long sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean deleteSession(Long sessionId) {
		// TODO Auto-generated method stub
		return null;
	}*/

	public Long run(Long userId, Long sutId, Long testPlanId) {
		
		return sessionManagerBean.run(userId, sutId, testPlanId);
	}

}

