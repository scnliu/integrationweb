package net.linybin7.common.log;


/**
 * ��־������
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
