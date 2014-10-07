package net.linybin7.core.web.component.tree;

import java.io.Serializable;

/**
 * ���ڵ�
 * 
 * 
 */
public class TreeNode implements Serializable {

	// �ڵ�ID
	private String nodeID;

	// ���ڵ�ID
	private String parentID;

	// �ڵ�����
	private String nodeName;

	// �Ƿ�open
	private boolean open = false;

	// �Ƿ���϶�
	private boolean drag = true;

	// �����϶���Ϊ���ڵ�
	private boolean dropInner = true;

	// �����϶����ڵ���
	private boolean drop = true;

	// Ҷ��
	private boolean leaf = false;

	// �Ƿ񸸽ڵ�
	private boolean isParent = false;

	// �Ҽ����ε���
	private boolean onRightClick = false;

	// ����
	private String link;

	// ��ʾ��Ϣ
	private String title;

	// checkbox valueֵ
	private String value;

	// �Ƿ��ѡ���
	private boolean hasInput = true;

	// �Ƿ�ѡ��
	private boolean checked;

	// �ڵ�ͼ
	private String icon;

	// ����״̬������Tab��ǩ

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
