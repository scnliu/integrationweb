package net.linybin7.common.hbmdao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;


/**
 * 
 * HQuery.java <br>
 *  <br>
 * Bensir <br>
 * 2009-4-24 ÏÂÎç02:40:54 <br>
 */

public class HQueryList extends HQueryMap {
	private List criterionList;

	public HQueryList() {
		super();
		criterionList = new ArrayList();
	}

	@Override
	public HQueryMap addCriterion(String filed, String restrict, Object value) {
		List list = new ArrayList();
		list.add(filed);
		list.add(restrict);
		list.add(value);
		criterionList.add(list);
		return this;
	}

	@Override
	public List getCriterionList() {
		List list = new ArrayList();
		for(Object o: this.get_list()){
			list.add(o);
		}
		if (this.criterionList != null && !criterionList.isEmpty()) {
			for (int i = 0; i < criterionList.size(); i++) {
				List l = (List) criterionList.get(i);
				String field = (String) l.get(0);			
				String res = (String) l.get(1);
				Object value = l.get(2);
				Criterion c = this.buildCriterion(field, res, value);
				list.add(c);
			}
		}
		return list;

	}
}
