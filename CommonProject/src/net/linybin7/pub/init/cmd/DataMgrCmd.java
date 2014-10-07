package net.linybin7.pub.init.cmd;

import java.io.Serializable;
import java.util.List;

import net.linybin7.common.component.select.Option;
import net.linybin7.common.component.select.OptionUtil;
import net.linybin7.common.component.table.TableModel;
import net.linybin7.core.util.Constants;
import net.linybin7.pub.data.bo.DataMgr;
import net.linybin7.pub.data.cmd.FileUploadCmd;


/**
 * 角色命令对象
 * 

 * 
 */
public class DataMgrCmd extends FileUploadCmd implements Serializable {
	
	private TableModel<DataMgr> tableModel = new TableModel<DataMgr>();

	private DataMgr tableMgr = new DataMgr();
	
	private String[] selectedIds;
	
	private String[] purviews;
	
	private String[][] type = null;
		
	private final String[][] stop = new String[][]{{"-1", ""},
			{String.valueOf(Constants.STOP_NO), "否"},
			{String.valueOf(Constants.STOP_YES), "是"}};

	public DataMgrCmd() {
		
	}

	
	public String[] getSelectedIds() {
		return selectedIds;
	}


	public void setSelectedIds(String[] selectedIds) {
		this.selectedIds = selectedIds;
	}


	public String[] getPurviews() {
		return purviews;
	}


	public void setPurviews(String[] purviews) {
		this.purviews = purviews;
	}


	public TableModel<DataMgr> getTableModel() {
		return tableModel;
	}


	public void setTableModel(TableModel<DataMgr> tableModel) {
		this.tableModel = tableModel;
	}


	public DataMgr getTableMgr() {
		return tableMgr;
	}


	public void setTableMgr(DataMgr tableMgr) {
		this.tableMgr = tableMgr;
	}


	public String[][] getType() {
		return type;
	}


	public void setType(String[][] type) {
		this.type = type;
	}


	/**
	 * 获得类型选项
	 * @param value
	 * @param hasBlank
	 * @return
	 */
	public List<Option> getTypeOption(){
		return OptionUtil.toOptions(type);
	}
	
	/**
	 * 获得是否停用选项
	 * @param value
	 * @param hasBlank
	 * @return
	 */
	public List<Option> getStopOption(){
		return OptionUtil.toOptions(stop);
	}
}
