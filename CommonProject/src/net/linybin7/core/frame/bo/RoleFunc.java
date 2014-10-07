package net.linybin7.core.frame.bo;

import java.io.Serializable;

/**
 * ½ÇÉ«¹¦ÄÜ
 * 
 * 
 */
public class RoleFunc implements Serializable {
	private String roleCode;
	private String funcCode;

	public RoleFunc() {

	}

	public RoleFunc(String roleCode, String funcCode) {
		this.roleCode = roleCode;
		this.funcCode = funcCode;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcCode == null) ? 0 : funcCode.hashCode());
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
		final RoleFunc other = (RoleFunc) obj;
		if (funcCode == null) {
			if (other.funcCode != null)
				return false;
		} else if (!funcCode.equals(other.funcCode))
			return false;
		if (roleCode == null) {
			if (other.roleCode != null)
				return false;
		} else if (!roleCode.equals(other.roleCode))
			return false;
		return true;
	}

}
