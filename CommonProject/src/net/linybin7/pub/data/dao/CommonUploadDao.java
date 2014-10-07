package net.linybin7.pub.data.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 * <p>
 * Title: 通用数据导入
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: 2012 . All rights reserved.
 * </p>
 * <p>
 * Company: eshine
 * </p>
 * <p>
 * CreateDate:2012-10-15
 * </p>
 * 
 * @author LinYuBin
 *         <p>
 *         ----------------------------------------------
 *         </p>
 *         <p>
 *         Date Mender Reason
 *         </p>
 */
public interface CommonUploadDao{
	public int[] insert2DB(List<String> typeList, final List<Object[]> list)throws SQLException;

	public void clearTable(String tableName);
	
	public void beginTransaction() throws Exception;

	public void commit() throws SQLException;

	public void rollback() throws SQLException;
	
	public void preparedStatement(String insertSql) throws Exception;
}