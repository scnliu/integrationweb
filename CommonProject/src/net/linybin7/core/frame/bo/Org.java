package net.linybin7.core.frame.bo;

import java.util.HashSet;
import java.util.Set;

import net.linybin7.core.util.Constants;


/**
 * 部门
 * 
 * 
 * 
 */
public class Org {
	// 部门编号
	private String id;

	// 上级部门编号
	private String parentId;

	// 部门简称
	private String orgName;

	// 部门全称
	private String orgFullName;

	// 部门负责人姓名
	private String chief;

	// 部门负责人联系电话
	private String phone;

	// 部门负责人手机号
	private String mobile;

	// 部门负责人email
	private String email;

	// 层次
	private int level;

	// 顺序
	private String order;

	// 是否停用
	private int isStop;
	
	private int userCount;

	// 描术信息
	private String descript;
	private Set del = new HashSet();

	public Set getDel() {
		return del;
	}

	public void setDel(Set del) {
		this.del = del;
	}

	/**
	 * Added by Wulb on 2009-03-18 <br>
	 * 部门编号 <br>
	 * 编码规则与信息类编码规范的前段码规则相同：<br>
	 * 1、由5位字母数字组成； <br>
	 * 2、前两位：用于区别不同的省、自治区、直辖市和特别行政区； <br>
	 * 3、第三位：以“0”开头的，分配给省级目录管理者及省级政务部门，“1”到“Z”开头的前段码分配给省下的各地市<br>
	 * 4、第四位：以“0”、“Q”到“Z”开头的，分配给地市级目录管理者和地市级政务部门，“1”到“P”开头的前段码分配给地市下的各区县。<br>
	 * 5、第五位：将“0”分配给区县级目录管理者，“1”到“Z”保留。 <br>
	 * 如：29001 表示"广东省人民政府办公厅"，290D3 表示"广东省人民检察院"
	 */

	public Org() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgFullName() {
		return orgFullName;
	}

	public void setOrgFullName(String orgFullName) {
		this.orgFullName = orgFullName;
	}

	public String getChief() {
		return chief;
	}

	public void setChief(String chief) {
		this.chief = chief;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getIsStop() {
		return isStop;
	}

	public void setIsStop(int isStop) {
		this.isStop = isStop;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getStop() {
		if (isStop == Constants.STOP_NO) {
			return "启用";
		} else if (isStop == Constants.STOP_YES) {
			return "停用";
		} else {
			return "未知状态";
		}
	}

	private String id2;
	private int faultCounts;
	
	/**
	 * @return id2
	 */
	public String getId2() {
		return id2;
	}
	/**
	 * @param id2 要设置的 id2
	 */
	public void setId2(String id2) {
		this.id2 = id2;
	}
	
	/**
	 * @return faultCounts
	 */
	public int getFaultCounts() {
		return faultCounts;
	}
	/**
	 * @param faultCounts 要设置的 faultCounts
	 */
	public void setFaultCounts(int faultCounts) {
		this.faultCounts = faultCounts;
	}
	
	private Set<User> User;

	/**
	 * @return the user
	 */
	public Set<User> getUser()
	{
		return User;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Set<User> user)
	{
		User = user;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getUserCount() {
		return userCount;
	}
}
