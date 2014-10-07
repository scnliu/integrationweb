package net.linybin7.common.util;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * ���Ϲ�����
 * 
 * @author JackenCai
 * 
 */
public final class CollectionUtil {
	private CollectionUtil() {

	}

	/**
	 * ��map����ת��Ϊ��ά����
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
	 * ��keyֵ��valueֵ�Ի�
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
	 * �жϼ����Ƿ�Ϊ��
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
	 * �ж�Map�Ƿ�Ϊ��
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
	 * �ж������Ƿ�Ϊ��
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
