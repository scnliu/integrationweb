package net.linybin7.common.hbmdao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;


/**
 * 
 * HQuery.java <br>
 *  <br>
 * Bensir <br>
 * 2009-7-7 ÏÂÎç03:29:53 <br>
 */

public interface HQuery {
	public static final String lt = "<";

	public static final String le = "<=";

	public static final String eq = "=";

	public static final String ge = ">=";

	public static final String gt = ">";

	public static final String like = "like";

	public static final String in = "in";

	public static final String ne = "<>";

	public HQuery addCriterion(String filed, String restrict, Object value);

	public HQuery addCriterion(Criterion c);

	public Criterion orCriterion(Criterion c1, Criterion c2);

	public Criterion buildCriterion(String filed, String restrict, Object value);

	public HQueryMap addAscOrder(String field);

	public HQueryMap addDescOrder(String filed);

	public List getCriterionList();

	public List getOrderList();

	public Class getClazz();

	public void setClazz(Class clazz);

	public DetachedCriteria buildCriteria();
	
	public DetachedCriteria buildCriteria(boolean order);

	public Map getCriterionMap();

	public void remove(String key);

	public String getEntityName();

	public void setEntityName(String entityName);
}
