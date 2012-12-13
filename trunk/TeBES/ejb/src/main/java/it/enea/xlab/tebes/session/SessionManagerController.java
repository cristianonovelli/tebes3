package it.enea.xlab.tebes.session;

import it.enea.xlab.tebes.entity.Session;

import java.util.List;

import javax.naming.InitialContext;

public class SessionManagerController implements SessionManagerRemote {

	private SessionManagerRemote sessionManagerBean;
	
	public SessionManagerController() throws Exception {

		InitialContext ctx = new InitialContext();
		sessionManagerBean = (SessionManagerRemote) ctx.lookup("TeBES-ear/SessionManagerImpl/remote");
	}

	public Session reactivation(Long sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long createSession(Session session) {
		// TODO Auto-generated method stub
		return null;
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

