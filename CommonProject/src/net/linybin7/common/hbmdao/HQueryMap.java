package net.linybin7.common.hbmdao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


/**
 * Restriction.java <br>
 *  <br>
 * Bensir <br>
 * 2008ÏÂÎç05:33:26 <br>
 */

public class HQueryMap implements HQuery {

	private List _list = new ArrayList();

	private Map criterionMap;

	private List orderList;

	private Class clazz;

	private String entityName;

	public HQueryMap() {
		super();
		criterionMap = new HashMap();
		orderList = new ArrayList();
	}

	public HQueryMap addCriterion(String filed, String restrict, Object value) {
		if (value != null)
			criterionMap.put(filed, new Object[] { restrict, value });
		return this;
	}

	public HQueryMap addAscOrder(String field) {
		this.orderList.add(Order.asc(field));
		return this;
	}

	public HQueryMap addDescOrder(String filed) {
		this.orderList.add(Order.desc(filed));
		return this;
	}

	public List getCriterionList() {
		List list = new ArrayList();
		for (Object o : _list) {
			list.add(o);
		}
		if (this.criterionMap != null && !criterionMap.isEmpty()) {
			Iterator it = criterionMap.keySet().iterator();
			while (it.hasNext()) {
				String field = (String) it.next();
				Object[] arr = (Object[]) criterionMap.get(field);
				String res = (String) arr[0];
				Object value = arr[1];
				Criterion c = this.buildCriterion(field, res, value);
				list.add(c);
			}
		}
		return list;
	}

	public List getOrderList() {
		return orderList;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public DetachedCriteria buildCriteria() {
		return buildCriteria(true);
	}

	public DetachedCriteria buildCriteria(boolean order) {
		DetachedCriteria c = this.clazz != null ? DetachedCriteria
				.forClass(this.clazz) : DetachedCriteria
				.forEntityName(entityName);
		List clist = this.getCriterionList();
		for (int i = 0; i < clist.size(); i++) {
			c.add((Criterion) clist.get(i));
		}
		if(order){
			List olist = this.getOrderList();
			for (int i = 0; i < olist.size(); i++) {
				c.addOrder((Order) olist.get(i));
			}
		}
		
		return c;
	}
	
	public Map getCriterionMap() {
		return criterionMap;
	}

	public void remove(String key) {
		criterionMap.remove(key);
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public HQuery addCriterion(Criterion c) {
		_list.add(c);
		return this;
	}

	public Criterion buildCriterion(String field, String restrict, Object value) {
		if (lt.equals(restrict))
			return (Restrictions.lt(field, value));
		else if (le.equals(restrict))
			return (Restrictions.le(field, value));
		else if (eq.equals(restrict))
			return (Restrictions.eq(field, value));
		else if (ge.equals(restrict))
			return (Restrictions.ge(field, value));
		else if (gt.equals(restrict))
			return (Restrictions.gt(field, value));
		else if (like.equals(restrict))
			return (Restrictions.like(field, "%" + value + "%"));
		else if (in.equals(restrict)) {
			if (value instanceof Object[])
				return (Restrictions.in(field, (Object[]) value));
			else {
				return (Restrictions.in(field, new Object[] { value }));
			}
		} else if (ne.equals(restrict)) {
			return (Restrictions.ne(field, value));
		}

		return null;
	}

	public List get_list() {
		return _list;
	}

	public Criterion orCriterion(Criterion c1, Criterion c2) {
		return Restrictions.or(c1, c2);
	}
}
