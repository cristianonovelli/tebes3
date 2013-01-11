package it.enea.xlab.tebes.users;


import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.sut.SUTManagerController;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserManagerImplITCase {

	// Interface Declaration
	// UserManagerRemote userManagerBean;
	
	UserProfileController userProfileController;
	UserAdminController adminController;
	SUTManagerController sutManagerController;
	
	// Global Variables
	Long idUser1, idUser2;
	Long idRole1, idRole2, idRole3, idRole4;
	Long idGroup1;
	
	
	/**
	 * Before: Setup Roles
	 * @throws Exception
	 */
	@Before
	public void before_preparation() throws Exception {
		
		userProfileController = new UserProfileController();
		adminController = new UserAdminController();
		sutManagerController = new SUTManagerController();
		
		// Create EJB linked to interface UserManagerRemote
		// InitialContext ctx = new InitialContext();
		////InitialContext ctx = ContextUtils.getInitialContext("http://winter.bologna.enea.it:8081");
		// userManagerBean = (UserManagerRemote) ctx.lookup("TeBES-ear/UserManagerImpl/remote");

		//UserAdminController adminController = new UserAdminController();
		
		// Prepare 4 user Roles 
		Role role1 = new Role(Constants.STANDARD_ROLE_NAME, Constants.STANDARD_ROLE_DESCRIPTION, Constants.STANDARD_ROLE_LEVEL);
		Role role2 = new Role(Constants.ADVANCED_ROLE_NAME, Constants.ADVANCED_ROLE_DESCRIPTION, Constants.ADVANCED_ROLE_LEVEL);
		Role role3 = new Role(Constants.ADMIN_ROLE_NAME, Constants.ADMIN_ROLE_DESCRIPTION, Constants.ADMIN_ROLE_LEVEL);
		Role role4 = new Role(Constants.DEVELOPER_ROLE_NAME, Constants.DEVELOPER_ROLE_DESCRIPTION, Constants.DEVELOPER_ROLE_LEVEL);
		
		// Create dei Ruoli che devono già essere fissati come setup del sistema
		idRole1 = adminController.createRole(role1);
		idRole2 = adminController.createRole(role2);
		idRole3 = adminController.createRole(role3);
		idRole4 = adminController.createRole(role4);

		// Get Role List
		List<Long> roleIdList = adminController.getRoleIdList();
		Assert.assertTrue(roleIdList.size() == 4);
		
		
		// Prepare 1 User Group 
		//Group group1 = new Group("xlab1", "X-Lab stuff");
		
		// Create 1 group
		//idGroup1 = userManagerBean.createGroup(group1);

		// Get Group List
		//List<Long> groupIdList = userManagerBean.getGroupIdList();
		//Assert.assertTrue(groupIdList.size() == 1);
		
	/*}

	
	*//**
	 * Test1: User Registration
	 * @throws Exception 
	 *//*
	@Test
	public void t1_registration() throws Exception {*/
		

		
		// PREPARE User 1 e 2
		User user1 = new User("Cristiano", "Novelli", "cristiano.novelli@enea.it", "xcristiano");
		User user2 = new User("Arianna", "Brutti", "arianna.brutti@enea.it", "xpiero");


		// CREATE USER: Persist Users in DB through JPA
		//idUser1 = userManagerBean.createUser(user1);
		//idUser2 = userManagerBean.createUser(user2);
		idUser1 = userProfileController.signUp(user1);
		idUser2 = userProfileController.signUp(user2);
		
		
		// Test: verifico vi siano due utenti creati
		List<Long> userIdList = adminController.getUserIdList();
		Assert.assertTrue(userIdList.size() >= 2);		
		
		// Attenzione: con getUserIdList gli Id non vengono restituiti in ordine 
		// la seguente assertion infatti può fallire
		// Assert.assertTrue(userIdList.get(0) < userIdList.get(1));
		
		// Se ho creato, testo creazione e lettura verificando gli id
		if (idUser1 > 0) {
			user1 = adminController.readUser(idUser1);
			Assert.assertNotNull(user1.getId());	
		}	
		if (idUser2 > 0) {
			user2 = adminController.readUser(idUser2);
			Assert.assertNotNull(user2.getId());	
		}
		
		// NOTA: alla regitrazione nella banca dati dovrebbe seguire anche la creazione di una directory di lavoro dove allocare file XML

	/*}
	
	
	*//**
	 * Test2: User Login
	 * In questo caso ho richiamato il Controller
	 * @throws Exception 
	 *//*
	@Test
	public void t2_login() throws Exception {*/
		
		
		// Correct login
		//UserProfileController userProfileController = new UserProfileController();
		user1 = userProfileController.login("cristiano.novelli@enea.it", "xcristiano");
		Assert.assertNotNull(user1);		
		
		// Incorrect login because there is wrong password
		user1 = userProfileController.login("cristiano.novelli@enea.it", "cristiano");
		Assert.assertNull(user1);
	/*}
	
	
	*//**
	 * Test3: Set Role into Users
	 * @throws Exception 
	 *//*
	@Test
	public void t3_setRole() throws Exception {*/
		
		//userProfileController = new UserProfileController();
		user1 = userProfileController.login("cristiano.novelli@enea.it", "xcristiano");
		Assert.assertTrue(user1.getId() > 0);
		
		user2 = userProfileController.login("arianna.brutti@enea.it", "xpiero");		
		Assert.assertTrue(user2.getId() > 0);
		
		
		// ROLE
		Role xlabRole = adminController.readRoleByLevel(4);
		Assert.assertNotNull(xlabRole);		
		
		// SET ROLE
		adminController.setUserRole(user1, xlabRole);
		user1 = adminController.readUser(user1.getId());
		Assert.assertTrue(user1.getRole().getId() > 0);

		adminController.setUserRole(user2, xlabRole);
		user2 = adminController.readUser(user2.getId());
		Assert.assertTrue(user2.getRole().getId() > 0);
	/*}
	
	
	*//**
	 * Test4: Add SUT to User
	 * @throws Exception 
	 *//*
	@Test
	public void t4_addSUT() throws Exception {*/
		
		//UserProfileController userManagerController = new UserProfileController();
		user1 = userProfileController.login("cristiano.novelli@enea.it", "xcristiano");
		Assert.assertNotNull(user1);

		//UserAdminController adminController = new UserAdminController();
		user1 = adminController.readUser(user1.getId());
		
		// Create a generic SUT
		SUT sut1 = new SUT("sut1", "xmldocument", "XML document uploaded by web interface");
		sutManagerController = new SUTManagerController();
		Long idSUT = sutManagerController.createSUT(sut1, user1);
		
		
		// se ho creato un nuovo SUT lo associo allo User
		if (idSUT > 0) {
		
			SUT sut = sutManagerController.readSUT(idSUT);
			Assert.assertNotNull(sut);
	
			Assert.assertTrue(user1.getId() > 0);
			
			userProfileController.addSUTToUser(sut.getId(), user1.getId());
		}
		
		user1 = userProfileController.login("cristiano.novelli@enea.it", "xcristiano");	
		List<SUT> sutList = user1.getSutList();
		Assert.assertTrue(sutList.size() > 0);
		
	/*}
	
	
	*//**
	 * Test5: Update User
	 * @throws Exception 
	 *//*
	@Test
	public void t5_update() throws Exception {*/
		
		//UserProfileController userMC = new UserProfileController();
		user2 = userProfileController.login("arianna.brutti@enea.it", "xpiero");
		if ( user2 == null )
			user2 = userProfileController.login("arianna.brutti@enea.it", "xarianna");		
		Assert.assertNotNull(user2);
		
		
		// Get System Admin Role
		Role adminRole = adminController.readRoleByLevel(3);
		Assert.assertNotNull(adminRole);	
		user2.setRole(adminRole);
		
		
		// Update User
		user2.setPassword("xarianna");
		Boolean updating = userProfileController.updateUser(user2);
		Assert.assertTrue(updating);
		
		// Check Login and Role
		user2 = userProfileController.login("arianna.brutti@enea.it", "xarianna");
		Assert.assertNotNull(user2);		
		Assert.assertEquals(adminRole.getLevel(), user2.getRole().getLevel());
		
	/*}


	*//**
	 * Test6: Delete User
	 * @throws Exception 
	 *//*
	@Test
	public void t6_delete() throws Exception {*/
		
		// login
		// UserProfileController userMC = new UserProfileController();
		user2 = userProfileController.login("arianna.brutti@enea.it", "xarianna");
		
		if (user2 != null) {
		
			// DELETE User
			Boolean deleting = adminController.deleteUser(user2.getId());
			Assert.assertTrue(deleting);
		}
		
		// Incorrect login (wrong password)
		user2 = userProfileController.login("arianna.brutti@enea.it", "xarianna");
		Assert.assertNull(user2);
	}
	
	
	
}




