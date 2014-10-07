package net.linybin7.common.component.select;


/**
 * 下拉选项
 * 

 * 
 */
public class Option {
	private String value;

	private String label;

	public Option(){}
	public Option(String value, String title) {
		this.label = title;
		this.value = value;
	}
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
