package it.enea.xlab.tebes.testaction;

import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;

import javax.ejb.Remote;

@Remote
public interface TestActionManagerRemote {

	public boolean execute(Action action);
	
	public boolean executeActionWorkflow(ActionWorkflow workflow);
}
