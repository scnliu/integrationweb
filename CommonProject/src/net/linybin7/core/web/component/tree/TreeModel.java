package net.linybin7.core.web.component.tree;

import java.util.ArrayList;
import java.util.List;

import net.linybin7.common.util.StringHelper;


/**
 * js树组件
 * 
 * 
 */
public class TreeModel {
	private String obj = null;
	// 是否带复选框
	private boolean hasCheckbox = false;

	// 链接目标框架
	private String target = "mainFrame";

	// 选择框名称
	private String checkBoxName = "selectedIds";

	// 选择框类型
	private String inputType = "checkbox";

	private String tabType = "tab";

	// 选择框是否可用
	private boolean cbDisabled = true;

	public List<TreeNode> nodesList = new ArrayList<TreeNode>();

	public TreeModel(String obj) {
		this.obj = obj;
	}

	public boolean getHasCheckbox() {
		return hasCheckbox;
	}

	public void setHasCheckbox(boolean hasCheckbox) {
		this.hasCheckbox = hasCheckbox;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getCheckBoxName() {
		return checkBoxName;
	}

	public void setCheckBoxName(String checkBoxName) {
		this.checkBoxName = checkBoxName;
	}

	public boolean getCbDisabled() {
		return cbDisabled;
	}

	public void setCbDisabled(boolean cbDisabled) {
		this.cbDisabled = cbDisabled;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getTabType() {
		return tabType;
	}

	public void setTabType(String tabType) {
		this.tabType = tabType;
	}

	public List<TreeNode> getNodesList() {
		return nodesList;
	}

	public void setNodesList(List<TreeNode> nodesList) {
		this.nodesList = nodesList;
	}

	/**
	 * 构造树
	 * 
	 * @return
	 */
	public String build() {
		StringBuilder builder = new StringBuilder();
		builder.append("var " + obj + "=new TreeView('" + obj + "',");
		builder.append(getHasCheckbox());
		builder.append(",'");
		builder.append(getTarget());
		builder.append("' ,null,null,null,'");
		builder.append(getCheckBoxName());
		builder.append("',");
		builder.append(getCbDisabled());
		builder.append(",'");
		builder.append(getInputType());
		builder.append("','");
		builder.append(getTabType());
		builder.append("');\n");
		for (TreeNode node : nodesList) {
			String title = node.getTitle() == null ? "" : node.getTitle();
			String key = node.getValue() == null ? null : "'" + node.getValue() + "'";
			String parentId = node.getParentID() == null ? "" : node.getParentID();
			builder.append("\t\t\t" + obj + ".add('");
			builder.append(node.getNodeID());
			builder.append("','");
			builder.append(parentId);
			builder.append("','");
			builder.append(node.getNodeName());
			builder.append("','");
			builder.append(StringHelper.trim(node.getLink()));
			builder.append("','");
			builder.append(title);
			builder.append("',");
			builder.append(key);
			builder.append(",");
			builder.append(node.isChecked() ? "'1'" : "'0'");
			builder.append(",");
			builder.append(StringHelper.isEmpty(node.getIcon()) ? null : "'"
					+ node.getIcon().trim() + "'");
			builder.append(",");
			builder.append(node.isHasInput());
			builder.append(");\n");
		}
		builder.append("\t\t\tdocument.write(" + obj + ");\n");
		return builder.toString();
	}

	public String ykbuild() {
		StringBuilder builder = new StringBuilder();
		builder.append("[ \n");
		for (TreeNode node : nodesList) {
			String title = node.getTitle() == null ? "" : node.getTitle();
			String key = node.getValue() == null ? null : "'" + node.getValue()

			+ "'";
			String parentId = node.getParentID() == null ? "" : node.getParentID();

			builder.append("{");

			builder.append("id:");
			builder.append("\"" + node.getNodeID() + "\",");
			builder.append("pId:");
			if (parentId == "") {
				parentId = "0";
			}
			builder.append("\"" + parentId + "\",");
			builder.append("name:");
			builder.append("\"" + node.getNodeName() + "\",");

			if (node.getLink() == "") {
				if (parentId == "0") {
					builder.append("open:true");
				} else {
					builder.append("open:false");
				}
			} else {
				if (target.equals("Home_Main_Frame")) {
					builder.append("taburl:");
					builder.append("\"" + StringHelper.trim(node.getLink()) + "\",");
					builder.append("showTabs:true");
				} else {
					if (inputType.equals("radio")) {

					} else {
						builder.append("url:");
						builder.append("\"" + StringHelper.trim(node.getLink()) + "\",");
						builder.append("target:");
						builder.append("\"" + target + "\",");
					}
					try {
						if (node.getNodeID().equals("frame") || node.getNodeID().equals("Root")) {
							builder.append("open:true");
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			builder.append("},");
		}
		String rs = builder.substring(0, builder.length() - 1);
		rs = rs + "] \n";
		return rs;
	}

	public String ykmainbuild() {
		StringBuilder builder = new StringBuilder();
		builder.append("[ \n");
		for (TreeNode node : nodesList) {
			String title = node.getTitle() == null ? "" : node.getTitle();
			String key = node.getValue() == null ? null : "'" + node.getValue()

			+ "'";
			String parentId = node.getParentID() == null ? "" : node.getParentID();

			builder.append("{");

			builder.append("id:");
			builder.append("\"" + node.getNodeID() + "\",");
			builder.append("pId:");
			if (parentId == "") {
				parentId = "0";
			}
			builder.append("\"" + parentId + "\",");
			builder.append("name:");
			builder.append("\"" + node.getNodeName() + "\",");
			builder.append("title:");
			builder.append("\"" + node.getTitle() + "\",");

			if (node.getLink() == "") {
				if (parentId == "0") {
					// 显示ROOT图标
					if (node.getIcon() == null) {
						builder.append("iconSkin:");
						builder.append("\"normalRoot\",");
					} else {
						builder.append("iconSkin:");
						builder.append("\"" + node.getIcon() + "\",");
					}
					builder.append("open:true");
				} else {
					// 显示Folder图标
					if (node.getIcon() == null) {
						builder.append("iconSkin:");
						builder.append("\"normalFolder\",");
					} else {
						builder.append("iconSkin:");
						builder.append("\"" + node.getIcon() + "\",");
					}
					builder.append("open:false");
				}
			} else {
				// 显示Page图标
				if (node.getIcon() == null) {
					builder.append("iconSkin:");
					builder.append("\"normalPage\",");
				} else {
					builder.append("iconSkin:");
					builder.append("\"" + node.getIcon() + "\",");
				}
				if (target.equals("Home_Main_Frame")) {
					builder.append("taburl:");
					builder.append("\"" + StringHelper.trim(node.getLink()) + "\",");
					builder.append("showTabs:true");
				} else {
					if (inputType.equals("radio")) {

					} else {
						builder.append("url:");
						builder.append("\"" + StringHelper.trim(node.getLink()) + "\",");
						builder.append("target:");
						builder.append("\"" + target + "\",");
					}
					if (node.getNodeID().equals("frame") || node.getNodeID().equals("Root")) {
						builder.append("open:true");
					}
				}
			}
			builder.append("}\n,");
		}
		String rs = builder.substring(0, builder.length() - 1);
		rs = rs + "] \n";
		return rs;
	}

	public String exprTypeTreebuild() {
		StringBuilder builder = new StringBuilder();
		builder.append("[ \n");
		for (TreeNode node : nodesList) {
			String title = node.getTitle() == null ? "" : node.getTitle();
			String key = node.getValue() == null ? null : "'" + node.getValue()

			+ "'";
			String parentId = node.getParentID() == null ? "" : node.getParentID();

			builder.append("{");

			builder.append("id:");
			builder.append("\"" + node.getNodeID() + "\",");
			builder.append("pId:");
			if (parentId == "") {
				parentId = "0";
			}
			builder.append("\"" + parentId + "\",");
			builder.append("name:");
			builder.append("\"" + node.getNodeName() + "\",");
			builder.append("title:");
			builder.append("\"" + node.getTitle() + "\",");
			if (node.isParent()) {
				builder.append("isParent:true,");
			}
			if (node.isOnRightClick()) {
				builder.append("noR:true,");
			}
			if (!node.isDrag()) {
				builder.append("drag:false,");
			}
			if (!node.isDrop()) {
				builder.append("drog:false,");
			}
			if (!node.isDropInner()) {
				builder.append("dropInner:false,");
			}
			if (node.isLeaf()) {
				builder.append("leaf:true,");
			}
			if (node.getLink() == "") {
				if (node.getIcon() == null) {
					// builder.append("iconSkin:");
					// builder.append("\"normalFolder\",");
				} else {
					builder.append("iconSkin:");
					builder.append("\"" + node.getIcon() + "\",");
				}
				builder.append("open:false");
			} else {
				// 显示Page图标
				if (node.getIcon() == null) {
					// builder.append("iconSkin:");
					// builder.append("\"normalPage\",");
				} else {
					builder.append("iconSkin:");
					builder.append("\"" + node.getIcon() + "\",");
				}
				if (target.equals("Home_Main_Frame")) {
					builder.append("taburl:");
					builder.append("\"" + StringHelper.trim(node.getLink()) + "\",");
					builder.append("showTabs:true");
				} else {
					if (inputType.equals("radio")) {

					} else {
						builder.append("url:");
						builder.append("\"" + StringHelper.trim(node.getLink()) + "\",");
						builder.append("target:");
						builder.append("\"" + target + "\",");
					}
					if (node.isOpen()) {
						builder.append("open:true");
					}
				}
			}
			builder.append("}\n,");
		}
		String rs = builder.substring(0, builder.length() - 1);
		rs = rs + "] \n";
		return rs;
	}
	
	public String treebuild() {
		StringBuilder builder = new StringBuilder();
		builder.append("[ \n");
		for (TreeNode node : nodesList) {
			String title = node.getTitle() == null ? "" : node.getTitle();
			String key = node.getValue() == null ? null : "'" + node.getValue()+ "'";
			String parentId = node.getParentID() == null ? "" : node.getParentID();
			builder.append("{");
			builder.append("id:");
			builder.append("\""+node.getNodeID()+"\",");
			builder.append("pId:");
			if (parentId==""){
				parentId="0";
			}
			builder.append("\""+parentId+"\",");
			builder.append("name:");
			builder.append("\""+node.getNodeName()+"\",");
			builder.append("title:");
			builder.append("\""+node.getTitle()+"\",");
			if(node.isParent()){
				builder.append("isParent:true,");
			}
			if(node.isOnRightClick()){
				builder.append("noR:true,");
			}
			if(!node.isDrag()){
				builder.append("drag:false,");
			}
			if(!node.isDrop()){
				builder.append("drog:false,");
			}
			if(!node.isDropInner()){
				builder.append("dropInner:false,");
			}
			if(node.isLeaf()){
				builder.append("leaf:true,");
			}
			if(node.getLink()==""){
				if(node.getIcon()==null){
//					builder.append("iconSkin:");
//					builder.append("\"normalFolder\","); 
				}
				else{
					builder.append("iconSkin:");
					builder.append("\""+node.getIcon()+"\","); 
				}
				builder.append("open:false");
			}else{
					  //显示Page图标
					  if(node.getIcon()==null){
//						  builder.append("iconSkin:"); 
//						  builder.append("\"normalPage\","); 
					  }else{
						  builder.append("iconSkin:");
						  builder.append("\""+node.getIcon()+"\","); 				  
					  }					
					if (target.equals("Home_Main_Frame")){
						builder.append("taburl:");
						builder.append("\""+StringHelper.trim(node.getLink())+"\",");
						builder.append("showTabs:true");
					}
					else{
						if (inputType.equals("radio")){
							
						}else{
							builder.append("url:");
							builder.append("\""+StringHelper.trim(node.getLink())+"\",");
							builder.append("target:");
							builder.append("\""+target+"\",");	
						}
						if(node.isOpen()){
							builder.append("open:true");
						}
						else{
							builder.append("open:false");
						}
					}					
			}
			builder.append("}\n,");
		}	
		String rs=builder.substring(0, builder.length()-1);
		rs=rs+"] \n";
		return rs;
	}

}
