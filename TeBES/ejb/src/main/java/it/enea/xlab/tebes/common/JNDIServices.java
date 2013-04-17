package it.enea.xlab.tebes.common;

import java.util.HashMap;
import java.util.Map;

import it.enea.xlab.tebes.action.ActionManagerRemote;
import it.enea.xlab.tebes.session.SessionManagerRemote;
import it.enea.xlab.tebes.sut.SUTManagerRemote;
import it.enea.xlab.tebes.testplan.TestPlanManagerRemote;
import it.enea.xlab.tebes.users.UserManagerRemote;

import javax.naming.InitialContext;
import javax.naming.NamingException;

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
	private static ActionManagerRemote actionManager = null;
	
	private static String UserManagerServiceName = "TeBES-ear/UserManagerImpl/remote";
	private static String SUTManagerServiceName = "TeBES-ear/SUTManagerImpl/remote";
	private static String SessionManagerServiceName = "TeBES-ear/SessionManagerImpl/remote";
	private static String TestPlanManagerServiceName = "TeBES-ear/TestPlanManagerImpl/remote";
	private static String ActionManagerServiceName = "TeBES-ear/ActionManagerImpl/remote";
	
	private static final Map<String, Object> EJB_CACHE = new HashMap<String, Object>();

	
	
	@SuppressWarnings("unchecked")
	public static <T> T retrieveEJB(String key, String name) {
		T ejbBean = null;

		if (EJB_CACHE.containsKey(key)) {
			ejbBean = (T) EJB_CACHE.get(key);
		} else {
			try {
				InitialContext context = new InitialContext();
				ejbBean = (T) context.lookup(name);
				EJB_CACHE.put(key, ejbBean);
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ejbBean;
	}
	

	public static UserManagerRemote getUserManagerService() throws NamingException {
		
		if (userManager == null) {

				InitialContext ctx = new InitialContext();		
				userManager = (UserManagerRemote) ctx.lookup(UserManagerServiceName);
				
		}
		
		
		return userManager;
	}

	public static SUTManagerRemote getSUTManagerService() throws NamingException {

		if (sutManager == null) {

				InitialContext ctx = new InitialContext();
				sutManager = (SUTManagerRemote) ctx.lookup(SUTManagerServiceName);
		}
		return sutManager;
	}
	

	public static SessionManagerRemote getSessionManagerService() throws NamingException {

		if (sessionManager == null) {

				InitialContext ctx = new InitialContext();
				sessionManager = (SessionManagerRemote) ctx.lookup(SessionManagerServiceName);
		}
		return sessionManager;
	}

	public static TestPlanManagerRemote getTestPlanManagerService() throws NamingException {

		if (testPlanManager == null) {

				InitialContext ctx = new InitialContext();
				testPlanManager = (TestPlanManagerRemote) ctx.lookup(TestPlanManagerServiceName);
		}
		return testPlanManager;
	}

	public static ActionManagerRemote getActionManagerService() throws NamingException {
		
		if (actionManager == null) {

			InitialContext ctx = new InitialContext();
			actionManager = (ActionManagerRemote) ctx.lookup(ActionManagerServiceName);
		}
		return actionManager;
	}
		
	
}
