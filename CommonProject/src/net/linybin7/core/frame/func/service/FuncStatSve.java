package net.linybin7.core.frame.func.service;

import java.util.List;

import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.frame.bo.FuncStat;


public interface FuncStatSve {

	public void add(FuncStat bo);

	public FuncStat get(String sysId, String funcCode);

	public void update(FuncStat bo);

	public void insertOrUpdate(FuncStat bo);

	public void update(List<FuncStat> list);

	public List stat();

	public List statModule();
	
	public int statCount();
}
