package net.linybin7.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


//import com.sun.org.apache.xerces.internal.impl.Constants;

/**
 * DOM工具类
 * 
 * @author JackenCai
 * 
 */
public final class DOMUtil {
	public static String encoding = "GBK";

	private DOMUtil() {
	}

	/**
	 * 创建空的Document对象
	 * 
	 * @return
	 */
	public static Document createDoc() {
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding(encoding);
		return doc;
	}

	/**
	 * 从指定的文件路径创建Document对象,不作校验
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static Document createDoc(String path) throws Exception {
		return createDoc(path, null);
	}

	/**
	 * 从指定的文件路径创建Document对象
	 * 
	 * @param path
	 * @param schemaURL
	 * @return
	 * @throws Exception
	 */
	public static Document createDoc(String path, String schemaURL)
			throws Exception {
		return createDoc(new File(path), schemaURL);
	}

	/**
	 * 从指定的文件对象创建Document对象,不作校验
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static Document createDoc(File file) throws Exception {
		return createDoc(file, null);
	}

	/**
	 * 从指定的文件对象创建Document对象
	 * 
	 * @param file
	 * @param schemaURL
	 * @return
	 * @throws Exception
	 */
	public static Document createDoc(File file, String schemaURL)
			throws Exception {
		return createDoc(new FileInputStream(file), schemaURL);
	}

	/**
	 * 从指定的输入流创建Document对象,不作校验
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static Document createDoc(InputStream is) throws Exception {
		return createDoc(is, null);
	}

	/**
	 * 创建document，忽略DTD
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
//	public static Document createDocIgnoreDTD(File file) throws Exception {
//		SAXReader reader = new SAXReader();
//		Document doc = null;
//		reader.setFeature(Constants.XERCES_FEATURE_PREFIX
//				+ Constants.LOAD_EXTERNAL_DTD_FEATURE, false);
//		return doc = reader.read(file);
//	}

	private static class XMLErrorHandler implements ErrorHandler {

		public void error(SAXParseException exception) throws SAXException {
			System.out.println("错误：第" + exception.getLineNumber() + "行;第"
					+ exception.getColumnNumber() + "列; 错误信息:"
					+ exception.getLocalizedMessage());
			throw new SAXException();
		}

		public void fatalError(SAXParseException exception) throws SAXException {
			System.out.println("失败：第" + exception.getLineNumber() + "行;第"
					+ exception.getColumnNumber() + "列; 失败信息:"
					+ exception.getLocalizedMessage());
			throw new SAXException();
		}

		public void warning(SAXParseException exception) throws SAXException {
			System.out.println("警告：第" + exception.getLineNumber() + "行;第"
					+ exception.getColumnNumber() + "列; 信息:"
					+ exception.getLocalizedMessage());
		}

	}

	private static SAXReader getReader(String schemaURL) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);

		SchemaFactory schemaFactory = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema");

		URL url = DOMUtil.class.getClassLoader().getResource(schemaURL);
		Schema schema = schemaFactory.newSchema(new StreamSource(url
				.openStream(), url.toString()));

		factory.setSchema(schema);

		SAXParser parser = factory.newSAXParser();

		SAXReader reader = new SAXReader(parser.getXMLReader());
		// XMLErrorHandler errorHandler = new XMLErrorHandler();
		// reader.setErrorHandler(errorHandler);
		return reader;
	}

	/**
	 * 从指定的输入流创建Document对象
	 * 
	 * @param is
	 * @param schemaURL
	 * @return
	 * @throws Exception
	 */
	public static Document createDoc(InputStream is, String schemaURL)
			throws Exception {
		SAXReader reader = new SAXReader();
		if (!StringUtils.isNnull(schemaURL)) {
			reader = getReader(schemaURL);
		}
		reader.setEncoding(encoding);
		return reader.read(is);
	}

	/**
	 * 从指定的输入流创建Document对象
	 * 
	 * @param reader
	 * @return
	 * @throws Exception
	 */
	public static Document createDoc(Reader reader) throws Exception {
		return createDoc(reader, null);
	}

	/**
	 * 从指定的输入流创建Document对象
	 * 
	 * @param reader
	 * @param schemaURL
	 * @return
	 * @throws Exception
	 */
	public static Document createDoc(Reader reader, String schemaURL)
			throws Exception {
		SAXReader saxReader = new SAXReader();
		if (!StringUtils.isNnull(schemaURL)) {
			saxReader = getReader(schemaURL);
		}
		return saxReader.read(reader);
	}

	/**
	 * 解析XML字符串得到Document对象
	 * 
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	public static Document parse(String xml, String schemaURL) throws Exception {
		StringReader reader = new StringReader(xml);
		return createDoc(reader, schemaURL);
	}

	/**
	 * 得到指定节点下指定路径的元素值
	 * 
	 * @param parent
	 * @param xPath
	 * @return
	 */
	public static String getNodeText(Node parent, String xPath) {
		Node selectNode = parent.selectSingleNode(xPath);
		if (null == selectNode) {
			return null;
		}
		return StringUtils.getString(selectNode.getText());
	}

	/**
	 * 格式化XML字符串
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static String format(String xml) throws Exception {
		return format(xml, null);
	}

	/**
	 * 格式化XML字符串为指定的字符集
	 * 
	 * @param xml
	 * @param character
	 * @return
	 * @throws Exception
	 */
	public static String format(String xml, String character) throws Exception {
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		if (null != character && character.trim().length() > 0) {
			outputFormat.setEncoding(character);
		}
		StringWriter stringWriter = new StringWriter();
		XMLWriter writer = new XMLWriter(stringWriter, outputFormat);
		writer.write(parse(xml, null));
		return stringWriter.toString();
	}

	/**
	 * 将document写到文件
	 * 
	 * @param doc
	 * @param file
	 * @throws Exception
	 */
	public static void write(Document doc, File file) throws Exception {
		FileWriter fw = new FileWriter(file);
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		outputFormat.setEncoding(encoding);
		XMLWriter writer = new XMLWriter(fw, outputFormat);
		writer.write(doc);
		writer.close();
	}

	/**
	 * 将字符串写到文件
	 * 
	 * @param content
	 * @param file
	 * @throws Exception
	 */
	public static void write(String content, File file) throws Exception {
		write(parse(content, null), file);
	}

	/**
	 * 获得指定文件的xml串
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String getXML(File file) throws Exception {
		return createDoc(file).asXML();
	}

	/**
	 * 清空子节点
	 * 
	 * @param ele
	 */
	public static void clearChildren(Element ele) {
		if (ele != null) {
			ele.clearContent();
		}
	}

	/**
	 * 根据属性名与属性值获得子元素
	 * 
	 * @param ele
	 * @param attrName
	 * @param attrValue
	 * @return
	 */
	public static Element getElementByAttr(Element ele, String attrName,
			String attrValue) {
		if (ele != null && !StringUtils.isNnull(attrName)
				&& !StringUtils.isNnull(attrValue)) {
			List children = ele.elements();
			for (int i = 0; i < children.size(); i++) {
				Element subEle = (Element) children.get(i);
				if (attrValue.equals(subEle.attributeValue(attrName))) {
					return subEle;
				}
			}
		}
		return null;
	}

	/**
	 * 获得子节点text内容,如果没有返回null
	 * @param parent
	 * @param name
	 * @return
	 */
	public static String elementStr(Element parent, String name) {
		return elementStr(parent, name, null);
	}

	/**
	 * 获得子节点text内容，如果没有返回默认值
	 * @param parent
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String elementStr(Element parent, String name,
			String defaultValue) {
		if (parent == null || name == null
				|| StringUtils.isNnull(parent.elementText(name))) {
			return defaultValue;
		}
		return parent.elementText(name).trim();
	}

	/**
	 * 获得对应子节点的int值
	 * @param parent
	 * @param elementName
	 * @param defValue
	 * @return
	 */
	public static int elementInt(Element parent, String elementName,
			int defValue) {
		String value = parent.elementTextTrim(elementName);
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return defValue;
	}

	/**
	 * 获得对应子节点的int值
	 * @param parent
	 * @param elementName
	 * @return
	 */
	public static int elementInt(Element parent, String elementName) {
		return elementInt(parent, elementName, Integer.MIN_VALUE);
	}

	/**
	 * 获得对应子节点的boolean值
	 * @param parent
	 * @param elementName
	 * @param defValue
	 * @return
	 */
	public static boolean elementBoolean(Element parent, String elementName,
			boolean defValue) {
		String value = parent.elementTextTrim(elementName);
		if (StringUtils.isNnull(value)) {
			return defValue;
		}
		if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes")) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 获得对应子节点的boolean值
	 * @param parent
	 * @param elementName
	 * @return
	 */
	public static boolean elementBoolean(Element parent, String elementName) {
		return elementBoolean(parent, elementName, false);
	}

	/**
	 *  获得元素属性值,没有返回空
	 * @param element
	 * @param name
	 * @return
	 */
	public static String getAttribute(Element element, String name) {
		return getAttribute(element, name, null);
	}

	/**
	 * 获得元素属性值,没有返回默认值
	 * @param element
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String getAttribute(Element element, String name,
			String defaultValue) {
		if (element == null || name == null
				|| element.attributeValue(name) == null
				|| element.attributeValue(name).trim().length() == 0) {
			return defaultValue;
		}
		return element.attributeValue(name).trim();
	}
}
