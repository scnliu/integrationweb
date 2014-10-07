package net.linybin7.common.config;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.linybin7.common.config.Config;
import net.linybin7.common.config.ConfigChangeListener;
import net.linybin7.common.util.DOMUtil;
import net.linybin7.common.util.ResourceUtil;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;





public final class Config {
	private static final Logger LOG = Logger.getLogger(Config.class);

	// �����ļ�
	private static final String configFile = "config.xml";

	// ���Զ���
	private HashMap<String, Object> properties = new HashMap<String, Object>();

	// �Ѿ��ı������
	private HashMap<String, String> changedProperties = new HashMap<String, String>();
	
	private static long lastModify = 0;
	
	private static Set<ConfigChangeListener> changeListeners = new HashSet<ConfigChangeListener> ();
	
	// ����
	private static Config instance = new Config();
	
	
	private Config() {
		try {
			reload();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ʵ��
	 * 
	 * @return Config
	 */
	public static Config getInstance() {
		return instance;
	}

	public static void registerChangeListener(ConfigChangeListener listener){
		changeListeners.add(listener);
	}
	
	/**
	 * ���������ƻ������ֵ,���Կ�Ƕ��
	 * 
	 * @param name
	 *            String
	 * @return String
	 */
	public String getProperty(String name) {
		Object obj = getObject(name);

		if (obj != null) {
			if (HashMap.class.isInstance(obj)) {
				LOG.info("����:" + name + "���Ǽ�����");
			}
			return getObject(name).toString();
		}
		return null;
	}

	/**
	 * ���������ƻ��HashMap����,���Կ�Ƕ��
	 * 
	 * @param name
	 *            String
	 * @return HashMap
	 * @throws Exception
	 */
	public HashMap<String, Object> getMap(String name) throws Exception {
		Object obj = getObject(name);
		if (obj != null) {
			if (!HashMap.class.isInstance(obj)) {
				throw new Exception(name + "�Ǽ�����");
			}
			return (HashMap<String, Object>) obj;
		}
		return null;
	}

	/**
	 * ͨ���������ƻ�ö���,���Կ�Ƕ��
	 * 
	 * @param name
	 *            String
	 * @return Object
	 */
	private Object getObject(String name) {
		reload();
		String[] key = name.split("\\.");
		HashMap<String, Object> map = properties;
		for (int i = 0; i < key.length - 1; i++) {
			if (!map.containsKey(key[i])) {
				return null;
			}
			map = (HashMap<String, Object>) map.get(key[i]);
		}
		return map.get(key[key.length - 1]) == null ? null : map
				.get(key[key.length - 1]);
	}

	/**
	 * ��������
	 * 
	 * @param name
	 *            String
	 * @param value
	 *            Object
	 */
	public void setProperty(String name, String value) throws Exception {
		String[] key = name.split("\\.");
		HashMap<String, Object> map = properties;
		for (int i = 0; i < key.length - 1; i++) {
			if (!map.containsKey(key[i])) {
				throw new Exception("����" + name + "������");
			}
			map = (HashMap<String, Object>) map.get(key[i]);
		}
		if (!map.containsKey(key[key.length - 1])) {
			throw new Exception("����" + name + "������");
		}
		if (HashMap.class.isInstance(map.get(key[key.length - 1]))) {
			throw new Exception(name + "���Ǽ�����");
		}
		map.put(key[key.length - 1], value);
		changedProperties.put(name, value);
	}

	/**
	 * ���Document
	 * 
	 * @return Document
	 * @throws Exception
	 */
	private Document getConfigDoc() throws Exception {
		File file = getConfigFile();
		Document doc = DOMUtil.createDoc(file);
		return doc;
	}

	/**
	 * ���¼������ò���
	 */
	private void reload(){
		try {
			File file = getConfigFile();
			if(file.lastModified() > lastModify){
				LOG.info("�������ò�����ʼ...");
				load();
				for(ConfigChangeListener listener : changeListeners){
					listener.doChange();
				}
				LOG.info("�������ò�����ʼ����.");
				lastModify = file.lastModified();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.info("�޷��������ò���.");
		}
	}
	
		
	/**
	 * ����ļ�����
	 * 
	 * @return File
	 * @throws Exception
	 */
	public static File getConfigFile() throws Exception {
		try {
//			File classPathFile = new File(Config.class.getClassLoader().getResource(configFile).toURI());
			File classPathFile = ResourceUtil.classPathFile(configFile);
			if(classPathFile.exists()){
				return classPathFile;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		File path = new File(Config.class.getResource("/").toURI());
		File temPath = new File(path, "classes");
		if (!temPath.exists()) {
			path = path.getParentFile();
		}
		return new File(path, configFile);
	}

	/**
	 * ��������
	 * 
	 * @throws Exception
	 */
	private void load() throws Exception {
		prepared(getConfigDoc().getRootElement());
	}

	/**
	 * ����
	 * 
	 * @param element
	 *            Element
	 * @throws Exception
	 */
	private void prepared(Element element) throws Exception {
		List properties = element.elements();
		for (int i = 0; properties != null && i < properties.size(); i++) {
			Element propertieE = (Element) properties.get(i);
			if (propertieE.elements().size() > 0) {
				addMap(propertieE);
				prepared(propertieE);
			} else {
				getParentMap(propertieE).put(propertieE.getQName().getName(),
						propertieE.getTextTrim());
			}
		}
	}

	/**
	 * ���
	 * 
	 * @param element
	 *            Element
	 */
	private void addMap(Element element) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		getParentMap(element).put(element.getQName().getName(), map);
	}

	/**
	 * ���Ԫ�صĸ�Map
	 * 
	 * @param element
	 *            Element
	 * @return HashMap
	 */
	private HashMap<String, Object> getParentMap(Element element) {
		String path = element.getPath();
		String[] node = path.split("/");
		HashMap<String, Object> map = properties;
		for (int i = 3; i < node.length; i++) {
			map = (HashMap<String, Object>) map.get(node[i - 1]);
		}
		return map;
	}

	/**
	 * �������޸�����
	 */
	public void save() {
		FileWriter writer = null;
		try {
			Document doc = getConfigDoc();
			String root = doc.getRootElement().getPath() + "/";
			Set<String> keySet = changedProperties.keySet();
			String[] keys = keySet.toArray(new String[0]);
			if (keys == null || keys.length == 0) {
				return;
			}
			for (int i = 0; i < keys.length; i++) {
				String value = changedProperties.get(keys[i]);
				String path = root + keys[i].replaceAll("\\.", "/");
				doc.selectSingleNode(path).setText(value);
			}
			writer = new FileWriter(getConfigFile());
			doc.write(writer);
			changedProperties.clear();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
