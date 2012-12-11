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
	public void before_rolesCreation() throws Exception {
		

		// Create EJB linked to interface UserManagerRemote
		InitialContext ctx = new InitialContext();
		//InitialContext ctx = ContextUtils.getInitialContext("http://winter.bologna.enea.it:8081");
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
		User user1 = new User();
		//cristianoUser.setUserId("IT-12345678909");
		user1.setName("Cristiano");
		user1.setSurname("Novelli");
		user1.seteMail("cristiano.novelli@enea.it");
		user1.setPassword("xcristiano");

		// PREPARE User: Arianna
		User user2 = new User();
		user2.setName("Arianna");
		user2.setSurname("Brutti");
		//ariannaUser.setUserId("IT-98765432101");
		user2.seteMail("arianna.brutti@enea.it");
		user2.setPassword("xpiero");

		// CREATE USER: Persist Users in DB through JPA
		idUser1 = userManagerBean.createUser(user1);
		idUser2 = userManagerBean.createUser(user2);
		
		// Test: verifico vi siano due utenti creati
		List<Long> userIdList = userManagerBean.getUserIdList();
		Assert.assertTrue(userIdList.size() >= 2);		
		// Attenzione: con getUserIdList gli Id non vengono restituiti in ordine 
		// la seguente assertion infatti fallisce
		// Assert.assertTrue(userIdList.get(0) < userIdList.get(1));
		
		// Se ho creato, testo creazione e lettura verificando gli id
		if (idUser1 > 0) {
			user1 = userManagerBean.readUser(idUser1);
			Assert.assertNotNull(user1.getId());	
		}	
		if (idUser2 > 0) {
			user2 = userManagerBean.readUser(idUser2);
			Assert.assertNotNull(user2.getId());	
		}
		
		// NOTA: alla regitrazione nella banca dati dovrebbe seguire anche la creazione di una directory di lavoro dove allocare file XML

	}
	
	
	/**
	 * Test2: User Login
	 * In questo caso ho richiamato il Controller
	 * @throws Exception 
	 */
	@Test
	public void t2_login() throws Exception {
		
		User user;
		
		// Correct login
		UserManagerController userMC = new UserManagerController();
		user = userMC.login("cristiano.novelli@enea.it", "xcristiano");
		Assert.assertNotNull(user);		
		
		// Incorrect login because there is wrong password
		user = userMC.login("cristiano.novelli@enea.it", "cristiano");
		Assert.assertNull(user);
	}
	
	
	/**
	 * Test3: Set Role into Users
	 * @throws Exception 
	 */
	@Test
	public void t3_setRole() throws Exception {
		
		UserManagerController userMC = new UserManagerController();
		User user1 = userMC.login("cristiano.novelli@enea.it", "xcristiano");
		Assert.assertNotNull(user1);
		Assert.assertTrue(user1.getId() > 0);
		
		User user2 = userMC.login("arianna.brutti@enea.it", "xpiero");		
		Assert.assertNotNull(user2);
		Assert.assertTrue(user2.getId() > 0);
		
		
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
	
	
	/**
	 * Test4: Add SUT to User
	 * @throws Exception 
	 */
	@Test
	public void t4_addSUT() throws Exception {

		UserManagerController userMC = new UserManagerController();
		User user = userMC.login("cristiano.novelli@enea.it", "xcristiano");
		Assert.assertNotNull(user);

		// Create a generic SUT
		SUT genericSUT = new SUT("sut1", "xmldocument", "XML document uploaded by web interface");
		Long idSUT = userManagerBean.createSUT(genericSUT);
		
		
		// se ho creato un nuovo SUT lo associo allo User
		if (idSUT > 0) {
		
			SUT sut = userManagerBean.readSUT(idSUT);
			Assert.assertNotNull(sut);
	
			Assert.assertTrue(user.getId() > 0);
			
			userManagerBean.addUserSUT(user.getId(), sut.getId());
		}
		
		user = userMC.login("cristiano.novelli@enea.it", "xcristiano");	
		List<SUT> sutList = user.getSutList();
		Assert.assertTrue(sutList.size() > 0);
		
	}
	
	
	/**
	 * Test5: Update User
	 * @throws Exception 
	 */
	@Test
	public void t5_update() throws Exception {
		
		UserManagerController userMC = new UserManagerController();
		User user = userMC.login("arianna.brutti@enea.it", "xpiero");
		if ( user == null )
			user = userMC.login("arianna.brutti@enea.it", "xarianna");		
		Assert.assertNotNull(user);
		
		
		// Get System Admin Role
		Role systemRole = userManagerBean.readRole(idRole3);
		Assert.assertNotNull(systemRole);	
			
		// Update User
		user.setPassword("xarianna");
		user.setRole(systemRole);
		Boolean updating = userManagerBean.updateUser(user);
		Assert.assertTrue(updating);
		
		// Check Login and Role
		user = userMC.login("arianna.brutti@enea.it", "xarianna");
		Assert.assertNotNull(user);		
		Assert.assertEquals(systemRole.getLevel(), user.getRole().getLevel());
		
	}


	/**
	 * Test6: Delete User
	 * @throws Exception 
	 */
	@Test
	public void t6_delete() throws Exception {
		
		// login
		UserManagerController userMC = new UserManagerController();
		User user2 = userMC.login("arianna.brutti@enea.it", "xarianna");
		
		if (user2 != null) {
		
			// DELETE User
			Boolean deleting = userManagerBean.deleteUser(user2.getId());
			Assert.assertTrue(deleting);
		}
		
		// Incorrect login (wrong password)
		user2 = userMC.login("arianna.brutti@enea.it", "xarianna");
		Assert.assertNull(user2);
	}
	
	
	
}




