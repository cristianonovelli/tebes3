package it.enea.xlab.tebes.users;

import it.enea.xlab.tebes.entity.User;

import javax.naming.InitialContext;

public class UserManagerController {

	private UserManagerRemote userManagerBean;
	
	
	
	public UserManagerController() throws Exception {

		InitialContext ctx = new InitialContext();
		userManagerBean = (UserManagerRemote) ctx.lookup("TeBES-ear/UserManagerImpl/remote");
	}


	public User login(String email, String password) {

		return userManagerBean.readUserbyEmailAndPassword(email, password);
	}

}
