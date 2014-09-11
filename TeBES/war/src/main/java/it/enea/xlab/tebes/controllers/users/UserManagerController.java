package it.enea.xlab.tebes.controllers.users;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.common.SUTConstants;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.SUTInteraction;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.sut.SUTManagerRemote;
import it.enea.xlab.tebes.users.UserManagerRemote;
import it.enea.xlab.tebes.utils.FormMessages;
import it.enea.xlab.tebes.utils.UserUtils;

import java.rmi.NotBoundException;
import java.util.List;

import javax.naming.NamingException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class UserManagerController extends WebController<User> {

	private static final long serialVersionUID = 1L;
	public static final String CONTROLLER_NAME = "UserManagerController";
	
	private UserManagerRemote userManagerService;
	private SUTManagerRemote sutManagerService;
	
	private User user;
	private String confirmPassword;
	private String userFormMessage;
	private boolean showUserFormMessage;
	
	public UserManagerController() throws NamingException {
		userManagerService = JNDIServices.getUserManagerService(); 
		sutManagerService = JNDIServices.getSUTManagerService();
	}

	public void initContext() throws NotBoundException, NamingException {
		// GET SERVICE
		userManagerService = JNDIServices.getUserManagerService(); 		
		sutManagerService = JNDIServices.getSUTManagerService();
	}
	
	// LOGIN
	public User login(String email, String password) {
		return userManagerService.readUserbyEmailAndPassword(email, password);
	}
	
	// ADD SUT TO USER
	public void addSUTToUser(Long sutId, Long userId) throws Exception {
		
		//User user = this.readUser(userId);
		
		//SUTManagerController sutManagerController = new SUTManagerController();
		//SUT sut = sutManagerController.readSUT(sutId);
		
		userManagerService.addSUTToUser(sutId, userId);
	}

	// READ User
	public User readUser(Long id) {
		return userManagerService.readUser(id);
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
			
			Role role = this.userManagerService.readRole(Constants.STANDARD_ROLE_NAME);
			if(role != null)
				newUser.setRole(role);

			Long result = this.userManagerService.createUser(newUser);
			if (result > 0) {
				
				// Create default SUT
				SUTInteraction interactionWebSite = new SUTInteraction(SUTInteraction.WEBSITE);
				SUT defaultSUT = new SUT("DefaultSUT", SUTConstants.SUT_TYPE1_DOCUMENT, interactionWebSite, "Default SUT Document-WebSite for User " + user.getSurname());				
				user = this.userManagerService.readUser(result);					
				sutManagerService.createSUT(defaultSUT, user);
				
			} else if(result == -1) {
				this.userFormMessage = FormMessages.getErrorUserAlreadyExisting();						
				this.showUserFormMessage = true;
				return "creation_fail";
			} else if(result == -2) {
				this.userFormMessage = FormMessages.getErrorUserCreation();
				this.showUserFormMessage = true;
				return "creation_fail";
			}
			this.resetFields();
			this.userFormMessage = FormMessages.getUserCreationSuccess();
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
