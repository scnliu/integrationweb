package net.linybin7.common.util;

/**
 * 
 * StringUtils.java <br>
 *  <br>
 * Bensir <br>
 * 2008-12-17 ÏÂÎç04:09:32 <br>
 */

public class StringUtils {

	public static boolean isNnull(String value) {
		return (value == null || value.trim().length() == 0) ? true : false;
	}

	public static String getString(String value, String def) {
		if (isNnull(value))
			return def;
		return value.trim();
	}

	public static String getString(String value) {
		return getString(value, "");
	}

	public static int getInt(String value, int def) {
		if (isNnull(value))
			return def;
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {

		}
		return def;
	}

	public static int getInt(String value) {
		return getInt(value, 0);
	}

	public static boolean getBoolean(String value, boolean def) {
		if (isNnull(value))
			return def;
		return Boolean.valueOf(value);
	}

	public static boolean getBoolean(String value) {
		return getBoolean(value, false);
	}

	public static long getLong(String value, long def) {
		if (isNnull(value))
			return def;
		try {
			return Long.valueOf(value);
		} catch (NumberFormatException e) {

		}
		return def;
	}
}
