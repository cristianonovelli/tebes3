package it.enea.xlab.tebes.entity;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Report implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final String DRAFT_STATE = "draft";
	private static final String FINAL_STATE = "final";

    /*<!-- Global Result can take the following 5 values: 
		"undefined": result is undefined because no action has been executed; 
		"success": when every action finished with success result;
		"warning": when there is at least an action with warning result and no action with failure or error result;
		"failure": when there is at least an action with failure result and no action with error result; 
		"error": there is at least an action with error result, this means that a system error happened -->  */   
	private static final String UNDEFINED_RESULT = "undefined";
	private static final String SUCCESSFUL_RESULT = "successful";
	private static final String WARNING_RESULT = "warning";
	private static final String FAILURE_RESULT = "failure";
	private static final String ERROR_RESULT = "error";
	
	private static final String REPORTNAME_PREFIX = "TR-";
	private static final String REPORTDESCRIPTION = "Report ";
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	private String name;
	private String description;
	private Long sessionID;
	private String datetime;
	private String state;

	@OneToOne(cascade=CascadeType.ALL)
	private TestResult tempResult;
	
	@Column(length=99999) 
	private String fullDescription;

	@Column(length=99999) 
	private String xml;
	
	private boolean partialResultSuccessfully;
	private boolean finalResultSuccessfully;
	
	
	
	public Report() {
		
		this.setState(DRAFT_STATE);
		this.setFullDescription("");
		this.setFinalResultSuccessfully(true);
		this.setTempResult(null);
	}

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public static String getDraftState() {
		return DRAFT_STATE;
	}


	public static String getFinalState() {
		return FINAL_STATE;
	}


	public String getTestResult() {
		return fullDescription;
	}


	public String getFullDescription() {
		return fullDescription;
	}


	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	public void addToFullDescription(String furtherDescription) {
		this.fullDescription = fullDescription.concat(furtherDescription);
	}

	public boolean isPartialResultSuccessfully() {
		return partialResultSuccessfully;
	}


	public void setPartialResultSuccessfully(boolean partialResultSuccessfully) {
		this.partialResultSuccessfully = partialResultSuccessfully;
	}


	public boolean isFinalResultSuccessfully() {
		return finalResultSuccessfully;
	}


	public void setFinalResultSuccessfully(boolean finalResultSuccessfully) {
		this.finalResultSuccessfully = finalResultSuccessfully;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Long getSessionID() {
		return sessionID;
	}


	public void setSessionID(Long sessionID) {
		this.sessionID = sessionID;
	}


	public String getDatetime() {
		return datetime;
	}


	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}


	public String getXml() {
		return xml;
	}


	public void setXml(String xml) {
		this.xml = xml;
	}


	public static String getReportnamePrefix() {
		return REPORTNAME_PREFIX;
	}


	public static String getReportdescription() {
		return REPORTDESCRIPTION;
	}


	public static String getUndefinedResult() {
		return UNDEFINED_RESULT;
	}


	public static String getWarningResult() {
		return WARNING_RESULT;
	}


	public static String getSuccessfulResult() {
		return SUCCESSFUL_RESULT;
	}


	public static String getFailureResult() {
		return FAILURE_RESULT;
	}


	public static String getErrorResult() {
		return ERROR_RESULT;
	}


	public TestResult getTempResult() {
		return tempResult;
	}


	public void setTempResult(TestResult result) {
		this.tempResult = result;
	}

}

