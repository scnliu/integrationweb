package net.linybin7.common.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.linybin7.common.util.StringHelper;

/**
 * 
 * @author JackenCai
 * 
 */
public final class DBUtil {
	private static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

	public static final String DB_NAME_UNKNOWN = null;
	public static final String DB_NAME_ORCALE10 = "ORACLE";
	public static final String DB_NAME_ORCALE9 = "ORACLE9";
	public static final String DB_NAME_SYBASE = "ADAPTIVE SERVER ENTERPRISE";
	public static final String DB_NAME_MSSQL = "MSSQL";
	public static final String DB_NAME_MSSQL2005 = "MICROSOFT SQL SERVER";
	public static final String DB_NAME_MYSQL = "MYSQL";
	public static final String DB_NAME_INFORMIX = "INFORMIX DYNAMIC SERVER";

	public static final int DB_TYPE_UNKNOWN = -1;
	public static final int DB_TYPE_ORCALE10 = 0;
	public static final int DB_TYPE_ORCALE9 = 1;
	public static final int DB_TYPE_SYBASE = 2;
	public static final int DB_TYPE_MSSQL = 3;
	public static final int DB_TYPE_MSSQL2005 = 4;
	public static final int DB_TYPE_MYSQL = 5;
	public static final int DB_TYPE_INFORMIX = 6;

	private DBUtil() {

	}

	/**
	 * 获得数据库产口名称
	 * 
	 * @param conn
	 * @return
	 */
	public static String getDBName(Connection conn) {
		String dbName = null;
		try {
			DatabaseMetaData meta = conn.getMetaData();
			dbName = meta.getDatabaseProductName().toUpperCase();
			if (DB_NAME_ORCALE10.equals(dbName)) {
				if (meta.getDatabaseMajorVersion() < 10) {
					return DB_NAME_ORCALE9;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbName;
	}

	/**
	 * 获得数据库类型
	 * 
	 * @param conn
	 * @return
	 */
	public static int getDBType(Connection conn) {
		String dbName = getDBName(conn);
		if (DB_NAME_ORCALE10.equals(dbName)) {
			return DB_TYPE_ORCALE10;
		} else if (DB_NAME_ORCALE9.equals(dbName)) {
			return DB_TYPE_ORCALE9;
		} else if (DB_NAME_SYBASE.equals(dbName)) {
			return DB_TYPE_SYBASE;
		} else if (DB_NAME_MSSQL.equals(dbName)) {
			return DB_TYPE_MSSQL;
		} else if (DB_NAME_MSSQL2005.equals(dbName)) {
			return DB_TYPE_MSSQL2005;
		} else if (DB_NAME_MYSQL.equals(dbName)) {
			return DB_TYPE_MYSQL;
		} else if (DB_NAME_INFORMIX.equals(dbName)) {
			return DB_TYPE_INFORMIX;
		}
		return DB_TYPE_UNKNOWN;
	}

	/**
	 * 关闭连接
	 * 
	 * @param closeable
	 */
	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 关闭statement
	 * 
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 关闭结果集
	 * 
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 指定的数据表是否存在；
	 * 
	 * @param topicCode
	 *            String
	 * @return boolean
	 */
	public static boolean tableExist(Connection conn, String tableCode) {
		Statement stmt = null;
		ResultSet set = null;
		try {
			stmt = conn.createStatement();
			String table = tableCode;
			String sql = "select * from " + table + " where 1!= 1";
			set = stmt.executeQuery(sql);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			close(stmt);
			close(set);
		}
	}

	private static class $Filter implements Filter {
		public boolean access(String value) {
			if (value != null && value.indexOf("$") < 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 获得数据库所有用户表表名
	 * 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static List<String> getTables(Connection conn, String schema, Filter... filters)
			throws Exception {
		ResultSet rs = null;
		DatabaseMetaData meta = null;
		// ResultSetMetaData rsMeta = null;
		List<String> list = new ArrayList<String>();
		$Filter $filter = new $Filter();

		try {
			meta = conn.getMetaData();
			int dbType = getDBType(conn);
			if (StringHelper.isEmpty(schema)
					&& (DB_TYPE_ORCALE10 == dbType || DB_TYPE_ORCALE9 == dbType)) {
				schema = meta.getUserName();
			}

			if (!StringHelper.isEmpty(schema)) {
				schema = schema.toUpperCase();
			} else {
				schema = null;
			}

			String[] tables = new String[] { "TABLE", "VIEW" };

			if (DB_TYPE_INFORMIX == dbType) {
				tables = null;
			}
			rs = meta.getTables(null, schema, null, tables);

			while (rs.next()) {
				String table = rs.getString("TABLE_NAME");
				if (!$filter.access(table)) {
					continue;
				}
				boolean access = true;
				if (filters != null) {
					for (Filter filter : filters) {
						if (!filter.access(table)) {
							access = false;
							continue;
						}
					}
				}

				if (access) {
					list.add(table);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("无法获得所有表名");
		} finally {
			close(rs);
		}
		return list;
	}

	public static List<String> getColumns(Connection conn, String table, Filter... filters) {
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsMeta = null;
		List<String> list = new ArrayList<String>();
		String sql = "select * from " + table + " where 1 != 1";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rsMeta = rs.getMetaData();
			for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
				String column = rsMeta.getColumnName(i);
				boolean access = true;
				for (Filter filter : filters) {
					if (!filter.access(column)) {
						access = false;
						continue;
					}
				}
				if (access) {
					list.add(column);
				}
			}
		} catch (Exception e) {
			System.out.println("error sql:" + sql);
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return list;
	}

	public static List<Column> columns(Connection conn, String table, Filter... filters) {
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsMeta = null;
		List<Column> list = new ArrayList<Column>();
		String sql = "select * from " + table + " where 1 != 1";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rsMeta = rs.getMetaData();
			for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
				String columnName = rsMeta.getColumnName(i);
				boolean access = true;
				for (Filter filter : filters) {
					if (!filter.access(columnName)) {
						access = false;
						continue;
					}
				}
				if (access) {
					Column column = new Column();
					column.setName(columnName);
					column.setLabel(columnName);
					list.add(column);
				}
			}
		} catch (Exception e) {
			System.out.println("error sql:" + sql);
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return list;
	}

	/**
	 * 获取字符串值
	 * 
	 * @param rs
	 * @param column
	 * @return
	 * @throws SQLException
	 */
	public static String getString(ResultSet rs, String column) throws SQLException {
		Object obj = rs.getObject(column);
		if (rs == null) {
			return null;
		}
		if (obj instanceof BigDecimal) {
			return obj.toString();
		}
		if (obj instanceof Timestamp) {
			return formater.format(obj);
		}
		return rs.getString(column);
	}

	/**
	 * 获得字段
	 * 
	 * @param field
	 * @return
	 */
	public static String getAsField(String field) {
		int index = field.toUpperCase().indexOf(" AS ");
		if (index < 0) {
			return field;
		}
		return field.substring(index + " AS ".length());
	}

	public static Object getObject(ResultSet resultSet, String columnName) throws Exception {
		Object obj = resultSet.getObject(columnName);
		if (obj == null) {
			return obj;
		}
		if (obj instanceof BigDecimal) {
			return obj.toString();
		}
		if (obj instanceof Timestamp) {
			return formater.format(obj);
		}
		return obj;
	}

	public static boolean isDuplicateKey(Exception e) {
		if (e.getMessage().indexOf("Duplicate entry") >= 0) {
			return true;
		}
		return false;
	}

	public static List<Table> getTableComment(Connection conn, String schema) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		HashMap<String, String> map = new HashMap<String, String>();
		List<Table> result = new ArrayList<Table>();
		try {
			list = DBUtil.getTables(conn, schema);
			try {
				pstmt = conn.prepareStatement(DialectFactory.createDialect(conn)
						.getTableCommentSql());
				// pstmt.setString(1, table);
				if (StringHelper.isEmpty(schema) && DBUtil.getDBType(conn) == DBUtil.DB_TYPE_MYSQL) {
					String url = conn.getMetaData().getURL();
					schema = url.substring(url.lastIndexOf("/") + 1);
					pstmt.setString(1, schema);
				}
				rs = pstmt.executeQuery();
				while (rs.next()) {
					map.put(rs.getString(1), rs.getString(2));
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
			for (String tab : list) {
				String label = tab;
				String comment = map.get(tab);
				if (!StringHelper.isEmpty(comment) && !comment.startsWith("InnoDB free:")) {
					label = comment;
				}
				result.add(new Table(tab, label));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public static List<Column> getColumnsComment(Connection conn, String table) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Column> result = new ArrayList<Column>();
		HashMap<String, String> map = new HashMap<String, String>();
		List<String> list = new ArrayList<String>();
		try {
			list = DBUtil.getColumns(conn, table);
			table = table.toUpperCase();
			try {
				pstmt = conn.prepareStatement(DialectFactory.createDialect(conn)
						.getColumnCommentSql());
				pstmt.setString(1, table);
				if (DBUtil.getDBType(conn) == DBUtil.DB_TYPE_MYSQL) {
					String url = conn.getMetaData().getURL();
					String schema = url.substring(url.lastIndexOf("/") + 1);
					pstmt.setString(2, schema);
				}
				rs = pstmt.executeQuery();
				while (rs.next()) {
					map.put(rs.getString(1), rs.getString(2));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			for (String col : list) {
				String label = col;
				String comment = map.get(col);
				if (!StringHelper.isEmpty(comment)) {
					label = comment;
				}
				result.add(new Column(col, label));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
}
