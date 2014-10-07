package net.linybin7.common.tag;


public class Dialog extends Tag{
   private String grid;
   private String form;
   private String href;
   private String modal;
   private boolean refreshOnShow;
   private boolean preventCachen;
   private String title_;
   
public Dialog() {
	super();
}
public String getGrid() {
	return grid;
}
public Dialog create(){
	return new Dialog();
}
public void setGrid(String grid) {
	this.grid = grid;
}
public String getForm() {
	return form;
}
public void setForm(String form) {
	this.form = form;
}
public String getHref() {
	return href;
}
public void setHref(String href) {
	this.href = href;
}
public String getModal() {
	return modal;
}
public void setModal(String modal) {
	this.modal = modal;
}
public boolean isRefreshOnShow() {
	return refreshOnShow;
}
public void setRefreshOnShow(boolean refreshOnShow) {
	this.refreshOnShow = refreshOnShow;
}
public boolean isPreventCachen() {
	return preventCachen;
}
public void setPreventCachen(boolean preventCachen) {
	this.preventCachen = preventCachen;
}
public String getTitle_() {
	return title_;
}
public void setTitle_(String title_) {
	this.title_ = title_;
}
   
}
