package it.enea.xlab.tebes.controllers.session;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.session.SessionManagerRemote;

import java.rmi.NotBoundException;
import java.util.List;

import javax.naming.NamingException;

public class SessionManagerController extends WebController {

	public static final String CONTROLLER_NAME = "SessionManagerController";
	
	private SessionManagerRemote sessionManagerBean;
	


	public SessionManagerController() {
				
	}

	
	public void initContext() throws NotBoundException, NamingException {
		
		sessionManagerBean = JNDIServices.getSessionManagerService();
	}
	
	
	public Session getSession(Long sessionId) {
		
		return sessionManagerBean.readSession(sessionId);
	}

	/*public List<Session> getSessionList(Long userId) {
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


	public Report getReport(Long sessionId) {
		
		return this.getSession(sessionId).getReport();
	}


	public List<Long> getSessionIdList() {
		
		return sessionManagerBean.getSessionIdList();
	}


	public Boolean deleteSession(Long id) {
		
		return sessionManagerBean.deleteSession(id);
	}

}

