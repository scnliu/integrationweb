package net.linybin7.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.FileSystemResource;


public class XmlHelper {
	// 是否在解析的时候进行验证
	private static boolean VALIDATING_OF_PARSER = false;

	private XmlHelper() {
	}

	/**
	 * 创建空的Document对象
	 * 
	 * @return 空的Document对象
	 */
	public static Document createDocument() {
		return DocumentHelper.createDocument();
	}

	/**
	 * 从指定的文件路径创建Document对象
	 * 
	 * @return XML数据对应的Document对象
	 * @param path
	 *            XML数据路径
	 */
	public static Document createDocument(String path) throws Exception {
		return createDocument(new File(path));
	}

	/**
	 * 从指定的文件对象创建Document对象
	 * 
	 * @return XML数据对应的Document对象
	 * @param path
	 *            XML数据文件对象
	 */
	public static Document createDocument(File file) throws Exception {
		return createDocument(new FileInputStream(file));
	}

	/**
	 * 从指定的输入流创建Document对象
	 * 
	 * @return XML数据对应的Document对象
	 * @param path
	 *            XML数据输入流对象
	 */
	public static Document createDocument(InputStream is) throws Exception {
		SAXReader reader = new SAXReader();
		if (VALIDATING_OF_PARSER) {
			reader.setValidation(true);
			reader.setProperty(
					"http://java.sun.com/xml/jaxp/properties/schemaLanguage",
					"http://www.w3.org/2001/XMLSchema");
		}
		return reader.read(is);
	}

	/**
	 * 解析XML字符串得到Document对象
	 * 
	 * @return XML数据对应的Document对象
	 * @param xml
	 *            XML字符串数据
	 */
	public static Document parse(String xml) throws DocumentException {
		return DocumentHelper.parseText(xml);
	}

	/**
	 * 得到指定节点下指定路径的元素值
	 * 
	 * @return 节点值
	 * @param parent
	 *            开始节点
	 * @param xPath
	 *            XPath路径
	 */
	public static String getNodeText(Node parent, String xPath) {
		Node selectNode = parent.selectSingleNode(xPath);
		if (null == selectNode) {
			return null;
		}
		return selectNode.getText();
	}

	/**
	 * 格式化XML字符串
	 * 
	 * @return 格式化后的XML字符串数据
	 * @param xml
	 *            待格式化的XML字符串数据
	 */
	public static String format(String xml) throws Exception {
		return format(xml, null);
	}

	/**
	 * 格式化XML字符串为指定的字符集
	 * 
	 * @return 格式化后的XML字符串数据
	 * @param xml
	 *            待格式化的XML字符串数据
	 * @param character
	 *            格式化XML字符串数据后指定的字符集
	 */
	public static String format(String xml, String character) throws Exception {
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		if (null != character && character.trim().length() > 0) {
			outputFormat.setEncoding(character);
		}
		StringWriter stringWriter = new StringWriter();
		XMLWriter writer = new XMLWriter(stringWriter, outputFormat);
		writer.write(parse(xml));
		return stringWriter.toString();
	}

	/**
	 * 设置在解析过程中是否验证
	 * 
	 * @param validating
	 *            指定在解析过程中是否验证
	 */
	public static void setValidating(boolean validating) {
		VALIDATING_OF_PARSER = validating;
	}

	/**
	 * 确定在解析过程中是否进行验证
	 * 
	 * @return 指出在解析过程中是否进行验证
	 */
	public static boolean isValidating() {
		return VALIDATING_OF_PARSER;
	}

	public static void main(String args[]) throws IOException, Exception {

		// InputStream is = ClassLoader
		// .getSystemResourceAsStream(SYSCONFIG_FILENAME);
		Document configDocument = XmlHelper
				.createDocument(new FileSystemResource("").getInputStream());
		Integer.parseInt(XmlHelper.getNodeText(configDocument,
				"/System-Config/property[@name=\"blockSize\"]"));

	}

}
