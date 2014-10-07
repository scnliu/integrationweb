package net.linybin7.common.tag;

public class Form extends Tag {
	private boolean ajax=true;
	private String onSubmit;
	public String getOnSubmit() {
		return onSubmit;
	}
	public void setOnSubmit(String onSubmit) {
		this.onSubmit = onSubmit;
	}
	public String getTagId(){
		return tag_id;
	}
	public boolean isAjax() {
		return ajax;
	}
	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

}
