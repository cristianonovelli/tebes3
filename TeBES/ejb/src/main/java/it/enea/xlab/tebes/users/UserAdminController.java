package it.enea.xlab.tebes.users;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.User;

import java.util.List;

public class UserAdminController {

	private UserManagerRemote userManagerBean;
	
	
	
	public UserAdminController() throws Exception {

		//InitialContext ctx = new InitialContext();
		userManagerBean = JNDIServices.getUserManagerService();
				//(UserManagerRemote) ctx.lookup("TeBES-ear/UserManagerImpl/remote");
	}

	
	
	// GET USER LIST
	public List<Long> getUserIdList() {
		
		return userManagerBean.getUserIdList();
	}
	
	// CREATE -> UserProfileManager.Registration
	

	// READ User
	public User readUser(Long id) {
		
		return userManagerBean.readUser(id);
	}
	
	// UPDATE -> UserProfileManager.Update
	
	// UPDATEROLE
	
	// DELETE User
	public Boolean deleteUser(Long id) {
		
		return userManagerBean.deleteUser(id);
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




}


