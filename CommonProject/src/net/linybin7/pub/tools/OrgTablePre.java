package net.linybin7.pub.tools;

/**
 * ����������ǰ׺
 * 
 */
public class OrgTablePre {
	
	
	public static String getOrgTable(String orgId, String tableName) {
		if (orgId != null && orgId.length() > 0)
			orgId = orgId + "_";
		else
			orgId = "";
		String table = orgId + tableName ;
		return table.toUpperCase();
	}

	public static String getPre(String orgId) {
		if (orgId != null && orgId.length() > 0)
			orgId = orgId + "_";
		else
			orgId = "";
		return orgId.toUpperCase();
	}
}
