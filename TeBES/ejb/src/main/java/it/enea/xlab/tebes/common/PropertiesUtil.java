
package it.enea.xlab.tebes.common;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

/**
 * @authors fulvio di marco, cristiano novelli
 * 
 * <p>Copyright 2009-2010 Epoca srl</p>
 * <p>Copyright 2012 Enea</p>
 */
public class PropertiesUtil {
	
	private static String PROPERTIES_FILE = "tebes.properties";
	private static org.apache.commons.configuration.Configuration configuration;
	private static Logger logger = Logger.getLogger(PropertiesUtil.class.getName());

	
	
	/**
	 * Get CONFIGURATION
	 */	
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
	 * Get PROPERTIES
	 */	
	public static String getArtifactsDirProperty() {

		return getConfiguration().getString("artifacts.dir");
	}

	public static String getArtifactsLocationProperty() {

		return getConfiguration().getString("artifacts.location");
	}
	
	public static String getSuperUserNameProperty() {

		return getConfiguration().getString("superuser.name");
	}	
	
	public static String getSuperUserSurnameProperty() {

		return getConfiguration().getString("superuser.surname");
	}	
	
	public static String getSuperUserEmailProperty() {

		return getConfiguration().getString("superuser.email");
	}	
	
	public static String getSuperUserPasswordProperty() {

		return getConfiguration().getString("superuser.password");
	}	
	
	public static String getSuperUserIdProperty() {

		return getConfiguration().getString("superuser.id");
	}			
	
	public static String getSuperUserReportProperty() {
		
		return getConfiguration().getString("superuser.report");
	}
	
	public static String getUsersDirProperty() {
		
		return getConfiguration().getString("users.dir");
	}
	
	public static String getTestPlansDirProperty() {

		return getConfiguration().getString("testplans.dir");
	}			
	
	public static String getDocsDirProperty() {
		
		return getConfiguration().getString("docs.dir");
	}

	public static String getReportsDirProperty() {
		
		return getConfiguration().getString("reports.dir");
	}
	
	
	
	/**
	 * Get TeBES home final URL
	 */
	public static String getTeBESURL() {
		
		String tebesURL = getConfiguration().getString("tebes.url");	
		tebesURL = checkFinalSlash(tebesURL);

		return tebesURL;
	}
	
	
	
	/**
	 * Get XML Artifacts Path
	 * URL of TeBES platform root. The URL has to end with the root directory "TeBES/"
	 */
	public static String getArtifactsDirPath() {
		
		String artifactsPath = getArtifactsLocationProperty();	
		artifactsPath = checkFinalSlash(artifactsPath);
		
		artifactsPath = artifactsPath.concat(getArtifactsDirProperty());
		artifactsPath = checkFinalSlash(artifactsPath);
		
		return artifactsPath;
	}
		
	
	
	/**
	 * Get SUPERUSER Dirs
	 */	
	public static String getSuperUserDirPath() {
		
		String result;
		
		String superuserId = getSuperUserIdProperty();
		
		result = PropertiesUtil.getUsersDirPath().concat(superuserId);
		
		result = checkFinalSlash(result);
		
		return result;
	}	
	
	public static String getSuperUserTestPlansDirPath() {
		
		String result;
		
		String testPlansDir = getTestPlansDirProperty();
		
		result = PropertiesUtil.getSuperUserDirPath().concat(testPlansDir);
		
		result = checkFinalSlash(result);
		
		return result;
	}	
	
	public static String getSuperUserDocsDirPath() {
		
		String result;
		
		String docsDir = getDocsDirProperty();
		
		result = PropertiesUtil.getSuperUserDirPath().concat(docsDir);
		
		result = checkFinalSlash(result);
		
		return result;
	}	

	public static String getSuperUserReportsDirPath() {
		
		String result;
		
		String reportsDir = getReportsDirProperty();
		
		result = PropertiesUtil.getSuperUserDirPath().concat(reportsDir);
		
		result = checkFinalSlash(result);
		
		return result;
	}	
	
	public static String getSuperUserReportAbsFileName() {
		
		String result;	
		
		String reportFileName = getSuperUserReportProperty();
		
		result = PropertiesUtil.getSuperUserReportsDirPath().concat(reportFileName);
		
		return result;
	}
	
	
	
	/**
	 * Get USERS Dir
	 */	
	public static String getUsersDirPath() {

		String result;
		
		String usersDirProperty = getUsersDirProperty();
		
		result = PropertiesUtil.getArtifactsDirPath().concat(usersDirProperty);
		
		result = checkFinalSlash(result);
		
		return result;
	}
	
	
	/**
	 * Get USER Dirs
	 */	
	public static String getUserDirPath(Long userId) {
		
		String result = getUsersDirPath().concat(userId.toString());	
		result = checkFinalSlash(result);
		
		return result;
	}
	
	public static String getUserDocsDirPath(Long id) {
		
		String result = getUserDirPath(id);
		
		String docsDir = getDocsDirProperty();
		result = result.concat(docsDir);
		result = checkFinalSlash(result);
		
		return result;
	}

	// Path Relativo (necessario in alcuni validatori)
	public static String getUserDocsRelPath(Long id) {
		
		//String result = getUserDir(id);
		String result = getArtifactsDirProperty();
		result = checkFinalSlash(result);
		
		result = result.concat(getUsersDirProperty());
		result = checkFinalSlash(result);
		
		result = result.concat(id.toString());
		result = checkFinalSlash(result);		

		result = result.concat(getDocsDirProperty());
		result = checkFinalSlash(result);	
		
		return result;
	}
	
	public static String getUserReportsDirPath(Long id) {
		
		String result = getUserDirPath(id);
		
		String reportsDir = getReportsDirProperty();
		result = result.concat(reportsDir);
		result = checkFinalSlash(result);
		
		return result;
	}

	
	
	/**
	 * Get TAML XML Schema testAssertionMarkupLanguage.xsd
	 */
	public static String getTAMLXMLSchema() {
		
		String artifactsRootPath = PropertiesUtil.getArtifactsDirPath();
		String xmlschemaDir = getConfiguration().getString("xmlschemas.dir");
		String tamlDir = getConfiguration().getString("taml.dir");
		String tamlXMLSchema = getConfiguration().getString("taml.xmlschema");
		
		String result = artifactsRootPath.concat(xmlschemaDir).concat(Constants.SLASH).concat(tamlDir).concat(Constants.SLASH).concat(tamlXMLSchema);
		
		return result;
	}

	
	
	/**
	 * Get JNDI Properties
	 */
	 public static String getJndiHost() {
		 
         return getConfiguration().getString("jndi.host");   
	 }
 
	 public static String getJndiPort() {
		 
         return getConfiguration().getString("jndi.port");  
	 }



	/**
	 * Check final SLASH
	 */	
	private static String checkFinalSlash(String path) {

		if (!path.endsWith(Constants.SLASH))
			return path.concat(Constants.SLASH);
		else
			return path;
	}
	

}

