package net.linybin7.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * 日期时间工具
 * 
 *
 */
public final class DateUtil {
	
	private static final SimpleDateFormat DATA_FRRMATER = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final SimpleDateFormat DATATIME_FRRMATER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	public static Date toDate(String value) throws ParseException{
		if(StringHelper.isEmpty(value)){
			return null;
		}
		return DATA_FRRMATER.parse(value);
	}
	
	public static Date toDateTime(String value) throws ParseException{
		if(StringHelper.isEmpty(value)){
			return null;
		}
		return DATATIME_FRRMATER.parse(value);
	}
	
	public static String toDateString(Date value){
		if(value == null){
			return "";
		}
		return DATA_FRRMATER.format(value);
	}
	
	public static String toDateTimeString(Date value){
		if(value == null){
			return "";
		}
		return DATATIME_FRRMATER.format(value);
	}
}
