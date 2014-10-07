package net.linybin7.common.db;

import java.sql.Connection;
import java.util.HashMap;

/**
 * 
 * 
 *
 */
public class DialectFactory {
	private static HashMap<String, Dialect> dialects = new HashMap<String, Dialect>();

	private DialectFactory() {

	}

	public static Dialect createDialect(Connection conn) {
		String dbName = DBUtil.getDBName(conn);
		return createDialect(dbName);
	}

	private static Dialect createDialect(String dbName) {
		Dialect dialect = dialects.get(dbName);
		if (dialect != null) {
			return dialect;
		}
		if (DBUtil.DB_NAME_SYBASE.equals(dbName)) {
			dialect = new SybaseDialect();
			dialects.put(dbName, dialect);
			return dialect;
		} else if (DBUtil.DB_NAME_MSSQL.equals(dbName) || DBUtil.DB_NAME_MSSQL2005.equals(dbName)) {
			dialect = new MssqlDialect();
			dialects.put(dbName, dialect);
			return dialect;
		} else if (DBUtil.DB_NAME_ORCALE10.equals(dbName)) {
			dialect = new OracleDialect();
			dialects.put(dbName, dialect);
			return dialect;
		} else if (DBUtil.DB_NAME_ORCALE9.equals(dbName)) {
			dialect = new Oracle9Dialect();
			dialects.put(dbName, dialect);
			return dialect;
		} else if (DBUtil.DB_NAME_MYSQL.equals(dbName)) {
			dialect = new MySqlDialect();
			dialects.put(dbName, dialect);
			return dialect;
		}
		return null;
	}
}
