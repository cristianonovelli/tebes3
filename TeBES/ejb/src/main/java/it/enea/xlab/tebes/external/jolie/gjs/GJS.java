/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.enea.xlab.tebes.external.jolie.gjs;

import it.enea.xlab.tebes.common.PropertiesUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;

import jolie.runtime.FaultException;
import jolie.runtime.Value;
import joliex.java.Callback;
import joliex.java.Service;
import joliex.java.ServiceFactory;

/**
 * @author Claudio Guidi, Cristiano Novelli
 */
public class GJS {

	private static final String CLIENT = "generateClientWS";
	private static final String SERVER = "generateServerWS";
	private static final String SODEP = "sodep";
	
	
	
    public static void generateClientWS(
    		String wsdl, 
    		String operation, 
    		String port, 
    		String serviceId, 
    		String tempLocalPath,
    		String outputFileName,
    		String messageProfile,
    		String[][] parameters) 
    		throws IOException, URISyntaxException {
        
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
        

        
        parameter0.setKey(parameters[0][0]);
        parameter0.set__Value(parameters[0][1]);
        WSMessageType.parameter parameter1 = messageType.new parameter();
        parameter1.setKey(parameters[1][0]);
        parameter1.set__Value(parameters[1][1]);
        
        messageType.addParameterValue(parameter0);
        messageType.addParameterValue(parameter1);
        request.setRequestMessage(messageType);


        service.callRequestResponse(CLIENT, request.getValue(), new Callback() {
        	
            //@Override
            public void onSuccess(Value value) {
                System.out.println("Success");
                factory.shutdown();

            }

            //@Override
            public void onFault(FaultException fe) {
                System.out.println("Fault " + fe.faultName());
                factory.shutdown();
            }

            //@Override
            public void onError(IOException ioe) {
                ioe.printStackTrace();
                factory.shutdown();
            }
        });
    }


   /* public static void generateServerWS(
    		String wsdl, 
    		String operation, 
    		String port, 
    		String serviceId, 
    		String tempLocalPath,
    		String outputFileName,
    		String messageProfile,
    		String[][] parameters) 
    		throws IOException, URISyntaxException {
        
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
        
        // Creazione messaggio
        WSMessageType messageType = new WSMessageType();
        WSMessageType.parameter parameter0 = messageType.new parameter();
        

        
        parameter0.setKey(parameters[0][0]);
        parameter0.set__Value(parameters[0][1]);
        WSMessageType.parameter parameter1 = messageType.new parameter();
        parameter1.setKey(parameters[1][0]);
        parameter1.set__Value(parameters[1][1]);
        
        messageType.addParameterValue(parameter0);
        messageType.addParameterValue(parameter1);
        request.setRequestMessage(messageType);


        service.callRequestResponse(SERVER, request.getValue(), new Callback() {
        	
            //@Override
            public void onSuccess(Value value) {
                System.out.println("Success");
                factory.shutdown();

            }

            //@Override
            public void onFault(FaultException fe) {
                System.out.println("Fault " + fe.faultName());
                factory.shutdown();
            }

            //@Override
            public void onError(IOException ioe) {
                ioe.printStackTrace();
                factory.shutdown();
            }
        });
    }*/

}

