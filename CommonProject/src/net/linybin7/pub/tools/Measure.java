/**
 * 
 */
package net.linybin7.pub.tools;

/**
 * @author HuangHuaSheng 2010-10-18 下午07:48:53
 */
public class Measure {
	public static final double EARTH_RADIUS = 6371.004;

	public static final double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 算两点之间距离
	 * 
	 * @author HuangHuaSheng 2010-10-18 下午07:50:16
	 * @param lng1
	 *            经度1
	 * @param lat1
	 *            纬度1
	 * @param lng2
	 *            经度2
	 * @param lat2
	 *            纬度2
	 * @return 返回结果以KM为单位
	 */
	public static final double getDistance(double lng1, double lat1, double lng2, double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
				* Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		// s = Math.ceil(s * 1000) / 1000;
		return s;
	}

	/**
	 * 扫频处理时计算两点
	 * 
	 * @author Linyubin 2011-3-2 上午08:53:12
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static final double getDistance2(double lng1, double lat1, double lng2, double lat2) {
		return Math.sqrt(Math.pow((lng1 - lng2) * 111.32 * Math.cos(Math.toRadians(lat1)), 2)
				+ Math.pow((lat1 - lat2) * 111.32, 2));
	}

	/**
	 * 计算两点垂直夹角时所用的距离公式
	 * 
	 * @author Linyubin 2011-3-2 上午08:51:45
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static final double getDistance3(double lng1, double lat1, double lng2, double lat2) {
		return Math.sqrt(Math.pow((lng1 - lng2) * 101, 2) + Math.pow((lat1 - lat2) * 112, 2));
	}

	/**
	 * 
	 * @author HuangHuaSheng 2010-10-20 下午04:11:59
	 * @param lng1
	 * @param lat1
	 * @param points
	 * @return
	 */
	public static final double min(double lng1, double lat1, double[] points) {
		double minDistance = 0;
		for (int i = 0; i < points.length - 1; i += 2) {
			double lng = points[i];
			double lat = points[i + 1];
			double distance = getDistance(lng1, lat1, lng, lat);
			if (minDistance == 0)
				minDistance = distance;
			if (distance < minDistance)
				minDistance = distance;

		}
		return minDistance;
	}

	/**
	 * 
	 * @author HuangHuaSheng 2010-10-20 下午04:12:21
	 * @param points
	 * @return
	 */
	public static final double min(double[] points) {
		double minDistance = 0;
		for (int i = 0; i < points.length - 1; i += 2) {
			double lng1 = points[i];
			double lat1 = points[i + 1];
			for (int j = points.length - 1; j > i + 2; j -= 2) {
				double lng = points[j - 1];
				double lat = points[j];
				double distance = getDistance(lng1, lat1, lng, lat);
				if (minDistance == 0)
					minDistance = distance;
				if (distance < minDistance)
					minDistance = distance;
			}
		}
		// int idx1=0,idx2=points.length-2;
		// for(int i=0;i<points.length-1;i+=2){
		// double lng1=points[i];
		// double lat1=points[i+1];
		// for(int j=points.length-1;j>i+2;j-=2){
		// double lng=points[j-1];
		// double lat=points[j];
		// double distance=Math.pow((lng-lng1), 2)+Math.pow((lat-lat1), 2);
		// if(minDistance==0)minDistance=distance;
		// if(distance<minDistance){
		// minDistance=distance;
		// idx1=i;idx2=j-1;
		// }
		// }
		// }
		// minDistance=getDistance(points[idx1],points[idx1+1],points[idx2],points[idx2+1]);
		return minDistance;
	}

	/**
	 * 
	 * @author HuangHuaSheng 2010-10-20 下午04:12:29
	 * @param lng1
	 * @param lat1
	 * @param points
	 * @return
	 */
	public static final double max(double lng1, double lat1, double[] points) {
		double maxDistance = 0;
		for (int i = 0; i < points.length - 1; i += 2) {
			double lng = points[i];
			double lat = points[i + 1];
			double distance = getDistance(lng1, lat1, lng, lat);
			if (distance > maxDistance)
				maxDistance = distance;

		}
		return maxDistance;
	}

	/**
	 * 
	 * @author HuangHuaSheng 2010-10-20 下午04:12:49
	 * @param points
	 * @return
	 */
	public static final double max(double[] points) {
		double maxDistance = 0;
		for (int i = 0; i < points.length - 1; i += 2) {
			double lng1 = points[i];
			double lat1 = points[i + 1];
			for (int j = points.length - 1; j > i + 2; j -= 2) {
				double lng = points[j - 1];
				double lat = points[j];
				double distance = getDistance(lng1, lat1, lng, lat);
				if (distance > maxDistance)
					maxDistance = distance;
			}
		}
		return maxDistance;
	}

	/**
	 * @author HuangHuaSheng 2010-10-20 下午04:12:59
	 * @param points
	 * @return
	 */
	public static final double avg(double[] points) {
		double totalDistance = 0;
		for (int i = 0; i < points.length - 1; i += 2) {
			double lng1 = points[i];
			double lat1 = points[i + 1];
			for (int j = points.length - 1; j > i + 2; j -= 2) {
				double lng = points[j - 1];
				double lat = points[j];
				totalDistance += getDistance(lng1, lat1, lng, lat);
			}
		}
		System.out.println("totalDistance:" + totalDistance);
		int len = points.length / 2;
		return totalDistance / (len * (len - 1) / 2);
	}

	/**
	 * 
	 * @author HuangHuaSheng 2010-10-20 下午04:13:08
	 * @param lng1
	 * @param lat1
	 * @param comparePoint
	 * @return
	 */
	public static final double avg(double lng1, double lat1, double[] comparePoint) {
		double totalDistance = 0;
		for (int i = 0; i < comparePoint.length - 1; i += 2) {
			double lng = comparePoint[i];
			double lat = comparePoint[i + 1];
			totalDistance += getDistance(lat1, lng1, lat, lng);
		}
		return totalDistance / (comparePoint.length / 2);
	}

	/**
	 * 计算小区跟测试点夹角Angle
	 * 
	 */
	public static int calAngle(double celllon, double celllat, double lon, double lat,
			double bearing) {
		int acsbear;

		// 计算测试点与小区相对北为的夹角
		if (celllon == lon && celllat > lat) {
			acsbear = 180;
		} else if (celllon == lon && celllat < lat) {
			acsbear = 0;
		} else if (celllon < lon && celllat == lat) {
			acsbear = 90;
		} else if (celllon > lon && celllat == lat) {
			acsbear = 270;
		} else if (celllon == lon && celllat == lat) {
			acsbear = -1;
		} else {
			acsbear = (int) Math.round(Math.toDegrees(Math.atan(Math.abs(celllon - lon)
					/ Math.abs(celllat - lat))));
		}

		if (celllon > lon && celllat < lat)
			acsbear = 360 - acsbear;
		else if (celllon > lon && celllat > lat)
			acsbear = 180 + acsbear;
		else if (celllon < lon && celllat > lat)
			acsbear = 180 - acsbear;

		// 计算出小区跟测试点夹角
		if (bearing > acsbear)
			acsbear = (int) (360 - Math.abs(bearing - acsbear));
		else
			acsbear = (int) (acsbear - bearing);
		return acsbear;
	}

	/**
	 * 计算垂直方向角
	 */
	public static int calVAngle(double ant_Height, double height, double distance, double downtilt,
			int angle) {
		int acsbear = 0;
		int vangle;
		double lat = (height - ant_Height) / 1000.0;
		double lon;
		double celllat = 0d;
		double celllon = 0d;
		if (angle >= 90 && angle <= 270)
			lon = 0.0d - distance;
		else
			lon = distance;
		if (celllat == lat) {
			if (celllon > lon)
				acsbear = 180;
			else if (celllon < lon)
				acsbear = 0;
		} else if (celllon == lon) {
			if (celllat > lat)
				acsbear = 270;
			else if (celllat < lat)
				acsbear = 90;
		} else {
			acsbear = (int) Math
					.round(Math.toDegrees(Math.atan((celllat - lat) / (celllon - lon))));
			if (celllon > lon)
				acsbear = acsbear + 180;// 第二象限、第三象限
			else if (acsbear < 0 && celllon < lon)
				acsbear = acsbear + 360;// 第四象限
		}
		acsbear = (450 - acsbear) % 360;// 转换为顺时针与正北的夹角

		if (acsbear - 90 > 0)
			acsbear = acsbear - 90;
		else
			acsbear = acsbear - 90 + 360;

		if (downtilt - acsbear > 0)
			vangle = (int) Math.round(360 - (downtilt - acsbear));
		else
			vangle = (int) Math.round(Math.abs(downtilt - acsbear));

		if (lat == celllat && lon == celllon)
			vangle = -1;
		return vangle;
	}

	/**
	 * 
	 * 计算空间耗损系数
	 * 
	 * @return
	 */
	public static double getGhv(int HAngle, int VAngle, double hsc, double vsc) {
		double W1;
		double W2;
		double V1;
		double V2;
		double U;
		double GHV = 0;
		W1 = Math.cos(Math.toRadians(HAngle)) * Math.cos(Math.toRadians(HAngle))
				* Math.sin(Math.toRadians(VAngle)) * Math.sin(Math.toRadians(VAngle));
		W2 = Math.cos(Math.toRadians(VAngle)) * Math.cos(Math.toRadians(VAngle));
		V1 = W1 * (1.0 - W2);
		V2 = W2 * (1.0 - W1);
		if (Math.sqrt(V1 * V1 + V2 * V2) != 0) {
			if (HAngle == 90 || HAngle == 270 || VAngle == 90) {
				U = 1.0;
			} else {
				U = 1.0 - Math.pow(10.0, hsc / 20);
				GHV = (hsc * V1 + vsc * V2) / Math.sqrt((V1 * V1 + V2 * V2)) * (1.0 - U)
						+ (hsc + vsc) * U;
			}
		} else {
			GHV = hsc;
		}
		if (hsc >= GHV && hsc >= vsc)
			GHV = hsc;
		else if (vsc >= hsc && vsc >= GHV)
			GHV = vsc;
		
		return Math.abs(GHV);
	}

	/**
	 * 计算小区有效范围
	 * 
	 * @author Linyubin 2011-3-4 上午08:54:37
	 * @param height
	 *            天线高度
	 * @param downtilt
	 *            天线下倾角
	 * @param vertical
	 *            天线半功率角，默认13
	 * @return
	 */
	public static double getCddDistance(double height, double downtilt, double vertical) {
		double distance;
		double angle = downtilt - vertical / 2;
		if (angle < 1)
			angle = 1.0d;
		distance = Math.round(height / Math.tan(Math.toRadians(angle)));
		return distance / 1000;
	}

	public static void main(String[] args) {
		double lng1 = 168.82421875;
		double lat1 = 45.32421875;
		double lng2 = 169;
		double lat2 = 0.67578125;
		double lng3 = 105.89453125;
		double lat3 = 19.1328125;
		double lng4 = 122.76953125;
		double lat4 = 37.0625;
		double lng5 = 105.89453125;
		double lat5 = 19.1328125;
		double lng6 = 122.76953125;
		double lat6 = 37.0625;
		// 1,1 1,4 4,4 4,1
		double[] cmpPoint = { lng1, lat1, lng2, lat2, lng3, lat3, lng4, lat4 };
		// double[] cmpPoint={1,2,3,1,5,3,5,2,3,1,5,3,5,2,56,4,7,98,54,54};
		System.out.println("distance1:" + getDistance(lng1, lat1, lng2, lat2));
		System.out.println("distance2:" + getDistance(lng3, lat3, lng4, lat4));
		System.out.println("distance3:" + getDistance(lng2, lat2, lng4, lat4));
		System.out.println("distance4:" + getDistance(lng2, lat2, lng3, lat3));
		System.out.println("distance5:" + getDistance(lng1, lat1, lng4, lat4));
		System.out.println("distance6:" + getDistance(lng3, lat3, lng1, lat1));
		long start = System.currentTimeMillis();
		for (int k = 0; k < 1000000; k++) {
			min(cmpPoint);
		}
		System.out.println("min:" + min(cmpPoint));
		System.out.println("t:" + (System.currentTimeMillis() - start));
		System.out.println("max:" + max(cmpPoint));
		System.out.println("t:" + (System.currentTimeMillis() - start));
		System.out.println("avg" + avg(cmpPoint));
		System.out.println("t:" + (System.currentTimeMillis() - start));
		String title = "";
		byte[] bt = "_".getBytes();
		for (byte b : bt) {
			System.out.println(b);
			if ((b > 64 && b < 128)) {
				System.out.println(b);
				Character c = (char) b;
				System.out.println(c);
				title += c;
			}
		}
	}

}
