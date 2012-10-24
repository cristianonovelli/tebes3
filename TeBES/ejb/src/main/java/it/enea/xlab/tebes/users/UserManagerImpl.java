package it.enea.xlab.tebes.users;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.entity.Role;


@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserManagerImpl implements UserManagerRemote {

	@PersistenceContext(unitName="TeBESPersistenceLayer")
	private EntityManager eM; 
	

	////////////////////
	// USER FUNCTIONS //
	////////////////////
	
	/**
	 * CREATE User
	 */
	public Long createUser(User user) {

		List<User> userList = this.findUsersByEmail(user.geteMail());
		
		if ( (userList == null) || (userList.size() == 0) ) {
			eM.persist(user);
			return user.getId();
		}
		else {
			return new Long(-1);
		}
	}

	/**
	 * READ User
	 */
	public User readUser(Long id) {

		return eM.find(User.class, id);
	}

	/**
	 * UPDATE User
	 */
	public Boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * DELETE User
	 */
	public Boolean deleteUser(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * GET User LIST
	 */
	public List<User> getUserList() {
		
        String queryString = "SELECT u FROM User AS u";   
        Query query = eM.createQuery(queryString);
        
        @SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();

        return userList;
	}

	

	
	////////////////////
	// JOIN FUNCTIONS //
	////////////////////
	
	
	/**
	 * Add SUT to User's SUT list.
	 */
	public void addUserSUT(Long userId, Long sutId) {

		User user = this.readUser(userId);
		SUT sut = this.readSUT(sutId);

		sut.addToUser(user);
		eM.persist(user);

		return;
	}
	

	/**
	 * Set Role of User.
	 */
	public void setRole(User user, Role role) {

		User u = this.readUser(user.getId());
		Role g = this.readRole(role.getId());
		
		u.setRole(g);
		eM.persist(g);	
		
		return;
	}


	
	
	///////////////////
	// SUT FUNCTIONS //
	///////////////////
	

	/**
	 * CREATE SUT
	 */
	public Long createSUT(SUT sut) {

		SUT existingSUT = this.findSUTByName(sut.getName());
		
		if (existingSUT == null) {
			eM.persist(sut);		
			return sut.getId();
		}
		else 
			return new Long(-1);
	}
	
	/**
	 * READ SUT
	 */	
	public SUT readSUT(Long idSUT) {
		
		return eM.find(SUT.class, idSUT);
	}	
	
	

	
	////////////////////
	// ROLE FUNCTIONS //
	////////////////////

	/**
	 * CREATE Role
	 * If role exists, return the id of existing role.
	 */
	public Long createRole(Role role) {

		Role existingRole = this.findRoleByLevel(role.getLevel());
		
		if (existingRole == null) {
			
			eM.persist(role);		
			return role.getId();
		}
		else
			return existingRole.getId();
	}
	
	/**
	 * READ Role
	 */
	public Role readRole(Long idGroup) {
		
		return eM.find(Role.class, idGroup);
	}
	
	/**
	 * GET Role LIST
	 */	
	public List<Role> getRoleList() {
		
        String queryString = "SELECT g FROM Role AS g";
        
        Query query = eM.createQuery(queryString);
        List<Role> roleList = query.getResultList();

        return roleList;
	}
	
	
	
	////////////////////
	// FIND FUNCTIONS //
	////////////////////
	
	/**
	 * FIND Role by Level
	 * @return the first role with that level, if the role is present 
	 * @return null, if the role is not present 
	 */
	private Role findRoleByLevel(int customLevel) {
		
        String queryString = "SELECT r FROM Role AS r WHERE r.level = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, customLevel);
        @SuppressWarnings("unchecked")
		List<Role> resultList = query.getResultList();
        if ((resultList != null) && (resultList.size() > 0))
        	return (Role) resultList.get(0);
        else
        	return null;
	}
	
	
	/**
	 * FIND SUT by Name
	 * @return the first SUT with that name, if the SUT is present 
	 * @return null, if the SUT is not present 
	 */	
	private SUT findSUTByName(String name) {
		
        String queryString = "SELECT s FROM SUT AS s WHERE s.name = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, name);
        @SuppressWarnings("unchecked")
		List<SUT> resultList = query.getResultList();
        if ((resultList != null) && (resultList.size() > 0))
        	return (SUT) resultList.get(0);
        else
        	return null;
	}

	/**
	 * FIND User List by email
	 */	
	@SuppressWarnings("unchecked")
	private List<User> findUsersByEmail(String parEmail) {
		
        String queryString = "SELECT u FROM User AS u WHERE u.eMail = ?1";
        
           Query query = eM.createQuery(queryString);
           query.setParameter(1, parEmail);
           return query.getResultList();
	}

	

	
	
}
