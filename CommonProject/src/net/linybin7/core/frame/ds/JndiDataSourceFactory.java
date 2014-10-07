package net.linybin7.core.frame.ds;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.linybin7.common.util.StringHelper;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;


public class JndiDataSourceFactory implements FactoryBean, InitializingBean {

	private DataSource target;

	private String jndiName;

	public JndiDataSourceFactory() {

	}

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	// public Connection getConnection() throws SQLException {
	// if(target == null){
	// throw new SQLException("无法创建数据源");
	// }
	// return target.getConnection();
	// }
	//
	// public Connection getConnection(String username, String password)
	// throws SQLException {
	// return target.getConnection(username, password);
	// }
	//
	// public PrintWriter getLogWriter() throws SQLException {
	// return target.getLogWriter();
	// }
	//
	// public int getLoginTimeout() throws SQLException {
	// return target.getLoginTimeout();
	// }
	//
	// public void setLogWriter(PrintWriter out) throws SQLException {
	// target.setLogWriter(out);
	// }
	//
	// public void setLoginTimeout(int seconds) throws SQLException {
	// target.setLoginTimeout(seconds);
	// }

	private DataSource lookup(String name) {
		try {
			Context context = new InitialContext();
			return (DataSource) context.lookup(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void afterPropertiesSet() throws Exception {
		if (StringHelper.isEmpty(jndiName)) {
			throw new Exception("jndiName 是必须设置的属性");
		}
		target = lookup(jndiName);
	}

	public Object getObject() throws Exception {
		return target;
	}

	public Class getObjectType() {
		return DataSource.class;
	}

	public boolean isSingleton() {
		return true;
	}

}
