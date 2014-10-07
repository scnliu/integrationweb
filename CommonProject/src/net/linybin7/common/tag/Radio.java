/**
 * 
 */
package net.linybin7.common.tag;

/**
 * @author HuangHuaSheng
 *2010-10-12 ионГ11:33:23
 */
public class Radio extends Tag{
	private String value;
	@Override
	public String toString(){
		String s=this.attr.toString();
		s+="value:'"+value+"'}";
		return s;
	}
	public Radio create(){
		return new Radio();
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
