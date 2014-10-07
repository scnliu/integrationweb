package net.linybin7.common.util;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 集合工具类
 * 
 * @author JackenCai
 * 
 */
public final class CollectionUtil {
	private CollectionUtil() {

	}

	/**
	 * 将map对象转化为二维数组
	 * 
	 * @param map
	 * @return
	 */
	public static String[][] map2Array(Map<String, String> map) {
		if (map.isEmpty()) {
			return null;
		}
		String[][] array = new String[map.size()][2];
		int i = 0;
		for (Entry<String, String> entry : map.entrySet()) {
			array[i][0] = entry.getKey();
			array[i][1] = entry.getValue();
			i++;
		}
		return array;
	}

	/**
	 * 将key值与value值对换
	 * 
	 * @param map
	 * @return
	 */
	public static Map trade(Map<String, String> map) {
		if (map == null) {
			return null;
		}
		try {
			Map newMap = (Map) map.getClass().newInstance();
			for (Entry<String, String> entry : map.entrySet()) {
				newMap.put(entry.getValue(), entry.getKey());
			}
			return newMap;
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}
	}

	/**
	 * 判断集合是否为空
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isEmpty(Collection c) {
		if (c == null || c.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断Map是否为空
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map map) {
		if (map == null || map.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(Object[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		return false;
	}
}
