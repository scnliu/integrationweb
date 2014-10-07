package net.linybin7.common.component.table;

public class Pagination {
	private int count = 0;

	private int currentPage = 1;

	private int pageSize = 10;

	public int getTotalPage() {
		if (this.count > 0) {
			return this.count % this.pageSize == 0 ? this.count / this.pageSize
					: this.count / this.pageSize + 1;
		}
		return 1;
	}

//	public int getFirstPage() {
//		return 1;
//	}
//
//	public int getBackPage() {
//		return this.currentPage > 1 ? this.currentPage - 1 : 1;
//	}
//
//	public int getNextPage() {
//		return this.currentPage + 1 < this.getTotalPage() ? this.currentPage + 1
//				: this.getTotalPage();
//	}
//
//	public int getLastPage() {
//		return this.getTotalPage();
//	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCurrentPage() {
		return currentPage > this.getTotalPage() ? this.getTotalPage()
				: this.currentPage;
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
}
