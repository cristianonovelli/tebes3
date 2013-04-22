package it.enea.xlab.tebes.test.rule;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.model.TestRule;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;


@Stateless
@Interceptors({Profile.class})
public class RuleManagerImpl implements RuleManagerRemote {
	
	public Report executeTestRule(TestRule testRule, Report report) {
		
		//boolean result = false;
		
		
		String xmlString = "TODO";
		
		report.addToFullDescription("\n- Prerequisite OK... EXE Predicate");
		report.addToFullDescription("\n- Language: " + testRule.getLanguage());
		report.addToFullDescription("\n- Value: " + testRule.getValue());
		
		if (testRule.getLanguage().equals(Constants.XPATH))
			report.setPartialResultSuccessfully(xPathValidation(xmlString, testRule.getValue()));
			
		if (testRule.getLanguage().equals(Constants.SCHEMATRON))
			report.setPartialResultSuccessfully(schematronValidation(xmlString, testRule.getValue()));
			
		
		report.addToFullDescription("\nTODO: Schematron or XPath Execution");
		
		//return result;
		return report;
	}
	
	
	public boolean schematronValidation(String xmlString, String xmlSchematron) {
		// TODO Auto-generated method stub
		return true;
	}


	public boolean xPathValidation(String xmlString, String xpath) {
		
		return true;
	}

	public boolean xmlSchemaValidation(String xmlString, String xsdString) {
		
		return true;
	}
}

