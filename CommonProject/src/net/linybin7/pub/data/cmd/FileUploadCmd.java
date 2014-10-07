package net.linybin7.pub.data.cmd;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileUploadCmd {
	private String file;
	protected int contentLength = -1;// 上传数据总大小
	protected int uploadLength;// 已经上传的数据量
	private String name;
	private Integer p;// p=1 基础数据 ； p=2基础数据2
	private String tableType = "";
	private boolean setMax = false;
	protected boolean stop = false;
	private boolean error = false;
	private String errorContent;
	private String returnInfo;
	protected Date sdate = new Date();// 开始时间
	protected Date edate = new Date();// 结束时间
	private int step = 1;// 步骤,1,2,3,4,5,6
	private String mrType = "3";
	private String company;
	private String colInfo;
	private String sysName;
	private String verid;
	private String cellids;
	
	private String[]tables;

	// UploadHuaweiCmd
	private String uploadDate;
	private String tableName;
	private String parentPath;
	private String sessionId;

	public FileUploadCmd() {
		super();
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public int getUploadLength() {
		return uploadLength;
	}

	public void setUploadLength(int uploadLength) {
		this.uploadLength = uploadLength;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getP() {
		return p;
	}

	public void setP(Integer p) {
		this.p = p;
	}

	public boolean isSetMax() {
		return setMax;
	}

	public void setSetMax(boolean setMax) {
		this.setMax = setMax;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorContent() {
		return errorContent;
	}

	public void setErrorContent(String errorContent) {
		this.errorContent = errorContent;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}

	public Date getEdate() {
		return edate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}

	public Date getSdate() {
		return sdate;
	}

	public String getSdateStr() {
		return fmtDate(sdate);
	}

	public final String fmtDate(Date d) {
		if (d == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(d);
	}

	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getMrType() {
		return mrType;
	}

	public void setMrType(String mrType) {
		this.mrType = mrType;
	}

	public static void splitToListInt(String str, List<Integer> list) {
		if (str != null && str.length() > 0) {
			String[] fns = str.split("\\W+");
			for (String s : fns) {
				list.add(Integer.valueOf(s));
			}
		}
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getColInfo() {
		return colInfo;
	}

	public void setColInfo(String colInfo) {
		this.colInfo = colInfo;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getVerid() {
		return verid;
	}

	public void setVerid(String verid) {
		this.verid = verid;
	}

	public String getCellids() {
		return cellids;
	}

	public void setCellids(String cellids) {
		this.cellids = cellids;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String[] getTables() {
		return tables;
	}

	public void setTables(String[] tables) {
		this.tables = tables;
	}
	
}