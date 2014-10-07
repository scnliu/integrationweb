package net.linybin7.core.util;

/**
 * ���ſƼ� <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-4-25-����04:30:38 <br>
 * @description <br>
 *              TODO
 **/
public class PathUtil {
	public static void main(String[] args) throws Exception {
		PathUtil p = new PathUtil();
		// System.out.println(p.getWebClassesPath());
		// System.out.println(p.getWebInfPath());
		// System.out.println(p.getWebRoot());
	}

	// ��ȡ��ǰ�ļ�·��

	public String getWebClassesPath() {
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		return path;
	}

	// ��ȡ��ǰ���̵�web-inf·��

	public String getWebInfPath() throws IllegalAccessException {
		String path = getWebClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF") + 8);
		} else {
			throw new IllegalAccessException("·����ȡ����");
		}
		return path;
	}

	// ��ȡ��ǰ����·��

	public String getWebRoot() throws IllegalAccessException {
		String path = getWebClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF/"));
		} else {
			throw new IllegalAccessException("·����ȡ����");
		}
		return path;
	}

}
