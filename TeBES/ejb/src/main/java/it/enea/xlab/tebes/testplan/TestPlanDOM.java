package it.enea.xlab.tebes.testplan;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xlab.xml.JXLabDOM;
import org.xml.sax.SAXException;

/**
 * TAMLDOM Class
 *  
 * Class to handling TAML standard
 * 
 * @author Cristiano Novelli
 */
public class TestPlanDOM extends JXLabDOM {

	// TAMLDOM Costructor
	public TestPlanDOM(String absFileName) 
		throws SAXException, IOException, ParserConfigurationException {
		
		super(absFileName, false, false);
		
	} 

	
	public TestPlanDOM() 
			throws SAXException, IOException, ParserConfigurationException {
			
			super();
			
		} 
	
	public NodeList getTestActionNodeList() {

		NodeList actionNodes = null;
		
		String xpath = "//TestPlan/TestActionList/TestAction";

		actionNodes = this.getNodesByXPath(xpath);

		return actionNodes;	 
	}


	
	public String getActionName(Element actionElement) {

		return actionElement.getElementsByTagName("tebes:ActionName").item(0).getFirstChild().getNodeValue();
	}

	public String getActionDescription(Element actionElement) {
		
		return actionElement.getElementsByTagName("tebes:ActionDescription").item(0).getFirstChild().getNodeValue();
	} 	
	
	public Node getTestNode(Element actionElement) {

		return actionElement.getElementsByTagName("tebes:Test").item(0);
	}	
	

	////////////////////////////////////////
	//// Methods to get Root ATTRIBUTES ////
	////////////////////////////////////////
	
	public String getRootDatetimeAttribute() {
		
		return this.getNodeAttribute(this.root, "datetime");
	}

	public String getRootStateAttribute() {
		
		return this.getStateAttribute(this.root);
	}
	
	public String getRootIdAttribute() {
		
		return this.getIdAttribute(this.root);
	}
	
	public String getRootUserIDAttribute() {
		
		return this.getUserIDAttribute(this.root);
	}

	public void setRootUserIDAttribute(String userId) {
		
		//this.getUserIDAttribute(this.root);
	}
	
	////////////////////////////////////////
	//// Methods to get node ATTRIBUTES ////
	////////////////////////////////////////

	public String getIdAttribute(Node node) {
		
		return this.getNodeAttribute(node, "id");
	}
	
	public String getUserIDAttribute(Node node) {
		
		return this.getNodeAttribute(node, "userID");
	}
	
	public String getUserSUTAttribute(Node node) {
		
		return this.getNodeAttribute(node, "userSUT");
	}

	public String getDatetimeAttribute(Node node) {
		
		return this.getNodeAttribute(node, "datetime");
	}

	public String getStateAttribute(Node node) {
		
		return this.getNodeAttribute(node, "state");
	}
	
	public String getNumberAttribute(Node node) {
		
		return this.getNodeAttribute(node, "number");
	}
	
	public String getLgAttribute(Node node) {
		
		return this.getNodeAttribute(node, "lg");
	}

	public String getTypeAttribute(Node node) {
		
		return this.getNodeAttribute(node, "type");
	}
	
	public String getLocationAttribute(Node node) {
		
		return this.getNodeAttribute(node, "location");
	}
	
	public String getJumpAttribute(Node node) {
		
		return this.getNodeAttribute(node, "jumpPrerequisites");
	}
	
	// Get generic node Attribute
	private String getNodeAttribute(Node node, String attributeLabel) {

		return this.getAttribute(attributeLabel, node);
		
	}





}



