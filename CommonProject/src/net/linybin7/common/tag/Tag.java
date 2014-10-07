package net.linybin7.common.tag;

import java.util.HashMap;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;
import ognl.OgnlOps;

public class Tag {
	private String formName;
	protected String tag_id;
	private String tag_name;
	private String onclick;
	private String onmouseover;
	private String ondblclick;
	private String onmouseout;
	private String onblur;
	private String onfocus;
	private String onsubmit;
	private String width_;
	private String height_;
	private String style_;
	protected StringBuffer attr=new StringBuffer("{");
	private static final Map<String,Boolean> eventMap=new HashMap<String,Boolean> ();
	static {
		Boolean b=true;
		eventMap.put("onclick",b);
		eventMap.put("onmouseover",b);
		eventMap.put("ondblclick",b);
		eventMap.put("onmouseout",b);
		eventMap.put("onfocus",b);
		eventMap.put("onblur",b);
		eventMap.put("onchange",b);
		eventMap.put("onsubmit",b);
		eventMap.put("onmouseup",b);
		eventMap.put("onchange",b);
		eventMap.put("oncheck",b);
		eventMap.put("constraints",b);
		eventMap.put("checked",b);
	}
	public Tag(){}
	public void setAttr(String attrName,String value){
		this.attr.append(attrName);
		this.attr.append(":");
		if(!isEvent(attrName))this.attr.append("\"");
		this.attr.append(value);
		if(!isEvent(attrName))this.attr.append("\"");
		this.attr.append(",");
	}
	@Override
	public String toString(){
		this.attr.append("a:\"\"");
		this.attr.append("}");
		
		String s=this.attr.toString();
		this.attr=new StringBuffer("{");
		return s;
	}
	
	public boolean isEvent(String attrName){
		Boolean b=eventMap.get(attrName.toLowerCase());
		if(b==null)return false;
		return true;
	}
	public String getTagId() {
		if(formName==null)formName="";
		return (formName+"_"+tag_name).replace(".", "_");
	}
  public String getProVal(String propertyName,Object obj) throws OgnlException{
           Object Oval=Ognl.getValue(propertyName, obj);
           if(Oval==null)return "";
         return OgnlOps.stringValue(Oval);
   }
	public void setTagId(String id) {
		this.tag_id = id;
	}
	public String getTagName() {
		return tag_name;
	}
	public void setTagName(String name) {
		this.tag_name = name;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onClick) {
		this.onclick = onClick;
	}
	public String getOnmouseover() {
		return onmouseover;
	}
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}
	public String getOndblclick() {
		return ondblclick;
	}
	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}
	public String getOnmouseout() {
		return onmouseout;
	}
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}
	public String getOnblur() {
		return onblur;
	}
	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}
	public String getOnfocus() {
		return onfocus;
	}
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}
	public String getOnsubmit() {
		return onsubmit;
	}
	public void setOnsubmit(String onsubmit) {
		this.onsubmit = onsubmit;
	}
	public String getWidth_() {
		return width_;
	}
	public void setWidth_(String width_) {
		this.width_ = width_;
	}
	public String getHeight_() {
		return height_;
	}
	public void setHeight_(String height_) {
		this.height_ = height_;
	}
	public String getStyle_() {
		return style_;
	}
	public void setStyle_(String style_) {
		this.style_ = style_;
	}
	
	
}
