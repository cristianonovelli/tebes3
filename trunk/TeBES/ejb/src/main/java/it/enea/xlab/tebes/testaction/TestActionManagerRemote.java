package it.enea.xlab.tebes.testaction;

import javax.ejb.Remote;

import it.enea.xlab.tebes.model.Action;
import it.enea.xlab.tebes.model.TestPlanOLD;

@Remote
public interface TestActionManagerRemote {

	public boolean execute(Action action);
	
	public boolean executeActionWorkflow(TestPlanOLD testPlan);
}
