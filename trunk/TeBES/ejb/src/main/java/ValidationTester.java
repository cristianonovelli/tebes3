import org.xlab.file.XLabFileManager;
import org.xlab.utilities.XLabUtilities;

import exception.LocalFileNotFoundException;
import exception.SchemaParserException;
import schemavalidator.SchemaMainValidator;
import schemavalidator.SchemaMainValidatorImpl;
import validator.ErrorMessage;


public class ValidationTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("1...");
		String fullXmlFileName = "ubl-invoice.xml";
		//String xsdFile = "http://docs.oasis-open.org/ubl/os-UBL-2.0/xsd/maindoc/UBL-Invoice-2.0.xsd";
		String xsdFile = "http://winter.bologna.enea.it/peppol_schema_rep/xsd/maindoc/UBL-Invoice-2.0.xsd";
		//String xsdFile = "http://winter.bologna.enea.it/peppol_schema_rep/schematron/sdi/UBL/SDI-UBL.sch";
		
		
		
		
		SchemaMainValidator smv = new SchemaMainValidatorImpl();
		smv.setRootDir("/Java/Temp/");
		
		ErrorMessage emList[] = null;
		
		System.out.println("2...");
		
		try {
			emList = smv.reportValidation(fullXmlFileName, xsdFile);
			System.out.println("3...");
			
			int i=0;
			System.out.println("PRE While");
			while (i<emList.length){
				
				System.out.println("RIGA " + i + ": " + emList[i].getErrorType());
				System.out.println("RIGA " + i + ": " + emList[i].getLineNumber());
				System.out.println("RIGA " + i + ": " + emList[i].getDescription());
				
				
				i++;
			}
			System.out.println("POST While");
			
			
		} catch (LocalFileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SchemaParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (emList != null)
			System.out.println("Test ok");
		else
			System.out.println("Test no");

		
	}

}
