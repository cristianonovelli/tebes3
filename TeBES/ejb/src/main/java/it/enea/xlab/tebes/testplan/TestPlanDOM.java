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

	
	////////////////////////////
	//// HEADER information ////
	////////////////////////////
	
	public String getTPName() {

		return this.getNodesByXPath("//TestPlan/TestPlanHeader/Name").item(0).getFirstChild().getNodeValue();	 
	}
	
	public String getTPState() {

		return this.getNodesByXPath("//TestPlan/TestPlanHeader/State").item(0).getFirstChild().getNodeValue();	 
	}
	
	public String getTPDescription(String lg) {

		return this.getNodesByXPath("//TestPlan/TestPlanHeader/Description[@lg=\"" + lg + "\"]").item(0).getFirstChild().getNodeValue();	 
	}

	public NodeList getTPDescriptionList() {

		return this.getNodesByXPath("//TestPlan/TestPlanHeader/Description");	 
	}
	
	public String getTPCreationDatetime() {

		return this.getNodesByXPath("//TestPlan/TestPlanHeader/CreationDatetime").item(0).getFirstChild().getNodeValue();	 
	}

	public String getTPLastUpdateDatetime() {

		return this.getNodesByXPath("//TestPlan/TestPlanHeader/LastUpdateDatetime").item(0).getFirstChild().getNodeValue();	 
	}
	
	
	/////////////////////////////
	//// TESTACTION NodeList ////
	/////////////////////////////	
	
	public NodeList getTestActionNodeList() {

		NodeList actionNodes = null;
		
		String xpath = "//TestPlan/TestActionList/TestAction";

		actionNodes = this.getNodesByXPath(xpath);

		return actionNodes;	 
	}


	
	public String getActionName(Element actionElement) {

		return actionElement.getElementsByTagName("tebes:ActionName").item(0).getFirstChild().getNodeValue();
	}

	/*public String getActionDescription(Element actionElement) {
		
		return actionElement.getElementsByTagName("tebes:ActionDescription").item(0).getFirstChild().getNodeValue();
	} 	*/
	
	public NodeList getActDescriptionList(Element actionElement) {
		return actionElement.getElementsByTagName("tebes:ActionDescription");
	}

	
	public Node getTestNode(Element actionElement) {

		return actionElement.getElementsByTagName("tebes:Test").item(0);
	}	
	

	
	
	////////////////////
	//// INPUT NODE ////
	////////////////////
	
	public NodeList getInputNodeList(Element actionElement) {

		return actionElement.getElementsByTagName("tebes:Input");	 
	}

	public String getInputName(Node inputNode) {
		
		return ((Element) inputNode).getElementsByTagName("tebes:Name").item(0).getFirstChild().getNodeValue();
	}

	public Node getSutNode(Node inputNode) {
		
		return ((Element) inputNode).getElementsByTagName("tebes:SUT").item(0);
	}
	
	public String getInputSutInteraction(Node sutNode) {
		
		return this.getAttribute("interaction", sutNode);
	}

	public String getInputSUTType(Node sutNode) {
		
		return this.getAttribute("type", sutNode);
	}

	public String getInputSUTLg(Node sutNode) {
		
		return this.getAttribute("lg", sutNode);
	}

	public String getInputSUTFileIdRef(Node sutNode) {
		
		return this.getAttribute("fileIdRef", sutNode);
	}
	
	
	public Node getGUINode(Node inputNode) {
		
		return ((Element) inputNode).getElementsByTagName("tebes:GUI").item(0);
	}


	public String getInputGuiReaction(Node guiNode) {
		
		return this.getAttribute("reaction", guiNode);
	}

	public NodeList getInputGuiDescriptionNodeList(Node guiNode) {
		
		return ((Element) guiNode).getElementsByTagName("tebes:GUIDescription");
	}
	
	////////////////////////////////////////
	//// Methods to get Root ATTRIBUTES ////
	////////////////////////////////////////

	/*public String getRootNameAttribute() {
		
		return this.getNodeAttribute(this.root, "name");
	}
	
	public String getRootCreationDatetimeAttribute() {
		
		return this.getNodeAttribute(this.root, "creationDatetime");
	}

	public String getRootLastUpdateDatetimeAttribute() {
		
		return this.getNodeAttribute(this.root, "lastUpdateDatetime");
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
		
		this.getUserIDAttribute(this.root);
	}
	
	public String getRootDescriptionAttribute() {
		
		return this.getDescriptionAttribute(this.root);
	}*/
	
	
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
	
	public String getDescriptionAttribute(Node node) {
		
		return this.getNodeAttribute(node, "description");
	}
	
	public String getNumberAttribute(Node node) {
		
		return this.getNodeAttribute(node, "number");
	}
	
	public String getLgAttribute(Node node) {
		
		return this.getNodeAttribute(node, "lg");
	}
	
	public String getNameAttribute(Node inputNode) {
		
		return this.getNodeAttribute(inputNode, "name");
	}

	/*public String getSutInteractionAttribute(Node node) {
		
		return this.getNodeAttribute(node, "sutInteraction");
	}*/
	
	public String getTypeAttribute(Node node) {
		
		return this.getNodeAttribute(node, "type");
	}
	
	public String getFileIdRefAttribute(Node node) {
		
		return this.getNodeAttribute(node, "fileIdRef");
	}

	public String getGuiReactionAttribute(Node node) {
		
		return this.getNodeAttribute(node, "guiReaction");
	}
	
	public String getGuiMessageAttribute(Node node) {
		
		return this.getNodeAttribute(node, "guiMessage");
	}
	
	
	public String getLocationAttribute(Node node) {
		
		return this.getNodeAttribute(node, "location");
	}
	
	public String getSkipAttribute(Node node) {
		
		return this.getNodeAttribute(node, "skipPrerequisites");
	}
	
	// Get generic node Attribute
	private String getNodeAttribute(Node node, String attributeLabel) {

		return this.getAttribute(attributeLabel, node);
		
	}


	public NodeList getInputDescriptionList(Node inputNode) {
		
		return ((Element) inputNode).getElementsByTagName("tebes:InputDescription");
	}


	public NodeList getGUIDescriptionList(Node guiNode) {

		return ((Element) guiNode).getElementsByTagName("tebes:GUIDescription");
	}


}



