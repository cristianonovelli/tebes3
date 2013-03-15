package it.enea.xlab.tebes.common;


public class Constants {

	public static final String PERSISTENCE_CONTEXT = "TeBESPersistenceLayer";
	
	
	// standard role
	public static final String STANDARD_ROLE_NAME = "standard_user";
	public static final String STANDARD_ROLE_DESCRIPTION = "Standard User Role: he can execute a test plan but he can't create/edit it";
	public static final int STANDARD_ROLE_LEVEL = 1;
	// standard role
	public static final String ADVANCED_ROLE_NAME = "advanced_user";
	public static final String ADVANCED_ROLE_DESCRIPTION = "Advanced User Role: he can create/edit/execute a test plan";
	public static final int ADVANCED_ROLE_LEVEL = 2;
	// standard role
	public static final String ADMIN_ROLE_NAME = "admin_user";
	public static final String ADMIN_ROLE_DESCRIPTION = "System Administrator Role: he is an advanced user and can add/modify test suites";
	public static final int ADMIN_ROLE_LEVEL = 3;
	// standard role
	public static final String SUPERUSER_ROLE_NAME = "super_user";
	public static final String SUPERUSER_ROLE_DESCRIPTION = "Super User Role: he has whole power and permissions on test bed platform";
	public static final int SUPERUSER_ROLE_LEVEL = 4;

	// group
	public static final String XLAB_GROUP_NAME = "xlab1";
	public static final String XLAB_GROUP_DESCRIPTION = "X-Lab stuff";	
	
	
	// User1
	public static final String USER1_EMAIL = "angelo.frascella@enea.it";
	public static final String USER1_PASSWORD = "xangelo";
	// User2
	public static final String USER2_EMAIL1 = "arianna.brutti@enea.it";
	public static final String USER2_EMAIL2 = "arianna2@enea.it";	
	public static final String USER2_PASSWORD1 = "xpiero";	
	public static final String USER2_PASSWORD2 = "xarianna";	

	// SUT description
	public static final String SUT_DESCRIPTION = "Static SUT description";
	
	// SUT Types
	public static final String SUT_TYPE1_DOCUMENT = "document";
	public static final String SUT_TYPE2_TRANSPORT = "transport";
	public static final String SUT_TYPE3_PROCESS = "process";

	// SUT Interactions
	// interazioni possibili: website (upload), email, webservice, ebxmlcpa
	public static final String INTERACTION_WEBSITE = "website";
	public static final String INTERACTION_EMAIL = "email";
	public static final String INTERACTION_WS = "webservice";
	public static final String INTERACTION_CPA = "ebxmlcpa";
	
	// Supported Test Definition Language Types
	public static final String TAML = "taml";
	
	// Supported Definition Language Types
	public static final String XML = "xml";
	public static final String UBL = "ubl";
	public static final String EBMS = "ebms";
	public static final String EBBP = "ebbp";
	
	// References of schema languages
	public static final String XPATH = "http://www.w3.org/TR/xpath20/";
	public static final String SCHEMATRON = "http://purl.oclc.org/dsdl/schematron";
	public static final String UBLSCHEMA = "http://docs.oasis-open.org/ubl/os-UBL-2.0/xsd/maindoc/UBL-Invoice-2.0.xsd";
	
	// Test Artifact Types
	public static final String TA = "TestAssertion";
	public static final String TC = "TestCase";
	public static final String TS = "TestSuite";
	

	
	// Other Values
	public static final String MANDATORY = "mandatory";
}
