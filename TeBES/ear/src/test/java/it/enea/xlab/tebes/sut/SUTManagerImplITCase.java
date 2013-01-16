package it.enea.xlab.tebes.sut;

import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.users.UserAdminController;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
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
	SUTManagerController sutManagerController;
	UserAdminController adminController;
	
	// Global Variables
	User tempUser;
	Long idTempUser;
	
	
	/**
	 * Before: Prepare SUTManagerController
	 * @throws Exception 
	 */
	@Before
	public void before_sutManagerController() throws Exception {
		
		// Get SUTManagerController Service
		sutManagerController = new SUTManagerController();			
		Assert.assertNotNull(sutManagerController);
		
		// Create a temporary User to associate to SUT
		adminController = new UserAdminController();
		tempUser = new User("Temp", "User4SUT", "tempuser.forsut@enea.it", "xuser");
		idTempUser = adminController.createUser(tempUser, null);
		tempUser = adminController.readUser(idTempUser);
		Assert.assertNotNull(tempUser);
	}

	
	/**
	 * Test
	 */
	@Test
	public void test_sutmanager() throws Exception {

		// 1. READ SUT LIST
		List<SUT> sutList = tempUser.getSutList();
		Assert.assertTrue(sutList.size() == 0);
		
		// 2. CREATE two generic SUTs
		SUT sut1 = new SUT("sut1", "xmldocument1", "XML document uploaded by web interface");
		SUT sut2 = new SUT("sut2", "xmldocument2", "XML document uploaded by web interface");
		Long sutId1 = sutManagerController.createSUT(sut1, tempUser);
		Long sutId2 = sutManagerController.createSUT(sut2, tempUser);
		Assert.assertTrue(sutId1 > 0);
		Assert.assertTrue(sutId2 > 0);
		
		// 3.1 READ SUT (to prepare the updating)
		sut1 = sutManagerController.readSUT(sutId1);
		Assert.assertNotNull(sut1);
		
		// 3.2 UPDATE SUT
		sut1.setDescription("u");		
		Boolean b = sutManagerController.updateSUT(sut1);
		Assert.assertTrue(b);
		sut1 = sutManagerController.readSUT(sutId1);
		Assert.assertTrue(sut1.getDescription().equals("u"));
		
		// 4. DELETE SUT
		b = sutManagerController.deleteSUT(sutId1);
		Assert.assertTrue(b);
		sut1 = sutManagerController.readSUT(sutId1);
		Assert.assertNull(sut1);
		
		// 5. Does User DELETING imply SUT DELETING?
		adminController.deleteUser(tempUser.getId());
		sut2 = sutManagerController.readSUT(sutId2);
		
		// TODO Assert.assertNull(sut2);
		// Per il momento lo cancello a mano ma è da gestire
		b = sutManagerController.deleteSUT(sutId2);
		sut2 = sutManagerController.readSUT(sutId2);
		Assert.assertNull(sut2);		

	}
	
}




