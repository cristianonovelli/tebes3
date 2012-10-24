package it.enea.xlab.tebes.users;


import java.util.List;

import javax.naming.InitialContext;


import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.entity.Role;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserManagerImplITCase {

	User user1, user2;

	Role currentGroup;
	UserManagerRemote userManagerBean;
	Long idUser1, idUser2;
	Long idRole1, idRole2, idRole3, idRole4;
	
	@Before
	public void t1_setup() throws Exception {
		

		// Create EJB linked to interface UserManagerRemote
		InitialContext ctx = new InitialContext();
		userManagerBean = (UserManagerRemote) ctx.lookup("TeBES-ear/UserManagerImpl/remote");

		
		// Prepare 4 user Roles 
		Role role1 = new Role("standard_user", "Standard Users Group", 1);
		Role role2 = new Role("advanced_user", "Advanced Users Group", 2);
		Role role3 = new Role("system_administrator", "System Administrators Group", 3);
		Role role4 = new Role("xlab", "XLab Staff Group", 4);
		
		// Create dei Ruoli che devono già essere fissati come setup del sistema
		idRole1 = userManagerBean.createRole(role1);
		idRole2 = userManagerBean.createRole(role2);
		idRole3 = userManagerBean.createRole(role3);
		idRole4 = userManagerBean.createRole(role4);

		// Get Role List
		List<Role> roleList = userManagerBean.getRoleList();
		Assert.assertTrue(roleList.size() == 4);
		
	}
	
	@Test
	public void t2_registration() {
		
		// PREPARE User: Cristiano
		User user1 = new User();
		user1.setName("Cristiano");
		user1.setSurname("Novelli");
		user1.seteMail("cristiano.novelli@enea.it");
		user1.setPassword("xcristiano");

		// PREPARE User: Arianna
		User user2 = new User();
		user2.setName("Arianna");
		user2.setSurname("Brutti");
		user2.seteMail("arianna.brutti@enea.it");
		user2.setPassword("xpiero");

		// CREATE USER: Persist Users in DB through JPA
		idUser1 = userManagerBean.createUser(user1);
		idUser2 = userManagerBean.createUser(user2);
		
		// Test: verifico vi siano i due utenti creati
		List<User> userList = userManagerBean.getUserList();
		Assert.assertTrue(userList.size() >= 2);
		
		// Test: verifico che gli id siano maggiori di zero
		// READ USER and Test
		if (idUser1 > 0)
			user1 = userManagerBean.readUser(idUser1);
		else
			user1 = userList.get(0);
		Assert.assertNotNull(user1);
		Assert.assertEquals(user1.getId(), new Long(1));
		
		if (idUser2 > 0)
			user2 = userManagerBean.readUser(idUser2);
		else
			user2 = userList.get(1);		
		Assert.assertNotNull(user2);
		Assert.assertEquals(user2.getId(), new Long(2));
		
		
		// ROLE
		Role xlabRole = userManagerBean.readRole(idRole4);
		Assert.assertNotNull(xlabRole);		
		
		// SET ROLE
		userManagerBean.setRole(user1, xlabRole);
		user1 = userManagerBean.readUser(user1.getId());
		Assert.assertTrue(user1.getRole().getId() > 0);

		userManagerBean.setRole(user2, xlabRole);
		user2 = userManagerBean.readUser(user2.getId());
		Assert.assertTrue(user2.getRole().getId() > 0);

	}
	
	
	@Test
	public void t3_login() {
		
		
	}
	
	@Test
	public void t4_addSUT() {

		// Create a generic SUT
		SUT genericSUT = new SUT("sut1", "xmldocument", "XML document uploaded by web interface");

		// Get First User
		List<User> userList = userManagerBean.getUserList();
		if ((userList != null) && (userList.size() > 0))
			user1 = userList.get(0);
		
		// SUT
		Long idSUT = userManagerBean.createSUT(genericSUT);
		
		
		// se ho creato un nuovo SUT lo associo allo User
		if (idSUT > 0) {
		
			SUT sut = userManagerBean.readSUT(idSUT);
			Assert.assertNotNull(sut);
	
			Assert.assertNotNull(user1);
			Assert.assertTrue(user1.getId() > 0);
			
			userManagerBean.addUserSUT(user1.getId(), sut.getId());
		}
		
		
		user1 = userManagerBean.readUser(user1.getId());	
		List<SUT> sutList = user1.getSutList();
		Assert.assertTrue(sutList.size() > 0);
		
	}
	
	
	@Test
	public void t5_update() {
		
		
	}


	@Test
	public void t6_delete() {
		
		
	}
	
}




