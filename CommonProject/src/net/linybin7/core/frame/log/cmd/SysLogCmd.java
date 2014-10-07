/**
 * 
 */
package net.linybin7.core.frame.log.cmd;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.linybin7.common.component.table.TableModel;
import net.linybin7.core.util.SysLog;


/**
 * @author Huangyouwen 2010-11-24 ÏÂÎç03:15:51
 */
public class SysLogCmd {

	private SysLog sysLog = new SysLog();

	private TableModel<SysLog> tableModel = new TableModel<SysLog>();

	public static final String MSG = "MSG";

	private Date from;
	private Date to;

	public Date getFromDate() {
		return from;
	}

	public Date getToDate() {
		return to;
	}

	public static SimpleDateFormat sdfForm = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

	public static SimpleDateFormat sdfTo = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public String getFrom() {
		try {
			if (from == null || "".equals(from)) {
				from = new Date();
				Calendar cld = Calendar.getInstance();
				cld.setTime(this.from);
				cld.add(Calendar.MONTH, -1);
				from = cld.getTime();
			}
			return sdf.format(from);
		} catch (Exception e) {
		}
		return "";
	}

	public void setFrom(String from) {
		try {
			this.from = sdfForm.parse(from + " 00:00:00");
		} catch (Exception e) {
			this.from = null;
		}
	}

	public String getTo() {
		try {
			if (to == null || "".equals(to)) {
				to = new Date();
			}
			return sdf.format(to);
		} catch (Exception e) {
		}
		return "";
	}

	public void setTo(String to) {
		try {
			this.to = sdfTo.parse(to + " 23:59:59");
		} catch (Exception e) {
			this.to = null;
		}
	}

	public TableModel<SysLog> getTableModel() {
		return tableModel;
	}

	public void setTableModel(TableModel<SysLog> tableModel) {
		this.tableModel = tableModel;
	}

	public SysLog getSysLog() {
		return sysLog;
	}

	public void setSysLog(SysLog sysLog) {
		this.sysLog = sysLog;
	}

}
