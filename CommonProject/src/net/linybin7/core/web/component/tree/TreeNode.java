package net.linybin7.core.web.component.tree;

import java.io.Serializable;

/**
 * 树节点
 * 
 * 
 */
public class TreeNode implements Serializable {

	// 节点ID
	private String nodeID;

	// 父节点ID
	private String parentID;

	// 节点名称
	private String nodeName;

	// 是否open
	private boolean open = false;

	// 是否可拖动
	private boolean drag = true;

	// 不能拖动成为父节点
	private boolean dropInner = true;

	// 不能拖动到节点上
	private boolean drop = true;

	// 叶子
	private boolean leaf = false;

	// 是否父节点
	private boolean isParent = false;

	// 右键屏蔽弹出
	private boolean onRightClick = false;

	// 链接
	private String link;

	// 提示信息
	private String title;

	// checkbox value值
	private String value;

	// 是否带选择框
	private boolean hasInput = true;

	// 是否选中
	private boolean checked;

	// 节点图
	private String icon;

	// 加上状态字区别Tab标签

	public TreeNode() {
	}

	public TreeNode(String nodeID, String parentID, String nodeName, String link, String title,
			String value, boolean hasInput, String tabType, boolean checked, String icon) {
		this.nodeID = nodeID;
		this.parentID = parentID;
		this.nodeName = nodeName;
		this.link = link;
		this.title = title;
		this.value = value;
		this.hasInput = hasInput;
		this.checked = checked;
		this.icon = icon;
	}

	public boolean isChecked() {
		return checked;
	}

	public String getNodeID() {
		return nodeID;
	}

	public String getNodeName() {
		return nodeName;
	}

	public String getLink() {
		return link;
	}

	public String getParentID() {
		return parentID;
	}

	public String getTitle() {
		return title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isHasInput() {
		return hasInput;
	}

	public void setHasInput(boolean hasInput) {
		this.hasInput = hasInput;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isOnRightClick() {
		return onRightClick;
	}

	public void setOnRightClick(boolean onRightClick) {
		this.onRightClick = onRightClick;
	}

	public boolean isDrag() {
		return drag;
	}

	public void setDrag(boolean drag) {
		this.drag = drag;
	}

	public boolean isDropInner() {
		return dropInner;
	}

	public void setDropInner(boolean dropInner) {
		this.dropInner = dropInner;
	}

	public boolean isDrop() {
		return drop;
	}

	public void setDrop(boolean drop) {
		this.drop = drop;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

}
