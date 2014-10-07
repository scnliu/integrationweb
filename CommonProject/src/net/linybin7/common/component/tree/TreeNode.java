package net.linybin7.common.component.tree;

import java.util.List;

/**
 * 树节点


 *
 */
public interface TreeNode {
	/**
	 * 节点ID
	 * @return
	 */
	public String getId();
	public void setId(String id);
	
	/**
	 * 节点名称
	 * @return
	 */
	public String getName();
	public void setName(String name);
	
	
	/**
	 * 节点链接
	 * @return
	 */
	public String getLink();
	public void setLink(String link);
			
	/**
	 * Checkbox 值

	 * @return
	 */
	public String getValue();
	public void setValue(String value);
	
	/**
	 * 子节点

	 * @return
	 */
	public List<TreeNode> getChildren();
	public void setChildren(List<TreeNode> children);
	
	/**
	 * 节点图片
	 * @return
	 */
	public String getIcon();
	public void setIcon(String icon);
		
}
