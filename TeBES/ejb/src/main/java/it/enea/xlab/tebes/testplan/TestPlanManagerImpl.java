package it.enea.xlab.tebes.testplan;

import it.enea.xlab.tebes.action.ActionManagerRemote;
import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.dao.TeBESDAO;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.TestPlanXML;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.users.UserManagerRemote;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.ParserConfigurationException;

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

	@EJB
	private ActionManagerRemote actionManager; 
	
	public TestPlan readTestPlan(Long id) {
		
		return eM.find(TestPlan.class, id);
	}	
	
	
	/**
	 * CREATE Test Plan
	 * Se ha un id settato ne crea una copia, altrimenti ne fa la semplice persistenza
	 * @param testPlanResult 
	 * @return 	id of TestPlan if created
	 * 			-1 if an exception occurs
	 * 			
	 * 
	 */
	public Long createTestPlan(TestPlan testPlan, Long userId) {

		TestPlan testPlan2 = null;

		
		try {

			String datetime = XLabDates.getCurrentUTC();
			
			// TODO creare prima le action e persistere il WF
			if ( testPlan.getId() == null ) {
				
				// Creo il TestPlan SENZA il Workflow
				testPlan2 = new TestPlan(null, datetime, Constants.STATE_DRAFT, testPlan.getLocation(), testPlan.getDescription(), null, null);
				eM.persist(testPlan2);	
				
				// TODO CREATE Workflow
				ActionWorkflow wf = testPlan.getWorkflow();
				ActionWorkflow wf2 = new ActionWorkflow();	
				wf2.setComment(wf.getComment());
				wf2.setActionMark(1);
				Long wf2Id = actionManager.createWorkflow(wf2);
				wf2 = actionManager.readWorkflow(wf2Id);
				
				Vector<Action> actionList = (Vector<Action>) wf.getActions();
				for (int i=0; i<actionList.size(); i++) 					
					actionManager.createAction(actionList.get(i), wf2Id);
				
				// CREATE TestPlanXML
				TestPlanXML tpXML = testPlan.getTestplanxml();
				TestPlanXML tpXML2 = new TestPlanXML();
				tpXML2.setAbsFileName(tpXML.getAbsFileName());
				tpXML2.setXml(tpXML.getXml());
				Long tpXML2Id = this.createTestPlanXML(tpXML2);
				tpXML2 = this.readTestPlanXML(tpXML2Id);
				
				// ADD Workflow and TestPlanXML to TestPlan
				this.addWorkflowToTestPlan(wf2Id, testPlan2.getId());
				this.addTestPlanXMLToTestPlan(tpXML2Id, testPlan2.getId());
				
				
				// ADD TestPlan to User
				this.addTestPlanToUser(testPlan2.getId(), userId);

				return testPlan2.getId();
			}
			else {
				
				// Qui chiamo la CLONE
				return this.cloneTestPlan(testPlan, userId);			
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return new Long(-1);
		}
	}

	
	
	private void addTestPlanXMLToTestPlan(Long testPlanXMLId, Long testPlanId) {

		TestPlanXML tpXML = this.readTestPlanXML(testPlanXMLId);
		TestPlan testPlan = this.readTestPlan(testPlanId);

		//workflow.addToTestPlan(testPlan);
		testPlan.setTestplanxml(tpXML);
		
		eM.persist(testPlan);
		
		return;
	}

	






	private TestPlanXML readTestPlanXML(Long id) {
		
		return eM.find(TestPlanXML.class, id);
	}


	private Long createTestPlanXML(TestPlanXML testPlanXML) {
		
		try {
			eM.persist(testPlanXML);
			return testPlanXML.getId();
		}
		catch(Exception e) {
			return new Long(-1);
		}	
	}


	public Long cloneTestPlan(TestPlan testPlan, Long userId) {
		
		TestPlan testPlanClone;
		
		String datetime = XLabDates.getCurrentUTC();
		
		ActionWorkflow wfClone = new ActionWorkflow(new Vector<Action>());
		wfClone.setComment("clone");	
		
		List<Action> actionList = testPlan.getWorkflow().getActions();
		
		for (int i=0; i<actionList.size(); i++) {				
			
			wfClone.getActions().add(this.cloneAction(actionList.get(i)));		
		}

		TestPlanXML tpXMLClone = this.cloneTestPlanXML(testPlan.getTestplanxml());
		
		testPlanClone = new TestPlan(null, datetime, Constants.STATE_DRAFT, testPlan.getLocation(), testPlan.getDescription(), wfClone, tpXMLClone);
		Long testPlanCloneId = this.createTestPlan(testPlanClone, userId);	

		return testPlanCloneId;
	}


	
	/*private TestPlan cloneTestPlanBean(TestPlan testPlan1) {
		
		return new TestPlan(
				testPlan1.getXml(), 
				testPlan1.getDatetime(), 
				Constants.STATE_DRAFT, 
				testPlan1.getLocation(), 
				testPlan1.getDescription(),
				this.cloneWorkflow(testPlan1.getWorkflow()));
	}*/
	
	
	
	private ActionWorkflow cloneWorkflow(ActionWorkflow workflow1) {
		
		ActionWorkflow workflow2 = new ActionWorkflow();
		
		List<Action> actionList1 = workflow1.getActions();
		
		Vector<Action> actionList2 = new Vector<Action>();
		for (int i=0; i<actionList1.size(); i++) 
			actionList2.add(this.cloneAction(actionList1.get(i)));
		
		workflow2.setActions(actionList2);
		workflow2.setActionMark(workflow1.getActionMark());
		
		return workflow2;
	}
	
	
	private Action cloneAction(Action action1) {
		
		Action action2 = new Action(
				action1.getActionNumber(), 
				action1.getActionName(), 
				action1.getState(),
				action1.getTestLanguage(), 
				action1.getTestType(), 
				action1.getTestLocation(),
				action1.getTestValue(), 
				action1.isJumpTurnedON(), 
				action1.getDescription());

		return action2;
	}	
	
	private TestPlanXML cloneTestPlanXML(TestPlanXML tpXML1) {
		
		TestPlanXML tpXML2 = new TestPlanXML(tpXML1.getXml(),tpXML1.getAbsFileName());

		return tpXML2;
	}		
	
	
	
	@SuppressWarnings("unchecked")
	public List<TestPlan> readUserTestPlanList(User user) {

		List<TestPlan> testPlanListResult = new Vector<TestPlan>();
		/*
        String queryString = "SELECT t FROM TestPlan AS t";
        
        
        Query query = eM.createQuery(queryString);
        //query.setParameter(1, user);
        List<TestPlan> tempTestPlanList = query.getResultList();
        
        for (int i=0; i<tempTestPlanList.size(); i++)         	
        	if (tempTestPlanList.get(i).getUser().getId().intValue()==user.getId().intValue())
        		testPlanListResult.add(tempTestPlanList.get(i));*/

		//System.out.println("-AAAAAAAAAA: readUserTestPlanList");
        List<TestPlan> testPlanList = (List<TestPlan>) eM.createQuery("SELECT t FROM TestPlan t").getResultList(); 
        //System.out.println("-AAAAAAAAAA: readUserTestPlanList: " + testPlanList.size());
        Iterator<TestPlan> i = testPlanList.iterator();
        TestPlan tpTemp;
        while (i.hasNext()) {
        	//System.out.println("-AAAAAAAAAA: pre");
        	tpTemp = (TestPlan) i.next();
        	
            if (tpTemp.getUser().getId().intValue()==user.getId()){
            	testPlanListResult.add(tpTemp);
            	//System.out.println("-AAAAAAAAAA: bingo!");
            }
            //System.out.println("-AAAAAAAAAA: post");
        }
        //System.out.println("-AAAAAAAAAA: readUserTestPlanList: " + testPlanListResult.size());
        
        return testPlanListResult;
	}


	
		
		private User getSuperUser(String email, String password) {
			
			User superUser;
			
			String superUserEmail = PropertiesUtil.getUser1Email();
			String superUserPassword = PropertiesUtil.getUser1Password();
			
			if (email.equals(superUserEmail) && password.equals(superUserPassword))
				superUser = userManager.readUserbyEmailAndPassword(superUserEmail, superUserPassword);
			else
				superUser = null;
			
			return superUser;
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
			
			testPlan.getUser().getTestPlans().remove(testPlan);
			
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
	



/*	@SuppressWarnings("unchecked")
	private List<TestPlan> readTestPlanById(String testPlanId) {
		
        String queryString = "SELECT t FROM TestPlan AS t WHERE t.testPlanId = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, testPlanId);
        return query.getResultList();
	}*/




	public TestPlan getTestPlanFromXML(String testPlanAbsFileName) {
		
		TestPlan testPlan = null;	
		//System.out.println("azaza3: " + testPlanAbsFileName);
		try {

			TestPlanDOM testPlanDOM = new TestPlanDOM(testPlanAbsFileName);			
			//System.out.println("2");	 
			if ( testPlanDOM.root == null ) {
				System.out.println("XReport: " + testPlanDOM.getReport().getErrorMessage());	
			}
			else {
				//System.out.println("3");
				ActionWorkflow workflow = new ActionWorkflow(this.getActionsFromXML(testPlanDOM));
				
				TestPlanXML tpXML = new TestPlanXML(testPlanDOM.getXMLString(), testPlanAbsFileName);
				
				//System.out.println("4:" + testPlanDOM.getXMLString());
				//System.out.println("4:" + testPlanDOM.getRootDatetimeAttribute());
				testPlan = new TestPlan(testPlanDOM.getXMLString(), testPlanDOM.getRootDatetimeAttribute(), testPlanDOM.getRootStateAttribute(), testPlanAbsFileName, testPlanDOM.getRootDescriptionAttribute(), workflow, tpXML);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
		return testPlan;
	}


	
	
	public Vector<Action> getActionsFromXML(Long testPlanId) throws SAXException, ParserConfigurationException, IOException {
		
		TestPlan testPlan = readTestPlan(testPlanId);
				//findTestPlanByTestPlanId(testPlanId);
	
		TestPlanDOM testPlanDOM = new TestPlanDOM();
		testPlanDOM.setContent(testPlan.getTestplanxml().getXml());
		
		return this.getActionsFromXML(testPlanDOM);
	}
	

	private Vector<Action> getActionsFromXML(TestPlanDOM testPlanDOM) throws SAXException, ParserConfigurationException, IOException {
		
		Vector<Action> actionsList = new Vector<Action>();
		
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

			Action action = new Action(number, name, Action.getTodoState(), lg, type, location, value, jump, description);
			//Long actionId = this.insertAction(action);
			
			//System.out.println(action.getActionSummaryString());
			
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


/*	public Long createAction(Action action) {

		Action existingAction = this.findActionByName(action.getActionName());
		
		if (existingAction == null) {
			eM.persist(action);		
			return action.getId();
		}
		else 
			return new Long(-1);
	}*/

/*	private Action findActionByName(String name) {
		
	    String queryString = "SELECT a FROM Action AS a WHERE a.actionName = ?1";
	    
	    Query query = eM.createQuery(queryString);
	    query.setParameter(1, name);
	    @SuppressWarnings("unchecked")
		List<Action> resultList = query.getResultList();
	    if ((resultList != null) && (resultList.size() > 0))
	    	return (Action) resultList.get(0);
	    else
	    	return null;
	}*/


/*	public Action readAction(Long id_action) {
		
		return eM.find(Action.class, id_action);
	}*/


/*	public void addActionToWorkflow(Long actionId, Long workflowId) {

		ActionWorkflow wf = this.readWorkflow(workflowId);
		Action a = this.readAction(actionId);

		a.addToWorkflow(wf);
		eM.persist(wf);

		return;
	}*/


	public void addWorkflowToTestPlan(Long workflowId, Long testPlanId) {
		
		ActionWorkflow workflow = this.readWorkflow(workflowId);
		TestPlan testPlan = this.readTestPlan(testPlanId);

		//workflow.addToTestPlan(testPlan);
		testPlan.setWorkflow(workflow);
		
		eM.persist(testPlan);
		
		return;
	}


	private Vector<String> getSystemTestPlanFileList() {

		Vector<String> result = null;
		
		// L'utente deve scegliere il TestPlan da importare: 
		// Get TeBES testPlan1 absolute PathName from .properties file
		String systemTestPlanAbsDirName = null;
		
		try {		
			systemTestPlanAbsDirName = PropertiesUtil.getSuperUserTestPlanDir();			
			//System.out.println("systemTestPlanAbsDirName:" + systemTestPlanAbsDirName);
			
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


	public boolean importSystemTestPlanFiles(User user) {
		
		boolean result = true;
		
		// Verifico SuperUser
		User superUser = this.getSuperUser(user.geteMail(), user.getPassword());
		if ( superUser != null ) {
		
			
			// Cancellare i TestPlan esistenti o effettuare gestione dei duplicati
			// deleteAllTestPlans(user)
			// TODO per verificare questa bisognerebbe richiamarlo una seconda volta
			// anche perchè se è lazy qui la getTestPlan non da nulla.
			List<TestPlan> testPlanList = user.getTestPlans();
			for (int tp=0 ;tp<testPlanList.size();tp++) 
				this.deleteTestPlan(testPlanList.get(tp).getId());

			// Recuperare lista dei file
			Vector<String> systemTestPlanFileList = this.getSystemTestPlanFileList();

			// System Dir
			String superUserTestPlanDir = PropertiesUtil.getSuperUserTestPlanDir();
			
			String testPlanAbsPathName;
			TestPlan testPlan;
			Long testPlanId;
			
			// Create 
			for (int i=0; i<systemTestPlanFileList.size();i++) {
						
				// GET TestPlan structure from XML
				testPlanAbsPathName = superUserTestPlanDir.concat(systemTestPlanFileList.elementAt(i));

				System.out.println("zxcv:" + i + ":" + testPlanAbsPathName);
				
				// Create
				testPlan = this.getTestPlanFromXML(testPlanAbsPathName);			
				System.out.println("zxcv:" + i + ":" + testPlan.getDatetime());
				testPlanId = this.createTestPlan(testPlan, user.getId());
				System.out.println("zxcv:" + i + ":" + testPlanId);
				
				// Se solo un'importazione non va a buon fine il risultato è false
				if (testPlanId.intValue() < 0 )
					result = false;
			}	
		}
		else
			result = false;

		
		return result;
	}


	public List<TestPlan> getSystemTestPlanList() {
		
		String superUserEmail = PropertiesUtil.getUser1Email();
		String superUserPassword = PropertiesUtil.getUser1Password();

		User superUser = userManager.readUserbyEmailAndPassword(superUserEmail, superUserPassword);
		
		List<TestPlan> superUserTestPlanList = superUser.getTestPlans();
		
		return superUserTestPlanList;
	}








		
	

}




