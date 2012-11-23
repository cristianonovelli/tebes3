package it.enea.xlab.tebes.common;

public class Properties {

	// URL
	public static final String TEBES_URL_HOME = "http://www.ubl-italia.org/TeBES/";
	
	// User and Test Plan
	//public static final String TeBES_LOCAL_HOME = "C:/Java/workspace-indigo2/TeBES/war/src/main/webapp/";
	public static final String TeBES_ARTIFACTS_LOCAL_HOME = "C:/Java/jboss-4.2.3.GA/server/default/data/TeBES/";
	public static final String TeBES_USER = "users/IT-12345678909/";	
	public static final String TeBES_TESTPLAN_DIR = "testplans/";
	public static final String TeBES_TESTPLAN_FILENAME = "TP_IT-12345678909_2012-06-04T184300.xml";
	
	public static final String TeBES_TESTPLAN_ABSFILENAME = TeBES_ARTIFACTS_LOCAL_HOME + TeBES_USER + TeBES_TESTPLAN_DIR + TeBES_TESTPLAN_FILENAME;
	
	//public static final String TEMP_DOC1 = "IT-12345678909";
	//public static final String TEMP_DOC2 = "IT-12345678909";
	
	// TAML
	public static final String TAML_XMLSCHEMA = TeBES_ARTIFACTS_LOCAL_HOME + "xmlschemas/TAML/testAssertionMarkupLanguage.xsd";

}
