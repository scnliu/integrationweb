package net.linybin7.is.weibo.cmd;


public class PageCmd {
	private int pageMethod;
	private int currentPage=1;
	private String queryName;
	private String startTime;
	private String endTime;
	
	private boolean isPage=true;
	
	
	public int getPageMethod() {
		return pageMethod;
	}
	public void setPageMethod(int pageMethod) {
		this.pageMethod = pageMethod;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public boolean isPage() {
		return isPage;
	}
	public void setPage(boolean isPage) {
		this.isPage = isPage;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	
}