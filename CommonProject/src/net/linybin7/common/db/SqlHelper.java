package net.linybin7.common.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import net.linybin7.common.util.PropertiesUtil;
import net.linybin7.core.web.util.SpringBeanFactory;

import org.apache.log4j.Logger;


/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-8-22-上午10:31:35 <br>
 * @description <br>
 *              TODO
 **/
public class SqlHelper {
	private static final Logger logger = Logger.getLogger(SqlHelper.class);
	private static DataSource ds;
	private static int dbtype = DBUtil.DB_TYPE_UNKNOWN;
	private static String dbname = DBUtil.DB_NAME_UNKNOWN;
	private static Map<String, SQL> SQLSET = new HashMap<String, SQL>();
	static {
		try {
			Properties p = PropertiesUtil.loadProperty("config/sqlset.properties");
			for (Object obj : p.keySet()) {
				String key = (String) obj;
				String sqlid = key.substring(0, key.lastIndexOf("."));
				String dbtype = key.substring(key.lastIndexOf(".") + 1);
				String sql = p.getProperty(key);

				SimpleSQL simSQL = new SimpleSQL();
				simSQL.setDbtype(Integer.valueOf(dbtype));
				simSQL.setSqlid(sqlid);
				simSQL.setSql(sql);

				SqlHelper.putSQL(key, simSQL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	public static String sql(String sqlid, String defSQL, Object... args) {
		String key = sqlid + "." + getDBtype();
		SQL sql = getSQL(key);
		return sql == null ? defSQL : sql.sql(args);
	}

	public static String sql(String sqlid, Object... args) {
		return SqlHelper.sql(sqlid, null, args);
	}

	public static void registerSQL(String sqlid, int dbtype, String sql) {
		SimpleSQL simSQL = new SimpleSQL();
		simSQL.setDbtype(dbtype);
		simSQL.setSqlid(sqlid);
		simSQL.setSql(sql);
		registerSQL(simSQL);
	}

	public static void registerSQL(String sqlid, SQL sql) {
		int dbtype = sql.dbtype();
		String key = sqlid + "." + dbtype;
		putSQL(key, sql);
	}

	public static void registerSQL(SQL sql) {
		int dbtype = sql.dbtype();
		String sqlid = sql.sqlid();
		String key = sqlid + "." + dbtype;
		putSQL(key, sql);
	}

	public static Dialect getDialect() {
		Connection conn = null;
		try {
			conn = getDs().getConnection();
			return DialectFactory.createDialect(conn);
		} catch (Exception e) {
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return null;
	}

	public static String pageSQL(String sql, int currentPage, int pageSize) {
		try {
			Dialect dialect = getDialect();
			return dialect.getPaginSQL(sql, currentPage, pageSize);
		} catch (Exception e) {
		}
		return sql;
	}

	private static SQL getSQL(String key) {
		return SQLSET.get(key);
	}

	private static void putSQL(String key, SQL sql) {
		SQLSET.put(key, sql);
	}

	private static DataSource getDs() {
		if (ds == null) {
			try {
				ds = (DataSource) SpringBeanFactory.getBean("coreDs");
			} catch (Exception e) {
			}
		}
		return ds;
	}

	private static void setDs(DataSource ds) {
		SqlHelper.ds = ds;
	}

	public static String getDBname() {
		if (dbname == DBUtil.DB_NAME_UNKNOWN) {
			Connection conn = null;
			try {
				conn = getDs().getConnection();
				dbname = DBUtil.getDBName(conn);
			} catch (Exception e) {
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
		return dbname;
	}

	public static int getDBtype() {
		if (dbtype == DBUtil.DB_TYPE_UNKNOWN) {
			Connection conn = null;
			try {
				conn = getDs().getConnection();
				dbtype = DBUtil.getDBType(conn);
			} catch (Exception e) {
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}

		}
		return dbtype;
	}

}
