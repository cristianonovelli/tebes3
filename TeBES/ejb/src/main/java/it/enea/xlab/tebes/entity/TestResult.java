package it.enea.xlab.tebes.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "testresult")
public class TestResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String PASS_RESULT = "pass";
	public static final String FAILURE_RESULT = "fail";
	public static final String NOTQUALIFIED_RESULT = "notQualified";
	public static final String ERROR_RESULT = "error";
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	private String globalResult;
	private int line;
	private String message;
	private String link;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="report_id")
	private Report report;
	
	public TestResult() {

	}

	public TestResult(String globalResult, int line, String message) {

		this.setGlobalResult(globalResult);
		this.setLine(line);
		this.setMessage(message);
		this.setLink(null);
	}

	public TestResult(String globalResult, int line, String message, String link) {

		this.setGlobalResult(globalResult);
		this.setLine(line);
		this.setMessage(message);
		this.setLink(link);
	}
	
	public String getGlobalResult() {
		return globalResult;
	}
	public void setGlobalResult(String globalResult) {
		this.globalResult = globalResult;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	
}
