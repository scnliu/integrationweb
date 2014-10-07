package net.linybin7.core.frame.bo;

import java.util.HashSet;
import java.util.Set;

import net.linybin7.core.util.Constants;


/**
 * ����
 * 
 * 
 * 
 */
public class Org {
	// ���ű��
	private String id;

	// �ϼ����ű��
	private String parentId;

	// ���ż��
	private String orgName;

	// ����ȫ��
	private String orgFullName;

	// ���Ÿ���������
	private String chief;

	// ���Ÿ�������ϵ�绰
	private String phone;

	// ���Ÿ������ֻ���
	private String mobile;

	// ���Ÿ�����email
	private String email;

	// ���
	private int level;

	// ˳��
	private String order;

	// �Ƿ�ͣ��
	private int isStop;
	
	private int userCount;

	// ������Ϣ
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
	 * ���ű�� <br>
	 * �����������Ϣ�����淶��ǰ���������ͬ��<br>
	 * 1����5λ��ĸ������ɣ� <br>
	 * 2��ǰ��λ����������ͬ��ʡ����������ֱϽ�к��ر��������� <br>
	 * 3������λ���ԡ�0����ͷ�ģ������ʡ��Ŀ¼�����߼�ʡ�������ţ���1������Z����ͷ��ǰ��������ʡ�µĸ�����<br>
	 * 4������λ���ԡ�0������Q������Z����ͷ�ģ���������м�Ŀ¼�����ߺ͵��м������ţ���1������P����ͷ��ǰ�������������µĸ����ء�<br>
	 * 5������λ������0����������ؼ�Ŀ¼�����ߣ���1������Z�������� <br>
	 * �磺29001 ��ʾ"�㶫ʡ���������칫��"��290D3 ��ʾ"�㶫ʡ������Ժ"
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
			return "����";
		} else if (isStop == Constants.STOP_YES) {
			return "ͣ��";
		} else {
			return "δ֪״̬";
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
	 * @param id2 Ҫ���õ� id2
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
	 * @param faultCounts Ҫ���õ� faultCounts
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
