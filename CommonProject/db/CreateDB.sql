/*==============================================================*/
/* Table: SA_ORG                                                */
/*==============================================================*/
CREATE TABLE SA_ORG  (
   ID                   VARCHAR2(20)                    NOT NULL,
   PARENTID             VARCHAR2(20),
   ORGNAME              VARCHAR2(30)                    NOT NULL,
   ORGFULLNAME          VARCHAR2(100)                   NOT NULL,
   CHIEF                VARCHAR2(30),
   PHONE                VARCHAR2(20),
   MOBILE               VARCHAR2(20),
   EMAIL                VARCHAR2(40),
   DESCRIPT             VARCHAR2(2000),
   LEVELNO              INT                             NOT NULL,
   ORDERNO              VARCHAR2(6)                     NOT NULL,
   ISSTOP               INT                             NOT NULL,
   CONSTRAINT PK_SA_ORG PRIMARY KEY (ID)
);

COMMENT ON TABLE SA_ORG IS
'��������';

COMMENT ON COLUMN SA_ORG.ID IS
'������';

COMMENT ON COLUMN SA_ORG.PARENTID IS
'�ϼ����������';

COMMENT ON COLUMN SA_ORG.ORGNAME IS
'���������';

COMMENT ON COLUMN SA_ORG.ORGFULLNAME IS
'������ȫ��';

COMMENT ON COLUMN SA_ORG.CHIEF IS
'����������������';

COMMENT ON COLUMN SA_ORG.PHONE IS
'�����������˵绰';

COMMENT ON COLUMN SA_ORG.MOBILE IS
'�������������ֻ���';

COMMENT ON COLUMN SA_ORG.EMAIL IS
'�������������ʼ�';

COMMENT ON COLUMN SA_ORG.DESCRIPT IS
'����������';

COMMENT ON COLUMN SA_ORG.LEVELNO IS
'����';

COMMENT ON COLUMN SA_ORG.ORDERNO IS
'�����';

COMMENT ON COLUMN SA_ORG.ISSTOP IS
'�Ƿ�ͣ��
1��ͣ�ã�
0�����ã�';
alter table SA_ORG NOLOGGING;


/*==============================================================*/
/* Table: SA_USER_ORG                                           */
/*==============================================================*/
CREATE TABLE SA_USER_ORG  (
   USERCODE             VARCHAR2(20)                    NOT NULL,
   ID                   VARCHAR2(20)                    NOT NULL,
   CONSTRAINT PK_SA_USER_ORG PRIMARY KEY (USERCODE, ID)
);

COMMENT ON TABLE SA_USER_ORG IS
'�û���������ϵ��';

COMMENT ON COLUMN SA_USER_ORG.USERCODE IS
'�û����';

COMMENT ON COLUMN SA_USER_ORG.ID IS
'������';

alter table SA_USER_ORG NOLOGGING;



/*==============================================================*/
/* Table: SA_SYS                                                */
/*==============================================================*/
CREATE TABLE SA_SYS  (
   ID                   VARCHAR(20)                     NOT NULL,
   SYSNAME              VARCHAR(80)                     NOT NULL,
   CONTEXT              VARCHAR(20)                     NOT NULL,
   ORDERNO              INT
);

COMMENT ON TABLE SA_SYS IS
'ϵͳ��';

COMMENT ON COLUMN SA_SYS.ID IS
'ϵͳID';

COMMENT ON COLUMN SA_SYS.SYSNAME IS
'ϵͳ����';

COMMENT ON COLUMN SA_SYS.CONTEXT IS
'������';

COMMENT ON COLUMN SA_SYS.ORDERNO IS
'�����';


insert into sa_sys (ID, SYSNAME, CONTEXT, ORDERNO)
values ('frame', 'FreeWork', 'project', 1);



/*==============================================================*/
/* Table: SYS_INDIVIDUATION                                     */
/*==============================================================*/
CREATE TABLE SYS_INDIVIDUATION  (
   SYSID                VARCHAR2(20)                    NOT NULL,
   USERCODE             VARCHAR2(20)                    NOT NULL,
   USERNAME             VARCHAR2(20)                    NOT NULL,
   SETCODE              VARCHAR2(50)                    NOT NULL,
   SETNAME              VARCHAR2(200),
   KIND                 VARCHAR2(200),
   SETTING              VARCHAR2(4000),
   DEFAULT_VALUE        VARCHAR2(4000),
   EXPRESSION           VARCHAR2(200),
   DES                  VARCHAR2(200),
   CONSTRAINT PK_SYS_INDIVIDUATION PRIMARY KEY (SYSID, USERCODE, SETCODE)
);

COMMENT ON TABLE SYS_INDIVIDUATION IS
'ϵͳ���ñ�';

COMMENT ON COLUMN SYS_INDIVIDUATION.SYSID IS
'ϵͳID';

COMMENT ON COLUMN SYS_INDIVIDUATION.USERCODE IS
'�û����';

COMMENT ON COLUMN SYS_INDIVIDUATION.USERNAME IS
'�û�����';

COMMENT ON COLUMN SYS_INDIVIDUATION.KIND IS
'����';

COMMENT ON COLUMN SYS_INDIVIDUATION.SETTING IS
'�趨ֵ';

COMMENT ON COLUMN SYS_INDIVIDUATION.DEFAULT_VALUE IS
'Ĭ��ֵ';

COMMENT ON COLUMN SYS_INDIVIDUATION.EXPRESSION IS
'���ʽ';

COMMENT ON COLUMN SYS_INDIVIDUATION.DES IS
'��ע';


alter table SYS_INDIVIDUATION NOLOGGING;



/*==============================================================*/
/* Table: SA_FUNCTION_STAT                                      */
/*==============================================================*/
CREATE TABLE SA_FUNCTION_STAT  (
   SYSID                VARCHAR2(20)                    NOT NULL,
   PARENTCODE           VARCHAR2(40)                    NOT NULL,
   FUNCCODE             VARCHAR2(40)                    NOT NULL,
   CNT                  INT,
   CONSTRAINT PK_SA_FUNCTION_STAT PRIMARY KEY (SYSID, PARENTCODE, FUNCCODE)
);

COMMENT ON TABLE SA_FUNCTION_STAT IS
'ϵͳ����ʹ��ͳ�Ʊ�';

COMMENT ON COLUMN SA_FUNCTION_STAT.SYSID IS
'ϵͳID';

COMMENT ON COLUMN SA_FUNCTION_STAT.PARENTCODE IS
'�ϼ����ܱ��';

COMMENT ON COLUMN SA_FUNCTION_STAT.FUNCCODE IS
'���ܱ��';

COMMENT ON COLUMN SA_FUNCTION_STAT.CNT IS
'ʹ�ô���';

alter table SA_FUNCTION_STAT NOLOGGING;


/*==============================================================*/
/* Table: SA_ROLE                                               */
/*==============================================================*/
CREATE TABLE SA_ROLE  (
   ROLECODE             VARCHAR(20)                     NOT NULL,
   ORGID                VARCHAR(20),
   ROLENAME             VARCHAR(20)                     NOT NULL,
   ISSTOP               INT                             NOT NULL,
   ROLEPROP             INT                             NOT NULL,
   DESCRIPT             VARCHAR(200),
   CONSTRAINT PK_SA_ROLE PRIMARY KEY (ROLECODE)
);

COMMENT ON TABLE SA_ROLE IS
'��ɫ��';

COMMENT ON COLUMN SA_ROLE.ROLECODE IS
'��ɫ���';

COMMENT ON COLUMN SA_ROLE.ORGID IS
'���ű��';

COMMENT ON COLUMN SA_ROLE.ROLENAME IS
'��ɫ����';

COMMENT ON COLUMN SA_ROLE.ISSTOP IS
'�Ƿ�ͣ��
1��ͣ�ã�
0�����ã�';

COMMENT ON COLUMN SA_ROLE.ROLEPROP IS
'��ɫ����
1����ͨ��ɫ��
2������Ա��ɫ��
3����������Ա��ɫ��';

COMMENT ON COLUMN SA_ROLE.DESCRIPT IS
'����';

insert into sa_role (ROLECODE, ORGID, ROLENAME, ISSTOP, ROLEPROP, DESCRIPT)
values ('admin', '', '���������ɫ', 0, 3, ''); 

alter table SA_ROLE NOLOGGING;


/*==============================================================*/
/* Table: SYS_ROLE_FUNCTION                                     */
/*==============================================================*/
CREATE TABLE SYS_ROLE_FUNCTION  (
   ROLECODE             VARCHAR2(20)                    NOT NULL,
   FUNCCODE             VARCHAR2(40)                    NOT NULL,
   CONSTRAINT PK_SYS_ROLE_FUNCTION PRIMARY KEY (ROLECODE, FUNCCODE)
);

COMMENT ON TABLE SYS_ROLE_FUNCTION IS
'��ɫ���ܹ�ϵ��';

COMMENT ON COLUMN SYS_ROLE_FUNCTION.ROLECODE IS
'��ɫ���';

COMMENT ON COLUMN SYS_ROLE_FUNCTION.FUNCCODE IS
'���ܱ��';

alter table SYS_ROLE_FUNCTION NOLOGGING;


insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '01');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '0101');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010101');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010102');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010103');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010104');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010105');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010106');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '0102');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010201');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010202');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010203');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010204');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010205');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010206');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '0103');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010301');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010302');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010303');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010304');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010305');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010306');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '0104');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010401');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010402');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010403');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010404');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010405');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010406');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '010407');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '0105');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', '0106');

insert into sys_role_function (ROLECODE, FUNCCODE)
values ('admin', 'frame');

 
/*==============================================================*/
/* Table: SA_USER                                               */
/*==============================================================*/
CREATE TABLE SA_USER  (
   USERCODE             VARCHAR2(20)                    NOT NULL,
   USERNAME             VARCHAR2(20)                    NOT NULL,
   ORGID                VARCHAR2(20),
   PASSWD               VARCHAR2(100)                   NOT NULL,
   TEL                  VARCHAR2(40),
   MOBILE               VARCHAR2(11),
   EMAIL                VARCHAR2(40),
   ISSTOP               INT                             NOT NULL,
   USERPROP             INT                             NOT NULL,
   DESCRIPT             VARCHAR2(200),
   TRUSTID              VARCHAR2(50),
   LIMITTYPE            INT                            DEFAULT 0,
   PERMITNUM            INT,
   LOGINNUM             INT,
   EFFECTTIME           DATE,
   DAYS                 INT,
   NUMS                 INT                            DEFAULT 0,
   ONTIME               INT                            DEFAULT 0,
   ISPASSWD             INT                            DEFAULT 0,
   CONSTRAINT PK_SA_USER PRIMARY KEY (USERCODE)
);

COMMENT ON TABLE SA_USER IS
'�û���';

COMMENT ON COLUMN SA_USER.USERCODE IS
'�û����';

COMMENT ON COLUMN SA_USER.USERNAME IS
'�û�����';

COMMENT ON COLUMN SA_USER.ORGID IS
'��ǰ������';

COMMENT ON COLUMN SA_USER.PASSWD IS
'����';

COMMENT ON COLUMN SA_USER.TEL IS
'�绰';

COMMENT ON COLUMN SA_USER.MOBILE IS
'�ֻ�����';

COMMENT ON COLUMN SA_USER.EMAIL IS
'�����ʼ�';

COMMENT ON COLUMN SA_USER.ISSTOP IS
'�Ƿ�ͣ��
1��ͣ�ã�
0�����ã�';

COMMENT ON COLUMN SA_USER.USERPROP IS
'�û�����
1����ͨ�û���
2������Ա��
3����������Ա��';

COMMENT ON COLUMN SA_USER.DESCRIPT IS
'����';

COMMENT ON COLUMN SA_USER.TRUSTID IS
'��֤ID';

COMMENT ON COLUMN SA_USER.LIMITTYPE IS
'ʹ������
0�������ƣ�
1����¼�������ƣ�
2��ʹ��ʱ�����ƣ�';

COMMENT ON COLUMN SA_USER.PERMITNUM IS
'�ɵ�¼����';

COMMENT ON COLUMN SA_USER.LOGINNUM IS
'�ѵ�¼����';

COMMENT ON COLUMN SA_USER.EFFECTTIME IS
'��Чʱ��';

COMMENT ON COLUMN SA_USER.DAYS IS
'��ʹ������';

COMMENT ON COLUMN SA_USER.NUMS IS
'��¼�ܴ���';

COMMENT ON COLUMN SA_USER.ONTIME IS
'����ʱ��';

COMMENT ON COLUMN SA_USER.ISPASSWD IS
'�Ƿ��޸Ĺ�����';


alter table SA_USER NOLOGGING;

insert into sa_user (USERCODE, ORGID, USERNAME, PASSWD, TEL, MOBILE, EMAIL, ISSTOP, USERPROP, DESCRIPT, TRUSTID, LIMITTYPE, PERMITNUM, LOGINNUM, EFFECTTIME, DAYS)
values ('admin', null, 'admin', '21232F297A57A5A743894A0E4A801FC3', '', '', '', 0, 3, '����Ա', '', 0, 5, 6, to_date('23-11-2010', 'dd-mm-yyyy'), 5);




/*==============================================================*/
/* Table: SYS_USER_ROLE                                         */
/*==============================================================*/
CREATE TABLE SYS_USER_ROLE  (
   USERCODE             VARCHAR2(20)                    NOT NULL,
   ROLECODE             VARCHAR2(20)                    NOT NULL,
   CONSTRAINT PK_SYS_USER_ROLE PRIMARY KEY (USERCODE, ROLECODE)
);

COMMENT ON TABLE SYS_USER_ROLE IS
'�û���ɫ��ϵ��';

COMMENT ON COLUMN SYS_USER_ROLE.USERCODE IS
'�û����';

COMMENT ON COLUMN SYS_USER_ROLE.ROLECODE IS
'��ɫ���';

alter table SYS_USER_ROLE NOLOGGING;

insert into sys_user_role (USERCODE, ROLECODE)
values ('admin', 'admin');



/*==============================================================*/
/* Table: SA_LOG                                                */
/*==============================================================*/
CREATE TABLE SA_LOG  (
   LEVNO                VARCHAR2(100),
   USERCODE             VARCHAR2(20) ,
   ADDR                 VARCHAR2(100),
   OPERATETIME          DATE,
   MOUDLE               VARCHAR2(200),
   MSG                  VARCHAR2(2000)
);


alter table SA_LOG NOLOGGING;


/*==============================================================*/
/* Table: SA_ERROR_LOG                                          */
/*==============================================================*/
CREATE TABLE SA_ERROR_LOG  (
   ID                   VARCHAR2(100)                   NOT NULL,
   MOUDLE               VARCHAR2(200),
   ERROR_CLASS          VARCHAR2(300),
   MSG                  VARCHAR2(2000),
   USERNAME             VARCHAR2(20)                    NOT NULL,
   USERCODE             VARCHAR2(20)                    NOT NULL,
   OPERATETIME          DATE,
   CONSTRAINT PK_SA_ERROR_LOG PRIMARY KEY (ID)
);

COMMENT ON TABLE SA_ERROR_LOG IS
'�쳣��־';

COMMENT ON COLUMN SA_ERROR_LOG.ID IS
'��־ID';

COMMENT ON COLUMN SA_ERROR_LOG.MOUDLE IS
'����ģ��';

COMMENT ON COLUMN SA_ERROR_LOG.ERROR_CLASS IS
'��
�����쳣���࣬������·����������������������';

COMMENT ON COLUMN SA_ERROR_LOG.MSG IS
'�쳣��Ϣ';

COMMENT ON COLUMN SA_ERROR_LOG.USERNAME IS
'�û�����';

COMMENT ON COLUMN SA_ERROR_LOG.USERCODE IS
'�û����';

COMMENT ON COLUMN SA_ERROR_LOG.OPERATETIME IS
'ʱ��';
alter table SA_ERROR_LOG NOLOGGING;




/*==============================================================*/
/* Table: DATAMGR                                               */
/*==============================================================*/
CREATE TABLE DATAMGR  (
   TABCODE              VARCHAR2(50)                    NOT NULL,
   TABNAME              VARCHAR2(200),
   TOOLS                VARCHAR2(50),
   ORGID                VARCHAR2(20),
   USERCODE             VARCHAR2(20),
   USERNAME             VARCHAR2(20),
   CREATETIME           DATE,
   USERCODE2            VARCHAR2(20),
   USERNAME2            VARCHAR2(20),
   IMPOTTIME            DATE,
   CONSTRAINT PK_DATAMGR PRIMARY KEY (TABCODE)
);

COMMENT ON TABLE DATAMGR IS
'���ݹ����';

COMMENT ON COLUMN DATAMGR.TABCODE IS
'����';

COMMENT ON COLUMN DATAMGR.TABNAME IS
'��������';

COMMENT ON COLUMN DATAMGR.TOOLS IS
'���߷���';

COMMENT ON COLUMN DATAMGR.ORGID IS
'���ű��';

COMMENT ON COLUMN DATAMGR.USERCODE IS
'�û����';

COMMENT ON COLUMN DATAMGR.USERNAME IS
'�û�����';

COMMENT ON COLUMN DATAMGR.CREATETIME IS
'����ʱ��';

COMMENT ON COLUMN DATAMGR.USERCODE2 IS
'�û����';

COMMENT ON COLUMN DATAMGR.USERNAME2 IS
'�û�����';

COMMENT ON COLUMN DATAMGR.IMPOTTIME IS
'����ʱ��';

alter table DATAMGR NOLOGGING; 

/*==============================================================*/
/* Table: TASK                                                  */
/*==============================================================*/
CREATE TABLE TASK  (
   TID                  VARCHAR2(20)                    NOT NULL,
   NAME                 VARCHAR2(200),
   TYPE                 VARCHAR2(50),
   STATE                VARCHAR2(20),
   BATCH                VARCHAR2(100),
   FILENAME             VARCHAR2(500),
   FILESIZE             NUMBER(12),
   STARTTIME            TIMESTAMP,
   ENDTIME              TIMESTAMP,
   USE_TIME             NUMBER(10),
   DES                  VARCHAR2(2000),
   COMPANY              VARCHAR2(200),
   ORGID                VARCHAR2(20),
   USERCODE             VARCHAR2(20),
   TOOLCODE             VARCHAR2(20),
   CONSTRAINT PK_TASK PRIMARY KEY (TID)
);

COMMENT ON TABLE TASK IS
'��������';

COMMENT ON COLUMN TASK.TID IS
'����ID';

COMMENT ON COLUMN TASK.NAME IS
'��������';

COMMENT ON COLUMN TASK.TYPE IS
'���ͣ�FAS;MRR;STS_APG;STS_IOG;CTR;MTR;NCS;';

COMMENT ON COLUMN TASK.STATE IS
'״̬
1����ʼ����
2������У�
3���ȴ���⣻
4�����ɹ���
0�����ʧ�ܣ�';

COMMENT ON COLUMN TASK.BATCH IS
'����';

COMMENT ON COLUMN TASK.FILENAME IS
'�ļ�����';

COMMENT ON COLUMN TASK.FILESIZE IS
'�ļ���С';

COMMENT ON COLUMN TASK.STARTTIME IS
'����ʱ��';

COMMENT ON COLUMN TASK.ENDTIME IS
'����ʱ��';

COMMENT ON COLUMN TASK.USE_TIME IS
'��ʱ';

COMMENT ON COLUMN TASK.DES IS
'��Ϣ';

COMMENT ON COLUMN TASK.COMPANY IS
'����:ericsson;huawei;nokia;alcatel;';

COMMENT ON COLUMN TASK.ORGID IS
'�������';

COMMENT ON COLUMN TASK.USERCODE IS
'�û����';

COMMENT ON COLUMN TASK.TOOLCODE IS
'���߱��';

alter table TASK NOLOGGING;


/*==============================================================*/
/* Table: MAP_CACHE_INFO                                        */
/*==============================================================*/
CREATE TABLE MAP_CACHE_INFO  (
   ORGID                VARCHAR2(20)                    NOT NULL,
   LAYER_NAME           VARCHAR2(100)                   NOT NULL,
   F_LEFT               FLOAT,
   BOTTOM               FLOAT,
   F_RIGHT              FLOAT,
   TOP                  FLOAT,
   TIMES                NUMBER,
   LAYER_ORDER          NUMBER                         DEFAULT 0,
   LAYER_NAME_CN        VARCHAR2(500),
   VISIBILITY           NUMBER                         DEFAULT 1,
   BASELAYER            NUMBER                         DEFAULT 0,
   CREATE_DATE          DATE,
   TOOLCODE             VARCHAR2(20)                    NOT NULL,
   CONSTRAINT PK_MAP_CACHE_INFO PRIMARY KEY (ORGID, LAYER_NAME, TOOLCODE)
);

COMMENT ON TABLE MAP_CACHE_INFO IS
'��ͼ������Ϣ��';

COMMENT ON COLUMN MAP_CACHE_INFO.ORGID IS
'�������';

COMMENT ON COLUMN MAP_CACHE_INFO.TIMES IS
'����ͼ�����(��Ϊ���������ı�־)';

COMMENT ON COLUMN MAP_CACHE_INFO.LAYER_ORDER IS
'ͼ������';

COMMENT ON COLUMN MAP_CACHE_INFO.LAYER_NAME_CN IS
'ͼ����������';

COMMENT ON COLUMN MAP_CACHE_INFO.VISIBILITY IS
'�Ƿ���ʾ';

COMMENT ON COLUMN MAP_CACHE_INFO.CREATE_DATE IS
'����ʱ��';

COMMENT ON COLUMN MAP_CACHE_INFO.TOOLCODE IS
'���߱��
T������δ��ŵ�ϵͳ����Ƶר�ҡ����и��ŵȵ�������ϵͳ����
T0������ƽ̨�������û�ͨ����ͬͼ�㣩��
T1�������Ż���
T2��MRR������
T3��FAS������
T4������ṹ��
T5�������Ż���';

alter table MAP_CACHE_INFO NOLOGGING; 

insert into map_cache_info (ORGID, LAYER_NAME, F_LEFT, BOTTOM, F_RIGHT, TOP, TIMES, LAYER_ORDER, LAYER_NAME_CN, VISIBILITY, BASELAYER, CREATE_DATE, TOOLCODE)
values ('ALL_USER_LAYER_', 'amap', 0, 0, 0, 0, 1, 0, '�ߵµ�ͼ', 1, 0, to_date('04-06-2012 09:54:39', 'dd-mm-yyyy hh24:mi:ss'), 'T');

commit;
/*==============================================================*/
/* Table: SA_FUNCTION                                           */
/*==============================================================*/
CREATE TABLE SA_FUNCTION  (
   FUNCCODE             VARCHAR2(40)                    NOT NULL,
   PARENTCODE           VARCHAR2(40),
   FUNCNAME             VARCHAR2(80)                    NOT NULL,
   FUNCPROP             INT                             NOT NULL,
   LEVELNO              INT                             NOT NULL,
   ORDERNO              INT                             NOT NULL,
   ISSTOP               INT                             NOT NULL,
   LINKPAGE             VARCHAR2(2000),
   TITLE                VARCHAR2(100),
   ICON                 VARCHAR2(100),
   DESCRIPT             VARCHAR2(200),
   SYSID                VARCHAR2(20)                    NOT NULL,
   CONSTRAINT PK_SA_FUNCTION PRIMARY KEY (FUNCCODE)
);

COMMENT ON TABLE SA_FUNCTION IS
'ϵͳ���ܱ�';

COMMENT ON COLUMN SA_FUNCTION.FUNCCODE IS
'���ܱ��';

COMMENT ON COLUMN SA_FUNCTION.PARENTCODE IS
'�ϼ����ܱ��';

COMMENT ON COLUMN SA_FUNCTION.FUNCNAME IS
'��������';

COMMENT ON COLUMN SA_FUNCTION.FUNCPROP IS
'��������
1:�˵���
2:��ť��
3:���ӣ�';

COMMENT ON COLUMN SA_FUNCTION.LEVELNO IS
'���ܼ���';

COMMENT ON COLUMN SA_FUNCTION.ORDERNO IS
'�����';

COMMENT ON COLUMN SA_FUNCTION.ISSTOP IS
'�Ƿ�ͣ��
1��ͣ�ã�
0�����ã�';

COMMENT ON COLUMN SA_FUNCTION.LINKPAGE IS
'����';

COMMENT ON COLUMN SA_FUNCTION.TITLE IS
'��ʾ��Ϣ';

COMMENT ON COLUMN SA_FUNCTION.ICON IS
'ͼ��';

COMMENT ON COLUMN SA_FUNCTION.DESCRIPT IS
'����';

COMMENT ON COLUMN SA_FUNCTION.SYSID IS
'ϵͳID';

alter table SA_FUNCTION NOLOGGING;

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('0101', '01', '������', 1, 2, 1, 0, '/org/orgManager.do?action=index', '������', 'tb2017', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('0102', '01', '�û�����', 1, 2, 2, 0, '/user/userManager.do?action=queryUser', '�û�����', 'tb20userset', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('0103', '01', '��ɫ����', 1, 2, 3, 0, '/role/roleManager.do?action=queryRole', '��ɫ����', 'tb20roleset', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('0104', '01', '���ܹ���', 1, 2, 4, 0, '/func/funcManager.do?action=index', '���ܹ���', 'tb2010', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('0105', '01', '����ͳ��', 1, 2, 5, 0, '/func/statManager.do?action=main', '', 'tb2062', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('0106', '01', 'ϵͳ��־', 1, 2, 6, 0, '/log/logManager.do?action=main', '', 'tb2059', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010101', '0101', '����', 2, 3, 1, 0, 'mainAdd();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010102', '0101', '�޸�', 2, 3, 2, 0, 'mainEdit();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010103', '0101', 'ɾ��', 2, 3, 3, 0, 'mainDelete();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010104', '0101', '�鿴', 3, 3, 4, 0, '', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010105', '0101', '����', 2, 3, 5, 0, 'mainStart();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010106', '0101', 'ͣ��', 2, 3, 6, 0, 'mainStop();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010201', '0102', '����', 2, 3, 1, 0, 'mainAdd();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010202', '0102', '�޸�', 2, 3, 2, 0, 'mainEdit();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010203', '0102', 'ɾ��', 2, 3, 3, 0, 'mainDelete();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010204', '0102', '�鿴', 3, 3, 4, 0, '', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010205', '0102', '����', 2, 3, 5, 0, 'mainStart();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010206', '0102', 'ͣ��', 2, 3, 6, 0, 'mainStop();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010301', '0103', '����', 2, 3, 1, 0, 'mainAdd();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010302', '0103', '�޸�', 2, 3, 2, 0, 'mainEdit();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010303', '0103', 'ɾ��', 2, 3, 3, 0, 'mainDelete();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010304', '0103', '�鿴', 3, 3, 4, 0, '', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010305', '0103', '����', 2, 3, 5, 0, 'mainStart();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010306', '0103', 'ͣ��', 2, 3, 6, 0, 'mainStop();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010401', '0104', '����', 2, 3, 1, 0, 'mainAdd();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010402', '0104', '�޸�', 2, 3, 2, 0, 'mainEdit();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010403', '0104', 'ɾ��', 2, 3, 3, 0, 'mainDelete();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010404', '0104', '�鿴', 3, 3, 4, 0, '', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010405', '0104', '����', 2, 3, 5, 0, 'mainStart();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010406', '0104', 'ͣ��', 2, 3, 6, 0, 'mainStop();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010407', '0104', '����˳��', 2, 3, 7, 0, 'mainSaveOrder();', '', '', '', 'frame');


insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('WEIBO', 'frame', '΢��Ӧ��ϵͳ', 1, 1, 0, 0, '', '΢��Ӧ��ϵͳ', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('WEIBO1', 'WEIBO', '΢�������ϵͳ', 1, 1,1, 0, '/weibo/trends.do?action=main', '', 'tb2042', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('WEIBO9', 'WEIBO', '���ݿ��ʼ��', 1, 9, 9, 0, '/init/dbInit.do?action=dbInitMain&'||'sysName=T1', '', 'tb2015', '', 'frame');



insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('frame', '', 'FreeWork����', 1, 0, 0, 0, '', 'FreeWork����', '', '', 'frame');



commit;