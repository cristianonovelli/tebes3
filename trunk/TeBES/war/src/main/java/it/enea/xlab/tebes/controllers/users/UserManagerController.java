package it.enea.xlab.tebes.controllers.users;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.entity.Group;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.users.UserManagerRemote;
import it.enea.xlab.tebes.utils.Messages;
import it.enea.xlab.tebes.utils.UserUtils;

import java.rmi.NotBoundException;
import java.util.List;

import javax.naming.NamingException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class UserManagerController extends WebController<User> {

	public static final String CONTROLLER_NAME = "UserManagerController";
	
	private UserManagerRemote userManagerBean;
	
	private User user;
	private String confirmPassword;
	private String userFormMessage;
	private boolean showUserFormMessage;
	
	public UserManagerController() throws NamingException {
		userManagerBean = JNDIServices.getUserManagerService(); 
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

	@Override
	public void resetSearchParameters() {
		// TODO Auto-generated method stub
	}

	@Override
	protected List<Criterion> determineRestrictions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Order> determineOrder() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String createUser() {
		
		String message = UserUtils.checkUserFields(user.getName(), user.getSurname(), user.geteMail(), user.getPassword(), confirmPassword);
		
		if(message == null) {
			
			User newUser = new User();
			newUser.setName(user.getName());
			newUser.setSurname(user.getSurname());
			newUser.seteMail(user.geteMail());
			newUser.setPassword(user.getPassword());
			
			Role role = this.userManagerBean.readRole(Constants.STANDARD_ROLE_NAME);
			if(role != null)
				newUser.setRole(role);

			Long result = this.userManagerBean.createUser(newUser);
			if(result == -1) {
				this.userFormMessage = Messages.FORM_ERROR_USER_ALREADY_EXISTING;
				this.showUserFormMessage = true;
				return "creation_fail";
			} else if(result == -2) {
				this.userFormMessage = Messages.FORM_ERROR_USER_CREATION;
				this.showUserFormMessage = true;
				return "creation_fail";
			}
			this.resetFields();
			this.userFormMessage = Messages.FORM_USER_CREATION_SUCCESS;
			this.showUserFormMessage = true;
			return "creation_success";
			
		} else {
			this.userFormMessage = message;
			this.showUserFormMessage = true;
			return "creation_fail";
		}
	}
	
	private void resetFields() {
		user = new User();
		confirmPassword = "";
	}
	
	public User getUser() {
		if(user == null)
			user = new User();
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getUserFormMessage() {
		return userFormMessage;
	}

	public boolean getShowUserFormMessage() {
		return showUserFormMessage;
	}
	
}
