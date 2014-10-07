package net.linybin7.core.frame.bo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import net.linybin7.common.util.StringHelper;
import net.linybin7.core.util.Constants;


/**
 * �û���Ϣ
 * 
 * 
 * 
 */
public class User implements Serializable {
	// �û����
	private String userCode;

	// ����ID
	private String orgId;

	// ��������,ҳ����ʾ����
	private String orgName;

	// �û���
	private String userName;

	// ����
	private String password;

	// �绰����
	private String tel;

	// �ֻ�
	private String mobile;

	// email
	private String email;

	// �Ƿ�ͣ��
	private int isStop = -1;

	// �û�����
	private int userProp = -1;

	// ����
	private String descript;

	// ʹ������0�������ƣ�1����¼�������ƣ�2��ʹ��ʱ������
	private int limitType = 0;
	// �ɵ�¼����
	private int permitNum = 30;
	// �ѵ�¼����
	private int loginNum = 0;
	// ��Чʱ��
	private Date effectTime;
	// ��ʹ������
	private int days = 30;
	
	private final Date effect = new Date();
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Long getLimitDate() {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(effectTime);
			cal.add(Calendar.DAY_OF_YEAR, days);
			// cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)days);
			Date date = cal.getTime(); // �õ�����ʱ��
			return date.getTime();
		} catch (Exception e) {

		}
		return null;
	}

	public String getEffect() {
		try {
			return sdf.format(effect);
		} catch (Exception e) {
		}
		return "";
	}

	// USBKey����ID USBKey��Ψһ��ʶ
	private String trustId;

	private String oldPassword;

	private String newPassword;

	private String cPassword;
	
	private int nums = 0;
	private long onTime = 0;
	private int isPassWd = 0;
	
	private String photoType;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getcPassword() {
		return cPassword;
	}

	public void setcPassword(String cPassword) {
		this.cPassword = cPassword;
	}

	public User() {

	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public int getIsStop() {
		return isStop;
	}

	public void setIsStop(int isStop) {
		this.isStop = isStop;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserProp() {
		return userProp;
	}

	public void setUserProp(int userProp) {
		this.userProp = userProp;
	}

	public String getCPassword() {
		return password;
	}

	public String getPasswordStr() {
		return StringHelper.wrapPwd(password);
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

	public String getType() {
		if (userProp == Constants.USER_TYPE_COMMON) {
			return "��ͨ�û�";
		} else if (userProp == Constants.USER_TYPE_ORG) {
			return "����Ա";
		} else if (userProp == Constants.USER_TYPE_SYS) {
			return "��������Ա";
		} else {
			return "δ֪����";
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userCode == null) ? 0 : userCode.hashCode());
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
		final User other = (User) obj;
		if (userCode == null) {
			if (other.userCode != null)
				return false;
		} else if (!userCode.equals(other.userCode))
			return false;
		return true;
	}

	public String getTrustId() {
		return trustId;
	}

	public void setTrustId(String trustId) {
		this.trustId = trustId;
	}

	/**
	 * @param limitType
	 *            the limitType to set
	 */
	public void setLimitType(int limitType) {
		this.limitType = limitType;
	}

	/**
	 * @return the limitType
	 */
	public int getLimitType() {
		return limitType;
	}

	/**
	 * @param permitNum
	 *            the permitNum to set
	 */
	public void setPermitNum(int permitNum) {
		this.permitNum = permitNum;
	}

	/**
	 * @return the permitNum
	 */
	public int getPermitNum() {
		return permitNum;
	}

	/**
	 * @param loginNum
	 *            the loginNum to set
	 */
	public void setLoginNum(int loginNum) {
		this.loginNum = loginNum;
	}

	/**
	 * @return the loginNum
	 */
	public int getLoginNum() {
		return loginNum;
	}

	/**
	 * @param effectTime
	 *            the effectTime to set
	 */
	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}

	/**
	 * @return the effectTime
	 */
	public Date getEffectTime() {
		return effectTime;
	}

	/**
	 * @param days
	 *            the days to set
	 */
	public void setDays(int days) {
		this.days = days;
	}

	/**
	 * @return the days
	 */
	public int getDays() {
		return days;
	}
	
	private Set<Org> Org;

	/**
	 * @return the org
	 */
	public Set<Org> getOrg()
	{
		return Org;
	}

	/**
	 * @param org the org to set
	 */
	public void setOrg(Set<Org> org)
	{
		Org = org;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

	public int getNums() {
		return nums;
	}


	public void setIsPassWd(int isPassWd) {
		this.isPassWd = isPassWd;
	}

	public int getIsPassWd() {
		return isPassWd;
	}
	
	public String getOnTimeStr(){
		if(onTime<1000*60)return Math.round(onTime/(1000.0))+"s";
		return Math.round(onTime/(1000.0*60))+"m";
	}

	public String getPhotoType() {
		return photoType;
	}

	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}

	public long getOnTime() {
		return onTime;
	}

	public void setOnTime(long onTime) {
		this.onTime = onTime;
	}
	
}
