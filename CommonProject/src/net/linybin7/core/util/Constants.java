package net.linybin7.core.util;

import java.util.HashMap;

/**
 * ��������
 * 
 * 
 * 
 */
public final class Constants {
	// key����
	/**
	 * sessionMapȫ�ֱ���
	 */
	public static HashMap sessionMap = new HashMap();
	
	/**
	 * �˵�����ȫ�ֱ���
	 */
	public static HashMap pubFuncMap = new HashMap();
	
	/**
	 * ����ͳ��ȫ�ֱ���
	 */
	public static HashMap funcMap = new HashMap();
	/**
	 * ��ʾ��Ϣkey
	 */
	public static final String KEY_MSG = "message_key";

	/**
	 * ������key
	 */
	public static final String KEY_VISITOR = "visitor_key";

	/**
	 * ϵͳ���ö���key
	 */
	public static final String KEY_SYSCFG = "syscfg_key";

	// ����Դ����
	/**
	 * ��������������Դ
	 */
	public static final int DS_TYPE_SERVER = 1;

	/**
	 * ��ͨ��������Դ
	 */
	public static final int DS_DYPE_COMMON = 2;

	// �û���ɫ����
	/**
	 * ����
	 */
	public static final int STOP_NO = 0;

	/**
	 * ͣ��
	 */
	public static final int STOP_YES = 1;
	/**
	 * δ��¼״̬
	 */
	public static final int STATUS_NO = 0;

	/**
	 * �ѵ�¼״̬
	 */
	public static final int STATUS_YES = 1;
	/**
	 * ��ͨ�û�
	 */
	public static final int USER_TYPE_COMMON = 1;

	/**
	 * ����Ա
	 */
	public static final int USER_TYPE_ORG = 2;

	/**
	 * ��������Ա
	 */
	public static final int USER_TYPE_SYS = 3;

	/**
	 * ��ͨ��ɫ
	 */
	public static final int ROLE_TYPE_COMMON = 1;

	/**
	 * �����ɫ
	 */
	public static final int ROLE_TYPE_ORG = 2;

	/**
	 * ���������ɫ
	 */
	public static final int ROLE_TYPE_SYS = 3;

	// ���ܹ�����
	/**
	 * �˵�
	 */
	public static final int FUNC_TYPE_MENU = 1;

	/**
	 * ��ť
	 */
	public static final int FUNC_TYPE_BUTTON = 2;

	/**
	 * ����
	 */
	public static final int FUNC_TYPE_LINK = 3;

	/**
	 * ���ܱ�Ų�������
	 */
	public static final String FUNC_PARAM_NAME = "fc";

	// �����

	public static final String TABLE_LITERAL = "literal-";

	// �˵�����
	/**
	 * ���ڵ���
	 */
	// public static final String TREE_ROOT = "Root";

	// ���Ի�����
	/**
	 * �˵�
	 */
	public static final String INDIVIDUATION_MEMU = "menu";

	/**
	 * Ĭ�ϲ˵�����
	 */
	public static final String INDIVIDUATION_MEMU_DEFAULT = "tree";

	/**
	 * ���Ͳ˵�
	 */
	public static final String INDIVIDUATION_MEMU_TREE = "tree";

	/**
	 * �۵�ʽ�˵�
	 */
	public static final String INDIVIDUATION_MEMU_ACCORDION = "accordion";

	/**
	 * ����
	 */
	public static final String INDIVIDUATION_TOPIC = "topic";

	/**
	 * Ĭ������
	 */
	public static final String INDIVIDUATION_TOPIC_DEFAULT = "blue";

	/**
	 * ��ʾģʽ
	 */
	public static final String INDIVIDUATION_MODE = "screenMode";

	/**
	 * Ĭ����ʾģʽ
	 */
	public static final String INDIVIDUATION_MODE_DEFAULT = "fullScreen";

	/**
	 * ȫ��ģʽ
	 */
	public static final String INDIVIDUATION_MODE_FULL_SCREEN = "fullScreen";

	/**
	 * ��ȫ��ģʽ
	 */
	public static final String INDIVIDUATION_MODE_NO_FULL_SCREEN = "noFullScreen";

	/**
	 * ��ҳ
	 */
	public static final String INDIVIDUATION_INDEX = "indexPage";

	/**
	 * Ĭ����ҳ
	 */
	public static final String INDIVIDUATION_INDEX_DEFAULT = "_WELCOME_PAGE";

	/**
	 * �Ƿ�Ϊ�򿪴�������
	 */
	public static final String WINDOW_ISOPEN = "isOpenWin_";

	/**
	 * ϵͳ����,���Ķ�
	 */
	public static final String SYS_TYPE_CENTER = "center";

	/**
	 * ϵͳ����,�ͻ���
	 */
	public static final String SYS_TYPE_CLIENT = "client";

	private Constants() {

	}

}