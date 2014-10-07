package net.linybin7.core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import net.linybin7.common.db.DBUtil;

import org.springframework.beans.factory.FactoryBean;


/**
 * 
 * 
 * 
 */
public class DialectFactory implements FactoryBean {

	private DataSource dataSource;

	private Dialect dialect;

	public DialectFactory() {
		dialect = createDialect();
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private Dialect createDialect() {
		try {
			String dbName = DBUtil.getDBName(getConn());
			return createDialect(dbName);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("无法创建数据库方言");
			return null;
		}
	}

	public static Dialect createDialect(Connection conn) {
		String dbName = DBUtil.getDBName(conn);
		return createDialect(dbName);
	}

	private static Dialect createDialect(String dbName) {
		Dialect dialect = null;
		if (DBUtil.DB_NAME_SYBASE.equals(dbName)) {
			dialect = new SybaseDialect();
		} else if (DBUtil.DB_NAME_MSSQL.equals(dbName) || DBUtil.DB_NAME_MSSQL2005.equals(dbName)) {
			dialect = new MssqlDialect();
		} else if (DBUtil.DB_NAME_ORCALE10.equals(dbName)) {
			dialect = new OracleDialect();
		} else if (DBUtil.DB_NAME_ORCALE9.equals(dbName)) {
			dialect = new Oracle9Dialect();
		}
		return dialect;
	}

	private Connection getConn() throws SQLException {
		return dataSource.getConnection();
	}

	public Object getObject() throws Exception {
		return dialect;
	}

	public Class getObjectType() {
		return Dialect.class;
	}

	public boolean isSingleton() {
		return true;
	}
}
