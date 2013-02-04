package it.enea.xlab.tebes.users;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.entity.Group;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.entity.Role;


@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserManagerImpl implements UserManagerRemote {

	@PersistenceContext(unitName=Constants.PERSISTENCE_CONTEXT)
	private EntityManager eM; 
	

	////////////////////
	// USER FUNCTIONS //
	////////////////////
	
	/**
	 * CREATE User
	 * @return 	id of User if created
	 * 			-1 if already a User with that email exists
	 * 			-2 if an exception occurs
	 */
	public Long createUser(User user) {

		List<User> userList = this.readUsersByEmail(user.geteMail());
		
		try {
			if ( (userList == null) || (userList.size() == 0) ) {
				eM.persist(user);
				return user.getId();
			}
			else {
				return new Long(-1);
			}
		}
		catch(Exception e) {
			return new Long(-2);
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
		
		Boolean result = false;
		
		 try {
			 if ( (user != null) && (user.getId() != null) ) {
				 user = eM.merge(user);
				 //eM.persist(user);
				 
				 if (user != null)
					 result = true;
			 }
			 
		} catch (IllegalArgumentException e) {
			result = false;
		} catch (Exception e2) {
			result = null;
		}
		 
		 return result;
	}
	
	/**
	 * DELETE User
	 */
	public Boolean deleteUser(Long id) {

		User user = this.readUser(id);
		
		if (user == null)
			return false;
		
		try {
			
			// TODO Deleting Sut List?
			
			
			
			eM.remove(user);
			
		} catch (IllegalArgumentException e) {
			return false;
		} catch (Exception e2) {
			return null;
		}
		
		return true;
	}
	
	public Boolean deleteUserByEmail(String email) {
		
		Boolean result = false;
		List<User> userList = this.readUsersByEmail(email);	
		
		if ( (userList == null) || (userList.size() == 0) ) {
			
			int i=0;
			while (i < userList.size()) {
			
				this.deleteUser(userList.get(i).getId());
				i++;		
			}
			result = true;
		}

		return result;
	}

	/**
	 * GET User LIST
	 */
	public List<Long> getUserIdList() {
		
        String queryString = "SELECT u.id FROM User AS u";   
        Query query = eM.createQuery(queryString);
        
        @SuppressWarnings("unchecked")
		List<Long> userIdList = query.getResultList();

        return userIdList;
	}

	

	
	////////////////////
	// JOIN FUNCTIONS //
	////////////////////
	
	
	/**
	 * Add SUT to User's SUT list.
	 */
/*	public void addSUTToUser(SUT sut, User user) {

		sut.addToUser(user);
		eM.persist(user);

		return;
	}*/
	public void addSUTToUser(Long sutId, Long userId) {

		User user = this.readUser(userId);
		SUT sut = this.readSUT(sutId);
		
		sut.addToUser(user);
		eM.persist(user);

		return;
	}
	
	// duplicato dalla sutmanagerimpl
	// non ho trovato altra via
	private SUT readSUT(Long sutID) {
		
		return eM.find(SUT.class, sutID);
	}	
	
	/**
	 * Set Role of User.
	 */
	public void setUserRole(User user, Role role) {

		User u = this.readUser(user.getId());
		Role r = this.readRole(role.getId());
		
		
		// Effettuo il Set se il Role è null oppure è diverso da superuser
		if ( ( u.getRole() == null ) || (u.getRole().getLevel() != Constants.SUPERUSER_ROLE_LEVEL ) ) {

			u.setRole(r);
			eM.persist(r);	
		}
		
		
		// se il ruolo dell'utente c'è e non è lo superuser
		if ( ( u.getRole() != null ) && (u.getRole().getLevel() != Constants.SUPERUSER_ROLE_LEVEL ) ) {
		

		}
		return;
	}


	
	
	///////////////////
	// SUT FUNCTIONS //
	///////////////////
	/*
	*//**
	 * CREATE SUT
	 *//*
	public Long createSUT(SUT sut) {

		SUT existingSUT = this.findSUTByName(sut.getName());
		
		if (existingSUT == null) {
			eM.persist(sut);		
			return sut.getId();
		}
		else 
			return new Long(-1);
	}
	
	*//**
	 * READ SUT
	 *//*	
	public SUT readSUT(Long idSUT) {
		
		return eM.find(SUT.class, idSUT);
	}	
	
	
	*//**
	 * DELETE SUT
	 *//*
	public Boolean deleteSUT(Long idSUT) {

		SUT sut = this.readSUT(idSUT);
		
		if (sut == null)
			return false;
		
		try {
			eM.remove(sut);
		} catch (IllegalArgumentException e) {
			return false;
		} catch (Exception e2) {
			return null;
		}
		
		return true;
	}
*/
	
	////////////////////
	// ROLE FUNCTIONS //
	////////////////////

	/**
	 * CREATE Role
	 * @return 	id of Role if created
	 * 			id of Role if already exists
	 */
	public Long createRole(Role role) {

		Role existingRole = this.readRoleByLevel(role.getLevel());
		
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
	public Role readRole(Long idRole) {
		
		return eM.find(Role.class, idRole);
	}
	
	/**
	 * READ Role by Level
	 * @return the first role with that level, if the role is present 
	 * @return null, if the role is not present 
	 */
	public Role readRoleByLevel(int customLevel) {
		
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
	 * GET Role LIST
	 */	
	public List<Long> getRoleIdList() {
		
        String queryString = "SELECT r.id FROM Role AS r";
        
        Query query = eM.createQuery(queryString);
        List<Long> roleIdList = query.getResultList();

        return roleIdList;
	}
	
	public Boolean deleteRole(Long id) {
		
		Role role = this.readRole(id);
		
		if (role == null)
			return false;
		
		try {

			eM.remove(role);
			
		} catch (IllegalArgumentException e) {
			return false;
		} catch (Exception e2) {
			return null;
		}
		
		return true;
	}

	
	
	/////////////////////
	// GROUP FUNCTIONS //
	/////////////////////
	
	/**
	 * CREATE Group
	 * If group exists, return the id of existing group.
	 */	
	public Long createGroup(Group group) {
		
		//Group existingGroup = this.findGroupByName(group.getName());
		
		//if (existingGroup == null) {
			
			eM.persist(group);		
			return group.getId();
		//}
		//else
			//return existingGroup.getId();
	}


	/**
	 * GET Group List
	 */
	public List<Long> getGroupIdList() {

        String queryString = "SELECT g.id FROM Group AS g";
        
        Query query = eM.createQuery(queryString);
        List<Long> groupIdList = query.getResultList();

        return groupIdList;
	}
	
	
	
	////////////////////
	// FIND FUNCTIONS //
	////////////////////
	

	
	
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

	private Group findGroupByName(String name) {

        String queryString = "SELECT g FROM Group AS g WHERE g.name = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, name);

		@SuppressWarnings("unchecked")
		List<Group> resultList = query.getResultList();
        if ((resultList != null) && (resultList.size() > 0))
        	return (Group) resultList.get(0);
        else
        	return null;
	}
	
	/**
	 * FIND User List by email
	 */	
	@SuppressWarnings("unchecked")
	public List<User> readUsersByEmail(String parEmail) {
		
        String queryString = "SELECT u FROM User AS u WHERE u.eMail = ?1";
        
           Query query = eM.createQuery(queryString);
           query.setParameter(1, parEmail);
           return query.getResultList();
	}


	
	///////////////////////
	// MANAGER FUNCTIONS //
	///////////////////////

	/**
	 * Login method
	 * @return User if there is a user with the specified email and password
	 * @return null otherwise
	 */
	public User readUserbyEmailAndPassword(String userEmail, String userPassword) {
		
		User result = null;
		
		List<User> userList = this.readUsersByEmail(userEmail);
		
		if ( (userList != null) && (userList.size() == 1) ) {			
			
			User user = userList.get(0);
			
			if (user.getPassword().equals(userPassword))
				result = user;
		}
		
		return result;
	}










	
}



