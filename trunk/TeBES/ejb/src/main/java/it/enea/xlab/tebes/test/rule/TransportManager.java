package it.enea.xlab.tebes.test.rule;

import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.dao.TeBESDAO;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.TestResult;
import it.enea.xlab.tebes.external.jolie.gjs.GJS;
import it.enea.xlab.tebes.external.jolie.gjs.GJSConstants;
import it.enea.xlab.tebes.external.jolie.gjs.GJSResult;

import java.net.ConnectException;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.xlab.file.XLabFileManager;

 


@Stateless
@Interceptors({Profile.class})
public class TransportManager {
	

	public TransportManager() {
		
	}
	
	////////////////////////////////////////////////
	// WSServerValidation -> GJS.generateClientWS //
	////////////////////////////////////////////////
	@SuppressWarnings("finally")
	public Report WSServerValidation(
			String wsdl, 
			String operation, 
			String port, 
			String serviceId,
			String outputPath,
			String outputReport,
			String[][] parameters, 
			Report report) {

		TestResult result = null;
		ArrayList<TestResult> testResultList = new ArrayList<TestResult>(); 
		
			try {
				
				report.addLineToFullDescription("----- START TransportManager.WSServerValidation");
				report.addLineToFullDescription("----- CALL to GJS.generateClientWS");
				
				GJSResult gjsResult = new GJSResult();
				
				// CALL TO GJS TO GENERATE WS CLIENT
				GJS.generateClientWS(wsdl, operation, port, serviceId, outputPath, outputReport, GJS.PROFILE_STANDARD, parameters, gjsResult);
				
				
				// TODO GESTIONE GET STATUS!!!
				
				// Gestione OUTPUT dipendentemente da GJSResult
				// 1. SUCCESS
				// 2. FAULT or ERROR

				// 1. SUCCESS
				if ( gjsResult.isSuccess() )
				{				
					// TODO INSERISCO 3 MESSAGGI DI SUCCESSO CHE PUNTANO AI FILE NELLA CARTELLA CON I RISPETTIVI LINK
					report.setPartialResultSuccessfully(true);
					
					// Inserisco messaggio 1di3, generale di SUCCESS
					result = new TestResult(TestResult.PASS_RESULT, 0, gjsResult.getDescription());
					testResultList.add(result);
					
					// Inserisco messaggio 2di3, link alla Richiesta 
					String wsRequest = outputPath.concat(GJSConstants.WSREQUEST);
					String wsRequestURL = TeBESDAO.location2publication(wsRequest);
					result = new TestResult(TestResult.PASS_RESULT, 0, 
							"See the generated request " + GJSConstants.WSREQUEST + " to the Link.", wsRequestURL);			
					testResultList.add(result);
					
					// Inserisco messaggio 3di3, link alla Risposta
					String wsResponse = outputPath.concat(GJSConstants.WSRESPONSE);
					String wsResponseURL = TeBESDAO.location2publication(wsResponse);
					result = new TestResult(TestResult.PASS_RESULT, 0, 
							"See the received response " + GJSConstants.WSRESPONSE + " to the Link.", wsResponseURL);
					
					testResultList.add(result);										
					report.setTempResultList(testResultList);					
					report.addLineToFullDescription("----- GJS Generator: " + gjsResult.getDescription());
					
				} // END 1. SUCCESS
				
				
				// 2. FAULT o ERROR
				if ( gjsResult.isFault() || gjsResult.isError() ) {

					
					report.setPartialResultSuccessfully(false);		
					
					result = new TestResult(TestResult.ERROR_RESULT, 0, gjsResult.getDescription());
					
					testResultList.add(result);										
					report.setTempResultList(testResultList);					
					report.addLineToFullDescription("----- GJS Generator: " + gjsResult.getDescription());
					
				} // END 2. FAULT o ERROR
				
				
				
				if (result == null)
					result = new TestResult(TestResult.ERROR_RESULT, 0, "No Results for GJS WS-Generator: contact TeBES Admnistrator.");
					
				report.setTempResult(result);
			} 
			
			
			catch (ConnectException e) {

				report.setPartialResultSuccessfully(false);				
				report.addLineToFullDescription(e.getMessage());
				
				result = new TestResult(TestResult.ERROR_RESULT, 0, e.getMessage());
				
				report.setTempResult(result);
				
				testResultList.add(result);
				report.setTempResultList(testResultList);
				
				e.printStackTrace();	
				
				report.addLineToFullDescription("----- " + TestResult.ERROR_RESULT + ": " + e.getMessage());
				
			} catch (Exception e) {
				
				report.setPartialResultSuccessfully(false);			
				report.addLineToFullDescription(e.getMessage());
				
				result = new TestResult(TestResult.ERROR_RESULT, 0, e.getMessage());
				
				report.setTempResult(result);
				
				testResultList.add(result);
				report.setTempResultList(testResultList);
				
				e.printStackTrace();
				
				report.addLineToFullDescription("----- " + TestResult.ERROR_RESULT + ": " + e.getMessage());

			} finally {			
				
				report.addLineToFullDescription("----- END TransportManager.WSServerValidation");
				return report;
			}

	} // END WSServerValidation
	
	
	
	
	
}

