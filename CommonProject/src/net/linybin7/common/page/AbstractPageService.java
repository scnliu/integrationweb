package net.linybin7.common.page;

import java.io.Serializable;
import java.util.List;

import net.linybin7.common.component.table.TableModel;
import net.linybin7.common.hbmdao.HbmServiceImpl;
import net.linybin7.common.tag.Grid;
import net.linybin7.common.tag.Pager;
import net.linybin7.core.frame.bo.User;

import org.hibernate.criterion.SimpleExpression;


public abstract class AbstractPageService extends HbmServiceImpl implements PageService{
   PageDAO pageDao;
   public List page(Grid grid,List<SimpleExpression> sExpr){
	   if(grid==null)grid=new Grid();
	   Long rowsCount=pageDao.countRows(sExpr);
	   Pager pager=grid.getPager();  
	   pager.setTotalRow(rowsCount.intValue());
	   grid.setPager(pager);
	   return pageDao.queryPageRows(grid,sExpr, pager.getStartIndex(), pager.getPageSize());
   }
   @Override
public List deleteItems(Grid grid){
	   List ids=grid.getIdentityList();
	   pageDao.delByList(ids);
	   Pager pager=grid.getPager();
	   pager.first();
	   return page(grid,null);
   }
public PageDAO getPageDao() {
	return pageDao;
}
public void setPageDao(PageDAO pageDao) {
	this.pageDao = pageDao;
	this.dao=pageDao;
}
@Override
public boolean exist(Serializable id){
	if(this.dao.get(id)!=null)return true;
	return false;
}
public void match(List list,SimpleExpression se ,Object value){
	if(value!=null)list.add(se);
}

public List query(TableModel model,String sql,String where) {
	
//	List <SimpleExpression> sExpr=new ArrayList();
//	if(cPro!=null){
//	match(sExpr,Restrictions.like("projectName",cPro.getProjectName(),MatchMode.ANYWHERE),cPro.getProjectName());
//	match(sExpr,Restrictions.eq("status",cPro.getFStatus()),cPro.getFStatus());
//	match(sExpr,Restrictions.eq("operator",cPro.getFOperator()),cPro.getFOperator());
//	}
	model.getPage().setCount((pageDao.countRows(sql).intValue()));
	Pager p=new Pager();
	p.page(model);
	List list= pageDao.pageModel(sql,p);
	return list;
}
@Override
public List queryData(TableModel model, User user, Object condition) {
	return null;
}

}
