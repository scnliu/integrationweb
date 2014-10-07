package net.linybin7.common.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ��ʽ��������
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
	 * �����ڶ����ʽ��Ϊָ����ʽ
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
	 * ��ʽ������
	 * @param value
	 * @return
	 */
	public static String formateNum(double value) {
		return numFormater.format(value);
	}

	/**
	 * ��ʽ���ַ�������
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
	 * ��ʽ��������ʱ��
	 * @param value
	 * @return
	 */
	public static String formateTime(long value) {
		return value / 1000 / 60 / 60 + "Сʱ " + value / 1000 / 60 % 60 + "�� "
				+ value / 1000 % 60 + "�� " + value % 1000 + "����";
	}

	/**
	 * ����Ϊyyyy-MM-dd���ַ���ת��ΪDate����
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
	 * ����Ϊyyyy-MM-dd HH:mm:ss���ַ���ת��ΪDate����
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
	 * ��Date�����ʽ��Ϊyyyy-MM-dd��ʽ
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
	 * ��Date�����ʽ��Ϊyyyy-MM-dd HH:mm:ss��ʽ
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
