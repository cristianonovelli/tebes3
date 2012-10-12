package it.enea.xlab.tebes.users;

import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.entity.UserGroup;

import javax.ejb.Local;

@Local
public interface UserManagerLocal {

	public Long createUser(User user);
	
	public User readUser(Long id);
	
	public Boolean updateUser(User user);
	
	public Boolean deleteUser(Long id);
	
	
	
	public Long createSUT(SUT sut);
	
	public Long createGroup(UserGroup group);
	
	
	
	public void addUserSUT(Long userId, Long sutId);

	public void setUserGroup(User user, UserGroup group);
	
}
