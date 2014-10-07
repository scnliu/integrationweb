package net.linybin7.common.jdbc;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.SimpleJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public interface JdbcDAO extends SimpleJdbcOperations  {
  public int queryForInt(String sql);
  public List query(String sql,Map params);
  public Object queryObject(Class entityClass, Serializable id);
  public SimpleJdbcTemplate	getSimpleJdbc();

}
