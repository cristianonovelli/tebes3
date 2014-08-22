package it.enea.xlab.tebes.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.xlab.file.XLabFileManager;

import it.enea.xlab.tebes.common.PropertiesUtil;

/**
 * I metodi sviluppati in questo File Manager potrebbero venire sostituiti con un altro tipo di DAO basato su Database
 * 
 * P.E. TAMLDOM potrebbe essere creato usando un inputstream ricavato dalla lettura della banca dati, 
 * o potrebbe essere creato un costruttore apposito
 */
public class TeBESDAO {

	
	public static String url2localLocation(String urlLocation) throws FileNotFoundException {
		
		System.out.println("TeBESDAO.url2localLocation");
		System.out.println("urlLocation: " + urlLocation);
		
		String localLocation = null;
		String relativeLocation = null;

		// Recognize TeBES home URL 
		if (urlLocation.startsWith(PropertiesUtil.getTeBESURL())) {
			
			// Get relative URL
			relativeLocation = urlLocation.substring(PropertiesUtil.getTeBESURL().length(), urlLocation.length());
			
			// Add file system local path
			localLocation = PropertiesUtil.getArtifactsDirPath().concat(relativeLocation);
			System.out.println("localLocation: " + localLocation);
			
		}
		else 
			localLocation = urlLocation;
		
		// Verificare che il file o la directory esista
		if( !XLabFileManager.isFileOrDirectoryPresent(localLocation) ) 
			throw new FileNotFoundException("Conversion from URL to Local path failed: maybe Test Plan specifies a file not present in TeBES.");
			
		return localLocation;
	}
	
	
	public static String location2publication(String location) {
	
		String publication = null;
		String relativeLocation = null;
		
		if (location.startsWith(PropertiesUtil.getArtifactsDirPath())) {
			
			// Get relative location
			relativeLocation = location.substring(PropertiesUtil.getArtifactsDirPath().length(), location.length());
			
			// Add file system local path
			publication = PropertiesUtil.getTeBESURL().concat(relativeLocation);			
		}
		
		return publication;
	}

}


