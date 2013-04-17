package it.enea.xlab.tebes.controllers.session;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.session.SessionManagerRemote;

import java.rmi.NotBoundException;

import javax.naming.NamingException;

public class SessionManagerController extends WebController {

	public static final String CONTROLLER_NAME = "SessionManagerController";
	
	private SessionManagerRemote sessionManagerBean;
	


	public SessionManagerController() {
				
	}

	
	public void initContext() throws NotBoundException, NamingException {
		
		sessionManagerBean = JNDIServices.getSessionManagerService();
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

