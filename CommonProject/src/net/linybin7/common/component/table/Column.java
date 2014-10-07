package net.linybin7.common.component.table;

/**
 * 

 * 
 */
public class Column {
	private String name;

	private String label;

	private String width;

	private String align = "left";

	private String link = "";
	
	private int show = 0; //1:tab³ÊÏÖ£»2£ºµ¯³ö£»
	
	private String tabname="";
	
	private String tabid="";

	private boolean pop = true;

	private boolean checkbox;

	public String cheboxName = "selectedIds";
	
	public String title;

	public Column() {

	}

	public Column(String name, String label, String width, String align,
			String link,int show,String tabname,String tabid, boolean checkbox) {
		this(name, label, width, align, link, show,tabname,tabid, checkbox, true);
	}

	public Column(String name, String label, String width, String align,
			String link,int show,String tabname,String tabid, boolean checkbox, boolean isPop) {
		this(name, label, width, align, link, show,tabname,tabid, checkbox, isPop, null);
	}
	
	public Column(String name, String label, String width, String align,
			String link,int show,String tabname,String tabid, boolean checkbox, boolean isPop, String title) {
		this.name = name;
		this.label = label;
		this.width = width;
		this.align = align;
		this.link = link == null ? "" : link.trim();
		this.show = show;
		this.tabname = tabname ==null ? "" : tabname.trim();
		this.tabid = tabid ==null ? "" : tabid.trim();
		this.checkbox = checkbox;
		this.pop = isPop;
		this.title= title;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getShow() {
		return show;
	}

	public void setShow(int show) {
		this.show = show;
	}
	
	public String getTabname() {
		return tabname;
	}

	public void setTabname(String tabname) {
		this.tabname = tabname;
	}	
	
	public String getTabid() {
		return tabid;
	}

	public void setTabid(String tabid) {
		this.tabid = tabid;
	}
	
	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getCheboxName() {
		return cheboxName;
	}

	public void setCheboxName(String cheboxName) {
		this.cheboxName = cheboxName;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean isPop() {
		return pop;
	}

	public void setPop(boolean pop) {
		this.pop = pop;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitleTrim(){
		if(title == null){
			return "";
		}else{
			return title.trim();
		}
	}
}
