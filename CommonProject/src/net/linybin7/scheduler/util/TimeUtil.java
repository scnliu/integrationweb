package net.linybin7.scheduler.util;

import java.text.MessageFormat;

/**
 * ���ſƼ� <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-4-12-����11:53:19 <br>
 * @description <br>
 *              TODO
 **/
public class TimeUtil {
	/**
	 * �Ѻ���ת��Ϊ�磺x��x��x��xСʱx��x��x����
	 * 
	 * @param mis
	 *            ����
	 * @param format
	 *            ת����ʽ YMDHmSs
	 * 
	 * @return TODO �д�����
	 */

	public static String format(Long mis, String format) {
		if (format == null || format.trim().length() == 0) {
			return mis + "";
		}
		if (mis == null) {
			return null;
		}
		Long sec = mis.longValue() / 1000;
		mis = mis % 1000;
		Long min = sec / 60;
		sec = sec % 60;
		Long hour = min / 60;
		min = min % 60;
		Long day = hour / 24;
		hour = hour % 24;
		Long mon = day / 30;
		day = day % 30;
		Long year = mon / 12;
		mon = mon % 12;

		String formater = format;
		formater = formater.replace("Y", "{6}");
		formater = formater.replace("M", "{5}");
		formater = formater.replace("D", "{4}");
		formater = formater.replace("H", "{3}");
		formater = formater.replace("m", "{2}");
		formater = formater.replace("S", "{1}");
		formater = formater.replace("s", "{0}");

		MessageFormat form = new MessageFormat(formater);
		return form.format(new Long[] { mis, sec, min, hour, day, mon, year });
	}

	/**
	 * �Ѻ���ת��Ϊ�磺x��x��x��xСʱx��x��x����
	 * 
	 * @param mis
	 *            ����
	 */
	public static String format(Long mis) {
		if (mis == null)
			return "";
		String format = "";

		long sec = mis / 1000;
		if (sec > 0) {
			format = "S��";
		} else {
			format = "s����";
		}

		long min = sec / 60;
		if (min > 0) {
			format = "m��" + format;
		}

		long hour = min / 60;
		if (hour > 0) {
			format = "Hʱ" + format;
		}

		long day = hour / 24;
		if (day > 0) {
			format = "D��" + format;
		}

		long mon = day / 30;
		if (mon > 0) {
			format = "M��" + format;
		}

		long year = mon / 12;
		if (year > 0) {
			format = "Y��" + format;
		}

		return format(mis, format);
	}
}
