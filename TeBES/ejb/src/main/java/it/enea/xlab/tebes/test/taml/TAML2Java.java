package it.enea.xlab.tebes.test.taml;


import java.util.Hashtable;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.model.ReportFragment;
import it.enea.xlab.tebes.model.Target;
import it.enea.xlab.tebes.model.TestRule;
import it.enea.xlab.tebes.model.Variable;



public class TAML2Java {

	public static final String VARIABLE_LG = "variable";
	
	public static final String TAML_TYPE = "http://docs.oasis-open.org/ns/tag/taml-201002/";
	public static final String SCHEMATRON_TYPE = "http://purl.oclc.org/dsdl/schematron";
	public static final String XPATH_TYPE = "http://www.w3.org/TR/xpath20/";

	
	
	/**
	 * Get Predicate
	 * 
	 * @return predicate TestRule (lg, value, isAndPresent)
	 */
	public TestRule getPredicate(TAMLDOM tamlDOM, Node testAssertionNode) {
		
		TestRule result = null;
		
		Element predicateElement = (Element) tamlDOM.getPredicateNode(testAssertionNode);
			
		result = testRuleHandler(tamlDOM, predicateElement);
			
		return result;
	}


	/**
	 * Get Prerequisite
	 * 
	 * @return prerequisite TestRule (lg, value, andIsPresent)
	 */
	public TestRule getPrerequisite(TAMLDOM tamlDOM, Node testAssertionNode) {

		TestRule result = null;
		
		Element prerequisiteElement = (Element) tamlDOM.getPrerequisiteNode(testAssertionNode);
			
		result = testRuleHandler(tamlDOM, prerequisiteElement);
			
		return result;
	}


	/**
	 * testRuleHandler
	 * Predicate and Prerequisite TestRules Handler
	 * N.B. if language isn't present, take "variable" value by default
	 * 
	 * @return TestRule (lg, value, andIsPresent)
	 */
	private TestRule testRuleHandler(TAMLDOM tamlDOM, Element testRuleElement) {
		
		TestRule result = null;
		
		if (testRuleElement != null) {
			String language = tamlDOM.getLgAttribute(testRuleElement);
			String value = tamlDOM.getNodeValue(testRuleElement);	
			
			if (language.equals("")) {

				language = VARIABLE_LG;			
			} 

			result = new TestRule(language, value);	
		}				

		return result;		
	}
	
	
	/**
	 * getTestAssertionRefHashtable
	 * 
	 * @return Hashtable (taId, TestAction)
	 * where TestAction is an external Test Assertion
	 */
	public Hashtable<String, Action> getTestAssertionRefHashtable(TAMLDOM tamlDOM) {	
	
		Hashtable<String, Action> actionTable = null;
		
		NodeList externalTANodeList = tamlDOM.getTestaAssertionRefListNodes();
	
		if ( externalTANodeList.getLength() > 0 ) {
			
			actionTable = new Hashtable<String, Action>();
					
			for (int i = 0; i < externalTANodeList.getLength(); i++) {
				
				String value = tamlDOM.getTaidAttribute(externalTANodeList.item(i));
				String name = tamlDOM.getNameAttribute(externalTANodeList.item(i));
				String location = tamlDOM.getSourcedocAttribute(externalTANodeList.item(i));
				
				// String actionId = "ta-taref".concat((new Integer(i+1)).toString());
				Action taAction = new Action(i+1, name, Action.getTodoState(), Constants.TAML, Constants.TA, location, value, false, null,  null, null, null);
				actionTable.put(value, taAction);
			}
		}
		
		return actionTable;
	}
	
	
	/**
	 * getVariableHashtable
	 * 
	 * @return Hashtable (vname, Variable)
	 * where Variable is a three values record (vname, vtype, vvalue)
	 */
	public Hashtable<String, Variable> getVariableHashtable(TAMLDOM tamlDOM, Node testAssertionNode) {		

		Hashtable<String, Variable> variableHashtable = null;
		
		NodeList varNodeList = tamlDOM.getVarNodes(testAssertionNode);
		
		if ( varNodeList.getLength() > 0 ) {
			
			variableHashtable = new Hashtable<String, Variable>();
			
			for (int i = 0; i < varNodeList.getLength(); i++) {
				
				String vname = tamlDOM.getVnameAttribute(varNodeList.item(i));
				String vtype = tamlDOM.getVtypeAttribute(varNodeList.item(i));
				String vvalue = tamlDOM.getNodeValue(varNodeList.item(i));
	
				Variable var = new Variable(vname, vvalue, vtype);
				variableHashtable.put(vname, var);					
			}			
		}
		
		return variableHashtable;	
	}


	public String getPrescriptionLevel(TAMLDOM tamlDOM, Node testAssertionNode) {
			
		return tamlDOM.getPrescriptionLevel(testAssertionNode);
	}


	public Target getTarget(TAMLDOM tamlDOM, Node testAssertionNode) {
		
		Target result = new Target();
		
		Element targetElement = (Element) tamlDOM.getTargetNode(testAssertionNode);
		
		result.setValue(tamlDOM.getNodeValue(targetElement));
		result.setType(tamlDOM.getTypeAttribute(targetElement));
			
		return result;
	}


	/**
	 * getReportHashtable
	 * 
	 * @return Hashtable (label, ReportFragment)
	 * where ReportFragment is a three values record (label, message, description)
	 */
	public Hashtable<String, ReportFragment> getReportHashtable(TAMLDOM tamlDOM, Node testAssertionNode) {

		Hashtable<String, ReportFragment> reportHashtable = null;
		
		NodeList reportNodeList = tamlDOM.getReportNodes(testAssertionNode);
		
		if ( reportNodeList.getLength() > 0 ) {
			
			reportHashtable = new Hashtable<String, ReportFragment>();
			
			for (int i = 0; i < reportNodeList.getLength(); i++) {
				
				String label = tamlDOM.getLabelAttribute(reportNodeList.item(i));
				String message = tamlDOM.getMessageAttribute(reportNodeList.item(i));
				String description = tamlDOM.getNodeValue(reportNodeList.item(i));
	
				ReportFragment rep = new ReportFragment(label, message, description);
				reportHashtable.put(label, rep);					
			}			
		}
		
		return reportHashtable;				
	}


	public Hashtable<String, Action> getTestAssertionHashtable(TAMLDOM tamlDOM) {
		
		Hashtable<String, Action> actionTable = null;
		
		NodeList taNodeList = tamlDOM.getTestAssertionNodeList();
	
		if ( taNodeList.getLength() > 0 ) {
			
			actionTable = new Hashtable<String, Action>();
					
			for (int i = 0; i < taNodeList.getLength(); i++) {
				
				String value = tamlDOM.getIdAttribute(taNodeList.item(i));
				String name = tamlDOM.getNameAttribute(taNodeList.item(i));
				String location = tamlDOM.xmlFile;
				String description = tamlDOM.getDescriptionValue(taNodeList.item(i));
						
				boolean jump;
				if (tamlDOM.getPrescriptionLevel(taNodeList.item(i)).equals(Constants.MANDATORY))
					jump = false;
				else
					jump = true;
				
				//String actionId = "ta-ta".concat((new Integer(i+1)).toString());
				Action taAction = new Action(i+1, name, Action.getTodoState(), Constants.TAML, Constants.TA, location, value, jump, description,  null, null, null);
				actionTable.put(value, taAction);
			}
		}
		
		return actionTable;
	}


	public Hashtable<String, String> getCommonNamespacesHashtable(TAMLDOM tamlDOM) {
		
		Hashtable<String, String> commonNamespacesTable = new Hashtable<String, String>();
		
		NodeList namespacesNodeList = null;

			try {
				namespacesNodeList = tamlDOM.getCommonNamespacesNodeList();
				
				if ( namespacesNodeList.getLength() > 0 )
					System.out.println("qwqw5");
				
				
				for (int i = 0; i < namespacesNodeList.getLength(); i++) {
							
							Node nsNode = namespacesNodeList.item(i);
							String label = nsNode.getAttributes().item(0).getLocalName();
							
							if ((label != null) && !label.equals("")) {
							
							// TODO la stringa "xmlns:" andrebbe isolata in una costante
							String value = tamlDOM.getNodeAttribute(nsNode, "xmlns:".concat(label));
							
								if ((value != null) && !value.equals("")) {
							
									System.out.println("label:" + label);
									System.out.println("value:" + value);
									
									// Insert into the Hashtable
									commonNamespacesTable.put(label, value);
								}
							
							}
				}				

			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				commonNamespacesTable = null;
			}

		return commonNamespacesTable;
	}

	
}



