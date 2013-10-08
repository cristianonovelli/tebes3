package it.enea.xlab.tebes.session;

import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.User;

import java.util.List;
import javax.ejb.Remote;

@Remote
public interface SessionManagerRemote {


	// Read Session
	public Session readSession(Long sessionId);
	// public List<Session> readSessionListbyUserId(Long userId);


	public Long createSession(Long userId, Long sutId, Long testPlanId);

	public List<Long> getSessionIdList(User superUser);

	public Boolean deleteSession(Long id);

	public Long check(Long userId, Long sutId, Long testPlanId);

	public Boolean updateSession(Session session);


	public Session runWorkflow(ActionWorkflow workflow, Session session);

	public Session suspendSession(Session session);

	public Session annulSession(Session session);

	public Session resumeSession(Session session);


	public String getReportURL(Session session);
}
