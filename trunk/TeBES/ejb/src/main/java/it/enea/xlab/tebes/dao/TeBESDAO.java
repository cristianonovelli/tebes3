package it.enea.xlab.tebes.dao;

import java.io.FileNotFoundException;

import org.xlab.file.XLabFileManager;

import it.enea.xlab.tebes.common.Paths;
import it.enea.xlab.tebes.common.PropertiesUtil;

/**
 * I metodi sviluppati in questo File Manager potrebbero venire sostituiti con un altro tipo di DAO basato su Database
 * 
 * P.E. TAMLDOM potrebbe essere creato usando un inputstream ricavato dalla lettura della banca dati, 
 * o potrebbe essere creato un costruttore apposito
 */
public class TeBESDAO {

	
	public static String url2localLocation(String urlLocation) throws FileNotFoundException {
		
		String localLocation = null;
		String relativeLocation = null;

		// Recognize TeBES home URL 
		if (urlLocation.startsWith(PropertiesUtil.getTeBESURL())) {
			
			// Get relative URL
			relativeLocation = urlLocation.substring(PropertiesUtil.getTeBESURL().length(), urlLocation.length());
			
			// Add file system local path
			localLocation = PropertiesUtil.getArtifactsPath().concat(relativeLocation);
			
			// Verificare che il file o la directory esista
			if( !XLabFileManager.isFileOrDirectoryPresent(localLocation) ) 
				throw new FileNotFoundException("Conversion from URL to Local path failed: maybe Test Plan specifies a file not present in TeBES.");
				
			
		}
		else
			localLocation = urlLocation;
		
		return localLocation;
	}
}


