package net.linybin7.core.frame.bo;

import net.linybin7.core.util.Constants;

/**
 * 角色
 * 
 * 
 * 
 */
public class Role {
	// 角色编号
	private String roleCode;

	// 部门ID
	private String orgId;

	// 部门名称,页面显示属性
	private String orgName;

	// 角色名称
	private String roleName;

	// 是否停用
	private int isStop = -1;

	// 用色类型
	private int roleProp = -1;

	// 描术
	private String descript;

	public Role() {

	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getIsStop() {
		return isStop;
	}

	public void setIsStop(int isStop) {
		this.isStop = isStop;
	}

	public int getRoleProp() {
		return roleProp;
	}

	public void setRoleProp(int roleProp) {
		this.roleProp = roleProp;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getType() {
		if (roleProp == Constants.ROLE_TYPE_COMMON) {
			return "普通角色";
		} else if (roleProp == Constants.ROLE_TYPE_ORG) {
			return "管理角色";
		} else if (roleProp == Constants.ROLE_TYPE_SYS) {
			return "超级管理角色";
		} else {
			return "未知类型";
		}
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleCode == null) ? 0 : roleCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Role other = (Role) obj;
		if (roleCode == null) {
			if (other.roleCode != null)
				return false;
		} else if (!roleCode.equals(other.roleCode))
			return false;
		return true;
	}

}
