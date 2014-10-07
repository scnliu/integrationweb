package net.linybin7.common.util;

import java.util.Enumeration;
import java.util.ResourceBundle;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


public class ResourceBundleUtil {

	public static final String BASE_NAME = "application";
	static {
		ResourceBundleUtil.setAllProperies();
	}

	public static final String getProperty(String key) {
		ResourceBundle rb = ResourceBundle.getBundle(BASE_NAME);
		String path = rb.getString(key);
		if (path != null) {
			StringBuffer buf = new StringBuffer(path);
			String prefix = PropertyPlaceholderConfigurer.DEFAULT_PLACEHOLDER_PREFIX;
			String suffix = PropertyPlaceholderConfigurer.DEFAULT_PLACEHOLDER_SUFFIX;

			int startIndex = path.indexOf(prefix);
			while (startIndex != -1) {
				int endIndex = path.indexOf(suffix, startIndex
						+ prefix.length());
				if (endIndex != -1) {
					String placeholder = path.substring(startIndex
							+ prefix.length(), endIndex);

					String propVal = ResourceBundleUtil.getProperty(placeholder);
					if (propVal != null) {
						buf.replace(startIndex, endIndex + suffix.length(),
								propVal);
						startIndex = buf.toString().indexOf(prefix,
								startIndex + propVal.length());
					} else {
						throw new BeanDefinitionStoreException("ÎÞ·¨½âÎö '"
								+ placeholder + "'");
					}
				} else {
					startIndex = -1;
				}
			}
			return buf.toString();
		} else {
			return path;
		}
	}

	public static void main(String args[]) {
		try {
			System.out.println(ResourceBundleUtil.getProperty("configpath"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setAllProperies() {
		ResourceBundle rb = ResourceBundle.getBundle(BASE_NAME);
		Enumeration<String> en = rb.getKeys();
		while (en.hasMoreElements()) {
			String key = en.nextElement();
			String value = ResourceBundleUtil.getProperty(key);
			System.setProperty(key, value);
		}

	}
}
