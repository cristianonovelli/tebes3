package it.enea.xlab.tebes.testaction;

import javax.ejb.Local;

import it.enea.xlab.tebes.model.Action;
import it.enea.xlab.tebes.model.TestPlan;

@Local
public interface TestActionManagerLocal {

	public boolean execute(Action action);
	
	public boolean executeActionWorkflow(TestPlan testPlan);
}
