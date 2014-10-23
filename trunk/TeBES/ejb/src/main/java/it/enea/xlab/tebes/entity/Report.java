package it.enea.xlab.tebes.entity;


import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "report")
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
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	private String name;
	//private String description;
	
	//@OneToMany(mappedBy="report", cascade=CascadeType.ALL)
	//@LazyCollection(LazyCollectionOption.FALSE)
	//private List<ReportDescription> reportDescriptions;	
	
	private Long sessionID;
	private String datetime;
	private String state;
	private String location;
	private String publication;
	private String logLocation;
	
	@OneToOne(cascade=CascadeType.ALL)
	private TestResult tempResult;

	@OneToMany(mappedBy="report", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TestResult> tempResultList;
	
	@Column(length=99999) 
	private String fullDescription;

	@Column(length=99999) 
	private String xml;
	
	private boolean partialResultSuccessfully;
	private boolean finalResultSuccessfully;

	private String globalResult;
	private String atomicResult;

	private String request4Interaction;
	
	
	
	public Report() {
		
		this.setState(DRAFT_STATE);
		this.setFullDescription("");
		this.setFinalResultSuccessfully(true);
		this.setTempResult(null);
		
		this.setAtomicResult(UNDEFINED_RESULT);
		this.setGlobalResult(UNDEFINED_RESULT);
		
		this.setRequest4Interaction(null);		
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


	public String getFullDescription() {
		return fullDescription;
	}


	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	public void addLineToFullDescription(String line) {
		this.fullDescription = fullDescription.concat(line).concat("\n");
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


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getPublication() {
		return publication;
	}


	public void setPublication(String publication) {
		this.publication = publication;
	}

	public String getLogLocation() {
		return logLocation;
	}
	
	public void setLogLocation(String logLocation) {
		this.logLocation = logLocation;
	}


	public List<TestResult> getTempResultList() {
		return tempResultList;
	}


	public void setTempResultList(List<TestResult> tempResultList) {
		this.tempResultList = tempResultList;
	}


	public String getGlobalResult() {
		return globalResult;
	}


	public void setGlobalResult(String globalResult) {
		this.globalResult = globalResult;
	}


	public String getAtomicResult() {
		return atomicResult;
	}


	public void setAtomicResult(String atomicResult) {
		this.atomicResult = atomicResult;
	}


	public String getRequest4Interaction() {
		return request4Interaction;
	}


	public void setRequest4Interaction(String request4Interaction) {
		this.request4Interaction = request4Interaction;
	}


}

