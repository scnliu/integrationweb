package net.linybin7.common.jdbc;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.orm.hibernate3.HibernateTemplate;


public class JdbcDAOImpl extends SimpleJdbcDaoSupport implements JdbcDAO {
	public static final Logger logger = Logger.getLogger(JdbcDAOImpl.class);

	protected HibernateTemplate hbmTemp;

	public JdbcDAOImpl() {
		super();

	}

	@Override
	public int queryForInt(String sql) {
		// logger.debug(sql);
		return this.getJdbcTemplate().queryForInt(sql);
	}

	@Override
	public List query(String sql, Map params) {
		// logger.debug(sql);
		return this.getSimpleJdbcTemplate().queryForList(sql, params);
	}

	@Override
	public Object queryObject(Class entityClass, Serializable id) {
		return this.getHbmTemp().get(entityClass, id);
	}

	public SqlRowSet queryRS(String sql) {
		// logger.debug(sql);
		SqlRowSet rs = this.getJdbcTemplate().queryForRowSet(sql);
		return rs;
	}

	public void setSessionFactory(SessionFactory fac) {
		hbmTemp = new HibernateTemplate(fac);
	}

	public HibernateTemplate getHbmTemp() {
		return hbmTemp;
	}

	@Override
	public SimpleJdbcTemplate getSimpleJdbc() {
		return this.getSimpleJdbcTemplate();
	}

	@Override
	public int[] batchUpdate(String sql, List<Object[]> batchArgs, int[] argTypes) {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().batchUpdate(sql, batchArgs, argTypes);
	}

	@Override
	public int[] batchUpdate(String sql, List<Object[]> batchArgs) {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().batchUpdate(sql, batchArgs);
	}

	@Override
	public int[] batchUpdate(String sql, Map[] arg1) {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().batchUpdate(sql, arg1);
	}

	@Override
	public int[] batchUpdate(String sql, SqlParameterSource[] batchArgs) {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().batchUpdate(sql, batchArgs);
	}

	@Override
	public <T> List<T> query(String sql, ParameterizedRowMapper<T> rm, Map args)
			throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().query(sql, rm, args);
	}

	@Override
	public <T> List<T> query(String sql, ParameterizedRowMapper<T> rm, Object... args)
			throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().query(sql, rm, args);
	}

	@Override
	public <T> List<T> query(String sql, ParameterizedRowMapper<T> rm, SqlParameterSource args)
			throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().query(sql, rm, args);
	}

	@Override
	public int queryForInt(String sql, Map args) throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForInt(sql, args);
	}

	@Override
	public int queryForInt(String sql, Object... args) throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForInt(sql, args);
	}

	@Override
	public int queryForInt(String sql, SqlParameterSource args) throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForInt(sql, args);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql, Map args) throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForList(sql, args);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql, Object... args)
			throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForList(sql, args);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql, SqlParameterSource args)
			throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForList(sql, args);
	}

	@Override
	public long queryForLong(String sql, Map args) throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForLong(sql, args);
	}

	@Override
	public long queryForLong(String sql, Object... args) throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForLong(sql, args);
	}

	@Override
	public long queryForLong(String sql, SqlParameterSource args) throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForLong(sql, args);
	}

	@Override
	public Map<String, Object> queryForMap(String sql, Map args) throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForMap(sql, args);
	}

	@Override
	public Map<String, Object> queryForMap(String sql, Object... args) throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForMap(sql, args);
	}

	@Override
	public Map<String, Object> queryForMap(String sql, SqlParameterSource args)
			throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForMap(sql, args);
	}

	@Override
	public <T> T queryForObject(String sql, Class<T> requiredType, Map args)
			throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForObject(sql, requiredType, args);
	}

	@Override
	public <T> T queryForObject(String sql, Class<T> requiredType, Object... args)
			throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForObject(sql, requiredType, args);
	}

	@Override
	public <T> T queryForObject(String sql, Class<T> requiredType, SqlParameterSource args)
			throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForObject(sql, requiredType, args);
	}

	@Override
	public <T> T queryForObject(String sql, ParameterizedRowMapper<T> rm, Map args)
			throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForObject(sql, rm, args);
	}

	@Override
	public <T> T queryForObject(String sql, ParameterizedRowMapper<T> rm, Object... args)
			throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForObject(sql, rm, args);
	}

	@Override
	public <T> T queryForObject(String sql, ParameterizedRowMapper<T> rm, SqlParameterSource args)
			throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().queryForObject(sql, rm, args);
	}

	@Override
	public int update(String sql, Map args) throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().update(sql, args);
	}

	@Override
	public int update(String sql, Object... args) throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().update(sql, args);
	}

	@Override
	public int update(String sql, SqlParameterSource args) throws DataAccessException {
		// logger.debug(sql);
		return getSimpleJdbcTemplate().update(sql, args);
	}

	@Override
	public JdbcOperations getJdbcOperations() {
		return getSimpleJdbcTemplate().getJdbcOperations();
	}

	@Override
	public NamedParameterJdbcOperations getNamedParameterJdbcOperations() {
		return getSimpleJdbcTemplate().getNamedParameterJdbcOperations();
	}

}
