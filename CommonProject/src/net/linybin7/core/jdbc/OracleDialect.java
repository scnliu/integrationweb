package net.linybin7.core.jdbc;

/**
 * 
 * 
 *
 */
public class OracleDialect extends AbstractDialect {

	public String toDate(String exp) {
		return "to_date(" + exp + ",'yyyy/mm/dd')";
	}

	public String toDateTime(String exp) {
		return "to_date(" + exp + ",'yyyy-mm-dd hh24:mi:ss')";
	}

	public String toDateChar(String exp) {
		return "to_char(" + exp + ",'yyyy/mm/dd')";
	}

	public String toDateTimeChar(String exp) {
		return "to_char(" + exp + ",'yyyy-mm-dd hh24:mi:ss')";
	}

	public String getPaginSQL(String sql, int currentPage, int pageSize) {
		return sql;
	}
}
