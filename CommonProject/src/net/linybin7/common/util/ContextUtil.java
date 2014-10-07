package net.linybin7.common.util;

import java.io.File;
import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.springframework.web.context.WebApplicationContext;


/**
 * 

 * 
 */
public class ContextUtil {
	private ContextUtil() {

	}

	public static File getContextPath() {
		try {
			File path = new File(ContextUtil.class.getResource("/").toURI());
			File temPath = new File(path, "classes");
			if (!temPath.exists()) {
				path = path.getParentFile().getParentFile();
			}
			return path;
		} catch (Exception e) {
			e.printStackTrace();
			return new File(".");
		}
	}

	/**
	 * 数据库是否已经配置
	 * 
	 * @param context
	 * @return true:已配置;false:未配置
	 */
	public static boolean dbHadConfiged(ServletContext context) {
		WebApplicationContext wac = (WebApplicationContext) context
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		DataSource ds = (DataSource) wac.getBean("dataSource");
		Connection conn = null;
		try {
			conn = ds.getConnection();
			return true;
		} catch (Exception e) {
			System.out.println("无法与数据库建立连接:" + e.getMessage());
			return false;
		} finally {
			close(conn);
		}
	}

	// /**
	// * 更新数据源
	// * @param context
	// * @param cfg
	// */
	// public static void updateDataSource(ServletContext context, DatabaseCfg
	// cfg){
	// WebApplicationContext wac = (WebApplicationContext)
	// context.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	// DataSource ds = (DataSource)wac.getBean("dataSource");
	// try {
	// if(ds instanceof BasicDataSource) {
	// BasicDataSource ds1 = (BasicDataSource)ds;
	// ds1.setDriverClassName(cfg.getDriverClassName());
	// ds1.setUrl(cfg.getUrl());
	// ds1.setUsername(cfg.getUsername());
	// ds1.setPassword(cfg.getPassword());
	// ds1.setMaxActive(cfg.getMaxActive());
	// ds1.setMaxWait(cfg.getMaxWait());
	// LocalSessionFactoryBean lsf =
	// (LocalSessionFactoryBean)wac.getBean("&sessionFactory");
	// lsf.updateDatabaseSchema();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	private static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {

			}
		}
	}
}
