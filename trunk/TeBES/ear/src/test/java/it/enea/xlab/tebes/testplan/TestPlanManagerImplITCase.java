package it.enea.xlab.tebes.testplan;

import it.enea.xlab.tebes.common.Properties;
import it.enea.xlab.tebes.entity.TestPlan;

import javax.naming.InitialContext;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestPlanManagerImplITCase {

	// Interface Declaration
	TestPlanManagerRemote testPlanManagerBean;
	
	
	/**
	 * Before (Test1): Setup 
	 */
	@Before
	public void before() throws Exception {
		

		// Create EJB linked to interface UserManagerRemote
		InitialContext ctx = new InitialContext();
		testPlanManagerBean = (TestPlanManagerRemote) ctx.lookup("TeBES-ear/TestPlanManagerImpl/remote");
	
		

		
	}
	
	

	
	/**
	 * Test1: User Registration
	 */
	@Test
	public void t1_check() {
		
		Assert.assertNotNull(testPlanManagerBean);
		
	}

	/**
	 * Test1: User Registration
	 */
	@Test
	public void t2_setupTestPlan() {
		
		Assert.assertEquals("C:/Java/jboss-4.2.3.GA/server/default/data/TeBES/users/IT-12345678909/testplans/TP_IT-12345678909_2012-06-04T184300.xml", 
				Properties.TeBES_TESTPLAN_ABSFILENAME);
		

	}

	@Test
	public void t3_setupTestPlan() {	
		// Get TeBES Test Plan	
		TestPlan testPlan = testPlanManagerBean.getTestPlanFromXML(Properties.TeBES_TESTPLAN_ABSFILENAME);
		
		Assert.assertNotNull(testPlan);
		Assert.assertEquals("2012-06-13T18:43:00", testPlan.getDatetime());
		Assert.assertEquals("draft", testPlan.getState());
		
		String tempTestPlanId = testPlan.getTestPlanId();
		
		// Create
		String testPlanId = testPlanManagerBean.createTestPlan(testPlan);
		Assert.assertEquals(tempTestPlanId, testPlanId);		
	}
}




