package it.enea.xlab.tebes.users;


import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;

import java.util.List;

import javax.naming.InitialContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserManagerImplITCase {

	// Interface Declaration
	UserManagerRemote userManagerBean;
	
	// Global Variables
	Long idUser1, idUser2;
	Long idRole1, idRole2, idRole3, idRole4;
	Long idGroup1;
	
	
	/**
	 * Before: Setup Roles
	 * @throws Exception
	 */
	@Before
	public void before_setupRoles() throws Exception {
		

		// Create EJB linked to interface UserManagerRemote
		InitialContext ctx = new InitialContext();
		userManagerBean = (UserManagerRemote) ctx.lookup("TeBES-ear/UserManagerImpl/remote");

		
		// Prepare 4 user Roles 
		Role role1 = new Role("standard_user", "Standard User Role: he can execute a test plan but he can't create/edit it", 1);
		Role role2 = new Role("advanced_user", "Advanced User Role: he can create/edit/execute a test plan", 2);
		Role role3 = new Role("system_administrator", "System Administrator Role: he is an advanced user and can add/modify test suites", 3);
		Role role4 = new Role("super_administrator", "Super Administrator Role: he has whole power and permissions on TeBES platform", 4);
		
		// Create dei Ruoli che devono già essere fissati come setup del sistema
		idRole1 = userManagerBean.createRole(role1);
		idRole2 = userManagerBean.createRole(role2);
		idRole3 = userManagerBean.createRole(role3);
		idRole4 = userManagerBean.createRole(role4);

		// Get Role List
		List<Long> roleIdList = userManagerBean.getRoleIdList();
		Assert.assertTrue(roleIdList.size() == 4);
		
		
		// Prepare 1 User Group 
		//Group group1 = new Group("xlab1", "X-Lab stuff");
		
		// Create 1 group
		//idGroup1 = userManagerBean.createGroup(group1);

		// Get Group List
		//List<Long> groupIdList = userManagerBean.getGroupIdList();
		//Assert.assertTrue(groupIdList.size() == 1);
		
	}

	
	/**
	 * Test1: User Registration
	 */
	@Test
	public void t1_registration() {
		
		// PREPARE User: Cristiano
		User cristianoUser = new User();
		cristianoUser.setName("Cristiano");
		cristianoUser.setSurname("Novelli");
		cristianoUser.seteMail("cristiano.novelli@enea.it");
		cristianoUser.setPassword("xcristiano");

		// PREPARE User: Arianna
		User ariannaUser = new User();
		ariannaUser.setName("Arianna");
		ariannaUser.setSurname("Brutti");
		ariannaUser.seteMail("arianna.brutti@enea.it");
		ariannaUser.setPassword("xpiero");

		// CREATE USER: Persist Users in DB through JPA
		idUser1 = userManagerBean.createUser(cristianoUser);
		idUser2 = userManagerBean.createUser(ariannaUser);
		
		// Test: verifico vi siano due utenti creati
		List<Long> userIdList = userManagerBean.getUserIdList();
		Assert.assertTrue(userIdList.size() >= 2);		
		// Attenzione: con getUserIdList gli Id non vengono restituiti in ordine 
		// la seguente assertion infatti fallisce
		// Assert.assertTrue(userIdList.get(0) < userIdList.get(1));
		
		// Se ho creato, testo creazione e lettura verificando gli id
		if (idUser1 > 0) {
			cristianoUser = userManagerBean.readUser(idUser1);
			Assert.assertNotNull(cristianoUser.getId());	
		}	
		if (idUser2 > 0) {
			ariannaUser = userManagerBean.readUser(idUser2);
			Assert.assertNotNull(ariannaUser.getId());	
		}

	}
	
	
	/**
	 * Test2: User Login
	 */
	@Test
	public void t2_login() {
		
		User login;
		
		// Correct login
		login= userManagerBean.login("cristiano.novelli@enea.it", "xcristiano");
		Assert.assertNotNull(login);		
		
		// Incorrect login because there is wrong password
		login = userManagerBean.login("cristiano.novelli@enea.it", "cristiano");
		Assert.assertNull(login);
	}
	
	
	/**
	 * Test3: Set Role
	 */
	@Test
	public void t3_setRole() {
		
		User cristianoUser = userManagerBean.login("cristiano.novelli@enea.it", "xcristiano");
		Assert.assertNotNull(cristianoUser);
		Assert.assertTrue(cristianoUser.getId() > 0);
		
		User ariannaUser = userManagerBean.login("arianna.brutti@enea.it", "xpiero");		
		Assert.assertNotNull(ariannaUser);
		Assert.assertTrue(ariannaUser.getId() > 0);
		
		
		// ROLE
		Role xlabRole = userManagerBean.readRole(idRole4);
		Assert.assertNotNull(xlabRole);		
		
		// SET ROLE
		userManagerBean.setRole(cristianoUser, xlabRole);
		cristianoUser = userManagerBean.readUser(cristianoUser.getId());
		Assert.assertTrue(cristianoUser.getRole().getId() > 0);

		userManagerBean.setRole(ariannaUser, xlabRole);
		ariannaUser = userManagerBean.readUser(ariannaUser.getId());
		Assert.assertTrue(ariannaUser.getRole().getId() > 0);
	}
	
	
	/**
	 * Test4: Add SUT to User
	 */
	@Test
	public void t4_addSUT() {

		User cristianoUser;
				
		cristianoUser = userManagerBean.login("cristiano.novelli@enea.it", "xcristiano");
		Assert.assertNotNull(cristianoUser);

		// Create a generic SUT
		SUT genericSUT = new SUT("sut1", "xmldocument", "XML document uploaded by web interface");
		Long idSUT = userManagerBean.createSUT(genericSUT);
		
		
		// se ho creato un nuovo SUT lo associo allo User
		if (idSUT > 0) {
		
			SUT sut = userManagerBean.readSUT(idSUT);
			Assert.assertNotNull(sut);
	
			Assert.assertTrue(cristianoUser.getId() > 0);
			
			userManagerBean.addUserSUT(cristianoUser.getId(), sut.getId());
		}
		
		cristianoUser = userManagerBean.login("cristiano.novelli@enea.it", "xcristiano");	
		List<SUT> sutList = cristianoUser.getSutList();
		Assert.assertTrue(sutList.size() > 0);
		
	}
	
	
	/**
	 * Test5: Update User
	 */
	@Test
	public void t5_update() {
		
		User ariannaUser;
		
		// Get second User
		ariannaUser = userManagerBean.login("arianna.brutti@enea.it", "xpiero");
		if ( ariannaUser == null )
			ariannaUser = userManagerBean.login("arianna.brutti@enea.it", "xarianna");		
		Assert.assertNotNull(ariannaUser);
		
		
		// Get System Admin Role
		Role systemRole = userManagerBean.readRole(idRole3);
		Assert.assertNotNull(systemRole);	
			
		// Update User
		ariannaUser.setPassword("xarianna");
		ariannaUser.setRole(systemRole);
		Boolean updating = userManagerBean.updateUser(ariannaUser);
		Assert.assertTrue(updating);
		
		// Check Login and Role
		ariannaUser = userManagerBean.login("arianna.brutti@enea.it", "xarianna");
		Assert.assertNotNull(ariannaUser);		
		Assert.assertEquals(systemRole.getLevel(), ariannaUser.getRole().getLevel());
		
	}


	/**
	 * Test6: Delete User
	 */
	@Test
	public void t6_delete() {
		
		// login
		User ariannaUser = userManagerBean.login("arianna.brutti@enea.it", "xarianna");
		
		if (ariannaUser != null) {
		
			// DELETE User
			Boolean deleting = userManagerBean.deleteUser(ariannaUser.getId());
			Assert.assertTrue(deleting);
		}
		
		// Incorrect login (wrong password)
		ariannaUser = userManagerBean.login("arianna.brutti@enea.it", "xarianna");
		Assert.assertNull(ariannaUser);
	}
	
}

