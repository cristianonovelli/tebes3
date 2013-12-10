package it.enea.xlab.tebes.action;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.Input;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.model.TAF;
import it.enea.xlab.tebes.report.ReportDOM;
import it.enea.xlab.tebes.report.ReportManagerRemote;
import it.enea.xlab.tebes.test.TestManagerImpl;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import validator.ErrorMessage;


@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ActionManagerImpl implements ActionManagerRemote {

	@PersistenceContext(unitName=Constants.PERSISTENCE_CONTEXT) 
	private EntityManager eM; 

	// Logger
	private static Logger logger = Logger.getLogger(ActionManagerImpl.class);
	
	
	
	@EJB
	private ReportManagerRemote reportManager; 
	
	//////////////////////
	/// ACTION METHODS ///
	//////////////////////

	/**
	 * CREATE Action
	 * @return 	id of Action if created
	 * 			-1 if a persist exception occours
	 */
	public Long createAction(Action action, Long workflowId) {

		try {
				eM.persist(action);
				
				
				this.addActionToWorkflow(action.getId(), workflowId);
				
				
				return action.getId();
		}
		catch(Exception e) {
			
			e.printStackTrace();
			return new Long(-1);
		}
	}
	
	
	/**
	 * READ Action
	 * @return 	Action if reading is OK
	 */
	public Action readAction(Long id) {
		
		Action result;
		try {
			
			result = eM.find(Action.class, id);
			
		} catch (EntityNotFoundException e) {
			result = null;
		}
		
		return result;
	}

	
	/**
	 * DELETE Action
	 * @return 	true if deleting is OK
	 */
	public Boolean deleteAction(Long id) {
		
		Action a = this.readAction(id);
		
		
		
		if (a == null)
			return false;
		
		try {
			
			a.getWorkflow().getActions().remove(a);
			
			//a.removeFromWorkflow();
			
			eM.remove(a);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		} catch (Exception e2) {
			return null;
		}
	}	
	

	/**
	 * ADD Action to ActionWorkflow
	 */
	public void addActionToWorkflow(Long actionId, Long workflowId) {

		Action a = this.readAction(actionId);
		ActionWorkflow wf = this.readWorkflow(workflowId);
		
		a.addToWorkflow(wf);
		/*if ( !wf.getActions().contains(a) )
			wf.getActions().add(a);*/
		
		eM.persist(wf);
		//eM.merge(wf);
		
		return;
	}
	

	/////////////////////
	/// INPUT METHODS ///
	/////////////////////

	/**
	 * CREATE Input
	 * @return 	id of Input if created
	 * 			-1 if a persist exception occours
	 */
	public Long createInput(Input input, Long actionId) {

		try {
				eM.persist(input);
								
				this.addInputToAction(input.getId(), actionId);
								
				return input.getId();
		}
		catch(Exception e) {
			
			e.printStackTrace();
			return new Long(-1);
		}
	}
	
	/**
	 * READ Input
	 * @return 	Input if reading is OK
	 */	
	public Input readInput(Long id) {

		return eM.find(Input.class, id);
	}

	
	/**
	 * UPDATE Input
	 * @return 	Input if reading is OK
	 */	
	public boolean updateInput(Input input) {

		Boolean result = false;

		 if ( (input != null) && (input.getId() != null) ) {
			 input = eM.merge(input);
			 //eM.persist(user);
			 
			 if (input != null)
				 result = true;
		 }

		 return result;
	}

	/**
	 * UPDATE Input
	 * @return 	Input if reading is OK
	 */	
	private boolean updateAction(Action action) {

		Boolean result = false;

		 if ( (action != null) && (action.getId() != null) ) {
			 action = eM.merge(action);
			 
			 if (action != null)
				 result = true;
		 }

		 return result;
	}
	
	/**
	 * ADD Input to Action
	 */
	public void addInputToAction(Long inputId, Long actionId) {

		Input i = this.readInput(inputId);
		Action a = this.readAction(actionId);
		
		i.addToAction(a);
		
		eM.persist(a);
		
		return;
	}
	
	
	////////////////////////
	/// WORKFLOW METHODS ///
	////////////////////////

	/**
	 * CREATE Workflow
	 * @return 	id of Workflow if created
	 * 			-1 if a persist exception occours
	 */
	public Long createWorkflow(ActionWorkflow workflow) {

		try {
				eM.persist(workflow);
				return workflow.getId();
		}
		catch(Exception e) {
			
			e.printStackTrace();
			return new Long(-1);
		}
	}
	
	
	/**
	 * READ Workflow by TestPlan
	 */
	public ActionWorkflow readWorkflow(Long id) {
		
		return eM.find(ActionWorkflow.class, id);
	}
	


	/**
	 * DELETE Workflow
	 * @return 	true if deleting is OK
	 */
	public Boolean deleteWorkflow(Long id) {
		
		ActionWorkflow wf = this.readWorkflow(id);
		
		if (wf == null)
			return false;
		
		try {
			eM.remove(wf);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		} catch (Exception e2) {
			return null;
		}
	}	
	

	
	///////////////////
	/// RUN METHODS ///
	///////////////////
	
	
/*	*//**
	 * RUN Workflow
	 * Actions Workflow Engine
	 * TODO supporto di un workflow più articolato, per ora è lineare e si risolve con un semplice for
	 * @return 	true if all actions return true
	 * 			false if one action return false
	 *//*
	public Report runWorkflow(ActionWorkflow workflow, Report report) {
		
		//boolean result = true;
		report.setFinalResultSuccessfully(true);
		
		// io credo dovrei fare una ricerca del tipo readActionByWorkflowId
		// eseguire questa action e una volta eseguita, eliminarla dal DB o settarla come "done"
		List<Action> actionList = workflow.getActions();
		int actionListSize = workflow.getActions().size();
		Action a;
		
		int nextActionMark = workflow.getNextActionMark(); 
		while (nextActionMark <= actionListSize) {
		// for (int k=0;k<actionList.size();k++) {
			
			a =	(Action) actionList.get(nextActionMark-1);
			
			if (a != null) {
				//result = result && runAction(a, report);
				report = runAction(a, report);
				report.setFinalResultSuccessfully(report.isFinalResultSuccessfully() && report.isPartialResultSuccessfully());
				
				
				// interaction?
			}
			else 
				report.setFinalResultSuccessfully(false);
			
			nextActionMark++;
		}
		
		
		// TODO incremento marker del workflow e persisto
		
		
		//for (int i = 0 ; i < workflow.size(); i++) {
		//	
		//	result = result && execute((Action) workflow.get(i));
		//}
		
		return report;
	}*/
	
	
	/**
	 * RUN Action
	 * Generic Test Action Execution
	 * checks the lg attribute and call the executor of the test language specified (p.es TAML)
	 * 
	 * @return 	true if type is equal to one of the three permitted values: "TestSuite", "TestCase", "TestAssertion"
	 * 			false otherwise
	 */
	public Report runAction(Action action, Session session) {
	
		
		Report report = session.getReport();
		
		//boolean result = false;
		report.setPartialResultSuccessfully(false);

		

		try {
			// Recupero Report DOM
			ReportDOM reportDOM = new ReportDOM();
			reportDOM.setContent(report.getXml());
			
			// prendo <GlobalResult>empty</GlobalResult>
			// se è empty
			// modifico la prima action
			// altrimenti ne faccio una copia e la modifico

			
			
			// Se GlobalResult non è "empty"
			// clono la action
			// poi in ogni caso modifico la action
			NodeList testActionListXML = reportDOM.getTestActionNodeList();
			
			Node actionNode = testActionListXML.item(0);
			String globalResult = reportDOM.getGlobalResult();
			
			// se TestPlanExecution/GlobalResult != "undefined"
			if ( !globalResult.equals(Report.getUndefinedResult()) ) {
				
				// Clono actionNode
				Node actionNodeClone = actionNode.cloneNode(true);
				actionNodeClone = reportDOM.doc.importNode(actionNodeClone, true); 
				reportDOM.insertActionNode(actionNodeClone);
				reportDOM.setNumberAttribute(actionNodeClone, new Integer(action.getActionNumber()).toString());
				
				// Ora actionNode punta all'actionNodeClone
				actionNode = actionNodeClone;
				
			}
			// se TestPlanExecution/GlobalResult == "undefined"
			else {


				
				Element actionElement = (Element) actionNode;
				
				 if ( (testActionListXML.getLength()!=1) || (!actionElement.getAttribute("number").equals("0")) ) {
					 System.out.println("ERROR: Inconsistent Report Template, GlobalResult is undefined but there is an executed action.");
					 actionNode = null;
				 }
				 else
					 reportDOM.setNumberAttribute(actionNode, new Integer(action.getActionNumber()).toString());
			}
			
			// Modifica dell'action individuata (template o clonata che sia)
			if (actionNode != null) {
				
				// Set Name
				reportDOM.setActionName(actionNode, action.getActionName());
				
				// Set Description
				reportDOM.setActionDescription(actionNode, action.getDescription());
				
				// Set Test Value and attributes
				reportDOM.setActionTest(actionNode, action.getTestValue());				
				Node testNode = reportDOM.getTestElement(actionNode);
				reportDOM.setTestSkipPrerequisitesAttribute(testNode, action.isSkipTurnedON());
				reportDOM.setTestLgAttribute(testNode, action.getTestLanguage());
				reportDOM.setTestLocationAttribute(testNode, action.getTestLocation());
				reportDOM.setTestTypeAttribute(testNode, action.getTestType());
				
				// Fine della modifica XML che NON riguarda l'esito dell'action
				report.setXml(reportDOM.getXMLString());	
				
				// ORA SI ESEGUE L'ACTION
				
				report.addToFullDescription("<br>");
				report.addToFullDescription("<br>");
				report.addToFullDescription("<br>-- Start Execution of Action: " + action.getActionName() + "--");
	
				
				// istanzio il TestManager
				TestManagerImpl testManager = new TestManagerImpl();
				
				report.addToFullDescription("<br>Building TAF for action: " + action.getActionName());
				
				// TAF Building
				Vector<TAF> tafList = testManager.buildTAF(action);
				
				// TODO qui potrei già inserire le action prerequisites
				// 1. se non ce ne sono, cancello quella presente;
				// 2. se ce ne sono, per ogni prerequisito creo l'elemento
				
				// TAF Execution
				if (tafList.size() > 0) {
					
					TAF taf;
					int i = 0;
					while (i<tafList.size()) {
					
						taf = tafList.get(i);
						
						report.addToFullDescription("<br>Built TAF " + taf.getName() + " successful.");
						report.addToFullDescription("<br>Start execution TAF " + taf.getName());
						
						
						
						// EXECUTE TAF
						report = testManager.executeTAF(taf, session);
						//report.setPartialResultSuccessfully(tafResult);
						
						
						// TODO
						// la action viene eseguita, essa potrà o meno richiamare altre taf
						// 1. nel caso abbia prerequisiti
						// 2. nel caso sia un test case che contiene più test assertion
						// 3. in entrambi i casi 1 e 2
						// Nel report vengono riportati i dettagli della test action.
						// A questi si aggiungono i risultati dei vari test man mano che vengono eseguiti
						// come tuple (result, line, message).
						
	
						
						
						
	
						Node testResultsNode = reportDOM.getTestResultsElement(actionNode);
	
						
						// 1 sta ad indicare il primo nodo figlio, ovvero dopo il nodo testo del nodo corrente
						Node firstSingleResultNode = testResultsNode.getChildNodes().item(1);
	
						// Set Single Result
						if (report.getTempResult() != null) {
							reportDOM.setSingleResult(firstSingleResultNode, action.getId(), taf.getName(), report.getTempResult().getGlobalResult(), report.getTempResult().getLine(), report.getTempResult().getMessage());
							System.out.println("runAction - tempResult: " + report.getTempResult().getGlobalResult());
						}
						else {
							reportDOM.setSingleResult(firstSingleResultNode, action.getId(), taf.getName(), "failure", 0, "Validation Failure: check Validation Project.");
							System.out.println("runAction - tempResult: failure");
						}
							// 	TODO com'è ora, se ci fosse una lista di TAF ne farebbe uno solo
						// Devo:
						// 1. controllare vedere se ce n'è uno solo e se ha id=0
						// in questo caso lo valorizzo
						// 2. nel caso invece ve ne sia più di uno o abbia id!=0
						// devo clonarlo, inserirlo e poi valorizzarlo
						
						
						// Fine della modifica XML che NON riguarda l'esito dell'action
						report.setXml(reportDOM.getXMLString());	
						
						// TODO ADD to XML Single Result
						// Poi azzera singleresult
						
						report.setTempResult(null);
						
						
						
						
						report.addToFullDescription("<br>Result: " + report.isPartialResultSuccessfully());
						
						
						i++;
					}
				}
				else
					report.addToFullDescription("<br>Built TAF Failure.");
	
				
				report.addToFullDescription("<br>-- End Execution of Action: " + action.getActionName() + "--");
				
				if ( report.isPartialResultSuccessfully() ) {
					reportDOM.setGlobalResult(Report.getSuccessfulResult());
					report.setXml(reportDOM.getXMLString());	
				}
				
				

				// Persist Report
				//report.setState(Report.getFinalState());
				boolean updating = reportManager.updateReport(report);
				
				
				// SET Action as DONE
				action = this.readAction(action.getId());
				action.setStateToDone();
				eM.persist(action);
				
				
				report.setPartialResultSuccessfully(report.isPartialResultSuccessfully() && updating);
					

				

				
				
			}
			else {
				System.out.println("TODO: SYSTEM ERROR nel Report");
				
				report.setPartialResultSuccessfully(false);
			}
			


			
			
			
			
			
			
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


			

		
		
		
		return report;
		//return result && updating;
	}


	
	

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


	public Boolean checkActionReady(Action action) {
		
		List<Input> inputs = action.getInputs();
		
		
		Boolean isReady = true;
		for (int i=0; i<inputs.size(); i++) {
		
			isReady = isReady && inputs.get(i).isInputSolved();
		}
		
		if (isReady) {
			action.setStateToReady();
			isReady = isReady && this.updateAction(action);
		}
		
		return isReady;
	}



}


