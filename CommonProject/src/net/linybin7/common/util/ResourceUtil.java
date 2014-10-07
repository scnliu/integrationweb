package net.linybin7.common.util;

import java.io.File;
import java.net.URI;
import java.net.URL;



public final class ResourceUtil {

	private ResourceUtil(){
		
	}
	
	public static File classPathFile(String path){
		URL url = ResourceUtil.class.getClassLoader().getResource(path);
		try {
//			System.out.println(url);
			String realPath = url.toString().replaceAll(" ", "%20");
			
			URI uri =  new URI(realPath);
			return new File(uri.getSchemeSpecificPart());
		}
		catch (Exception ex) {
			// Fallback for URLs that are not valid URIs (should hardly ever happen).
			return new File(url.getFile());
		}
	}
}
