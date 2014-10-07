package net.linybin7.core.frame.bo;

import java.util.HashSet;
import java.util.Set;

import net.linybin7.core.util.Constants;


/**
 * 功能
 * 
 * 
 */
public class Func {
	// 功能编号
	private String funcCode;

	// 父节点编号
	private String parentCode;

	// 所属系统
	private String sys;

	private String context;

	// 功能名称
	private String funcName;

	// 功能类型
	private int funcProp;

	// 层次
	private int level;

	// 顺序
	private int order;

	// 是否停用
	private int isStop;

	// 链接
	private String link;

	// 提示信息
	private String title;

	// 图标
	private String icon;

	// 描术信息
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
			return "启用";
		} else if (isStop == Constants.STOP_YES) {
			return "停用";
		} else {
			return "未知状态";
		}
	}

	public String getType() {
		if (funcProp == Constants.FUNC_TYPE_MENU) {
			return "菜单";
		} else if (funcProp == Constants.FUNC_TYPE_BUTTON) {
			return "按钮";
		} else if (funcProp == Constants.FUNC_TYPE_LINK) {
			return "链接";
		} else {
			return "未知类型";
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
