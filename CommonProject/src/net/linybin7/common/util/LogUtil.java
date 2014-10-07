package net.linybin7.common.util;

import org.apache.log4j.Logger;

/**
 * 日志工具类
 * 
 * @author JackenCai
 * 
 */
public class LogUtil {

	private static final Logger _DEBUGER = Logger.getLogger("debug");

	private static final Logger SYS_LOG = Logger.getLogger("system");

	private LogUtil() {

	}

	public static Logger getLogger() {
		return _DEBUGER;
	}

	public static Logger getSysLog() {
		return SYS_LOG;
	}

	public static String getRecursiveMsg(Throwable e) {
		StringBuffer sb = new StringBuffer();
		Throwable ept = e;
		do {
			sb.append(ept.getClass() + ":" + ept.getMessage());
		} while ((ept = ept.getCause()) != null);
		return sb.toString();
	}
}
