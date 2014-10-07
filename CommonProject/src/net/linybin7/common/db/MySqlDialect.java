package net.linybin7.common.db;


public class MySqlDialect extends AbstractDialect {

	@Override
	public String getPaginSQL(String sql, int currentPage, int pageSize) {
		String page = " ";
		if (pageSize > 0 && currentPage > 0) {
			int start = pageSize * (currentPage - 1);
			int end = pageSize * currentPage;
			page = sql + " limit " + start + ", " + pageSize;
		} else {
			page = sql;
		}
		return page;
	}

	@Override
	public String toDate(String exp) {
		return "STR_TO_DATE(" + exp + ",'%Y-%m-%e')";
	}

	@Override
	public String toDateChar(String exp) {
		return "DATE_FORMAT(" + exp + ",'%Y-%m-%e')";
	}

	@Override
	public String toTime(String exp) {
		return "STR_TO_DATE(" + exp + ",'%H:%i:%S')";
	}

	@Override
	public String toTimeChar(String exp) {
		return "DATE_FORMAT(" + exp + ",'%H:%i:%S')";
	}

	@Override
	public String toDateTime(String exp) {
		return "STR_TO_DATE('" + exp + "','%Y-%m-%e %H:%i:%S')";
	}

	@Override
	public String toDateTimeChar(String exp) {
		return "DATE_FORMAT(" + exp + ",'%Y-%m-%e %H:%i:%S')";
	}

	@Override
	public String toDateTime(String exp, String format) {
		return "STR_TO_DATE(" + exp + ",'" + format + "')";
	}

	@Override
	public String toDateTimeChar(String exp, String format) {
		return "DATE_FORMAT(" + exp + ",'" + format + "')";
	}

	@Override
	public String getTableCommentSql() {
		return "SELECT TABLE_NAME, TABLE_COMMENT, TABLE_TYPE, TABLE_SCHEMA FROM INFORMATION_SCHEMA.TABLES where TABLE_TYPE = 'BASE TABLE' and TABLE_SCHEMA = ?";
	}

	@Override
	public String getColumnCommentSql() {
		return "SELECT COLUMN_NAME, COLUMN_COMMENT, TABLE_NAME FROM INFORMATION_SCHEMA.COLUMNS c where c.TABLE_NAME = ? and TABLE_SCHEMA = ?";
	}

}
