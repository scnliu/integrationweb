package net.linybin7.core.frame.bo;

import net.linybin7.core.util.Constants;

/**
 * ��ɫ
 * 
 * 
 * 
 */
public class Role {
	// ��ɫ���
	private String roleCode;

	// ����ID
	private String orgId;

	// ��������,ҳ����ʾ����
	private String orgName;

	// ��ɫ����
	private String roleName;

	// �Ƿ�ͣ��
	private int isStop = -1;

	// ��ɫ����
	private int roleProp = -1;

	// ����
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
			return "��ͨ��ɫ";
		} else if (roleProp == Constants.ROLE_TYPE_ORG) {
			return "�����ɫ";
		} else if (roleProp == Constants.ROLE_TYPE_SYS) {
			return "���������ɫ";
		} else {
			return "δ֪����";
		}
	}

	public String getStop() {
		if (isStop == Constants.STOP_NO) {
			return "����";
		} else if (isStop == Constants.STOP_YES) {
			return "ͣ��";
		} else {
			return "δ֪״̬";
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
