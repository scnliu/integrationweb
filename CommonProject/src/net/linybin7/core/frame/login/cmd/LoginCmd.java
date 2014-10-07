package net.linybin7.core.frame.login.cmd;

public class LoginCmd {
	public String userCode;

	public String password;

	public String vtype = "0";

	/* CA µÇÂ¼²ÎÊý */
	public String pinCode;
	public String userSignCert;
	public String userEncCert;
	public String userKeyType;
	public String randomData;
	public String userSignedData;

	private boolean onlyVerify = false;

	private boolean ukLogin = false;
	private String verifyCode;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVtype() {
		return vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getUserSignCert() {
		return userSignCert;
	}

	public void setUserSignCert(String userSignCert) {
		this.userSignCert = userSignCert;
	}

	public String getUserEncCert() {
		return userEncCert;
	}

	public void setUserEncCert(String userEncCert) {
		this.userEncCert = userEncCert;
	}

	public String getUserKeyType() {
		return userKeyType;
	}

	public void setUserKeyType(String userKeyType) {
		this.userKeyType = userKeyType;
	}

	public String getRandomData() {
		return randomData;
	}

	public void setRandomData(String randomData) {
		this.randomData = randomData;
	}

	public String getUserSignedData() {
		return userSignedData;
	}

	public void setUserSignedData(String userSignedData) {
		this.userSignedData = userSignedData;
	}

	public boolean isOnlyVerify() {
		return onlyVerify;
	}

	public void setOnlyVerify(boolean onlyVerify) {
		this.onlyVerify = onlyVerify;
	}

	public boolean isUkLogin() {
		return ukLogin;
	}

	public void setUkLogin(boolean ukLogin) {
		this.ukLogin = ukLogin;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

}
