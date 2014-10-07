package net.linybin7.core.frame.individuation.service;

import java.util.List;
import java.util.Map;

import net.linybin7.core.frame.bo.Individuation;
import net.linybin7.core.frame.bo.User;


public interface IndividuationService {
	
	public Individuation getIndividuation(String userCode, String setCode);

	public void editTheme(User user, String setCode, String setting);

	public Map<String, String> setDefault(String userCode, String kind);

	public void update(User u, String params, String kind);

	public void resetAll(User u);
	
	public List<Individuation> getAll(String userCode);

}
