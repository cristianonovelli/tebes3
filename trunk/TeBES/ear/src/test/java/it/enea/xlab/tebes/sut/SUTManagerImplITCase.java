package it.enea.xlab.tebes.sut;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.SUTConstants;
import it.enea.xlab.tebes.controllers.sut.SUTManagerController;
import it.enea.xlab.tebes.controllers.users.UserAdminController;
import it.enea.xlab.tebes.controllers.users.UserProfileController;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.SUTInteraction;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.utilities.WebControllersUtilities;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * SUT Manager Controller Summary
 * Which are the main operations of the SUT Manager?
 * 1. View SUT list for the current User
 * 2. CREATE new SUT
 * 3. READ and UPDATE a SUT
 * 4. DELETE a SUT (a User deleting would implies the deleting of relative sut list)
 * 
 * @Before CREATE the current User
 * 		
 * @author Cristiano Novelli
 */
public class SUTManagerImplITCase {

	// Interfaces Declarations
	static SUTManagerController sutManagerController;
	static UserAdminController userAdminController;
	static UserProfileController userProfileController;

	static Role role1_standard, role2_advanced, role3_admin, role4_superuser;
	
	/**
	 * Before: Prepare SUTManagerController
	 * @throws Exception 
	 */
	@BeforeClass
	public static void before_sutManager() throws Exception {

		// Get Services
		userAdminController = (UserAdminController) WebControllersUtilities.getManager(UserAdminController.CONTROLLER_NAME);
		Assert.assertNotNull(userAdminController);

		userProfileController = (UserProfileController) WebControllersUtilities.getManager(UserProfileController.CONTROLLER_NAME);
		Assert.assertNotNull(userProfileController);		

		sutManagerController = (SUTManagerController) WebControllersUtilities.getManager(SUTManagerController.CONTROLLER_NAME);
		Assert.assertNotNull(sutManagerController);
		
		// Get Role List
		List<Long> roleIdList = userAdminController.getRoleIdList();
		Assert.assertTrue( (roleIdList.size() == 0) || (roleIdList.size() == 4) );

		if (roleIdList.size() == 0) {
			
			// Prepare 4 user Roles 
			role1_standard = new Role(Constants.STANDARD_ROLE_NAME, Constants.STANDARD_ROLE_DESCRIPTION, Constants.STANDARD_ROLE_LEVEL);
			role2_advanced = new Role(Constants.ADVANCED_ROLE_NAME, Constants.ADVANCED_ROLE_DESCRIPTION, Constants.ADVANCED_ROLE_LEVEL);
			role3_admin = new Role(Constants.ADMIN_ROLE_NAME, Constants.ADMIN_ROLE_DESCRIPTION, Constants.ADMIN_ROLE_LEVEL);
			role4_superuser = new Role(Constants.SUPERUSER_ROLE_NAME, Constants.SUPERUSER_ROLE_DESCRIPTION, Constants.SUPERUSER_ROLE_LEVEL);
			
			// Create dei Ruoli che devono già essere fissati come setup del sistema
			userAdminController.createRole(role1_standard);
			userAdminController.createRole(role2_advanced);
			userAdminController.createRole(role3_admin);
			userAdminController.createRole(role4_superuser);

			// Get Role List
			roleIdList = userAdminController.getRoleIdList();
			Assert.assertTrue(roleIdList.size() == 4);
		}

		role1_standard = userAdminController.readRole(roleIdList.get(0));
		role2_advanced = userAdminController.readRole(roleIdList.get(1));
		role3_admin = userAdminController.readRole(roleIdList.get(2));
		role4_superuser = userAdminController.readRole(roleIdList.get(3));


	}

	
	/**
	 * Test
	 */
	@Test
	public void test_sutmanager() throws Exception {

		
		// Create a temporary User	
		User tempUser = new User("Temp", "User4SUT", "tempuser.forsut@enea.it", "xuser");
		Long idTempUser = userProfileController.registration(tempUser, role1_standard);
		
		// Login
		tempUser = userProfileController.login(tempUser.geteMail(), tempUser.getPassword());
		idTempUser = tempUser.getId();
		
		Assert.assertNotNull(tempUser);
		Assert.assertTrue(idTempUser > 0);
		
		
		// 1. READ SUT LIST
		List<SUT> sutList = tempUser.getSutList();
		Assert.assertTrue(sutList.size() == 0);
		

		// 2. CREATE two generic SUTs
		SUTInteraction interaction = new SUTInteraction(SUTConstants.INTERACTION_WEBSITE);
		SUT sut1 = new SUT("sut1", SUTConstants.SUT_TYPE1_DOCUMENT, interaction, "XML document1 uploaded by web interface");
		Long sutId1 = sutManagerController.createSUT(sut1, tempUser);
		
		if (sutId1 < 0) {
			
			sut1 = sutManagerController.readSUTByName(sut1.getName());
			sutId1 = sut1.getId();
		}
		Assert.assertNotNull(sut1);
		Assert.assertTrue(sutId1 > 0);
		Assert.assertTrue(sut1.getInteraction().getType().equals(SUTConstants.INTERACTION_WEBSITE)); 

		SUT sut2 = new SUT("sut2", SUTConstants.SUT_TYPE1_DOCUMENT, interaction, "XML document2 uploaded by web interface");	
		Long sutId2 = sutManagerController.createSUT(sut2, tempUser);
		
		if (sutId2 < 0) {
			
			sut2 = sutManagerController.readSUTByName(sut2.getName());
			sutId2 = sut2.getId();
		}
		Assert.assertNotNull(sut2);
		Assert.assertTrue(sutId2 > 0);
		
		
		// 3.1 READ SUT (to prepare the updating)
		sut1 = sutManagerController.readSUT(sutId1);
		Assert.assertNotNull(sut1);
		//userProfileController.addSUTToUser(sut1.getId(), tempUser.getId());
		
		sut2 = sutManagerController.readSUT(sutId2);
		Assert.assertNotNull(sut2);
		//userProfileController.addSUTToUser(sut2.getId(), tempUser.getId());		

		tempUser = userProfileController.login(tempUser.geteMail(), tempUser.getPassword());
		
		// 1. READ SUT LIST
		sutList = tempUser.getSutList();
		Assert.assertTrue(sutList.size() == 2);
		
		// 3.2 UPDATE SUT
		sut1.setDescription(Constants.SUT_DESCRIPTION);		
		Boolean b = sutManagerController.updateSUT(sut1);
		Assert.assertTrue(b);
		sut1 = sutManagerController.readSUT(sutId1);
		Assert.assertTrue(sut1.getDescription().equals(Constants.SUT_DESCRIPTION));
		
		
		// 4. DELETE SUT
		b = sutManagerController.deleteSUT(sutId1);
		Assert.assertTrue(b);
		sut1 = sutManagerController.readSUT(sutId1);
		Assert.assertNull(sut1);
		
		
		// 5. Does User DELETING imply SUT DELETING? YES
		b = userAdminController.deleteUser(tempUser.getId());
		Assert.assertTrue(b);
		sut2 = sutManagerController.readSUT(sutId2);
		Assert.assertNull(sut2);			
	}
	
	@AfterClass
	public static void after_sutManager() throws Exception {

		Boolean deleting;
		
/*		// Get Role List
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
		}	*/	

		
		List<Long> userIdList = userAdminController.getUserIdList();
		
		User tempUser;
		Long tempUserId;
		for (int u=0;u<userIdList.size();u++) {
			
			tempUserId = (Long) userIdList.get(u);
			Assert.assertTrue(tempUserId.intValue() > 0);

			tempUser = userAdminController.readUser(tempUserId);			
			Assert.assertNotNull(tempUser);
						
			// DELETE User
			if (tempUser.getRole().getLevel() != role4_superuser.getLevel() ) {			
				deleting = userAdminController.deleteUser(tempUser.getId());
				Assert.assertTrue(deleting);			
			}
		}
		
		
	}
}




