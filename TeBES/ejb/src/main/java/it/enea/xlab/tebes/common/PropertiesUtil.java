
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
		// artifacts.location=C:/Java/jboss-4.2.3.GA/server/default/
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
	
	public static String getFileManagerProperty() {
		
		return getConfiguration().getString("file.manager");
	}
	
	
	/**
	 * Get TeBES home final URL 
	 */
	public static String getTeBESURL() {
		
		String tebesURL = getConfiguration().getString("tebes.url");	
		tebesURL = checkFinalSlash(tebesURL);

		return tebesURL;
	}
	
	public static String getTeBESArtifactsURL() {
		
		String tebesURL = getConfiguration().getString("artifacts.url");	
		tebesURL = checkFinalSlash(tebesURL);

		return tebesURL;
	}

	public static String getGJSURL() {
		
		String tebesURL = getConfiguration().getString("gjs.url");	
		tebesURL = checkFinalSlash(tebesURL);

		return tebesURL;
	}
	
	/**
	 * Get TeBES home final URL
	 */
	public static String getTeBESFileURL(String fileIdRef, String userId) {
		
		String tebesURL = getTeBESURL();
		tebesURL = checkFinalSlash(tebesURL);
		
		tebesURL = tebesURL.concat(getFileManagerProperty()).concat("?userId=" + userId + "fileIdRef=").concat(fileIdRef);

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
		System.out.println("getSuperUserDirPath1");
		String superuserId = getSuperUserIdProperty();
		System.out.println("getSuperUserDirPath2: " + superuserId);
		result = PropertiesUtil.getUsersDirPath().concat(superuserId);
		System.out.println("getSuperUserDirPath3: " + result);
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
		System.out.println("getSuperUserReportsDirPath1");
		String reportsDir = getReportsDirProperty();
		System.out.println("getSuperUserReportsDirPath2: " + reportsDir);
		result = PropertiesUtil.getSuperUserDirPath().concat(reportsDir);
		System.out.println("getSuperUserReportsDirPath3:" + result);
		result = checkFinalSlash(result);
		
		return result;
	}	
	
	public static String getSuperUserReportAbsFileName() {
		
		String result;	
		System.out.println("getSuperUserReportAbsFileName1");
		String reportFileName = getSuperUserReportProperty();
		System.out.println("getSuperUserReportAbsFileName2: " + reportFileName);
		result = PropertiesUtil.getSuperUserReportsDirPath().concat(reportFileName);
		System.out.println("getSuperUserReportAbsFileName3: " + result);
		
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

	// Path Relativo (necessario in alcuni validatori e per costruire l'URL)
	// p.es. users/1/docs/
	public static String getUserDocsRelPath(Long userId) {
		
		String result = getUsersDirProperty();
		result = checkFinalSlash(result);
		
		result = result.concat(userId.toString());
		result = checkFinalSlash(result);		

		result = result.concat(getDocsDirProperty());
		result = checkFinalSlash(result);	
		
		return result;
	}


	// Path Relativo (necessario per fornire l'URL)
	// p.es. users/1/reports/
	private static String getUserReportRelPath(Long userId) {
		
		String result = getUsersDirProperty();
		result = checkFinalSlash(result);
		
		result = result.concat(userId.toString());
		result = checkFinalSlash(result);		

		result = result.concat(getReportsDirProperty());
		result = checkFinalSlash(result);	
		
		return result;
	}
	
	public static String getUserReportURL(Long userId, String reportFileName) {
		
		String result = getTeBESURL();
		result = checkFinalSlash(result);
		
		result = result.concat(getUserReportRelPath(userId));
		result = checkFinalSlash(result);		

		result = result.concat(reportFileName);
		
		return result;
	}
		
	// Path Relativo (necessario per fornire l'URL)
	// p.es. users/1/testplans/
	private static String getTestPlanRelPath(Long userId) {
		
		String result = getUsersDirProperty();
		result = checkFinalSlash(result);
		
		result = result.concat(userId.toString());
		result = checkFinalSlash(result);		

		result = result.concat(getTestPlansDirProperty());
		result = checkFinalSlash(result);	
		
		return result;
	}
	
	public static String getTestPlansDirPath(Long id) {
		
		String result = getUserDirPath(id);
		
		String testPlanDir = getTestPlansDirProperty();
		result = result.concat(testPlanDir);
		result = checkFinalSlash(result);
		
		return result;
	}
	
	public static String getTestPlanURL(Long userId, String testPlanFileName) {
		
		String result = getTeBESURL();
		result = checkFinalSlash(result);
		
		result = result.concat(getTestPlanRelPath(userId));
		result = checkFinalSlash(result);		

		result = result.concat(testPlanFileName);
		
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





	public static String getUserLogsDirPath(Long id) {

		String result = getUserDirPath(id);
		
		String logsDir = getLogsDirProperty();
		result = result.concat(logsDir);
		result = checkFinalSlash(result);
		
		return result;
	}





	public static String getLogsDirProperty() {
		
		return getConfiguration().getString("logs.dir");
	}
	
	public static String getCacheDirProperty() {
		
		return getConfiguration().getString("cache.dir");
	}
	
	
	public static String getCacheDirPath() {
		
		String result = PropertiesUtil.getArtifactsDirPath().concat(getCacheDirProperty());
		
		result = checkFinalSlash(result);
		
		return result;
	}
}

