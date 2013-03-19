package it.enea.xlab.tebes.testplan;

import it.enea.xlab.tebes.common.Constants; 
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.dao.TeBESDAO;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.Group;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.users.UserManagerImpl;
import it.enea.xlab.tebes.users.UserManagerRemote;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xlab.file.XLabFileManager;
import org.xlab.utilities.XLabDates;
import org.xml.sax.SAXException;

@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TestPlanManagerImpl implements TestPlanManagerRemote {

	@PersistenceContext(unitName=Constants.PERSISTENCE_CONTEXT)
	private EntityManager eM; 
	
	// Logger
	private static Logger logger = Logger.getLogger(TestPlanManagerImpl.class);
	
	@EJB
	private UserManagerRemote userManager; 
	
	public TestPlan readTestPlan(Long id) {
		
		return eM.find(TestPlan.class, id);
	}	
	
	
	/**
	 * CREATE Test Plan
	 * La createTestPlan crea un nuovo TestPlan solo se non esistono per questo utente TestPlan con lo stesso datetime
	 * @return 	id of TestPlan if created
	 * 			-1 if a persist exception occurs
	 * 			-2 if an addTestPlanToUser error occours
	 */
	public Long createTestPlan(TestPlan testPlan, Long userId) {

		TestPlan testPlanResult = null;
		
		
		
		// sto creando un testplan e quindi creo un nuovo datetime!
		// N.B. a meno che non sia l'import iniziale dello superuser!
		//List<TestPlan> testPlanList = this.readTestPlanByDatetimeAndUserId(testPlan.getDatetime(), userId);

			//if ( (testPlanList == null) || (testPlanList.size() == 0) ) {
				
				try {

					String datetime = XLabDates.getCurrentUTC();
					
					
					if ( testPlan.getId() != null ) {
							TestPlan testPlan2 = new TestPlan(testPlan.getXml(), datetime, Constants.STATE_DRAFT, null);
							testPlanResult = testPlan2;
					}
					else {
						
						TestPlan testPlan1 = testPlan;
						testPlan.setDatetime(datetime);
						testPlan.setLocation(null);
						testPlanResult = testPlan1;
					}
						
					

					
					// C'E' PROPRIO BISOGNO DI INDICARE USERID E TESTPLANID NELL'XML??????????????
					// O IN ALTRE PAROLE, C'è BISOGNO DI SINCRONIZZARLI?
					// NO
					
					/*	try {
						// Create TestPlan DOM
					TestPlanDOM tpDOM = new TestPlanDOM();
						// Set imported XML into the DOM
						tpDOM.setContent(testPlan.getXml());
						

						
						tpDOM.setNodeAttribute(tpDOM.getRootElement(), Constants.ID_XMLATTRIBUTE_LABEL, testPlan.getId().toString());
						// Set new TestPlan/@userID
						tpDOM.setNodeAttribute(tpDOM.getRootElement(), Constants.USERID_XMLATTRIBUTE_LABEL, userId.toString());
						// Set new TestPlan/@datetime
						tpDOM.setNodeAttribute(tpDOM.getRootElement(), Constants.DATETIME_XMLATTRIBUTE_LABEL, datetime);
						// Set DOM into the TestPlan structure
						testPlan.setXml(tpDOM.getXMLString());
						
					} catch (TransformerFactoryConfigurationError e) {
						e.printStackTrace();
						return new Long(-3);	
					}*/
					
					eM.persist(testPlanResult);	
					
					// Set new TestPlan/@id
					while (testPlanResult.getId() == null)
						Thread.sleep(50);
					
					// Add Test Plan to User
					Long adding = this.addTestPlanToUser(testPlanResult.getId(), userId);
					
					
					
					if (adding > 0) {	
						return testPlanResult.getId();
					}
					else
						return new Long(-2);							
				}
				catch(Exception e) {
					e.printStackTrace();
					return new Long(-1);
				}
			//}
			//else
			//	return new Long(-3);
				

	}

	
	@SuppressWarnings("unchecked")
	public Vector<TestPlan> readUserTestPlanList(User user) {

		Vector<TestPlan> testPlanListResult = new Vector<TestPlan>();
		
        String queryString = "SELECT t FROM TestPlan AS t";
        
        Query query = eM.createQuery(queryString);
        List<TestPlan> tempTestPlanList = query.getResultList();
        
        for (int i=0; i<tempTestPlanList.size(); i++)         	
        	if (tempTestPlanList.get(i).getUser().getId().intValue()==user.getId().intValue())
        		testPlanListResult.add(tempTestPlanList.get(i));
	
        return testPlanListResult;
	}

	
	
	/**
	 * UPDATE Test Plan
	 */
	public Boolean updateTestPlan(TestPlan testPlan) {
		
		Boolean result = false;
		
		 if ( (testPlan != null) && (testPlan.getId() > 0) ) {
			 
			 testPlan = eM.merge(testPlan);
			 //eM.persist(user);
			 
			 if (testPlan != null)
				 result = true;
		 }
		
		return result;
	}

	
	/**
	 * DELETE TestPlan
	 */
	public Boolean deleteTestPlan(Long testPlanId) {

		TestPlan testPlan = this.readTestPlan(testPlanId);
		
		if (testPlan == null)
			return false;
		
		try {
			
			eM.remove(testPlan);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e2) {
			e2.printStackTrace();
			return null;
		}
		
		return true;
	}

	
	
	
	
	public List<TestPlan> readTestPlanByDatetimeAndUserId(String datetime, Long userId) {

		User user = userManager.readUser(userId);
		List<TestPlan> testPlanList1 = user.getTestPlans();
		List<TestPlan> testPlanList2 = null;
		
		for(int i=0; i<testPlanList1.size(); i++) {
			
			if (testPlanList1.get(i).getDatetime().equals(datetime))
				testPlanList2.add(testPlanList1.get(i));
			
		}
		
        return testPlanList2;
	}
	



	@SuppressWarnings("unchecked")
	private List<TestPlan> readTestPlanById(String testPlanId) {
		
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
				testPlan = new TestPlan(testPlanDOM.getXMLString(), testPlanDOM.getRootDatetimeAttribute(), testPlanDOM.getRootStateAttribute(), testPlanAbsFileName);
			
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


	public Vector<String> getSystemXMLTestPlanList() {

		Vector<String> result = null;
		
		// L'utente deve scegliere il TestPlan da importare: 
		// Get TeBES testPlan1 absolute PathName from .properties file
		String systemTestPlanAbsDirName = null;
		
		try {		
			systemTestPlanAbsDirName = PropertiesUtil.getSuperUserTestPlanDir();
			
			if (XLabFileManager.isFileOrDirectoryPresent(systemTestPlanAbsDirName) ) 

			result = XLabFileManager.getFileList(systemTestPlanAbsDirName);

		} catch (Exception e) {
			
			result = null;		
		}	
		
		return result;
	}



	/**
	 * ADD TestPlan to User
	 * @return 	 1 ok
	 * 			-1 exception1 read error
	 * 			-2 exception2 persist error
	 */
	public Long addTestPlanToUser(Long testPlanId, Long userId) {

		try {
			
			User user = userManager.readUser(userId);
			TestPlan testPlan = this.readTestPlan(testPlanId);
			
			// logger.info();
			
			try {
				
				testPlan.setUser(user);
				eM.persist(testPlan);
				
			} catch (Exception e2) {
				e2.printStackTrace();
				return new Long(-2);
			}	
		} catch (Exception e1) {
			e1.printStackTrace();
			return new Long(-1);
		}		
		
		return new Long(1);	
	}

	

	public Vector<TestPlan> readSystemTestPlanList() {
		
		Vector<TestPlan> testPlanListResult = new Vector<TestPlan>();
		
        String queryString = "SELECT t FROM TestPlan AS t";
        
        Query query = eM.createQuery(queryString);
        List<TestPlan> tempTestPlanList = query.getResultList();
        
        Long superUserId = userManager.getSuperUserId();
        for (int i=0; i<tempTestPlanList.size(); i++)         	
        	if (tempTestPlanList.get(0).getUser().getId().intValue()==superUserId.intValue())
        		testPlanListResult.add(tempTestPlanList.get(0));
	
        return testPlanListResult;
	}

		
	

}




