package it.enea.xlab.tebes.common;

import it.enea.xlab.tebes.session.SessionManagerRemote;
import it.enea.xlab.tebes.sut.SUTManagerRemote;
import it.enea.xlab.tebes.testplan.TestPlanManagerRemote;
import it.enea.xlab.tebes.users.UserManagerRemote;

import javax.naming.InitialContext;

/**
 * @author fulvio di marco, chiara pezzi, stefano monti
 * @author cristiano novelli
 *
 */
public class JNDIServices {
	
	
	private static UserManagerRemote userManager = null;
	private static SUTManagerRemote sutManager = null;
	private static SessionManagerRemote sessionManager = null;
	private static TestPlanManagerRemote testPlanManager = null;
	
	private static String UserManagerServiceName = "TeBES-ear/UserManagerImpl/remote";
	private static String SUTManagerServiceName = "TeBES-ear/SUTManagerImpl/remote";
	private static String SessionManagerServiceName = "TeBES-ear/SessionManagerImpl/remote";
	private static String TestPlanManagerServiceName = "TeBES-ear/TestPlanManagerImpl/remote";
	

	public static UserManagerRemote getUserManagerService() {
		
		if (userManager == null) {
			try {
				InitialContext ctx = new InitialContext();
				
				userManager = (UserManagerRemote) ctx.lookup(UserManagerServiceName);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return userManager;
	}

	public static SUTManagerRemote getSUTManagerService() {

		if (sutManager == null) {
			try {
				InitialContext ctx = new InitialContext();
				sutManager = (SUTManagerRemote) ctx.lookup(SUTManagerServiceName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sutManager;
	}
	

	public static SessionManagerRemote getSessionManagerService() {

		if (sessionManager == null) {
			try {
				InitialContext ctx = new InitialContext();
				sessionManager = (SessionManagerRemote) ctx.lookup(SessionManagerServiceName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionManager;
	}

	public static TestPlanManagerRemote getTestPlanManagerService() {

		if (testPlanManager == null) {
			try {
				InitialContext ctx = new InitialContext();
				testPlanManager = (TestPlanManagerRemote) ctx.lookup(TestPlanManagerServiceName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return testPlanManager;
	}
		
	
}
