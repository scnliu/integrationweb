package net.linybin7.common.util;

/**
 * 

 * 
 */
public class StringUtil {
	private StringUtil() {

	}

	/**
	 * ÊÇ·ñ¿Õ°×
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * È¥µô×óÓÒ¿Õ°×
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		if (str == null) {
			return null;
		}
		return str.trim();
	}

	public static int parseInt(String str) {
		return Integer.parseInt(str);
	}
}
