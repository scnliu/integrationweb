package net.linybin7.core.web.context;


import javax.servlet.ServletContext;


/**
 * Web иообнд
 * 
 * 
 */
public final class WebContext {
	public static String contextPath;
	public static ServletContext context;

	public void set(String key, Object obj) {
		context.setAttribute(key, obj);
	}

	public Object get(String key) {
		return context.getAttribute(key);
	}
}
