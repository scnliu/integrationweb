package net.linybin7.common.database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import net.linybin7.common.db.SqlHelper;
import net.linybin7.common.hbmdao.HbmDAOImpl;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

@SuppressWarnings("rawtypes")
public abstract class AbstractDataDAO extends HbmDAOImpl implements DataDAO {
	public Configuration config = new Configuration();
	protected JdbcTemplate jdbc;
	protected BasicDataSource ds;
	protected Connection conn;

	public JdbcTemplate getJdbc() {
		return jdbc;
	}

	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	public void setDataSource(DataSource ds) {
		this.jdbc = new JdbcTemplate(ds);
	}

	public BasicDataSource getDs() {
		return ds;
	}

	public void setDs(BasicDataSource ds) {
		this.ds = ds;
	}
	
	public HibernateTemplate getHbn(){
		return getHibernateTemplate();
	}
	
	/**常用sql**/
	/*********************************************************************************************/
	@Override
	public void clearAll(String tableName) throws Exception {
		
		tableName = tableName.toUpperCase();
		try {
			String sql =SqlHelper.sql("delete_table");
			this.getJdbc().update(sql+tableName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.commit();
	}
	
	@Override
	public int count(String tableName) {
		return this.jdbc.queryForInt("select count(*) from " + tableName);
	}

	@Override
	public boolean checkDeptTab(String tableName) {
		try {
			this.jdbc.queryForList("select min(CELLID) from " + tableName);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	/**简单事务实现**/
	/*********************************************************************************************/
	
	@Override
	public void beginTransaction() throws SQLException {
		conn = ds.getConnection();
		conn.setAutoCommit(false);
		conn.setSavepoint("point1");
		conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
	}

	@Override
	public void commit() throws SQLException {
		if (conn == null)
			conn = this.getDs().getConnection();
		conn.commit();
		conn.close();
	}

	@Override
	public void rollback() throws SQLException {
		conn.rollback();
		conn.commit();
		conn.close();
	}

	@Override
	public void releaseConn() throws SQLException {
		conn.close();
	}

	@Override
	public Connection getConn() throws SQLException {
		return ds.getConnection();
	}
	
	
	/**简单hibernate封装，执行sql**/
	/*********************************************************************************************/
	@Override
	public List findAll(final Class c, final String tableName) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session s) throws HibernateException {
				SQLQuery q = s.createSQLQuery("select * from " + tableName);
				q.addEntity(c);
				return q.list();
			}
		});
	}
	
	@Override
	public Object get(Class cls, Serializable id) {
		return this.getHibernateTemplate().get(cls, id);
	}

	@Override
	public Object get(final String columnName, final String value, final String tableName) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session s) throws HibernateException {
				SQLQuery q = s.createSQLQuery("select * from " + tableName + " where " + columnName
						+ "='" + value + "'");
				q.addEntity(getClazz());
				return q.uniqueResult();
			}
		});
	}
	
	@Override
	public void save(Object entity) {
		this.getHibernateTemplate().save(entity);
	}
	
	/**
	 * 获取表的列属性
	 * 
	 * @see net.linybin7.common.database.DataDAO#getColumns()
	 */
	@Override
	public List<Column> getColumns() throws Exception {
		List<Column> list = new ArrayList<Column>();
		PersistentClass pc = config.getClassMapping(getEntityName());
		if (pc == null) {
			config = config.addClass(Class.forName(getEntityName()));
			pc = config.getClassMapping(getEntityName());
		}
		@SuppressWarnings("unchecked")
		Iterator<Column> it = pc.getTable().getColumnIterator();

		while (it.hasNext()) {
			Column c = it.next();
			list.add(c);
		}
		return list;
	}
}