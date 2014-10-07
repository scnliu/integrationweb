package net.linybin7.common.widget;

public class Button {
	// 按钮id
	String id;

	// 按钮标签
	String label;

	// 是否显示标签
	boolean showLabel = true;

	public boolean isShowLabel() {
		return showLabel;
	}

	// 点击事件方法名
	String onclick;

	// 按钮图标css class
	String iconClass;

	public Button(String id, String label, boolean showLabel, String onclick,
			String iconClass) {
		super();
		this.id = id;
		this.label = label;
		this.showLabel = showLabel;
		this.onclick = onclick;
		this.iconClass = iconClass;
	}

	public Button() {
		super();
	}

	public Button(String id) {
		this.id = id;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public void setShowLabel(boolean showLabel) {
		this.showLabel = showLabel;
	}
}
