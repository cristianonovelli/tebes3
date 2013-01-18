package it.enea.xlab.tebes.testplan;

import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.dao.TeBESDAO;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.TestPlan;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TestPlanManagerImpl implements TestPlanManagerRemote {

	@PersistenceContext(unitName="TeBESPersistenceLayer")
	private EntityManager eM; 
	
	
	public TestPlan readTestPlan(Long id) {
		
		return eM.find(TestPlan.class, id);
	}	
	
	
	/**
	 * CREATE Test Plan
	 * La createTestPlan crea un nuovo TestPlan solo se non esistono per questo utente TestPlan con lo stesso datetime
	 * @return id del TestPlan se viene creato
	 * @return -1 altrimenti
	 * @return -2 se per esempio
	 */
	public Long createTestPlan(TestPlan testPlan) {
		
		Long result;
		
		List<TestPlan> testPlanList = readTestPlanByUserIdAndDatetime(testPlan.getUserId(), testPlan.getDatetime());
		
		if ( (testPlanList == null) || (testPlanList.size() == 0) ) {
			eM.persist(testPlan);	
			result = testPlan.getId();
		}
		else
			result = new Long(-1);
			
		return result;	
	}

	
	/**
	 * UPDATE Test Plan
	 */
	public Boolean updateTestPlan(TestPlan testPlan) {
		
		Boolean result = false;
		
		 if ( (testPlan != null) && (testPlan.getId() != null) ) {
			 testPlan = eM.merge(testPlan);
			 //eM.persist(user);
			 
			 if (testPlan != null)
				 result = true;
		 }
		
		return result;
	}

	
	
	@SuppressWarnings("unchecked")
	public List<TestPlan> readTestPlanByUserIdAndDatetime(Long userId, String datetime) {

        String queryString = "SELECT t FROM TestPlan AS t WHERE t.userId = ?1 AND t.datetime = ?2";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, userId);
        query.setParameter(2, datetime);
        
        return query.getResultList();
	}

	
/*	public String createTestPlan(TestPlan testPlan) {
		
		List<TestPlan> testPlanList = this.findTestPlanById(testPlan.getTestPlanId());
		
		if ( (testPlanList == null) || (testPlanList.size() == 0) ) {

			eM.persist(testPlan);
			
		}
		else {
			testPlan = testPlanList.get(0);
		}
		
		return testPlan.getTestPlanId();
	}
*/
	



	@SuppressWarnings("unchecked")
	private List<TestPlan> findTestPlanById(String testPlanId) {
		
        String queryString = "SELECT t FROM TestPlan AS t WHERE t.testPlanId = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, testPlanId);
        return query.getResultList();
	}




	public TestPlan getTestPlanFromXML(String testPlanAbsFileName) {
		
		TestPlan testPlan = null;	
		
		try {

			TestPlanDOM testPlanDOM = new TestPlanDOM(testPlanAbsFileName);			
			
			if ( testPlanDOM.root == null ) {
				System.out.println("XReport: " + testPlanDOM.getReport().getErrorMessage());	
			}
			else
				testPlan = new TestPlan(new Long(testPlanDOM.getRootUserIDAttribute()), testPlanDOM.getXMLString(), testPlanDOM.getRootDatetimeAttribute(), testPlanDOM.getRootStateAttribute(), testPlanAbsFileName);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
		return testPlan;
	}


	
	public Vector<Action> getActionsFromXML(Long testPlanId) throws SAXException, ParserConfigurationException, IOException {
		
		Vector<Action> actionsList = new Vector<Action>();
		
		TestPlan testPlan = readTestPlan(testPlanId);
				//findTestPlanByTestPlanId(testPlanId);
	
		TestPlanDOM testPlanDOM = new TestPlanDOM();
		testPlanDOM.setContent(testPlan.getXml());
		
		// Ottengo lista actions
		NodeList actionNodes = testPlanDOM.getTestActionNodeList();

		// Ciclo su ogni azione
		Element actionElement = null;
		for (int i = 0; i < actionNodes.getLength(); i++) {
			
			actionElement = (Element) actionNodes.item(i);
			
			//String actionId = testPlanDOM.getIdAttribute(actionElement);	
			int number = new Integer(testPlanDOM.getNumberAttribute(actionElement)).intValue();
			
			String name = testPlanDOM.getActionName(actionElement);
			String description = testPlanDOM.getActionDescription(actionElement);				
			
			Node testNode = testPlanDOM.getTestNode(actionElement);
			String lg = testPlanDOM.getLgAttribute(testNode);

			String type = testPlanDOM.getTypeAttribute(testNode);		
			String value = testNode.getFirstChild().getNodeValue();				
			String jumpString = testPlanDOM.getJumpAttribute(testNode);		
			boolean jump;
			if (jumpString.equals("true"))
				jump = true;
			else 
				jump = false;
			
			String location = testPlanDOM.getLocationAttribute(testNode);

			location = TeBESDAO.url2localLocation(location);

			Action action = new Action(number, name, lg, type, location, value, jump, description);
			//Long actionId = this.insertAction(action);
			
			System.out.println(action.getActionSummaryString());
			
			actionsList.add(action);
			
		}	
		
		return actionsList;
	}

	

/*	public Long insertAction(Action action) {
		
			eM.persist(action);
			return action.getId();
	}*/


/*	public TestPlan findTestPlanByTestPlanId(String testPlanId) {
		
        String queryString = "SELECT tp FROM TestPlan AS tp WHERE tp.testPlanId = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, testPlanId);
        @SuppressWarnings("unchecked")
		List<TestPlan> resultList = query.getResultList();
        if ((resultList != null) && (resultList.size() > 0))
        	return (TestPlan) resultList.get(0);
        else
        	return null;
	}*/


	public Boolean updateWorkflow(ActionWorkflow workflow) {
		
		Boolean result = false;

		 if ( (workflow != null) && (workflow.getId() != null) ) {
			 workflow = eM.merge(workflow);
			 //eM.persist(user);
			 
			 if (workflow != null)
				 result = true;
		 }

		 return result;
	}


	public Long insertWorkflow(ActionWorkflow workflow, Long testPlan_id) {
		
			TestPlan tp = this.readTestPlan(testPlan_id);
			if (tp.getWorkflow() == null) {
				eM.persist(workflow);
				return workflow.getId();
			}
			else return new Long(-1);
	}


	public ActionWorkflow readWorkflow(Long workflowId) {
		
		return eM.find(ActionWorkflow.class, workflowId);
	}


	public Long createAction(Action action) {

		Action existingAction = this.findActionByName(action.getActionName());
		
		if (existingAction == null) {
			eM.persist(action);		
			return action.getId();
		}
		else 
			return new Long(-1);
	}

	private Action findActionByName(String name) {
		
	    String queryString = "SELECT a FROM Action AS a WHERE a.actionName = ?1";
	    
	    Query query = eM.createQuery(queryString);
	    query.setParameter(1, name);
	    @SuppressWarnings("unchecked")
		List<Action> resultList = query.getResultList();
	    if ((resultList != null) && (resultList.size() > 0))
	    	return (Action) resultList.get(0);
	    else
	    	return null;
	}


	public Action readAction(Long id_action) {
		
		return eM.find(Action.class, id_action);
	}


	public void addActionToWorkflow(Long actionId, Long workflowId) {

		ActionWorkflow wf = this.readWorkflow(workflowId);
		Action a = this.readAction(actionId);

		a.addToWorkflow(wf);
		eM.persist(wf);

		return;
	}


	public void addWorkflowToTestPlan(Long workflowId, Long testPlanId) {
		
		ActionWorkflow workflow = this.readWorkflow(workflowId);
		TestPlan testPlan = this.readTestPlan(testPlanId);

		workflow.addToTestPlan(testPlan);
		eM.persist(testPlan);
		
		return;
	}


	






	/*public void addActionToWorkflow(Long workflowId, Long actionId) {

		User user = this.readUser(userId);
		SUT sut = this.readSUT(sutId);

		sut.addToUser(user);
		eM.persist(user);

		return;
	}*/

}




