package net.linybin7.core.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDialect implements Dialect {

	public String quoValue(String value) {
		if (value == null) {
			return null;
		}
		value = "'" + value.replaceAll("'", "''") + "'";
		return value;
	}

	public String getSqlValue(String value, String type) {
		if (TYPE_NUMBER.equals(type)) {
			return value;
		} else if (TYPE_CAHR.equals(type)) {
			return quoValue(value);
		} else if (TYPE_DATE.equals(type)) {
			return toDate(value);
		} else if (TYPE_DATE_TIME.equals(type)) {
			return toDateTime(value);
		}
		return value;
	}

	public byte[] getBlobAsBytes(ResultSet rs, String columnName) throws SQLException {
		return rs.getBytes(columnName);
	}

	public byte[] getBlobAsBytes(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getBytes(columnIndex);
	}

	public InputStream getBlobAsBinaryStream(ResultSet rs, String columnName) throws SQLException {
		return rs.getBinaryStream(columnName);
	}

	public InputStream getBlobAsBinaryStream(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getBinaryStream(columnIndex);
	}

	public String getClobAsString(ResultSet rs, String columnName) throws SQLException {
		return rs.getString(columnName);
	}

	public String getClobAsString(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getString(columnIndex);
	}

	public InputStream getClobAsAsciiStream(ResultSet rs, String columnName) throws SQLException {
		return rs.getAsciiStream(columnName);
	}

	public InputStream getClobAsAsciiStream(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getAsciiStream(columnIndex);
	}

	public Reader getClobAsCharacterStream(ResultSet rs, String columnName) throws SQLException {
		return rs.getCharacterStream(columnName);
	}

	public Reader getClobAsCharacterStream(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getCharacterStream(columnIndex);
	}

	public LobManager getLobManager() {
		return new DefaultLobManager();
	}
}
