package net.linybin7.common.widget;

public class GridField {
	public GridField() {
		super();
	}

	String field;
	String name;
	String width = "auto";

	public String getField() {
		return field;
	}

	public GridField(String field, String name, String width) {
		super();
		this.field = field;
		this.name = name;
		this.width = width;
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
}
