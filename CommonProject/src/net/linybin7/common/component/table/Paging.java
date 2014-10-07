package net.linybin7.common.component.table;

/**
 * 分页
 * @author JackenCai
 *
 */
public class Paging {

	/**
	 * 当前页
	 * 
	 */
	private int currentPage = 1;

	/**
	 * 当前页属性
	 * 
	 */
	private String currentPageProp = "page.currentPage";

	/**
	 * 每页大小
	 */
	private int pageSize = 15;

	/**
	 * 总记录数
	 */
	private int count = 0;

	/**
	 * 表单action
	 */
	private String action = "";

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCurrentPageProp() {
		return currentPageProp;
	}

	public void setCurrentPageProp(String currentPageProp) {
		this.currentPageProp = currentPageProp;
	}

	/**
	 * 获得总页数
	 * 
	 * 
	 * @return
	 */
	public int getTotalPage() {
		return count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
	}

	/**
	 * 是否是首页
	 * 
	 * 
	 * @return
	 */
	public boolean isFirst() {
		return currentPage == 1||count==0;
	}

	/**
	 * 是否是最后一页
	 * 
	 * 
	 * @return
	 */
	public boolean isLast() {
		return currentPage >= getTotalPage()||count==0;
	}

	/**
	 * 下一页
	 * 
	 * 
	 * @return
	 */
	public int getNext() {
		if (  getTotalPage() > 0 && currentPage + 1 > getTotalPage()) {
			return getTotalPage();
		}
		return currentPage + 1;
	}

	public int turnNext(){
		return currentPage + 1;
	}
	
	/**
	 * 上一页
	 * 
	 * 
	 * @return
	 */
	public int getPre() {
		if (currentPage - 1 < 1) {
			return 1;
		}
		return currentPage - 1;
	}
	
}
