package net.linybin7.core.frame.ds;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;

import net.linybin7.common.db.DBUtil;
import net.linybin7.common.util.CollectionUtil;
import net.linybin7.common.util.DOMUtil;
import net.linybin7.common.util.StringHelper;
import net.linybin7.core.frame.bo.DataSource;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


/**
 * ����Դ����
 * 
 * 
 * 
 */
public class DataSourceCfg {

	private File cfgFile;

	private static Map<String, String> driverMap = new LinkedHashMap<String, String>();

	private static Map<String, String> dialectMap = new HashMap<String, String>();

	static {
		driverMap.put("1", "oracle.jdbc.driver.OracleDriver");
		driverMap.put("2", "com.sybase.jdbc2.jdbc.SybDriver");
		driverMap.put("3", "com.microsoft.jdbc.sqlserver.SQLServerDriver");
		driverMap.put("4", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
		driverMap.put("5", "com.mysql.jdbc.Driver");
		driverMap.put("6", "com.informix.jdbc.IfxDriver");
		driverMap.put("7", "com.ibm.db2.jcc.DB2Driver");

		dialectMap.put("1", "org.hibernate.dialect.OracleDialect");
		dialectMap.put("2", "org.hibernate.dialect.SybaseDialect");
		dialectMap.put("3", "org.hibernate.dialect.SQLServerDialect");
		dialectMap.put("4", "org.hibernate.dialect.SQLServerDialect");
		dialectMap.put("5", "org.hibernate.dialect.MySQLDialect");
		dialectMap.put("6", "org.hibernate.dialect.InformixDialect");
		dialectMap.put("7", "org.hibernate.dialect.DB2Dialect");
	}

	public DataSourceCfg() {

	}

	public DataSourceCfg(File cfgFile) {
		this();
		this.cfgFile = cfgFile;
	}

	public File getCfgFile() {
		return cfgFile;
	}

	public void setCfgFile(File cfgFile) {
		this.cfgFile = cfgFile;
	}

	/**
	 * ����bean id��ȡ����Դ
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<DataSource> getDataSource(String[] ids) throws Exception {
		List<DataSource> dss = new ArrayList<DataSource>();
		if (CollectionUtil.isEmpty(ids)) {
			return dss;
		}
		Document doc = DOMUtil.createDoc(cfgFile);
		Element rootEle = doc.getRootElement();

		for (String id : ids) {
			Element eleBean = DOMUtil.getElementByAttr(rootEle, "id", id);
			DataSource ds = getDataSource(eleBean);
			ds.setId(id);
			dss.add(ds);
		}
		return dss;
	}

	/**
	 * ��������Դ
	 * 
	 * @param dataSource
	 * @param ids
	 * @throws Exception
	 */
	public void updateDataSource(List<DataSource> dss) throws Exception {
		if (CollectionUtil.isEmpty(dss)) {
			return;
		}
		Document doc = DOMUtil.createDoc(cfgFile);
		Element rootEle = doc.getRootElement();

		for (DataSource ds : dss) {
			Element dbEle = DOMUtil.getElementByAttr(rootEle, "id", ds.getId());
			DOMUtil.clearChildren(dbEle);

			if (ds.getType() == DataSource.DS_TYPE_SERVER) {
				Attribute classAttr = dbEle.attribute("class");
				// classAttr
				// .setValue("org.springframework.jndi.JndiObjectFactoryBean");
				classAttr.setValue("com.eshine.core.frame.ds.JndiDataSourceFactory");

				Element pEle = dbEle.addElement("property");
				pEle.addAttribute("name", "jndiName");
				Element vEle = pEle.addElement("value");
				vEle.setText(ds.getJndiName());
			} else {
				Attribute classAttr = dbEle.attribute("class");
				classAttr.setValue("org.apache.commons.dbcp.BasicDataSource");
				Element driverEle = dbEle.addElement("property");
				driverEle.addAttribute("name", "driverClassName");
				Element vDriverEle = driverEle.addElement("value");
				vDriverEle.setText(getDriver(ds.getDbType()));

				Element urlEle = dbEle.addElement("property");
				urlEle.addAttribute("name", "url");
				Element vUrlEle = urlEle.addElement("value");
				vUrlEle.setText(getUrl(ds.getDbType(), ds.getIp(), ds.getPort(), ds.getDbName(), ds
						.getServer()));

				Element userEle = dbEle.addElement("property");
				userEle.addAttribute("name", "username");
				Element vUserEle = userEle.addElement("value");
				vUserEle.setText(ds.getUserName());

				Element pwdEle = dbEle.addElement("property");
				pwdEle.addAttribute("name", "password");
				Element vPwdEle = pwdEle.addElement("value");
				vPwdEle.setText(ds.getPassword());

				Element activeEle = dbEle.addElement("property");
				activeEle.addAttribute("name", "maxActive");
				Element vActiveEle = activeEle.addElement("value");
				vActiveEle.setText(String.valueOf(ds.getMaxActive()));

				Element waitEle = dbEle.addElement("property");
				waitEle.addAttribute("name", "maxWait");
				Element vWaitEle = waitEle.addElement("value");
				vWaitEle.setText(String.valueOf(ds.getMaxWait()));
			}
		}

		OutputFormat format = OutputFormat.createPrettyPrint();
		try {
			XMLWriter writer = new XMLWriter(new FileOutputStream(cfgFile), format);
			writer.write(doc);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("д�ĵ�����ʧ��");
		}
	}

	/**
	 * ��������Ԫ��,��ȡ����Դ����
	 * 
	 * @param dsEle
	 * @return
	 */
	private DataSource getDataSource(Element element) {
		DataSource dataSource = new DataSource();
		Iterator itr = element.elementIterator();
		while (itr.hasNext()) {
			Element temp = (Element) itr.next();

			if (temp.attributeValue("name").equals("jndiName")) {
				dataSource.setJndiName(temp.elementTextTrim("value"));
				dataSource.setType(DataSource.DS_TYPE_SERVER);
				return dataSource;
			}

			dataSource.setType(DataSource.DS_TYPE_COMMON);

			if (temp.attributeValue("name").equals("url")) {
				dataSource.setUrl(temp.elementTextTrim("value"));
			}
			if (temp.attributeValue("name").equals("driverClassName")) {
				dataSource.setDriver(temp.elementTextTrim("value"));
			}
			if (temp.attributeValue("name").equals("username")) {
				dataSource.setUserName(temp.elementTextTrim("value"));
			}
			if (temp.attributeValue("name").equals("password")) {
				dataSource.setPassword(temp.elementTextTrim("value"));
			}
			if (temp.attributeValue("name").equals("maxActive")) {
				dataSource.setMaxActive(Integer.parseInt(temp.elementTextTrim("value")));
			}
			if (temp.attributeValue("name").equals("maxWait")) {
				dataSource.setMaxWait(Integer.parseInt(temp.elementTextTrim("value")));
			}
		}

		dataSource.setDbType(getDbType(dataSource.getDriver()));
		dataSource.setIp(getIp(dataSource.getDbType(), dataSource.getUrl()));
		dataSource.setPort(getPort(dataSource.getDbType(), dataSource.getUrl()));
		dataSource.setDbName(getDbName(dataSource.getDbType(), dataSource.getUrl()));
		return dataSource;
	}

	/**
	 * ��������Դ
	 * 
	 * @param ds
	 * @throws Exception
	 */
	public void testDs(DataSource ds) throws Exception {
		Connection conn = null;

		try {
			if (ds.getType() == DataSource.DS_TYPE_SERVER) {
				InitialContext initContext = new InitialContext();
				javax.sql.DataSource ds1 = (javax.sql.DataSource) initContext.lookup(ds
						.getJndiName());
				conn = ds1.getConnection();
			} else {
				Class.forName(getDriver(ds.getDbType()));
				String url = getUrl(ds.getDbType(), ds.getIp(), ds.getPort(), ds.getDbName(), ds
						.getServer());
				conn = DriverManager.getConnection(url, ds.getUserName(), ds.getPassword());
			}
		} catch (SQLException e) {
			String msg = e.getMessage();
			if (msg.indexOf("java.net.UnknownHostException") >= 0) {
				throw new Exception(ds.getName() + "-��Ч��IP��ַ��ָ��������ʾ����");
			}
			if (msg.indexOf("�˿ں�") >= 0 && msg.indexOf("��Ч") >= 0) {
				throw new Exception(ds.getName() + "-��Ч�˿ں�:" + ds.getPort());
			}
			if (msg.indexOf("�޷��򿪵�¼����������ݿ�") >= 0) {
				throw new Exception(ds.getName() + "-���ݿ�:" + ds.getDbName() + "������");
			}
			if (msg.indexOf("�û�") >= 0 && msg.indexOf("��¼ʧ��") >= 0) {
				throw new Exception(ds.getName() + "-�û��������벻��ȷ");
			}
			e.printStackTrace();
			String sqlState = e.getSQLState();
			if (e.getErrorCode() == 1017 || e.getErrorCode() == 1045
					|| (e.getErrorCode() == 0 && sqlState != null && sqlState.equals("JZ00L"))) {
				throw new Exception(ds.getName() + "-�û����������");
			} else if (e.getErrorCode() == 17002
					|| (e.getErrorCode() == 0 && sqlState != null && sqlState.equals("JZ006"))) {
				throw new Exception(ds.getName() + "-��Ч��ַ��˿ڴ���");
			} else if (e.getErrorCode() == 1130) {
				throw new Exception(ds.getName() + "-���ݿ�δ��������");
			} else if ((e.getErrorCode() == 0 && sqlState == null) || e.getErrorCode() == 1049) {
				throw new Exception(ds.getName() + "-���ݿ����ƴ���");
			} else {
				throw new Exception(ds.getName() + "-�޷��������ݿ�:" + e.getMessage());
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			String prifx = "java:comp/env/";
			String jndi = ds.getJndiName();
			if (jndi != null && jndi.indexOf(prifx) >= 0) {
				jndi = jndi.substring(jndi.indexOf(prifx) + prifx.length());
			}
			throw new Exception(ds.getName() + "-����Դ:" + jndi + "������");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(ds.getName() + "-�޷��������ݿ�:" + e.getMessage());
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ������������,������ݿ�����
	 * 
	 * @param driver
	 * @return
	 */
	public int getDbType(String driver) {
		for (Entry<String, String> entry : driverMap.entrySet()) {
			if (entry.getValue().equals(driver)) {
				return Integer.parseInt(entry.getKey());
			}
		}
		return 0;
	}

	/**
	 * �������ݿ�����,�����������
	 * 
	 * @param type
	 * @return
	 */
	public String getDriver(int type) {
		String driver = driverMap.get(String.valueOf(type));
		return StringHelper.trim(driver);
	}

	public String getDialect(int type) {
		String dialect = dialectMap.get(String.valueOf(type));
		return StringHelper.trim(dialect);
	}

	/**
	 * ��ȡ���ݿ�����
	 * 
	 * @param dbType
	 *            --���ݿ�����
	 * @param ip
	 *            --ip��ַ
	 * @param port
	 *            --�˿�
	 * @param dbName
	 *            --���ݿ�����
	 * @return
	 */
	public String getUrl(int dbType, String ip, String port, String dbName, String server) {
		switch (dbType) {
		case 1:
			return "jdbc:oracle:thin:@" + ip + ":" + port + ":" + dbName;
		case 2:
			return "jdbc:sybase:Tds:" + ip + ":" + port + "/" + dbName;
		case 3:
			return "jdbc:microsoft:sqlserver://" + ip + ":" + port + ";DatabaseName=" + dbName;
		case 4:
			return "jdbc:sqlserver://" + ip + ":" + port + ";DatabaseName=" + dbName;
		case 5:
			return "jdbc:mysql://" + ip + ":" + port + "/" + dbName;
		case 6:
			return "jdbc:informix-sqli://" + ip + ":" + port + "/" + dbName + ":INFORMIXSERVER="
					+ server;
		case 7:
			return "jdbc:db2://" + ip + ":" + port + "/" + dbName;
		default:
			return "";
		}
	}

	/**
	 * ���ip��ַ
	 * 
	 * @param dbType
	 *            --���ݿ�����
	 * @param url
	 *            --���ݿ�����
	 * @return
	 */
	public String getIp(int dbType, String url) {
		int startIndex = 0;
		int endIndex = 0;
		switch (dbType) {
		case 1:
			startIndex = url.indexOf("@") + "@".length();
			endIndex = url.indexOf(":", startIndex);
			return url.substring(startIndex, endIndex);
		case 2:
			startIndex = url.indexOf("@") + "@".length();
			endIndex = url.indexOf(":", startIndex);
			return url.substring(startIndex, endIndex);
		case 3:
		case 4:
		case 5:
			startIndex = url.indexOf("//") + "//".length();
			endIndex = url.indexOf(":", startIndex);
			return url.substring(startIndex, endIndex);
		case 6:
		case 7:
			startIndex = url.indexOf("//") + 2;
			endIndex = url.indexOf(":", startIndex);
			return url.substring(startIndex, endIndex);
		default:
			return "";
		}
	}

	/**
	 * ��ȡ�˿�
	 * 
	 * @param dbType
	 *            --���ݿ�����
	 * @param url
	 *            --���ݿ�����
	 * @return
	 */
	public String getPort(int dbType, String url) {
		int startIndex = 0;
		int endIndex = 0;
		switch (dbType) {
		case 1:
			startIndex = url.indexOf(":", url.indexOf("@")) + ":".length();
			endIndex = url.indexOf(":", startIndex);
			return url.substring(startIndex, endIndex);
		case 2:
			startIndex = url.indexOf(":", url.indexOf("Tds:") + "Tds:".length()) + ":".length();
			endIndex = url.indexOf("/", startIndex);
			return url.substring(startIndex, endIndex);
		case 3:
		case 4:
			startIndex = url.indexOf(":", url.indexOf("//")) + ":".length();
			endIndex = url.indexOf(";", startIndex);
			return url.substring(startIndex, endIndex);
		case 5:
			startIndex = url.indexOf(":", url.indexOf("//")) + ":".length();
			endIndex = url.indexOf("/", startIndex);
			return url.substring(startIndex, endIndex);
		case 6:
		case 7:
			startIndex = url.indexOf(":", url.indexOf("//")) + ":".length();
			endIndex = url.indexOf("/", startIndex);
			return url.substring(startIndex, endIndex);
		default:
			return "";
		}
	}

	/**
	 * ��ȡ���ݿ�����
	 * 
	 * @param dbType
	 *            --���ݿ�����
	 * @param url
	 *            --���ݿ�����
	 * @return
	 */
	public String getDbName(int dbType, String url) {
		int startIndex = 0;
		int endIndex = 0;
		switch (dbType) {
		case 1:
			startIndex = url.lastIndexOf(":") + ":".length();
			return url.substring(startIndex);
		case 2:
			startIndex = url.indexOf("/") + "/".length();
			return url.substring(startIndex);
		case 3:
		case 4:
			startIndex = url.indexOf("DatabaseName=") + "DatabaseName=".length();
			return url.substring(startIndex);
		case 5:
			startIndex = url.lastIndexOf("/") + "/".length();
			return url.substring(startIndex);
		case 6:
			startIndex = url.indexOf("/", url.indexOf("//") + 2) + "/".length();
			endIndex = url.indexOf(":", startIndex);
			return url.substring(startIndex, endIndex);
		case 7:
			startIndex = url.indexOf("/", url.indexOf("//") + 2) + "/".length();
			return url.substring(startIndex);
		default:
			return "";
		}
	}
}
