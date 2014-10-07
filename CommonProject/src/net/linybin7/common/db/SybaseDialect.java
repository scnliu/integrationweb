package net.linybin7.common.db;


/**
 * 
 * 
 *
 */
public class SybaseDialect extends AbstractDialect {

	public String toDate(String exp) {
		return "CONVERT(datetime," + exp + ",111)";
	}

	public String toDateTime(String exp) {
		return "CONVERT(datetime," + exp + ")";
	}

	public String toDateChar(String exp) {
		return "CONVERT(varchar," + exp + ",111)";
	}

	public String toDateTimeChar(String exp) {
		return "CONVERT(varchar," + exp + ")";
	}

	public String getPaginSQL(String sql, int currentPage, int pageSize) {
		return sql;
	}

	@Override
	public String toTime(String exp) {
		return exp;
	}

	@Override
	public String toTimeChar(String exp) {
		return exp;
	}

	@Override
	public String toDateTime(String exp, String format) {
		return exp;
	}

	@Override
	public String toDateTimeChar(String exp, String format) {
		return exp;
	}

}
