package it.enea.xlab.tebes.testaction;

import java.util.Vector;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.model.Action;
import it.enea.xlab.tebes.model.TAF;
import it.enea.xlab.tebes.model.TestPlan;
import it.enea.xlab.tebes.testmanager.TestManagerImpl;


@Stateless
@Interceptors({Profile.class})
public class TestActionManagerImpl implements TestActionManagerRemote {

	
	
	
	// cicla su ogni action e trovo action da eseguire da cui estraggo Test

	// chiamo metodo test manager su Test da eseguire dipendentemente dal tipo

	public TestActionManagerImpl() {

		// TODO Auto-generated constructor stub
	}


	/**
	 * Actions Workflow Engine
	 * TODO supporto di un workflow più articolato, per ora è lineare e si risolve con un semplice for
	 * @return 	true if all actions return true
	 * 			false if one action return false
	 */
	public boolean executeActionWorkflow(TestPlan testPlan) {
		
		Vector<Action> workflow = testPlan.getWorkflow();
		
		boolean result = true;

		System.out.println("Execution of " + workflow.size() + " test actions.");
		for (int i = 0 ; i < workflow.size(); i++) {
			
			result = result && execute((Action) workflow.get(i));
		}
		
		return result;
	}
	
	
	/**
	 * Generic Test Action Execution
	 * checks the lg attribute and call the executor of the test language specified (p.es TAML)
	 * 
	 * @return 	true if type is equal to one of the three permitted values: "TestSuite", "TestCase", "TestAssertion"
	 * 			false otherwise
	 */
	public boolean execute(Action action) {
	
		boolean result = false;
		
		System.out.println("");
		System.out.println("-- Start Execution of Action: " + action.getActionName() + "--");

		try {
			
			TestManagerImpl testManager = new TestManagerImpl();
			
			// TAF Building
			System.out.println("Building TAF for action: " + action.getActionName());
			TAF taf = testManager.buildTAF(action);
			
			
			// TAF Execution
			if (taf != null) {
				System.out.println("Built TAF " + taf.getName() + " successful.");
				System.out.println("Start execution TAF " + taf.getName());
				result = testManager.executeTAF(taf);
				System.out.println("Result: " + result);
			}
			else
				System.out.println("Built TAF Failure.");

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("-- End Execution of Action: " + action.getActionName() + "--");
		
		return result;
	}

	
	
}
