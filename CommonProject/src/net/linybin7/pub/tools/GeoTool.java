/**
 * 
 */
package net.linybin7.pub.tools;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;


/**
 * @author HuangHuaSheng 2011-2-18 下午02:20:58
 */
public class GeoTool {
	public static String username;
	public static String password;
	public static String url;
	public static String driverClass;

	public static void setJdbc(Map connectionParameters) {
		try {
			if (url == null) {
				// BasicDataSource ds=(BasicDataSource)beanFactory.getBean("coreDs");
				// WMS wms=(WMS)context.getAttribute("WMS");
				// Catalog catalog=wms.getGeoServer().getCatalog();
				// DataStoreInfo store=catalog.getStoreByName("cell",DataStoreInfo.class);
				Properties p = new Properties();
				InputStream is = GeoTool.class.getClassLoader().getResourceAsStream(
						"config/DBConnect.properties");
				p.load(is);
				username = p.getProperty("username");
				password = p.getProperty("password");
				driverClass = p.getProperty("driverClassName");
				url = p.getProperty("url");
				String[] ps = url.split(":");
				String host = ps[3].replace("@", "");
				String db = ps[ps.length - 1];
				connectionParameters.put("database", db);
				connectionParameters.put("host", host);
				connectionParameters.put("schema", username);
				connectionParameters.put("user", username);
				connectionParameters.put("passwd", password);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author HuangHuaSheng <br>
	 * @since 2011-3-10 <br>
	 * @param lon
	 *            中心点经度
	 * @param lat
	 *            中心点纬度
	 * @param radius
	 *            边长
	 * @description <br>
	 *              转换正方形
	 */
	public static double[] convertRectangle(double lon, double lat, float length) {
		double radius = Math.sqrt(length * length * 2);//
		double girth = Measure.EARTH_RADIUS * Math.PI * 1000 * 2;// 地球一圈周长
		double latGirth = girth * Math.cos(Math.PI * (lat / 180));// 所在纬度圈周长
		double lonDiff = (length / 2 / girth) * 360;
		double latDiff = (length / 2 / latGirth) * 360;
		double lon1 = lon + lonDiff;
		double lat1 = lat + latDiff;
		double lon2 = lon - lonDiff;
		double lat2 = lat - latDiff;
		// double distance=Measure.getDistance(lon1,lat1,lon2,lat2)*1000;
		return new double[] { lon1, lat1, lon1, lat2, lon2, lat2, lon1, lat2, lon1, lat1 };
	}

	public static void main(String[] args) {
		convertRectangle(113.98210000, 22.53770000, 12);
	}

	public static double longToSphericalMercatorX(double x) {
		return (x / 180.0) * 20037508.34;
	}

	public static double latToSphericalMercatorY(double y) {
		if (y > 85.05112) {
			y = 85.05112;
		}

		if (y < -85.05112) {
			y = -85.05112;
		}

		y = (Math.PI / 180.0) * y;
		double tmp = Math.PI / 4.0 + y / 2.0;
		return 20037508.34 * Math.log(Math.tan(tmp)) / Math.PI;
	}

	public static double lonToX(double lon) {
		// return GeoTool.longToSphericalMercatorX(lon);
		return lon * 100000;
	}

	public static double latToY(double lat) {
		// return GeoTool.latToSphericalMercatorY(lat);
		return lat * 100000;
	}
}
