package net.linybin7.common.tag;

import java.util.HashMap;
import java.util.Map;

public class Button extends Tag{
	private String iconClass;
	private String gridId;
	private String dialog;
	public Button(){
		super();
	}
	public Button create(){
		return new Button();
	}
	public void setAttr(String attrName,String value){
		super.attr.append(attrName);
		super.attr.append(":");
		if(!isEvent(attrName))super.attr.append("\"");
		else if(this.getGridId()!=null){
			super.attr.append("function(){");

			System.out.println(attrName+":"+value);
		}
		super.attr.append(value);
		if(!isEvent(attrName))super.attr.append("\"");
		else if(this.getGridId()!=null){
			super.attr.append("();}");
		}
		super.attr.append(",");
	}
	public String getGridId() {
		return gridId;
	}
	public void setGridId(String gridId) {
		System.out.println("gridId:"+gridId);
		this.gridId = gridId;
	}
	public String getIconClass() {
		return iconClass;
	}
	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
		setAttr("iconClass",iconClass);
	}
	public String getDialog() {
		return dialog;
	}
	public void setDialog(String dialog) {
		this.dialog = dialog;
	}
	
	
}
