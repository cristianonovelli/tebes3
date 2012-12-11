package it.enea.xlab.tebes.session;

import it.enea.xlab.tebes.entity.Session;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface SessionManagerRemote {
	
	// Create Session
	public Long createSession(Session session);
	
	// Read Session
	public Session readSession(Long sessionId);
	public List<Session> readSessionListbyUserId(Long userId);
	
	// Update Session
	public Boolean updateSession(Long sessionId);
	
	// Delete Session
	public Boolean deleteSession(Long sessionId);

}
