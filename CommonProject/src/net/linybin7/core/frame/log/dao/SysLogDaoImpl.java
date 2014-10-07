package net.linybin7.core.frame.log.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.linybin7.common.jdbc.JdbcDAOImpl;
import net.linybin7.core.frame.bo.LogContent;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

public class SysLogDaoImpl extends JdbcDAOImpl implements SysLogDao {

	@Override
	public void save(final LogContent content) {
		final String sql = "INSERT INTO SA_LOG(LEVNO,MOUDLE,MSG,USERCODE,ADDR,OPERATETIME) VALUES(?,?,?,?,?,?)";
		JdbcTemplate jdbcTemp = this.getJdbcTemplate();
		jdbcTemp.execute(new ConnectionCallback() {
			@Override
			public Object doInConnection(Connection con) throws SQLException, DataAccessException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, content.getLevl());
				pstmt.setString(2, content.getMoudle());
				pstmt.setString(3, content.getMsg());
				pstmt.setString(4, content.getUserCode());
				pstmt.setString(5, content.getAddr());
				pstmt.setTimestamp(6, new Timestamp(content.getOperateTime().getTime()));
				pstmt.execute();
				return null;
			}
		});
	}

}