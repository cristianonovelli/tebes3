package it.enea.xlab.tebes.users;


import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.entity.Group;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.sut.SUTManagerController;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserManagerImplITCase {

	// Interface Declaration
	static UserProfileController userProfileController;
	static UserAdminController userAdminController;
	static SUTManagerController sutManagerController;
	

	/**
	 * Before: Setup Roles and SuperUser
	 * @throws Exception
	 */
	@BeforeClass
	public static void before_userManager() throws Exception {
		
		// Get UserProfileManager Service
		userProfileController = new UserProfileController();
		Assert.assertNotNull(userProfileController);
		
		// Get UserAdminManager Service
		userAdminController = new UserAdminController();
		Assert.assertNotNull(userAdminController);
		
		// Get SUTManagerController Service
		sutManagerController = new SUTManagerController();
		Assert.assertNotNull(sutManagerController);
		
		// Create EJB linked to interface UserManagerRemote
		// InitialContext ctx = new InitialContext();
		////InitialContext ctx = ContextUtils.getInitialContext("http://winter.bologna.enea.it:8081");
		// userManagerBean = (UserManagerRemote) ctx.lookup("TeBES-ear/UserManagerImpl/remote");

		//UserAdminController adminController = new UserAdminController();
		
		// Prepare 4 user Roles 
		Role role1_standard = new Role(Constants.STANDARD_ROLE_NAME, Constants.STANDARD_ROLE_DESCRIPTION, Constants.STANDARD_ROLE_LEVEL);
		Role role2_advanced = new Role(Constants.ADVANCED_ROLE_NAME, Constants.ADVANCED_ROLE_DESCRIPTION, Constants.ADVANCED_ROLE_LEVEL);
		Role role3_admin = new Role(Constants.ADMIN_ROLE_NAME, Constants.ADMIN_ROLE_DESCRIPTION, Constants.ADMIN_ROLE_LEVEL);
		Role role4_superuser = new Role(Constants.SUPERUSER_ROLE_NAME, Constants.SUPERUSER_ROLE_DESCRIPTION, Constants.SUPERUSER_ROLE_LEVEL);
		
		// Create dei Ruoli che devono già essere fissati come setup del sistema
		Long id_role1_standard = userAdminController.createRole(role1_standard);
		Long id_role2_advanced = userAdminController.createRole(role2_advanced);
		Long id_role3_admin = userAdminController.createRole(role3_admin);
		Long id_role4_superuser = userAdminController.createRole(role4_superuser);

		// Get Role List
		List<Long> roleIdList = userAdminController.getRoleIdList();
		Assert.assertTrue(roleIdList.size() == 4);
		
		role1_standard = userAdminController.readRole(id_role1_standard);
		role2_advanced = userAdminController.readRole(id_role2_advanced);
		role3_admin = userAdminController.readRole(id_role3_admin);
		role4_superuser = userAdminController.readRole(id_role4_superuser);
		
		// Login SuperUser with Role
		// lo super User non è in memoria o caricato dall'import.sql iniziale
		// lo superUser fa login direttamente da file di proprietà e in quel momento viene caricato in memoria
		String superUserEmail = PropertiesUtil.getUser1Email();
		String superUserPassword = PropertiesUtil.getUser1Password();
		User superUser = new User("Cristiano", "Novelli", superUserEmail, superUserPassword);

		Long id_superuser = userAdminController.createUser(superUser, role4_superuser);
		
		// che sia stato creato o che fosse già esistente, faccio Login per il superUser
		superUser = userProfileController.login(superUserEmail, superUserPassword);
		id_superuser = superUser.getId();
		
		Assert.assertTrue(id_superuser > 0);
		superUser = userAdminController.readUser(id_superuser);
		Assert.assertNotNull(superUser);		

		// Prepare User Group 
		Group group1 = new Group(Constants.XLAB_GROUP_NAME, Constants.XLAB_GROUP_DESCRIPTION);
		
		// Create group
		Long idGroup1 = userAdminController.createGroup(group1);
		Assert.assertTrue(idGroup1 > 0);	

		
	}

	
	/**
	 * Test1: User Profile Manager
	 * @throws Exception 
	 */
	@Test
	public void test1_userprofile() throws Exception {
		
		// PREPARE User 1 e 2 (FORM)
		User user1 = new User("Angelo", "Frascella", Constants.USER1_EMAIL, Constants.USER1_PASSWORD);
		User user2 = new User("Arianna", "Brutti", Constants.USER2_EMAIL1, Constants.USER2_PASSWORD1);

		// REGISTRATION USERS: Persist Users in DB through JPA
		// il ProfileController può e deve assegnare solo lo standard Role
		// sarà  il superUser o l'adminuser a modificare tale ruolo in un secondo momento
		Role standardRole = userAdminController.readRoleByLevel(1);
		Long idUser1 = userProfileController.registration(user1, standardRole);
		
		Role advancedRole = userAdminController.readRoleByLevel(2);
		Long idUser2 = userProfileController.registration(user2, advancedRole);
		
		// idUser2 negativo perchè gli ho dato tramite il profileController un ruolo non standard e non è permesso in fase di registrazione
		Assert.assertTrue(idUser2 < 0);
		// ripeto l'operazione con il ruolo standard
		idUser2 = userProfileController.registration(user2, standardRole);
		
		// Test: verifico i tre utenti creati
		List<Long> userIdList = userAdminController.getUserIdList();
		Assert.assertTrue(userIdList.size() == 3);		
		
		// Attenzione: con getUserIdList gli Id non vengono restituiti in ordine 
		// la seguente assertion infatti potrebbe fallire
		// Assert.assertTrue(userIdList.get(0) < userIdList.get(1));

		// li leggo con una LOGIN
		user1 = userProfileController.login(user1.geteMail(), user1.getPassword());			
		user2 = userProfileController.login(user2.geteMail(), user2.getPassword());
	
		// Test: in un modo o nell'altro (create o update) a questo punto ho i 2 Users
		Assert.assertNotNull(user1);	
		Assert.assertNotNull(user2);
		Assert.assertTrue(user1.getId() > 0);
		Assert.assertTrue(user2.getId() > 0);

		// Test ruolo di default
		Assert.assertTrue(user1.getRole().getLevel() == Constants.STANDARD_ROLE_LEVEL);
		Assert.assertTrue(user2.getRole().getLevel() == Constants.STANDARD_ROLE_LEVEL);
		

		// User Profile UPDATING
		user2.seteMail(Constants.USER2_EMAIL2);
		user2.setPassword(Constants.USER2_PASSWORD2);
		Boolean updating = userProfileController.updateUser(user2);
		Assert.assertTrue(updating);
		
		user2 = userProfileController.login(user2.geteMail(), user2.getPassword());
		Assert.assertNotNull(user2);	
		Assert.assertEquals(user2.getPassword(), Constants.USER2_PASSWORD2);
		
		// Logout	
		user1 = null; 
		user2 = null; 
	}
	
	
	/**
	 * Test2: User Admin Manager
	 * @throws Exception 
	 */
	@Test
	public void test2_useradmin() throws Exception {	
	
		// admin
		// TODO login admin (inserito nella before)
		// TODO get users list
		// TODO cambia il loro Role
		// TODO delete user
		// logout
		
		// Login Admin per usare il service EJB AdminController altrimenti inacessibile
		String superUserEmail = PropertiesUtil.getUser1Email();
		String superUserPassword = PropertiesUtil.getUser1Password();	
		User superUser = userProfileController.login(superUserEmail, superUserPassword);
		Assert.assertNotNull(superUser);
		
		// Get User List
		List<Long> userIdList = userAdminController.getUserIdList();
		Assert.assertTrue(userIdList.size() > 0);
		
		// Get Admin ROLE
		Role adminRole = userAdminController.readRoleByLevel(3);
		Assert.assertNotNull(adminRole);	

		// Get Group List
		List<Long> groupIdList = userAdminController.getGroupIdList();
		Assert.assertTrue(groupIdList.size() == 1);
		Group xlabGroup = userAdminController.readGroup(groupIdList.get(0));
		Assert.assertNotNull(xlabGroup);
		
		
		User tempUser;
		Long tempUserId, groupSetting;
		
		// Per ogni utente (tranne lo superuser) cambio ruolo e assegno gruppo
		for (int u=0;u<userIdList.size();u++) {
			
			tempUserId = (Long) userIdList.get(u);
			Assert.assertTrue(tempUserId > 0);

			tempUser = userAdminController.readUser(tempUserId);			
			Assert.assertNotNull(tempUser);		
			
			// Questo controllo deve esserci sia qui che anche internamente alle funzioni
			if (tempUserId.intValue() != superUser.getId().intValue()) {			
				
				// Change ROLE to User
				userAdminController.setUserRole(tempUser, adminRole);
				
				// Check Group and Role
				tempUser = userAdminController.readUser(tempUserId);					
				Assert.assertTrue(tempUser.getRole().getLevel() == Constants.ADMIN_ROLE_LEVEL);
			
			}
			
			// Set xlab GROUP to everyone
			groupSetting = userAdminController.setUserGroup(tempUser, xlabGroup);
			Assert.assertTrue(groupSetting>0);
			tempUser = userAdminController.readUser(tempUserId);	
			Assert.assertTrue(tempUser.getGroup().getName().equals(Constants.XLAB_GROUP_NAME));
			
		}
		
		// Verifico Role
		tempUser = userProfileController.login(Constants.USER1_EMAIL, Constants.USER1_PASSWORD);
		Assert.assertEquals(tempUser.getRole().getLevel(), adminRole.getLevel());

		// DELETE Users
		Boolean deleting;
		for (int u=0;u<userIdList.size();u++) {
			
			tempUserId = (Long) userIdList.get(u);
			Assert.assertTrue(tempUserId > 0);
			
			// Questo controllo deve esserci sia qui che anche internamente alle funzioni
			if (tempUserId.intValue() != superUser.getId().intValue()) {

				tempUser = userAdminController.readUser(tempUserId);			
				Assert.assertNotNull(tempUser);

				// DELETE User
				deleting = userAdminController.deleteUser(tempUser.getId());
				Assert.assertTrue(deleting);
			
			}
			
		}
		
		// Last Check
		// è rimasto solo l'admin?
		userIdList = userAdminController.getUserIdList();
		Assert.assertTrue(userIdList.size() == 1);
	}
	

	/**
	 * After: Clean
	 * The Role deleting implies the User deleting!
	 * @throws Exception
	 */
	@AfterClass
	public static void after_clean() throws Exception {

		Boolean deleting;
		
		// Get Role List
		List<Long> roleIdList = userAdminController.getRoleIdList();
		Assert.assertTrue(roleIdList.size() == 4);
		
		Role tempRole;
		Long tempRoleId;
		
		// Cancello ogni ruolo
		for (int u=0;u<roleIdList.size();u++) {
			
			tempRoleId = (Long) roleIdList.get(u);
			Assert.assertTrue(tempRoleId > 0);

				tempRole = userAdminController.readRole(tempRoleId);			
				Assert.assertNotNull(tempRole);
							
				// DELETE Role
				deleting = userAdminController.deleteRole(tempRole.getId());
				Assert.assertTrue(deleting);			
		}		
		
		// Get Group List
		List<Long> groupIdList = userAdminController.getGroupIdList();
		Assert.assertTrue(groupIdList.size() == 1);
		deleting = userAdminController.deleteGroup(groupIdList.get(0));
		Assert.assertTrue(deleting);
		groupIdList = userAdminController.getGroupIdList();
		Assert.assertTrue(groupIdList.size() == 0);
		
		// Last Check
		// Sono stati eliminati tutti gli utenti?
		List<Long> userIdList = userAdminController.getUserIdList();
		userIdList = userAdminController.getUserIdList();
		Assert.assertTrue(userIdList.size() == 0);
		
	}
}




