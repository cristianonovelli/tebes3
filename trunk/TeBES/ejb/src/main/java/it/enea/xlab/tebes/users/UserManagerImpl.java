package it.enea.xlab.tebes.users;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.entity.UserGroup;


@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserManagerImpl implements UserManagerRemote {

	@PersistenceContext(unitName="TeBESPersistenceLayer")
	private EntityManager eM; 
	
	public Long createUser(User user) {

		eM.persist(user);		
		return user.getId();
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
	public void addUserSUT(User user, SUT sut) {
		
		
		User tempUser = this.readUser(user.getId());

		// commento la vecchia linea
		// tempUser.getUserSut().add(sut);
		
		// provo ad effettuare la relazione user-sut dal sut
		// si veda il seguente metodo
		sut.addToUser(tempUser);
		
		// persisto il sut
		eM.merge(sut);
		/*user.addSut(sut);*/
		

		return;
	}
	
	/**
	 * Set group of User.
	 * @param user
	 * @param group
	 * @return group ID
	 */
/*	public void setUserGroup(User user, UserGroup group) {
		
		user.setUserGroup(group);
		eM.persist(user);	
		return;
	}	*/

}
