package it.enea.xlab.tebes.testaction;

import javax.ejb.Local;

import it.enea.xlab.tebes.model.Action;
import it.enea.xlab.tebes.model.TestPlanOLD;

@Local
public interface TestActionManagerLocal {

	public boolean execute(Action action);
	
	public boolean executeActionWorkflow(TestPlanOLD testPlan);
}
