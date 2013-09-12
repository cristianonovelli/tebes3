
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
	 * Get XML Artifacts Path
	 * URL of TeBES platform root. The URL has to end with the root directory "TeBES/"
	 */
	public static String getArtifactsPath() {
		
		String artifactsPath = getConfiguration().getString("artifacts.path");
		
		artifactsPath = checkFinalSlash(artifactsPath);

		return artifactsPath;
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
	 * Get PROPERTIES
	 */	
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
	
	public static String getUserDirProperty() {
		
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
	 * Get SUPERUSER Dirs
	 */	
	public static String getSuperUserDir() {
		
		String result;
		
		String superuserId = getSuperUserIdProperty();
		
		result = PropertiesUtil.getUsersDir().concat(superuserId);
		
		result = checkFinalSlash(result);
		
		return result;
	}	
	
	public static String getSuperUserTestPlansDir() {
		
		String result;
		
		String testPlansDir = getTestPlansDirProperty();
		
		result = PropertiesUtil.getSuperUserDir().concat(testPlansDir);
		
		result = checkFinalSlash(result);
		
		return result;
	}	
	
	public static String getSuperUserDocsDir() {
		
		String result;
		
		String docsDir = getDocsDirProperty();
		
		result = PropertiesUtil.getSuperUserDir().concat(docsDir);
		
		result = checkFinalSlash(result);
		
		return result;
	}	

	public static String getSuperUserReportsDir() {
		
		String result;
		
		String reportsDir = getReportsDirProperty();
		
		result = PropertiesUtil.getSuperUserDir().concat(reportsDir);
		
		result = checkFinalSlash(result);
		
		return result;
	}	
	
	public static String getSuperUserReportAbsFileName() {
		
		String result;	
		
		String reportFileName = getSuperUserReportProperty();
		
		result = PropertiesUtil.getSuperUserReportsDir().concat(reportFileName);
		
		return result;
	}
	
	
	
	/**
	 * Get USERS Dir
	 */	
	public static String getUsersDir() {

		String result;
		
		String usersDirProperty = getUserDirProperty();
		
		result = PropertiesUtil.getArtifactsPath().concat(usersDirProperty);
		
		result = checkFinalSlash(result);
		
		return result;
	}
	
	
	/**
	 * Get USER Dirs
	 */	
	public static String getUserDir(String userDir) {
		
		String result = getUsersDir().concat(userDir);	
		result = checkFinalSlash(result);
		
		return result;
	}
	
	public static String getUserDocsAbsDir(Long id) {
		
		String result = getUserDir(id.toString());
		
		String docsDir = getDocsDirProperty();
		result = result.concat(docsDir);
		result = checkFinalSlash(result);
		
		return result;
	}
		
	public static String getUserReportsDir(Long id) {
		
		String result = getUserDir(id.toString());
		
		String reportsDir = getReportsDirProperty();
		result = result.concat(reportsDir);
		result = checkFinalSlash(result);
		
		return result;
	}

	
	
	/**
	 * Get TAML XML Schema testAssertionMarkupLanguage.xsd
	 */
	public static String getTAMLXMLSchema() {
		
		String artifactsRootPath = PropertiesUtil.getArtifactsPath();
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
	
	
	
	/*public static String getSuperUserReport1AbsPathName() {
	
	
	String result = PropertiesUtil.getSuperUserReportsDir();	
	
	String reportFileName = getConfiguration().getString("superuser.report1");
	
	result = result.concat(reportFileName);
	
	return result;
}*/
	/*	public static String getSuperUserTestPlan1AbsPathName() {
	
	String absTestPlanDir = PropertiesUtil.getSuperUserTestPlansDir();	
	String testPlan1FileName = getConfiguration().getString("superuser.testplan1");
	
	String result = absTestPlanDir.concat(testPlan1FileName);
	
	return result;
}	


public static Long getSuperUserTestPlan1Id() {
	
	return  getConfiguration().getLong("superuser.testplan1id");
}	*/

}

