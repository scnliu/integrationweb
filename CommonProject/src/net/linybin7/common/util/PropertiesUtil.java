package net.linybin7.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;


/**
 * 
 * Bensir
 * 
 * 2008-1-16 ÉÏÎç10:03:11
 */
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
		InputStream inputStream = PropertiesUtil.class.getResourceAsStream("/"
				+ propFileName);
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

	public static String getApplicationProperty(String key, String def) {		
		return PropertiesUtil.getProperty("/application.properties", key, def);
	}
	
	public static void load(Properties props, Reader reader) throws IOException {
		BufferedReader in = new BufferedReader(reader);
		while (true) {
			String line = in.readLine();
			if (line == null) {
				return;
			}
			line = StringHelper.trim(line);
			if (!StringHelper.isEmpty(line)) {
				char firstChar = line.charAt(0);
				if (firstChar != '#' && firstChar != '!') {
					int separatorIndex = line.indexOf("=");
					if (separatorIndex == -1) {
						separatorIndex = line.indexOf(":");
					}
					String key = (separatorIndex != -1 ? line.substring(0, separatorIndex) : line);
					String value = (separatorIndex != -1) ? line.substring(separatorIndex + 1) : "";
					key = StringHelper.trim(key);
					value = StringHelper.trim(value);
					value = StringHelper.symbo(value);
					props.put(key, value);
				}
			}
		}
	}
}
