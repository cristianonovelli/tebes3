package it.enea.xlab.tebes.testrule;

import it.enea.xlab.tebes.model.TestRule;

import javax.ejb.Remote;

@Remote
public interface TestRuleManagerRemote {
	
	public boolean executeTestRule(TestRule testRule);
	
	public boolean schematronValidation(String xmlString, String xmlSchematron);
	
	public boolean xPathValidation(String xmlString, String xpath);
	
	public boolean xmlSchemaValidation(String xmlString, String xsdString);
}
