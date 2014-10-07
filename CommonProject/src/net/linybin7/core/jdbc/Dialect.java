package net.linybin7.core.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * 
 *
 */
public interface Dialect {
	public static String TYPE_CAHR = "1";
	public static String TYPE_NUMBER = "2";
	public static String TYPE_DATE = "3";
	public static String TYPE_DATE_TIME = "4";

	String getPaginSQL(String sql, int currentPage, int pageSize);

	String toDate(String exp);

	String toDateTime(String exp);

	String toDateChar(String exp);

	String toDateTimeChar(String exp);

	String quoValue(String value);

	String getSqlValue(String value, String type);

	byte[] getBlobAsBytes(ResultSet rs, String columnName) throws SQLException;

	byte[] getBlobAsBytes(ResultSet rs, int columnIndex) throws SQLException;

	InputStream getBlobAsBinaryStream(ResultSet rs, String columnName) throws SQLException;

	InputStream getBlobAsBinaryStream(ResultSet rs, int columnIndex) throws SQLException;

	String getClobAsString(ResultSet rs, String columnName) throws SQLException;

	String getClobAsString(ResultSet rs, int columnIndex) throws SQLException;

	InputStream getClobAsAsciiStream(ResultSet rs, String columnName) throws SQLException;

	InputStream getClobAsAsciiStream(ResultSet rs, int columnIndex) throws SQLException;

	Reader getClobAsCharacterStream(ResultSet rs, String columnName) throws SQLException;

	Reader getClobAsCharacterStream(ResultSet rs, int columnIndex) throws SQLException;

	LobManager getLobManager();
}
