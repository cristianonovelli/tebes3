package it.enea.xlab.tebes.users;

import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UserManagerLocal {

	// CREATE User
	public Long createUser(User user);
	
	// READ User
	public User readUser(Long id);	
	public User readUserbyEmailAndPassword(String userEmail, String userPassword);	
	public List<Long> getUserIdList();
	
	// UPDATE User
	public Boolean updateUser(User user);	
	
	// DELETE User
	public Boolean deleteUser(Long id);	
	

	
	// Join functions
	//public void addSUTToUser(SUT sut, User user);
	public void addSUTToUser(Long sutId, Long userId);
	public void setUserRole(User user, Role role);

	// SUT functions	
	//public Long createSUT(SUT sut);
	//public SUT readSUT(Long idSUT);
	
	// Role functions
	public Long createRole(Role role);	
	public Role readRole(Long idRole);	
	public Role readRoleByLevel(int level);
	public List<Long> getRoleIdList();

	

	// Group functions
	//public Long createGroup(Group group);
	//public List<Long> getGroupIdList();
	
}
