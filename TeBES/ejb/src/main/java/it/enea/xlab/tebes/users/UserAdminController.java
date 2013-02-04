package it.enea.xlab.tebes.users;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.User;

import java.util.List;

public class UserAdminController {

	private UserManagerRemote userManagerBean;
	
	
	
	public UserAdminController() throws Exception {

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

	
	
	// GET USER LIST
	public List<Long> getUserIdList() {
		
		return userManagerBean.getUserIdList();
	}
	
	// CREATE -> Equivalente a UserProfileManager.signUp MA ASSEGNA ANCHE UN RUOLO DIVERSO DA QUELLO DI DEFAULT
	/**
	 * Registration (CREATE) User
	 * @return userId
	 */
	public Long createUser(User user, Role role) {

		Long userId = userManagerBean.createUser(user);
		
		if (userId > 0) {
			user = this.readUser(userId);
			userManagerBean.setUserRole(user, role);
		}
		return userId;
	}	

	// READ User
	public User readUser(Long id) {
		
		return userManagerBean.readUser(id);
	}
	
/*	public User readUserbyEmailAndPassword(String email) {
		
		return userManagerBean.readUserbyEmailAndPassword(userEmail, userPassword)
	}*/
	
	
	// UPDATE -> UserProfileManager.Update
	
	// UPDATEROLE
	
	// DELETE User
	public Boolean deleteUser(Long id) {
		
		return userManagerBean.deleteUser(id);
	}

	public Boolean deleteUserByEmail(String email) {
		
		return userManagerBean.deleteUserByEmail(email);
	}
	
	
	// GET Role LIST
	public List<Long> getRoleIdList() {
		
		// Se servono tutti i ruoli, 
		// allora creo in questa classe il metodo getRoleList 
		// che utilizza il presente metodo
		
		return userManagerBean.getRoleIdList();
	}
	
	// CREATE Role
	public Long createRole(Role role) {

		return userManagerBean.createRole(role);
	}

	// READ Role
	public Role readRole(Long roleId) {
		
		return userManagerBean.readRole(roleId);
	}

	// READ Role by level
	public Role readRoleByLevel(int level) {
		
		return userManagerBean.readRoleByLevel(level);
	}



	public void setUserRole(User user, Role role) {
		
		userManagerBean.setUserRole(user, role);
	}



	public Boolean deleteRole(Long id) {
		
		return userManagerBean.deleteRole(id);
	}












}


