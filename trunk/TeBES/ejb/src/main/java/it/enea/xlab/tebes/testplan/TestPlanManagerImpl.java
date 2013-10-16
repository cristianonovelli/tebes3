package it.enea.xlab.tebes.testplan;

import it.enea.xlab.tebes.action.ActionManagerRemote;
import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.dao.TeBESDAO;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.Input;
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
				testPlan2 = new TestPlan(testPlan.getName(), testPlan.getDescription(), datetime, datetime, Constants.STATE_DRAFT, testPlan.getLocation(), null, null);
				eM.persist(testPlan2);	
				
				// TODO CREATE Workflow
				ActionWorkflow wf = testPlan.getWorkflow();
				ActionWorkflow wf2 = new ActionWorkflow();	
				wf2.setComment(wf.getComment());
				wf2.setActionMark(1);
				Long wf2Id = actionManager.createWorkflow(wf2);
				wf2 = actionManager.readWorkflow(wf2Id);
				
				Vector<Action> actionList = (Vector<Action>) wf.getActions(); 
				Vector<Input> inputList;
				Long actionId;
				Action actionTemp;
				for (int i=0; i<actionList.size(); i++) {				
					
					actionTemp = actionList.get(i);
					
					actionId = actionManager.createAction(actionTemp, wf2Id);

					// prendo lista input					
					inputList = (Vector<Input>) actionTemp.getInputs();
					
					// creo input
					for (int j=0; j<inputList.size(); j++) {	
						
						actionManager.createInput(inputList.get(j), actionId);
					}
				}			
				
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

				logger.debug("CREATED TestPlan with ID " + testPlan2.getId() + ": " + testPlan2.getDescription());
				
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
		
		testPlanClone = new TestPlan(testPlan.getName(), testPlan.getDescription(), datetime, datetime, Constants.STATE_DRAFT, testPlan.getLocation(), wfClone, tpXMLClone);
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
	
	
	
/*	private ActionWorkflow cloneWorkflow(ActionWorkflow workflow1) {
		
		ActionWorkflow workflow2 = new ActionWorkflow();
		
		List<Action> actionList1 = workflow1.getActions();
		
		Vector<Action> actionList2 = new Vector<Action>();
		for (int i=0; i<actionList1.size(); i++) 
			actionList2.add(this.cloneAction(actionList1.get(i)));
		
		workflow2.setActions(actionList2);
		workflow2.setActionMark(workflow1.getActionMark());
		
		return workflow2;
	}*/
	
	
	private Action cloneAction(Action action1) {
		
		Action action2 = new Action(
				action1.isPrerequisite(),
				action1.getActionNumber(), 
				action1.getActionName(), 
				action1.getActionId(),
				action1.getState(),
				action1.getTestLanguage(), 
				action1.getTestType(), 
				action1.getTestLocation(),
				action1.getTestValue(), 
				action1.isSkipTurnedON(), 
				action1.getDescription()				
				);

		List<Input> inputList1 = action1.getInputs();
		Vector<Input> inputList2 = new Vector<Input>();
		for (int i=0; i<inputList1.size(); i++) {				
			
			inputList2.add(this.cloneInput(inputList1.get(i)));		
		}
		
		action2.setInputs(inputList2);
		
		return action2;
	}	
	
	
	
	private Input cloneInput(Input input1) {
		
		return new Input(
				input1.getName(), 
				input1.getDescription(), 
				input1.getType(), 
				input1.getLg(),				
				input1.getInteraction(), 
				input1.getFileIdRef(), 
				input1.getGuiReaction(),
				input1.getGuiMessage(),
				input1.isInteractionOK());	
	}
	
	
	private TestPlanXML cloneTestPlanXML(TestPlanXML tpXML1) {
		
		TestPlanXML tpXML2 = new TestPlanXML(tpXML1.getXml(),tpXML1.getAbsFileName());

		return tpXML2;
	}		
	
	
	
	@SuppressWarnings("unchecked")
	public List<TestPlan> readUserTestPlanList(User user) {

		List<TestPlan> testPlanListResult = new Vector<TestPlan>();

        List<TestPlan> testPlanList = (List<TestPlan>) eM.createQuery("SELECT t FROM TestPlan t").getResultList(); 

        Iterator<TestPlan> i = testPlanList.iterator();
        TestPlan tpTemp;
        while (i.hasNext()) {

        	tpTemp = (TestPlan) i.next();
        	
            if (tpTemp.getUser().getId().intValue()==user.getId()){
            	testPlanListResult.add(tpTemp);
            }
        }
        
        return testPlanListResult;
	}


	
		
		private User getSuperUser(String email, String password) {
			
			User superUser;
			
			String superUserEmail = PropertiesUtil.getSuperUserEmailProperty();
			String superUserPassword = PropertiesUtil.getSuperUserPasswordProperty();
			
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
			 
			 if (testPlan != null) {
				 result = true;
				 logger.debug("UPDATED TestPlan with ID " + testPlan.getId() + ": " + testPlan.getDescription());
			 }
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
			logger.debug("DELETED TestPlan with ID " + testPlan.getId() + ": " + testPlan.getDescription());
			
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
			
			if (testPlanList1.get(i).getCreationDatetime().equals(datetime))
				testPlanList2.add(testPlanList1.get(i));
			
		}
		
        return testPlanList2;
	}
	


	public TestPlan getTestPlanFromXML(String testPlanAbsFileName) {
		
		TestPlan testPlan = null;	

		try {

			TestPlanDOM testPlanDOM = new TestPlanDOM(testPlanAbsFileName);			

			if ( testPlanDOM.root == null ) {
				System.out.println("XReport: " + testPlanDOM.getReport().getErrorMessage());	
			}
			else {
				ActionWorkflow workflow = new ActionWorkflow(this.getActionsFromXML(testPlanDOM));
				
				TestPlanXML tpXML = new TestPlanXML(testPlanDOM.getXMLString(), testPlanAbsFileName);

				testPlan = new TestPlan(testPlanDOM.getRootNameAttribute(), testPlanDOM.getRootDescriptionAttribute(), testPlanDOM.getRootCreationDatetimeAttribute(), testPlanDOM.getRootLastUpdateDatetimeAttribute(), 
						testPlanDOM.getRootStateAttribute(), testPlanAbsFileName, workflow, tpXML);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
		return testPlan;
	}



	private Vector<Action> getActionsFromXML(TestPlanDOM testPlanDOM) throws SAXException, ParserConfigurationException, IOException {
		
		Vector<Action> actionsList = new Vector<Action>();
		
		// Ottengo lista actions
		NodeList actionNodes = testPlanDOM.getTestActionNodeList();

		// Ciclo su ogni azione
		Element actionElement = null;
		for (int i = 0; i < actionNodes.getLength(); i++) {
			
			actionElement = (Element) actionNodes.item(i);
			
			
				
			int number = new Integer(testPlanDOM.getNumberAttribute(actionElement)).intValue();			
			String name = testPlanDOM.getActionName(actionElement);
			String actionId = testPlanDOM.getIdAttribute(actionElement);
			
			String description = testPlanDOM.getActionDescription(actionElement);				
			
			Node testNode = testPlanDOM.getTestNode(actionElement);
			String lg = testPlanDOM.getLgAttribute(testNode);

			String type = testPlanDOM.getTypeAttribute(testNode);		
			String value = testNode.getFirstChild().getNodeValue();				
			String skipString = testPlanDOM.getSkipAttribute(testNode);		
			boolean skip;
			if (skipString.equals("true"))
				skip = true;
			else 
				skip = false;
			
			String location = testPlanDOM.getLocationAttribute(testNode);

			location = TeBESDAO.url2localLocation(location);
			
			// Input
			NodeList inputNodeList = testPlanDOM.getInputNodeList(actionElement);
			
			Node inputNode;
			String inputName, inputDescription, inputType, inputLanguage; 
			String inputInteraction, inputFileIdRef, inputGuiReaction, inputGuiMessage;
			Vector<Input> inputList = new Vector<Input>();
			for (int j = 0; j < inputNodeList.getLength(); j++) {
			
				// Get Input Node
				inputNode = inputNodeList.item(j);
				
				// Get Input values
				inputName = testPlanDOM.getNameAttribute(inputNode);
				inputDescription = testPlanDOM.getDescriptionAttribute(inputNode);	
				inputType = testPlanDOM.getTypeAttribute(inputNode);		
				inputLanguage = testPlanDOM.getLgAttribute(inputNode);				
				inputInteraction = testPlanDOM.getSutInteractionAttribute(inputNode);
				inputFileIdRef = testPlanDOM.getFileIdRefAttribute(inputNode);		
				inputGuiReaction = testPlanDOM.getGuiReactionAttribute(inputNode);
				inputGuiMessage = testPlanDOM.getGuiMessageAttribute(inputNode);
				
				// Create Input object
				Input input = new Input(inputName, inputDescription, inputType, inputLanguage,
						inputInteraction, inputFileIdRef, inputGuiReaction, inputGuiMessage, false);
				
				// Add Input object to List
				inputList.add(input);
			
			}

			Action action = new Action(false, number, name, actionId, Action.getNewState(), lg, type, location, value, skip, description);
			
			action.setInputs(inputList);
			
			actionsList.add(action);
		}	

		return actionsList;
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
			systemTestPlanAbsDirName = PropertiesUtil.getSuperUserTestPlansDirPath();			
			
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
			String superUserTestPlanDir = PropertiesUtil.getSuperUserTestPlansDirPath();
			
			String testPlanAbsPathName;
			TestPlan testPlan;
			Long testPlanId;
			
			// Create 
			for (int i=0; i<systemTestPlanFileList.size();i++) {
						
				// GET TestPlan structure from XML
				testPlanAbsPathName = superUserTestPlanDir.concat(systemTestPlanFileList.elementAt(i));
				
				// Create
				testPlan = this.getTestPlanFromXML(testPlanAbsPathName);			

				testPlanId = this.createTestPlan(testPlan, user.getId());

				
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
		
		String superUserEmail = PropertiesUtil.getSuperUserEmailProperty();
		String superUserPassword = PropertiesUtil.getSuperUserPasswordProperty();

		User superUser = userManager.readUserbyEmailAndPassword(superUserEmail, superUserPassword);
		
		List<TestPlan> superUserTestPlanList = superUser.getTestPlans();
		
		return superUserTestPlanList;
	}


}


