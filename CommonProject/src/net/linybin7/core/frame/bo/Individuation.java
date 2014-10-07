package net.linybin7.core.frame.bo;

import java.io.Serializable;


/**
 * 个性化设置
 * 
 * 
 * 
 */
public class Individuation implements Serializable {

	// 系统Id;
	private String sysId;

	// 用户编号
	private String userCode;
	
	private String userName;

	private String setCode;
	
	private String setName;
	
	private String kind;
	
	private String setting;
	
	private String defaultValue;
	
	private String expression;
	
	private String des;

	/**
	 * @return the sysId
	 */
	public String getSysId() {
		return sysId;
	}

	/**
	 * @param sysId the sysId to set
	 */
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the setCode
	 */
	public String getSetCode() {
		return setCode;
	}

	/**
	 * @param setCode the setCode to set
	 */
	public void setSetCode(String setCode) {
		this.setCode = setCode;
	}

	/**
	 * @return the setName
	 */
	public String getSetName() {
		return setName;
	}

	/**
	 * @param setName the setName to set
	 */
	public void setSetName(String setName) {
		this.setName = setName;
	}

	/**
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * @param kind the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * @return the setting
	 */
	public String getSetting() {
		return setting;
	}

	/**
	 * @param setting the setting to set
	 */
	public void setSetting(String setting) {
		this.setting = setting;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * @return the des
	 */
	public String getDes() {
		return des;
	}

	/**
	 * @param des the des to set
	 */
	public void setDes(String des) {
		this.des = des;
	}

	
}
