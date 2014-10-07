package net.linybin7.common.component.tree;

import java.util.List;

/**
 * ���ڵ�


 *
 */
public interface TreeNode {
	/**
	 * �ڵ�ID
	 * @return
	 */
	public String getId();
	public void setId(String id);
	
	/**
	 * �ڵ�����
	 * @return
	 */
	public String getName();
	public void setName(String name);
	
	
	/**
	 * �ڵ�����
	 * @return
	 */
	public String getLink();
	public void setLink(String link);
			
	/**
	 * Checkbox ֵ

	 * @return
	 */
	public String getValue();
	public void setValue(String value);
	
	/**
	 * �ӽڵ�

	 * @return
	 */
	public List<TreeNode> getChildren();
	public void setChildren(List<TreeNode> children);
	
	/**
	 * �ڵ�ͼƬ
	 * @return
	 */
	public String getIcon();
	public void setIcon(String icon);
		
}
