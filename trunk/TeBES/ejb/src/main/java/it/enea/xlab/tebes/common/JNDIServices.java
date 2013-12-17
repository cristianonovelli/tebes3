package it.enea.xlab.tebes.common;

import it.enea.xlab.tebes.action.ActionManagerRemote;
import it.enea.xlab.tebes.authentication.AuthenticationManager;
import it.enea.xlab.tebes.file.FileManagerRemote;
import it.enea.xlab.tebes.paging.PagingManager;
import it.enea.xlab.tebes.session.SessionManagerRemote;
import it.enea.xlab.tebes.sut.SUTManagerRemote;
import it.enea.xlab.tebes.testplan.TestPlanManagerRemote;
import it.enea.xlab.tebes.users.UserManagerRemote;
import it.enea.xlab.validation.ValidationManagerRemote;
 


import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author fulvio di marco, chiara pezzi, stefano monti
 * @author cristiano novelli
 *
 */
public class JNDIServices {
	
	private static String hostPort = PropertiesUtil.getJndiHost() + ":" + PropertiesUtil.getJndiPort();
	
	private static UserManagerRemote userManager = null;
	private static SUTManagerRemote sutManager = null;
	private static SessionManagerRemote sessionManager = null;
	private static TestPlanManagerRemote testPlanManager = null;
	private static ActionManagerRemote actionManager = null;
	private static ValidationManagerRemote validationManager = null;
	private static AuthenticationManager authenticationManager = null;
	private static PagingManager pagingManager = null;
	private static FileManagerRemote fileManager = null;
	
	private static String UserManagerServiceName = "TeBES-ear/UserManagerImpl/remote";
	private static String SUTManagerServiceName = "TeBES-ear/SUTManagerImpl/remote";
	private static String SessionManagerServiceName = "TeBES-ear/SessionManagerImpl/remote";
	private static String TestPlanManagerServiceName = "TeBES-ear/TestPlanManagerImpl/remote";
	private static String ActionManagerServiceName = "TeBES-ear/ActionManagerImpl/remote";
	private static String AuthenticationManagerServiceName = "TeBES-ear/AuthenticationManagerImpl/remote";
	private static String PagingManagerName = "TeBES-ear/PagingManagerImpl/remote";
	private static String FileManagerServiceName = "TeBES-ear/FileManagerImpl/remote";
	
	// ESTERNO
	private static String ValidationManagerServiceName = "Validation-ear/ValidationManagerImpl/remote";
	
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
	
	private static InitialContext getInitialContext() throws NamingException {
		
		return new InitialContext();
	}

	public static UserManagerRemote getUserManagerService() throws NamingException {
		
		if (userManager == null) {
	
				userManager = (UserManagerRemote) getInitialContext().lookup(UserManagerServiceName);
				
		}
		
		
		return userManager;
	}

	public static SUTManagerRemote getSUTManagerService() throws NamingException {

		if (sutManager == null) {

				sutManager = (SUTManagerRemote) getInitialContext().lookup(SUTManagerServiceName);
		}
		return sutManager;
	}
	

	public static SessionManagerRemote getSessionManagerService() throws NamingException {

		if (sessionManager == null) {

				sessionManager = (SessionManagerRemote) getInitialContext().lookup(SessionManagerServiceName);
		}
		return sessionManager;
	}

	public static TestPlanManagerRemote getTestPlanManagerService() throws NamingException {

		if (testPlanManager == null) {

				testPlanManager = (TestPlanManagerRemote) getInitialContext().lookup(TestPlanManagerServiceName);
		}
		return testPlanManager;
	}

	public static ActionManagerRemote getActionManagerService() throws NamingException {
		
		if (actionManager == null) {

			actionManager = (ActionManagerRemote) getInitialContext().lookup(ActionManagerServiceName);
		}
		return actionManager;
	}
		
	public static ValidationManagerRemote getValidationManagerService() throws NamingException {
		
		if (validationManager == null) {

				try {
					validationManager = (ValidationManagerRemote) getInitialContext().lookup(ValidationManagerServiceName);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}		
		
		return validationManager;
	}

	public static AuthenticationManager getAuthenticationManagerService() throws NamingException {
		
		if (authenticationManager == null) {

			authenticationManager = (AuthenticationManager)getInitialContext().lookup(AuthenticationManagerServiceName);
		}
		return authenticationManager;
	}

	public static PagingManager getPagingManager() {
		if (pagingManager == null) {
			try {
				pagingManager = (PagingManager) getInitialContext().lookup(PagingManagerName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pagingManager;
	}


	public static FileManagerRemote getFileManagerService() throws NamingException {

		if (fileManager == null) {

			fileManager = (FileManagerRemote) getInitialContext().lookup(FileManagerServiceName);
		}
		
		return fileManager;
	}


}
