/**
 * 
 */
package net.linybin7.core.frame.log;

import org.apache.log4j.Logger;

/**
 * @author Huangyouwen 2010-11-23 обнГ03:01:25
 */
public class LoggerManager {

	private static Logger sysLogger = Logger.getLogger("net.linybin7.log.sys");

	public static Logger getSysLogger() {
		return sysLogger;
	}

	public static void setSysLogger(Logger sysLogger) {
		LoggerManager.sysLogger = sysLogger;
	}

}
