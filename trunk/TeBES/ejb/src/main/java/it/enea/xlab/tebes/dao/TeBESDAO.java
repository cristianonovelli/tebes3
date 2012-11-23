package it.enea.xlab.tebes.dao;

import org.xlab.file.XLabFileManager;

import it.enea.xlab.tebes.common.Properties;

/**
 * I metodi sviluppati in questo File Manager potrebbero venire sostituiti con un altro tipo di DAO basato su Database
 * 
 * P.E. TAMLDOM potrebbe essere creato usando un inputstream ricavato dalla lettura della banca dati, 
 * o potrebbe essere creato un costruttore apposito
 */
public class TeBESDAO {

	
	public static String url2localLocation(String urlLocation) {
		
		String localLocation = null;
		String relativeLocation = null;
		
		// Recognize TeBES home URL 
		if (urlLocation.startsWith(Properties.TEBES_URL_HOME)) {
			
			// Get relative URL
			relativeLocation = urlLocation.substring(Properties.TEBES_URL_HOME.length(), urlLocation.length());
			
			// Add file system local path
			localLocation = Properties.TeBES_ARTIFACTS_LOCAL_HOME.concat(relativeLocation);
			
			// Verificare che il file o la directory esista
			if( !XLabFileManager.isPresent(localLocation) ) {
				localLocation = null;
				System.out.println("It is NOT present the file or directory related to: " + urlLocation);
			}
		}
		else
			localLocation = urlLocation;
		
		return localLocation;
	}
}


