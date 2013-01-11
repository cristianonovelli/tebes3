package it.enea.xlab.tebes.sut;


import org.junit.Assert;
import org.junit.Before;

public class SUTManagerImplITCase {

	// Interface Declaration
	SUTManagerController sutManagerController;
	
	// Global Variables

	
	
	/**
	 * Before: Prepare SUTManagerController
	 * @throws Exception 
	 */
	@Before
	public void before_sutManagerController() throws Exception {
		

		sutManagerController = new SUTManagerController();	
		
		Assert.assertNotNull(sutManagerController);
		
		
		
		
		// 1 create User
		//2 sutManagerController.createSUT(sut, user)
		// update sut
		// readsut		
		// delete user (e a cascata anche il sut, tocheck)
	}

	
	
	// 1. Quali sono le funzioni relative al SUT Manager? 
	// testiamo le CRUD: Create, Read, Update, Delete
	// già presenti in 
	
	// 2. Quali informazioni devono essere presenti nel DB? 
	//prepariamole
	
	// 3. Run Sut Manager Test, CRUD
	// parte da DB vuoto, arriva a DB vuoto.
	
	
}




