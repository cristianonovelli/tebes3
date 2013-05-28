package it.enea.xlab.tebes.test.rule;

import java.io.IOException;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.model.TestRule;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.xlab.xml.JXLabDOM;
import org.xlab.xml.XLabDOM;
import org.xlab.xml.XLabDOMException;
import org.xml.sax.SAXException;


@Stateless
@Interceptors({Profile.class})
public class RuleManagerImpl implements RuleManagerRemote {
	
	public Report executeTestRule(TestRule testRule, Report report) {
		
		//boolean result = false;
		
		// TODO
		// recupero il file oggetto dal message store della sessione
		// e glielo passo come parametro
		// perchè l'accesso al message store va fatto prima 
		
		String xmlString = "TeBES_Artifacts/users/1/docs/ubl-invoice.xml";
		
		report.addToFullDescription("\n- Prerequisite OK... EXE Predicate");
		report.addToFullDescription("\n- Language: " + testRule.getLanguage());
		report.addToFullDescription("\n- Value: " + testRule.getValue());
		
		
		
		
		if (testRule.getLanguage().equals(Constants.XPATH)) {
			
			
			report.addToFullDescription("\nTESTRULE: " + testRule.getValue());
			
			
			
			// TODO
			// se l'oggetto dell'espressione XPath è una validazione XML Schema
			// recupero l'XSD
			
			
			report.setPartialResultSuccessfully(xPathValidation(xmlString, testRule.getValue()));
			
		}
			
			
		if (testRule.getLanguage().equals(Constants.SCHEMATRON))
			report.setPartialResultSuccessfully(schematronValidation(xmlString, testRule.getValue()));
			
		if (testRule.getLanguage().equals(Constants.XMLSCHEMA)) 
			report.setPartialResultSuccessfully(xmlSchemaValidation(xmlString, testRule.getValue()));
		

		
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
		
		System.out.println("xmlSchemaValidation A:" + xmlString);
		System.out.println("xmlSchemaValidation B:" + xsdString);
		

		
		
		/*SchemaMainValidator smv = new SchemaMainValidatorImpl();
		smv.setRootDir("/Java/Temp/");
		
		ErrorMessage emList[] = null;
		

		
		try {
			emList = smv.reportValidation(xmlString, xsdString);

			
			int i=0;

			while (i<emList.length){
				
				System.out.println("RIGA " + i + ": " + emList[i].getErrorType());
				System.out.println("RIGA " + i + ": " + emList[i].getLineNumber());
				System.out.println("RIGA " + i + ": " + emList[i].getDescription());
				
				
				i++;
			}

			
			
		} catch (LocalFileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SchemaParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (emList != null)
			System.out.println("xaz Test ok");
		else
			System.out.println("xaz Test no");
		
		*/
		return true;
	}
}

