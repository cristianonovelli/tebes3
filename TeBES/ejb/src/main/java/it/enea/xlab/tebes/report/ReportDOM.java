package it.enea.xlab.tebes.report;

import it.enea.xlab.tebes.entity.TestResult;

import java.io.IOException;
import java.util.List;

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
	
/*	public NodeList getTestActionNodeList() {

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
	*/
	
	

	////////////////
	//// HEADER ////
	////////////////
	
	private Node getHeaderChildNode(String childNodeName) {

		return this.getNodesByXPath("//Report/ReportHeader/" + childNodeName).item(0);
	}

	public void setHeaderId(String id) {
		
		Node headerIdNode = this.getHeaderChildNode("Id");
		headerIdNode.getFirstChild().setNodeValue(id);
	}

	public void setHeaderName(String name) {
		
		Node headerNameNode = this.getHeaderChildNode("Name");
		headerNameNode.getFirstChild().setNodeValue(name);		
	}

	public void setHeaderState(String state) {
		
		Node headerStateNode = this.getHeaderChildNode("State");
		headerStateNode.getFirstChild().setNodeValue(state);
	}

	public void setHeaderDescription(String language, String value) {

		Node headerDescriptionNode = this.getNodesByXPath("//Report/ReportHeader/Description[@lg=\"" + language + "\"]").item(0);		
		headerDescriptionNode.getFirstChild().setNodeValue(value);
	}


	
	
	
	


	/*public String getRootStateAttribute() {
		
		return this.getStateAttribute(this.root);
	}*/
	
	
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
	
	/*public String getSessionIDAttribute(Node node) {
		
		return this.getNodeAttribute(node, "sessionID");
	}

	public void setSessionIDAttribute(Node node, String sessionID) {
		
		this.setNodeAttribute(node, "sessionID", sessionID);
	}*/
	
	/*public String getDatetimeAttribute(Node node) {
		
		return this.getNodeAttribute(node, "datetime");
	}

	public void setDatetimeAttribute(Node node, String datetime) {
		
		this.setNodeAttribute(node, "datetime", datetime);
	}*/
	
	/*public String getStateAttribute(Node node) {
		
		return this.getNodeAttribute(node, "state");
	}
	
	public void setStateAttribute(Node node, String state) {
		
		this.setNodeAttribute(node, "state", state);
	}*/
	
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


	public void setSessionCreationDateTime(String creationDateTime) {
		
		Element startDatetimeElement = (Element) this.getSessionElement().getElementsByTagName("tebes:CreationDateTime").item(0);
		
		startDatetimeElement.getChildNodes().item(0).setNodeValue(creationDateTime);
	}


	public void setSessionLastUpdateDateTime(String lastUpdateDateTime) {
		
		Element lastUpdateDatetimeElement = (Element) this.getSessionElement().getElementsByTagName("tebes:LastUpdateDateTime").item(0);
		
		lastUpdateDatetimeElement.getChildNodes().item(0).setNodeValue(lastUpdateDateTime);
	}
	
	
	
	/////////////////////////////////////
	//// Methods to handle USER Node ////
	/////////////////////////////////////
	
	public Element getUserElement() {
		
		return (Element) getRootElement().getElementsByTagName("tebes:User").item(0);
	}
	
	public void setUserName(String userName) {
		
		Element userNameElement = (Element) this.getUserElement().getElementsByTagName("tebes:Name").item(0);
		
		userNameElement.getChildNodes().item(0).setNodeValue(userName);
	}	
	
	public void setUserSurname(String userSurname) {
		
		Element userSurnameElement = (Element) this.getUserElement().getElementsByTagName("tebes:Surname").item(0);
		
		userSurnameElement.getChildNodes().item(0).setNodeValue(userSurname);
	}


	
	////////////////////////////////////
	//// Methods to handle SUT Node ////
	////////////////////////////////////
	
	private Element getSUTElement() {
		
		return (Element) getRootElement().getElementsByTagName("tebes:SUT").item(0);
	}

	public void setSUTId(Long sutId) {
		
		this.setIdAttribute(this.getSUTElement(), sutId.toString());	
	}

	private void setSUTChildNode(String nodeTagName, String value) {
		
		Element sutChildElement = (Element) this.getSUTElement().getElementsByTagName(nodeTagName).item(0);		
		sutChildElement.getChildNodes().item(0).setNodeValue(value);	
	}
	
	public void setSUTName(String name) {
		
		this.setSUTChildNode("tebes:Name", name);			
	}

	public void setSUTType(String type) {
		
		this.setSUTChildNode("tebes:Type", type);
	}

	public void setSUTLanguage(String language) {
		
		this.setSUTChildNode("tebes:Language", language);
	}

/*	public void setSUTReference(String reference) {
		
		this.setSUTChildNode("tebes:Reference", reference);	
	}*/

	public void setSUTInteraction(String interactionType) {
		
		this.setSUTChildNode("tebes:Interaction", interactionType);	
	}

	public void setSUTDescription(String description) {
		
		this.setSUTChildNode("tebes:Description", description);
	}

	
	
	/////////////////////////////////////////
	//// Methods to handle TestPlan Node ////
	/////////////////////////////////////////
	
	private Element getTestPlanElement() {
		
		return (Element) getRootElement().getElementsByTagName("tebes:TestPlan").item(0);
	}

	public void setTestPlanId(Long testPlanId) {
		
		this.setIdAttribute(this.getTestPlanElement(), testPlanId.toString());	
	}

	private void setTestPlanChildNode(String nodeTagName, String value) {
		
		Element tpChildElement = (Element) this.getTestPlanElement().getElementsByTagName(nodeTagName).item(0);		
		tpChildElement.getChildNodes().item(0).setNodeValue(value);	
	}

	public void setTestPlanName(String name) {
		
		this.setTestPlanChildNode("tebes:Name", name);
	}
	
	public Node getTestPlanDescriptionNode(String language) {

		return this.getNodesByXPath("//Report/Session/TestPlan/Description[@lg=\"" + language + "\"]").item(0);	 
	}
	
	public void setTestPlanDescription(String language, String description) {

		this.getTestPlanDescriptionNode(language).getFirstChild().setNodeValue(description);
	}
	
	public void setTestPlanCreationDatetime(String creationDatetime) {
		
		this.setTestPlanChildNode("tebes:CreationDateTime", creationDatetime);
	}

	public void setTestPlanLastUpdateDatetime(String lastUpdateDatetime) {
		
		this.setTestPlanChildNode("tebes:LastUpdateDateTime", lastUpdateDatetime);
	}
	
	public void setTestPlanState(String state) {

		this.setTestPlanChildNode("tebes:State", state);
	}

	public void setTestPlanReference(String location) {
		
		this.setTestPlanChildNode("tebes:Reference", location);
	}




	
	//////////////////////////////////////////
	//// Methods to handle Execution Node ////
	//////////////////////////////////////////

	public String getGlobalResult() {
		
		return this.doc.getElementsByTagName("tebes:GlobalResult").item(0).getFirstChild().getNodeValue();
	}


	public void setNumberAttribute(Node actionXML, String number) {
		
		this.setNodeAttribute(actionXML, "number", number);		
	}


	public void insertActionNode(Node actionNodeClone) {

		Node testActionListNode = this.getTestActionListNode();
		testActionListNode.insertBefore(actionNodeClone, null);
	}


	public Node getTestActionListNode() {

		return this.doc.getElementsByTagName("tebes:TestActionList").item(0);
	}


	public void setGlobalResult(String result) {

		Element globalResultElement = (Element) this.getGlobalResultNode();
		globalResultElement.getChildNodes().item(0).setNodeValue(result);	
	
	}


	private Node getGlobalResultNode() {

		return this.doc.getElementsByTagName("tebes:GlobalResult").item(0);
	}

	public Node getTestElement(Node actionNode) {
		
		return ((Element) actionNode).getElementsByTagName("tebes:Test").item(0);
	}


	public NodeList getTestActionNodeList() {

			return this.doc.getElementsByTagName("tebes:TestAction");
	}


	public void setNodeValue(Node rootNode, String nodeLabel, String nodeValue) {

		Element rootElement = (Element) rootNode;
		Element myElement = (Element) rootElement.getElementsByTagName(nodeLabel).item(0);
		
		myElement.getChildNodes().item(0).setNodeValue(nodeValue);
	}	
	
	public void setActionName(Node actionNode, String name) {

		this.setNodeValue(actionNode, "tebes:ActionName", name);
	}

	public NodeList getInputNodeList(Element actionElement) {

		return actionElement.getElementsByTagName("tebes:Input");	 
	}
	
	/*public void setActionDescription(Node actionNode, String description) {

		this.setNodeValue(actionNode, "tebes:ActionDescription", description);
	}*/
	
	public void setTestActionDescription(Node action, String language, String description) {

		this.getTestActionDescriptionNode(action, language).getFirstChild().setNodeValue(description);
	}


	private Node getTestActionDescriptionNode(Node action, String language) {
		
		Node resultNode = null;
		
		NodeList actionDescriptionNodeList = ((Element) action).getElementsByTagName("tebes:ActionDescription");
		
		for (int i=0; i<actionDescriptionNodeList.getLength(); i++)
			if (this.getAttribute("lg", actionDescriptionNodeList.item(i)).equals(language))
					resultNode= actionDescriptionNodeList.item(i);

		return resultNode;
	}


	public void setActionTest(Node actionNode, String testValue) {
		
		this.setNodeValue(actionNode, "tebes:Test", testValue);
	}


	public void setTestSkipPrerequisitesAttribute(Node actionNode,
			boolean skipTurnedON) {
		
		if (skipTurnedON)
			this.setNodeAttribute(actionNode, "skipPrerequisites", "true");
		else
			this.setNodeAttribute(actionNode, "skipPrerequisites", "false");
		
	}


	public void setTestLgAttribute(Node actionNode, String testLanguage) {
		
		this.setNodeAttribute(actionNode, "lg", testLanguage);
	}


	public void setTestLocationAttribute(Node actionNode, String testLocation) {
		
		this.setNodeAttribute(actionNode, "location", testLocation);
	}


	public void setTestTypeAttribute(Node actionNode, String testType) {
		
		this.setNodeAttribute(actionNode, "type", testType);
	}


	public void setSingleResult(Node singleResultNode, String id, String name, String result, int line, String message) {
		
		//for(int i=0; i<testResulList.size(); i++) {
		
			this.setNodeAttribute(singleResultNode, "id", id);
			this.setNodeAttribute(singleResultNode, "name", name);
			this.setNodeAttribute(singleResultNode, "result", result);
			this.setNodeAttribute(singleResultNode, "line", new Integer(line).toString());
			this.setNodeAttribute(singleResultNode, "message", message);
		//}
	}


	public Node getTestResultsNode(Node actionNode) {
		
		return ((Element) actionNode).getElementsByTagName("tebes:TestResults").item(0);
	}


	/*public Node getPrerequisitesElement(Node actionNode) {
		
		return ((Element) actionNode).getElementsByTagName("tebes:Prerequisites").item(0);
	}*/


	public void insertPrerequisiteResultNode(Node clonePrerequisiteResultNode, Node prerequisiteResultNode, Node actionContext) {
		
		Node prerequisitesNode = this.getPrerequisitesNode((Element) actionContext);
		clonePrerequisiteResultNode = prerequisitesNode.insertBefore(clonePrerequisiteResultNode, prerequisiteResultNode);

	}


	public Node getPrerequisitesNode(Element actionNode) {
		
		return actionNode.getElementsByTagName("tebes:Prerequisites").item(0);
	}


	public void setPrerequisiteResult(
			Node prerequisiteResultNode, 
			Long id,
			String name, 
			String note,
			String result, 
			int line, 
			String message) {
		
		this.setNodeAttribute(prerequisiteResultNode, "id", id.toString());
		this.setNodeAttribute(prerequisiteResultNode, "name", name);
		this.setNodeAttribute(prerequisiteResultNode, "result", result);
		this.setNodeAttribute(prerequisiteResultNode, "description", note);
		//this.setNodeAttribute(prerequisiteResultNode, "line", new Integer(line).toString());
		//this.setNodeAttribute(prerequisiteResultNode, "message", message);
		
		Element prerequisite = (Element) prerequisiteResultNode;
		Node single = prerequisite.getElementsByTagName("tebes:SinglePrerequisiteResult").item(0);
		
		//this.setNodeAttribute(single, "id", id.toString());
		this.setNodeAttribute(single, "name", name);
		this.setNodeAttribute(single, "result", result);
		this.setNodeAttribute(single, "line", new Integer(line).toString());
		this.setNodeAttribute(single, "message", message);
		
	}


	public Node getTestAction(String actionId) {

		String xpath = "//Report/TestPlanExecution/TestActionList/TestAction[@id='" + actionId + "']";

		return this.getNodesByXPath(xpath).item(0);

	}


	public void removePrerequisitesNode(Node prerequisitesNode) {
		
		prerequisitesNode.getParentNode().removeChild(prerequisitesNode);		
	}




	public void duplicateInputTemplateNode(Element actionElement) {
		
		Node inputNodeTemplate = this.getFirstInputNode(actionElement);
		
		Node inputNodeTemplateClone = inputNodeTemplate.cloneNode(true);
		inputNodeTemplateClone = this.doc.importNode(inputNodeTemplateClone, true); 
		this.insertInputNode(inputNodeTemplateClone, actionElement);
	}


	private Node getFirstInputNode(Element actionElement) {

		return this.getInputNodeList(actionElement).item(0);
	}

	
	public void insertInputNode(Node inputNode, Element actionElement) {

		Node inputsNode = this.getInputsNode(actionElement);
		inputsNode.insertBefore(inputNode, null);
	}


	private Node getInputsNode(Element actionElement) {
		
		return actionElement.getElementsByTagName("tebes:Inputs").item(0);
	}


	public void setInputName(String name, Node inputNode) {
		
		this.setNodeValue(inputNode, "tebes:Name", name);
	}
	



	public Node getSUTNode(Node inputNode) {
		
		return ((Element) inputNode).getElementsByTagName("tebes:SUT").item(0);
	}

	public void setInputSUTSource(String source, Node sutNode) {
		
		this.setNodeAttribute(sutNode, "fileSource", source);
	}
	
	public void setInputSUTInteraction(String interaction, Node sutNode) {
		
		this.setNodeAttribute(sutNode, "interaction", interaction);
	}


	public void setInputSUTType(String type, Node sutNode) {
		
		this.setNodeAttribute(sutNode, "type", type);
	}


	public void setInputSUTLg(String lg, Node sutNode) {
		
		this.setNodeAttribute(sutNode, "lg", lg);
	}


	public void setInputSUTIdRef(String fileIdRef, Node sutNode) {
		
		this.setNodeAttribute(sutNode, "fileIdRef", fileIdRef);
	}


	public Node insertSingleResultNode(Node cloneSingleResultNode,
			Node singleResultNodeTemplate, Node actionContext) {

		Node testResultsNode = this.getTestResultsNode((Element) actionContext);
		return testResultsNode.insertBefore(cloneSingleResultNode, singleResultNodeTemplate);
	}


	public NodeList getSingleResultNodeList(Element actionElement) {
		
		return actionElement.getElementsByTagName("tebes:SingleResult");
	}

	
	
}









