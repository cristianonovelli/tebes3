package it.enea.xlab.tebes.authentication;

import javax.ejb.Remote;

import it.enea.xlab.tebes.entity.User;

@Remote
public interface AuthenticationManager {

	public User getUser(String username);
	
	public boolean isAdmin(String username);
	
}
