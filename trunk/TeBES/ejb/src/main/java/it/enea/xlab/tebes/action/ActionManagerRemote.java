package it.enea.xlab.tebes.action;

import java.io.IOException;

import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionDescription;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.GUIDescription;
import it.enea.xlab.tebes.entity.InputDescription;
import it.enea.xlab.tebes.entity.TestPlanDescription;
import it.enea.xlab.tebes.entity.Input;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Session;

import javax.ejb.Remote;

@Remote
public interface ActionManagerRemote {

	
	public Long createWorkflow(ActionWorkflow workflow);
	public ActionWorkflow readWorkflow(Long id);
	public Boolean deleteWorkflow(Long id);

	
	public Long createAction(Action action, Long workflowId);
	public Action readAction(Long id);
	public Boolean deleteAction(Long id);
	
	public Long createInput(Input input, Long actionId);
	public Input readInput(Long id);
	public void addInputToAction(Long inputId, Long actionId);
	
	public void addActionToWorkflow(Long actionId, Long workflowId);
	
	
	public Report runAction(Action action, Session session);
	

	
	public Boolean updateWorkflow(ActionWorkflow workflow);
	public boolean updateInput(Input input);
	public Boolean checkActionReady(Action action);
	public Long createActionDescription(ActionDescription actionDescription,
			Long actionId);
	public Long createInputDescription(InputDescription inputDescription,
			Long inputId);
	public Long createGUIDescription(GUIDescription guiDescription, Long inputId);

	

}
