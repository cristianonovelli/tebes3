package it.enea.xlab.tebes.users;

import java.util.List;

import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.entity.Role;

import javax.ejb.Remote;

@Remote
public interface UserManagerRemote {

	// Manager Functions
	public User login(String userEmail, String userPassword);	
	
	// User functions
	public Long createUser(User user);	
	public User readUser(Long id);	
	public Boolean updateUser(User user);	
	public Boolean deleteUser(Long id);	
	public List<Long> getUserIdList();

	// Join functions
	public void addUserSUT(Long userId, Long sutId);
	public void setRole(User user, Role role);

	// SUT functions	
	public Long createSUT(SUT sut);
	public SUT readSUT(Long idSUT);
	
	// Role functions
	public Long createRole(Role role);	
	public Role readRole(Long idRole);	
	public List<Long> getRoleIdList();
	



}
