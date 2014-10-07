package net.linybin7.core.frame.func.service;

import java.util.List;

import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.frame.bo.FuncStat;
import net.linybin7.core.frame.func.dao.FuncStatDao;


public class FuncStatSveImpl implements FuncStatSve{
	
	private FuncStatDao statDao;

	@Override
	public void add(FuncStat bo) {
		// TODO Auto-generated method stub
		statDao.add(bo);
	}

	@Override
	public FuncStat get(String sysId, String funcCode) {
		// TODO Auto-generated method stub
		return statDao.get(sysId, funcCode);
	}

	@Override
	public void update(FuncStat bo) {
		// TODO Auto-generated method stub
		statDao.update(bo);
	}

	@Override
	public void insertOrUpdate(FuncStat bo) {
		// TODO Auto-generated method stub
		statDao.insertOrUpdate(bo);
	}

	public void setStatDao(FuncStatDao statDao) {
		this.statDao = statDao;
	}

	public FuncStatDao getStatDao() {
		return statDao;
	}

	@Override
	public void update(List<FuncStat> list) {
		// TODO Auto-generated method stub
		statDao.update(list);
	}

	@Override
	public List stat() {
		// TODO Auto-generated method stub
		return statDao.stat();
	}

	@Override
	public List statModule() {
		// TODO Auto-generated method stub
		return statDao.statModule();
	}

	@Override
	public int statCount() {
		// TODO Auto-generated method stub
		return statDao.statCount();
	}

}
