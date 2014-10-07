package weibo4j.examples.oauth2;

import org.apache.log4j.Logger;

public class Log {
	
	static Logger log = Logger.getLogger("net.linybin7.log.weibo");
	
    public static void logDebug(String message) {
			log.debug(message);
	}

	public static void logInfo(String message) {
			log.info(message);
	}


}
