package net.linybin7.common.tag;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.linybin7.common.page.PageService;

import ognl.Ognl;
import ognl.OgnlException;

public class Grid extends Tag{
 private Pager pager=new Pager();
 private String pageAction="";
 private List list=new ArrayList();
 private List<String> columnFields=new ArrayList<String>();
 private int pageMethod=1;
 private int currentPage=1;
 private String height="300px";
 private boolean delete=false;
 private List<String> identityList=new ArrayList<String>();
 private final List<Column> columns=new ArrayList<Column>();
 private String identityName="";
 private boolean indirectSelection;
 private boolean nestedSorting;
 private int pageSize=15;
 private boolean dnd;
 private String sort;
 private String sortname;
 private String sortorder;
 private String searchFormId;
 private boolean containLink;
 private boolean gSearchHandle;
 private boolean queryHandle;
 private String linkUrl;
 private StringBuffer query=new StringBuffer("{ ");
 private final StringBuffer unSortFields=new StringBuffer("[");
 private final Map parseNode=new HashMap();
 public Grid() {
	super();
}
 public Grid create(){
	 return new Grid();
 }
 public String getHeight() {
	return height;
}
public void setHeight(String height) {
	this.height = height;
}
public void page(PageService sv,HttpServletResponse rsp){
	List list;
	if(delete){
		list=sv.deleteItems(this);
	}
	else
	list=sv.page(this);
	this.setList(list);
	this.write(rsp);
}
public void delete()
{
	
}
public boolean isAsc(){
	if(sortorder==null)return false;
	if(sortorder.equals("asc"))return true;
	return false;
}
public boolean isDesc(){
	if(sortorder==null)return false;
	if(sortorder.equals("desc"))return true;
	return false;
}
public String getSortField(){
	return sortname;
	
}
public void parseFiled() throws OgnlException{
	for(int j=0;j<columnFields.size();j++){
		Object node=Ognl.parseExpression(columnFields.get(j));
		parseNode.put(columnFields.get(j), node);
	}
}
public String getProp(String field,Object obj) throws OgnlException{
	Object o=Ognl.getValue(parseNode.get(field), obj);
	if(o==null)return "";
	return String.valueOf(o);
}
public void write(HttpServletResponse rsp){

Long start=System.currentTimeMillis();
	 if(list==null)list=new ArrayList();
	 PrintWriter pw;
	try {
		parseFiled();
		rsp.setContentType("text/json;charset=GBK");
        rsp.setHeader("Cache-Control","no-store, max-age=0, no-cache, must-revalidate");    
        rsp.addHeader("Cache-Control", "post-check=0, pre-check=0");
        rsp.setHeader("Pragma", "no-cache"); 
		rsp.setCharacterEncoding("GBK");
		pw = rsp.getWriter();
		StringBuffer sb=new StringBuffer();
		sb.append("/*{");
		sb.append("totalRow:");sb.append(String.valueOf(pager.getTotalRow()));sb.append(",");
		sb.append("totalPage:");sb.append(String.valueOf(pager.getTotalPage()));sb.append(",");
		sb.append("currentPage:");sb.append(String.valueOf(pager.getCurrentpage()));sb.append(",");
		sb.append("pageSize:");sb.append(String.valueOf(pager.getPageSize()));sb.append(",");
		sb.append("startIndex:");sb.append(String.valueOf(pager.getStartIndex()+1));sb.append(",");
		sb.append("endIndex:");sb.append(String.valueOf(pager.getStartIndex()+list.size()));sb.append(",");
		sb.append("hasNext:");sb.append(String.valueOf(pager.hasNext()));sb.append(",");
		sb.append("hasPrevious:");sb.append(String.valueOf(pager.hasPrevious()));sb.append(",");
		sb.append("numRows:");sb.append(String.valueOf(list.size()));sb.append(",");
		sb.append("identifier:'");sb.append(getIdentityName());sb.append("',");
		sb.append("items:[");
		for(int i=0;i<list.size();i++){
			sb.append("{");
			for(int j=0;j<columnFields.size();j++){
				String field=columnFields.get(j);
				sb.append(field);
				sb.append(":\"");
				sb.append(getProp(field, list.get(i)));
				sb.append("\"");
				if(j!=columnFields.size()-1)
				sb.append(",");
			}
			sb.append("}");
			if(i!=list.size()-1)
				sb.append(",");
		}
		sb.append("]");
		sb.append("}*/");
		pw.write(sb.toString());
		rsp.flushBuffer();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
Long end=System.currentTimeMillis();
System.out.println("time:.........................");
System.out.println(start-end);
 }
public void setQueryAttr(String queryName,String value){
	queryHandle=true;
	this.query.append("'");
	this.query.append(queryName);
	this.query.append("'");
	this.query.append(":");
	this.query.append("'");
	this.query.append(value);
	this.query.append("'");
	this.query.append(",");
}
public void addField(String field){
	this.columnFields.add(field);
}
public void addUnSortField(String field){
	this.unSortFields.append("'");
	this.unSortFields.append(field);
	this.unSortFields.append("'");
	this.unSortFields.append(",");
}
public String getUnSortFields(){
	this.unSortFields.append("'_a']");
	return this.unSortFields.toString();
}
public void addColumn(String field,String name,String width,boolean hidden){
	Column c=new Column(field,name,width,hidden);
	addField(field);
	columns.add(c);
}
public void addColumn(String field,String name,String width,boolean hidden,String linkAction){
	Column c=new Column(field,name,width,hidden,linkAction);
	containLink=true;
	linkUrl=linkAction;
	addField(field);
	columns.add(c);
}
public void addColumn(String field,String name,String width,boolean hidden,boolean sortable,String formater){
	Column c=new Column(field,name,width,hidden,sortable,formater);
	addField(field);
	if(sortable){
		
	}
	columns.add(c);
}
public String getFields(){
	StringBuffer sb=new StringBuffer("[");
	for(int i=0;i<columnFields.size();i++){
		sb.append("'");
		sb.append(columnFields.get(i));
		sb.append("'");
		if(i!=columnFields.size()-1)sb.append(",");
	}
	sb.append("]");
	return sb.toString();
}
public void clear(){
	
}

public String getQuery() {
	this.query.replace(this.query.length()-1, this.query.length(), "}");
	String s=this.query.toString();
	this.query=new StringBuffer("{");
	return s;
}
public void setQuery(String query) {
}
public String getPageAction() {
	return pageAction;
}
public void setPageAction(String pageAction) {
	this.pageAction = pageAction;
}
public Pager getPager() {
	return pager;
}
public void setPager(Pager pager) {
	this.pager = pager;
}
public List getList() {
	return list;
}
public void setList(List list) {
	this.list = list;
}
public List<String> getColumnFields() {
	return columnFields;
}
public void setColumnFields(List columnFields) {
	this.columnFields = columnFields;
}
public int getPageMethod() {
	return pageMethod;
}
public void setPageMethod(int pageMethod) {
	this.pageMethod = pageMethod;
	pager.setPageMethod(pageMethod);
}
public int getCurrentPage() {
	return currentPage;
}
public void setCurrentPage(int currentPage) {
	this.currentPage = currentPage;
	this.pager.setCurrentpage(currentPage);
}
@Override
public String getTagId(){
	return tag_id;
}
public boolean isDelete() {
	return delete;
}
public void setDelete(boolean delete) {
	this.delete = delete;
}
public List<String> getIdentityList() {
	return identityList;
}
public void setIdentityList(List<String> identityList) {
	this.identityList = identityList;
}
public String getIdentityName() {
	return identityName;
}
public void setIdentityName(String identityName) {
	this.identityName = identityName;
}
public boolean isIndirectSelection() {
	return indirectSelection;
}
public void setIndirectSelection(boolean indirectSelection) {
	this.indirectSelection = indirectSelection;
}
public int getPageSize() {
	return pageSize;
}
public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
	this.pager.setPageSize(pageSize);
}
public void setCount(int count){
	this.pager.setPageSize(count);
}
public boolean isDnd() {
	return dnd;
}
public void setDnd(boolean dnd) {
	this.dnd = dnd;
}
public boolean isNestedSorting() {
	return nestedSorting;
}
public void setNestedSorting(boolean nestedSorting) {
	this.nestedSorting = nestedSorting;
}
public String getSort() {
	return sort;
}
public void setSort(String sort) {
	this.sort = sort;
}
public String getSearchFormId() { 
	return searchFormId;
}
public void setSearchFormId(String searchFormId) {
	this.searchFormId = searchFormId;
}
public boolean isContainLink() {
	return containLink;
}
public void setContainLink(boolean containLink) {
	this.containLink = containLink;
}
public String getLinkUrl() {
	return linkUrl;
}
public void setLinkUrl(String linkUrl) {
	this.linkUrl = linkUrl;
}
public boolean isGSearchHandle() {
	return gSearchHandle;
}
public void setGSearchHandle(boolean gSearchHandle) {
	this.gSearchHandle = gSearchHandle;
}
public boolean isQueryHandle() {
	return queryHandle;
}
public void setQueryHandle(boolean queryHandle) {
	this.queryHandle = queryHandle;
}
public List getRows(){
	return list;
}
/**
 * @return the sortname
 */
public String getSortname() {
	return sortname;
}
/**
 * @param sortname the sortname to set
 */
public void setSortname(String sortname) {
	this.sortname = sortname;
}
/**
 * @return the sortorder
 */
public String getSortorder() {
	return sortorder;
}
/**
 * @param sortorder the sortorder to set
 */
public void setSortorder(String sortorder) {
	this.sortorder = sortorder;
}
}
