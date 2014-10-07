package net.linybin7.common.tag;

public class Column {
  private String field;
  private String name;
  private String width;
  private boolean hidden;
  private String link;
  private int openType;
  private boolean sortable=true;
  private String formatter;
  public Column(){}
  
public Column(String field, String name, String width,boolean hidden) {
	super();
	this.field = field;
	this.name = name;
	this.width = width;
	this.hidden=hidden;
}

public Column(String field, String name, String width, boolean hidden,
		String link) {
	super();
	this.field = field;
	this.name = name;
	this.width = width;
	this.hidden = hidden;
	this.link = link;
	this.formatter = "formatLink";
}

public Column(String field, String name, String width, boolean hidden,
		boolean sortable, String formater) {
	super();
	this.field = field;
	this.name = name;
	this.width = width;
	this.hidden = hidden;
	this.sortable = sortable;
	this.formatter = formater;
}

public boolean isSortable() {
	return sortable;
}

public void setSortable(boolean sortable) {
	this.sortable = sortable;
}

public int getOpenType() {
	return openType;
}

public void setOpenType(int openType) {
	this.openType = openType;
}

public boolean isHidden() {
	return hidden;
}

public void setHidden(boolean hidden) {
	this.hidden = hidden;
}

public String getField() {
	return field;
}
public void setField(String field) {
	this.field = field;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getWidth() {
	return width;
}
public void setWidth(String width) {
	this.width = width;
}

public String getLink() {
	return link;
}

public void setLink(String link) {
	this.link = link;
}

public String getFormatter() {
	return formatter;
}

public void setFormatter(String formatter) {
	this.formatter = formatter;
}

  
}
