package net.linybin7.common.jdbc;

public class JdbcServiceImpl implements JdbcService {
   protected JdbcDAO dao;

public JdbcServiceImpl() {
	super();
}

public JdbcDAO getDao() {
	return dao;
}

public void setDao(JdbcDAO dao) {
	this.dao = dao;
}
}
