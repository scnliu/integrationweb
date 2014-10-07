package net.linybin7.core.util;

import javax.servlet.http.HttpServletRequest;

import net.linybin7.core.frame.log.LoggerManager;
import net.linybin7.core.frame.bo.LogContent;

import org.apache.log4j.Logger;

public class SysLog {

	private static Logger logger = LoggerManager.getSysLogger();
	
	/**
	 * 	2013-3-11上午11:42:16
	 *	@param syslog[0]	String模块
	 *	@param syslog[1]	String内容
	 *	@param syslog[2]	HttpServletRequest
	 *	@param syslog[3]	Exception
	 */
	public static void info(String model,String msg,HttpServletRequest req,Exception e) {
		String level="I";		
		LogContent content=new LogContent();
		content.setMessage(level,model, msg,req, e);
		logger.info(content);
	}
	
	public static void warn(String model,String msg,HttpServletRequest req,Exception e) {
		String level="W";
		LogContent content=new LogContent();
		content.setMessage(level,model, msg,req, e);
		logger.warn(content);
	}
	
	public static void error(String model,String msg,HttpServletRequest req,Exception e) {
		String level="E";
		LogContent content=new LogContent();
		content.setMessage(level,model, msg,req, e);
		logger.error(content);
	}
	
	/**
	 *如果某个模块需要单独记录，在此声明入口，并修改net.linybin7.core.frame.log.JDBCSysAppender的flushBuffer方法
	public static void exmpleLog(Object...syslog){
		Logger logger = LoggerManager.getExmple();
		String level="E";
		String model=(String)syslog[0];
		String msg=(String)syslog[1];
		
		HttpServletRequest req=(HttpServletRequest)syslog[2];		
		Exception e=(Exception)syslog[3];
		
		LogContent content=new LogContent();
		content.setMessage(level,model, msg,req, e);
		logger.error(content);
	}
	*/
}