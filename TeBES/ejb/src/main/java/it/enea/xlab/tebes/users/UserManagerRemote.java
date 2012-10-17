package it.enea.xlab.tebes.users;

import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.entity.UserGroup;

import javax.ejb.Remote;

@Remote
public interface UserManagerRemote {

	public Long createUser(User user);
	
	public User readUser(Long id);
	
	public Boolean updateUser(User user);
	
	public Boolean deleteUser(Long id);

	
	
	
	public Long createSUT(SUT sut);

	public SUT readSUT(Long idSUT);
	
	
	public Long createGroup(UserGroup group);
	
	public UserGroup readGroup(Long idGroup);	
	
	
	public void addUserSUT(Long userId, Long sutId);

	public void setUserGroup(User user, UserGroup group);




}
