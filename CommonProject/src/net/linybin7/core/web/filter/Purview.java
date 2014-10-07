package net.linybin7.core.web.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import net.linybin7.common.util.IOUtil;
import net.linybin7.common.util.PropertiesUtil;
import net.linybin7.common.util.StringHelper;
import net.linybin7.core.util.DirUtil;

import org.apache.log4j.Logger;


/**
 * 主题加载器
 * 
 * 
 * 
 */
public final class Purview {
	private final Logger LOG = Logger.getLogger(Purview.class);

	private static Purview instance = new Purview();

	private Properties props;

	private long lastModify = 0;

	private File purviewFile;

	private Map<String, String> paramsMap = new HashMap<String, String>();
	
	private Map<String, List<String>> noFilterUrlMap = new HashMap<String, List<String>>();

	private Map<String, String> jumpUrlMap = new HashMap<String, String>();

	private Purview() {
		try {
			purviewFile = DirUtil.getContextPath("/WEB-INF/classes/config/purview.properties");
			load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Purview instance() {
		return instance;
	}

	private String getProp(String key) {
		load();
		if (key == null || key.trim().length() == 0) {
			return null;
		}

		return StringHelper.trim(props.getProperty(key));
	}

	/**
	 * 获得配置文件
	 * 
	 * @return
	 */
	public File getConfigFile() {
		return purviewFile;
	}

	/**
	 * 是否允许重新加载
	 * 
	 * @return
	 */
	protected boolean canReload() {
		if (purviewFile.lastModified() > lastModify) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 加载主题
	 * 
	 * @param topic
	 */
	private void load() {
		if (!canReload()) {
			return;
		}
		LOG.info("开始加载权限配置...");
		props = new Properties();
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(new FileInputStream(purviewFile), "GBK");
			PropertiesUtil.load(props, inputStreamReader);
			resetParamsMap();
			lastModify = purviewFile.lastModified();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(inputStreamReader);
		}
		LOG.info("加载权限配置结束");
	}

	private void resetParamsMap() {
		System.out.println("props:" + props);

		paramsMap = new HashMap<String, String>();
		for (Entry<Object, Object> entry : props.entrySet()) {
			String key = entry.getKey().toString().trim();
			try {
				int index = key.indexOf("?");
				if (index > 0) {
					paramsMap.put(key.substring(0, index), key.substring(index + 1));
				}
			} catch (Exception e) {
			}

			if (key.startsWith("no.filter.url.")) {
				String value = (String) entry.getValue();
				if (value != null && value.trim().length() > 0) {
					int index = value.indexOf("?");
					if (index > 0) {
						String url = value.substring(0, index);
						String action = value.substring(index + 1);
						int idx = action.indexOf("=");
						if (idx > 0) {
							String act = action.substring(idx + 1);
							noFilterUrlMap.put(url, Arrays.asList(act.split(",")));
						}
					}
				}
			}

			if (key.startsWith("no.login.jump.url.")) {
				String sp = key.replace("no.login.jump.url.", "");
				String value = (String) entry.getValue();

				if (value != null && value.trim().length() > 0) {
					jumpUrlMap.put(sp, value);
				}
			}
		}
		System.out.println("jumpUrlMap:" + jumpUrlMap);
		System.out.println("noFilterUrlMap:" + noFilterUrlMap);
		System.out.println("paramsMap:" + paramsMap);
	}

	private String key(HttpServletRequest req, String key) {
		String value = paramsMap.get(key);
		if (!StringHelper.isEmpty(value)) {
			String[] splits = value.split(",");
			StringBuilder builder = new StringBuilder(key);
			builder.append("?");
			for (String s : splits) {
				int index = s.indexOf("-");
				if (index > 0) {
					String param = s.substring(0, index);
					builder.append(param);
					builder.append("-");
					builder.append(StringHelper.trim(req.getParameter(param)));
					builder.append(",");
				}
			}
			if (builder.charAt(builder.length() - 1) == ',') {
				builder.deleteCharAt(builder.length() - 1);
			}
			return builder.toString();
		} else {
			return key;
		}
	}

	public String funcCode(HttpServletRequest req, String key) {
		load();
		String realKey = key(req, key);
		String funcCode = null;
		try {
			funcCode = props.getProperty(realKey);
			// System.out.println("key:" + realKey + "; funCode:" + funcCode);
		} catch (Exception e) {
		}
		return funcCode;
	}

	public Map<String, List<String>> getNoFilterUrlMap() {
		return noFilterUrlMap;
	}

	public Map<String, String> getJumpUrlMap() {
		return jumpUrlMap;
	}

	public void setJumpUrlMap(Map<String, String> jumpUrlMap) {
		this.jumpUrlMap = jumpUrlMap;
	}

}
