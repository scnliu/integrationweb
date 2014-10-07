package net.linybin7.pub.data.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.linybin7.common.util.PropertiesUtil;

import org.apache.log4j.Logger;


/**
 * @author hezheng 2011-6-16 下午08:19:48
 */
public class FileHelper {
	private static final Logger logger = Logger.getLogger(FileHelper.class);

	public static String getBasePath() {
		Properties properties = PropertiesUtil.loadProperty("config/config.properties");
		String basePath = properties.getProperty("ulPath");
		return basePath;
	}

	public static String getPath(String childrenPath) {
		String path = childrenPath;
		if ("base".equals(childrenPath)) {
			path = getBasePath() + "base";
		} else if ("ericsson".equals(childrenPath)) {
			path = getBasePath() + "ericsson";
		} else if ("huawei".equals(childrenPath)) {
			path = getBasePath() + "huawei";
		}
		return path;
	}

	public List<Files> getFileInfo(String childrenPath) throws Exception {
		String path = getPath(childrenPath);
		List<Files> fileList = new ArrayList<Files>();
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File ff : files) {
					Files f = new Files();
					f.setFileName(ff.getName());
					f.setFileSize(ff.length() / 1024);
					fileList.add(f);
				}
			}
		} else {
			logger.error("目录不存在");
		}
		return fileList;
	}
}