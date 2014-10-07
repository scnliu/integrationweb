package net.linybin7.common.page;

import java.io.Serializable;
import java.util.List;

import net.linybin7.common.component.table.TableModel;
import net.linybin7.common.hbmdao.HbmService;
import net.linybin7.common.tag.Grid;
import net.linybin7.core.frame.bo.User;


public interface PageService extends HbmService{
  public List page(Grid grid);
  public boolean exist(Serializable id);
  public List deleteItems(Grid grid);
  /**
   * 使用TableModel分页
   * @author HuangHuaSheng
   * 2010-11-4 上午10:46:23
   * @param model
   * @param user
   * @param cPro
   * @param sExpr
   * @return
   */
  public List queryData(TableModel model, User user, Object condition);
		
}
