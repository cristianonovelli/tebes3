package it.enea.xlab.tebes.action;

import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.TestPlan;

import javax.ejb.Remote;

@Remote
public interface ActionManagerRemote {

	public Long createAction(Action action);
	
	public Long createWorkflow(ActionWorkflow workflow);
	
	
	public void addActionToWorkflow(Long actionId, Long workflowId);
	
	//public Long readWorkflowByTestPlan(TestPlan tp);

	public Boolean deleteAction(Long id);

	public Boolean deleteWorkflow(Long id);
	
/*	public boolean runAction(Action action);
	
	public boolean runWorkflow(ActionWorkflow workflow);

	public Action readAction(Long id);
	
	
	public ActionWorkflow readWorkflow(Long id);*/
}
