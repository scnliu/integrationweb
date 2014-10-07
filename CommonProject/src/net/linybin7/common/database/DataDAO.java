package net.linybin7.common.database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.linybin7.common.hbmdao.HbmDAO;
import net.linybin7.pub.data.bo.DataMgr;

import org.hibernate.mapping.Column;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

@SuppressWarnings("rawtypes")
public interface DataDAO extends HbmDAO {
	
	public JdbcTemplate getJdbc();
	public HibernateTemplate getHbn();

	public boolean checkDeptTab(String tableName);

	public void clearAll(String tableName) throws Exception;

	public int count(String tableName);

	
	

	public void beginTransaction() throws Exception;

	public void commit() throws SQLException;

	public void rollback() throws SQLException;

	public void releaseConn() throws SQLException;

	public Connection getConn() throws SQLException;
	
	

	List<Column> getColumns() throws Exception;	

	public Object get(Class cls, Serializable id);
	
	public Object get(String columnName, String value, String tableName);

	public List findAll(final Class c, final String tableName);
}
