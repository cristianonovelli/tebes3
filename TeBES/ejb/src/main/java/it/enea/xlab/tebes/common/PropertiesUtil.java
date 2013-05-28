
package it.enea.xlab.tebes.common;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

/**
 * @author fulvio
 * @author cristiano novelli
 * 
 * <p>Copyright 2009-2010 Epoca srl</p>
 * <p>Copyright 2012 Enea</p>
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
		
		if (!artifactsPath.endsWith(SLASH))
			artifactsPath = artifactsPath.concat(SLASH);

		return artifactsPath;
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
	
	
	public static String getUsersDir() {

		String artifactsRootPath = PropertiesUtil.getArtifactsPath();
		String usersDir = getConfiguration().getString("users.dir");
		
		String result = artifactsRootPath.concat(usersDir);
		
		if (!result.endsWith(SLASH))
			result = result.concat(SLASH);
		
		return result;
	}
	
	public static String getUser1Email() {

		return getConfiguration().getString("superuser.email");
	}	

	public static String getUser1Password() {

		return getConfiguration().getString("superuser.password");
	}	
	
	public static String getSuperUserDir() {
		
		String absUsersDir = PropertiesUtil.getUsersDir();	
		String systemId = getConfiguration().getString("superuser.id");
		
		String result = absUsersDir.concat(systemId);
		
		if (!result.endsWith(SLASH))
			result = result.concat(SLASH);
		
		return result;
	}	
	
	
	public static String getSuperUserTestPlanDir() {
		
		String absUser1Dir = PropertiesUtil.getSuperUserDir();
		String testPlansDir = getConfiguration().getString("testplans.dir");
		
		String result = absUser1Dir.concat(testPlansDir);
		
		if (!result.endsWith(SLASH))
			result = result.concat(SLASH);
		
		return result;
	}	
	
	
	public static String getSuperUserTestPlan1AbsPathName() {
		
		String absTestPlanDir = PropertiesUtil.getSuperUserTestPlanDir();	
		String testPlan1FileName = getConfiguration().getString("superuser.testplan1");
		
		String result = absTestPlanDir.concat(testPlan1FileName);
		
		return result;
	}	

	
	public static Long getSuperUserTestPlan1Id() {
		
		return  getConfiguration().getLong("superuser.testplan1id");
	}	
	
	
	/**
	 * Get TAML XML Schema testAssertionMarkupLanguage.xsd
	 */
	public static String getTAMLXMLSchema() {
		
		String artifactsRootPath = PropertiesUtil.getArtifactsPath();
		String xmlschemaDir = getConfiguration().getString("xmlschemas.dir");
		String tamlDir = getConfiguration().getString("taml.dir");
		String tamlXMLSchema = getConfiguration().getString("taml.xmlschema");
		
		String result = artifactsRootPath.concat(xmlschemaDir).concat(SLASH).concat(tamlDir).concat(SLASH).concat(tamlXMLSchema);
		
		return result;
	}

	
	/**
	 * Get Report Dir
	 */
	public static String getSuperUserReportDir() {
		
		String absUser1Dir = PropertiesUtil.getSuperUserDir();
		String reportsDir = getConfiguration().getString("reports.dir");
		
		String result = absUser1Dir.concat(reportsDir);
		
		if (!result.endsWith(SLASH))
			result = result.concat(SLASH);
		
		return result;
	}	
	
	/**
	 * Get Report PathName
	 */
	public static String getSuperUserReportAbsPathName() {
		
		
		String result = PropertiesUtil.getSuperUserReportDir();	
		
		String reportFileName = getConfiguration().getString("superuser.report");
		
		result = result.concat(reportFileName);
		
		return result;
	}
}

