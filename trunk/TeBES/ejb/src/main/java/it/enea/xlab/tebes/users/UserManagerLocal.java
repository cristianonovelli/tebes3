package it.enea.xlab.tebes.users;

import java.util.List;

import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.entity.Role;

import javax.ejb.Local;

@Local
public interface UserManagerLocal {

	public Long createUser(User user);
	
	public User readUser(Long id);
	
	public Boolean updateUser(User user);
	
	public Boolean deleteUser(Long id);

	
	
	
	public Long createSUT(SUT sut);

	public SUT readSUT(Long idSUT);
	
	
	public Long createRole(Role role);
	
	public Role readRole(Long idRole);	
	
	public List<Role> getRoleList();
	
	
	public void addUserSUT(Long userId, Long sutId);

	public void setRole(User user, Role role);
	
}
