package net.linybin7.core.util;

import java.util.HashMap;

/**
 * 常量定义
 * 
 * 
 * 
 */
public final class Constants {
	// key常量
	/**
	 * sessionMap全局变量
	 */
	public static HashMap sessionMap = new HashMap();
	
	/**
	 * 菜单功能全局变量
	 */
	public static HashMap pubFuncMap = new HashMap();
	
	/**
	 * 功能统计全局变量
	 */
	public static HashMap funcMap = new HashMap();
	/**
	 * 提示信息key
	 */
	public static final String KEY_MSG = "message_key";

	/**
	 * 访问者key
	 */
	public static final String KEY_VISITOR = "visitor_key";

	/**
	 * 系统配置对象key
	 */
	public static final String KEY_SYSCFG = "syscfg_key";

	// 数据源常量
	/**
	 * 服务器类型数据源
	 */
	public static final int DS_TYPE_SERVER = 1;

	/**
	 * 普通类型数据源
	 */
	public static final int DS_DYPE_COMMON = 2;

	// 用户角色常量
	/**
	 * 启用
	 */
	public static final int STOP_NO = 0;

	/**
	 * 停用
	 */
	public static final int STOP_YES = 1;
	/**
	 * 未登录状态
	 */
	public static final int STATUS_NO = 0;

	/**
	 * 已登录状态
	 */
	public static final int STATUS_YES = 1;
	/**
	 * 普通用户
	 */
	public static final int USER_TYPE_COMMON = 1;

	/**
	 * 管理员
	 */
	public static final int USER_TYPE_ORG = 2;

	/**
	 * 超级管理员
	 */
	public static final int USER_TYPE_SYS = 3;

	/**
	 * 普通角色
	 */
	public static final int ROLE_TYPE_COMMON = 1;

	/**
	 * 管理角色
	 */
	public static final int ROLE_TYPE_ORG = 2;

	/**
	 * 超级管理角色
	 */
	public static final int ROLE_TYPE_SYS = 3;

	// 功能管理常量
	/**
	 * 菜单
	 */
	public static final int FUNC_TYPE_MENU = 1;

	/**
	 * 按钮
	 */
	public static final int FUNC_TYPE_BUTTON = 2;

	/**
	 * 链接
	 */
	public static final int FUNC_TYPE_LINK = 3;

	/**
	 * 功能编号参数名称
	 */
	public static final String FUNC_PARAM_NAME = "fc";

	// 表格常量

	public static final String TABLE_LITERAL = "literal-";

	// 菜单常量
	/**
	 * 根节点编号
	 */
	// public static final String TREE_ROOT = "Root";

	// 个性化常量
	/**
	 * 菜单
	 */
	public static final String INDIVIDUATION_MEMU = "menu";

	/**
	 * 默认菜单类型
	 */
	public static final String INDIVIDUATION_MEMU_DEFAULT = "tree";

	/**
	 * 树型菜单
	 */
	public static final String INDIVIDUATION_MEMU_TREE = "tree";

	/**
	 * 折叠式菜单
	 */
	public static final String INDIVIDUATION_MEMU_ACCORDION = "accordion";

	/**
	 * 主题
	 */
	public static final String INDIVIDUATION_TOPIC = "topic";

	/**
	 * 默认主题
	 */
	public static final String INDIVIDUATION_TOPIC_DEFAULT = "blue";

	/**
	 * 显示模式
	 */
	public static final String INDIVIDUATION_MODE = "screenMode";

	/**
	 * 默认显示模式
	 */
	public static final String INDIVIDUATION_MODE_DEFAULT = "fullScreen";

	/**
	 * 全屏模式
	 */
	public static final String INDIVIDUATION_MODE_FULL_SCREEN = "fullScreen";

	/**
	 * 非全屏模式
	 */
	public static final String INDIVIDUATION_MODE_NO_FULL_SCREEN = "noFullScreen";

	/**
	 * 首页
	 */
	public static final String INDIVIDUATION_INDEX = "indexPage";

	/**
	 * 默认首页
	 */
	public static final String INDIVIDUATION_INDEX_DEFAULT = "_WELCOME_PAGE";

	/**
	 * 是否为打开窗口属性
	 */
	public static final String WINDOW_ISOPEN = "isOpenWin_";

	/**
	 * 系统类型,中心端
	 */
	public static final String SYS_TYPE_CENTER = "center";

	/**
	 * 系统类型,客户端
	 */
	public static final String SYS_TYPE_CLIENT = "client";

	private Constants() {

	}

}