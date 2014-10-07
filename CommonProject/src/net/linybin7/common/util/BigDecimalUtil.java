package net.linybin7.common.util;

import java.math.BigDecimal;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-8-23-下午08:47:38 <br>
 * @description <br>
 *              TODO
 **/
public class BigDecimalUtil {
	
	public static BigDecimal get(Object obj) {
		if (obj == null)
			return null;

		if (obj.getClass().getName().equals(String.class.getName())) {
			return new BigDecimal((String) obj);
		}
		if (obj.getClass().getName().equals(Short.class.getName())) {
			return new BigDecimal((Short) obj);
		}

		if (obj.getClass().getName().equals(Integer.class.getName())) {
			return new BigDecimal((Integer) obj);
		}

		if (obj.getClass().getName().equals(Long.class.getName())) {
			return new BigDecimal((Long) obj);
		}

		if (obj.getClass().getName().equals(Float.class.getName())) {
			return new BigDecimal((Float) obj);
		}

		if (obj.getClass().getName().equals(Double.class.getName())) {
			return new BigDecimal((Double) obj);
		}
		
		if (obj.getClass().getName().equals(BigDecimal.class.getName())) {
			return  (BigDecimal) obj ;
		}

		return null;
	}
}
