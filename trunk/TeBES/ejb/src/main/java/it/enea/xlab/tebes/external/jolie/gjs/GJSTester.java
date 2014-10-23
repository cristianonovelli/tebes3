package it.enea.xlab.tebes.external.jolie.gjs;



public class GJSTester {

	
	
	//////////
	// MAIN //
	//////////
	public static void main(String[] args) throws Exception {
		
		generalDoubleTest();
		
		//globalWeatherDoubleTest();
		
	} // End MAIN

	
	
	/////////////////////////
	// GENERAL DOUBLE TEST //
	/////////////////////////
	public static void generalDoubleTest() throws Exception {
		
		System.out.println("************************");
		System.out.println("** General DoubleTest **");
		System.out.println("************************");
		System.out.println();
		
		
		// Preparo Risposta per il Server
		String[][] parameters = new String[1][2];	
		parameters[0][0] = "result";
		parameters[0][1] = "ResponseFromServer";
		
		// GJSResult
		GJSResult myHandler = new GJSResult();
		GJSResult stHandler = new GJSResult();
		
		try {
			System.out.println("** Test1 General-Server **");
			System.out.println("CALL: generateServerWS");
			GJS.generateServerWS("http://localhost:11000/getwsdl", 
					"submit", 
					"SoapPortServicePort",
					"General-Server",
					"C:/Temp/general-server",
					"report.xml",
					"standard",
					10000,
					parameters, 
					myHandler);
		} catch (Exception e) {
			myHandler.setError(e.getMessage());
		}

		int i=0;
		System.out.println("Waiting for Test1:");
		while( i<5 ) {
			try {
				GJS.getStatus("General-Server", stHandler);
								
				Thread.sleep(1000);
				System.out.println(" " + ++i + " " + stHandler.getValue());
				
				if ( (stHandler != null) && (stHandler.getValue() != null) && !stHandler.getValue().equals("Initialization") )
					i = 10;

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println();
		System.out.println("End GJSTester-Server: " + myHandler.getDescription());		
		
		System.out.println("****************************");
		System.out.println();
		
		if (myHandler.isSuccess()) {
		
			String[][] parameters2 = new String[1][2];
			
			parameters2[0][0] = "msg";
			parameters2[0][1] = "document";
	
			GJSResult myHandler2 = new GJSResult();
			
			System.out.println("** Test2 GJSTester-Client **");
			System.out.println("CALL: generateClientWS");
			System.out.println("Test Client for WS: " + myHandler.getValue());
			GJS.generateClientWS(myHandler.getValue(), 
						"submit", 
						"SoapPortServicePort",
						"General-Client",
						"C:/Temp/general-client",
						"report.xml",
						"standard",
						parameters2, 
						myHandler2);
		
			i=0;
			stHandler = new GJSResult();
			System.out.print("waiting for test2:");
			while( i<5 ) {
				try {
					GJS.getStatus("General-Client", stHandler);
					
					Thread.sleep(1000);
					System.out.println(" " + ++i + " " + stHandler.getValue());
					
					if ( (stHandler != null) && (stHandler.getValue() != null) && stHandler.getValue().equals("Done") )
						i = 5;
	
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println();
			
			System.out.println("END GJSTester-Client: " + myHandler2.getDescription());		
			System.out.println("END GJSTester-Client: " + myHandler2.getType());	

		}
		else
		{
			System.out.println("Test1 returned a NOT success result: Test2 skipped.");
			System.out.println();
		}
		
		GJS.stop("General-Server", stHandler);
		GJS.stop("General-Client", stHandler);		
		
		System.out.println("** END General DoubleTest **");
		System.out.println("****************************");
		System.out.println("****************************");
		System.out.println();
	}

	
	
	///////////////////////////////
	// GLOBALWEATHER DOUBLE TEST //
	///////////////////////////////
	public static void globalWeatherDoubleTest() throws Exception {
		
		System.out.println("******************************");
		System.out.println("** GlobalWeather DoubleTest **");
		System.out.println("******************************");
		System.out.println();
		
		
		// Preparo Risposta per il Server
		String[][] parameters = new String[1][2];	
		parameters[0][0] = "GetWeatherResult";
		parameters[0][1] = "ResultFromDoubleTest";

		
		// GJSResult
		GJSResult globalWeatherHandler = new GJSResult();
		GJSResult gwStHandler = new GJSResult();
		
		try {
			System.out.println("** Test GlobalWeather-Server **");
			System.out.println("CALL: generateServerWS");
			GJS.generateServerWS("http://www.webservicex.net/globalweather.asmx?WSDL", 
					"GetWeather", 
					"GlobalWeatherSoap",
					"GlobalWeather-Server",
					"C:/Temp/globalweather-server",
					"report.xml",
					"standard",
					10000,
					parameters, 
					globalWeatherHandler);
		} catch (Exception e) {
			globalWeatherHandler.setError(e.getMessage());
		}

		int i=0;
		System.out.println("waiting for test1:");
		while( i<5 ) {
			try {
				GJS.getStatus("GlobalWeather-Server", gwStHandler);
								
				Thread.sleep(1000);
				System.out.println(" " + ++i + " " + gwStHandler.getValue());
				
				if ( (gwStHandler != null) && (gwStHandler.getValue() != null) && !gwStHandler.getValue().equals("Initialization") )
					i = 10;

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println();
		System.out.println("End GJSTester-Server: " + globalWeatherHandler.getDescription());		
		
		System.out.println("****************************");
		System.out.println();


		if (globalWeatherHandler.isSuccess()) {
		
		
			String[][] parametersClient = new String[2][2];
			
			parametersClient[0][0] = "CountryName";
			parametersClient[0][1] = "Italy";
	
			parametersClient[1][0] = "CityName";
			parametersClient[1][1] = "Bologna";
	
			GJSResult myHandler2 = new GJSResult();
			
			System.out.println("** Test2 GlobalWeather-Client **");
			System.out.println("CALL: generateClientWS");
			System.out.println("Test Client for WS: " + globalWeatherHandler.getValue());
			GJS.generateClientWS(globalWeatherHandler.getValue(), 
						"GetWeather", 
						"GlobalWeatherSoap",
						"GlobalWeather-Client",
						"C:/Temp/globalweather-client",
						"report.xml",
						"standard",
						parametersClient, 
						myHandler2);
		
			i=0;
			gwStHandler = new GJSResult();
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
			
			System.out.println("END GlobalWeather-Client: " + myHandler2.getDescription());		
			System.out.println("END GlobalWeather-Client: " + myHandler2.getType());	
			System.out.println();
		}
		else
		{
			System.out.println("Test1 returned a NOT success result: Test2 skipped.");
			System.out.println();
		}
		
		GJS.stop("GlobalWeather-Server", gwStHandler);
		GJS.stop("GlobalWeather-Client", gwStHandler);
		
		System.out.println("** END GlobalWeather DoubleTest **");
		System.out.println("**********************************");
		System.out.println("**********************************");
		System.out.println();
	}

}
