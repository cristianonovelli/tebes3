package it.enea.xlab.tebes.controllers.users;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.users.UserManagerRemote;

import java.rmi.NotBoundException;

import javax.naming.NamingException;

public class UserManagerController extends WebController {

	public static final String CONTROLLER_NAME = "UserManagerController";
	
	private UserManagerRemote userManagerBean;
	
	
	
	public UserManagerController() {

	}

	
	public void initContext() throws NotBoundException, NamingException {
		
		// GET SERVICE
		userManagerBean = JNDIServices.getUserManagerService(); 		
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
