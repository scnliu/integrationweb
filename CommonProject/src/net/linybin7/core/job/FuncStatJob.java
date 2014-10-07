package net.linybin7.core.job;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.frame.bo.FuncStat;
import net.linybin7.core.frame.func.service.FuncStatSve;
import net.linybin7.core.frame.func.service.FuncSve;
import net.linybin7.core.util.Constants;


public class FuncStatJob {
	
	private static final String SYS = "NPI";
	
	private FuncStatSve statService;
	
	private FuncSve funcService;

	public void stat(){
		Map map = Constants.funcMap;
		Set keys = map.keySet();
		Iterator<String> iterator = keys.iterator();
		List<FuncStat> list = new ArrayList<FuncStat>();
		while(iterator.hasNext()){
			String funcCode = iterator.next();
			FuncStat funcStat = statService.get(SYS, funcCode);
			if(funcStat != null){
				funcStat.setCnt(funcStat.getCnt()+(Integer)map.get(funcCode));
			}else{
				funcStat = new FuncStat();
				funcStat.setCnt((Integer)map.get(funcCode));
				funcStat.setFuncCode(funcCode);
				Func func = funcService.get(funcCode);
				funcStat.setParentCode(func.getParentCode());
				funcStat.setSysId(SYS);
			}
			Constants.funcMap.put(funcCode, 0);
			list.add(funcStat);
		}
		statService.update(list);
	}

	public void setStatService(FuncStatSve statService) {
		this.statService = statService;
	}

	public FuncStatSve getStatService() {
		return statService;
	}

	public void setFuncService(FuncSve funcService) {
		this.funcService = funcService;
	}

	public FuncSve getFuncService() {
		return funcService;
	}
}
