package it.enea.xlab.tebes.users;

import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.sut.SUTManagerController;

import javax.naming.InitialContext;

public class UserManagerController {

	private UserManagerRemote userManagerBean;
	
	
	
	public UserManagerController() throws Exception {

		InitialContext ctx = new InitialContext();
		userManagerBean = (UserManagerRemote) ctx.lookup("TeBES-ear/UserManagerImpl/remote");
	}

	
	// LOGIN
	public User login(String email, String password) {

		return userManagerBean.readUserbyEmailAndPassword(email, password);
	}
	
	// ADD SUT TO USER
	public void addSUTToUser(Long sutId, Long userId) throws Exception {
		
		//User user = this.readUser(userId);
		
		//SUTManagerController sutManagerController = new SUTManagerController();
		//SUT sut = sutManagerController.readSUT(sutId);
		
		userManagerBean.addSUTToUser(sutId, userId);
	}

	// READ User
	public User readUser(Long id) {
		
		return userManagerBean.readUser(id);
	}
	

}
