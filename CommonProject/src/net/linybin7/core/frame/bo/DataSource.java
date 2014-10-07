package net.linybin7.core.frame.bo;

/**
 * ����Դ
 * 
 * 
 * 
 */
public class DataSource {
	/**
	 * ����������Դ
	 */
	public static final int DS_TYPE_SERVER = 1;

	/**
	 * ��ͨ����Դ
	 */
	public static final int DS_TYPE_COMMON = 2;

	// ����Դid;
	private String id;

	// ����Դ����
	private String name;

	// ����Դ����
	private int type;

	// jndi����
	private String jndiName;

	/* ��������ֻ����������Դ����Ϊ����������Դʱ,������ */

	// ���ݿ�����
	private int dbType;

	// ���ݿ�����url
	private String url;

	// ���ݿ���������
	private String driver;

	// �û���
	private String userName;

	// ����
	private String password;

	// �����
	private int maxActive = 20;

	// ���ȴ�ʱ��
	private int maxWait = 10000;

	// ���ݿ������ip
	private String ip;

	// ���ݿ�������˿�
	private String port;

	// ���ݿ�����
	private String dbName;

	private String server;

	public DataSource() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public int getDbType() {
		return dbType;
	}

	public void setDbType(int dbType) {
		this.dbType = dbType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * ����Դ��������
	 * 
	 * @return
	 */
	public String getTypeName() {
		switch (type) {
		case 1:
			return "����������Դ";
		default:
			return "��ͨ����Դ";
		}
	}

	/**
	 * ���ݿ���������
	 * 
	 * @return
	 */
	public String getDbTypeName() {
		switch (dbType) {
		case 1:
			return "Oracle";
		case 2:
			return "Sybase";
		case 3:
			return "SQL2000";
		case 4:
			return "SQL2005";
		case 5:
			return "MySQL";
		case 6:
			return "Informix";
		case 7:
			return "DB2";
		default:
			return "δ֪���ݿ�";
		}
	}
}
