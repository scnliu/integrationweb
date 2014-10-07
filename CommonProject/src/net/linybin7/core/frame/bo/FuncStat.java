package net.linybin7.core.frame.bo;

import java.io.Serializable;

public class FuncStat implements Serializable{
	private String sysId;
	private String parentCode;
	private String funcCode;
	private int cnt;
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public String getSysId() {
		return sysId;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}
	public String getFuncCode() {
		return funcCode;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getCnt() {
		return cnt;
	}
}
