package net.linybin7.pub.data.bo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户数据库记录
 * 
 * 
 * 
 */
public class DataMgr implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 表名
	private String tabCode;

	// 表中文名
	private String tabName;

	// 部门编号
	private String orgId;

	// 用户编号
	private String userCode;

	// 用户名称
	private String userName;
	
	// 创建时间
	private Date createTime;

	// 用户编号2
	private String userCode2;

	// 用户名称2
	private String userName2;
	
	// 导入时间
	private Date impotTime;
	
	private String tools;
	
	
	
	// 数据量
	private String tabCount;
	
	public String getTabCount() {
		return tabCount;
	}
	public void setTabCount(String tabCount) {
		this.tabCount = tabCount;
	}

	public String getTabCode() {
		return tabCode;
	}

	public void setTabCode(String tabCode) {
		this.tabCode = tabCode;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getUserCode2() {
		return userCode2;
	}
	
	public void setUserCode2(String userCode2) {
		this.userCode2 = userCode2;
	}
	
	public String getUserName2() {
		return userName2;
	}
	
	public void setUserName2(String userName2) {
		this.userName2 = userName2;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getImpotTime() {
		return impotTime;
	}
	public void setImpotTime(Date impotTime) {
		this.impotTime = impotTime;
	}
	
	//时间显示格式
	public String getCreateTimeShow(){
		String strDate = "";
		if(createTime!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strDate = sdf.format(createTime);
		}
		return strDate;
	}
	//时间显示格式
	public String getImpotTimeShow(){
		String strDate = "";
		if(impotTime!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strDate = sdf.format(impotTime);
		}
		return strDate;
	}
	public String getTools() {
		return tools;
	}
	public void setTools(String tools) {
		this.tools = tools;
	}
	
	
}
