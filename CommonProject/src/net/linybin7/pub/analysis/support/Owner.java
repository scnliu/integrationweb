package net.linybin7.pub.analysis.support;

import net.linybin7.core.frame.bo.User;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-7-29-上午09:35:42 <br>
 * @description <br>
 *              TODO
 **/
public class Owner {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String executeType;
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Owner() {
	}

	public Owner(User user, String executeType) {
		this.user = user;
		this.executeType = executeType;
	}

	public String getExecuteType() {
		return executeType;
	}

	public void setExecuteType(String executeType) {
		this.executeType = executeType;
	}

	public String getExecuteGroupId() {
		String type = executeType == null ? "A" + executeType : executeType;
		String orgId = user == null ? null : user.getOrgId();
		orgId = orgId == null ? "B" + orgId : orgId;
		return type + "_" + orgId;
	}

}
