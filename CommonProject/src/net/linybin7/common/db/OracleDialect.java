package net.linybin7.common.db;


/**
 * 
 * 
 *
 */
public class OracleDialect extends AbstractDialect {

	public String toDate(String exp) {
		return "to_date('" + exp + "','yyyy-mm-dd')";
	}

	public String toDateTime(String exp) {
		return "to_date('" + exp + "','yyyy-mm-dd hh24:mi:ss')";
	}

	public String toTime(String exp) {
		return "to_date(" + exp + ",'hh24:mi:ss')";
	}

	public String toTimeChar(String exp) {
		return "to_char('" + exp + "','hh24:mi:ss')";
	}

	public String toDateChar(String exp) {
		return "to_char(" + exp + ",'yyyy-mm-dd')";
	}

	public String toDateTimeChar(String exp) {
		return "to_char(" + exp + ",'yyyy-mm-dd hh24:mi:ss')";
	}

	public String getPaginSQL(String sql, int currentPage, int pageSize) {
		String page = " ";

		if (pageSize > 0 && currentPage > 0) {
			int start = pageSize * (currentPage - 1);
			int end = pageSize * currentPage;

			page += " SELECT  ";
			page += "   *  ";
			page += " FROM  ";
			page += "     ( ";
			page += "        SELECT  ";
			page += "         A.*, ROWNUM RN  ";
			page += "        FROM  ";
			page += "          ( ";
			page += sql;
			page += "          ) A  ";
			page += "        WHERE ROWNUM <= " + end;
			page += "      )  ";
			page += " WHERE  ";
			page += "   RN > " + start;
		} else {
			page = sql;
		}
		return page;
	}

	public String getTableCommentSql() {
		return "select TABLE_NAME, COMMENTS, TABLE_TYPE from user_tab_comments t where t.TABLE_TYPE = 'TABLE'";
	}

	public String getColumnCommentSql() {
		return "select COLUMN_NAME, COMMENTS, TABLE_NAME from user_col_comments c where c.TABLE_NAME = ?";
	}

	@Override
	public String toDateTime(String exp, String format) {
		return "to_date('" + exp + "','" + format + "')";
	}

	@Override
	public String toDateTimeChar(String exp, String format) {
		return "to_char(" + exp + ",'" + format + "')";
	}
}
