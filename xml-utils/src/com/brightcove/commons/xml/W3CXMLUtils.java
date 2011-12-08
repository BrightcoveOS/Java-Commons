package com.brightcove.commons.xml;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <p>
 *    A utility class to make working with W3C XML libraries easier
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class W3CXMLUtils {
	public static Element getFirstElementChild(Node node){
		if(node == null){
			return null;
		}
		
		Node child = node.getFirstChild();
		while(child != null){
			if(child.getNodeType() == Node.ELEMENT_NODE){
				return (Element)child;
			}
			child = child.getNextSibling();
		}
		return null;
	}
	
	public static Element getNextElementSibling(Node node){
		if(node == null){
			return null;
		}
		
		Node sibling = node.getNextSibling();
		while(sibling != null){
			if(sibling.getNodeType() == Node.ELEMENT_NODE){
				return (Element)sibling;
			}
			sibling = sibling.getNextSibling();
		}
		return null;
	}
	
	public static String getStringValue(Node node){
		if(node == null){
			return null;
		}
		
		// For an element (e.g. <foo>bar</foo>), return the value of the first
		// child node (which should be the text node "bar" or null if the
		// element is empty).
		if(node.getNodeType() == Node.ELEMENT_NODE){
			Node child = node.getFirstChild();
			if(child != null){
				String ret = child.getNodeValue();
				if(ret != null){
					return ret;
				}
				return "";
			}
			return "";
		}
		
		// For an attribute (e.g. <foo attribute="bar"/>), return the value
		// of the attribute
		if(node.getNodeType() == Node.ATTRIBUTE_NODE){
			return ((Attr)node).getNodeValue();
		}
		
		// For everything else, just do your best to return the node value
		return node.getNodeValue();
	}
}
