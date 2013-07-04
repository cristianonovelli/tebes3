/*package it.enea.xlab.tebes.controllers.session;


import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.validation.ValidationManagerRemote;

import java.rmi.NotBoundException;

import javax.naming.NamingException;

import validator.ErrorMessage;

public class ValidationController extends WebController {

	public static final String CONTROLLER_NAME = "ValidationController";
	
	private ValidationManagerRemote validationManagerService;
	
	
	public ValidationController() {
	
	}

	public void initContext() throws NotBoundException, NamingException {
		
		// GET SERVICE
		System.out.println("initContext1");
		validationManagerService = JNDIServices.getValidationManagerService(); 		
		System.out.println("initContext2");
		System.out.println("validationManagerService:" + validationManagerService.getClass().toString());
	}
	
	
	public ErrorMessage[] validation(String xmlRelPathFileName, String xsdURL) {
		
		System.out.println("qwqw0");
		return validationManagerService.validation(xmlRelPathFileName, xsdURL);
	}

	public Long nothing() {
		
		
		System.out.println("qwqw -1c");
		
		return validationManagerService.nothing();
	}

}


*/