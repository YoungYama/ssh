package com.yzz.test;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parse and create xml string using java xml api.
 * 
 * @author kaige
 */
public class XMLTest {

	private static XPath xPath;
	private static DocumentBuilder docbuilder;
	private static DocumentBuilderFactory dbfactory;
	private static DocumentBuilderFactory bFactory;
	private static DocumentBuilder builder;
	private static TransformerFactory tfactory;
	private static Transformer transformer;

	public static void main(String[] args) throws Throwable {

		dbfactory = DocumentBuilderFactory.newInstance();
		docbuilder = dbfactory.newDocumentBuilder();
		xPath = XPathFactory.newInstance().newXPath();
		bFactory = DocumentBuilderFactory.newInstance();
		builder = bFactory.newDocumentBuilder();
		tfactory = TransformerFactory.newInstance();
		transformer = tfactory.newTransformer();

		System.out.println("--------------------jdom parse xml test--------------------");
		parseXml();

		System.out.println("--------------------jdom create xml test--------------------");
		createXml();
	}

	/**
	 * Java Dom parse xml test
	 * 
	 * @throws Throwable
	 */
	static void parseXml() throws Throwable {

		String xml = "<?xml version='1.0' encoding='UTF-8'?>" + "<employees>" + "<employee id='0001' type='staff'>"
				+ "<fields>" + "<field name='name' depth='1'>s001</field>" + "<field name='sex' depth='2'>1</field>"
				+ "<field name='mail' depth='3'>s001@xxx.com</field>" + "</fields>" + "</employee>"
				+ "<employee id='0002' type='leader'>" + "<fields>" + "<field name='name' depth='1'>s002</field>"
				+ "<field name='sex' depth='2'>2</field>" + "<field name='mail' depth='3'>s002@xxx.com</field>"
				+ "</fields>" + "</employee>" + "<employee id='0003' type='ceo'>" + "<fields>"
				+ "<field name='name' depth='1'>s003</field>" + "<field name='sex' depth='2'>1</field>"
				+ "<field name='mail' depth='3'>s003@xxx.com</field>" + "</fields>" + "</employee>" + "</employees>";

		Document doc = docbuilder.parse(new ByteArrayInputStream(xml.getBytes(Charset.defaultCharset())));
		
		String expression = "employees/employee[@type='staff']|employees/employee[@type='leader']";

		NodeList nodeList = selectNodeList(doc, expression);

		for (int i = 0; i < nodeList.getLength(); i++) {
			Element node = (Element) nodeList.item(i);
			System.out.println(node.getAttribute("id"));

			NodeList childrenNodeList = selectNodeList(node, "fields/field[@depth>2]");
			for (int j = 0; j < childrenNodeList.getLength(); j++) {
				Element child = (Element) childrenNodeList.item(j);

				System.out.print(child.getAttribute("name"));
				System.out.print("-->");
				System.out.println(child.getTextContent());
			}
		}

		System.out.println("Select a single node-----------------");
		Node singleNode = selectSingleNode(doc, "/employees/employee/fields/field[.='s001']");
		System.out.println(singleNode.getTextContent());

	}

	/**
	 * Select multiple nodes from a parent node.
	 * 
	 * @param item
	 *            Parent node
	 * @param xpath
	 *            XPath expression
	 * @return Result
	 * @throws Throwable
	 *             Exception
	 */
	private static NodeList selectNodeList(Object item, String xpath) throws Throwable {

		return (NodeList) xPath.compile(xpath).evaluate(item, XPathConstants.NODESET);
	}

	/**
	 * Select a single node from a parent node.
	 * 
	 * @param item
	 *            Parent node
	 * @param xpath
	 *            XPath expression
	 * @return Result
	 * @throws Throwable
	 *             Exception
	 */
	private static Node selectSingleNode(Object item, String xpath) throws Throwable {

		return (Node) xPath.compile(xpath).evaluate(item, XPathConstants.NODE);
	}

	/**
	 * Create XML document.
	 * 
	 * @throws Throwable
	 */
	static void createXml() throws Throwable {

		Document doc = builder.newDocument();

		Element root = doc.createElement("employees");
		doc.appendChild(root);

		Element elemObject = doc.createElement("employee");
		root.appendChild(elemObject);
		elemObject.setAttribute("id", "s001");
		elemObject.setAttribute("type", "staff");
		Element elemFields = doc.createElement("fields");
		elemObject.appendChild(elemFields);
		Element elemField01 = doc.createElement("field");
		elemFields.appendChild(elemField01);
		elemField01.setAttribute("name", "name");
		elemField01.setAttribute("depth", "1");
		elemField01.setTextContent("staff 001");

		// create a StringWriter for the output
		StringWriter outWriter = new StringWriter();
		StreamResult result = new StreamResult(outWriter);
		transformer.transform(new DOMSource(root), result);
		String resXml = outWriter.getBuffer().toString();

		// transformer.setOutputProperty("encoding", "UTF-8");
		System.out.println(resXml);

	}
}
