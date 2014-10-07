package net.linybin7.core.frame.individuation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.linybin7.core.frame.bo.Individuation;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class IndividuationDaoImpl extends HibernateDaoSupport implements IndividuationDao {

	@Override
	public void save(Individuation id) {
		this.getHibernateTemplate().save(id);
	}

	@Override
	public void delete(String userCode, String setCode) {
		Individuation id = get(userCode, setCode);
		this.getHibernateTemplate().delete(id);
	}

	@Override
	public void update(Individuation id) {
		this.getHibernateTemplate().update(id);
	}

	@Override
	public Individuation get(String userCode,String setCode) {
		List list = getHibernateTemplate().find(
				"from Individuation obj where obj.userCode=? and obj.setCode=? ", new Object[]{userCode, setCode});
		if (list != null && list.size() > 0) {
			Individuation individuation = (Individuation) list.get(0);
			return individuation;
		} else {
			return null;
		}
	}

	@Override
	public Map<String,String> setDefault(String usercode, String kind) {
		Map<String,String> map=new HashMap<String, String>();
		List list = getHibernateTemplate().find(
				"from Individuation obj where obj.userCode=? and obj.kind=? ", new Object[]{usercode,kind});
		if (list != null && list.size() > 0) {
			for(Object obj:list){
				Individuation id = (Individuation) obj;
				id.setSetting(id.getDefaultValue());
				update(id);
				map.put(id.getSetCode(), id.getDefaultValue());
			}
		}
		return map;
	}

	@Override
	public List<Individuation> getAll(String userCode) {
		return getHibernateTemplate().find("from Individuation obj where obj.userCode=? ",userCode);
	}

}
