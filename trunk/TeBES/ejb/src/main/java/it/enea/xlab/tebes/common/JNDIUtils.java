/**
 *  <p>Copyright © 2009-2010 Epoca srl</p>
 *
 * <p>This file is part of FatturazioneElettronica. FatturazioneElettronica is free 
 * software: you can redistribute it and/or modify it under the terms of the GNU 
 * General Public License as published by the Free Software Foundation, either 
 * version 3 of the License, or (at your option) any later version. Fatturazione 
 * Elettronica is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You 
 * should have received a copy of the GNU General Public License along with 
 * FatturazioneElettronica. If not, see <http://www.gnu.org/licenses/>.</p>
 * 
 * 
 *//*
package it.fe.core.common.utils;

import it.fe.core.ejb.QuicknoteManager;
import it.fe.core.ejb.authorization.PermissionManager;
import it.fe.core.ejb.generic.CrudManager;
import it.fe.core.ejb.invoice.ClientManager;
import it.fe.core.ejb.invoice.InvoiceManager;
import it.fe.core.ejb.invoice.InvoiceStateManager;
import it.fe.core.ejb.invoice.OriginalDocumentManager;
import it.fe.core.ejb.invoice.SupplierManager;
import it.fe.core.ejb.invoice.TransmissionTypeManager;
import it.fe.core.ejb.mail.MailService;
import it.fe.core.ejb.process.ProcessManager;
import it.fe.core.ejb.process.ProcessServiceFacade;
import it.fe.core.ejb.process.performance.ProcessPerformanceManager;
import it.fe.core.ejb.user.ContextManager;
import it.fe.core.ejb.user.RoleManager;
import it.fe.core.ejb.user.UserManager;

import java.util.Hashtable;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;

*//**
 * @author fulvio di marco, chiara pezzi, stefano monti
 * 
 * <p>Copyright © 2009-2010 Epoca srl</p>
 *
 * <p>This file is part of FatturazioneElettronica. FatturazioneElettronica is free 
 * software: you can redistribute it and/or modify it under the terms of the GNU 
 * General Public License as published by the Free Software Foundation, either 
 * version 3 of the License, or (at your option) any later version. Fatturazione 
 * Elettronica is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You 
 * should have received a copy of the GNU General Public License along with 
 * FatturazioneElettronica. If not, see <http://www.gnu.org/licenses/>.</p>
 *
 *//*
public class JNDIUtils {
	
	
	private static UserManager userManager = null;
	private static RoleManager roleManager = null;
	private static ContextManager contextManager = null;
	private static PermissionManager permissionManager = null;
	
	private static InvoiceManager invoiceManager = null;
	private static InvoiceStateManager invoiceStateManager = null;
	private static ClientManager clientManager = null;
	private static SupplierManager supplierManager = null;
	private static CrudManager crudManager = null;
	private static OriginalDocumentManager originalDocumentManager = null;
	private static TransmissionTypeManager transmissionTypeManager = null;

	private static ProcessServiceFacade processServiceFacade = null;
	private static ProcessManager coMoProcessManager = null;
	private static ProcessManager uniMoReProcessManager = null;
	private static ProcessManager genericProcessManager = null;
	
	private static ProcessPerformanceManager processPerformanceManager = null;
	
	private static MailService mailService = null;
	
	private static QuicknoteManager quicknoteManager = null;
	
	private static String hostPort = FEPropertiesUtil.getJndiHost()+":"+FEPropertiesUtil.getJndiPort();
	
	private static String UserManagerName = "UserManagerBean/remote";
	private static String RoleManagerName = "RoleManagerBean/remote";
	private static String ContextManagerName = "ContextManagerBean/remote";
	private static String PermissionManagerName = "PermissionManagerBean/remote";
	
	//private static String InvoiceManagerName = "InvoiceManagerComuneModenaBean/remote";
	private static String InvoiceManagerName = FEPropertiesUtil.getInvoiceManagerName() + "/remote";
	private static String InvoiceStateManagerName = "InvoiceStateManagerBean/remote";
	private static String SupplierManagerName = "SupplierManagerBean/remote";
	private static String ClientManagerName = "ClientManagerBean/remote";
	private static String CrudManagerName = "CrudManagerBean/remote";
	
	private static String OriginalDocumentManagerName = "OriginalDocumentManagerBean/remote";
	private static String TransmissionTypeManagerName = "TransmissionTypeManagerBean/remote";
	
	private static String ProcessServiceFacadeName = "ProcessServiceFacadeBean/remote";
	
	private static String CoMoProcessManagerName = "ComuneModenaProcessManagerBean/remote";
	private static String UniMoReProcessManagerName = "UnimoreProcessManagerBean/remote";
	private static String GenericProcessManagerName = "GenericProcessManagerBean/remote";
	
	private static String ProcessPerformanceManagerName = "ProcessPerformanceManagerBean/remote";
	
	private static String MailServiceName = "MailServiceBean/remote";
	
	private static String QuicknoteManagerName = "QuicknoteManagerBean/remote";
	
	
	private static Logger logger = Logger.getLogger(JNDIUtils.class.getName());
	
	@SuppressWarnings("unchecked")
	private static InitialContext getInitialContext(String hostPort)
			throws Exception {
		Hashtable props = getInitialContextProperties(hostPort);
		return new InitialContext(props);
	}

	@SuppressWarnings("unchecked")
	private static Hashtable getInitialContextProperties(String hostPort) {
		Hashtable props = new Hashtable();
		props.put("java.naming.factory.initial",
				"org.jnp.interfaces.NamingContextFactory");
		props.put("java.naming.factory.url.pkgs",
				"org.jboss.naming:org.jnp.interfaces");
		props.put("java.naming.provider.url", hostPort);

		return props;
	}

	public static UserManager getUserManager() {
		
		//logger.info("Looking up JNDI for "+UserManagerName);
		
		if (userManager == null) {
			try {
				userManager = (UserManager) getInitialContext(
						hostPort ).lookup(UserManagerName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userManager;
	}
	
	public static RoleManager getRoleManager() {
		//logger.info("Looking up "+RoleManagerName);
		if (roleManager == null) {
			try {
				roleManager = (RoleManager) getInitialContext(
						hostPort ).lookup(RoleManagerName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return roleManager;
	}
	
	public static ContextManager getContextManager() {
		//logger.info("Looking up "+ContextManagerName);
		if (contextManager == null) {
			try {
				contextManager = (ContextManager) getInitialContext(
						hostPort ).lookup(ContextManagerName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return contextManager;
	}
	
	public static InvoiceStateManager getInvoiceStateManager() {
		//logger.info("Looking up "+InvoiceStateManagerName);
		if (invoiceStateManager == null) {
			
			if (FEPropertiesUtil.useMock()) {
				invoiceStateManager = createMockComponent("it.fe.core.ejb.mock.InvoiceStateManagerMock");
			}
			else {
				// looking up JNDI Context
				try {
					invoiceStateManager = (InvoiceStateManager) getInitialContext(
							hostPort ).lookup(InvoiceStateManagerName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
			
		}
		return invoiceStateManager;
	}
	
	public static PermissionManager getPermissionManager() {
		//logger.info("Looking up "+PermissionManagerName);
		if (permissionManager == null) {
			try {
				permissionManager = (PermissionManager) getInitialContext(
						hostPort ).lookup(PermissionManagerName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return permissionManager;
	}
	
	public static ClientManager getClientManager() {
		//logger.info("Looking up "+ClientManagerName);
		if (clientManager == null) {
			try {
				clientManager = (ClientManager) getInitialContext(
						hostPort ).lookup(ClientManagerName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return clientManager;
	}
	
	public static SupplierManager getSupplierManager() {
		//logger.info("Looking up "+SupplierManagerName);
		if (supplierManager == null) {
			try {
				supplierManager = (SupplierManager) getInitialContext(
						hostPort ).lookup(SupplierManagerName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return supplierManager;
	}
	
	public static InvoiceManager getInvoiceManager() {
		//logger.info("Looking up "+InvoiceManagerName);
		if (invoiceManager == null) {
			
			if (FEPropertiesUtil.useMock()) {
				invoiceManager = createMockComponent("it.fe.core.ejb.mock.InvoiceManagerMock");
			}
			else {
				// looking up JNDI Context
				try {
					invoiceManager = (InvoiceManager) getInitialContext(
							hostPort ).lookup(InvoiceManagerName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
			
		}
		return invoiceManager;
	}
	
	public static CrudManager getCrudManager() {
		//logger.info("Looking up "+CrudManagerName);
		if (crudManager == null) {
			try {
				crudManager = (CrudManager) getInitialContext(
						hostPort ).lookup(CrudManagerName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return crudManager;
	}
	
	public static OriginalDocumentManager getOriginalDocumentManager() {
		//logger.info("Looking up "+OriginalDocumentManagerName);
		if (originalDocumentManager == null) {
			try {
				originalDocumentManager = (OriginalDocumentManager) getInitialContext(
						hostPort ).lookup(OriginalDocumentManagerName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return originalDocumentManager;
	}
	
	public static TransmissionTypeManager getTransmissionTypeManager() {
		//logger.info("Looking up "+TransmissionTypeManagerName);
		if (transmissionTypeManager == null) {
			try {
				transmissionTypeManager = (TransmissionTypeManager) getInitialContext(
						hostPort ).lookup(TransmissionTypeManagerName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return transmissionTypeManager;
	}
	
	public static ProcessManager getProcessManager() {
		
		//String deployedProcessName = FEPropertiesUtil.getCurrentlyDeployedProcessName();
		String deployedProcessName = FEPropertiesUtil.getActiveProcessName();
		
		if (deployedProcessName != null) {
			
			if (deployedProcessName.equals("CoMo")) {
				//logger.info("Looking up "+CoMoProcessManagerName);
				if (coMoProcessManager == null) {
					
					if (FEPropertiesUtil.isTest()) {
						coMoProcessManager = createMockComponent("it.fe.core.ejb.process.como.CoMoProcessManagerBean");
					}
					else {
						// looking up JNDI Context
						try {
							coMoProcessManager = (ProcessManager) getInitialContext(
									hostPort ).lookup(CoMoProcessManagerName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
				return coMoProcessManager;
			}
			else if (deployedProcessName.equals("UniMoRe")) {
				//logger.info("Looking up "+UniMoReProcessManagerName);
				if (uniMoReProcessManager == null) {
					
					if (FEPropertiesUtil.isTest()) {
						uniMoReProcessManager = createMockComponent("it.fe.core.ejb.process.unimore.UniMoreProcessManagerBean");
					}
					else {
						// looking up JNDI Context
						try {
							uniMoReProcessManager = (ProcessManager) getInitialContext(
									hostPort ).lookup(UniMoReProcessManagerName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
				return uniMoReProcessManager;
			}
			else if (deployedProcessName.equals("Generic")) {
				//logger.info("Looking up "+UniMoReProcessManagerName);
				if (genericProcessManager == null) {
					
					if (FEPropertiesUtil.isTest()) {
						genericProcessManager = createMockComponent("it.fe.core.ejb.process.generic.GenericProcessManagerBean");
					}
					else {
						// looking up JNDI Context
						try {
							genericProcessManager = (ProcessManager) getInitialContext(
									hostPort ).lookup(GenericProcessManagerName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
				return genericProcessManager;
			}
			else {
				return null;
			}
			
		}
		else {
			return null;
		}
		
	}
	
	public static ProcessServiceFacade getProcessServiceFacade() {
		//logger.info("Looking up "+ProcessServiceFacadeName);
		if (processServiceFacade == null) {
			
			if (FEPropertiesUtil.isTest()) {
				processServiceFacade = createMockComponent("it.fe.core.ejb.process.ProcessServiceFacadeBean");
			}
			else {
				// looking up JNDI Context
				try {
					processServiceFacade = (ProcessServiceFacade) getInitialContext(
							hostPort ).lookup(ProcessServiceFacadeName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		return processServiceFacade;
	}
	
	public static ProcessPerformanceManager getProcessPerformanceManager() {
		//logger.info("Looking up "+ProcessPerformanceManagerName);
		if (processPerformanceManager == null) {
			
			if (FEPropertiesUtil.isTest()) {
				processPerformanceManager = createMockComponent("it.fe.core.ejb.process.performance.ProcessPerformanceManagerBean");
			}
			else {
				// looking up JNDI Context
				try {
					processPerformanceManager = (ProcessPerformanceManager) getInitialContext(
							hostPort ).lookup(ProcessPerformanceManagerName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		return processPerformanceManager;
	}
	
	public static QuicknoteManager getQuicknoteManager() {
		if (quicknoteManager == null) {
			
			if (FEPropertiesUtil.isTest()) {
				quicknoteManager = createMockComponent("it.fe.core.ejb.QuicknoteManagerBean");
			}
			else {
				// looking up JNDI Context
				try {
					quicknoteManager = (QuicknoteManager) getInitialContext(
							hostPort ).lookup(QuicknoteManagerName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		return quicknoteManager;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T createMockComponent(String className) {
		logger.info("Creating mock component for: "+className);
		try {
			return (T) Class.forName(className).newInstance();
		} catch (Exception e) {
			logger.error("Failed to instantiate mock component '"+className+"'");
			return null;
		}
	}
	
	public static MailService getMailService() {
		if (mailService == null) {
			
			if (FEPropertiesUtil.isTest()) {
				mailService = createMockComponent("it.fe.core.ejb.mail.MailServiceBean");
			}
			else {
				// looking up JNDI Context
				try {
					mailService = (MailService) getInitialContext(
							hostPort ).lookup(MailServiceName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		return mailService;
	}
	
}

*/