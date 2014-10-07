package net.linybin7.pub.data.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.mapping.Column;

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
public interface CommonUploadService {
	public List<Column> getColumns(String fileName);

	public String getTableName(String fileName);

	public String getTableComment(String fileName);

	public String getUploadDir(String fileName);

	public String getDes(String fileName);

	public boolean allowNullCol(String fileName);

	public String getRowProcessor(String fileName);

	public void clearTable(String tableName);

	public int[] insert2DB(List<String> typeList, final List<Object[]> list)throws SQLException;
	
	public Map<String,String>getDataProperty(String fileName) throws Exception;
	
	public void begin() throws Exception ;

	public void end() throws Exception ;
	
	public void rollback();
	
	public void preparedStatement(String insertSql) throws Exception;
}
