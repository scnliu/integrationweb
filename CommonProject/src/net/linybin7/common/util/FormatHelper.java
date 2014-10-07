package net.linybin7.common.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 格式化工具类
 * @author JackenCai
 *
 */
public class FormatHelper {
	private static NumberFormat numFormater = NumberFormat.getInstance();

	private static final SimpleDateFormat DATA_FRRMATER = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static final SimpleDateFormat DATATIME_FRRMATER = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 将日期对象格式化为指定格式
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		if (date == null)
			return "";
		if (StringHelper.isEmpty(format)) {
			format = "yyyy-MM-dd HH:mm:ss.ms";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String formatString(String value, int length) {
		return formatString(value, 0, length);
	}

	public static String formatString(String value, int start, int offset) {
		if (StringHelper.isEmpty(value))
			return "";
		if (value.length() < offset)
			return value;
		return value.substring(start, offset) + " ......";
	}

	/**
	 * 格式化数字
	 * @param value
	 * @return
	 */
	public static String formateNum(double value) {
		return numFormater.format(value);
	}

	/**
	 * 格式化字符串数字
	 * @param string
	 * @return
	 */
	public static String formateNum(String value) {
        try {
            return numFormater.format(value);
		} catch (Exception e) {
			return value;
		}
    }
	
	/**
	 * 格式化长整形时间
	 * @param value
	 * @return
	 */
	public static String formateTime(long value) {
		return value / 1000 / 60 / 60 + "小时 " + value / 1000 / 60 % 60 + "分 "
				+ value / 1000 % 60 + "秒 " + value % 1000 + "毫秒";
	}

	/**
	 * 将形为yyyy-MM-dd的字符串转化为Date对象
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(String value) throws ParseException {
		if (StringHelper.isEmpty(value)) {
			return null;
		}
		return DATA_FRRMATER.parse(value);
	}

	/**
	 * 将形为yyyy-MM-dd HH:mm:ss的字符串转化为Date对象
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	public static Date toDateTime(String value) throws ParseException {
		if (StringHelper.isEmpty(value)) {
			return null;
		}
		return DATATIME_FRRMATER.parse(value);
	}

	/**
	 * 将Date对象格式化为yyyy-MM-dd形式
	 * @param value
	 * @return
	 */
	public static String toDateString(Object value) {
		if (value == null) {
			return "";
		}
		return DATA_FRRMATER.format(value);
	}

	/**
	 * 将Date对象格式化为yyyy-MM-dd HH:mm:ss形式
	 * @param value
	 * @return
	 */
	public static String toDateTimeString(Object value) {
		if (value == null) {
			return "";
		}
		return DATATIME_FRRMATER.format(value);
	}
}
