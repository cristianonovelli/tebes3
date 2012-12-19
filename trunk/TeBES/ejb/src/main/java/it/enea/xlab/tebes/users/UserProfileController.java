package it.enea.xlab.tebes.users;

import it.enea.xlab.tebes.entity.User;
import javax.naming.InitialContext;

public class UserProfileController {

	private UserManagerRemote userManagerBean;
	
	
	
	public UserProfileController() throws Exception {

		InitialContext ctx = new InitialContext();
		userManagerBean = (UserManagerRemote) ctx.lookup("TeBES-ear/UserManagerImpl/remote");
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


