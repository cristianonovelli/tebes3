package it.enea.xlab.tebes.testplan;

import java.util.List;

import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.TestPlan;

import javax.ejb.Remote;

@Remote
public interface TestPlanManagerRemote {

	//public TestPlanOLD importTestPlan(String testPlanFilePath, String userId);
	
	public TestPlan readTestPlan(Long id);
	
	public TestPlan getTestPlanFromXML(String testPlanAbsFileName);

	public Long createTestPlan(TestPlan testPlan);

	public List<Action> getActionsFromXML(Long testPlanId);

	//public TestPlan findTestPlanByTestPlanId(String testPlanId);

	public Boolean updateWorkflow(ActionWorkflow workflow);
	public Long insertWorkflow(ActionWorkflow workflow, Long testPlan_id);
	public ActionWorkflow readWorkflow(Long workflowId);

	public Long createAction(Action action);

	public Action readAction(Long id_action);

	public void addAction(Long id, Long id2);

	public void addWorkflow(Long id, Long id2);
	
	
	
}
