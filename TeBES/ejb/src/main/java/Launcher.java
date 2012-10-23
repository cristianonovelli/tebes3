/*



import it.enea.xlab.tebes.common.Properties;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.model.TestPlan;
import it.enea.xlab.tebes.testaction.TestActionManagerImpl;
import it.enea.xlab.tebes.testplan.TestPlanManagerImpl;
import it.enea.xlab.tebes.users.UserManagerImpl;

import org.junit.Test;


public class Launcher {

	*//**
	 * @param args
	 *//*
	@Test
	public static void main(String[] args) {
        
		// I COMMENTI IN QUESTO METODO MAIN E LE CHIAMATE DESCRIVONO 
		// COME LA CORE PLATFORM DEVE ESSERE INTEGRATA CON IL RESTO DEL SISTEMA
		
		System.out.println("///////////////////////////////////");
		System.out.println("/////// TeBES Core Platform ///////");
		System.out.println("///////////////////////////////////");
		System.out.println();
		
		System.out.println("-----------------------------------");
		System.out.println("---------------TeBES---------------");
		System.out.println("TeBES URL HOME: " + Properties.TEBES_URL_HOME);
		System.out.println("TeBES LOCAL HOME: " + Properties.TeBES_LOCAL_HOME);
		System.out.println("-----------------------------------");
		System.out.println();
		
		
		// 1. utente accede alla piattaforma, 
		// ha il suo spazio privato
		// e ha configurato e scelto il SUT da testare
		System.out.println("-----------------------------------");
		System.out.println("---------------USER----------------");
		System.out.println("User: " + Properties.TEMP_USER);

		
		// Preparazione Entity User
		SUT currentSUT = new SUT("xmldocument", "XML document uploaded by web interface");
		Role currentGroup = new Role("administrators", "Administrators Group of TeBES Platform");
		User currentUser = new User(Properties.TEMP_USER, "Cristiano", "Novelli", "cristiano.novelli@enea.it");
		
		UserManagerImpl userManager = new UserManagerImpl();
		//userManager.setUserGroup(currentUser, currentGroup);
		//userManager.addUserSUT(currentUser, currentSUT);

		
		// old
		//currentUser.addSut(currentSUT);
		//currentUser.setUserGroup(currentGroup);
		
		
		// Persistenza User Entity tramite JNDI 
		try {
			
			InitialContext ctx = new InitialContext();
			UserManagerRemote bean = (UserManagerRemote) ctx.lookup("TeBES-ear-1.0-SNAPSHOT/UserManagerImpl/remote");
			Long id = bean.createUser(currentUser);
			System.out.println("Persistence Test - UserId: " + id);
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		// 2. l'utente ha selezionato/configurato un piano di Test	
		// il Test Plan viene caricato nella core platform
		System.out.println("Selected Test Plan: " + Properties.TEMP_TESTPLAN_FILENAME);
		System.out.println("-----------------------------------");
		System.out.println();
		
		// Import Test Plan
		System.out.println("-----------------------------------");
		System.out.println("------------TEST PLAN--------------");
		TestPlanManagerImpl tpManager = new TestPlanManagerImpl(); 
		TestPlan testPlan = tpManager.importTestPlan(Properties.TEMP_TESTPLAN_FILENAME, Properties.TEMP_USER);
		System.out.println("-----------------------------------");
		System.out.println();
		
		
		// Execution
		if ( testPlan != null ) {
		
			// 3. l'utente riceve informazioni sul piano di test che andrà eseguito
			System.out.println("-----------------------------------");
			System.out.println("--------INFORMATION TO TEST--------");
			System.out.println("To perform the Test Plan " + testPlan.getTestPlanId());
			System.out.println("the User " + testPlan.getUserId());
			System.out.println("has to configure his SUT: " + testPlan.getUserSUT() + ".");
			System.out.println("following these instructions:");
			System.out.println("TODO: 1, 2, 3, A, B, C");
			System.out.println("-----------------------------------");
			System.out.println();
			
			// 4. l'utente lancia l'esecuzione del piano di Test e quindi del workflow di test action
			System.out.println("-----------------------------------");
			System.out.println("-------------EXECUTION-------------");
			System.out.println("User launched the test plan Execution."); 
			TestActionManagerImpl actionManager = new TestActionManagerImpl(); 
			
			boolean actionWorkflowExecutionResult = actionManager.executeActionWorkflow(testPlan);
			System.out.println();
			
			// 5. utente può interagire durante esecuzione (monitoraggio, interventi)
			System.out.println("TODO: Interazione Utente");
			System.out.println();
			if ( actionWorkflowExecutionResult )
				System.out.println(">>>> Test Plan Execution Successful! <<<<");
			else
				System.out.println(">>>> Test Plan Execution Failure! <<<<");
			System.out.println();
			
			System.out.println("-----------------------------------");
			System.out.println();
			
			// 6. utente preleva report finale
			System.out.println("-----------------------------------");
			System.out.println("--------------REPORT---------------");
			System.out.println("TODO: REPORT");
			System.out.println("-----------------------------------");
			System.out.println();
		}
		else {
			System.out.println();
			System.out.println("Test Plan import process detects ERROR...");
			System.out.println("Maybe UserId does not match!");
			System.out.println();
		}
		
		System.out.println("///////////////////////////////////");
		System.out.println("///////////////////////////////////");
	}

	
}


*/