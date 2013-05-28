package it.enea.xlab.tebes.report;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xlab.xml.JXLabDOM;
import org.xml.sax.SAXException;

/**
 * ReportDOM Class
 *  
 * Class to handling TeBES Report
 * 
 * @author Cristiano Novelli
 */
public class ReportDOM extends JXLabDOM {

	// ReportDOM Costructor
	public ReportDOM(String absFileName) 
		throws SAXException, IOException, ParserConfigurationException {
		
		super(absFileName, false, false);
		
	} 

	
	public ReportDOM() 
			throws SAXException, IOException, ParserConfigurationException {
			
			super();
			
		} 

	
	

	///////////////////////////////////
	//// Methods to handle Actions ////
	///////////////////////////////////
	
	public NodeList getTestActionNodeList() {

		NodeList actionNodes = null;
		
		String xpath = "//Report/TestPlanExecution/TestActionList/TestAction";

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
	
	
	public String getRootIdAttribute() {
		
		return this.getIdAttribute(this.root);
	}

	public String getRootNameAttribute() {
		
		return this.getNameAttribute(this.root);
	}
	
	public String getRootDescriptionAttribute() {
		
		return this.getDescriptionAttribute(this.root);
	}

	
	public String getRootSessionIDAttribute() {
		
		return this.getSessionIDAttribute(this.root);
	}

	public String getRootDatetimeAttribute() {
		
		return this.getNodeAttribute(this.root, "datetime");
	}

	public String getRootStateAttribute() {
		
		return this.getStateAttribute(this.root);
	}
	
	////////////////////////////////////////////
	//// Methods to get/set node ATTRIBUTES ////
	////////////////////////////////////////////

	public String getIdAttribute(Node node) {
		
		return this.getNodeAttribute(node, "id");
	}
	
	public void setIdAttribute(Node node, String id) {
		
		this.setNodeAttribute(node, "id", id);
	}

	public String getNameAttribute(Node node) {
		
		return this.getNodeAttribute(node, "name");
	}

	public void setNameAttribute(Node node, String name) {
		
		this.setNodeAttribute(node, "name", name);
	}
	
	public String getDescriptionAttribute(Node node) {
		
		return this.getNodeAttribute(node, "description");
	}
	
	public void setDescriptionAttribute(Node node, String description) {
		
		this.setNodeAttribute(node, "description", description);
	}
	
	public String getSessionIDAttribute(Node node) {
		
		return this.getNodeAttribute(node, "sessionID");
	}

	public void setSessionIDAttribute(Node node, String sessionID) {
		
		this.setNodeAttribute(node, "sessionID", sessionID);
	}
	
	public String getDatetimeAttribute(Node node) {
		
		return this.getNodeAttribute(node, "datetime");
	}

	public void setDatetimeAttribute(Node node, String datetime) {
		
		this.setNodeAttribute(node, "datetime", datetime);
	}
	
	public String getStateAttribute(Node node) {
		
		return this.getNodeAttribute(node, "state");
	}
	
	public void setStateAttribute(Node node, String state) {
		
		this.setNodeAttribute(node, "state", state);
	}
	
	// Get generic node Attribute
	private String getNodeAttribute(Node node, String attributeLabel) {

		return this.getAttribute(attributeLabel, node);
		
	}
	
	
	////////////////////////////////////////
	//// Methods to handle SESSION Node ////
	////////////////////////////////////////
	
	
	
	public Element getSessionElement() {
		
		return (Element) getRootElement().getElementsByTagName("tebes:Session").item(0);
	}


	public void setSessionStartDateTime(String starteDateTime) {
		
		Element startDatetimeElement = (Element) this.getSessionElement().getElementsByTagName("tebes:StartDateTime").item(0);
		
		startDatetimeElement.setNodeValue(starteDateTime);
	}


	public void setSessionLastDateTime(String lastDateTime) {
		
		Element lastDatetimeElement = (Element) this.getSessionElement().getElementsByTagName("tebes:LastDateTime").item(0);
		
		lastDatetimeElement.setNodeValue(lastDateTime);
	}
	
	
	
	/////////////////////////////////////
	//// Methods to handle USER Node ////
	/////////////////////////////////////
	
	public Element getUserElement() {
		
		return (Element) getRootElement().getElementsByTagName("tebes:User").item(0);
	}
	
	public void setUserName(String userName) {
		
		Element userNameElement = (Element) this.getUserElement().getElementsByTagName("tebes:Name").item(0);
		
		userNameElement.setNodeValue(userName);
	}	
	
	public void setUserSurname(String userSurname) {
		
		Element userSurnameElement = (Element) this.getUserElement().getElementsByTagName("tebes:Surname").item(0);
		
		userSurnameElement.setNodeValue(userSurname);
	}		
	
	
}









