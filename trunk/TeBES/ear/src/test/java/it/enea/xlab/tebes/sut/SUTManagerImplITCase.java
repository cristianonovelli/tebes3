package it.enea.xlab.tebes.sut;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
		//sutManagerController.createSUT(sut, user)
	}

	
	
}




