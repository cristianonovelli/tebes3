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

	// Client, Server or Status Calls
	public static final String CLIENT = "generateClientWS";
	public static final String SERVER = "generateServerWS";
	public static final String STATUS = "getStatus";
	public static final String STOP = "stop";

	// Protocol TeBES -> GJS
	private static final String SODEP = "sodep";

	// GJS Profile
	public static final String PROFILE_STANDARD = "standard";

	// Other
	private static boolean waitGJS = true;
	private static final int WAIT_UNIT = 100;


	
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
		
		// Creazione factory
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
		for (int p=0; p<parameters.length; p++) {
			
			WSMessageType.parameter singleParameter = messageType.new parameter();
			singleParameter.setKey(parameters[p][0]);
			singleParameter.set__Value(parameters[p][1]);
			messageType.addParameterValue(singleParameter);
		}
		request.setRequestMessage(messageType);

		
		// CALL callRequestResponse
		service.callRequestResponse(CLIENT, request.getValue(), new Callback() {

			public void onSuccess(Value value) {
				
				gjsResult.setSuccess();		
				gjsResult.setDescription("Success: Client generated and call to WS performed!");
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
		System.out.print("Waiting for GJS factory shutdown: ");
		while( waitGJS ) {
			try {

				Thread.sleep(WAIT_UNIT);
				System.out.print(++i + " ");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
		
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
		
		// Creazione factory
		final ServiceFactory factory = new ServiceFactory();
		String gjsURL = PropertiesUtil.getGJSURL();
		final Service service = factory.create(new URI(gjsURL), SODEP, Value.UNDEFINED_VALUE);

		// Creazione richiesta
		GenerateServerWSRequest request = new GenerateServerWSRequest();
		request.setWsdl(wsdl);

		request.setOperation(operation);
		request.setPort(port);

		// Creazione messaggio
		WSMessageType messageType = new WSMessageType();
		for (int p=0; p<parameters.length; p++) {
			
			WSMessageType.parameter singleParameter = messageType.new parameter();
			singleParameter.setKey(parameters[p][0]);
			singleParameter.set__Value(parameters[p][1]);
			messageType.addParameterValue(singleParameter);
		}
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
				gjsResult.setDescription("Success: Web Service generated at URL: " + response.getWsdlEndpoint() + " Test your Client!");
				
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

				factory.shutdown();
				waitGJS=false;
			}

		}); // END callRequestResponse

		// Aspetto che la callRequestResponse raggiunga un esito
		int i=0;
		System.out.print("Waiting for GJS factory shutdown: ");
		while( waitGJS ) {
			try {

				Thread.sleep(WAIT_UNIT);
				System.out.print(++i + " ");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
		
		return;
		
	} // END generateServerWS
	
	
	
	/////////////////
	/// getStatus ///
	/////////////////
	public static void getStatus(String idService, 
			final GJSResult gjsResult) 
					throws URISyntaxException, IOException, InterruptedException {

		
		// Creazione factory
		final ServiceFactory factory = new ServiceFactory();
		String gjsURL = PropertiesUtil.getGJSURL();
		final Service service = factory.create(new URI(gjsURL), SODEP, Value.UNDEFINED_VALUE);

        // Creazione richiesta
        GetStatusRequest request = new GetStatusRequest();
        request.setIdService( idService );
        
        service.callRequestResponse(STATUS, request.getValue(), new Callback() {
        	
            public void onSuccess(Value value) {

                GetStatusResponse response = new GetStatusResponse( value );				
				gjsResult.setSuccess(response.getStatus());				
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

				factory.shutdown();
				waitGJS=false;
			}
        });
      
    } // END getStatus

	
	
	////////////
	/// stop ///
	////////////
	public static void stop(
			String idService, 
			final GJSResult gjsResult) 
					throws URISyntaxException, IOException, InterruptedException {

		
		// Creazione factory
		final ServiceFactory factory = new ServiceFactory();
		String gjsURL = PropertiesUtil.getGJSURL();
		final Service service = factory.create(new URI(gjsURL), SODEP, Value.UNDEFINED_VALUE);

        // Creazione richiesta
        StopRequest request = new StopRequest();
        request.setIdService( idService );
        
        service.callRequestResponse(STOP, request.getValue(), new Callback() {
        	
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

				factory.shutdown();
				waitGJS=false;
			}
        });
      
    } // END stop
	

} // END GJS

