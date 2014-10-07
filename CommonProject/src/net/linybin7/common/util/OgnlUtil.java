package net.linybin7.common.util;

import java.util.Map;

import ognl.Ognl;

/**
 * Ognl工具类
 * @author JackenCai
 *
 */
public class OgnlUtil {
	
	private static final OgnlUtil instance = new OgnlUtil();
	
	private OgnlUtil(){
		
	}
	
	public static OgnlUtil getInstance(){
		return instance;
	}
	
	public static Map getContext(Object obj)throws Exception{
		try {
			return Ognl.createDefaultContext(obj);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static Object getValue(String exp, Object rootObj)throws Exception{
		try {
			return Ognl.getValue(exp, rootObj);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 解释字符串,将${}替换为真实值

	 * @param exp
	 * @param rootObj
	 * @return
	 * @throws Exception
	 */
	public static String parse(String exp, Object rootObj)throws Exception{
		if(exp == null || exp.indexOf("${") < 0){
			return exp;
		}
		int startIndex = -1;
		int endIndex = 0;
		while( (startIndex = exp.indexOf("${", startIndex + 1)) >= 0){
			endIndex = exp.indexOf("}", startIndex);
			if(endIndex <= 0){
				break;
			}
			String prop = exp.substring(startIndex + 2, endIndex);
			Object value = getValue(prop, rootObj);
			exp = exp.substring(0, startIndex) + value + exp.substring(endIndex + 1);
		}
		return exp;
	}
	
	public static Object getValue(String exp, Map context, Object rootObj)throws Exception{
		try {
			return Ognl.getValue(exp, context, rootObj);
		} catch (Exception e) {
			throw e;
		}
	}
	
}
