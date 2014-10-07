package net.linybin7.core.frame.individuation.service;

import java.util.List;
import java.util.Map;

import net.linybin7.core.frame.bo.Individuation;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.individuation.dao.IndividuationDao;


public class IndividuationServiceImpl implements IndividuationService{
	
	private IndividuationDao dao;

	public IndividuationDao getDao() {
		return dao;
	}

	public void setDao(IndividuationDao dao) {
		this.dao = dao;
	}

	@Override
	public Individuation getIndividuation(String userCode, String setCode) {
		return dao.get(userCode, setCode);
	}
	
	@Override
	public void editTheme(User user, String setCode,
			String setting) {
		Individuation id=dao.get(user.getUserCode(), setCode);
		if(id!=null){
			id.setSetting(setting);
			dao.update(id);
		}
		else{
			id=new Individuation();
			id.setUserCode(user.getUserCode());
			id.setUserName(user.getUserName());
			id.setKind("TOPIC");
			id.setSetCode("topic");
			id.setSetting(setting);
			id.setSetName("Ö÷Ìâ");
			id.setDefaultValue(setting);
			id.setSysId("V7");
			dao.save(id);
		}
	}

	@Override
	public Map<String, String> setDefault(String usercode, String kind) {
		return dao.setDefault(usercode, kind);
	}

	@Override
	public void update(User u, String params, String kind) {
		String[] keys=params.split(";");
		for(String key:keys){
			String[] values=key.split(",");
			if(kind.equals(values[0])){
				Individuation id=getIndividuation(u.getUserCode(), values[1]);
				if(id!=null){
					id.setSetting(values.length==3?values[2]:"");
					dao.update(id);
				}
				else if(values.length==3&&values[2]!=null&&values[2].length()!=0){
					id=new Individuation();
					id.setUserCode(u.getUserCode());
					id.setUserName(u.getUserName());
					id.setDefaultValue(values[2]);
					id.setKind(values[0]);
					id.setSetCode(values[1]);
					id.setSetting(values[2]);
					id.setSysId("V7");
					dao.save(id);
				}
			}
		}
	}
	
	@Override
	public void resetAll(User u) {
		List<Individuation> ids = dao.getAll(u.getUserCode());
		for(Individuation id:ids){
			id.setSetting(id.getDefaultValue());
			dao.update(id);
		}
	}

	@Override
	public List<Individuation> getAll(String userCode) {
		return dao.getAll(userCode);
	}
	
}
