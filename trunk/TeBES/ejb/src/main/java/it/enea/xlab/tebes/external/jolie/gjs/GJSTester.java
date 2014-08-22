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
		
		
		String[][] parameters = new String[2][2];
		
		parameters[0][0] = "CountryName";
		parameters[0][1] = "Italy";

		parameters[1][0] = "CityName";
		parameters[1][1] = "Bologna";

		
		
		
		
		
			/*GJS.generateClientWS("http://www.webservicex.net/globalweather.asmx?WSDL", 
					"GetWeather", 
					"GlobalWeatherSoap",
					"test",
					"./test/tmppath",
					"report.xml",
					"standard",
					parameters);*/
			

	}

}
