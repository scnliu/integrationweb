package net.linybin7.common.db;

import java.io.InputStream;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 
 * 
 *
 */
public class Oracle9Dialect extends OracleDialect {
	public byte[] getBlobAsBytes(ResultSet rs, String columnName) throws SQLException {
		java.sql.Blob blob = rs.getBlob(columnName);
		return blob != null ? blob.getBytes(1, (int) blob.length()) : null;
	}

	public byte[] getBlobAsBytes(ResultSet rs, int columnIndex) throws SQLException {
		java.sql.Blob blob = rs.getBlob(columnIndex);
		return blob != null ? blob.getBytes(1, (int) blob.length()) : null;
	}

	public InputStream getBlobAsBinaryStream(ResultSet rs, String columnName) throws SQLException {
		java.sql.Blob blob = rs.getBlob(columnName);
		return blob != null ? blob.getBinaryStream() : null;
	}

	public InputStream getBlobAsBinaryStream(ResultSet rs, int columnIndex) throws SQLException {
		java.sql.Blob blob = rs.getBlob(columnIndex);
		return blob != null ? blob.getBinaryStream() : null;
	}

	public String getClobAsString(ResultSet rs, String columnName) throws SQLException {
		java.sql.Clob clob = rs.getClob(columnName);
		return clob != null ? clob.getSubString(1, (int) clob.length()) : null;
	}

	public String getClobAsString(ResultSet rs, int columnIndex) throws SQLException {
		java.sql.Clob clob = rs.getClob(columnIndex);
		return clob != null ? clob.getSubString(1, (int) clob.length()) : null;
	}

	public InputStream getClobAsAsciiStream(ResultSet rs, String columnName) throws SQLException {
		java.sql.Clob clob = rs.getClob(columnName);
		return clob != null ? clob.getAsciiStream() : null;
	}

	public InputStream getClobAsAsciiStream(ResultSet rs, int columnIndex) throws SQLException {
		java.sql.Clob clob = rs.getClob(columnIndex);
		return clob != null ? clob.getAsciiStream() : null;
	}

	public Reader getClobAsCharacterStream(ResultSet rs, String columnName) throws SQLException {
		java.sql.Clob clob = rs.getClob(columnName);
		return clob != null ? clob.getCharacterStream() : null;
	}

	public Reader getClobAsCharacterStream(ResultSet rs, int columnIndex) throws SQLException {
		java.sql.Clob clob = rs.getClob(columnIndex);
		return clob != null ? clob.getCharacterStream() : null;
	}

	public LobManager getLobManager() {
		return new Oracle9LobManager();
	}
}
