package net.linybin7.common.page;

import java.util.List;

import net.linybin7.common.hbmdao.HbmDAO;
import net.linybin7.common.tag.Grid;
import net.linybin7.common.tag.Pager;

import org.hibernate.criterion.SimpleExpression;


public interface PageDAO extends HbmDAO {
  public Long countRows(List<SimpleExpression> sExpr);
  public List queryPageRows(Grid grid,List<SimpleExpression> sExpr,int startIndex,int pageSize);
  public void delByList(final List<String> ids);
	public List pageModel(final String sql,Pager p);
	public Long countRows(final String sql);
}
