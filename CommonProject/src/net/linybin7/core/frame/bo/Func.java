package net.linybin7.core.frame.bo;

import java.util.HashSet;
import java.util.Set;

import net.linybin7.core.util.Constants;


/**
 * ����
 * 
 * 
 */
public class Func {
	// ���ܱ��
	private String funcCode;

	// ���ڵ���
	private String parentCode;

	// ����ϵͳ
	private String sys;

	private String context;

	// ��������
	private String funcName;

	// ��������
	private int funcProp;

	// ���
	private int level;

	// ˳��
	private int order;

	// �Ƿ�ͣ��
	private int isStop;

	// ����
	private String link;

	// ��ʾ��Ϣ
	private String title;

	// ͼ��
	private String icon;

	// ������Ϣ
	private String descript;
	private Set children = new HashSet();

	public Set getChildren() {
		return children;
	}

	public void setChildren(Set children) {
		this.children = children;
	}

	public Func() {

	}

	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getSys() {
		return sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getIsStop() {
		return isStop;
	}

	public void setIsStop(int isStop) {
		this.isStop = isStop;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public int getFuncProp() {
		return funcProp;
	}

	public void setFuncProp(int funcProp) {
		this.funcProp = funcProp;
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
		if (funcProp == Constants.FUNC_TYPE_MENU) {
			return "�˵�";
		} else if (funcProp == Constants.FUNC_TYPE_BUTTON) {
			return "��ť";
		} else if (funcProp == Constants.FUNC_TYPE_LINK) {
			return "����";
		} else {
			return "δ֪����";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcCode == null) ? 0 : funcCode.hashCode());
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
		final Func other = (Func) obj;
		if (funcCode == null) {
			if (other.funcCode != null)
				return false;
		} else if (!funcCode.equals(other.funcCode))
			return false;
		return true;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}

}
