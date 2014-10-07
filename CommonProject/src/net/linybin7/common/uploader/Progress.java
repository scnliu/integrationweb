package net.linybin7.common.uploader;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;


/**
 * 
 * Progress.java <br>
 *  <br>
 * Bensir <br>
 * 2008-7-8 ÉÏÎç11:37:25 <br>
 */

public class Progress {

	private String action;

	private String context;

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public JSONObject refresh(HttpServletRequest req) {
		ProgressReport report = ProgressUitl.getReport(req);
		if (report == null)
			report = new ProgressReport();

		JSONObject json = new JSONObject();

		json.put("StartTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date(report.getStartTime())));
		json.put("RemainTime", report.getRemainTime() + " Sec");
		json.put("SpanTime", report.getSpanTime() + " Sec");
		json.put("TotalSize", report.getTotalSize());
		json.put("DoneSize", report.getDoneSize());
		json.put("Percent", report.getPercent());
		json.put("File", report.getFile());
		json.put("Speed", report.getSpeed());
		json.put("Info", report.getInfo());

		return json;
	}

}
