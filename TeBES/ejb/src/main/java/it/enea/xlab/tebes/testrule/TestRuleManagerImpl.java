package it.enea.xlab.tebes.testrule;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.model.TestRule;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;


@Stateless
@Interceptors({Profile.class})
public class TestRuleManagerImpl implements TestRuleManagerRemote {
	
	public boolean executeTestRule(TestRule testRule) {
		
		boolean result = false;
		
		String xmlString = "TODO";
		
		System.out.println("- Prerequisite OK... EXE Predicate");
		System.out.println("- Language: " + testRule.getLanguage());
		System.out.println("- Value: " + testRule.getValue());
		
		if (testRule.getLanguage().equals(Constants.XPATH))
			result = xPathValidation(xmlString, testRule.getValue());
			
		if (testRule.getLanguage().equals(Constants.SCHEMATRON))
			result = schematronValidation(xmlString, testRule.getValue());
			
		
		System.out.println("TODO: Schematron or XPath Execution");
		
		return result;
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

