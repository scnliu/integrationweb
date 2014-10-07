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
 * DOM������
 * 
 * @author JackenCai
 * 
 */
public final class DOMUtil {
	public static String encoding = "GBK";

	private DOMUtil() {
	}

	/**
	 * �����յ�Document����
	 * 
	 * @return
	 */
	public static Document createDoc() {
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding(encoding);
		return doc;
	}

	/**
	 * ��ָ�����ļ�·������Document����,����У��
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static Document createDoc(String path) throws Exception {
		return createDoc(path, null);
	}

	/**
	 * ��ָ�����ļ�·������Document����
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
	 * ��ָ�����ļ����󴴽�Document����,����У��
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static Document createDoc(File file) throws Exception {
		return createDoc(file, null);
	}

	/**
	 * ��ָ�����ļ����󴴽�Document����
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
	 * ��ָ��������������Document����,����У��
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static Document createDoc(InputStream is) throws Exception {
		return createDoc(is, null);
	}

	/**
	 * ����document������DTD
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
			System.out.println("���󣺵�" + exception.getLineNumber() + "��;��"
					+ exception.getColumnNumber() + "��; ������Ϣ:"
					+ exception.getLocalizedMessage());
			throw new SAXException();
		}

		public void fatalError(SAXParseException exception) throws SAXException {
			System.out.println("ʧ�ܣ���" + exception.getLineNumber() + "��;��"
					+ exception.getColumnNumber() + "��; ʧ����Ϣ:"
					+ exception.getLocalizedMessage());
			throw new SAXException();
		}

		public void warning(SAXParseException exception) throws SAXException {
			System.out.println("���棺��" + exception.getLineNumber() + "��;��"
					+ exception.getColumnNumber() + "��; ��Ϣ:"
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
	 * ��ָ��������������Document����
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
	 * ��ָ��������������Document����
	 * 
	 * @param reader
	 * @return
	 * @throws Exception
	 */
	public static Document createDoc(Reader reader) throws Exception {
		return createDoc(reader, null);
	}

	/**
	 * ��ָ��������������Document����
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
	 * ����XML�ַ����õ�Document����
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
	 * �õ�ָ���ڵ���ָ��·����Ԫ��ֵ
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
	 * ��ʽ��XML�ַ���
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static String format(String xml) throws Exception {
		return format(xml, null);
	}

	/**
	 * ��ʽ��XML�ַ���Ϊָ�����ַ���
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
	 * ��documentд���ļ�
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
	 * ���ַ���д���ļ�
	 * 
	 * @param content
	 * @param file
	 * @throws Exception
	 */
	public static void write(String content, File file) throws Exception {
		write(parse(content, null), file);
	}

	/**
	 * ���ָ���ļ���xml��
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String getXML(File file) throws Exception {
		return createDoc(file).asXML();
	}

	/**
	 * ����ӽڵ�
	 * 
	 * @param ele
	 */
	public static void clearChildren(Element ele) {
		if (ele != null) {
			ele.clearContent();
		}
	}

	/**
	 * ����������������ֵ�����Ԫ��
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
	 * ����ӽڵ�text����,���û�з���null
	 * @param parent
	 * @param name
	 * @return
	 */
	public static String elementStr(Element parent, String name) {
		return elementStr(parent, name, null);
	}

	/**
	 * ����ӽڵ�text���ݣ����û�з���Ĭ��ֵ
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
	 * ��ö�Ӧ�ӽڵ��intֵ
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
	 * ��ö�Ӧ�ӽڵ��intֵ
	 * @param parent
	 * @param elementName
	 * @return
	 */
	public static int elementInt(Element parent, String elementName) {
		return elementInt(parent, elementName, Integer.MIN_VALUE);
	}

	/**
	 * ��ö�Ӧ�ӽڵ��booleanֵ
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
	 * ��ö�Ӧ�ӽڵ��booleanֵ
	 * @param parent
	 * @param elementName
	 * @return
	 */
	public static boolean elementBoolean(Element parent, String elementName) {
		return elementBoolean(parent, elementName, false);
	}

	/**
	 *  ���Ԫ������ֵ,û�з��ؿ�
	 * @param element
	 * @param name
	 * @return
	 */
	public static String getAttribute(Element element, String name) {
		return getAttribute(element, name, null);
	}

	/**
	 * ���Ԫ������ֵ,û�з���Ĭ��ֵ
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
