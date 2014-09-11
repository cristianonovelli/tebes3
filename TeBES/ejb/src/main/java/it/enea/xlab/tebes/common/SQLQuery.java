package it.enea.xlab.tebes.common;


public class SQLQuery {

	// USER
	public static final String SELECT_USERS = "SELECT u FROM User AS u";
	public static final String WHERE_EMAIL = " WHERE u.eMail = ?";
	public static final String SELECT_USERIDS = "SELECT u.id FROM User AS u";
	
	// ROLE
	public static final String SELECT_ROLES = "SELECT r FROM Role AS r";
	public static final String WHERE_LEVEL = " WHERE r.level = ?";
	public static final String SELECT_ROLEID = "SELECT r.id FROM Role AS r";
	
	public static final String SELECT_ROLE_BYUSERID = "SELECT role FROM User user WHERE user.id =:userId";
	public static final String SELECT_ROLE_BYNAME = "SELECT role FROM Role role WHERE role.name =:roleName";
	
	// GROUP
	public static final String SELECT_GROUPS = "SELECT g FROM usergroup AS g";
	public static final String SELECT_GROUPID = "SELECT g.id FROM usergroup AS g";
	public static final String SELECT_GROUP_BYNAME = "SELECT g FROM usergroup g WHERE g.name =:groupName";
	
	public static final String SELECT_TESTPLANS = "SELECT t FROM TestPlan AS t";
	
	// ACTION
	public static final String SELECT_ACTIONID = "SELECT a.id FROM Action AS a";
	
	// SUT
	public static final String SELECT_SUTS = "SELECT s FROM SUT AS s";
	public static final String WHERE_NAME = " WHERE s.name = ?";
	
		// SESSION
	public static final String SELECT_SESSIONS = "SELECT s FROM Session AS s";
	public static final String WHERE_USERID = " WHERE s.userId = ?";
	public static final String SELECT_SESSIONID = "SELECT s.id FROM Session AS s";
	public static final String WHERE_USER = " WHERE s.user = ?"; 
	
	
	// FILE MANAGER
	public static final String SELECT_FILES = "SELECT f FROM FileStore AS f";
	public static final String WHERE_REFID = " WHERE f.fileRefId = ?";
	public static final String WHERE_TYPE = " WHERE f.type = ?";
	//public static final String WHERE_SOURCE = " WHERE f.source = ?";
	
	public static final String SELECT_TEXTS = "SELECT t FROM TextStore AS t";
	public static final String WHERE_REFID2 = " WHERE t.refId = ?";
	
}
