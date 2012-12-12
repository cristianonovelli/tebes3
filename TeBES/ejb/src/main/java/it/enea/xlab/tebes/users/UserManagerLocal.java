package it.enea.xlab.tebes.users;

import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UserManagerLocal {

	// Manager Functions
	public Boolean login(String userEmail, String userPassword);	
	
	// User functions
	public Long createUser(User user);	
	public User readUser(Long id);	
	public Boolean updateUser(User user);	
	public Boolean deleteUser(Long id);	
	public List<User> getUserList();

	// Join functions
	//public void addSUTToUser(SUT sut, User user);
	public void addSUTToUser(Long sutId, Long userId);
	public void setRole(User user, Role role);

	// SUT functions	
	public Long createSUT(SUT sut);
	public SUT readSUT(Long idSUT);
	
	// Role functions
	public Long createRole(Role role);	
	public Role readRole(Long idRole);	
	public List<Role> getRoleList();
	
	// Group functions
	//public Long createGroup(Group group);
	//public List<Long> getGroupIdList();
	
}
