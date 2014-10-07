package net.linybin7.core.frame.log;

import java.util.Iterator;

import net.linybin7.core.frame.bo.LogContent;
import net.linybin7.core.frame.log.dao.SysLogDao;
import net.linybin7.core.web.util.SpringBeanFactory;

import org.apache.log4j.jdbc.JDBCAppender;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

public class JDBCSysAppender extends JDBCAppender {

	@Override
	public void flushBuffer() {
		removes.ensureCapacity(buffer.size());
		for (Iterator i = buffer.iterator(); i.hasNext();) {
			try {
				LoggingEvent event = (LoggingEvent) i.next();
				if (event.getMessage() instanceof LogContent) {
					LogContent log = (LogContent) event.getMessage();
					((SysLogDao) SpringBeanFactory.getBean("sysLogDao")).save(log);
				}
				removes.add(event);
			} catch (Exception e) {
				errorHandler.error("Failed to excute sql", e, ErrorCode.FLUSH_FAILURE);
			}
		}
		buffer.removeAll(removes);
		removes.clear();
	}
}