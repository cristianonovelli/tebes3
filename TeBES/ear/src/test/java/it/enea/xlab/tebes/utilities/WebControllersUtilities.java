package it.enea.xlab.tebes.utilities;

import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.controllers.session.SessionManagerController;
import it.enea.xlab.tebes.controllers.sut.SUTManagerController;
import it.enea.xlab.tebes.controllers.testplan.TestPlanManagerController;
import it.enea.xlab.tebes.controllers.users.UserAdminController;
import it.enea.xlab.tebes.controllers.users.UserProfileController;

import java.rmi.NotBoundException;

public class WebControllersUtilities {

public static WebController getManager(String nome)  {	
		
		WebController controller = null;
		
		
		if (nome.equals(UserAdminController.CONTROLLER_NAME))
			controller = new UserAdminController();	

		if (nome.equals(UserProfileController.CONTROLLER_NAME))
			controller = new UserProfileController();	
		
		if (nome.equals(SUTManagerController.CONTROLLER_NAME))
			controller = new SUTManagerController();	

		if (nome.equals(TestPlanManagerController.CONTROLLER_NAME))
			controller = new TestPlanManagerController();	

		if (nome.equals(SessionManagerController.CONTROLLER_NAME))
			controller = new SessionManagerController();
		
		
		int guard = 0;
		
		// To avoid Remote Not Bound exception, we retry to lockup for 5 attempts
		// waiting 2 seconds between an attempt and another
		while (guard < 5) {
			try {
				
				controller.initContext();
				
				guard = 5;
			} catch (Exception e) {
				
				if (e instanceof NotBoundException) {
				
				
				guard++;
				System.out.println("REMOTE NOT BOUND for TestPlanManagerController. Try " + guard + " of 5");				
				
				//wait(2000);
				Thread.currentThread();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				else
					break;
			}
		}		
		
		
		return controller;
	}
}
