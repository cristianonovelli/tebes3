package it.enea.xlab.tebes.external.jolie.gjs;

import it.enea.xlab.tebes.common.PropertiesUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import jolie.runtime.FaultException;
import jolie.runtime.Value;
import joliex.java.Callback;
import joliex.java.Service;
import joliex.java.ServiceFactory;

/**
 * @author Cristiano Novelli, Claudio Guidi
 */
public class GJS {

	// Client or Server Calls
	public static final String CLIENT = "generateClientWS";
	public static final String SERVER = "generateServerWS";

	// Protocol TeBES -> GJS
	private static final String SODEP = "sodep";

	// GJS Profile
	public static final String PROFILE_STANDARD = "standard";

	// Other
	private static boolean waitGJS = true;


	
	////////////////////////
	/// generateClientWS ///
	////////////////////////
	public static void generateClientWS(
			String wsdl, 
			String operation, 
			String port, 
			String serviceId, 
			String tempLocalPath,
			String outputFileName,
			String messageProfile,
			String[][] parameters,
			final GJSResult gjsResult) 
					throws IOException, URISyntaxException {

		gjsResult.setCall(CLIENT);
		
		// Creazione client
		final ServiceFactory factory = new ServiceFactory();
		String gjsURL = PropertiesUtil.getGJSURL();
		final Service service = factory.create(new URI(gjsURL), SODEP, Value.UNDEFINED_VALUE);

		// Creazione richiesta
		GenerateClientWSRequest request = new GenerateClientWSRequest();
		request.setWsdl(wsdl);
		request.setOperation(operation);
		request.setPort(port);
		request.setIdService(serviceId);
		request.setPath(tempLocalPath);
		request.setFilename(outputFileName);
		request.setMessageProfile(messageProfile);

		// Creazione messaggio
		WSMessageType messageType = new WSMessageType();
		WSMessageType.parameter parameter0 = messageType.new parameter();


// TODO ciclo 
		parameter0.setKey(parameters[0][0]);
		parameter0.set__Value(parameters[0][1]);
		WSMessageType.parameter parameter1 = messageType.new parameter();
		parameter1.setKey(parameters[1][0]);
		parameter1.set__Value(parameters[1][1]);

		messageType.addParameterValue(parameter0);
		messageType.addParameterValue(parameter1);
		request.setRequestMessage(messageType);

		
		// CALL callRequestResponse
		service.callRequestResponse(CLIENT, request.getValue(), new Callback() {

			public void onSuccess(Value value) {
				
				gjsResult.setSuccess();				
				shutdown();
			}

			public void onFault(FaultException fe) {
				
				gjsResult.setFault(fe.faultName());
				shutdown();
			}

			public void onError(IOException ioe) {
				
				gjsResult.setError(ioe.getMessage());
				shutdown();
			}
			
			public void shutdown() {

				waitGJS=false;
				factory.shutdown();
			}
			
		}); // END callRequestResponse
		
		// Aspetto che la callRequestResponse raggiunga un esito
		int i=0;
		while( waitGJS ) {
			try {

				Thread.sleep(100);
				System.out.println(i++);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return;
		
	} // END generateClientWS

	
	
	////////////////////////
	/// generateServerWS ///
	////////////////////////
	public static void generateServerWS(
			String wsdl, 
			String operation, 
			String port, 
			String serviceId, 
			String tempLocalPath,
			String outputFileName,
			String messageProfile,
			int timeout,
			String[][] parameters, 
			final GJSResult gjsResult) 
					throws IOException, URISyntaxException {

		gjsResult.setCall(SERVER);
		
		// Creazione server
		final ServiceFactory factory = new ServiceFactory();
		String gjsURL = PropertiesUtil.getGJSURL();
		final Service service = factory.create(new URI(gjsURL), SODEP, Value.UNDEFINED_VALUE);

		// Creazione richiesta
		GenerateServerWSRequest request = new GenerateServerWSRequest();
		request.setWsdl(wsdl);

		request.setOperation(operation);
		request.setPort(port);

		WSMessageType messageType = new WSMessageType();
		WSMessageType.parameter parameter0 = messageType.new parameter();
		parameter0.setKey(parameters[0][0]);
		parameter0.set__Value(parameters[0][1]);
		messageType.addParameterValue(parameter0);
		request.setResponseMessage(messageType);


		request.setIdService(serviceId);
		request.setPath(tempLocalPath);
		request.setFilename(outputFileName);
		request.setMessageProfile(messageProfile);
		request.setTimeout(timeout);


		service.callRequestResponse(SERVER, request.getValue(), new Callback() {

			public void onSuccess(Value value) {
				
				GenerateServerWSResponse response = new GenerateServerWSResponse( value );				
				gjsResult.setSuccess(response.getWsdlEndpoint());				
				shutdown();
			}

			public void onFault(FaultException fe) {
				
				gjsResult.setFault(fe.faultName());
				shutdown();
			}

			public void onError(IOException ioe) {
				
				gjsResult.setError(ioe.getMessage());
				shutdown();
			}

			public void shutdown() {

				waitGJS=false;
				factory.shutdown();
			}

		}); // END callRequestResponse

		// Aspetto che la callRequestResponse raggiunga un esito
		int i=0;
		while( waitGJS ) {
			try {

				Thread.sleep(100);
				System.out.println(i++);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return;
		
	} // END generateServerWS

} // END GJS

