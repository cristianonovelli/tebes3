package it.enea.xlab.tebes.test;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.dao.TeBESDAO;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.model.ReportFragment;
import it.enea.xlab.tebes.model.TAF;
import it.enea.xlab.tebes.model.Target;
import it.enea.xlab.tebes.model.TestRule;
import it.enea.xlab.tebes.model.Variable;
import it.enea.xlab.tebes.test.taml.TAML2Java;
import it.enea.xlab.tebes.test.taml.TAMLDOM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.log4j.Logger;
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
public class TAMLManager extends TestManagerImpl implements TestManagerRemote {

	
	// Logger
	private static Logger logger = Logger.getLogger(TAMLManager.class);
	
	
	public TAMLManager() {

	}
 
	
	/**
	 * Nel TAML Manager la builTAF gestisce l'action di tipo TAML
	 */
	public ArrayList<TAF> buildTAF(Action action) {

		ArrayList<TAF> tafListResult = new ArrayList<TAF>();
		
		TAF taf = null;
		
		// Test Suite 
		if (action.getTestType().equals(Constants.TS)) {
			
			tafListResult = buildTestSuiteTAF(action);
		}

		// Test Case 
		if (action.getTestType().equals(Constants.TC)) {
			
			tafListResult = buildTestCaseTAF(action);
		}
		
		// Test Assertion 
		if (action.getTestType().equals(Constants.TA)) {
			
			
			taf = buildTestAssertionTAF(action);
			tafListResult.add(taf);
		}
		
		
		
		return tafListResult;
	}


	
	private ArrayList<TAF> buildTestSuiteTAF(Action action) {
		
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
	private ArrayList<TAF> buildTestCaseTAF(Action action) {

		ArrayList<TAF> tafListResult = new ArrayList<TAF>();
		
		// TAF 
		TAF taf = null;

		// Manager to transform TAML XML to Java executable structures
		TAML2Java t2j = new TAML2Java();
		
		TAMLDOM tamlDOM;
		Hashtable<String, Action> taHashtable = null;
				


		
		try {

			// DAO: Retrieving of absolute TAML file path
			// (nel Test Plan viene recuperato il path relativo
			// p.es. "TeBES/testsuites/UBL/TS-005_sdi/TC-001_SDI-UBL-T10.xml"
			// a cui viene qui aggiunto	la root artifacts delle properties
			String absoluteLocation = TeBESDAO.url2localLocation(action.getTestLocation());					
			if (absoluteLocation == null) {
				logger.error("ERROR: Method url2localLocation returns null!");
				return null;
			}
			
			// Get DOM object from TAML 
			tamlDOM = new TAMLDOM(absoluteLocation, PropertiesUtil.getTAMLXMLSchema(), true);

			// Get TestAssertion Hashtable from TAML
			taHashtable = t2j.getTestAssertionHashtable(tamlDOM, action.getInputs());	
			
			Enumeration<Action> actionsEnumeration = taHashtable.elements();
			ArrayList<Action> actionsVector = new ArrayList<Action>();
			
			while (actionsEnumeration.hasMoreElements()) {
				
				
				// TODO To Check
				tafListResult.add(this.buildTestAssertionTAF(actionsEnumeration.nextElement()));				
			}
			
			System.out.println("actionsArrayList.size: " + actionsVector.size());

			
			
			
			
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
		
		return tafListResult;
	}
	
	
	/**
	 * buildTestAssertionTAF
	 * Build TAF representing a Test Assertion defined through TAML standard and specified by Action.
	 * 
	 * @param action
	 * @return TAF
	 */
	private TAF buildTestAssertionTAF(Action action) {

		System.out.println("buildTestAssertionTAF-1");
		
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
		ArrayList<Action> prerequisites = null;
		
		try {

			// DAO: Retrieving of absolute TAML file path
			// (nel Test Plan viene recuperato il path relativo
			// p.es. "TeBES/testsuites/UBL/TS-005_sdi/TC-001_SDI-UBL-T10.xml"
			// a cui viene qui aggiunto	Properties.TeBES_HOME)
			String absoluteLocation = TeBESDAO.url2localLocation(action.getTestLocation());		
			
			if (absoluteLocation == null) {
				logger.error("ERROR: Method url2localLocation returns null!");
				return null;
			} else
				System.out.println("buildTestAssertionTAF-2");
			
			
			// Get DOM object from TAML 
			tamlDOM = new TAMLDOM(absoluteLocation, PropertiesUtil.getTAMLXMLSchema(), true);

			// Get TestAssertion Hashtable from TAML
			taHashtable = t2j.getTestAssertionHashtable(tamlDOM, action.getInputs());
			
			System.out.println("buildTestAssertionTAF-3");
			System.out.println("buildTestAssertionTAF testValue:" + action.getTestValue());
			
			// Get testAssertion Node from TAML
			testAssertionNode = tamlDOM.getTestAssertionNode((action.getTestValue()));

			// Get Variables Hashtable from TAML
			System.out.println("buildTestAssertionTAF XML:" + tamlDOM.getXMLString());
			System.out.println("buildTestAssertionTAF node:" + testAssertionNode.getNodeName());
			variableHashtable = t2j.getVariableHashtable(tamlDOM, testAssertionNode);

			// Get CommonNamespaces Hashtable from TAML
			Hashtable<String, String> commonNamespacesHashtable = t2j.getCommonNamespacesHashtable(tamlDOM);	
			
			// Get Target
			target = t2j.getTarget(tamlDOM, testAssertionNode);

			System.out.println("buildTestAssertionTAF-4");
			
			// Get Predicate from TAML
			predicate = t2j.getPredicate(tamlDOM, testAssertionNode);		

			// Get Prescription
			prescription = t2j.getPrescriptionLevel(tamlDOM, testAssertionNode);
				
			// Se il SALTO dei prerequisiti NON è impostatato
			if (!action.isSkipTurnedON()) {

				// Get Prerequisite	from TAML
				prerequisite = t2j.getPrerequisite(tamlDOM, testAssertionNode);		
	
				// Se il prerequisito c'è
				if (prerequisite != null) {
					
					// TODO SE PRESENTI CONNETTORI LOGICI AND O OR AL MOMENTO RITORNA NULL
					
					// altrimenti ritorna un vettore con il solo prerequisite passato.
					ArrayList<TestRule> testRuleList = this.andSplitTestRule(prerequisite);				
			
					// Get TestAssertionRef Hashtable from TAML
					taRefHashtable = t2j.getTestAssertionRefHashtable(tamlDOM, action.getInputs());			
					
					
					// Normalize Prerequisite 
					prerequisites = this.normalize(true, testRuleList, variableHashtable, taHashtable, taRefHashtable, commonNamespacesHashtable, true);
					
				}
			}

			System.out.println("buildTestAssertionTAF-5");
			
			ArrayList<TestRule> testRuleList2 = new ArrayList<TestRule>();
			 testRuleList2.add(predicate);
			 
			// Normalize Predicate
			// Se il predicato TAML rispetta il profilo TAML4TeBES
			// allora il predicato è sicuramente un'espressione XPath o Schematron
			 ArrayList<Action> predicates = this.normalize(false, testRuleList2, variableHashtable, taHashtable, taRefHashtable, commonNamespacesHashtable, action.isSkipTurnedON());
			
			// Get Report Hashtable from TAML
			Hashtable<String, ReportFragment> reportHashtable = t2j.getReportHashtable(tamlDOM, testAssertionNode);			
			
			System.out.println("buildTestAssertionTAF-6");

			// Adjust Predicate Test Rule
			// TODO Si assume che il predicato sia 1 ed 1 soltanto
			Action predicateAction = (Action) predicates.get(0);

			
			predicate.setLanguage(predicateAction.getTestLanguage());
			predicate.setValue(predicateAction.getTestValue());
			predicate.setLogicConnectorIsPresent(false);

			String comment = "Generated from Action: ".concat(action.getActionName());
			comment = tamlDOM.getDescriptionValue(testAssertionNode);
			
			///// Creazione della TAF ///// 			
			taf = new TAF(action.getActionName(), action.isPrerequisite(), target, predicate, prerequisites, action.isSkipTurnedON(), prescription, reportHashtable, action.getInputs(), comment);
			
	
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
	 * @param skip
	 * @return
	 */
	private ArrayList<Action> normalize(boolean prerequisite, ArrayList<TestRule> testRuleList, 
			Hashtable<String, Variable> variableHashtable, Hashtable<String, Action> taHashtable, Hashtable<String, Action> taRefHashtable, Hashtable<String, String> namespacesHashtable, boolean skip) {
		
		
		
		ArrayList<Action> result = null;
		
		// Se tempPrerequisites probabilmente lo split ha incontrato un errore a causa di presenza di connettori logici
		if (testRuleList != null) {

			result = new ArrayList<Action>();
			
			int i=0;
			while (i<testRuleList.size()) {
				
				TestRule singleTestRule = (TestRule) testRuleList.get(i);
				
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

					// creo l'id della test action come:
					// "ta" + var.getValue() + i+1
					String actionId = "ta-".concat(var.getValue()).concat(new String(new Integer(i+1).toString()));
					
					// se la variabile è di tipo Schematron
					if (var.getType().equals(TAML2Java.SCHEMATRON_TYPE)) {
						
						
						
						// prendo la TA esterna
						// TODO qui prendo il nome della variabile
						Action schematron = new Action(prerequisite, i+1, var.getName(), actionId, Action.getReadyState(), TAML2Java.SCHEMATRON_TYPE, Constants.TA, var.getValue(), var.getValue(), skip);
						
						result.add(schematron);
					}
					
					if (var.getType().equals(TAML2Java.XPATH_TYPE)) {
						
						Action xpathAction = new Action(prerequisite, i+1, var.getName(), actionId, Action.getReadyState(), TAML2Java.XPATH_TYPE, Constants.TA, var.getValue(), var.getValue(), skip);
						
						if ( xpathAction.getTestValue().startsWith("count(//") && xpathAction.getTestValue().endsWith(") ge 1")) {
							
							xpathAction = this.normalizeXPathToXMLSChema(xpathAction, namespacesHashtable);
						}
						
						result.add(xpathAction);
					}
				} 
				// Se il linguaggio è XPATH (senza passare dalla variabile)
				else if (singleTestRule.getLanguage().equals(TAML2Java.XPATH_TYPE)) {
					
					
					String xpathId = "xpath".concat((new Integer(i+1)).toString());
					Action xpath = new Action(prerequisite, i+1, xpathId, xpathId, Action.getReadyState(), TAML2Java.XPATH_TYPE, Constants.TA, singleTestRule.getValue(), singleTestRule.getValue(), skip);
					
					result.add(xpath);
				}
					
				i++;
				
			} // end while
			
		} //end else

		// returns the Vector of Action
		return result;
		
	} // end normalize method

	
	private Action normalizeXPathToXMLSChema(Action xpathAction, Hashtable<String, String> namespacesHashtable) {
		
		
		
		
		// 1. Ricavo ublin dalla stringa
		String value = xpathAction.getTestValue();
		if ( value.startsWith("count(//") && value.endsWith(") ge 1")) {
			
			// Recupero namespace dalla Action
			String nsName = value.substring(value.lastIndexOf("/")+1, value.lastIndexOf(":"));
			
			// Recupero URL XML Schema dalla namespacesHashtable utilizzando come chiave il namespace ricavato
			String nsURL = namespacesHashtable.get(nsName);
			
			if (nsURL != null) {
				xpathAction.setTestLanguage(Constants.XMLSCHEMA);
				xpathAction.setTestType(Constants.XMLSCHEMA);
				xpathAction.setTestValue(nsURL);
			}
		}

		return xpathAction;
	}
	
	// TODO : è supportato solo AND
	// PER ORA NON EFFETTUA NIENTE E SE CONTIENE AND O OR RITORNA NULL!!!
	// Si dovrebbe splittare l'espressione composta nei vari componenti
	// ma quali sono le precedenze?
	// e come mantenerle una volta convertiti i componenti in singole Test Action?
	// probabilmente la soluzione è la stessa che si userà nel Test Plan 
	// per getire l'elemento Choreography.
	private ArrayList<TestRule> andSplitTestRule(TestRule prerequisite) {
		
		ArrayList<TestRule> result = new ArrayList<TestRule>();

		if (prerequisite.isLogicConnectorPresent()) {
			
			String[] temp = prerequisite.getValue().split(prerequisite.AND_DELIMITER);
			
			for (int i = 0; i < temp.length; i++) {
				
				TestRule tr = new TestRule(prerequisite.getLanguage(), temp[i]);
				result.add(tr);
			}
		}
		else
			result.add(prerequisite);
		
		return result;
	}


}


