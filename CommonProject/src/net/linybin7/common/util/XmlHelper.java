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
	// �Ƿ��ڽ�����ʱ�������֤
	private static boolean VALIDATING_OF_PARSER = false;

	private XmlHelper() {
	}

	/**
	 * �����յ�Document����
	 * 
	 * @return �յ�Document����
	 */
	public static Document createDocument() {
		return DocumentHelper.createDocument();
	}

	/**
	 * ��ָ�����ļ�·������Document����
	 * 
	 * @return XML���ݶ�Ӧ��Document����
	 * @param path
	 *            XML����·��
	 */
	public static Document createDocument(String path) throws Exception {
		return createDocument(new File(path));
	}

	/**
	 * ��ָ�����ļ����󴴽�Document����
	 * 
	 * @return XML���ݶ�Ӧ��Document����
	 * @param path
	 *            XML�����ļ�����
	 */
	public static Document createDocument(File file) throws Exception {
		return createDocument(new FileInputStream(file));
	}

	/**
	 * ��ָ��������������Document����
	 * 
	 * @return XML���ݶ�Ӧ��Document����
	 * @param path
	 *            XML��������������
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
	 * ����XML�ַ����õ�Document����
	 * 
	 * @return XML���ݶ�Ӧ��Document����
	 * @param xml
	 *            XML�ַ�������
	 */
	public static Document parse(String xml) throws DocumentException {
		return DocumentHelper.parseText(xml);
	}

	/**
	 * �õ�ָ���ڵ���ָ��·����Ԫ��ֵ
	 * 
	 * @return �ڵ�ֵ
	 * @param parent
	 *            ��ʼ�ڵ�
	 * @param xPath
	 *            XPath·��
	 */
	public static String getNodeText(Node parent, String xPath) {
		Node selectNode = parent.selectSingleNode(xPath);
		if (null == selectNode) {
			return null;
		}
		return selectNode.getText();
	}

	/**
	 * ��ʽ��XML�ַ���
	 * 
	 * @return ��ʽ�����XML�ַ�������
	 * @param xml
	 *            ����ʽ����XML�ַ�������
	 */
	public static String format(String xml) throws Exception {
		return format(xml, null);
	}

	/**
	 * ��ʽ��XML�ַ���Ϊָ�����ַ���
	 * 
	 * @return ��ʽ�����XML�ַ�������
	 * @param xml
	 *            ����ʽ����XML�ַ�������
	 * @param character
	 *            ��ʽ��XML�ַ������ݺ�ָ�����ַ���
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
	 * �����ڽ����������Ƿ���֤
	 * 
	 * @param validating
	 *            ָ���ڽ����������Ƿ���֤
	 */
	public static void setValidating(boolean validating) {
		VALIDATING_OF_PARSER = validating;
	}

	/**
	 * ȷ���ڽ����������Ƿ������֤
	 * 
	 * @return ָ���ڽ����������Ƿ������֤
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
