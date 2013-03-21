package it.enea.xlab.tebes.test.taml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xlab.utilities.XLabException;
import org.xlab.xml.JXLabDOM;
import org.xlab.xml.XLabDOMException;
import org.xml.sax.SAXException;


/**
 * TAMLDOM Class
 *  
 * Class to handling TAML standard
 * 
 * @author Cristiano Novelli
 */
public class TAMLDOM extends JXLabDOM {

	
	// TAMLDOM Costructor 1
	public TAMLDOM(String absFileName) 
		throws SAXException, IOException, ParserConfigurationException {
		
		super(absFileName, false, false);
		
	} 

	// TAMLDOM Costructor 2
	public TAMLDOM(String absXMLFileName, String absXSDFileName, boolean namespaceAware) 
			throws SAXException, IOException, ParserConfigurationException, XLabException, XLabDOMException {
		
			super(absXMLFileName, absXSDFileName, namespaceAware);
	} 
	
	
	
	// Get testAssertion Node
	public Node getTestAssertionNode(String taId) 
		throws TransformerException {

		Node taNode = null;
		
    	String xpath = "/*/taml:testAssertion[@id = \"" + taId + "\"]";		

    	taNode = XPathAPI.selectSingleNode(this.doc, xpath);

		return taNode;
	} 	

	public NodeList getTestAssertionNodeList() {
		
		return this.root.getElementsByTagName("taml:testAssertion");
	}
	
	public NodeList getTestaAssertionRefListNodes() {
		
		return this.root.getElementsByTagName("taml:testAssertionRef");
	}
	
	// TODO questo sarebbe da spostare nella JXLabDOM
	public String getNodeValue(Node node) {

			return node.getFirstChild().getNodeValue();
	}
	
	
	///////////////////////////////////////////////////////////
	//// Methods to get testAssertion Child NODE and VALUE ////
	///////////////////////////////////////////////////////////


	public Node getTargetNode(Node testAssertionNode) {
		
		return this.getTAChildNode(testAssertionNode, "taml:target");
	}
	
	public Node getPrerequisiteNode(Node testAssertionNode) {
		
		return this.getTAChildNode(testAssertionNode, "taml:prerequisite");
	}

	public Node getPredicateNode(Node testAssertionNode) {
		
		return this.getTAChildNode(testAssertionNode, "taml:predicate");
	}

	
	public Node getPrescriptionNode(Node testAssertionNode) {
		
		return this.getTAChildNode(testAssertionNode, "taml:prescription");
	}
	
	public Node getDescriptionNode(Node testAssertionNode) {
		
		return this.getTAChildNode(testAssertionNode, "taml:description");
	}

	public String getDescriptionValue(Node testAssertionNode) {
		
		return this.getNodeValue(this.getTAChildNode(testAssertionNode, "taml:description"));
	}
	
	// Get generic testAssertion Child Node
	private Node getTAChildNode(Node testAssertionNode, String nodeTag) {
		
		Node childNode = null;
		
		if (testAssertionNode != null) {
			
			Element taElement = (Element) testAssertionNode;
			childNode = taElement.getElementsByTagName(nodeTag).item(0);
		}
		
		return childNode;
	}
	
	/////////////////////////////////////////////////////
	//// Methods to get testAssertion Children NODES ////
	/////////////////////////////////////////////////////


	public NodeList getVarNodes(Node testAssertionNode) {
		
		return this.getTAChildrenNodeList(testAssertionNode, "taml:var");
	}

	public NodeList getReportNodes(Node testAssertionNode) {
		
		return this.getTAChildrenNodeList(testAssertionNode, "taml:report");
	}
	
	// Get generic testAssertion Children Nodes
	private NodeList getTAChildrenNodeList(Node testAssertionNode, String childrenTag) {
		
		NodeList childrenNodeList = null;
		
		if (testAssertionNode != null) {
			
			Element taElement = (Element) testAssertionNode;
			childrenNodeList = taElement.getElementsByTagName(childrenTag);
		}
		
		return childrenNodeList;
	}
	

	
	
	///////////////////////////////////////////////////////
	//// Methods to get testAssertion Child ATTRIBUTES ////
	///////////////////////////////////////////////////////

	public String getNameAttribute(Node node) {
			
		return this.getNodeAttribute(node, "name");
	}

	public String getIdAttribute(Node node) {

		return this.getNodeAttribute(node, "id");
	}
	
	public String getTaidAttribute(Node node) {

		return this.getNodeAttribute(node, "taid");
	}
	
	public String getSourcedocAttribute(Node node) {

		return this.getNodeAttribute(node, "sourcedoc");
	}
	
	public String getLgAttribute(Node node) {

		return this.getNodeAttribute(node, "lg");
	}

	public String getVnameAttribute(Node node) {

		return this.getNodeAttribute(node, "vname");
	}	
	
	public String getVtypeAttribute(Node node) {
		
		return this.getNodeAttribute(node, "vtype");
	}

	public String getTypeAttribute(Node node) {
		
		return this.getNodeAttribute(node, "type");
	}
	
	public String getPrescriptionLevel(Node node) {
		
		return this.getNodeAttribute(this.getPrescriptionNode(node), "level");
	}
	
	public String getLabelAttribute(Node node) {

		return this.getNodeAttribute(node, "label");
	}	
	
	public String getMessageAttribute(Node node) {

		return this.getNodeAttribute(node, "message");
	}

	public String getDescriptionAttribute(Node node) {

		return this.getNodeAttribute(node, "description");
	}

	// Get generic node Attribute
	private String getNodeAttribute(Node node, String attributeLabel) {

		String attributeValue = null;
		
		if (node != null) 
			attributeValue = ((Element) node).getAttribute(attributeLabel);

		return attributeValue;
	}



}

