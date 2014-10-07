package net.linybin7.common.tag;


public class DateTime extends Tag{
	@Override
	public boolean isEvent(String attrName){
		if("value".equals(attrName))return true;
		return super.isEvent(attrName);
	}
}
