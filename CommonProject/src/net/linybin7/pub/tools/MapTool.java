/**
 * 
 */
package net.linybin7.pub.tools;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;


/**
 * @author HuangHuaSheng 2011-1-17 ÉÏÎç11:11:34
 */
public final class MapTool {
	public static String getStrokeColor(String fillColor) {
		if (fillColor == null)
			fillColor = "#ffff00";
		Integer s = Integer.decode(fillColor);
		return Integer.toHexString(s - (4049));
	}

	public Image createImage(List<Point> data) {
		List SphericalMtData = new ArrayList(data.size());
		for (Point p : data) {
			double x = GeoTool.longToSphericalMercatorX(p.lon);
			double y = GeoTool.latToSphericalMercatorY(p.lat);
			Point smP = new Point(x, y);
			SphericalMtData.add(smP);
		}
		return null;
	}
}
