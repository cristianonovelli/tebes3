package it.enea.xlab.tebes.entity;

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
	private String name;
	
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

	public FileStore(String fileRefId, String name, String type, String creationDatetime, String source) {

		this.setFileRefId(fileRefId);
		this.name = name;
		this.type = type;
		this.source = source;
		this.creationDatetime = creationDatetime;
		this.lastUpdateDatetime = creationDatetime;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
 
	
}


