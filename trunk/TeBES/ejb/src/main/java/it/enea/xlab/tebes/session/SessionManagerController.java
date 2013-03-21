package it.enea.xlab.tebes.session;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.entity.Session;

import java.util.List;

public class SessionManagerController implements SessionManagerRemote {

	private SessionManagerRemote sessionManagerBean;
	
	public SessionManagerController() throws Exception {

		sessionManagerBean = JNDIServices.getSessionManagerService();
	}

	public Session reactivateSession(Long sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long createSession(Session session) {
		
		return sessionManagerBean.createSession(session);
	}

	public Session readSession(Long sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Session> readSessionListbyUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean deleteSession(Long sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

}

