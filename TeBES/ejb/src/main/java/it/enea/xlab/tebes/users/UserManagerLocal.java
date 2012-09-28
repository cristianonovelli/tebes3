package it.enea.xlab.tebes.users;

import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;

import javax.ejb.Local;

@Local
public interface UserManagerLocal {

	public Long createUser(User user);
	
	public Long readUser(Long id);
	
	public Boolean updateUser(User user);
	
	public Boolean deleteUser(Long id);
	
	public Long addUserSUT(User user, SUT sut);

	//public Long setUserGroup(User user, UserGroup group);
	
	
	

	
	
}
