package net.linybin7.common.db;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.linybin7.common.util.IOUtil;

import oracle.sql.BLOB;
import oracle.sql.CLOB;


/**
 * 
 * 
 *
 */
class Oracle9LobManager implements LobManager {
	private List<BLOB> blobs = new ArrayList<BLOB>();
	private List<CLOB> clobs = new ArrayList<CLOB>();

	public void close() {
		try {
			for (BLOB blob : blobs) {
				blob.freeTemporary();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			for (CLOB clob : clobs) {
				clob.freeTemporary();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setBlobAsBinaryStream(PreparedStatement ps, int paramIndex,
			InputStream contentStream, int contentLength) throws SQLException {
		BLOB blob = prepareBlob(ps.getConnection());
		OutputStream out = blob.getBinaryOutputStream();
		IOUtil.copy(contentStream, out);
		close(blob);
		ps.setBlob(paramIndex, blob);
	}

	public void setBlobAsBytes(PreparedStatement ps, int paramIndex, byte[] content)
			throws SQLException {
		BLOB blob = prepareBlob(ps.getConnection());
		OutputStream out = blob.getBinaryOutputStream();
		IOUtil.copy(content, out);
		close(blob);
		ps.setBlob(paramIndex, blob);
	}

	public void setClobAsAsciiStream(PreparedStatement ps, int paramIndex, InputStream asciiStream,
			int contentLength) throws SQLException {
		CLOB clob = prepareClob(ps.getConnection());
		OutputStream out = clob.getAsciiOutputStream();
		IOUtil.copy(asciiStream, out);
		close(clob);
		ps.setClob(paramIndex, clob);
	}

	public void setClobAsCharacterStream(PreparedStatement ps, int paramIndex,
			Reader characterStream, int contentLength) throws SQLException {
		CLOB clob = prepareClob(ps.getConnection());
		Writer out = clob.getCharacterOutputStream();
		IOUtil.copy(characterStream, out);
		close(clob);
		ps.setClob(paramIndex, clob);
	}

	public void setClobAsString(PreparedStatement ps, int paramIndex, String content)
			throws SQLException {
		CLOB clob = prepareClob(ps.getConnection());
		Writer out = clob.getCharacterOutputStream();
		IOUtil.copy(content, out);
		close(clob);
		ps.setClob(paramIndex, clob);

	}

	private BLOB prepareBlob(Connection conn) throws SQLException {
		BLOB blob = BLOB.createTemporary(conn, false, BLOB.DURATION_SESSION);
		blob.open(BLOB.MODE_READWRITE);
		return blob;
	}

	private CLOB prepareClob(Connection conn) throws SQLException {
		CLOB clob = CLOB.createTemporary(conn, false, CLOB.DURATION_SESSION);
		clob.open(CLOB.MODE_READWRITE);
		return clob;
	}

	private void close(CLOB clob) {
		try {
			clob.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void close(BLOB blob) {
		try {
			blob.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
