package it.enea.xlab.tebes.sut;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.Interaction;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.users.UserAdminController;
import it.enea.xlab.tebes.users.UserProfileController;

import java.util.List;

import org.apache.bcel.classfile.Constant;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
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

		// Get SUTManager Service
		sutManagerController = new SUTManagerController();			
		Assert.assertNotNull(sutManagerController);
		
		// Get UserAdmin Service
		userAdminController = new UserAdminController();
		Assert.assertNotNull(userAdminController);

		// Get UserProfile service for the Test
		userProfileController = new UserProfileController();
		Assert.assertNotNull(userProfileController);
		
		
		// Prepare 4 user Roles 
		role1_standard = new Role(Constants.STANDARD_ROLE_NAME, Constants.STANDARD_ROLE_DESCRIPTION, Constants.STANDARD_ROLE_LEVEL);
		role2_advanced = new Role(Constants.ADVANCED_ROLE_NAME, Constants.ADVANCED_ROLE_DESCRIPTION, Constants.ADVANCED_ROLE_LEVEL);
		role3_admin = new Role(Constants.ADMIN_ROLE_NAME, Constants.ADMIN_ROLE_DESCRIPTION, Constants.ADMIN_ROLE_LEVEL);
		role4_superuser = new Role(Constants.SUPERUSER_ROLE_NAME, Constants.SUPERUSER_ROLE_DESCRIPTION, Constants.SUPERUSER_ROLE_LEVEL);
		
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
		Interaction interaction = new Interaction(Constants.INTERACTION_WEBSITE);
		SUT sut1 = new SUT("sut1", Constants.SUT_TYPE1_DOCUMENT, Constants.UBL, Constants.UBLSCHEMA, interaction, "XML document1 uploaded by web interface");
		Long sutId1 = sutManagerController.createSUT(sut1, tempUser);
		
		if (sutId1 < 0) {
			
			sut1 = sutManagerController.readSUTByName(sut1.getName());
			sutId1 = sut1.getId();
		}
		Assert.assertNotNull(sut1);
		Assert.assertTrue(sutId1 > 0);
		Assert.assertTrue(sut1.getInteraction().getType().equals(Constants.INTERACTION_WEBSITE)); 

		SUT sut2 = new SUT("sut2", Constants.SUT_TYPE1_DOCUMENT, Constants.UBL, Constants.UBLSCHEMA, interaction, "XML document2 uploaded by web interface");	
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
		userProfileController.addSUTToUser(sut1.getId(), tempUser.getId());
		
		sut2 = sutManagerController.readSUT(sutId2);
		Assert.assertNotNull(sut2);
		userProfileController.addSUTToUser(sut2.getId(), tempUser.getId());		

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
		
		// Last Check
		// Sono stati eliminati tutti gli utenti?
		List<Long> userIdList = userAdminController.getUserIdList();
		userIdList = userAdminController.getUserIdList();
		Assert.assertTrue(userIdList.size() == 0);
		
	}
}




