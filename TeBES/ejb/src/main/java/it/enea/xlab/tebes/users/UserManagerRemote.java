package it.enea.xlab.tebes.users;

import it.enea.xlab.tebes.entity.Group;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.User;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface UserManagerRemote {
	
	// CREATE User
	public Long createUser(User user);
	
	// READ User
	public User readUser(Long id);	
	public User readUserbyEmailAndPassword(String userEmail, String userPassword);	
	public List<Long> getUserIdList();
	public Long getSuperUserId();
	
	// UPDATE User
	public Boolean updateUser(User user);	
	public void addSUTToUser(Long sutId, Long userId);
	
	// DELETE User
	public Boolean deleteUser(Long id);	
	public Boolean deleteUserByEmail(String email);

	// Role functions
	public Long createRole(Role role);	
	public Role readRole(Long idRole);	
	public Role readRoleByLevel(int level);
	public List<Long> getRoleIdList();
	public void setUserRole(User user, Role role);
	public Boolean deleteRole(Long id);

	// Group functions
	public Long createGroup(Group group);
	public List<Long> getGroupIdList();
	public Group readGroup(Long id);
	public Long setUserGroup(User user, Group group);
	public Boolean deleteGroup(Long id);

	//public Boolean deleteTestPlan(Long userId, Long testPlanId);
}
