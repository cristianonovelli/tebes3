package it.enea.xlab.tebes.action;

import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.TestPlan;

import javax.ejb.Local;

@Local
public interface ActionManagerLocal {

	public boolean execute(Action action);
	
	public boolean executeActionWorkflow(ActionWorkflow workflow);
}
