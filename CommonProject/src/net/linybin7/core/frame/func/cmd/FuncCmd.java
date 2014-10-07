package net.linybin7.core.frame.func.cmd;

import java.io.Serializable;
import java.util.List;

import net.linybin7.common.component.select.Option;
import net.linybin7.common.component.select.OptionUtil;
import net.linybin7.common.component.table.TableModel;
import net.linybin7.common.tag.Grid;
import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.util.Constants;


/**
 * �����������
 * 
 * 
 * 
 */
public class FuncCmd extends Grid implements Serializable {

	private TableModel<Func> tableModel = new TableModel<Func>();

	private Func func = new Func();

	private String[] selectedIds;

	private String msgImgLsit;

	private final String[][] type = new String[][] { { "-1", "" },
			{ String.valueOf(Constants.FUNC_TYPE_MENU), "�˵�" },
			{ String.valueOf(Constants.FUNC_TYPE_BUTTON), "��ť" },
			{ String.valueOf(Constants.FUNC_TYPE_LINK), "����" } };

	private final String[][] stop = new String[][] { { "-1", "" },
			{ String.valueOf(Constants.STOP_NO), "��" }, { String.valueOf(Constants.STOP_YES), "��" } };

	public FuncCmd() {

	}

	public String[] getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String[] selectedIds) {
		this.selectedIds = selectedIds;
	}

	public TableModel<Func> getTableModel() {
		return tableModel;
	}

	public void setTableModel(TableModel<Func> tableModel) {
		this.tableModel = tableModel;
	}

	public Func getFunc() {
		return func;
	}

	public void setFunc(Func func) {
		this.func = func;
	}

	/**
	 * �������ѡ��
	 * 
	 * @param value
	 * @param hasBlank
	 * @return
	 */
	public List<Option> getTypeOption() {
		return OptionUtil.toOptions(type);
	}

	/**
	 * ����Ƿ�ͣ��ѡ��
	 * 
	 * @param value
	 * @param hasBlank
	 * @return
	 */
	public List<Option> getStopOption() {
		return OptionUtil.toOptions(stop);
	}

	public void setMsgImgLsit(String msgImgLsit) {
		this.msgImgLsit = msgImgLsit;
	}

	public String getMsgImgLsit() {
		return msgImgLsit;
	}
}
