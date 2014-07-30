/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.enea.xlab.tebes.external.jolie.gjs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jolie.net.CommMessage;
import jolie.runtime.FaultException;
import jolie.runtime.JavaService;
import jolie.runtime.Value;
import joliex.java.Callback;
import joliex.java.Service;
import joliex.java.ServiceFactory;

/**
 *
 * @author claudio
 */
public class JavaJolieclient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        // preparazione del Value

        // creazione client
        final ServiceFactory factory = new ServiceFactory();
        final Service service = factory.create(new URI("socket://localhost:9111"), "sodep", Value.UNDEFINED_VALUE);

        // creazione messaggio di richiesta, Value è la classe Java con la quale si riesce a creare un messaggio per Jolie
        GenerateClientWSRequest request = new GenerateClientWSRequest();
        request.setWsdl("http://www.webservicex.net/globalweather.asmx?WSDL");
        request.setOperation("GetWeather");
        request.setPort("GlobalWeatherSoap");
        request.setIdService("test");
        request.setPath("./test/tmppath");
        request.setFilename("report.xml");
        request.setMessageProfile("standard");
        WSMessageType messageType = new WSMessageType();
        WSMessageType.parameter parameter0 = messageType.new parameter();
        parameter0.setKey("CountryName");
        parameter0.set__Value("Italy");
        WSMessageType.parameter parameter1 = messageType.new parameter();
        parameter1.setKey("CityName");
        parameter1.set__Value("Bologna");
        messageType.addParameterValue(parameter0);
        messageType.addParameterValue(parameter1);
        request.setRequestMessage(messageType);


        service.callRequestResponse("generateClientWS", request.getValue(), new Callback() {
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
}
