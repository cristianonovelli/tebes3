package it.enea.xlab.tebes.entity;

import it.enea.xlab.tebes.common.Constants;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FileStore implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	private String fileRefId;
	
	// con estensione
	private String fileName;
	
	// The type field can take the values: testplan, report, document, message
	// TODO specificare i tipi di file e le estensioni
	private String type;
	
	// Source File
	@Column(length=9999) 
	private String source;
	
	// Date
	private String creationDatetime;
	private String lastUpdateDatetime;

	
	public FileStore() {

	}

	public FileStore(String fileRefId, String fileName, String type, String creationDatetime, String source) {

		this.setFileRefId(fileRefId);
		this.setFileName(fileName);
		this.type = type;
		this.source = source;
		this.creationDatetime = creationDatetime;
		this.lastUpdateDatetime = creationDatetime;
	}
	
	
	public static String generateTeBESFileName(String fileRefId, String userId, String sourceFileName) {
		
		// The TeBES filename starts with fileRefId ...
		String result = fileRefId;		
		result = result.concat(Constants.UNDERSCORE);
		
		// ... it continues with UserID ...	
		result = result.concat(Constants.USERID_PREFIX);
		result = result.concat(Constants.MINUS);		
		result = result.concat(userId);		
		result = result.concat(Constants.UNDERSCORE);
		
		// ... it ends with sourceFileName.	
		result = result.concat(sourceFileName);
		
		return result;
	}
	
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCreationDatetime() {
		return creationDatetime;
	}

	public void setCreationDatetime(String creationDatetime) {
		this.creationDatetime = creationDatetime;
	}

	public String getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateDatetime(String lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	public String getFileRefId() {
		return fileRefId;
	}

	public void setFileRefId(String fileId) {
		this.fileRefId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

 
	
}


