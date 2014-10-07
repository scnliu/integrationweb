/**
 * 
 */
package net.linybin7.pub.tools;

/**
 * @author HuangHuaSheng 2010-10-18 下午07:52:49
 */
public class Point {

	/**
	 * 经度
	 */
	public double lon;
	/**
	 * 纬度
	 */
	public double lat;
	private boolean link2mainCell;// 连接主小区

	public Point() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Point(double lon, double lat) {
		super();
		this.lon = lon;
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public boolean isLink2mainCell() {
		return link2mainCell;
	}

	public void setLink2mainCell(boolean link2mainCell) {
		this.link2mainCell = link2mainCell;
	}
}
