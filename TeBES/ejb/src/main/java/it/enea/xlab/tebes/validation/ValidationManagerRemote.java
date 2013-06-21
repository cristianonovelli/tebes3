package it.enea.xlab.tebes.validation;

import javax.ejb.Remote;

import validator.ErrorMessage;

@Remote
public interface ValidationManagerRemote {

	public ErrorMessage[] validation(String xmlRelPathFileName, String xsdURL);
	
	public Long nothing();
	
}
