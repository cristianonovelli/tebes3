package it.enea.xlab.tebes.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "inputdescription")
public class InputDescription implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String language;
	private String value;
	
	@ManyToOne
	@JoinColumn(name="userinput_id")
	Input userInput;
	
	public InputDescription() {

	}	
	
	public InputDescription(String language, String value) {

		this.setLanguage(language);
		this.setValue(value);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Input getUserInput() {
		return userInput;
	}

	public void setUserInput(Input userInput) {
		this.userInput = userInput;
	}



}


