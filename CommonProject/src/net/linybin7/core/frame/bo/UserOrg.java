package net.linybin7.core.frame.bo;

import java.io.Serializable;


public class UserOrg implements Serializable 
{
	private String userCode;
	
	private String id;
	
	public UserOrg()
	{
		
	}
	
	public UserOrg(String userCode, String id)
	{
		this.userCode = userCode;
		this.id = id;
	}

	/**
	 * @return the userCode
	 */
	public String getUserCode()
	{
		return userCode;
	}

	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode)
	{
		this.userCode = userCode;
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((userCode == null) ? 0 : userCode.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UserOrg other = (UserOrg) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (userCode == null) {
			if (other.userCode != null)
				return false;
		} else if (!userCode.equals(other.userCode))
			return false;
		return true;
	}
	
}
