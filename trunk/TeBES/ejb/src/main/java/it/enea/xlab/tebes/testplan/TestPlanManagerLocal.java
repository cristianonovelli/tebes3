package it.enea.xlab.tebes.testplan;

import it.enea.xlab.tebes.model.TestPlan;

import javax.ejb.Local;

@Local
public interface TestPlanManagerLocal {

	public TestPlan importTestPlan(String testPlanFilePath, String userId);
	
	public TestPlanDOM getTestPlanDOM();
}
