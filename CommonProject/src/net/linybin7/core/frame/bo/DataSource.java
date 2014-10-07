package net.linybin7.core.frame.bo;

/**
 * 数据源
 * 
 * 
 * 
 */
public class DataSource {
	/**
	 * 服务器数据源
	 */
	public static final int DS_TYPE_SERVER = 1;

	/**
	 * 普通数据源
	 */
	public static final int DS_TYPE_COMMON = 2;

	// 数据源id;
	private String id;

	// 数据源名称
	private String name;

	// 数据源类型
	private int type;

	// jndi名称
	private String jndiName;

	/* 以下属性只有在类数据源类型为服务器数据源时,才有用 */

	// 数据库类型
	private int dbType;

	// 数据库连接url
	private String url;

	// 数据库驱动类名
	private String driver;

	// 用户名
	private String userName;

	// 密码
	private String password;

	// 最大活动数
	private int maxActive = 20;

	// 最大等待时间
	private int maxWait = 10000;

	// 数据库服务器ip
	private String ip;

	// 数据库服务器端口
	private String port;

	// 数据库名称
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
	 * 数据源类型名称
	 * 
	 * @return
	 */
	public String getTypeName() {
		switch (type) {
		case 1:
			return "服务器数据源";
		default:
			return "普通数据源";
		}
	}

	/**
	 * 数据库类型名称
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
			return "未知数据库";
		}
	}
}
