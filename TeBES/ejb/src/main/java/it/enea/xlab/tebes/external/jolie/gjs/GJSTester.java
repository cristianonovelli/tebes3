package it.enea.xlab.tebes.external.jolie.gjs;

import java.io.IOException;
import java.net.URISyntaxException;

import jolie.runtime.Value;

public class GJSTester {

	/**
	 * @param args
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception {
		
		System.out.println("******************************");
		System.out.println("** GlobalWeather DoubleTest **");
		System.out.println("******************************");
		System.out.println();
		
		
		// Preparo Risposta per il Server
		String[][] parameters = new String[2][2];	
		parameters[0][0] = "Result";
		parameters[0][1] = "ResponseFromGlobalWeather";
		
		// GJSResult
		GJSResult myHandler = new GJSResult();
		
		try {
			System.out.println("** Test1 GJSTester-Server **");
			System.out.println("CALL: generateServerWS");
			GJS.generateServerWS("http://www.webservicex.net/globalweather.asmx?WSDL", 
					"GetWeather", 
					"GlobalWeatherSoap",
					"GJSTester-Server",
					"C:/Temp/server",
					"report.xml",
					"standard",
					10000,
					parameters, 
					myHandler);
		} catch (Exception e) {
			myHandler.setError(e.getMessage());
		}
		
	int i=0;
	System.out.print("waiting for test1:");
		while( i<3 ) {
			try {

				Thread.sleep(1000);
				System.out.print(" " + ++i);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
		System.out.println("End GJSTester-Server: " + myHandler.getDescription());		
		
		System.out.println("****************************");
		System.out.println();
		
		
		String[][] parameters2 = new String[2][2];
		
		parameters2[0][0] = "CountryName";
		parameters2[0][1] = "Italy";

		parameters2[1][0] = "CityName";
		parameters2[1][1] = "Bologna";

		GJSResult myHandler2 = new GJSResult();
		
		System.out.println("** Test2 GJSTester-Client **");
		System.out.println("CALL: generateClientWS");
		System.out.println("Test Client for WS: " + myHandler.getUrl());
		GJS.generateClientWS(myHandler.getUrl(), 
					"GetWeather", 
					"GlobalWeatherSoap",
					"GJSTester-Client",
					"C:/Temp/client",
					"report.xml",
					"standard",
					parameters2, 
					myHandler2);
	
		i=0;
		System.out.print("waiting for test2:");
		while( i<3 ) {
			try {

				Thread.sleep(1000);
				System.out.print(" " + ++i);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
		
		System.out.println("END GJSTester-Client: " + myHandler2.getDescription());		
		System.out.println("END GJSTester-Client: " + myHandler2.getType());	

		System.out.println();
		System.out.println("******************************");
		System.out.println();
	}

}
