package it.enea.xlab.tebes.testplan;

import it.enea.xlab.tebes.entity.TestPlan;

import javax.naming.InitialContext;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestPlanManagerImplITCase {

	// Interface Declaration
	TestPlanManagerRemote testPlanManagerBean;
	
	// Global Variables
	//
	
	
	/**
	 * Before (Test1): Setup 
	 */
	@Before
	public void t1_setupTestPlan() throws Exception {
		

		// Create EJB linked to interface UserManagerRemote
		InitialContext ctx = new InitialContext();
		testPlanManagerBean = (TestPlanManagerRemote) ctx.lookup("TeBES-ear/TestPlanManagerImpl/remote");
		
		//TestPlan testPlan = testPlanManagerBean.importTestPlan(Properties.TEMP_TESTPLAN_FILENAME, Properties.TEMP_USER);
		String testPlanXMLString = "<testPlanXMLString />";
		
		
		TestPlan testPlan = new TestPlan(testPlanXMLString, "dsdsd", "dsdsd", "dsdsd");
		Long testPlanId = testPlanManagerBean.createTestPlan(testPlan);
		Assert.assertTrue(testPlanId > 0);
		
	}
	
	
	
	/**
	 * Test2: User Registration
	 */
	@Test
	public void t2_Check() {
		
		Assert.assertNotNull(testPlanManagerBean);
		
	}

}




