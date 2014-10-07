package net.linybin7.common.tag;

import net.linybin7.common.component.table.TableModel;

public class Pager {
  private int totalRow;
  private int pageSize=15;
  private int startIndex;
  private int endIndex;
  private int currentpage=1;
  private int totalPage=0;
  private int pageMethod=0;//1:first,2:previous,3:next,4:last
  private boolean hasNext=true;
  private boolean hasPrevious=true;

public int getPageMethod() {
	return pageMethod;
}
public void setPageMethod(int pageMethod) {
	this.pageMethod = pageMethod;
}
public Pager() {
		super();
	}
public void page(TableModel model){
	setTotalRow(model.getPage().getCount());
	setPageSize(model.getPage().getPageSize());
	setPageMethod(0);
	setCurrentpage(model.getPage().getCurrentPage());
	page();
}
public void page(){
	if(pageMethod!=0){
		switch(pageMethod){
		case 1:first(); break;
		case 2:previous();break;
		case 3:next();break;
		case 4:last();break;
		default:first();
		}
	}
	else{
		calTotalPage();
		if(currentpage>totalPage)currentpage=totalPage;
		startIndex=(currentpage-1)*pageSize;
		if(startIndex<0)startIndex=0;
		endIndex=startIndex+pageSize;
	}
	
}
public void previous(){
	calTotalPage();
	currentpage=currentpage-1;
	if(currentpage==0){
		currentpage=1;
	}
	startIndex=(currentpage-1)*pageSize;
	endIndex=startIndex+pageSize;
}
public void next(){
	calTotalPage();
	currentpage++;
	if(currentpage>totalPage){
		currentpage=totalPage;
	}
	startIndex=(currentpage-1)*pageSize;
	if(startIndex<0)startIndex=0;
	endIndex=startIndex+pageSize;
}
public void first(){
	calTotalPage();
	currentpage=1;
	startIndex=0;
	endIndex=pageSize;
	hasPrevious=false;
}
public void last(){
	calTotalPage();
	currentpage=totalPage;
	startIndex=(currentpage-1)*pageSize;
	if(startIndex<0)startIndex=0;
	endIndex=startIndex+pageSize;
	hasNext=false;
}
public void calTotalPage(){
	totalPage=totalRow/pageSize;
	if(totalRow%pageSize>0)totalPage++;
}

public Pager(int totalRow, int pageSize) {
	super();
	this.totalRow = totalRow;
	this.pageSize = pageSize;
}

public int getTotalRow() {
	return totalRow;
}
public void setTotalRow(int totalRow) {
	this.totalRow = totalRow;
	page();
}
public int getPageSize() {
	return pageSize;
}
public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
}
public int getStartIndex() {
	return startIndex;
}
public void setStartIndex(int startIndex) {
	this.startIndex = startIndex;
}
public int getEndIndex() {
	return endIndex;
}
public void setEndIndex(int endIndex) {
	this.endIndex = endIndex;
}
public int getCurrentpage() {
	return currentpage;
}
public void setCurrentpage(int currentpage) {
	this.currentpage = currentpage;
}
public int getTotalPage() {
	return totalPage;
}
public void setTotalPage(int totalPage) {
	this.totalPage = totalPage;
}
public boolean hasNext() {
	if(totalPage<2)return false;
	if(currentpage==totalPage)return false;
	return hasNext;
}
public void setHasNext(boolean hasNext) {
	this.hasNext = hasNext;
}
public boolean hasPrevious() {
	if(totalPage<2)return false;
	if(currentpage==1)return false;
	return hasPrevious;
}
public void setHasPrevious(boolean hasPrevious) {
	this.hasPrevious = hasPrevious;
}
  
}
