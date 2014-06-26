package it.enea.xlab.tebes.test.rule;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.entity.FileStore;
import it.enea.xlab.tebes.entity.Input;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestResult;
import it.enea.xlab.tebes.file.FileManagerRemote;
import it.enea.xlab.tebes.model.TAF;
import it.enea.xlab.tebes.model.TestRule;
import it.enea.xlab.validation.ValidationManagerRemote;
import it.enea.xlab.validation.XErrorMessage;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;

 


@Stateless
@Interceptors({Profile.class})
public class RuleManagerImpl implements RuleManagerRemote {
	
	@EJB
	private ValidationManagerRemote validationManager; 

	@EJB
	private FileManagerRemote fileManager; 
	
	
	
	/**
	 * Execute Test Rule
	 */
	public Report executeTestRule(TAF taf, Session session) { 
		
		
		Report report = session.getReport();
		
		TestRule testRule = taf.getPredicate();

		
		
		report.addToFullDescription("\n- Language: " + testRule.getLanguage());
		report.addToFullDescription("\n- Value: " + testRule.getValue());

		report.addToFullDescription("\n-executeTestRule: taf.getName(): " + taf.getName());
		report.addToFullDescription("\n-executeTestRule: taf.getInputs().size(): " + taf.getInputs().size());
		report.addToFullDescription("\n-executeTestRule: testRule.getLanguage(): " + testRule.getLanguage());
		report.addToFullDescription("\n-executeTestRule: testRule.getValue(): " + testRule.getValue());
		
		
		try {
			
			fileManager = JNDIServices.getFileManagerService();
			
			Input input = taf.getInputs().get(0);
			
			FileStore file = fileManager.readFilebyIdRef(input.getFileIdRef());
			report.addToFullDescription("\n-executeTestRule: file: " + file.getFileRefId());
			report.addToFullDescription("\n-executeTestRule: file: " + file.getFileName());
			report.addToFullDescription("\n-executeTestRule: file: " + file.getType());
			
			String userDocsAbsDir = PropertiesUtil.getUserDocsDirPath(session.getUser().getId());
	
			String xmlString = userDocsAbsDir.concat(file.getFileName());
			//String xmlString = "TeBES_Artifacts/users/1/docs/ubl-invoice.xml";
			report.addToFullDescription("executeTestRule: file: " + xmlString);
			
			
			
			// XML Schema validation
			if (testRule.getLanguage().equals(Constants.XMLSCHEMA)) { 
				
				report.addToFullDescription("\n-START validation XMLSCHEMA");

						
				String schema = testRule.getValue();
				
				// TODO bisogna passargli non il path assoluto ma l'URL della cache di TeBES
				/*if ( !XURL.isURLExistent(schema) ) {
					
					schema = PropertiesUtil.getCacheDirPath().concat(XLabUtilities.getFileNameFromAbsFileName(schema));			
				}*/
				
				String fileRelPath = userDocsAbsDir.concat(file.getFileName());
				
				report.addToFullDescription("\n-executeTestRule: xml: " + fileRelPath);
				report.addToFullDescription("\n-executeTestRule: xsd: " + testRule.getValue());
				
				report = xmlSchemaValidation( fileRelPath, schema, report );
				
				report.addToFullDescription("\n-END XMLSCHEMA Validation");
			}			
				
			
			
			if (testRule.getLanguage().equals(Constants.SCHEMATRON)) {
				
				report.addToFullDescription("\n-START SCHEMATRON Validation");
				
				String schematron = testRule.getValue();
				
				// TODO bisogna passargli non il path assoluto ma l'URL della cache di TeBES
				/*if ( !XURL.isURLExistent(schematron) ) {
					
					schematron = PropertiesUtil.getCacheDirPath().concat(XLabUtilities.getFileNameFromAbsFileName(schematron));			
				}*/
					
				
				report = schematronValidation(xmlString, schematron, report);
				
				report.addToFullDescription("\n-END SCHEMATRON Validation");
			}

			

	
			if (testRule.getLanguage().equals(Constants.XPATH)) {
				
				report.addToFullDescription("\n-START XPATH Validation");	 
				
				report.addToFullDescription("\n-TESTRULE: " + testRule.getValue());
	
				report = xPathValidation(xmlString, testRule.getValue(), report);
				
				report.addToFullDescription("\n-END XPATH Validation");
			}
		
			
		} catch (NamingException e) {
			
			System.out.println("executeTestRule: NamingException");
			
			// TODO Adjust Report
			e.printStackTrace();
		}		
		
		
		
		return report;
	}
	

	public Report xmlSchemaValidation(String xmlString, String xsdString, Report report) {

		
		
		// TODO
		// verifica sia uno schematron valido

		report.addToFullDescription("XML Schema Validation - XML: " + xmlString);
		report.addToFullDescription("XML Schema Validation - XSD: " + xsdString);
		

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
					report.addToFullDescription("Add TestResult " + i + ": "  
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
					
					report.addToFullDescription("\nAdd TestResult " + i + ": "  
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

