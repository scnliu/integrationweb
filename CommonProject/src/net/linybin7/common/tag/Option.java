/**
 * 
 */
package net.linybin7.common.tag;

/**
 * @author HuangHuaSheng
 *2010-10-21 обнГ05:21:09
 */
public class Option {
	private String key;
	  private String label;
  public Option() {
		super();
	}
public Option(String key, String label) {
		super();
		this.key = key;
		this.label = label;
	}

public String getKey() {
	return key;
}
public void setKey(String key) {
	this.key = key;
}
public String getLabel() {
	return label;
}
public void setLabel(String label) {
	this.label = label;
}
}
