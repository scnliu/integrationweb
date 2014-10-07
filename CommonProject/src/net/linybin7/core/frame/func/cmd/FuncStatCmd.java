package net.linybin7.core.frame.func.cmd;

import java.io.Serializable;

import net.linybin7.common.component.table.TableModel;
import net.linybin7.common.tag.Grid;
import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.frame.bo.FuncStat;


public class FuncStatCmd extends Grid implements Serializable{

	private Func condition = new Func();
	
	private TableModel<FuncStatBean> tableModel = new TableModel<FuncStatBean>();

	public void setCondition(Func condition) {
		this.condition = condition;
	}

	public Func getCondition() {
		return condition;
	}

	public void setTableModel(TableModel<FuncStatBean> tableModel) {
		this.tableModel = tableModel;
	}

	public TableModel<FuncStatBean> getTableModel() {
		return tableModel;
	}
}
