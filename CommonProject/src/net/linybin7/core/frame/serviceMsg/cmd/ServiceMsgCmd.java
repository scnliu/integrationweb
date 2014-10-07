package net.linybin7.core.frame.serviceMsg.cmd;

public class ServiceMsgCmd {

	private long freePhysicalMemorySize; // 剩余的物理内存

	private int totalThread; // 线程总数
	private String filePath; // 文件路径

	private String jvmid;// JVM版本
	private String jvmMaxMemory;// JVM最大内存
	private String jvmTotalMemory;// JVM可用内存
	private String jvmFreeMemory;// JVM剩余内存
	private String jvmUsedMemory;// 已用内存

	private String dbName;// 数据库名称
	private String dbVersion;// 数据库版本
	private String dbDriver;// 数据库驱动
	private String dbUrl;// 数据库的连接

	private String serverName;// 服务器名称
	private String serverVersion;// 服务器版本

	private String systemName;// 操作系统名称
	private String systemVersion;// 操作系统版本

	private String usedMemory; // 已使用的物理内存
	private String cpuRatio; // cpu使用率
	private int cpuCount; // cpu个数

	private String loginName;
	private int nowCount;
	private int maximum;
	private String maxTime;
	private int loginCount;

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
		this.freePhysicalMemorySize = freePhysicalMemorySize;
	}

	public long getFreePhysicalMemorySize() {
		return freePhysicalMemorySize;
	}

	public void setTotalThread(int totalThread) {
		this.totalThread = totalThread;
	}

	public int getTotalThread() {
		return totalThread;
	}

	public void setCpuRatio(String cpuRatio) {
		this.cpuRatio = cpuRatio;
	}

	public String getCpuRatio() {
		return cpuRatio;
	}

	public void setCpuCount(int cpuCount) {
		this.cpuCount = cpuCount;
	}

	public int getCpuCount() {
		return cpuCount;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setJvmid(String jvmid) {
		this.jvmid = jvmid;
	}

	public String getJvmid() {
		return jvmid;
	}

	public void setJvmMaxMemory(String jvmMaxMemory) {
		this.jvmMaxMemory = jvmMaxMemory;
	}

	public String getJvmMaxMemory() {
		return jvmMaxMemory;
	}

	public void setJvmTotalMemory(String jvmTotalMemory) {
		this.jvmTotalMemory = jvmTotalMemory;
	}

	public String getJvmTotalMemory() {
		return jvmTotalMemory;
	}

	public void setJvmFreeMemory(String jvmFreeMemory) {
		this.jvmFreeMemory = jvmFreeMemory;
	}

	public String getJvmFreeMemory() {
		return jvmFreeMemory;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbVersion(String dbVersion) {
		this.dbVersion = dbVersion;
	}

	public String getDbVersion() {
		return dbVersion;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}

	public String getServerVersion() {
		return serverVersion;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setJvmUsedMemory(String jvmUsedMemory) {
		this.jvmUsedMemory = jvmUsedMemory;
	}

	public String getJvmUsedMemory() {
		return jvmUsedMemory;
	}

	public void setUsedMemory(String usedMemory) {
		this.usedMemory = usedMemory;
	}

	public String getUsedMemory() {
		return usedMemory;
	}

	public void setNowCount(int nowCount) {
		this.nowCount = nowCount;
	}

	public int getNowCount() {
		return nowCount;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public int getMaximum() {
		return maximum;
	}

	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}

	public String getMaxTime() {
		return maxTime;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
}
