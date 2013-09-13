package it.enea.xlab.tebes.users;

import java.util.List;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;

public class UserProfileController {

	private UserManagerRemote userManagerBean;
	

	public UserProfileController() throws Exception {

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

	
	/**
	 * Sign Up (CREATE) User
	 * When a user signes up, the system assign him the standard role with level 1
	 * 
	 * @return 	id of User if created
	 * 			-1 if already a User with that email exists
	 * 			-2 if an exception occurs
	 * 			-3 if the Role isn't standard
	 */
	public Long registration(User user, Role role) {
		
		Long userId;
		
		if (role.getLevel() == Constants.STANDARD_ROLE_LEVEL) {
		
			userId = userManagerBean.createUser(user);
		
			if (userId > 0) {

				try {
					
					user = this.login(user.geteMail(), user.getPassword());

					userManagerBean.setUserRole(user, role);
					
				} catch (Exception e) {					
					return new Long(-2);
				}
			}
		}
		else 
			userId = new Long(-3);
		
		return userId;
	}
	
	
	
	// LOGIN
	public User login(String email, String password) {

		return userManagerBean.readUserbyEmailAndPassword(email, password);
	}
	
	
	// UPDATE
	public Boolean updateUser(User user) {

		// Eventuali Controlli
		
		return userManagerBean.updateUser(user);
	}


	
	// ADD SUT TO USER
	// TODO questa dovrebbe stare nel sut manager
	public void addSUTToUser(Long sutId, Long userId) throws Exception {

		userManagerBean.addSUTToUser(sutId, userId);
	}

	

}


