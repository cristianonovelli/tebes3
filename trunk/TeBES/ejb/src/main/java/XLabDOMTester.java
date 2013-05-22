import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;

import it.enea.xlab.tebes.test.taml.TAMLDOM;


public class XLabDOMTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		try {
			TAMLDOM t = new TAMLDOM("C:/Java/jboss-4.2.3.GA/bin/TeBES_Artifacts/users/0/testplans/TP-1.xml");
			System.out.println(t.getXMLString());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
