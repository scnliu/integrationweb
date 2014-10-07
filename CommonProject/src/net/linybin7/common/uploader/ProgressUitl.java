package net.linybin7.common.uploader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ProgressUitl {
	private HttpSession session;

	public static final String RID = "ridabcde";

	private String sid;

	private ProgressReport report;

	public ProgressUitl(HttpServletRequest req, ProgressReport report) {
		this.report = report;
		this.report.setTotalSize(req.getContentLength());
		this.report.setStartTime(System.currentTimeMillis());

		this.session = req.getSession();
		this.initSession();
	}

	private void initSession() {
		this.sid = RID + System.currentTimeMillis();
		session.setAttribute(RID, sid);
		session.setAttribute(sid, null);
		this.refresh(0);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	public void dispose() {
		System.out
				.println("::::::::::::::::::::::..........................................");
		String rid = (String) session.getAttribute(RID);
		if (rid != null) {
			session.setAttribute(rid, null);
			session.setAttribute(RID, null);
		}
	}

	public void refresh(long size) {
		long done = this.report.getDoneSize() + size;
		this.report.setDoneSize(done);
		this.session.setAttribute(this.sid, this.report);
	}

	public ProgressReport getReport() {
		if (this.session != null) {
			String rid = (String) this.session.getAttribute(RID);
			if (rid != null) {
				Object obj = session.getAttribute(rid);
				if (obj instanceof ProgressReport) {
					return (ProgressReport) obj;
				}
			}
		}
		return null;
	}

	public static ProgressReport getReport(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			String rid = (String) session.getAttribute(RID);
			if (rid != null) {
				Object obj = session.getAttribute(rid);
				if (obj instanceof ProgressReport) {
					return (ProgressReport) obj;
				}
			}
		}
		return null;
	}

	public void setReport(ProgressReport report) {
		this.report = report;
	}
}
