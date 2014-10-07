package net.linybin7.pub.tools;

import net.linybin7.core.frame.bo.User;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-5-27-下午02:38:06 <br>
 * @description <br>
 *              TODO
 **/
public class TableNameHelper {
	public static String getTableName(User user, String tableName) {
		String groupId = user == null ? null : user.getOrgId();
		return OrgTablePre.getOrgTable(groupId, tableName);
	}
}
