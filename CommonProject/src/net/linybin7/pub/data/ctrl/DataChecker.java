package net.linybin7.pub.data.ctrl;

import java.util.List;

import net.linybin7.pub.data.bo.DataCheck;

/**
 * @author Linyubin
 * 
 */
public interface DataChecker {
	/**
	 * 
	 * @author Linyubin 2012-2-28上午10:47:32
	 * @param title
	 *            导入文件列名
	 * @param objs
	 *            导入数据
	 * @param datachek
	 *            检查状态
	 * @return
	 */
	public DataCheck check(List title, Object[] objs, DataCheck datachek);
}
