package it.enea.xlab.tebes.test.rule;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.entity.FileStore;
import it.enea.xlab.tebes.entity.Input;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.file.FileManagerRemote;
import it.enea.xlab.tebes.model.TAF;
import it.enea.xlab.tebes.model.TestRule;
import it.enea.xlab.tebes.validation.ValidationManagerRemote;

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
		
		System.out.println("executeTestRule: START");
		
		Report report = session.getReport();
		
		TestRule testRule = taf.getPredicate();

		
		report.addToFullDescription("\n- Prerequisite OK... EXE Predicate");
		report.addToFullDescription("\n- Language: " + testRule.getLanguage());
		report.addToFullDescription("\n- Value: " + testRule.getValue());

		System.out.println("executeTestRule: taf.getName(): " + taf.getName());
		System.out.println("executeTestRule: taf.getInputs().size(): " + taf.getInputs().size());
		System.out.println("executeTestRule: testRule.getLanguage(): " + testRule.getLanguage());
		System.out.println("executeTestRule: testRule.getValue(): " + testRule.getValue());
		
		
		try {
			fileManager = JNDIServices.getFileManagerService();
			
			
			// File 1
			Input input = taf.getInputs().get(0);
			
			FileStore file = fileManager.readFilebyIdRef(input.getFileIdRef());
			System.out.println("executeTestRule: file: " + file.getFileRefId());
			System.out.println("executeTestRule: file: " + file.getFileName());
			System.out.println("executeTestRule: file: " + file.getType());
			
			String userDocsAbsDir = PropertiesUtil.getUserDocsDirPath(session.getUser().getId());
	
			String xmlString = userDocsAbsDir.concat(file.getFileName());
			//String xmlString = "TeBES_Artifacts/users/1/docs/ubl-invoice.xml";
			System.out.println("executeTestRule: file: " + xmlString);
			
			
			
			if (testRule.getLanguage().equals(Constants.XPATH)) {
				
				
				report.addToFullDescription("\nTESTRULE: " + testRule.getValue());
	
				System.out.println("executeTestRule: pre-validation XPATH");	 
				boolean xpathValidation = xPathValidation(xmlString, testRule.getValue());
				report.setPartialResultSuccessfully(xpathValidation);
				System.out.println("executeTestRule: post-validation XPATH");
			}
				
				
			
			
			if (testRule.getLanguage().equals(Constants.SCHEMATRON)) {
				System.out.println("executeTestRule: pre-validation SCHEMATRON");
				report.setPartialResultSuccessfully(schematronValidation(xmlString, testRule.getValue()));
				System.out.println("executeTestRule: post-validation SCHEMATRON");
			}
			
			
			
			// XML Schema validation
			if (testRule.getLanguage().equals(Constants.XMLSCHEMA)) { 
				
				System.out.println("executeTestRule: pre-validation XMLSCHEMA");
	
				
	
						
				//String fileRelPath = file.getFileName();
				//String fileRelPath = xmlString;
				
				String fileRelPath = userDocsAbsDir.concat(file.getFileName());
				
				System.out.println("executeTestRule: xml: " + fileRelPath);
				System.out.println("executeTestRule: xsd: " + testRule.getValue());
				
				report.setPartialResultSuccessfully(xmlSchemaValidation( fileRelPath, testRule.getValue() ));
				
				System.out.println("executeTestRule: post-validation XMLSCHEMA");
			}
	
			
			report.addToFullDescription("\nTODO: Schematron or XPath Execution");
			
			System.out.println("executeTestRule: END");
			
		} catch (NamingException e) {
			
			System.out.println("executeTestRule: NamingException");
			
			// TODO Adjust Report
			e.printStackTrace();
		}		
		
		
		
		return report;
	}
	

	

	
	public boolean schematronValidation(String xmlString, String xmlSchematron) {

		return true;
	}


	public boolean xPathValidation(String xmlString, String xpath) {
		
		return true;
	}

	public boolean xmlSchemaValidation(String xmlString, String xsdString) {

		

		System.out.println("xmlSchemaValidation A:" + xmlString);
		System.out.println("xmlSchemaValidation B:" + xsdString);
		

		//String xmlRelPathFileName = "TeBES_Artifacts/users/0/docs/ubl-invoice.xml";
		//String xsdURL = "http://winter.bologna.enea.it/peppol_schema_rep/xsd/maindoc/UBL-Invoice-2.0.xsd";
		
		ErrorMessage emList[] = null;

		try {
			validationManager = JNDIServices.getValidationManagerService();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			emList = validationManager.validation(xmlString, xsdString);
			
			int i=0;

			while (i<emList.length){
				
				System.out.println("RIGA " + i + ": " + emList[i].getErrorType());
				System.out.println("RIGA " + i + ": " + emList[i].getLineNumber());
				System.out.println("RIGA " + i + ": " + emList[i].getDescription());
				
				
				i++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return true;
	}
}

