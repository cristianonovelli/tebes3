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

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ActionManagerImpl implements ActionManagerRemote {

	@PersistenceContext(unitName=Constants.PERSISTENCE_CONTEXT) 
	private EntityManager eM; 
	
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
	
/*	public Long readWorkflowByTestPlan(TestPlan tp) {
		
        String queryString = "SELECT w FROM ActionWorkflow AS w";
        
        Query query = eM.createQuery(queryString);

		try {
			@SuppressWarnings("unchecked")
			List<ActionWorkflow> wfList = query.getResultList();
			
			// TODO TROPPO INEFFICIENTE
			for(int i=0; i<wfList.size(); i++) {
				if (wfList.get(0).getTestPlan().getId().intValue()==tp.getId().intValue())
					return wfList.get(0).getId();
			}
			
		} catch (Exception e) {
			return new Long(-1);
		}
        
		return new Long(-2);
	}*/

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
	public Report runAction(Action action, Report report) {
	
		//boolean result = false;
		report.setPartialResultSuccessfully(false);

		

		try {
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
			System.out.println("poiu testActionListXML:" + testActionListXML.getLength());
			
			Node actionNode = testActionListXML.item(0);
			String globalResult = reportDOM.getGlobalResult();
			System.out.println("poiu globalResult:" + globalResult);
			
			if ( !globalResult.equals(Report.getUndefinedResult()) ) {
				
				System.out.println("poiu clono");
				
				// 1. Clono actionNode
				Node actionNodeClone = actionNode.cloneNode(true);
				actionNodeClone = reportDOM.doc.importNode(actionNodeClone, true); 
				reportDOM.insertActionNode(actionNodeClone);
				reportDOM.setNumberAttribute(actionNodeClone, new Integer(action.getActionNumber()).toString());
				
				// Ora actionNode punta all'actionNodeClone
				actionNode = actionNodeClone;
				
			}
			else {
				// Se GlobalResul è "empty"
				// dovrò individuare l'unica action contenuta
				
				System.out.println("non clono, modifico");
				
				Element actionElement = (Element) actionNode;
				
				 if ( (testActionListXML.getLength()!=1) || (!actionElement.getAttribute("number").equals("0")) ) {
					 System.out.println("ERROR: Inconsistent Report Template, GlobalResult is empty but there is an executed action.");
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
				reportDOM.setTestJumpPrerequisitesAttribute(testNode, action.isJumpTurnedON());
				reportDOM.setTestLgAttribute(testNode, action.getTestLanguage());
				reportDOM.setTestLocationAttribute(testNode, action.getTestLocation());
				reportDOM.setTestTypeAttribute(testNode, action.getTestType());
				
				// Fine della modifica XML che NON riguarda l'esito dell'action
				report.setXml(reportDOM.getXMLString());	
				
				// ORA SI ESEGUE L'ACTION
				
				report.addToFullDescription("\n");
				report.addToFullDescription("\n");
				report.addToFullDescription("\n-- Start Execution of Action: " + action.getActionName() + "--");
	
				
				
				TestManagerImpl testManager = new TestManagerImpl();
				
				// TAF Building
				report.addToFullDescription("\nBuilding TAF for action: " + action.getActionName());
				TAF taf = testManager.buildTAF(action);
				
				
				// TAF Execution
				if (taf != null) {
					
					report.addToFullDescription("\nBuilt TAF " + taf.getName() + " successful.");
					report.addToFullDescription("\nStart execution TAF " + taf.getName());
					
					// EXECUTE TAF
					report = testManager.executeTAF(taf, report);
					//report.setPartialResultSuccessfully(tafResult);
					
					report.addToFullDescription("\nResult: " + report.isPartialResultSuccessfully());
				}
				else
					report.addToFullDescription("\nBuilt TAF Failure.");
	
				
				report.addToFullDescription("\n-- End Execution of Action: " + action.getActionName() + "--");
				
				if ( report.isPartialResultSuccessfully() ) {
					reportDOM.setGlobalResult(Report.getSuccessfulResult());
					report.setXml(reportDOM.getXMLString());	
				}
				
				

				// Persist Report
				//report.setState(Report.getFinalState());
				boolean updating = reportManager.updateReport(report);
				
				
				// SET Action as DONE
				action = this.readAction(action.getId());
				action.setState(Action.getDoneState());
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


	public Session runWorkflow(ActionWorkflow workflow, Session session) {
		
		//boolean result = true;
		
		Report report = session.getReport();
		
				
		// io credo dovrei fare una ricerca del tipo readActionByWorkflowId
		// eseguire questa action e una volta eseguita, eliminarla dal DB o settarla come "done"
		List<Action> actionList = workflow.getActions();
		int actionListSize = workflow.getActions().size();

		
		int actionMark = workflow.getActionMark(); 
		
		System.out.println("actionMark: " + actionMark);
		
		System.out.println("actionListSize: " + actionListSize);
			

		Action a = (Action) actionList.get(actionMark-1);
			
		if (a != null) {
			
			report = runAction(a, report);
			report.setFinalResultSuccessfully(report.isFinalResultSuccessfully() && report.isPartialResultSuccessfully());
			
			if ( report.isPartialResultSuccessfully() ) {
				
				actionMark++;				
				workflow.setActionMark(actionMark);
			}
		}
		else 
			report.setFinalResultSuccessfully(false);
		
		System.out.println("actionMark2: " + actionMark);
		
		System.out.println("actionListSize2: " + actionListSize);		

		
		// Se questa era l'ultima azione il Report è concluso
		// e la sessione di Test termina
		if (actionMark > actionListSize) {
			
			report.setState(Report.getFinalState());
			
		}
		
		// TODO persistere: workflow, report e aggiornare session
		boolean updating = this.updateWorkflow(workflow);
		
		updating = updating && reportManager.updateReport(report);

		return session;
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
	
}


