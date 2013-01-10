package it.enea.xlab.tebes.users;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.entity.User;

public class UserProfileController {

	private UserManagerRemote userManagerBean;
	

	public UserProfileController() throws Exception {

		int guard = 0;
		
		// To avoid Remote Not Bound exception, we retry to lockup for 5 attempts
		// waiting 2 seconds between an attempt and another
		while (guard < 5) {
			try {
				
				// GET SERVICE
				userManagerBean = JNDIServices.getUserManagerService();
				
				guard = 5;
			} catch (Exception e) {
				
				guard++;
				System.out.println("REMOTE NOT BOUND for UserAdminController. Try " + guard + " of 5");				
				wait(2000);
			}
		}
				
	}

	
	/**
	 * Sign Up (CREATE) User
	 * When a user signes up, the system assign him the standard role with level 1
	 * @return userId
	 */
	public Long signUp(User user) {
		
		// Eventuali Controlli
		// TODO
		
		// Set default standard Role
		// TODO

		return userManagerBean.createUser(user);
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
		
		//User user = this.readUser(userId);
		
		//SUTManagerController sutManagerController = new SUTManagerController();
		//SUT sut = sutManagerController.readSUT(sutId);
		
		userManagerBean.addSUTToUser(sutId, userId);
	}





















	

}


