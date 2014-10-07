package net.linybin7.scheduler.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-4-14-上午11:49:52 <br>
 * @description <br>
 *              TODO
 **/
public class PropertiesUtil {
	public static String getProperty(String propFileName, String key, String def) {
		Properties properties = PropertiesUtil.loadProperty(propFileName);
		String value = def;
		try {
			value = properties.getProperty(key, def);
			value = new String(value.getBytes("ISO-8859-1"), "GBK");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static Properties loadProperty(String propFileName) {
		InputStream inputStream = PropertiesUtil.class.getResourceAsStream("/" + propFileName);
		Properties properties = new Properties();

		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return properties;
	}

	public static Properties union(Properties... ps) {
		Properties properties = new Properties();
		for (int i = ps.length - 1; i >= 0; i--) {
			Properties p = ps[i];
			if (p != null && p.size() > 0)
				properties.putAll(p);
			// System.out.println(p);
		}
		// System.out.println(properties);
		return properties;
	}

	public static Properties unionDefault(Properties... ps) {
		return PropertiesUtil.union(PropertiesUtil.union(ps), PropertiesUtil
				.loadDefaultProperties());
	}

	public static Properties unionDefault(String propFileName) {
		return PropertiesUtil.union(PropertiesUtil.loadProperty(propFileName), PropertiesUtil
				.loadDefaultProperties());
	}

	public static Properties loadDefaultProperties() {
		return PropertiesUtil.loadProperty("com/eshine/scheduler/scheduler_default.properties");
	}
}
