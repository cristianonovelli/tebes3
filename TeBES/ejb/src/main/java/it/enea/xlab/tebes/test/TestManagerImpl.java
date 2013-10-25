package it.enea.xlab.tebes.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.model.TAF;
import it.enea.xlab.tebes.model.TestRule;
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
			
			
			// prendo il predicate e se � di tipo xpath e contiene una validazione schema
			// lo modifico in modo tale che esprima quello
			// questo � il punto giusto per farlo, perch� non � strettamente relativo a TAML
			
			
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
			
			// Se l'espressione XPath � una richiesta di validazione
			if (testRule.getLanguage().startsWith("count(//") && testRule.getLanguage().endsWith(") ge 1")) {
				
			}
		}
		
		return testRule;
	}*/


	public Report executeTAF(TAF taf, Session session) {
		
		Report report = session.getReport();
		
		boolean okPrerequisites = false;
		
		
		report.addToFullDescription("\n----");
		report.addToFullDescription("\nSTART executeTAF: " + taf.getName());
		
		
		// Prerequisites
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
					report = this.executeTAF(t, session);
	
	
					// TODO come facevo in runAction dovrei prendere un elemento, 
					// clonarlo, modificarlo e metterlo nella prerequisites list
					report.addToFullDescription("\n-POST executeTAF call (into the while)");
					
					
					
					sumPrerequisites = sumPrerequisites && report.isPartialResultSuccessfully();

					// Se il prerequisito � andato male ed era obbligatorio... esco
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
		
		// Una volta superati i prerequisiti, eseguo il Test (pu� anche essere un prerequisito questa taf)
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
