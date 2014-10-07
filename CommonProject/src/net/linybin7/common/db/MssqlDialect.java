package net.linybin7.common.db;


/**
 * 
 * 
 *
 */
public class MssqlDialect extends SybaseDialect {

	public String toDate(String exp) {
		return "CONVERT(datetime," + exp + ",111)";
	}

	public String toDateTime(String exp) {
		return "CONVERT(datetime," + exp + ",120)";
	}

	public String toDateChar(String exp) {
		return "CONVERT(varchar," + exp + ",111)";
	}

	public String toDateTimeChar(String exp) {
		return "CONVERT(varchar," + exp + ",120)";
	}

	public String getPaginSQL(String sql, int currentPage, int pageSize) {
		return sql;
	}
}
