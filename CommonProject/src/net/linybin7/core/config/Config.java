package net.linybin7.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import net.linybin7.common.util.CollectionUtil;
import net.linybin7.common.util.IOUtil;
import net.linybin7.common.util.PropertiesUtil;
import net.linybin7.common.util.StringHelper;
import net.linybin7.core.util.Constants;
import net.linybin7.core.util.DirUtil;

import org.apache.log4j.Logger;


/**
 * 主题加载器
 * 
 * 
 * 
 */
public final class Config {
	private final Logger LOG = Logger.getLogger(Config.class);

	public static final String CITY_KEY = "city";

	public static final String SYSS = "syss";

	public static final String SYS_KEY = "sysKey";

	public static final String SYS_VERSION = "sysVersion";

	public static final String SYS_NAME = "sysName";

	public static final String SYS_TYPE = "isCenter";

	private static Config instance = new Config();

	private Properties props;

	private long lastModify = 0;

	private File configFile;

	private final SysCfg sysCfg = new SysCfg(this);

	private Map<String, String> citysCNMap = null;

	private Map<String, String> citysNCMap = null;

	private Config() {
		try {
			configFile = DirUtil.getContextPath("/WEB-INF/classes/config/config.properties");
			load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Config instance() {
		return instance;
	}

	public SysCfg getTopic() {
		return sysCfg;
	}

	public Map<String, String> getCitysCNMap() {
		load();
		return citysCNMap;
	}

	public Map<String, String> getCitysNCMap() {
		load();
		return citysNCMap;
	}

	public String getProp(String key) {
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
		return configFile;
	}

	/**
	 * 是否允许重新加载
	 * 
	 * @return
	 */
	protected boolean canReload() {
		if (configFile.lastModified() > lastModify) {
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
	private synchronized void load() {
		if (!canReload()) {
			return;
		}
		LOG.info("开始加载主题...");
		props = new Properties();
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(new FileInputStream(configFile), "GBK");
			PropertiesUtil.load(props, inputStreamReader);
			Map<String, String> map = new LinkedHashMap<String, String>();
			loadMap(map);
			citysCNMap = map;
			citysNCMap = CollectionUtil.trade(map);
			sysCfg.setSyss(StringHelper.trim(props.getProperty(SYSS)));
			sysCfg.setSysKey(StringHelper.trim(props.getProperty(SYS_KEY)));
			sysCfg.setSysVersion(StringHelper.trim(props.getProperty(SYS_VERSION)));
			sysCfg.setSysName(StringHelper.trim(props.getProperty(SYS_NAME)));
			String isCenter = props.getProperty(SYS_TYPE);
			sysCfg.setSysType(!StringHelper.isEmpty(isCenter)
					&& isCenter.trim().equalsIgnoreCase("true") ? Constants.SYS_TYPE_CENTER
					: Constants.SYS_TYPE_CLIENT);
			sysCfg.setTopicsMap(map);
			String city = StringHelper.trim(props.getProperty(CITY_KEY));
			sysCfg.setCityCode(city);
			sysCfg.setCityName(map.get(city.toUpperCase()));

			lastModify = configFile.lastModified();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(inputStreamReader);
		}
		LOG.info("加载主题结束");
	}

	/**
	 * 加载map对象
	 * 
	 * @param map
	 */
	private void loadMap(Map<String, String> map) {
		if (map == null) {
			return;
		}
		List<OrderCitry> orderCitys = new ArrayList<OrderCitry>();
		Set<Entry<Object, Object>> entrys = props.entrySet();
		for (Entry entry : entrys) {
			String key = StringHelper.trim(((String) entry.getKey()));
			String value = StringHelper.trim((String) entry.getValue());

			try {
				if (key.startsWith(CITY_KEY + ".")) {
					int index1 = key.indexOf(".");
					int index2 = key.indexOf("_");
					String code = key.substring(index1 + 1, index2);
					String index = key.substring(index2 + 1);
					orderCitys.add(new OrderCitry(index, code.toUpperCase(), value));
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		Collections.sort(orderCitys, new CityComparator());
		for (OrderCitry city : orderCitys) {
			map.put(city.code, city.name);
		}
	}

	private static class OrderCitry {
		private int order;

		private final String code;

		private final String name;

		public OrderCitry(String order, String code, String name) {
			try {
				this.order = Integer.parseInt(order);
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.code = code;
			this.name = name;
		}
	}

	private static class CityComparator implements Comparator<OrderCitry> {
		@Override
		public int compare(OrderCitry o1, OrderCitry o2) {
			return o1.order - o2.order;
		}

	}

	/**
	 * 系统配置
	 * 
	 * 
	 * 
	 */
	public static class SysCfg {
		// 所有主题map
		private Map<String, String> topicsMap = new HashMap<String, String>();

		// 可用系统
		private String syss;

		// 系统编号
		private String sysKey;

		// 系统版本
		private String sysVersion;

		// 系统名称
		private String sysName;

		// 系统类型
		private String sysType = Constants.SYS_TYPE_CLIENT;

		// 地市代码
		private String cityCode;

		// 地市名称
		private String cityName;

		// 前缀
		private String prefix = "";

		// 配置对象
		private final Config config;

		private SysCfg(Config config) {
			this.config = config;
		}

		public Map<String, String> getTopicsMap() {
			config.load();
			return topicsMap;
		}

		public void setTopicsMap(Map<String, String> topicsMap) {
			this.topicsMap = topicsMap;
		}

		public String getCityCode() {
			config.load();
			return cityCode;
		}

		public void setCityCode(String cityCode) {
			this.cityCode = cityCode;
		}

		public String getCityName() {
			config.load();
			return cityName;
		}

		public void setCityName(String cityName) {
			this.cityName = cityName;
		}

		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		public String getSyss() {
			config.load();
			return syss;
		}

		public void setSyss(String syss) {
			this.syss = syss;
		}

		public String getSysKey() {
			config.load();
			return sysKey;
		}

		public void setSysKey(String sysKey) {
			this.sysKey = sysKey;
		}

		public String getSysVersion() {
			config.load();
			return sysVersion;
		}

		public void setSysVersion(String sysVersion) {
			this.sysVersion = sysVersion;
		}

		public String getSysName() {
			config.load();
			return sysName;
		}

		public void setSysName(String sysName) {
			this.sysName = sysName;
		}

		public String getSysType() {
			config.load();
			return sysType;
		}

		public void setSysType(String sysType) {
			this.sysType = sysType;
		}

		/**
		 * 获得对应主题的CSS路径
		 * 
		 * @param topic
		 * @return
		 */
		public String css(String topic) {
			config.load();
			if (StringHelper.isEmpty(topic)) {
				// return prefix + "/css/" + cityCode;
				return prefix + "/css";
			} else {
				if (topic.equals("blue")) {
					return prefix + "/css";
				} else {
					return prefix + "/css/" + cityCode + "/" + topic;
				}
			}
		}

		/**
		 * 获得对应主题的图片路径
		 * 
		 * @param topic
		 * @return
		 */
		public String img(String topic) {
			config.load();
			if (StringHelper.isEmpty(topic)) {
				// return prefix + "/images/" + cityCode;
				return prefix + "/images";
			} else {
				if (topic.equals("blue")) {
					return prefix + "/images";
				} else {
					return prefix + "/images/" + cityCode + "/" + topic;
				}
			}
		}
	}
}
