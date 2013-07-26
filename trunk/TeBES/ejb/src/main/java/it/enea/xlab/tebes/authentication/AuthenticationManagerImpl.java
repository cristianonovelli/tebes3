package it.enea.xlab.tebes.authentication;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.users.UserManagerRemote;

@Stateless(name="AuthenticationManagerImpl")  
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AuthenticationManagerImpl implements AuthenticationManager {

	private final String ADMIN_ROLE = "admin_user";
	
	@PersistenceContext(unitName=Constants.PERSISTENCE_CONTEXT) 
	private EntityManager eM; 
	
	private UserManagerRemote userManagerBean;
	
	public AuthenticationManagerImpl() throws Exception {
		int guard = 1;
		
		// To avoid Remote Not Bound exception, we retry to lockup for 5 attempts
		// waiting 2 seconds between an attempt and another
		while (guard < 5) {
			try {
				
				// GET SERVICE
				userManagerBean = JNDIServices.getUserManagerService(); 
				
				guard = 5;
			} catch (Exception e) {
				
				System.out.println("REMOTE NOT BOUND for UserAdminController. Try " + guard + " of 5");				
				Thread.sleep(2000);
				guard++;
			}
		}
	}
	
	public User getUser(String username) {
		
		List<User> users = userManagerBean.readUsersByEmail(username);
		if(users != null)
			return users.get(0);
		else 
			return null;
	}

	public boolean isAdmin(String username) {
		
		List<User> users = userManagerBean.readUsersByEmail(username);
		Role userRole = null;
		
		if(users != null) {
			userRole = users.get(0).getRole();
			
			if(userRole.getName().equals(ADMIN_ROLE))
				return true;
		}
		
		return false;
	}

}
