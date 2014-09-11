package it.enea.xlab.tebes.test.rule;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.common.SUTConstants;
import it.enea.xlab.tebes.entity.FileStore;
import it.enea.xlab.tebes.entity.Input;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.SUTInteraction;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TextStore;
import it.enea.xlab.tebes.external.jolie.gjs.GJSConstants;
import it.enea.xlab.tebes.input.FileManagerRemote;
import it.enea.xlab.tebes.model.TAF;
import it.enea.xlab.tebes.model.TestRule;
import it.enea.xlab.tebes.report.ReportManagerRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;

import org.xlab.file.XLabFileManager;
import org.xlab.utilities.XLabDates;

 


@Stateless
@Interceptors({Profile.class})
public class RuleManagerImpl implements RuleManagerRemote {
	

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
		report.addLineToFullDescription("---- TEST EXECUTION ----");
		TestRule testRule = taf.getPredicate();
		report.addLineToFullDescription("---- Language: " + testRule.getLanguage());
		report.addLineToFullDescription("---- Value: " + testRule.getValue());
		
		
		
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
			
			DocumentManager documentManager = new DocumentManager();
			
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
					
					report = documentManager.xmlSchemaValidation( fileRelPath, schema, report );
					
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
						
					
					report = documentManager.schematronValidation(xmlString, schematron, report);
					
					report.addLineToFullDescription("-END SCHEMATRON Validation");
				}

				

				// CASO 1.3 Document XPATH Validation
				else if (testRule.getLanguage().equals(Constants.XPATH)) {
					
					report.addLineToFullDescription("---- XPATH validation recognized");
					
					report.addLineToFullDescription("-TESTRULE: " + testRule.getValue());
		
					report = documentManager.xPathValidation(xmlString, testRule.getValue(), report);
					
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
			
			TransportManager transportManager = new TransportManager();
			
			
			
			report.addLineToFullDescription("---- Subcase: " + taf.getTarget().getValue());
			 
			
			// NEL CASO TRANSPORT ABBIAMO 4 CASI, RELATIVI A 4 TIPI DI SUT DA TESTARE: 
			// website, email, webservice-client, webservice-server
			// C'E' SEMPRE UN INPUT (QUANDO NON C'E'?)
			
			
			
			/////////////////////////////
			// 2.4 Transport WS_SERVER //
			/////////////////////////////
			// In questo caso l'utente espone il WS e richiama la generazione del Client
			if ( taf.getTarget().getValue().equals(SUTInteraction.WS_SERVER)) {
				
				report.addLineToFullDescription("---- Preparing for calling to GJS WS-Generator");
				
				
				// 2.4.1 Definition of WS Identifier as: sutInteraction + datetime
				String serviceId = SUTInteraction.WS_SERVER.concat(Constants.UNDERSCORE);
				serviceId = serviceId.concat(XLabDates.getCurrentUTC());
				// TODO quando ricarichi xlab-common, sostituisci usando metodo getCurrentCleanedUTC()
				serviceId = serviceId.replace(":", "");
				serviceId = serviceId.replace("-", "");
				
				report.addLineToFullDescription("---- Defined Service ID: " + serviceId);
				
				
				// 2.4.2 WSDL SUT Parameters
				SUTInteraction sutInteraction = session.getSut().getInteraction();
				String wsdl = sutInteraction.getEndpoint();
				String operation = sutInteraction.getOperation();
				String port = sutInteraction.getPort();
				
				report.addLineToFullDescription("----- Add WSDL parameters");
				report.addLineToFullDescription("------ WSDL: " + wsdl);
				report.addLineToFullDescription("------ Operation: " + operation);
				report.addLineToFullDescription("------ Port: " + port);
				
				
				// 2.4.3 GJS Output
				String gjsOutputPath = PropertiesUtil.getUserWSDirPath(session.getUser().getId());
				gjsOutputPath = gjsOutputPath.concat(serviceId).concat(Constants.SLASH);
				XLabFileManager.createDir(gjsOutputPath);
				String gjsOutputReport = GJSConstants.REPORT_NAME;
				
				report.addLineToFullDescription("---- Output: " + gjsOutputPath + gjsOutputReport);
				
				
				// 2.4.4 Text Input Parameters
				Input inputTemp;
				int inputNumber = taf.getInputs().size();
				String[][] parameters = new String[inputNumber][inputNumber];	
				for (int i=0; i<inputNumber; i++) {

					inputTemp = taf.getInputs().get(i);				
					TextStore text = fileManager.readTextbyIdRef(inputTemp.getFileIdRef());

					parameters[i][0] = text.getLabel();
					parameters[i][1] = text.getValue();
					
					report.addLineToFullDescription("----- Add Text Input: " + text.getLabel() + " - " + text.getValue());
				}

				report.addLineToFullDescription("---- Start CALL GJS WS-Generator to generate WS CLIENT...");
				
				// WS-SERVER CALL
				report = transportManager.WSServerValidation(wsdl, operation, port, serviceId, gjsOutputPath, gjsOutputReport, parameters, report);			
				
				report.addLineToFullDescription("---- OK. End CALL to GJS WS-Generator.");
				
			} // END CASE 2.4
			
			
			// OTHERWISE
			else
				report.addLineToFullDescription("---- ERROR: No TRANSPORT subcase recognized!");
			
			
		} // END CASE 2 TRANSPORT
			
		
		
		
		
		
		report.addLineToFullDescription("------------------------");
		
		return report;
	}
	


	
	
}

