package it.enea.xlab.tebes.test.rule;

import java.io.File;
import java.io.IOException;

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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;

import validator.ErrorMessage;

 


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
		
		System.out.println("<br> executeTestRule: START");
		
		Report report = session.getReport();
		
		TestRule testRule = taf.getPredicate();

		
		report.addToFullDescription("<br>- Prerequisite OK... EXE Predicate");
		report.addToFullDescription("<br>- Language: " + testRule.getLanguage());
		report.addToFullDescription("<br>- Value: " + testRule.getValue());

		report.addToFullDescription("<br>-executeTestRule: taf.getName(): " + taf.getName());
		report.addToFullDescription("<br>-executeTestRule: taf.getInputs().size(): " + taf.getInputs().size());
		report.addToFullDescription("<br>-executeTestRule: testRule.getLanguage(): " + testRule.getLanguage());
		report.addToFullDescription("<br>-executeTestRule: testRule.getValue(): " + testRule.getValue());
		
		
		try {
			fileManager = JNDIServices.getFileManagerService();
			
			Input input = taf.getInputs().get(0);
			
			FileStore file = fileManager.readFilebyIdRef(input.getFileIdRef());
			report.addToFullDescription("<br>-executeTestRule: file: " + file.getFileRefId());
			report.addToFullDescription("<br>-executeTestRule: file: " + file.getFileName());
			report.addToFullDescription("<br>-executeTestRule: file: " + file.getType());
			
			String userDocsAbsDir = PropertiesUtil.getUserDocsDirPath(session.getUser().getId());
	
			String xmlString = userDocsAbsDir.concat(file.getFileName());
			//String xmlString = "TeBES_Artifacts/users/1/docs/ubl-invoice.xml";
			report.addToFullDescription("executeTestRule: file: " + xmlString);
			
			
			
			// XML Schema validation
			if (testRule.getLanguage().equals(Constants.XMLSCHEMA)) { 
				
				report.addToFullDescription("<br>-START validation XMLSCHEMA");

						
				//String fileRelPath = file.getFileName();
				//String fileRelPath = xmlString;
				
				String fileRelPath = userDocsAbsDir.concat(file.getFileName());
				
				report.addToFullDescription("<br>-executeTestRule: xml: " + fileRelPath);
				report.addToFullDescription("<br>-executeTestRule: xsd: " + testRule.getValue());
				
				report = xmlSchemaValidation( fileRelPath, testRule.getValue(), report );
				
				report.addToFullDescription("<br>-END XMLSCHEMA Validation");
			}			
				
			
			
			if (testRule.getLanguage().equals(Constants.SCHEMATRON)) {
				
				report.addToFullDescription("<br>-START SCHEMATRON Validation");
				
				report = schematronValidation(xmlString, testRule.getValue(), report);
				
				report.addToFullDescription("<br>-END SCHEMATRON Validation");
			}

			

	
			if (testRule.getLanguage().equals(Constants.XPATH)) {
				
				report.addToFullDescription("<br>-START XPATH Validation");	 
				
				report.addToFullDescription("<br>-TESTRULE: " + testRule.getValue());
	
				report = xPathValidation(xmlString, testRule.getValue(), report);
				
				report.addToFullDescription("<br>-END XPATH Validation");
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

		System.out.println("xmlSchemaValidation A:" + xmlString);
		System.out.println("xmlSchemaValidation B:" + xsdString);
		

		//String xmlRelPathFileName = "TeBES_Artifacts/users/0/docs/ubl-invoice.xml";
		//String xsdURL = "http://winter.bologna.enea.it/peppol_schema_rep/xsd/maindoc/UBL-Invoice-2.0.xsd";
		
		XErrorMessage emList[] = null;

		try {
			validationManager = JNDIServices.getValidationManagerService();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			emList = validationManager.validation(xmlString, xsdString);
			
			// TODO considero solo il primo record (dalle prove... solo il primo veniva effettivamente usato)
			TestResult result;
			if (emList.length > 0)
				result = new TestResult(emList[0].getErrorType(), emList[0].getLineNumber(), emList[0].getDescription());
			else
				result = new TestResult("success", 0, "Success: Empty Error Message List");
			
			report.setTempResult(result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		report.setPartialResultSuccessfully(true);
		return report;
	}	

	
	public Report schematronValidation(String xmlString, String xmlSchematron, Report report) {

		System.out.println("schematronValidation! azx 1");
		
		System.out.println("schematronValidation! azx: " + xmlString);
		System.out.println("schematronValidation! azx: " + xmlSchematron);
		
		
		// TODO
		// verifica sia uno schematron valido
		
		XErrorMessage emList[] = null;

		try {
			System.out.println("schematronValidation! azx2");
			validationManager = JNDIServices.getValidationManagerService();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println("schematronValidation! azx3");
			emList = validationManager.validation(xmlString, xmlSchematron);
			System.out.println("schematronValidation! azx4");
			
			// TODO considero solo il primo record (dalle prove... solo il primo veniva effettivamente usato)
			TestResult result;
			if (emList.length > 0)
				result = new TestResult(emList[0].getErrorType(), emList[0].getLineNumber(), emList[0].getDescription());
			else
				result = new TestResult("success", 0, "Success: Empty Error Message List");
				
			report.setTempResult(result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		report.setPartialResultSuccessfully(true);
		return report;
	}


	public Report xPathValidation(String xmlString, String xpath, Report report) {
		
		report.setPartialResultSuccessfully(true);
		return report;
	}
}

