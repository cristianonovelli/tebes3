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

	private String name;
	
	// The type field can take the values: testplan, report, document, message
	private String type;
	
	// The type field can take the values: xml, html, pdf, ...
	private String language;
	
	// Source File
	@Column(length=9999) 
	private String source;
	
	// Date
	private String creationDatetime;
	private String lastUpdateDatetime;

	
	public FileStore() {

	}

	public FileStore(String name, String type, String language, String creationDatetime, String source) {

		this.name = name;
		this.type = type;
		this.language = language;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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
 
	
}


