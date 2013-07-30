package it.enea.xlab.tebes.controllers.users;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.users.UserManagerRemote;

import java.rmi.NotBoundException;
import java.util.List;

import javax.naming.NamingException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class UserProfileController extends WebController<User> {

	private UserManagerRemote userManagerBean;
	
	public static final String CONTROLLER_NAME = "UserProfileController";

	
	public UserProfileController() {
	
	}

	
	public void initContext() throws NotBoundException, NamingException {
		
		// GET SERVICE
		userManagerBean = JNDIServices.getUserManagerService(); 		
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


	
	// ADD SUT TO USER
	// TODO questa dovrebbe stare nel sut manager
	/*public void addSUTToUser(Long sutId, Long userId) throws Exception {

		userManagerBean.addSUTToUser(sutId, userId);
	}*/


/*	public Boolean deleteTestPlan(Long userId, Long testPlanId) {
		
		return userManagerBean.deleteTestPlan(userId, testPlanId);
	}*/


	

}


