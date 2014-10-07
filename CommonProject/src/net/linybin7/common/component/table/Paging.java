package net.linybin7.common.component.table;

/**
 * ��ҳ
 * @author JackenCai
 *
 */
public class Paging {

	/**
	 * ��ǰҳ
	 * 
	 */
	private int currentPage = 1;

	/**
	 * ��ǰҳ����
	 * 
	 */
	private String currentPageProp = "page.currentPage";

	/**
	 * ÿҳ��С
	 */
	private int pageSize = 15;

	/**
	 * �ܼ�¼��
	 */
	private int count = 0;

	/**
	 * ��action
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
	 * �����ҳ��
	 * 
	 * 
	 * @return
	 */
	public int getTotalPage() {
		return count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
	}

	/**
	 * �Ƿ�����ҳ
	 * 
	 * 
	 * @return
	 */
	public boolean isFirst() {
		return currentPage == 1||count==0;
	}

	/**
	 * �Ƿ������һҳ
	 * 
	 * 
	 * @return
	 */
	public boolean isLast() {
		return currentPage >= getTotalPage()||count==0;
	}

	/**
	 * ��һҳ
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
	 * ��һҳ
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
