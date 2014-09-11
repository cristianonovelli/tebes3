package it.enea.xlab.tebes.external.jolie.gjs;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Hashtable;

public class GJSTester {

	/**
	 * @param args
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception {
		
		/*
		String[][] parameters = new String[2][2];
		
		parameters[0][0] = "CountryName";
		parameters[0][1] = "Italy";

		parameters[1][0] = "CityName";
		parameters[1][1] = "Bologna";

		GJS.generateClientWS("http://www.webservicex.net/globalweather.asmx?WSDL", 
					"GetWeather", 
					"GlobalWeatherSoap",
					"test",
					"./test/tmppath",
					"report.xml",
					"standard",
					parameters);*/
		
		
		String[][] parameters = new String[2][2];
		
		parameters[0][0] = "result";
		parameters[0][1] = "false";
		
		System.out.println("start");
		
		GJSResult myHandler = new GJSResult();
		try {
			GJS.generateServerWS("http://www.webservicex.net/globalweather.asmx?WSDL", 
					"GetWeather", 
					"GlobalWeatherSoap",
					"test",
					"./test/tmppath3",
					"report.xml",
					"standard",
					10000,
					parameters, myHandler);
		} catch (Exception e) {
			myHandler.setError(e.getMessage());
		}
		
		
		System.out.println("end: " + myHandler.getDescription());

	}

}
