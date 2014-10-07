package net.linybin7.common.util;

/**
 * 数字工具
 * 
 * 
 * 
 */
public final class NumberUtil {

	private NumberUtil() {

	}

	/**
	 * 解释对象为int
	 * 
	 * @param obj
	 * @param defalue
	 * @return
	 */
	public static int parseInt(String value, int defalue) {
		try {
			return Integer.parseInt(value.toString());
		} catch (Exception e) {
			return defalue;
		}
	}

	public static boolean parseBoolan(String value, boolean def) {
		if (StringHelper.isEmpty(value)) {
			return def;
		}
		if ("yes".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value)) {
			return true;
		}
		return false;
	}

}
