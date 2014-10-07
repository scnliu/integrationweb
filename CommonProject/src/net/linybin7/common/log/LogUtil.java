package net.linybin7.common.log;


/**
 * 日志工具类
 * 
 * @author JackenCai
 * 
 */
public class LogUtil {

	public static String getRecursiveMsg(Throwable e) {
		StringBuffer sb = new StringBuffer();
		Throwable ept = e;
		do {
			sb.append(ept.getClass() + ":" + ept.getMessage());
		} while ((ept = ept.getCause()) != null);
		return sb.toString();
	}
}
