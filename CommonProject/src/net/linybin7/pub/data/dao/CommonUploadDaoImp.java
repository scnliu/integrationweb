package net.linybin7.pub.data.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import net.linybin7.common.database.AbstractDataDAO;

import org.hibernate.cfg.Configuration;


/**
 * 
 * Title: 通用数据导入
 * Description:
 * 
 * CreateDate:2012-10-15
 * 
 * @author LinYuBin
 *  Date Mender Reason
 */
public class CommonUploadDaoImp extends AbstractDataDAO implements CommonUploadDao {
	public Configuration config = new Configuration();

	PreparedStatement ps;

	@Override
	public void preparedStatement(String insertSql) throws Exception {
		ps = conn.prepareStatement(insertSql);
	}
	
	@Override
	public void clearTable(String tableName) {
		String clearSql = "truncate table " + tableName;
		try {
			getJdbc().update(clearSql);
		} catch (Exception e) {
			clearSql = "delete from " + tableName;
			getJdbc().update(clearSql);
		}
	}

	@Override
	public int[] insert2DB(List<String> typeList, final List<Object[]> list) throws SQLException {

		int[] result = new int[0];
		for (int i = 0; i < list.size(); i++) {
			int k = 1;
			Object[] objs = list.get(i);
			for (int j = 0; j < objs.length; j++) {
				String type=typeList.get(j);
				if(type.indexOf("date")>=0 && objs[j] !=null){
					Date date=(Date)objs[j];
					Timestamp ts=new Timestamp(date.getTime());
					ps.setTimestamp(k, ts);
				}else{
					ps.setObject(k, objs[j]);
				}
				k++;
			}
			ps.executeUpdate();
		}
		/**
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			getJdbc().update(sql, obj);
		}
		*/
		return result;
	}

	@Override
	protected Class getClazz() {
		return null;
	}

	@Override
	protected String getEntityName() {
		return null;
	}
	
}