package it.enea.xlab.tebes.testplan;

import it.enea.xlab.tebes.model.TestPlan;

import javax.ejb.Remote;

@Remote
public interface TestPlanManagerRemote {

	public TestPlan importTestPlan(String testPlanFilePath, String userId);
	
	public TestPlanDOM getTestPlanDOM();
}
