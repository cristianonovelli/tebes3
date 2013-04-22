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
	public TAF buildTAF(Action action) {
		
		TAF result = null;

		try {
			
			String thisPackage = this.getClass().getPackage().getName();
			Class extendedManager = Class.forName(thisPackage.concat(".").concat(action.getTestLanguage().toUpperCase().concat(TESTMANAGER_POSTFIX)));
			Method xMethod = extendedManager.getMethod(BUILDTAF_METHOD, Action.class);
			result = (TAF) xMethod.invoke(extendedManager.newInstance(), action);
						
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
		
		return result;
	}
	
	
	public Report executeTAF(TAF taf, Report report) {
		
		boolean okPrerequisites = false, okPredicate = false;
		
		
		report.addToFullDescription("\n----");
		report.addToFullDescription("\nSTART executeTAF: " + taf.getName());
		
		
		// Qui sto considerando che la TAF sia una Test Assertion, 
		// ma invece bisognerebbe gestire anche il fatto sia una Test Suite o un Test Case
		// A DIRE IL VERO NO! UNA VOLTA CHE E' TAF NON MI INTERESSA PIU'! 
		// DEVO SOLO GESTIRE I CASI IN CUI LA TEST RULE E' VUOTA!
		
		// Prerequisites
		Vector<Action> prerequisites = taf.getTests();
		if ( !taf.isJumpTurnedON() && (prerequisites != null) ) {
		
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
				TAF t = this.buildTAF(a);

				// Recursive execution
				report.addToFullDescription("\n---> Recursive execution");
				
				//boolean singleResult = this.executeTAF(t, report);
				report = this.executeTAF(t, report);
				
				sumPrerequisites = sumPrerequisites && report.isPartialResultSuccessfully();
				
				// Se il prerequisito è andato male ed era obbligatorio... esco
				if (!report.isPartialResultSuccessfully() && t.getPrescription().equals(Constants.MANDATORY))
					i = prerequisites.size();
				
				i++;
			}
			okPrerequisites = sumPrerequisites;
			
		}
		else {
			
			if (taf.isJumpTurnedON())
				report.addToFullDescription("\nJump Prerequisite turned ON");
			else
				report.addToFullDescription("\nJump Prerequisite turned OFF but there is not prerequisites");
			
			okPrerequisites = true;
		}
		
		// Una volta superati i prerequisiti, eseguo il Test
		if (okPrerequisites) {
		
			TestRule predicate = taf.getPredicate();

			RuleManagerImpl testRuleManager = new RuleManagerImpl();
			
			
			// TODO Execution of Predicate
			report = testRuleManager.executeTestRule(predicate, report);
			//report.setPartialResultSuccessfully(okPredicate);
			
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
