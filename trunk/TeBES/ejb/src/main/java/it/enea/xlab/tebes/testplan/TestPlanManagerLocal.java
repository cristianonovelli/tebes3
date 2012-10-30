package it.enea.xlab.tebes.testplan;

import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.model.TestPlanOLD;

import javax.ejb.Local;

@Local
public interface TestPlanManagerLocal {

	public TestPlanOLD importTestPlan(String testPlanFilePath, String userId);
	
	public TestPlanDOM getTestPlanDOM();
	
	public Long createTestPlan(TestPlan testPlan);
}