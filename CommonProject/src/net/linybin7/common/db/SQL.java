package net.linybin7.common.db;

/**
 * ���ſƼ� <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-8-22-����10:55:09 <br>
 * @description <br>
 *              TODO
 **/
public interface SQL {
	public String sql(Object... args);

	public String sqlid();

	public int dbtype();
}
