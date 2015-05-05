package com.example.switchyard.switchyard_example;

import javax.xml.transform.TransformerFactory;

import javax.xml.transform.dom.DOMResult;

import javax.xml.transform.stream.StreamSource;

import org.switchyard.annotations.Transformer;

import org.w3c.dom.Document;

import org.w3c.dom.Element;

import org.w3c.dom.NodeList;

import java.io.StringReader;

public class Transformers {

	/**
	 * 
	 * Transform from payload type is
	 * {urn:com.example.switchyard:switchyard-example:1.0}sayHello to a String
	 * 
	 * @param sayHello
	 * 
	 * @return String
	 */

	@Transformer(from = "{urn:com.example.switchyard:switchyard-example:1.0}sayHello")
	public String transform(Element from) {

		return new String(getElementValue(from, "urn:string"));

	}

	/**
	 * 
	 * Transform String to
	 * {urn:com.example.switchyard:switchyard-example:1.0}sayHelloResponse
	 * 
	 * <p/>
	 * 
	 * @param String
	 * 
	 * @return 
	 *         {http://webservices.samples.jboss.org/}HelloWorld_sayHelloResponse
	 */

	@Transformer(to = "{urn:com.example.switchyard:switchyard-example:1.0}sayHelloResponse")
	public Element transform(String helloString) {

		StringBuffer ackXml = new StringBuffer()
				.append("<hello:sayHelloResponse xmlns:hello=\"urn:com.example.switchyard:switchyard-example:1.0\">")
				.append("<return>" + helloString + "</return>")
				.append("</hello:sayHelloResponse>");

		return toElement(ackXml.toString());

	}

	/**
	 * 
	 * Returns the text value of a child node of parent.
	 */

	private String getElementValue(Element parent, String elementName) {

		String value = null;
		NodeList nodes = parent.getElementsByTagName(elementName);

		if (nodes.getLength() > 0) {
			value = nodes.item(0).getChildNodes().item(0).getNodeValue();

		}

		return value;

	}

	private Element toElement(String xml) {

		DOMResult dom = new DOMResult();

		try {
			TransformerFactory.newInstance().newTransformer().transform(
			new StreamSource(new StringReader(xml)), dom);

		} catch (Exception ex) {
			ex.printStackTrace();

		}

		return ((Document) dom.getNode()).getDocumentElement();

	}

}