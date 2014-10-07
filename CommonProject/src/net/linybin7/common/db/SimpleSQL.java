package net.linybin7.common.db;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-8-22-上午11:14:22 <br>
 * @description <br>
 *              TODO
 **/
public class SimpleSQL implements SQL {
	private String sqlid;
	private int dbtype;
	private String sql;

	public SimpleSQL() {
		super();
	}

	public SimpleSQL(String sqlid, int dbtype) {
		super();
		this.sqlid = sqlid;
		this.dbtype = dbtype;
	}

	public String getSqlid() {
		return sqlid;
	}

	public void setSqlid(String sqlid) {
		this.sqlid = sqlid;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public int getDbtype() {
		return dbtype;
	}

	public void setDbtype(int dbtype) {
		this.dbtype = dbtype;
	}

	@Override
	public int dbtype() {
		return this.dbtype;
	}

	@Override
	public String sqlid() {
		return this.sqlid;
	}

	@Override
	public String sql(Object... args) {
		return this.sql;
	}

}
