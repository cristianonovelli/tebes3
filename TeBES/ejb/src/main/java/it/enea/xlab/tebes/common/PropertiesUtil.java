
package it.enea.xlab.tebes.common;

import java.io.FileNotFoundException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.xlab.file.XLabFileManager;

/**
 * @author fulvio
 * @author cristiano novelli
 * 
 * <p>Copyright 2009-2010 Epoca srl</p>
 * <p>Copyright 2012 Enea</p>
 *
 *
 */
public class PropertiesUtil {
	
	private static String PROPERTIES_FILE = "tebes.properties";
	private static org.apache.commons.configuration.Configuration configuration;
	private static Logger logger = Logger.getLogger(PropertiesUtil.class.getName());
	
	private static String SLASH = "/";
	
	private static org.apache.commons.configuration.Configuration getConfiguration() {
		if (configuration == null) {
			try {
				configuration = new PropertiesConfiguration(PROPERTIES_FILE);
			} catch (ConfigurationException e) {
				logger.error(e.getMessage());
			}
		}
		return configuration;
	}
	
	/**
	 * Get XML Artifacts Path
	 * URL of TeBES platform root. The URL has to end with the root directory "TeBES/"
	 */
	public static String getArtifactsPath() {
		
		String artifactsPath = getConfiguration().getString("artifacts.path");
		
		if (artifactsPath.endsWith("TeBES"))
			artifactsPath = artifactsPath.concat(SLASH);

		if (artifactsPath.endsWith("/TeBES/"))
			return artifactsPath;
		else
			return null;
	}
	
	/**
	 * Get TeBES home final URL
	 */
	public static String getTeBESURL() {
		
		String tebesURL = getConfiguration().getString("tebes.url");
		
		if (!tebesURL.endsWith(SLASH))
			tebesURL = tebesURL.concat(SLASH);

		return tebesURL;
	}
	
	
	public static String getUsersDir() throws FileNotFoundException {

		String artifactsRootPath = PropertiesUtil.getArtifactsPath();
		String usersDir = getConfiguration().getString("users.dir");
		
		String result = artifactsRootPath.concat(usersDir);
		
		if (!result.endsWith(SLASH))
			result = result.concat(SLASH);
		
		if (XLabFileManager.isFileOrDirectoryPresent(result))
			return result;
		else 
			throw new FileNotFoundException();
	}
	
	public static String getUser1Dir() throws FileNotFoundException {
		
		String absUsersDir = PropertiesUtil.getUsersDir();	
		String user1Id = getConfiguration().getString("user1.id");
		
		String result = absUsersDir.concat(user1Id);
		
		if (!result.endsWith(SLASH))
			result = result.concat(SLASH);
		
		if (XLabFileManager.isFileOrDirectoryPresent(result))
			return result;
		else 
			throw new FileNotFoundException();
	}	
	
	public static String getTestPlanDir() throws FileNotFoundException {
		
		String absUser1Dir = PropertiesUtil.getUser1Dir();
		String testPlansDir = getConfiguration().getString("testplans.dir");
		
		String result = absUser1Dir.concat(testPlansDir);
		
		if (!result.endsWith(SLASH))
			result = result.concat(SLASH);
		
		if (XLabFileManager.isFileOrDirectoryPresent(result))
			return result;
		else 
			throw new FileNotFoundException();
	}	
	
	public static String getTestPlan1AbsPathName() throws FileNotFoundException {
		
		String absTestPlanDir = PropertiesUtil.getTestPlanDir();	
		String testPlan1FileName = getConfiguration().getString("user1.testplan");
		
		String result = absTestPlanDir.concat(testPlan1FileName);
		
		
		if (XLabFileManager.isFileOrDirectoryPresent(result))
			return result;
		else 
			throw new FileNotFoundException();
	}	

	public static String getTestPlanIdOfUser1() throws FileNotFoundException {
		
		return  getConfiguration().getString("user1.testplanid");
	}	
	
	/**
	 * Get TAML XML Schema testAssertionMarkupLanguage.xsd
	 * 
	 * @throws FileNotFoundException
	 */
	public static String getTAMLXMLSchema() throws FileNotFoundException {
		
		String artifactsRootPath = PropertiesUtil.getArtifactsPath();
		String xmlschemaDir = getConfiguration().getString("xmlschemas.dir");
		String tamlDir = getConfiguration().getString("taml.dir");
		String tamlXMLSchema = getConfiguration().getString("taml.xmlschema");
		
		String result = artifactsRootPath.concat(xmlschemaDir).concat(SLASH).concat(tamlDir).concat(SLASH).concat(tamlXMLSchema);
		
		if (XLabFileManager.isFileOrDirectoryPresent(result))
			return result;
		else 
			throw new FileNotFoundException();
	}
	
}
