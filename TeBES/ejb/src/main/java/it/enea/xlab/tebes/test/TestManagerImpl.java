package it.enea.xlab.tebes.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.model.TAF;
import it.enea.xlab.tebes.model.TestRule;
import it.enea.xlab.tebes.rule.RuleManagerImpl;


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
	
	
	public boolean executeTAF(TAF taf) {
		
		boolean okPrerequisites = false, okPredicate = false;
		
		System.out.println("----");
		System.out.println("START executeTAF: " + taf.getName());
		
		
		// Qui sto considerando che la TAF sia una Test Assertion, 
		// ma invece bisognerebbe gestire anche il fatto sia una Test Suite o un Test Case
		// A DIRE IL VERO NO! UNA VOLTA CHE E' TAF NON MI INTERESSA PIU'! 
		// DEVO SOLO GESTIRE I CASI IN CUI LA TEST RULE E' VUOTA!
		
		// Prerequisites
		Vector<Action> prerequisites = taf.getTests();
		if ( !taf.isJumpTurnedON() && (prerequisites != null) ) {
		
			System.out.println("There are Prerequisites");
			
			// Ciclo sui prerequisites
			int i=0;
			boolean sumPrerequisites = true;
			while (i<prerequisites.size()) {
				
				// Prerequisite Action
				Action a = prerequisites.elementAt(i);
				
				System.out.println("Prerequisite: " + a.getActionName());
				System.out.println("Building TAF from: " + a.getActionName());
				
				// Prerequisite TAF from Action
				TAF t = this.buildTAF(a);

				// Recursive execution
				System.out.println("---> Recursive execution");
				boolean singleResult = this.executeTAF(t);
				sumPrerequisites = sumPrerequisites && singleResult;
				
				// Se il prerequisito è andato male ed era obbligatorio... esco
				if (!singleResult && t.getPrescription().equals(Constants.MANDATORY))
					i = prerequisites.size();
				
				i++;
			}
			okPrerequisites = sumPrerequisites;
			
		}
		else {
			
			if (taf.isJumpTurnedON())
				System.out.println("Jump Prerequisite turned ON");
			else
				System.out.println("Jump Prerequisite turned OFF but there is not prerequisites");
			
			okPrerequisites = true;
		}
		
		// Una volta superati i prerequisiti, eseguo il Test
		if (okPrerequisites) {
		
			TestRule predicate = taf.getPredicate();

			RuleManagerImpl testRuleManager = new RuleManagerImpl();
			
			
			// TODO Execution of Predicate
			okPredicate = testRuleManager.executeTestRule(predicate);
			
			// TODO Gestione Report
			System.out.println("Report Message: " + taf.getReportFragments().get("pass").getMessage());
			System.out.println("Report Fragment: " + taf.getReportFragments().get("pass").getDescription());	
		
		}
		else {
			System.out.println("The Execution of Predicate of TAF: " + taf.getName());
		}
		
		System.out.println("END executeTAF: " + taf.getName());
		System.out.println("----");
		
		return okPredicate;
	}
	
	
}
