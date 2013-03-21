package it.enea.xlab.tebes.rule;

import it.enea.xlab.tebes.model.TestRule;

import javax.ejb.Local;

@Local
public interface RuleManagerLocal {
	
	public boolean executeTestRule(TestRule testRule);
	
	public boolean schematronValidation(String xmlString, String xmlSchematron);
	
	public boolean xPathValidation(String xmlString, String xpath);
	
	public boolean xmlSchemaValidation(String xmlString, String xsdString);
}
