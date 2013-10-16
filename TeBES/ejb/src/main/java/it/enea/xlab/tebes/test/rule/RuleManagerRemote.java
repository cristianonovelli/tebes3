package it.enea.xlab.tebes.test.rule;

import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.model.TAF;


import javax.ejb.Remote;

@Remote
public interface RuleManagerRemote {
	
	public Report executeTestRule(TAF taf, Session session);
	
	public Report schematronValidation(String xmlString, String xmlSchematron, Report report);
	
	public Report xPathValidation(String xmlString, String xpath, Report report);
	
	public Report xmlSchemaValidation(String xmlString, String xsdString, Report report);
}
