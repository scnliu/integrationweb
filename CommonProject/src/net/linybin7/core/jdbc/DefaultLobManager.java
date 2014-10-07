package net.linybin7.core.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * 
 *
 */
public class DefaultLobManager implements LobManager {

	public void close() {
		// do nothing
	}

	public void setBlobAsBinaryStream(PreparedStatement ps, int paramIndex,
			InputStream contentStream, int contentLength) throws SQLException {
		ps.setBinaryStream(paramIndex, contentStream, contentLength);

	}

	public void setBlobAsBytes(PreparedStatement ps, int paramIndex, byte[] content)
			throws SQLException {
		ps.setBytes(paramIndex, content);
	}

	public void setClobAsAsciiStream(PreparedStatement ps, int paramIndex, InputStream asciiStream,
			int contentLength) throws SQLException {
		ps.setAsciiStream(paramIndex, asciiStream, contentLength);
	}

	public void setClobAsCharacterStream(PreparedStatement ps, int paramIndex,
			Reader characterStream, int contentLength) throws SQLException {
		ps.setCharacterStream(paramIndex, characterStream, contentLength);
	}

	public void setClobAsString(PreparedStatement ps, int paramIndex, String content)
			throws SQLException {
		ps.setString(paramIndex, content);

	}

}
