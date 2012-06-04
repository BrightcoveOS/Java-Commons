package com.brightcove.commons.xml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.xml.utils.DefaultErrorHandler;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * <p>
 *    A utility class to make working with Xalan XML libraries easier
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class XalanUtils {
	Logger log;
	
	public XalanUtils(){
		log = Logger.getLogger(this.getClass().getCanonicalName());
	}
	
	/**
	 * <p>
	 *    Parses an XML file into a W3C Document
	 * </p>
	 * 
	 * @param xmlFile File to read and parse
	 * @param isValidating Should parser be validating or forgiving
	 * 
	 * @return XML document parsed from file
	 * 
	 * @throws ParserConfigurationException If XML could not be parsed
	 * @throws SAXException If XML could not be parsed
	 * @throws IOException If file could not be read
	 */
	public static Document parseXml(File xmlFile, Boolean isValidating) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		
		factory.setValidating(isValidating);
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		if (isValidating){
			builder.setErrorHandler(new DefaultErrorHandler(true));
		}
		
		Document doc = builder.parse(xmlFile);
		
		return doc;
	}
	
	/**
	 * <p>
	 *    Creates a new blank XML document with the specified root element
	 * </p>
	 * 
	 * @param rootElementName Name of the root element
	 * @return XML document
	 * @throws ParserConfigurationException If document couldn't be properly constructed
	 */
	public static Document createDocument(String rootElementName) throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		
		Element rootElem = doc.createElement(rootElementName);
		doc.appendChild(rootElem);
		
		return doc;
	}
	
	/**
	 * <p>
	 *    Creates a new element in the document context, and optionally puts
	 *    text content in as a child text element
	 * </p>
	 * 
	 * @param doc Document to create element in
	 * @param elementName Name of element to create
	 * @param elementContent If not null, contents will be added as a Text child of the element
	 * @param parentElem If not null, new element will be added as a child of this parent element
	 * 
	 * @return Element created
	 */
	public static Element createSimpleElement(Document doc, String elementName, String elementContent, Element parentElem){
		Element elem = doc.createElement(elementName);
		
		if(elementContent != null){
			Text content = doc.createTextNode(elementContent);
			elem.appendChild(content);
		}
		
		if(parentElem != null){
			parentElem.appendChild(elem);
		}
		
		return elem;
	}
	
	/**
	 * 
	 * <p>
	 *    Formats an XML org.w3c.dom Document into a pretty-printed String.
	 * </p>
	 * <p>
	 *    "pretty-print" means proper line breaks, indenting, spacing, etc, so
	 *    a single-line XML mess becomes human readable
	 * </p>
	 * <p>
	 *    Cribbed from
	 *    <a href="http://www.chipkillmar.net/2009/03/25/pretty-print-xml-from-a-dom/">http://www.chipkillmar.net/2009/03/25/pretty-print-xml-from-a-dom/</a>
	 * </p>
	 *
	 * @param document org.w3c.dom Document to convert
	 * @param configFile XSLT file that dictates how to convert the document
	 * @return Pretty-print formatted String of document
	 *
	 * @throws TransformerException if config file or document couldn't be parsed
	 * 
	 */
	public static String prettyPrintWithTrAX(Document document, File configFile) throws TransformerException {
		// Pretty-prints a DOM document to XML using TrAX.
		// Note that a stylesheet is needed to make formatting reliable.
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer(new StreamSource(configFile));
		StringWriter stringWriter = new StringWriter();
		StreamResult streamResult = new StreamResult(stringWriter);
		DOMSource domSource = new DOMSource(document);
		transformer.transform(domSource, streamResult);
		return stringWriter.toString();
	}
	
	/**
	 * <p>
	 *    Formats an XML org.w3c.dom Document into a pretty-printed String.
	 * </p>
	 * <p>
	 *    "pretty-print" means proper line breaks, indenting, spacing, etc, so
	 *    a single-line XML mess becomes human readable
	 * </p>
	 * <p>
	 *    Cribbed from
	 *    <a href="http://www.chipkillmar.net/2009/03/25/pretty-print-xml-from-a-dom/">http://www.chipkillmar.net/2009/03/25/pretty-print-xml-from-a-dom/</a>
	 * </p>
	 *
	 * @param document org.w3c.dom Document to convert
	 * @param configDocument XSLT document that dictates how to convert the document
	 * @return Pretty-print formatted String of document
	 * 
	 * @throws TransformerException If one of the documents could not be parsed
	 */
	public static String prettyPrintWithTrAX(Document document, Document configDocument) throws TransformerException {
		// Pretty-prints a DOM document to XML using TrAX.
		// Note that a stylesheet is needed to make formatting reliable.
		
		// Step 1 - get a DOMSource representation of the pretty print
		// XSL document
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		DOMSource ppSrc = new DOMSource(configDocument);
		//Transformer ppTransformer = transformerFactory.newTransformer(ppSrc);
		
		ppSrc.setSystemId("internal_pretty_print_document");
		
		// Now create a transformer that will use the pretty print document
		// to convert the real document
		Transformer transformer = transformerFactory.newTransformer(ppSrc);
		
		// Now transform the actual document
		StringWriter stringWriter = new StringWriter();
		StreamResult streamResult = new StreamResult(stringWriter);
		DOMSource domSource = new DOMSource(document);
		transformer.transform(domSource, streamResult);
		return stringWriter.toString();
	}
	
	/**
	 * <p>
	 *    Formats an XML org.w3c.dom Document into a pretty-printed String.
	 * </p>
	 * <p>
	 *    "pretty-print" means proper line breaks, indenting, spacing, etc, so
	 *    a single-line XML mess becomes human readable
	 * </p>
	 * <p>
	 *    Cribbed from
	 *    <a href="http://www.chipkillmar.net/2009/03/25/pretty-print-xml-from-a-dom/">http://www.chipkillmar.net/2009/03/25/pretty-print-xml-from-a-dom/</a>
	 * </p>
	 * <p>
	 *    Note that a default "pretty-print" XSLT document will be created for the conversion. This is normally fine for most applications.
	 * </p>
	 *
	 * @param document Document to convert
	 * @return Pretty-print formatted String of document
	 * @throws TransformerException If document couldn't be parsed
	 * @throws ParserConfigurationException If document couldn't be parsed
	 */
	public static String prettyPrintWithTrAX(Document document) throws TransformerException, ParserConfigurationException {
		Document prettyPrintXsl = XalanUtils.GeneratePrettyPrintDocument();
		
		return prettyPrintWithTrAX(document, prettyPrintXsl);
	}
	
	/**
	 * <p>
	 *    Generates a default "pretty-print" XSLT document for use in
	 *    converting Documents.
	 * </p>
	 * <p>
	 *    "pretty-print" means proper line breaks, indenting, spacing, etc, so
	 *    a single-line XML mess becomes human readable
	 * </p>
	 *
	 * @return Document representing a "pretty-print" XSLT that dictates how to convert another document
	 *
	 * @throws ParserConfigurationException If document couldn't be built
	 */
	public static Document GeneratePrettyPrintDocument() throws ParserConfigurationException {
		final String XSL_NAMESPACE = "http://www.w3.org/1999/XSL/Transform";
		final String XALAN_NAMESPACE = "http://xml.apache.org/xslt";
		final String DEFAULT_NAMESPACE = null;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document prettyPrintXsl = builder.newDocument();
		Element rootElem = (Element)prettyPrintXsl.createElementNS(XSL_NAMESPACE, "xsl:stylesheet");
		rootElem.setAttributeNS(DEFAULT_NAMESPACE, "version", "1.0");
		prettyPrintXsl.appendChild(rootElem);
		
		Element outputElem = (Element)prettyPrintXsl.createElementNS(XSL_NAMESPACE, "xsl:output");
		outputElem.setAttributeNS(DEFAULT_NAMESPACE, "method", "xml");
		outputElem.setAttributeNS(DEFAULT_NAMESPACE, "encoding", "UTF-8");
		outputElem.setAttributeNS(DEFAULT_NAMESPACE, "indent", "yes");
		outputElem.setAttributeNS(XALAN_NAMESPACE, "xalan:indent-amount", "4");
		rootElem.appendChild(outputElem);
		
		Element stripSpaceElem = (Element)prettyPrintXsl.createElementNS(XSL_NAMESPACE, "xsl:strip-space");
		stripSpaceElem.setAttributeNS(DEFAULT_NAMESPACE, "elements", "*");
		rootElem.appendChild(stripSpaceElem);
		
		Element templateElem = (Element)prettyPrintXsl.createElementNS(XSL_NAMESPACE, "xsl:template");
		templateElem.setAttributeNS(DEFAULT_NAMESPACE, "match", "@*|node()");
		rootElem.appendChild(templateElem);
		
		Element copyElem = (Element)prettyPrintXsl.createElementNS(XSL_NAMESPACE, "xsl:copy");
		templateElem.appendChild(copyElem);
		
		Element applyElem = (Element)prettyPrintXsl.createElementNS(XSL_NAMESPACE, "xsl:apply-templates");
		applyElem.setAttributeNS(DEFAULT_NAMESPACE, "select", "@*|node()");
		copyElem.appendChild(applyElem);
		
		// TransformerFactory transformerFactory = TransformerFactory.newInstance();
		// Transformer transformer = transformerFactory.newTransformer();
		// StringWriter stringWriter = new StringWriter();
		// StreamResult streamResult = new StreamResult(stringWriter);
		// DOMSource domSource = new DOMSource(prettyPrintXsl);
		// transformer.transform(domSource, streamResult);
		// System.out.println("PRETTY PRINT XSL: '" + stringWriter.toString() + "'.");
		
		return prettyPrintXsl;
	}
	
	/**
	 * <p>
	 *    Formats an XML org.w3c.dom Node into a pretty-printed String.
	 * </p>
	 * <p>
	 *    "pretty-print" means proper line breaks, indenting, spacing, etc, so
	 *    a single-line XML mess becomes human readable
	 * </p>
	 * <p>
	 *    Cribbed from
	 *    <a href="http://www.chipkillmar.net/2009/03/25/pretty-print-xml-from-a-dom/">http://www.chipkillmar.net/2009/03/25/pretty-print-xml-from-a-dom/</a>
	 * </p>
	 *
	 * @param node Node to convert
	 * @param prettyPrintDocument org.w3c.dom Document representing a "pretty-print" XSLT that dictates how to convert the document
	 * @return Pretty-print formatted String of document
	 * 
	 * @throws TransformerException If document could not be read
	 */
	public static String prettyPrintWithTrAX(Node node, Document prettyPrintDocument) throws TransformerException {
		// Pretty-prints a DOM document to XML using TrAX.
		// Note that a stylesheet is needed to make formatting reliable.
		
		// Step 1 - get a DOMSource representation of the pretty print
		// XSL document
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		DOMSource ppSrc = new DOMSource(prettyPrintDocument);
		//Transformer ppTransformer = transformerFactory.newTransformer(ppSrc);
		
		ppSrc.setSystemId("internal_pretty_print_document");
		
		// Now create a transformer that will use the pretty print document
		// to convert the real document
		Transformer transformer = transformerFactory.newTransformer(ppSrc);
		
		// Now transform the actual document
		StringWriter stringWriter = new StringWriter();
		StreamResult streamResult = new StreamResult(stringWriter);
		DOMSource domSource = new DOMSource(node);
		transformer.transform(domSource, streamResult);
		return stringWriter.toString();
	}
	
	/**
	 * <p>
	 *    Formats an XML org.w3c.dom Node into a pretty-printed String.
	 * </p>
	 * <p>
	 *    "pretty-print" means proper line breaks, indenting, spacing, etc, so a single-line XML mess becomes human readable
	 * </p>
	 * <p>
	 *    Cribbed from
	 *    <a href="http://www.chipkillmar.net/2009/03/25/pretty-print-xml-from-a-dom/">http://www.chipkillmar.net/2009/03/25/pretty-print-xml-from-a-dom/</a>
	 * </p>
	 *
	 * @param node Node to convert
	 * @return Pretty-print formatted String of document
	 * @throws TransformerException If node could not be converted
	 * @throws ParserConfigurationException If node could not be converted
	 */
	public static String prettyPrintWithTrAX(Node node) throws TransformerException, ParserConfigurationException {
		Document prettyPrintXsl = XalanUtils.GeneratePrettyPrintDocument();
		
		return prettyPrintWithTrAX(node, prettyPrintXsl);
	}
	
	/**
	 * <p>
	 *    Returns all the nodes selected by the given xpath
	 * </p>
	 * <p>
	 *    Returns a List instead of a NodeList so that things like Iterators
	 *    will work as expected.
	 * </p>
	 * 
	 * @param context Node to begin search at
	 * @param xpath XPath expression to evaluate
	 * @return List of Nodes matching XPath expression
	 * @throws TransformerException If XML selection fails
	 */
	public static List<Node> getNodesFromXPath(Node context, String xpath) throws TransformerException {
		List<Node> ret = new ArrayList<Node>();
		
		NodeList list = XPathAPI.selectNodeList(context, xpath);
		if(list != null){
			for(int idx=0;idx<list.getLength();idx++){
				Node node = list.item(idx);
				ret.add(node);
			}
		}
		
		return ret;
	}
	
	/**
	 * <p>
	 *    Gets a string value from a document using a given XPath
	 * </p>
	 * 
	 * <p>
	 *    If the xpath resolves to a text node or an attribute node, the
	 *    return value will be the node value for that node.
	 * </p>
	 * 
	 * <p>
	 *    For any other node type, this will attempt to get the first child
	 *    of the node (assuming that is the textual value for the node), and
	 *    return that child's value.
	 * </p>
	 * 
	 * @param context Node to search within
	 * @param xpath XPath statement to evaluate
	 * @return String value selected from the XML
	 * @throws TransformerException If XML selection fails
	 */
	public static String getStringFromXPath(Node context, String xpath) throws TransformerException {
		List<Node> list = XalanUtils.getNodesFromXPath(context, xpath);
		if((list == null) || (list.size() < 1)){
			return null;
		}
		
		Node node = list.get(0);
		if(node == null){
			// No idea why this should ever happen...
			return null;
		}
		
		if(Node.ATTRIBUTE_NODE == node.getNodeType()){
			return node.getNodeValue();
		}
		
		if(Node.TEXT_NODE == node.getNodeType()){
			return node.getNodeValue();
		}
		
		Node child = node.getFirstChild();
		if(child != null){
			return child.getNodeValue();
		}
		
		return null;
	}
	
	/**
	 * <p>
	 *    Searches XML for a String value, and then attempts to convert
	 *    that to a Long value.
	 * </p>
	 * 
	 * <p>
	 *    If the xpath resolves to a text node or an attribute node, the
	 *    return value will be the node value for that node.
	 * </p>
	 * 
	 * <p>
	 *    For any other node type, this will attempt to get the first child
	 *    of the node (assuming that is the textual value for the node), and
	 *    return that child's value.
	 * </p>
	 * 
	 * @param context Document or node to search in
	 * @param xpath XPath statement to evaluate
	 * @return Long representing String value found by XPath
	 * @throws TransformerException If XML selection fails
	 */
	public static Long getLongFromXPath(Node context, String xpath) throws TransformerException {
		String strVal = getStringFromXPath(context, xpath);
		
		if(strVal != null){
			try{
				Long retVal = new Long(strVal);
				return retVal;
			}
			catch(Exception e){
				throw new TransformerException("Couldn't convert value '" + strVal + "' to a Long.  Exception " + e + " caught.");
			}
		}
		
		return null;
	}
	
	/**
	 * <p>
	 *    Searches XML for a String value, and then attempts to convert
	 *    that to an Integer value.
	 * </p>
	 * 
	 * <p>
	 *    If the xpath resolves to a text node or an attribute node, the
	 *    return value will be the node value for that node.
	 * </p>
	 * 
	 * <p>For any other node type, this will attempt to get the first child
	 *    of the node (assuming that is the textual value for the node), and
	 *    return that child's value.</p>
	 * 
	 * @param context Document or node to search in
	 * @param xpath XPath statement to evaluate
	 * @return Integer representing String value found by XPath
	 * @throws TransformerException
	 */
	public static Integer getIntegerFromXPath(Node context, String xpath) throws TransformerException {
		String strVal = getStringFromXPath(context, xpath);
		
		if(strVal != null){
			try{
				Integer retVal = new Integer(strVal);
				return retVal;
			}
			catch(Exception e){
				throw new TransformerException("Couldn't convert value '" + strVal + "' to an Integer.  Exception " + e + " caught.");
			}
		}
		return null;
	}
	
	/**
	 * <p>
	 *    Searches XML for a String value, and then attempts to convert
	 *    that to a Boolean value.
	 * </p>
	 * 
	 * <p>
	 *    If the xpath resolves to a text node or an attribute node, the
	 *    return value will be the node value for that node.
	 * </p>
	 * 
	 * <p>
	 *    For any other node type, this will attempt to get the first child
	 *    of the node (assuming that is the textual value for the node), and
	 *    return that child's value.
	 * </p>
	 * 
	 * @param context Document or node to search in
	 * @param xpath XPath statement to evaluate
	 * @return Boolean representing String value found by XPath
	 * @throws TransformerException
	 */
	public static Boolean getBooleanFromXPath(Node context, String xpath) throws TransformerException {
		String strVal = getStringFromXPath(context, xpath);
		
		if(strVal != null){
			if(strVal.equalsIgnoreCase("true")){
				return true;
			}
			else if(strVal.equalsIgnoreCase("false")){
				return false;
			}
		}
		
		return null;
	}
	
	/**
	 * <p>
	 *    Builds an XML Element from a String containing XML
	 * </p>
	 * 
	 * @param xml String with XML to parse
	 * @return Element representing XML passed in
	 * @throws ParserConfigurationException If XML could not be parsed
	 * @throws SAXException If XML could not be parsed
	 * @throws IOException If String could not be read
	 */
	public static Element extractXmlFromString(String xml) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder        builder = factory.newDocumentBuilder();
		InputSource            source  = new InputSource();
		source.setCharacterStream(new StringReader(xml));
		
		Document doc = builder.parse(source);
		
		return doc.getDocumentElement();
	}
	
	/**
	 * This method ensures that the output String has only
	 * valid XML unicode characters as specified by the
	 * XML 1.0 standard. For reference, please see
	 * <a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">the
	 * standard</a>. This method will return an empty
	 * String if the input is null or empty.
	 * 
	 * @param in The String whose non-valid characters we want to remove.
	 * @return The in String, stripped of non-valid characters.
	*/
	public static String stripNonValidXMLCharacters(String in) {
		StringBuffer out = new StringBuffer(); // Used to hold the output.
		char current; // Used to reference the current character.
		
		if (in == null || ("".equals(in))) return ""; // vacancy test.
		for (int i = 0; i < in.length(); i++) {
			current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
			if ((current == 0x9) ||
					(current == 0xA) ||
					(current == 0xD) ||
					((current >= 0x20) && (current <= 0xD7FF)) ||
					((current >= 0xE000) && (current <= 0xFFFD)) ||
					((current >= 0x10000) && (current <= 0x10FFFF))){
				out.append(current);
			}
		}
		return out.toString();
	}
	
	/**
	 * <p>
	 *    Removes characters that may be legal UTF-8, but will cause problems for the Xalan parser
	 * </p>
	 * 
	 * @param doc Document to strip
	 */
	public static void stripNonValidXMLCharacters(Document doc){
		Element docElem = doc.getDocumentElement();
		
		Node node = docElem.getFirstChild();
		while(node != null){
			XalanUtils.stripNonValidXMLCharacters(node);
			node = node.getNextSibling();
		}
	}
	
	/**
	 * <p>
	 *    Removes characters that may be legal UTF-8, but will cause problems for the Xalan parser
	 * </p>
	 * 
	 * @param node Node to strip
	 */
	public static void stripNonValidXMLCharacters(Node node){
		if(node == null){
			return;
		}
		
		if(node.getNodeType() == Node.TEXT_NODE){
			String stripped = stripNonValidXMLCharacters(node.getNodeValue());
			node.setNodeValue(stripped);
		}
		else{
			Node child = node.getFirstChild();
			while(child != null){
				stripNonValidXMLCharacters(child);
				child = child.getNextSibling();
			}
		}
	}
}
