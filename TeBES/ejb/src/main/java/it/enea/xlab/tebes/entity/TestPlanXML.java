package it.enea.xlab.tebes.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="testplanxml")
public class TestPlanXML implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String absFileName;
	
	@Column(length=9999) 
	private String xml;
	


	public TestPlanXML() {

	}
	
	public TestPlanXML(String xml, String absFileName) {

		this.setXml(xml);
		this.setAbsFileName(absFileName);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbsFileName() {
		return absFileName;
	}

	public void setAbsFileName(String absFileName) {
		this.absFileName = absFileName;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}
	
	
}

