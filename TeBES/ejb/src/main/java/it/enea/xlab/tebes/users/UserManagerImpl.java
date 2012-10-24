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
	
	/**
	 * CREATE User
	 */
	public Long createUser(User user) {

		List<User> userList = this.findByEmail(user.geteMail());
		
		if ( (userList == null) || (userList.size() == 0) ) {
			eM.persist(user);
			return user.getId();
		}
		else {
			return new Long(-1);
		}
	}

	public User readUser(Long id) {

		return eM.find(User.class, id);
	}

	public Boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean deleteUser(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
	/**
	 * Add SUT to SUT list of User.
	 * @param user
	 * @param group
	 * @return SUT ID
	 */
	public void addUserSUT(Long userId, Long sutId) {

		User user = this.readUser(userId);
		SUT sut = this.readSUT(sutId);
		// commento la vecchia linea
		// tempUser.getUserSut().add(sut);
		
		
		//List<SUT> sutList = this.findSUTByName(sut.getName());
		
		//if (sutList.size() == 0) {
			
			// provo ad effettuare la relazione user-sut dal sut
			// si veda il seguente metodo
			sut.addToUser(user);
			
			eM.persist(user);
		//}
		
		// persisto il sut
		//eM.merge(sut);
		/*user.addSut(sut);*/

		return;
	}
	
	
	private List<SUT> findSUTByName(String name) {
        
		String queryString = "SELECT s FROM SUT AS s WHERE s.name = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, name);
        @SuppressWarnings("unchecked")
		List<SUT> resultList = query.getResultList();

        return resultList;
	}

	/**
	 * Set group of User.
	 * @param user
	 * @param role
	 * @return group ID
	 */
	public void setRole(User user, Role role) {

		User u = this.readUser(user.getId());
		Role g = this.readRole(role.getId());
		
		u.setRole(g);
		eM.persist(g);	
		
		return;
	}

	public Long createSUT(SUT sut) {

		eM.persist(sut);		
		return sut.getId();
	}

	
	public SUT readSUT(Long idSUT) {
		
		return eM.find(SUT.class, idSUT);
	}	
	
	
/*	public Long createRole(Role group) {

		eM.persist(group);		
		return group.getId();
	}*/

	/**
	 * CREATE Role.
	 * @return the role id. in any case.
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
	
	
	public Role readRole(Long idGroup) {
		
		return eM.find(Role.class, idGroup);
	}

	
/*	public void refreshUser(Long userId) {

		User user = this.readUser(userId);
		eM.refresh(user);
	
		return;
	}*/
	
	@SuppressWarnings("unchecked")
	private List<User> findByEmail(String parEmail) {
		
        String queryString = "SELECT u FROM User AS u WHERE u.eMail = ?1";
        
           Query query = eM.createQuery(queryString);
           query.setParameter(1, parEmail);
           return query.getResultList();
	}

	public List<Role> getRoleList() {
		
        String queryString = "SELECT g FROM Role AS g";
        
        Query query = eM.createQuery(queryString);
        List<Role> roleList = query.getResultList();

        return roleList;
	}
	
	public List<User> getUserList() {
		
        String queryString = "SELECT u FROM User AS u";   
        Query query = eM.createQuery(queryString);
        
        @SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();

        return userList;
	}
	
	
}
