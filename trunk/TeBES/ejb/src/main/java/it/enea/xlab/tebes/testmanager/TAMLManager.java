package it.enea.xlab.tebes.testmanager;

import it.enea.xlab.taml.TAML2Java;
import it.enea.xlab.taml.TAMLDOM;
import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Properties;
import it.enea.xlab.tebes.dao.TeBESDAO;
import it.enea.xlab.tebes.model.Action;
import it.enea.xlab.tebes.model.ReportFragment;
import it.enea.xlab.tebes.model.TAF;
import it.enea.xlab.tebes.model.Target;
import it.enea.xlab.tebes.model.TestRule;
import it.enea.xlab.tebes.model.Variable;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Node;
import org.xlab.utilities.XLabException;
import org.xlab.xml.XLabDOMException;
import org.xml.sax.SAXException;


/**
 * TAML Manager
 * Create TAF structure from TAML (TAML4TeBES profile) 
 * 
 * @param action
 * @return
 */
public class TAMLManager extends TestManagerImpl implements TestManagerLocal {


	
	public TAMLManager() {

	}

	
	/**
	 * Nel TAML Manager la builTAF gestisce l'action di tipo TAML
	 */
	public TAF buildTAF(Action action) {

		TAF taf = new TAF();

		System.out.println("---");
		System.out.println("START TAMLManager.buildTestAssertionTAF: " + action.getActionName());
		
		// Test Suite Execution
		if (action.getTestType().equals(Constants.TS)) {
			
			taf = buildTestSuiteTAF(action);
		}

		// Test Case Execution
		if (action.getTestType().equals(Constants.TC)) {
			
			taf = buildTestCaseTAF(action);
		}
		
		// Test Assertion Execution
		if (action.getTestType().equals(Constants.TA)) {
			
			
			taf = buildTestAssertionTAF(action);
		}
		
		System.out.println("END TAMLManager.buildTestAssertionTAF: " + action.getActionName());
		System.out.println("---");
		
		
		return taf;
	}



	private TAF buildTestSuiteTAF(Action action) {
		
		return null;
	}

	
	/**
	 * buildTestCaseTAF
	 * Build TAF representing a Test Case defined through TAML standard.
	 * The resulting TAF has an empty predicate but a vector of actions representing the test assertions of testcases
	 * 
	 * @param action
	 * @return TAF
	 */	
	private TAF buildTestCaseTAF(Action action) {

		// TAF Result
		TAF taf = null;

		// Manager to transform TAML XML to Java executable structures
		TAML2Java t2j = new TAML2Java();
		
		TAMLDOM tamlDOM;
		Hashtable<String, Action> taHashtable = null;
				
		// DAO: Retrieving of absolute TAML file path
		// (nel Test Plan viene recuperato il path relativo
		// p.es. "TeBES/testsuites/UBL/TS-005_sdi/TC-001_SDI-UBL-T10.xml"
		// a cui viene qui aggiunto	Properties.TeBES_HOME)
		String absoluteLocation = TeBESDAO.url2localLocation(action.getTestLocation());					
		System.out.println("pre-TAMLDOM:" + absoluteLocation);
		
		try {
		
			// Get DOM object from TAML 
			tamlDOM = new TAMLDOM(absoluteLocation, Properties.TAML_XMLSCHEMA, true);

			// Get TestAssertion Hashtable from TAML
			taHashtable = t2j.getTestAssertionHashtable(tamlDOM);	
			
			Enumeration<Action> actionsEnumeration = taHashtable.elements();
			Vector<Action> actionsVector = new Vector<Action>();
			
			while (actionsEnumeration.hasMoreElements()) {
				
				actionsVector.add(actionsEnumeration.nextElement());
				
			}
			
			System.out.println("actionsVector.size: " + actionsVector.size());

			
			// Ora devo creare una TAF che rappresenta il mio Test Case
			// Non ha predicati ma ha una serie di actions
			//taf = new TAF(action.getActionName(), action., predicate, prerequisites, jumpTurnedON, prescription, reportFragments, note)
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XLabException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XLabDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return taf;
	}
	
	
	/**
	 * buildTestAssertionTAF
	 * Build TAF representing a Test Assertion defined through TAML standard and specified by Action.
	 * 
	 * @param action
	 * @return TAF
	 */
	private TAF buildTestAssertionTAF(Action action) {

		// TAF Result
		TAF taf = null;
		
		// Manager to transform TAML XML to Java executable structures
		TAML2Java t2j = new TAML2Java();
		
		// Model Structures Declaration
		TAMLDOM tamlDOM;
		Node testAssertionNode;
		Hashtable<String, Variable> variableHashtable;
		Target target;
		TestRule predicate = null, prerequisite = null;
		String prescription;
		Hashtable<String, Action> taHashtable = null, taRefHashtable = null;
		Vector<Action> prerequisites = null;
		
		try {

			// DAO: Retrieving of absolute TAML file path
			// (nel Test Plan viene recuperato il path relativo
			// p.es. "TeBES/testsuites/UBL/TS-005_sdi/TC-001_SDI-UBL-T10.xml"
			// a cui viene qui aggiunto	Properties.TeBES_HOME)
			String absoluteLocation = TeBESDAO.url2localLocation(action.getTestLocation());					
			//System.out.println("pre-TAMLDOM:" + absoluteLocation);
			// Get DOM object from TAML 
			tamlDOM = new TAMLDOM(absoluteLocation, Properties.TAML_XMLSCHEMA, true);

			// Get TestAssertion Hashtable from TAML
			taHashtable = t2j.getTestAssertionHashtable(tamlDOM);
			
			// Get testAssertion Node from TAML
			testAssertionNode = tamlDOM.getTestAssertionNode((action.getTestValue()));

			// Get Variables Hashtable from TAML
			variableHashtable = t2j.getVariableHashtable(tamlDOM, testAssertionNode);

			// Get Target
			target = t2j.getTarget(tamlDOM, testAssertionNode);

			// Get Predicate from TAML
			predicate = t2j.getPredicate(tamlDOM, testAssertionNode);		

			// Get Prescription
			prescription = t2j.getPrescriptionLevel(tamlDOM, testAssertionNode);
				
			// Se il SALTO dei prerequisiti NON è impostatato
			if (!action.isJumpTurnedON()) {

				// Get Prerequisite	from TAML
				prerequisite = t2j.getPrerequisite(tamlDOM, testAssertionNode);		
	
				// Se il prerequisito c'è
				if (prerequisite != null) {
					
					// TODO SE PRESENTI CONNETTORI LOGICI AND O OR AL MOMENTO RITORNA NULL
					// altrimenti ritorna un vettore con il solo prerequisite passato.
					 Vector<TestRule> testRuleList = this.andSplitTestRule(prerequisite);				
			
					// Get TestAssertionRef Hashtable from TAML
					taRefHashtable = t2j.getTestAssertionRefHashtable(tamlDOM);			
	
					// Normalize Prerequisite 
					prerequisites = this.normalize(testRuleList, variableHashtable, taHashtable, taRefHashtable, true);
				}
			}
			
			 Vector<TestRule> testRuleList2 = new Vector<TestRule>();
			 testRuleList2.add(predicate);
			 
			// Normalize Predicate
			// Se il predicato TAML rispetta il profilo TAML4TeBES
			// allora il predicato è sicuramente un'espressione XPath o Schematron
			Vector<Action> predicates = this.normalize(testRuleList2, variableHashtable, taHashtable, taRefHashtable, action.isJumpTurnedON());
			
			// Get Report Hashtable from TAML
			Hashtable<String, ReportFragment> reportHashtable = t2j.getReportHashtable(tamlDOM, testAssertionNode);			
			

			// Adjust Predicate Test Rule
			// TODO Si assume che il predicato sia 1 ed 1 soltanto
			Action predicateAction = (Action) predicates.firstElement();
			predicate.setLanguage(predicateAction.getTestLanguage());
			predicate.setValue(predicateAction.getTestLocation());
			predicate.setLogicConnectorIsPresent(false);
			
			///// Creazione della TAF ///// 			
			taf = new TAF(action.getActionName(), target, predicate, prerequisites, action.isJumpTurnedON(), prescription, reportHashtable, null);
			
	
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
		} catch (XLabException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XLabDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return taf;
	}
	
	
	/**
	 * Il metodo normalize è usato per normalizzare sia il predicato che il prerequisito
	 * Per "normalizzare" si intende esplicitare una regola quando essa fa riferimento a variabili o test assertion esterne.
	 * Nel caso del predicato restituirà un vettore con una sola Action in quanto, per assunto, il predicato è una singola espressione.
	 * 
	 * @param testRule
	 * @param variableHashtable
	 * @param taRefHashtable
	 * @param taRefHashtable2 
	 * @param jump
	 * @return
	 */
	private Vector<Action> normalize(Vector<TestRule> testRuleList, Hashtable<String, Variable> variableHashtable, Hashtable<String, Action> taHashtable, Hashtable<String, Action> taRefHashtable, boolean jump) {
		
		Vector<Action> result = null;
		
		// Se tempPrerequisites probabilmente lo split ha incontrato un errore a causa di presenza di connettori logici
		if (testRuleList != null) {

			result = new Vector<Action>();
			
			int i=0;
			while (i<testRuleList.size()) {
				
				TestRule singleTestRule = (TestRule) testRuleList.elementAt(i);
				
				// se il linguaggio è uguale a "variabile"
				if (singleTestRule.getLanguage().equals(TAML2Java.VARIABLE_LG)) {
					
					// prendo la variabile
					Variable var = variableHashtable.get(singleTestRule.getValue());
				
					// se la variabile è di tipo TAML ci sono 2 casi:
					// 1. il prerequisito è una TA interna
					// 2. il prerequisito è una TA esterna
					if (var.getType().equals(TAML2Java.TAML_TYPE)) {
						
						Action taRef;
						
						// caso 1. TA interna
						taRef = taHashtable.get(var.getName());
						if (taRef != null) {
							taRef.setActionNumber(i+1);
							result.add(taRef);
						}
						else {
						
							// caso 2. TA esterna
							taRef = taRefHashtable.get(var.getName());
							
							if (taRef != null) {
								taRef.setActionNumber(i+1);
								result.add(taRef);
							}
						}
					}

					// se la variabile è di tipo Schematron
					if (var.getType().equals(TAML2Java.SCHEMATRON_TYPE)) {
						
						// prendo la TA esterna
						// TODO qui prendo il nome della variabile
						Action schematron = new Action(i+1, var.getName(), TAML2Java.SCHEMATRON_TYPE, Constants.TA, var.getValue(), var.getValue(), jump, null);
						
						result.add(schematron);
					}
					
					if (var.getType().equals(TAML2Java.XPATH_TYPE)) {
						
						Action xpath = new Action(i+1, var.getName(), TAML2Java.XPATH_TYPE, Constants.TA, var.getValue(), var.getValue(), jump, null);
						
						result.add(xpath);
					}
				} 
				// Se il linguaggio è XPATH (senza passare dalla variabile)
				else if (singleTestRule.getLanguage().equals(TAML2Java.XPATH_TYPE)) {
					
					Action xpath = new Action(i+1, "xpath".concat((new Integer(i+1)).toString()), TAML2Java.XPATH_TYPE, Constants.TA, singleTestRule.getValue(), singleTestRule.getValue(), jump, null);
					
					result.add(xpath);
				}
					
				i++;
				
			} // end while
			
		} //end else

		// returns the Vector of Action
		return result;
		
	} // end normalize method

	
	// TODO : è supportato solo AND
	// PER ORA NON EFFETTUA NIENTE E SE CONTIENE AND O OR RITORNA NULL!!!
	// Si dovrebbe splittare l'espressione composta nei vari componenti
	// ma quali sono le precedenze?
	// e come mantenerle una volta convertiti i componenti in singole Test Action?
	// probabilmente la soluzione è la stessa che si userà nel Test Plan 
	// per getire l'elemento Choreography.
	private Vector<TestRule> andSplitTestRule(TestRule prerequisite) {
		
		Vector<TestRule> result = new Vector<TestRule>();

		if (prerequisite.isLogicConnectorPresent()) {
			
			String[] temp = prerequisite.getValue().split(prerequisite.AND_DELIMITER);
			
			for (int i = 0; i < temp.length; i++) {
				
				TestRule tr = new TestRule(prerequisite.getLanguage(), temp[i]);
				System.out.println("SPLIT: " + temp[i]);
				result.add(tr);
			}
		}
		else
			result.add(prerequisite);
		
		return result;
	}
	


}


