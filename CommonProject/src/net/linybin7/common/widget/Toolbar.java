package net.linybin7.common.widget;

import java.util.LinkedHashMap;
import java.util.Map;


public class Toolbar {

	String id;

	public Toolbar() {
		super();
		this.id = "toolbar" + System.currentTimeMillis();
	}

	Map<String, Button> buttons = new LinkedHashMap<String, Button>();

	public void addButton(Button b) {
		buttons.put(b.getId(), b);
	}

	public Button removeButton(Button b) {
		return this.removeButton(b.getId());
	}

	public Button removeButton(String bid) {
		return buttons.remove(bid);
	}

	public void addButton(String bid) {
		if (bid.equalsIgnoreCase(this.getDefaultAddButton().getId())) {
			this.addButton(this.getDefaultAddButton());
		} else if (bid.equalsIgnoreCase(this.getDefaultEditButton().getId())) {
			this.addButton(this.getDefaultEditButton());
		} else if (bid.equalsIgnoreCase(this.getDefaultDeleteButton().getId())) {
			this.addButton(this.getDefaultDeleteButton());
		} else if (bid.equalsIgnoreCase(this.getDefaultStopButton().getId())) {
			this.addButton(this.getDefaultStopButton());
		} else if (bid.equalsIgnoreCase(this.getDefaultStartButton().getId())) {
			this.addButton(this.getDefaultSeparator());
		} else if (bid.equalsIgnoreCase(this.getDefaultSeparator().getId())) {
			this.addButton(this.getDefaultAddButton());
		}
	}

	public Button getDefaultAddButton() {
		return new Button(this.id + ".add", "新增", true, "add",
				"dijitEditorIconCopy");
	}

	public Button getDefaultEditButton() {
		return new Button(this.id + ".edit", "修改", true, "edit",
				"dijitEditorIconWikiword");
	}

	public Button getDefaultDeleteButton() {
		return new Button(this.id + ".delete", "删除", true, "delete",
				"dijitEditorIconDelete");
	}

	public Button getDefaultViewButton() {
		return new Button(this.id + ".view", "查看", true, "view",
				"dijitEditorIconSelectAll");
	}

	public Button getDefaultStopButton() {
		return new Button(this.id + ".stop", "停用", true, "stop",
				"dijitEditorIconUnlink");
	}

	public Button getDefaultStartButton() {
		return new Button(this.id + ".start", "启用", true, "start",
				"dijitEditorIconCreateLink");
	}

	public Button getDefaultSeparator() {
		return new Button("Separator");
	}

	public Map<String, Button> getButtons() {
		return buttons;
	}

	public Map<String, Button> getDefaultButtons() {
		Map<String, Button> defaultButtons = new LinkedHashMap<String, Button>();
		defaultButtons.put(this.getDefaultAddButton().getId(), this
				.getDefaultAddButton());
		defaultButtons.put(this.getDefaultEditButton().getId(), this
				.getDefaultEditButton());
		defaultButtons.put(this.getDefaultDeleteButton().getId(), this
				.getDefaultDeleteButton());
		defaultButtons.put(this.getDefaultViewButton().getId(), this
				.getDefaultViewButton());
		defaultButtons.put(this.getDefaultSeparator().getId(), this
				.getDefaultSeparator());
		defaultButtons.put(this.getDefaultStartButton().getId(), this
				.getDefaultStartButton());
		defaultButtons.put(this.getDefaultStopButton().getId(), this
				.getDefaultStopButton());
		return defaultButtons;
	}

	public void setButtons(Map<String, Button> buttons) {
		this.buttons = buttons;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
