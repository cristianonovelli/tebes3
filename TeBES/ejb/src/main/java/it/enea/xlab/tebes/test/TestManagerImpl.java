package it.enea.xlab.tebes.test;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestResult;
import it.enea.xlab.tebes.model.TAF;
import it.enea.xlab.tebes.report.ReportDOM;
import it.enea.xlab.tebes.test.rule.RuleManagerImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Node;


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
		
		
		
		// STEPS
		// 1. la TAF ha prerequisiti: ciclo per costruire taf per ogni prerequisito e definisco il resultNode
		// - è la taf principale della action (quando non è prerequisito) e quindi il risultato va in singleresult
		// - è un prerequisito e quindi devo clonare il prerequisite (se non è il template)
		// 2. eseguo la taf corrente
		// 3. setto il risultato
		
		// 1di3 PREREQUISITI
		Vector<Action> prerequisites = taf.getTests();
		TAF tafRicorsiva = null;
		Node resultNode;
		boolean sumPrerequisites = true;
		if ( (prerequisites != null) && !taf.isSkipTurnedON() ) {

			
			int i=0;
			
			// Ciclo sui prerequisites
			while (i<prerequisites.size() && sumPrerequisites) {
				
				// GET Action Prerequisite 
				Action prerequisiteAction = prerequisites.elementAt(i);
				
				report.addToFullDescription("\nPrerequisite: " + prerequisiteAction.getActionName());
				report.addToFullDescription("\nBuilding TAF from: " + prerequisiteAction.getActionName());

				
				// BUILD TAF from Action
				Vector<TAF> tafList = this.buildTAF(prerequisiteAction);
				report.addToFullDescription("\nTAF list size: " + tafList.size());
				
				
				int j=0;	
				// Una action potrebbe generare una o più TAF >>> CICLO per eseguirle tutte
				while ( (j < tafList.size()) && sumPrerequisites ) {

					// Recursive execution
					report.addToFullDescription("\n---> Recursive execution");

					// GET TAF prerequisito
					tafRicorsiva = tafList.get(j);
					report.addToFullDescription("\nTAFPrerequisite with j: " + j + " and name " + tafRicorsiva.getName());
					

					
					// EXECUTE TAF PREREQUISITE
					report = this.executeTAF(tafRicorsiva, session, reportDOM, actionId);
					
					
					report.addToFullDescription("\nPartial Result: " + report.isPartialResultSuccessfully());
					
									// SET prerequisite RESULT
									
									Node actionNode = reportDOM.getTestAction(actionId);
									report.addToFullDescription("\nactionId: " + actionId);
									report.addToFullDescription("\nisPrerequisite > new Node");
									
									Node prerequisitesNode = reportDOM.getPrerequisitesElement(actionNode);		
									
									// "1" sta ad indicare il primo nodo figlio, ovvero dopo il nodo testo del nodo corrente
									Node prerequisiteResultNode = prerequisitesNode.getChildNodes().item(1);
									report.addToFullDescription("\n 0.PrerequisiteResult/@result=EMPTY > " + prerequisiteResultNode.getNodeName());
									
									// Se NON è un nodo template, lo clono
									// Se E' il nodo template, uso quello
									String resultValue = reportDOM.getAttribute("result", prerequisiteResultNode);
									report.addToFullDescription("\nresultValue: " + resultValue);
									
									try {
										report.addToFullDescription("\n" + reportDOM.getXMLString());
									} catch (TransformerFactoryConfigurationError e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} catch (TransformerException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									
									Node selectedPrerequisiteNode;
									if (!resultValue.equals("EMPTY")) {
										
										report.addToFullDescription("\n1.PrerequisiteResult/@result=EMPTY > Clone!");
										Node clonePrerequisiteResultNode = prerequisiteResultNode.cloneNode(true);
										report.addToFullDescription("\n2.PrerequisiteResult/@result=EMPTY > " + clonePrerequisiteResultNode.getNodeName());
							
										clonePrerequisiteResultNode = reportDOM.doc.importNode(clonePrerequisiteResultNode, true); 
										report.addToFullDescription("\n3.PrerequisiteResult/@result=EMPTY taf: " + tafRicorsiva.getName());
										reportDOM.insertPrerequisiteResultNode(clonePrerequisiteResultNode, prerequisiteResultNode, actionNode);
										report.addToFullDescription("\n5.EndPrequisiteResult Clone!");
										
										// il PrerequisiteNode selezionato è quello clonato
										selectedPrerequisiteNode = clonePrerequisiteResultNode;
									}	
									else {
										// il PrerequisiteNode selezionato è quello template
										report.addToFullDescription("\n1.PrerequisiteResult/@result=EMPTY > Template!");
										selectedPrerequisiteNode = prerequisiteResultNode;
									}
				
									
									// Set Prerequisite
									if (report.getTempResult() != null) {
										report.addToFullDescription("\nTAF Execution: " + report.getTempResult().getGlobalResult());
										report.addToFullDescription("\nisPrerequisite: " + tafRicorsiva.isPrerequisite());
										
										reportDOM.setPrerequisiteResult(selectedPrerequisiteNode, new Long(j), tafRicorsiva.getName(), report.getTempResult().getGlobalResult(), report.getTempResult().getLine(), report.getTempResult().getMessage());
										try {
											report.addToFullDescription("\n" + reportDOM.getXMLString());
										} catch (TransformerFactoryConfigurationError e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (TransformerException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}
									else {
										reportDOM.setPrerequisiteResult(selectedPrerequisiteNode, new Long(j), tafRicorsiva.getName(), "syserror", 0, "Validation System Error: check Validation Project from TestManagerImpl.executeTAF method.");
										report.addToFullDescription("\nTAF Execution: tempResult NULL");
									}
									
									
									try {
										report.setXml(reportDOM.getXMLString());
									} catch (TransformerFactoryConfigurationError e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (TransformerException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
					
					
									
									sumPrerequisites = sumPrerequisites && report.isPartialResultSuccessfully();
									report.addToFullDescription("\nsumPrerequisites: " + sumPrerequisites);
									
									// Se il prerequisito è andato male ed era obbligatorio... esco
									if (!report.isPartialResultSuccessfully() && tafRicorsiva.getPrescription().equals(Constants.MANDATORY))
										i = prerequisites.size();
									
									j++;
					
				} // end while TAFList for each prerequisite (j)
				
				i++;
				
			} // end while prerequisites (i)

			
		} // end if prerequisites
		else {
			
			// Nel caso in cui 
			// 1. non ci sono prerequisiti o non si vogliono eseguire e 
			// 2. questo non è un prerequisito
			// allora elimino il nodo prerequisites
			if (!taf.isPrerequisite()) {
				
				Node actionNode = reportDOM.getTestAction(actionId);				
				Node prerequisitesNode = reportDOM.getPrerequisitesElement(actionNode);		
				reportDOM.removePrerequisitesNode(prerequisitesNode);
			}
			
		}

		

		
		
		
		
		
		
		
		
			okPrerequisites = sumPrerequisites;
			
		
		// Una volta superati i prerequisiti, eseguo il Test (può anche essere un prerequisito questa taf)
		if (okPrerequisites) {
		
			//TestRule predicate = taf.getPredicate();

			RuleManagerImpl testRuleManager = new RuleManagerImpl();
			
			report.addToFullDescription("\n- Prerequisite OK... EXE Predicate");
			
			// TODO Execution of Predicate
			report = testRuleManager.executeTestRule(taf, session);
			//report.setPartialResultSuccessfully(okPredicate);
			report.addToFullDescription("\n-POST executeTAF call (okPrerequisites)\n");
			
			if (report.getTempResult() != null) {
				report.addToFullDescription("\nTest Rule Output: " + report.getTempResult().getGlobalResult());
				report.addToFullDescription("\nPartial Result post executeTestRule: " + report.isPartialResultSuccessfully());
			}
			else
				report.addToFullDescription("\nNo output to append. Test Rule RESULT SUCCESSFUL\n");
			
			// TODO MI SERVE SAPERE SE SI TRATTA DI ACTION O DI PREREQUISITE
			// SE TAF = ACTION INSERISCO IN RESULTS > TESTRESULT			
			// SE TAF = PREREQUISITE INSERISCO RESULTS > TESTRESULT > PrerequisiteList
			
			

			
			
			// TODO Gestione Report IF SUCCESS.... ALLORA REPORT FRAGMENT
			//report.addToFullDescription("\nReport Message: " + taf.getReportFragments().get("pass").getMessage());
			//report.addToFullDescription("\nReport Fragment: " + taf.getReportFragments().get("pass").getDescription());	
		
		}
		else {
			report.addToFullDescription("\nPrerequisite FAIL in the Execution of TAF: " + taf.getName());
			
			String message = "";
			if (tafRicorsiva != null) {
				message = tafRicorsiva.getName();
			}
			
			TestResult result = new TestResult("notQualified", 0, "Failure: Prerequisite " + message + " failed!");
			
			
			
			report.setTempResult(result);
			report.setPartialResultSuccessfully(false);

	
			
			
			
		}
		
		report.addToFullDescription("\nEND executeTAF: " + taf.getName());
		report.addToFullDescription("\n----\n");
		
// TODO QUI IL REPORT NON VIENE PERSISISTITO (POSSO RICHIAMARE DA QUESTA CLASSE L'EJB?)
		// FORSE IN QUESTO CASO E' MEGLIO RITORNARLO COME VALORE DI RITORNO
		
		//return okPredicate;
		return report;
	}
	
	
}
