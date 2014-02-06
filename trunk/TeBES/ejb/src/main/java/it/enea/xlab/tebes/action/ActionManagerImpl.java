package it.enea.xlab.tebes.action;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionDescription;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.Input;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.model.TAF;
import it.enea.xlab.tebes.report.ReportDOM;
import it.enea.xlab.tebes.report.ReportManagerRemote;
import it.enea.xlab.tebes.test.TestManagerImpl;

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

import org.apache.log4j.Logger;
import org.w3c.dom.Node;


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
	
	
	public Long createActionDescription(ActionDescription actionDescription,
			Long actionId) {
		
		try {
			eM.persist(actionDescription);
							
			this.addDescriptionToAction(actionDescription.getId(), actionId);
							
			return actionDescription.getId();
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

	private ActionDescription readActionDescription(Long id) {
		
		return eM.find(ActionDescription.class, id);
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
	
	private void addDescriptionToAction(Long id, Long actionId) {

		ActionDescription ad = this.readActionDescription(id);
		Action a = this.readAction(actionId);
		
		ad.setTestAction(a);
		
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
	
		// Il metodo runAction lancia l'esecuzione di una action presa dal TestPlan,
		// ciò implica l'esecuzione di una o più Test Assertion e dei loro Prerequisiti
		//
		// SUMMARY
		// 1. Recupero Report
		// 2. Gestione Action XML (template o clonata)
		// 3. Aggiornamento Action XML
		// 4. BUILD TAF list da Action (no prerequisites)
		// 5. EXE TAF
		// 6. Aggiorno il Report
		// 7. Setto Action come "Done"
		
		
		
		// 1.1 Recupero entity Report
		Report report = session.getReport();
		
		// 1.2 Set risultato parziale a false
		// (il risultato parziale è il risultato momentaneo dell'esecuzione di tutti i test della nostra action)
		report.setPartialResultSuccessfully(true);

		

		try {
			// 1.3 Recupero Report DOM
			ReportDOM reportDOM = new ReportDOM();
			reportDOM.setContent(report.getXml());
			

			// 2.1 Se GlobalResult != "undefined" vuol dire che non è la prima action che eseguo,
			// allora Clono quella presente e la modifico
			//NodeList testActionListXML = reportDOM.getTestActionNodeList();
			Node actionTRNode = reportDOM.getTestAction(action.getActionId());
					//testActionListXML.item(0);
			//String globalResult = reportDOM.getGlobalResult();
			
			// se TestPlanExecution/GlobalResult != "undefined"
			/*if ( !globalResult.equals(Report.getUndefinedResult()) ) {
				
				// 2.2 Clono actionNode
				Node actionNodeClone = actionNode.cloneNode(true);
				actionNodeClone = reportDOM.doc.importNode(actionNodeClone, true); 
				reportDOM.insertActionNode(actionNodeClone);
				reportDOM.setNumberAttribute(actionNodeClone, new Integer(action.getActionNumber()).toString());
				
				// Ora actionNode punta all'actionNodeClone
				actionNode = actionNodeClone;
				
			}
			// 2.2 Se TestPlanExecution/GlobalResult == "undefined" vuol dire che è la prima action che eseguo			
			else {
		
				Element actionElement = (Element) actionNode;
				
				// Se c'è più di una action OPPURE se l'attributo number != 0 allora c'è qualcosa che non va, 
				// perché non è la situazione che mi aspetto utilizzando il template la prima volta
				
				 if ( (testActionListXML.getLength()!=1) || (!actionElement.getAttribute("number").equals("0")) ) {
					 System.out.println("ERROR: Inconsistent Report Template, GlobalResult is undefined but there is an executed action.");
					 actionNode = null;
				 }
				 // Se è tutto ok allora semplicemente aggiorno l'attributo @number
				 else
					 reportDOM.setNumberAttribute(actionNode, new Integer(action.getActionNumber()).toString());
			}*/
			
			
			if (actionTRNode != null) {
							
				// 3. Action pronta, aggiorno XML
				// l'action XML (template o clonata che sia) è pronta per essere aggiornata 
				// Aggiorno Action XML (info NON relative all'esecuzione del test)
				
				// Set Name
				reportDOM.setActionName(actionTRNode, action.getActionName());
				
				// Set Description
				ActionDescription aDescription;
				List<ActionDescription> actionDescriptionList = action.getActionDescriptions();
				for(int ad=0; ad<actionDescriptionList.size(); ad++) {
					aDescription = actionDescriptionList.get(ad);
					reportDOM.setTestActionDescription(actionTRNode, aDescription.getLanguage(), aDescription.getValue());
				}
				
				// Set Test Value and attributes
				reportDOM.setActionTest(actionTRNode, action.getTestValue());				
				Node testNode = reportDOM.getTestElement(actionTRNode);
				reportDOM.setTestSkipPrerequisitesAttribute(testNode, action.isSkipTurnedON());
				reportDOM.setTestLgAttribute(testNode, action.getTestLanguage());
				reportDOM.setTestLocationAttribute(testNode, action.getTestLocation());
				reportDOM.setTestTypeAttribute(testNode, action.getTestType());
				
				// Fine della modifica XML che NON riguarda l'esito dell'action
				report.setXml(reportDOM.getXMLString());	
				
				
				
				
				// 4. BUILD TAF dalla Action
				
				report.addToFullDescription("\n");
				report.addToFullDescription("\n");
				report.addToFullDescription("\n-- Start Execution of Action: " + action.getActionName() + "--");
	
				
				// 4.1 Istanzio il TestManager
				TestManagerImpl testManager = new TestManagerImpl();
				
				report.addToFullDescription("\nBuilding... TAF for action: " + action.getActionName());
				
				
				// 4.2 BUILD Lista di TAF Building from Action
				// (in questa lista non ci sono prerequisiti!)
				Vector<TAF> tafList = testManager.buildTAF(action);
				report.addToFullDescription("\nBuilt TAF List of " + tafList.size() + " TAF.");
				
				
				
				// 5. TAF List Execution
				if (tafList.size() > 0) {
					
					TAF taf;
					int i = 0;
					while (i<tafList.size()) {
					
						taf = tafList.get(i);
						
						report.addToFullDescription("\nBuilt TAF " + taf.getName() + " OK.");
						report.addToFullDescription("\nStart execution TAF " + taf.getName());
						

						
						// 5.1 EXECUTE TAF		
						report.addToFullDescription("pre-executeTAF check: " + taf.getReportFragments().get("fail").getDescription());
						report = testManager.executeTAF(taf, session, reportDOM, reportDOM.getAttribute("id", actionTRNode));

						

						// Aggiorno local reportDOM (potrebbe essere cambiato nell'esecuzione della taf)
						//reportDOM.setContent(report.getXml());						

						
						
						
						// 5.2 Gestisco risultato in ritorno e lo metto dentro SingleResult
						Node testResultsNode = reportDOM.getTestResultsElement(actionTRNode);	
						
						// 5.2.1 Setto attributi testResultsNode
						if (report.getTempResult().getGlobalResult().equals("pass")) {
							
							// set "pass" report
							reportDOM.setNodeAttribute(testResultsNode, "result", report.getTempResult().getGlobalResult());
							reportDOM.setNodeAttribute(testResultsNode, "message", taf.getReportFragments().get("pass").getDescription());
						}
						else if (report.getTempResult().getGlobalResult().equals("notQualified")) {
							
							// set "notQualified" report
							reportDOM.setNodeAttribute(testResultsNode, "result", report.getTempResult().getGlobalResult());
							reportDOM.setNodeAttribute(testResultsNode, "message", taf.getReportFragments().get("notQualified").getDescription());
						}
						else {
							
							// set "fail" report
							reportDOM.setNodeAttribute(testResultsNode, "result", report.getTempResult().getGlobalResult());
							reportDOM.setNodeAttribute(testResultsNode, "message", taf.getReportFragments().get("fail").getDescription());							
						}
						
						
						// "1" sta ad indicare il primo nodo figlio, ovvero dopo il nodo testo del nodo corrente
						Node firstSingleResultNode = testResultsNode.getChildNodes().item(1);
						
						// Set Single Result
						if (report.getTempResult() != null) {
							reportDOM.setSingleResult(firstSingleResultNode, action.getId(), taf.getName(), report.getTempResult().getGlobalResult(), report.getTempResult().getLine(), report.getTempResult().getMessage());
							System.out.println("TAF Execution: " + report.getTempResult().getGlobalResult());
							report.addToFullDescription("\nTAF Execution: " + report.getTempResult().getGlobalResult());
							report.addToFullDescription("\nisPrerequisite: " + taf.isPrerequisite());
							
						}
						else {
							
							reportDOM.setSingleResult(firstSingleResultNode, action.getId(), taf.getName(), "syserror", 0, "Validation Failure: check Validation Project.");
							System.out.println("runAction - tempResult NULL: error");
							report.addToFullDescription("\nTAF Execution: tempResult NULL");
						}

						// 5.3 Gestione Action che esegue più Test
						// 	TODO com'è ora, se ci fosse una lista di TAF ne farebbe uno solo
						// Devo:
						// 1. controllare vedere se ce n'è uno solo e se ha id=0
						// in questo caso lo valorizzo
						// 2. nel caso invece ve ne sia più di uno o abbia id!=0
						// devo clonarlo, inserirlo e poi valorizzarlo
						

						// 6.1 Aggiorno Report
						report.setXml(reportDOM.getXMLString());	
						// Poi azzero tempResult per la prossima esecuzione					
						report.setTempResult(null);

						report.addToFullDescription("\nResult: " + report.isPartialResultSuccessfully());
						
						i++;
					}
				}
				else
					report.addToFullDescription("\nBuilt TAF Failure.");
	
				
				// 6.2 Aggiorno Full Description
				report.addToFullDescription("\n-- End Execution of Action: " + action.getActionName() + "--");
				
				// 6.3 Aggiorno Global Result
				//if ( report.isPartialResultSuccessfully() ) {
					reportDOM.setGlobalResult(Report.getSuccessfulResult());
					report.setXml(reportDOM.getXMLString());	
				//}
				// TODO non manca il caso di false?
				

				// 6.4 Update Report
				//report.setState(Report.getFinalState());
				boolean updating = reportManager.updateReport(report);
				report.setPartialResultSuccessfully(report.isPartialResultSuccessfully() && updating);
				
				// 6.5 SET Action as DONE
				action = this.readAction(action.getId());
				action.setStateToDone();
				eM.persist(action);
				
				
			}
			// Nel caso actionNode == null
			else {
				report.addToFullDescription("SYSTEM ERROR: il metodo ActionManagerImpl.runAction incontra un'inconsistente (actionNode == null) dopo l'inizializzazione della Action XML.");
				logger.error("SYSTEM ERROR: il metodo ActionManagerImpl.runAction incontra un'inconsistente (actionNode == null) dopo l'inizializzazione della Action XML.");
				report.setPartialResultSuccessfully(false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return report;
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


