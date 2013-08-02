package it.enea.xlab.tebes.testplan;

import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface TestPlanManagerRemote {

	//public TestPlanOLD importTestPlan(String testPlanFilePath, String userId);
	
	public TestPlan readTestPlan(Long id);
	public List<TestPlan>  readTestPlanByDatetimeAndUserId(String datetime, Long userId);
	//public Vector<String> getSystemTestPlanFileList();

	public List<TestPlan> readUserTestPlanList(User user);
	//public List<TestPlan> readSystemTestPlanList();	
	
	public TestPlan getTestPlanFromXML(String testPlanAbsFileName);

	public Long createTestPlan(TestPlan testPlan, Long userId);
	public Long cloneTestPlan(TestPlan testPlan, Long userId);
	
	public Boolean updateTestPlan(TestPlan testPlan);
	public Boolean deleteTestPlan(Long testPlanId);
	
	//public List<Action> getActionsFromXML(Long testPlanId) throws FileNotFoundException, SAXException, ParserConfigurationException, IOException;

	//public TestPlan findTestPlanByTestPlanId(String testPlanId);


	public Long insertWorkflow(ActionWorkflow workflow, Long testPlan_id);
	public ActionWorkflow readWorkflow(Long workflowId);

	//public Long createAction(Action action);

	//public Action readAction(Long id_action);

	//public void addActionToWorkflow(Long actionId, Long workflowId);

	public void addWorkflowToTestPlan(Long workflowId, Long testPlanId);
	public Long addTestPlanToUser(Long testPlanId, Long userId);
	public boolean importSystemTestPlanFiles(User superUser);
	public List<TestPlan> getSystemTestPlanList();
	

	
	
}

