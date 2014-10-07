package net.linybin7.common.db;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-8-22-上午10:55:09 <br>
 * @description <br>
 *              TODO
 **/
public interface SQL {
	public String sql(Object... args);

	public String sqlid();

	public int dbtype();
}
