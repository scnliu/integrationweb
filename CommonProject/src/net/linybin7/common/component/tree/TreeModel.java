package net.linybin7.common.component.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 

 *  
 */
public class TreeModel {

	/**
	 * �Ƿ����ѡ���ѡ��
	 */
	private boolean hasInput;

	/**
	 * ����������:checkbox �� radio
	 */
	private String type = "checkbox";

	/**
	 * ����������

	 */
	private String name = "selectedIds";

	/**
	 * �������Ƿ���Ч

	 */
	private boolean disabled;

	/**
	 * target
	 */
	private String target = "";

	/**
	 * ���ڵ�

	 */
	private TreeNode root;

	/**
	 * ѡ�еĽڵ�ID
	 */
	private Map<String, String> checkedMap = new HashMap<String, String>();
	
	private int expandLevel = 0;

	public int getExpandLevel() {
		return expandLevel;
	}

	public void setExpandLevel(int expandLevel) {
		this.expandLevel = expandLevel;
	}

	public TreeModel() {

	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isHasInput() {
		return hasInput;
	}

	public void setHasInput(boolean hasInput) {
		this.hasInput = hasInput;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setSelected(List ids){
		for(int i = 0; i < ids.size(); i++){
			checkedMap.put((String)ids.get(i), null);
		}
	}
	
	public void setSelected(String[] ids){
		for(int i = 0; ids != null && i < ids.length; i++){
			checkedMap.put(ids[i], null);
		}
	}

	/**
	 * �Ƿ�Ϊ��
	 * 
	 * @param map
	 * @return
	 */
	private boolean isBlank(Map map) {
		return map == null || map.size() == 0;
	}

	/**
	 * �����ڵ�
	 * 
	 * @param node
	 * @return
	 */
	public String buildNode(TreeNode node, String context) {

		if (node == null) {
			return "";
		}
		StringBuffer nodes = new StringBuffer();
		nodes.append("<div dojoType=\"my:TreeNode\" id=\"");
		nodes.append(node.getId());
		nodes.append("\" title=\"");
		nodes.append(node.getName());
		if(node.getIcon() != null && node.getIcon().trim().length() > 0){
			nodes.append("\" childIconSrc=\"");
			nodes.append(context);
			nodes.append(node.getIcon());
		}
		nodes.append("\" link=\"");
		String link = node.getLink() == null ? "" : node.getLink().trim();
		nodes.append(link);
		if (!isBlank(checkedMap) && checkedMap.containsKey(node.getId())) {
			nodes.append("\" checkedValue=\"true");
		}else{
			nodes.append("\" checkedValue=\"false");
		}
		nodes.append("\" selectedValue=\"");
		nodes.append(node.getValue());
		nodes.append("\">\n");
		for (TreeNode child : node.getChildren()) {
			nodes.append(buildNode(child, context));
		}
		nodes.append("</div>\n");		
		String str = nodes.toString();
		
		return str;
	}
}
