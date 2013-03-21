package it.enea.xlab.tebes.action;

import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;

import javax.ejb.Remote;

@Remote
public interface ActionManagerRemote {

	public boolean execute(Action action);
	
	public boolean executeActionWorkflow(ActionWorkflow workflow);
}
