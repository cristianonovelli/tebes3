package it.enea.xlab.tebes.testplan;

import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.ejb.Remote;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

@Remote
public interface TestPlanManagerRemote {

	//public TestPlanOLD importTestPlan(String testPlanFilePath, String userId);
	
	public TestPlan readTestPlan(Long id);
	public List<TestPlan>  readTestPlanByDatetimeAndUserId(String datetime, Long userId);
	public Vector<String> getSystemXMLTestPlanList();
	public List<TestPlan> readUserTestPlanList(User user);
	public List<TestPlan> readSystemTestPlanList();	
	
	public TestPlan getTestPlanFromXML(String testPlanAbsFileName);

	public Long createTestPlan(TestPlan testPlan, Long userId);
	
	public Boolean updateTestPlan(TestPlan testPlan);
	public Boolean deleteTestPlan(Long testPlanId);
	
	public List<Action> getActionsFromXML(Long testPlanId) throws FileNotFoundException, SAXException, ParserConfigurationException, IOException;

	//public TestPlan findTestPlanByTestPlanId(String testPlanId);

	public Boolean updateWorkflow(ActionWorkflow workflow);
	public Long insertWorkflow(ActionWorkflow workflow, Long testPlan_id);
	public ActionWorkflow readWorkflow(Long workflowId);

	//public Long createAction(Action action);

	//public Action readAction(Long id_action);

	//public void addActionToWorkflow(Long actionId, Long workflowId);

	public void addWorkflowToTestPlan(Long workflowId, Long testPlanId);
	public Long addTestPlanToUser(Long testPlanId, Long userId);


	
	
	
	
}

