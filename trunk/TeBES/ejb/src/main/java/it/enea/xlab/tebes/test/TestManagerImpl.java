package it.enea.xlab.tebes.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
 
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import it.enea.xlab.tebes.common.Constants; 
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.model.TAF;
import it.enea.xlab.tebes.model.TestRule;
import it.enea.xlab.tebes.report.ReportDOM;
import it.enea.xlab.tebes.report.ReportManagerRemote;
import it.enea.xlab.tebes.test.rule.RuleManagerImpl;


@Stateless
@Interceptors({Profile.class})
public class TestManagerImpl implements TestManagerRemote {

	private static final String TESTMANAGER_POSTFIX = "Manager";
	private static final String BUILDTAF_METHOD = "buildTAF";


	public TestManagerImpl() {
		
	}
	

	/**
	 * @throws ClassNotFoundException se, tramite reflection, i Manager non vengono trovati 
	 * (p.es. se non sono stati definiti nello stesso Package di TestManager)
	 * @return
	 */
	public Vector<TAF> buildTAF(Action action) {
		
		Vector<TAF> tafListResult = new Vector<TAF>();
		TAF tafResult = null;

		try {
			
			String thisPackage = this.getClass().getPackage().getName();
			Class extendedManager = Class.forName(thisPackage.concat(".").concat(action.getTestLanguage().toUpperCase().concat(TESTMANAGER_POSTFIX)));
			Method xMethod = extendedManager.getMethod(BUILDTAF_METHOD, Action.class);
			tafListResult = (Vector<TAF>) xMethod.invoke(extendedManager.newInstance(), action);

			//tafListResult.add(tafResult);
			
			// TODO Normalize predicate
			//result.setPredicate(this.normalizeTestRule(result.getPredicate()));
			
			
			// prendo il predicate e se è di tipo xpath e contiene una validazione schema
			// lo modifico in modo tale che esprima quello
			// questo è il punto giusto per farlo, perchè non è strettamente relativo a TAML
			
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tafListResult;
	}
	
	
/*	private TestRule normalizeTestRule(TestRule testRule) {
		
		if (testRule.getLanguage().equals(Constants.XPATH)) {
			
			// Se l'espressione XPath è una richiesta di validazione
			if (testRule.getLanguage().startsWith("count(//") && testRule.getLanguage().endsWith(") ge 1")) {
				
			}
		}
		
		return testRule;
	}*/


	public Report executeTAF(TAF taf, Session session, ReportDOM reportDOM, String actionId) {
		
		// Il metodo executeTAF esegue una TAF costruita da una action e quindi può essere 
		// un prerequisito così come un insieme di Test
		//
		// SUMMARY
		// 1. Valutazione Prerequisiti della TAF
		// 2. Richiamo ricorsivamente il Prerequisito
		// 3. Eseguo il Test della TAF
		
		Report report = session.getReport();		
		boolean okPrerequisites = false;
		
		
		report.addToFullDescription("\n");
		report.addToFullDescription("--- TAF: " + taf.getName() + " ---\n");
		
		
		
		// CASISTICA
		// 1. la TAF è una Action che richiama un singolo test, una Test Assertion;
		// 2. la TAF è una Action che richiama un TestCase o una TestSuite e, quindi, una serie di test (taf.getTests);
		// 3. la TAF è uno o più prerequisiti
		// A QUESTI CASI VA AGGIUNTO CHE LA TAF PUO' AVERE O MENO PREREQUISITI CHE VANNO IN QUEL CASO RICHIAMATI
		// RICORSIVAMENTE COME PRIMA AZIONE
		
		
		
		
		
		
		// 1. La TAF ha prerequisiti che devono essere eseguiti? 
		Vector<Action> prerequisites = taf.getTests();
		if ( !taf.isSkipTurnedON() && (prerequisites != null) ) {
		
			report.addToFullDescription("\nThere are Prerequisites");
			
			// Ciclo sui prerequisites
			int i=0;
			boolean sumPrerequisites = true;
			while (i<prerequisites.size()) {
				
				// Prerequisite Action
				Action a = prerequisites.elementAt(i);
				
				report.addToFullDescription("\nPrerequisite: " + a.getActionName());
				report.addToFullDescription("\nBuilding TAF from: " + a.getActionName());

				
				// Prerequisite TAF from Action
				Vector<TAF> tafList = this.buildTAF(a);
				
				TAF t;
				int j=0;
				while ((j < tafList.size()) && (i<prerequisites.size())) {
				
					t = tafList.get(j);
							
					
					// Recursive execution
					report.addToFullDescription("\n---> Recursive execution");
					
					//boolean singleResult = this.executeTAF(t, report);
					
					
					
					
					report = this.executeTAF(t, session, reportDOM, actionId);
	
					
					try {
					
					//ReportDOM reportDOM = null;
					
	
						//reportDOM = new ReportDOM();
						//reportDOM.setContent(report.getXml());
						
						Node actionNode = reportDOM.getTestAction(actionId);
						System.out.println("getTestActionNode: " + actionNode.getNodeName());

					
					if (reportDOM != null) {
					
						// 
						//Node testResultsNode = reportDOM.getTestResultsElement(actionNode);
						
						System.out.println("\nisPrerequisite > new Node");
						report.addToFullDescription("\nisPrerequisite > new Node");
						
						if (taf.isPrerequisite()) {
						
							
								Node prerequisitesNode = reportDOM.getPrerequisitesElement(actionNode);		
								
								// "1" sta ad indicare il primo nodo figlio, ovvero dopo il nodo testo del nodo corrente
								Node prerequisiteResultNode = prerequisitesNode.getChildNodes().item(1);
								report.addToFullDescription("\n0.PrerequisiteResult/@result=EMPTY > " + prerequisiteResultNode.getNodeName());
								
								// Se NON è un nodo template, lo clono
								// Se E' il nodo template, uso quello
								String resultValue = reportDOM.getAttribute("result", prerequisiteResultNode);
								
								Node selectedPrerequisiteNode;
								if (!resultValue.equals("EMPTY")) {
									
									report.addToFullDescription("\n1.PrerequisiteResult/@result=EMPTY > Clone!");
									Node clonePrerequisiteResultNode = prerequisiteResultNode.cloneNode(true);
									report.addToFullDescription("\n2.PrerequisiteResult/@result=EMPTY > " + clonePrerequisiteResultNode.getNodeName());
						
									clonePrerequisiteResultNode = reportDOM.doc.importNode(clonePrerequisiteResultNode, true); 
									report.addToFullDescription("\n3.PrerequisiteResult/@result=EMPTY taf: " + taf.getName());
									reportDOM.insertPrerequisiteResultNode(clonePrerequisiteResultNode, prerequisiteResultNode, actionNode);
									report.addToFullDescription("\n5.EndPrequisiteResult Clone!");
									
									selectedPrerequisiteNode = clonePrerequisiteResultNode;
								}	
								else {
									
									selectedPrerequisiteNode = prerequisiteResultNode;
								}
	
								
								// Set Prerequisite
								if (report.getTempResult() != null) {
									reportDOM.setPrerequisiteResult(selectedPrerequisiteNode, new Long(j), taf.getName(), report.getTempResult().getGlobalResult(), report.getTempResult().getLine(), report.getTempResult().getMessage());
									System.out.println("TAF Execution: " + report.getTempResult().getGlobalResult());
									report.addToFullDescription("\nTAF Execution: " + report.getTempResult().getGlobalResult());
									report.addToFullDescription("\nisPrerequisite: " + taf.isPrerequisite());
								}
								else {
									reportDOM.setPrerequisiteResult(selectedPrerequisiteNode, new Long(j), taf.getName(), "syserror", 0, "Validation Failure: check Validation Project.");
									System.out.println("executeTAF - tempResult: failure");
									report.addToFullDescription("\nTAF Execution: tempResult NULL");
								}
								
								
									report.setXml(reportDOM.getXMLString());
								

							
						}
						else
							// TODO
							System.out.println("TODO: situazione non gestita!!!!!!!!!!!!!!!!!!!!!!!!!");
						
					}
					
						} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				
					// TODO come facevo in runAction dovrei prendere un elemento, 
					// clonarlo, modificarlo e metterlo nella prerequisites list
					report.addToFullDescription("\n-POST executeTAF call (into the while)");
					
					
					
					sumPrerequisites = sumPrerequisites && report.isPartialResultSuccessfully();

					// Se il prerequisito è andato male ed era obbligatorio... esco
					if (!report.isPartialResultSuccessfully() && t.getPrescription().equals(Constants.MANDATORY))
						i = prerequisites.size();
					
					j++;
				}

				
				i++;
			}
			okPrerequisites = sumPrerequisites;
			
		}
		else {
			
			if (taf.isSkipTurnedON())
				report.addToFullDescription("\nSkip Prerequisite turned ON");
			else
				report.addToFullDescription("\nSkip Prerequisite turned OFF but there is not prerequisites");
			
			okPrerequisites = true;
		}
		
		// Una volta superati i prerequisiti, eseguo il Test (può anche essere un prerequisito questa taf)
		if (okPrerequisites) {
		
			//TestRule predicate = taf.getPredicate();

			RuleManagerImpl testRuleManager = new RuleManagerImpl();
			
			
			// TODO Execution of Predicate
			report = testRuleManager.executeTestRule(taf, session);
			//report.setPartialResultSuccessfully(okPredicate);
			report.addToFullDescription("\n-POST executeTAF call (okPrerequisites)\n");
			
			if (report.getTempResult() != null)
				report.addToFullDescription("\n Test Rule Output: " + report.getTempResult());
			else
				report.addToFullDescription("\nNo output to append. Test Rule RESULT SUCCESSFUL\n");
			
			// TODO MI SERVE SAPERE SE SI TRATTA DI ACTION O DI PREREQUISITE
			// SE TAF = ACTION INSERISCO IN RESULTS > TESTRESULT			
			// SE TAF = PREREQUISITE INSERISCO RESULTS > TESTRESULT > PrerequisiteList
			
			
			
			
			
			
			// TODO Gestione Report
			report.addToFullDescription("\nReport Message: " + taf.getReportFragments().get("pass").getMessage());
			report.addToFullDescription("\nReport Fragment: " + taf.getReportFragments().get("pass").getDescription());	
		
		}
		else {
			report.addToFullDescription("\nThe Execution of Predicate of TAF: " + taf.getName());
		}
		
		report.addToFullDescription("\nEND executeTAF: " + taf.getName());
		report.addToFullDescription("\n----\n");
		
// TODO QUI IL REPORT NON VIENE PERSISISTITO (POSSO RICHIAMARE DA QUESTA CLASSE L'EJB?)
		// FORSE IN QUESTO CASO E' MEGLIO RITORNARLO COME VALORE DI RITORNO
		
		//return okPredicate;
		return report;
	}
	
	
}
