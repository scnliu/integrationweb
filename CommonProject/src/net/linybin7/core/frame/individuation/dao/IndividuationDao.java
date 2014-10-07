package net.linybin7.core.frame.individuation.dao;

import java.util.List;
import java.util.Map;

import net.linybin7.core.frame.bo.Individuation;


public interface IndividuationDao {
	
	public void save(Individuation id);
	
	public void delete(String userCode, String setCode);
	
	public void update(Individuation id);
	
	public Individuation get(String userCode, String setCode);

	public Map<String,String> setDefault(String usercode, String kind);

	public List<Individuation> getAll(String userCode);

}
