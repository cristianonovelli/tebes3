package it.enea.xlab.tebes.session;

import it.enea.xlab.tebes.entity.Session;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface SessionManagerRemote {

	// Reactivation (Ripristino)
	//public Session reactivateSession(Long sessionId);
	
	// Create Session
	//public Long createSession(Session session);
	
	// Read Session
	public Session readSession(Long sessionId);
	// public List<Session> readSessionListbyUserId(Long userId);
	
	// Delete Session
	//public Boolean deleteSession(Long sessionId);

	public Long run(Long userId, Long sutId, Long testPlanId);

}