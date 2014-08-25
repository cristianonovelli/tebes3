package it.enea.xlab.tebes.test.rule;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.common.SUTConstants;
import it.enea.xlab.tebes.entity.FileStore;
import it.enea.xlab.tebes.entity.Input;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestResult;
import it.enea.xlab.tebes.input.FileManagerRemote;
import it.enea.xlab.tebes.model.TAF;
import it.enea.xlab.tebes.model.TestRule;
import it.enea.xlab.tebes.report.ReportManagerRemote;
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
	
	@EJB
	private ReportManagerRemote reportManager; 
	
	/**
	 * Execute Test Rule
	 */
	public Report executeTestRule(TAF taf, Session session) { 
		
		Report report = session.getReport();
		
		// TARGET TYPE INDICA LA TIPOLOGIA DEL TEST: document, transport, process
		// PER OGNUNO DEI TRE TIPI AGIRO' DIVERSAMENTE

		report.addLineToFullDescription("---executeTestRule");
		report.addLineToFullDescription("---Target Type: " + taf.getTarget().getType());
		
		report.addLineToFullDescription("\n");
		report.addLineToFullDescription("---- TEST RULE ----");
		TestRule testRule = taf.getPredicate();
		report.addLineToFullDescription("- Language: " + testRule.getLanguage());
		report.addLineToFullDescription("- Value: " + testRule.getValue());
		report.addLineToFullDescription("TAF Input List Size: " + taf.getInputs().size());
		
		
		
		try {
			
			// FILE and TEXT Manager
			fileManager = JNDIServices.getFileManagerService();
			
		} catch (NamingException e1) {
			reportManager.saveLog(report, "executeTestRule.NamingException");
			e1.printStackTrace();
		}
		
		
		
		//////////////////////
		// CASO 1: DOCUMENT //
		//////////////////////
		if (taf.getTarget().getType().equals(SUTConstants.SUT_TYPE1_DOCUMENT)) {
			
			report.addLineToFullDescription("---- DOCUMENT Level recognized");
			
			try {
				Input input = taf.getInputs().get(0);
				
				FileStore file = fileManager.readFilebyIdRef(input.getFileIdRef());
				report.addLineToFullDescription("-executeTestRule: file: " + file.getFileRefId());
				report.addLineToFullDescription("-executeTestRule: file: " + file.getFileName());
				report.addLineToFullDescription("-executeTestRule: file: " + file.getType());
				
				String userDocsAbsDir = PropertiesUtil.getUserDocsDirPath(session.getUser().getId());
		
				String xmlString = userDocsAbsDir.concat(file.getFileName());
				//String xmlString = "TeBES_Artifacts/users/1/docs/ubl-invoice.xml";
				report.addLineToFullDescription("executeTestRule: file: " + xmlString);
				
				
				
				// CASO 1.1 Document XML SCHEMA validation
				if (testRule.getLanguage().equals(Constants.XMLSCHEMA)) { 
					
					report.addLineToFullDescription("---- XMLSCHEMA validation recognized");

							
					String schema = testRule.getValue();
					
					// TODO bisogna passargli non il path assoluto ma l'URL della cache di TeBES
					/*if ( !XURL.isURLExistent(schema) ) {
						
						schema = PropertiesUtil.getCacheDirPath().concat(XLabUtilities.getFileNameFromAbsFileName(schema));			
					}*/
					
					String fileRelPath = userDocsAbsDir.concat(file.getFileName());
					
					report.addLineToFullDescription("-executeTestRule: xml: " + fileRelPath);
					report.addLineToFullDescription("-executeTestRule: xsd: " + testRule.getValue());
					
					report = xmlSchemaValidation( fileRelPath, schema, report );
					
					report.addLineToFullDescription("-END XMLSCHEMA Validation");
				}			
					
				
				
				// CASO 1.2 Document SCHEMATRON validation
				else if (testRule.getLanguage().equals(Constants.SCHEMATRON)) {
					
					report.addLineToFullDescription("---- SCHEMATRON validation recognized");
					
					String schematron = testRule.getValue();
					
					// TODO bisogna passargli non il path assoluto ma l'URL della cache di TeBES
					/*if ( !XURL.isURLExistent(schematron) ) {
						
						schematron = PropertiesUtil.getCacheDirPath().concat(XLabUtilities.getFileNameFromAbsFileName(schematron));			
					}*/
						
					
					report = schematronValidation(xmlString, schematron, report);
					
					report.addLineToFullDescription("-END SCHEMATRON Validation");
				}

				

				// CASO 1.3 Document XPATH Validation
				else if (testRule.getLanguage().equals(Constants.XPATH)) {
					
					report.addLineToFullDescription("---- XPATH validation recognized");
					
					report.addLineToFullDescription("-TESTRULE: " + testRule.getValue());
		
					report = xPathValidation(xmlString, testRule.getValue(), report);
					
					report.addLineToFullDescription("-END XPATH Validation");
				}
				
				else
					report.addLineToFullDescription("---- ERROR: No DOCUMENT subcase recognized!");
				
			
				
			} catch (IndexOutOfBoundsException e) {
				
				System.out.println("executeTestRule: IndexOutOfBoundsException");
				reportManager.saveLog(report, "executeTestRule");

				e.printStackTrace();
			}		
			
		}
		
		
		
		
		///////////////////////
		// CASO 2: TRANSPORT //
		///////////////////////
		if (taf.getTarget().getType().equals(SUTConstants.SUT_TYPE2_TRANSPORT)) {
			
			
			report.addLineToFullDescription("---- TRANSPORT Level recognized");
			report.addLineToFullDescription("---- Selecting of subcase depending by " + taf.getInputs().get(0).getInteraction());
			 
			
			// NEL CASO TRANSPORT ABBIAMO 4 CASI, RELATIVI A 4 TIPI DI SUT DA TESTARE: 
			// website, email, webservice-client, webservice-server
			// C'E' SEMPRE UN INPUT (QUANDO NON C'E'?)
			
			// 2.4 Transport WS_SERVER Validation
			// In questo caso l'utente espone il WS e richiama la generazione del Client
			if (taf.getInputs().get(0).getInteraction().equals(SUTConstants.INTERACTION_WS_SERVER)) {
				
				report.addLineToFullDescription("---- WS_SERVER validation recognized");
				report.addLineToFullDescription("Input Interaction: " + taf.getInputs().get(0).getInteraction());
				
				report.addLineToFullDescription("---- CALLING GJS WS-Generator to generate WS Client...");
				
				report.addLineToFullDescription("---- TODO");
				
				report.addLineToFullDescription("---- OK CALLED GJS WS-Generator.");
			}
			else
				report.addLineToFullDescription("---- ERROR: No TRANSPORT subcase recognized!");
			
			
		}
			
		
		
		
		
		
		
		// Se ha Input...
		if (taf.getInputs().size() > 0) {
		
			Input inputTemp = taf.getInputs().get(0);
			report.addLineToFullDescription("Input Interaction: " + inputTemp.getInteraction());
		}
		

		
		
		
		
		
		
		
		
		
		
		
		return report;
	}
	

	public Report xmlSchemaValidation(String xmlString, String xsdString, Report report) {

		
		
		// TODO
		// verifica sia uno schematron valido

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

