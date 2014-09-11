package it.enea.xlab.tebes.test.rule;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.TestResult;
import it.enea.xlab.validation.ValidationManagerRemote;
import it.enea.xlab.validation.XErrorMessage;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;

 


@Stateless
@Interceptors({Profile.class})
public class DocumentManager {
	
	@EJB
	private ValidationManagerRemote validationManager; 

	public DocumentManager() {
		
	}
	

	public Report xmlSchemaValidation(String xmlString, String xsdString, Report report) {


		report.addLineToFullDescription("XML Schema Validation - XML: " + xmlString);
		report.addLineToFullDescription("XML Schema Validation - XSD: " + xsdString);
		

		//String xmlRelPathFileName = "TeBES_Artifacts/users/0/docs/ubl-invoice.xml";
		//String xsdURL = "http://winter.bologna.enea.it/peppol_schema_rep/xsd/maindoc/UBL-Invoice-2.0.xsd";
		
		XErrorMessage emList[] = null;

		try {
			validationManager = JNDIServices.getValidationManagerService();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TestResult result;
		
		try {
			emList = validationManager.validation(xmlString, xsdString);
			
			ArrayList<TestResult> testResultList = new ArrayList<TestResult>(); 
			
			// TODO considero solo il primo record (dalle prove... solo il primo veniva effettivamente usato)
			
			if (emList.length > 0) {
				
				String errorType;
				if (emList[0].getErrorType().equals("ERROR"))
					errorType = TestResult.FAILURE_RESULT;
				else
					errorType = TestResult.ERROR_RESULT;
				
				result = new TestResult(errorType, emList[0].getLineNumber(), emList[0].getDescription());
				report.setPartialResultSuccessfully(false);
				
				// NEW
				
				for (int i=0; i<emList.length; i++) {
					
					testResultList.add(new TestResult(errorType, emList[i].getLineNumber(), emList[i].getDescription()));
					report.addLineToFullDescription("Add TestResult " + i + ": "  
					+ errorType + " " + emList[i].getLineNumber() + " " + emList[i].getDescription() );
				}
				report.setTempResultList(testResultList);
				
			}
			else {
				result = new TestResult(TestResult.PASS_RESULT, 0, "Success: Empty Error Message List");
				report.setPartialResultSuccessfully(true);
				
				testResultList.add(result);
				report.setTempResultList(testResultList);
			}
		
			report.setTempResult(result);

		} catch (Exception e) {

			e.printStackTrace();
			
			result = new TestResult(TestResult.ERROR_RESULT, 0, e.getMessage());		
			report.setTempResult(result);
			report.setPartialResultSuccessfully(false);
			return report;
		}
		

		return report;
	}	

	
	public Report schematronValidation(String xmlString, String xmlSchematron, Report report) {

		
		// TODO
		// verifica sia uno schematron valido
		
		XErrorMessage emList[] = null;

		try {

			validationManager = JNDIServices.getValidationManagerService();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestResult result;
		try {

			emList = validationManager.validation(xmlString, xmlSchematron);

			ArrayList<TestResult> testResultList = new ArrayList<TestResult>(); 
			
			// TODO considero solo il primo record (dalle prove... solo il primo veniva effettivamente usato)
			
			if (emList.length > 0) {
				
				String errorType;
				if (emList[0].getErrorType().equals("ERROR"))
					errorType = TestResult.FAILURE_RESULT;
				else
					errorType = TestResult.ERROR_RESULT;
				
				result = new TestResult(emList[0].getErrorType(), emList[0].getLineNumber(), emList[0].getDescription());
				report.setPartialResultSuccessfully(false);
				
				// NEW
				
				for (int i=0; i<emList.length; i++) {
					
					testResultList.add(new TestResult(errorType, emList[i].getLineNumber(), emList[i].getDescription()));
					
					report.addLineToFullDescription("Add TestResult " + i + ": "  
							+ errorType + " " + emList[i].getLineNumber() + " " + emList[i].getDescription() );
				}
				report.setTempResultList(testResultList);
			}
			else {
				
				/*String errorType;
				if (emList[0].getErrorType().equals("ERROR"))
					errorType = TestResult.FAILURE_RESULT;
				else
					errorType = TestResult.ERROR_RESULT;*/
				
				result = new TestResult(TestResult.PASS_RESULT, 0, "Success: Empty Error Message List");
				report.setPartialResultSuccessfully(true);
				
				testResultList.add(result);
				report.setTempResultList(testResultList);
			}
			
			report.setTempResult(result);

		} catch (Exception e) {
			
			result = new TestResult(TestResult.ERROR_RESULT, 0, e.getMessage());		
			report.setTempResult(result);
			
			e.printStackTrace();
			
			
			report.setPartialResultSuccessfully(false);
			return report;
		}
		
		
		return report;
	}


	public Report xPathValidation(String xmlString, String xpath, Report report) {
		
		report.setPartialResultSuccessfully(true);
		return report;
	}
	
	
}

