package net.linybin7.common.tag;

import java.util.ArrayList;
import java.util.List;


public class CheckBox extends Radio{
  private int next_id=0;
  private List<String> valueList;
  @Override
public String getTagId(){
	  String id=super.getTagId();
	  return id;
  }
  public void clear(){
	  next_id=0;
  }
  public boolean isChecked(String value){
	  if(valueList==null)return false;
	  for(String s:valueList){
		  if(value.equals(s))return true;
	  }
	  return false;
  }
	@Override
	public CheckBox create(){
		return new CheckBox();
	}
	public List<String> getValueList() {
		return valueList;
	}
	public void setValueList(String[] values) {
		if(values!=null){
			this.valueList=new ArrayList<String>();
			for(String s:values){
				this.valueList.add(s);
			}
		}
	}
	public void setValueList(List<String> valueList) {
		this.valueList = valueList;
	}
}
