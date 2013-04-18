package it.enea.xlab.tebes.action;

import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestPlan;

import javax.ejb.Remote;

@Remote
public interface ActionManagerRemote {


	public Long createWorkflow(ActionWorkflow workflow);
	public ActionWorkflow readWorkflow(Long id);
	public Boolean deleteWorkflow(Long id);

	
	public Long createAction(Action action, Long workflowId);
	public Action readAction(Long id);
	public Boolean deleteAction(Long id);
	
	
	public void addActionToWorkflow(Long actionId, Long workflowId);
	
	
	public boolean runAction(Action action, Session session);
	public boolean runWorkflow(ActionWorkflow workflow, Session session);
	

}
