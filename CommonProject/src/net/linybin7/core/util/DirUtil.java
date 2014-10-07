package net.linybin7.core.util;

import java.io.File;

import net.linybin7.common.util.StringHelper;
import net.linybin7.core.web.context.WebContext;


/**
 * Ä¿Â¼¹¤¾ß
 * 
 * 
 */
public final class DirUtil {

	private DirUtil() {

	}

	public static File getContextPath(String path) {
		return new File(WebContext.contextPath, path);
	}

	public String getContext() {
		String context = WebContext.contextPath;
		if (!StringHelper.isEmpty(context)) {
			return context;
		} else if (StringHelper.isEmpty(System.getProperty("contextPath"))) {
			return System.getProperty("contextPath");
		} else {
			return "./";
		}
	}

}
