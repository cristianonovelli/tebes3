package it.enea.xlab.tebes.test.rule;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.common.Profile;

import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.model.TestRule;
import it.enea.xlab.tebes.users.UserManagerRemote;
import it.enea.xlab.tebes.validation.ValidationManagerRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;

import validator.ErrorMessage;



@Stateless
@Interceptors({Profile.class})
public class RuleManagerImpl implements RuleManagerRemote {
	
	//@EJB
	//private ValidationManagerRemote validationManager; 
	 
	public Report executeTestRule(TestRule testRule, Report report) { 
		
		//boolean result = false;
		
		// TODO
		// recupero il file oggetto dal message store della sessione
		// e glielo passo come parametro
		// perchè l'accesso al message store va fatto prima 
		
		String xmlString = "TeBES_Artifacts/users/1/docs/ubl-invoice.xml";
		
		report.addToFullDescription("\n- Prerequisite OK... EXE Predicate");
		report.addToFullDescription("\n- Language: " + testRule.getLanguage());
		report.addToFullDescription("\n- Value: " + testRule.getValue());
		
		
		
		
		if (testRule.getLanguage().equals(Constants.XPATH)) {
			
			
			report.addToFullDescription("\nTESTRULE: " + testRule.getValue());
			
			
			
			// TODO
			// se l'oggetto dell'espressione XPath è una validazione XML Schema
			// recupero l'XSD
			
			
			report.setPartialResultSuccessfully(xPathValidation(xmlString, testRule.getValue()));
			
		}
			
			
		if (testRule.getLanguage().equals(Constants.SCHEMATRON))
			report.setPartialResultSuccessfully(schematronValidation(xmlString, testRule.getValue()));
			
		if (testRule.getLanguage().equals(Constants.XMLSCHEMA)) 
			report.setPartialResultSuccessfully(xmlSchemaValidation(xmlString, testRule.getValue()));
		

		
		report.addToFullDescription("\nTODO: Schematron or XPath Execution");
		
		//return result;
		return report;
	}
	

	

	
	public boolean schematronValidation(String xmlString, String xmlSchematron) {
		// TODO Auto-generated method stub
		return true;
	}


	public boolean xPathValidation(String xmlString, String xpath) {
		
		return true;
	}

	public boolean xmlSchemaValidation(String xmlString, String xsdString) {

		

		System.out.println("xmlSchemaValidation A:" + xmlString);
		System.out.println("xmlSchemaValidation B:" + xsdString);
		

		/*String xmlRelPathFileName = "TeBES_Artifacts/users/1/docs/ubl-invoice.xml";
		String xsdURL = "http://winter.bologna.enea.it/peppol_schema_rep/xsd/maindoc/UBL-Invoice-2.0.xsd";
		
		ErrorMessage emList[] = null;

		try {
			validationManager = JNDIServices.getValidationManagerService();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		emList = validationManager.validation(xmlRelPathFileName, xsdURL);
		
		
		
		int i=0;
		System.out.println("PRE While");
		while (i<emList.length){
			
			System.out.println("RIGA " + i + ": " + emList[i].getErrorType());
			System.out.println("RIGA " + i + ": " + emList[i].getLineNumber());
			System.out.println("RIGA " + i + ": " + emList[i].getDescription());
			
			
			i++;
		}
		System.out.println("POST While");*/
		
		
		
		return true;
	}
}

