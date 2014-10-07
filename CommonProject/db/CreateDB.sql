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
'工作区表';

COMMENT ON COLUMN SA_ORG.ID IS
'工作区';

COMMENT ON COLUMN SA_ORG.PARENTID IS
'上级工作区编号';

COMMENT ON COLUMN SA_ORG.ORGNAME IS
'工作区简称';

COMMENT ON COLUMN SA_ORG.ORGFULLNAME IS
'工作区全称';

COMMENT ON COLUMN SA_ORG.CHIEF IS
'工作区负责人姓名';

COMMENT ON COLUMN SA_ORG.PHONE IS
'工作区负责人电话';

COMMENT ON COLUMN SA_ORG.MOBILE IS
'工作区负责人手机号';

COMMENT ON COLUMN SA_ORG.EMAIL IS
'工作区负责人邮件';

COMMENT ON COLUMN SA_ORG.DESCRIPT IS
'工作区介绍';

COMMENT ON COLUMN SA_ORG.LEVELNO IS
'级别';

COMMENT ON COLUMN SA_ORG.ORDERNO IS
'排序号';

COMMENT ON COLUMN SA_ORG.ISSTOP IS
'是否停用
1：停用；
0：启用；';
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
'用户工作区关系表';

COMMENT ON COLUMN SA_USER_ORG.USERCODE IS
'用户编号';

COMMENT ON COLUMN SA_USER_ORG.ID IS
'工作区';

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
'系统表';

COMMENT ON COLUMN SA_SYS.ID IS
'系统ID';

COMMENT ON COLUMN SA_SYS.SYSNAME IS
'系统名称';

COMMENT ON COLUMN SA_SYS.CONTEXT IS
'上下文';

COMMENT ON COLUMN SA_SYS.ORDERNO IS
'排序号';


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
'系统设置表';

COMMENT ON COLUMN SYS_INDIVIDUATION.SYSID IS
'系统ID';

COMMENT ON COLUMN SYS_INDIVIDUATION.USERCODE IS
'用户编号';

COMMENT ON COLUMN SYS_INDIVIDUATION.USERNAME IS
'用户名称';

COMMENT ON COLUMN SYS_INDIVIDUATION.KIND IS
'类型';

COMMENT ON COLUMN SYS_INDIVIDUATION.SETTING IS
'设定值';

COMMENT ON COLUMN SYS_INDIVIDUATION.DEFAULT_VALUE IS
'默认值';

COMMENT ON COLUMN SYS_INDIVIDUATION.EXPRESSION IS
'表达式';

COMMENT ON COLUMN SYS_INDIVIDUATION.DES IS
'备注';


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
'系统功能使用统计表';

COMMENT ON COLUMN SA_FUNCTION_STAT.SYSID IS
'系统ID';

COMMENT ON COLUMN SA_FUNCTION_STAT.PARENTCODE IS
'上级功能编号';

COMMENT ON COLUMN SA_FUNCTION_STAT.FUNCCODE IS
'功能编号';

COMMENT ON COLUMN SA_FUNCTION_STAT.CNT IS
'使用次数';

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
'角色表';

COMMENT ON COLUMN SA_ROLE.ROLECODE IS
'角色编号';

COMMENT ON COLUMN SA_ROLE.ORGID IS
'部门编号';

COMMENT ON COLUMN SA_ROLE.ROLENAME IS
'角色名称';

COMMENT ON COLUMN SA_ROLE.ISSTOP IS
'是否停用
1：停用；
0：启用；';

COMMENT ON COLUMN SA_ROLE.ROLEPROP IS
'角色类型
1：普通角色；
2：管理员角色；
3：超级管理员角色；';

COMMENT ON COLUMN SA_ROLE.DESCRIPT IS
'描述';

insert into sa_role (ROLECODE, ORGID, ROLENAME, ISSTOP, ROLEPROP, DESCRIPT)
values ('admin', '', '超级管理角色', 0, 3, ''); 

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
'角色功能关系表';

COMMENT ON COLUMN SYS_ROLE_FUNCTION.ROLECODE IS
'角色编号';

COMMENT ON COLUMN SYS_ROLE_FUNCTION.FUNCCODE IS
'功能编号';

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
'用户表';

COMMENT ON COLUMN SA_USER.USERCODE IS
'用户编号';

COMMENT ON COLUMN SA_USER.USERNAME IS
'用户名称';

COMMENT ON COLUMN SA_USER.ORGID IS
'当前工作区';

COMMENT ON COLUMN SA_USER.PASSWD IS
'密码';

COMMENT ON COLUMN SA_USER.TEL IS
'电话';

COMMENT ON COLUMN SA_USER.MOBILE IS
'手机号码';

COMMENT ON COLUMN SA_USER.EMAIL IS
'电子邮件';

COMMENT ON COLUMN SA_USER.ISSTOP IS
'是否停用
1：停用；
0：启用；';

COMMENT ON COLUMN SA_USER.USERPROP IS
'用户类型
1：普通用户；
2：管理员；
3：超级管理员；';

COMMENT ON COLUMN SA_USER.DESCRIPT IS
'描述';

COMMENT ON COLUMN SA_USER.TRUSTID IS
'认证ID';

COMMENT ON COLUMN SA_USER.LIMITTYPE IS
'使用类型
0：无限制；
1：登录次数限制；
2：使用时限限制；';

COMMENT ON COLUMN SA_USER.PERMITNUM IS
'可登录次数';

COMMENT ON COLUMN SA_USER.LOGINNUM IS
'已登录次数';

COMMENT ON COLUMN SA_USER.EFFECTTIME IS
'生效时间';

COMMENT ON COLUMN SA_USER.DAYS IS
'可使用天数';

COMMENT ON COLUMN SA_USER.NUMS IS
'登录总次数';

COMMENT ON COLUMN SA_USER.ONTIME IS
'在线时长';

COMMENT ON COLUMN SA_USER.ISPASSWD IS
'是否修改过密码';


alter table SA_USER NOLOGGING;

insert into sa_user (USERCODE, ORGID, USERNAME, PASSWD, TEL, MOBILE, EMAIL, ISSTOP, USERPROP, DESCRIPT, TRUSTID, LIMITTYPE, PERMITNUM, LOGINNUM, EFFECTTIME, DAYS)
values ('admin', null, 'admin', '21232F297A57A5A743894A0E4A801FC3', '', '', '', 0, 3, '管理员', '', 0, 5, 6, to_date('23-11-2010', 'dd-mm-yyyy'), 5);




/*==============================================================*/
/* Table: SYS_USER_ROLE                                         */
/*==============================================================*/
CREATE TABLE SYS_USER_ROLE  (
   USERCODE             VARCHAR2(20)                    NOT NULL,
   ROLECODE             VARCHAR2(20)                    NOT NULL,
   CONSTRAINT PK_SYS_USER_ROLE PRIMARY KEY (USERCODE, ROLECODE)
);

COMMENT ON TABLE SYS_USER_ROLE IS
'用户角色关系表';

COMMENT ON COLUMN SYS_USER_ROLE.USERCODE IS
'用户编号';

COMMENT ON COLUMN SYS_USER_ROLE.ROLECODE IS
'角色编号';

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
'异常日志';

COMMENT ON COLUMN SA_ERROR_LOG.ID IS
'日志ID';

COMMENT ON COLUMN SA_ERROR_LOG.MOUDLE IS
'功能模块';

COMMENT ON COLUMN SA_ERROR_LOG.ERROR_CLASS IS
'类
发生异常的类，包括包路径、出错方法、出错行数。';

COMMENT ON COLUMN SA_ERROR_LOG.MSG IS
'异常信息';

COMMENT ON COLUMN SA_ERROR_LOG.USERNAME IS
'用户名称';

COMMENT ON COLUMN SA_ERROR_LOG.USERCODE IS
'用户编号';

COMMENT ON COLUMN SA_ERROR_LOG.OPERATETIME IS
'时间';
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
'数据管理表';

COMMENT ON COLUMN DATAMGR.TABCODE IS
'表名';

COMMENT ON COLUMN DATAMGR.TABNAME IS
'表中文名';

COMMENT ON COLUMN DATAMGR.TOOLS IS
'工具分类';

COMMENT ON COLUMN DATAMGR.ORGID IS
'部门编号';

COMMENT ON COLUMN DATAMGR.USERCODE IS
'用户编号';

COMMENT ON COLUMN DATAMGR.USERNAME IS
'用户名称';

COMMENT ON COLUMN DATAMGR.CREATETIME IS
'创建时间';

COMMENT ON COLUMN DATAMGR.USERCODE2 IS
'用户编号';

COMMENT ON COLUMN DATAMGR.USERNAME2 IS
'用户名称';

COMMENT ON COLUMN DATAMGR.IMPOTTIME IS
'数据时间';

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
'入库任务表';

COMMENT ON COLUMN TASK.TID IS
'任务ID';

COMMENT ON COLUMN TASK.NAME IS
'任务名称';

COMMENT ON COLUMN TASK.TYPE IS
'类型：FAS;MRR;STS_APG;STS_IOG;CTR;MTR;NCS;';

COMMENT ON COLUMN TASK.STATE IS
'状态
1：初始化；
2：入库中；
3：等待入库；
4：入库成功；
0：入库失败；';

COMMENT ON COLUMN TASK.BATCH IS
'批次';

COMMENT ON COLUMN TASK.FILENAME IS
'文件名称';

COMMENT ON COLUMN TASK.FILESIZE IS
'文件大小';

COMMENT ON COLUMN TASK.STARTTIME IS
'创建时间';

COMMENT ON COLUMN TASK.ENDTIME IS
'结束时间';

COMMENT ON COLUMN TASK.USE_TIME IS
'耗时';

COMMENT ON COLUMN TASK.DES IS
'信息';

COMMENT ON COLUMN TASK.COMPANY IS
'厂商:ericsson;huawei;nokia;alcatel;';

COMMENT ON COLUMN TASK.ORGID IS
'机构编号';

COMMENT ON COLUMN TASK.USERCODE IS
'用户编号';

COMMENT ON COLUMN TASK.TOOLCODE IS
'工具编号';

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
'地图缓存信息表';

COMMENT ON COLUMN MAP_CACHE_INFO.ORGID IS
'机构编号';

COMMENT ON COLUMN MAP_CACHE_INFO.TIMES IS
'生成图层次数(作为浏览器缓存的标志)';

COMMENT ON COLUMN MAP_CACHE_INFO.LAYER_ORDER IS
'图层排序';

COMMENT ON COLUMN MAP_CACHE_INFO.LAYER_NAME_CN IS
'图层中文名称';

COMMENT ON COLUMN MAP_CACHE_INFO.VISIBILITY IS
'是否显示';

COMMENT ON COLUMN MAP_CACHE_INFO.CREATE_DATE IS
'创建时间';

COMMENT ON COLUMN MAP_CACHE_INFO.TOOLCODE IS
'工具编号
T：其他未编号的系统（易频专家、上行干扰等单独运行系统）；
T0：管理平台（所有用户通用相同图层）；
T1：邻区优化；
T2：MRR分析；
T3：FAS分析；
T4：网络结构；
T5：智能优化；';

alter table MAP_CACHE_INFO NOLOGGING; 

insert into map_cache_info (ORGID, LAYER_NAME, F_LEFT, BOTTOM, F_RIGHT, TOP, TIMES, LAYER_ORDER, LAYER_NAME_CN, VISIBILITY, BASELAYER, CREATE_DATE, TOOLCODE)
values ('ALL_USER_LAYER_', 'amap', 0, 0, 0, 0, 1, 0, '高德地图', 1, 0, to_date('04-06-2012 09:54:39', 'dd-mm-yyyy hh24:mi:ss'), 'T');

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
'系统功能表';

COMMENT ON COLUMN SA_FUNCTION.FUNCCODE IS
'功能编号';

COMMENT ON COLUMN SA_FUNCTION.PARENTCODE IS
'上级功能编号';

COMMENT ON COLUMN SA_FUNCTION.FUNCNAME IS
'功能名称';

COMMENT ON COLUMN SA_FUNCTION.FUNCPROP IS
'功能类型
1:菜单；
2:按钮；
3:链接；';

COMMENT ON COLUMN SA_FUNCTION.LEVELNO IS
'功能级别';

COMMENT ON COLUMN SA_FUNCTION.ORDERNO IS
'排序号';

COMMENT ON COLUMN SA_FUNCTION.ISSTOP IS
'是否停用
1：停用；
0：启用；';

COMMENT ON COLUMN SA_FUNCTION.LINKPAGE IS
'链接';

COMMENT ON COLUMN SA_FUNCTION.TITLE IS
'提示信息';

COMMENT ON COLUMN SA_FUNCTION.ICON IS
'图标';

COMMENT ON COLUMN SA_FUNCTION.DESCRIPT IS
'描述';

COMMENT ON COLUMN SA_FUNCTION.SYSID IS
'系统ID';

alter table SA_FUNCTION NOLOGGING;

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('0101', '01', '工作区', 1, 2, 1, 0, '/org/orgManager.do?action=index', '工作区', 'tb2017', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('0102', '01', '用户管理', 1, 2, 2, 0, '/user/userManager.do?action=queryUser', '用户管理', 'tb20userset', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('0103', '01', '角色管理', 1, 2, 3, 0, '/role/roleManager.do?action=queryRole', '角色管理', 'tb20roleset', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('0104', '01', '功能管理', 1, 2, 4, 0, '/func/funcManager.do?action=index', '功能管理', 'tb2010', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('0105', '01', '访问统计', 1, 2, 5, 0, '/func/statManager.do?action=main', '', 'tb2062', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('0106', '01', '系统日志', 1, 2, 6, 0, '/log/logManager.do?action=main', '', 'tb2059', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010101', '0101', '新增', 2, 3, 1, 0, 'mainAdd();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010102', '0101', '修改', 2, 3, 2, 0, 'mainEdit();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010103', '0101', '删除', 2, 3, 3, 0, 'mainDelete();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010104', '0101', '查看', 3, 3, 4, 0, '', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010105', '0101', '启用', 2, 3, 5, 0, 'mainStart();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010106', '0101', '停用', 2, 3, 6, 0, 'mainStop();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010201', '0102', '新增', 2, 3, 1, 0, 'mainAdd();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010202', '0102', '修改', 2, 3, 2, 0, 'mainEdit();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010203', '0102', '删除', 2, 3, 3, 0, 'mainDelete();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010204', '0102', '查看', 3, 3, 4, 0, '', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010205', '0102', '启用', 2, 3, 5, 0, 'mainStart();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010206', '0102', '停用', 2, 3, 6, 0, 'mainStop();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010301', '0103', '新增', 2, 3, 1, 0, 'mainAdd();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010302', '0103', '修改', 2, 3, 2, 0, 'mainEdit();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010303', '0103', '删除', 2, 3, 3, 0, 'mainDelete();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010304', '0103', '查看', 3, 3, 4, 0, '', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010305', '0103', '启用', 2, 3, 5, 0, 'mainStart();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010306', '0103', '停用', 2, 3, 6, 0, 'mainStop();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010401', '0104', '新增', 2, 3, 1, 0, 'mainAdd();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010402', '0104', '修改', 2, 3, 2, 0, 'mainEdit();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010403', '0104', '删除', 2, 3, 3, 0, 'mainDelete();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010404', '0104', '查看', 3, 3, 4, 0, '', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010405', '0104', '启用', 2, 3, 5, 0, 'mainStart();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010406', '0104', '停用', 2, 3, 6, 0, 'mainStop();', '', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('010407', '0104', '保存顺序', 2, 3, 7, 0, 'mainSaveOrder();', '', '', '', 'frame');


insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('WEIBO', 'frame', '微博应用系统', 1, 1, 0, 0, '', '微博应用系统', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('WEIBO1', 'WEIBO', '微话题跟踪系统', 1, 1,1, 0, '/weibo/trends.do?action=main', '', 'tb2042', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('WEIBO9', 'WEIBO', '数据库初始化', 1, 9, 9, 0, '/init/dbInit.do?action=dbInitMain&'||'sysName=T1', '', 'tb2015', '', 'frame');



insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('frame', '', 'FreeWork工程', 1, 0, 0, 0, '', 'FreeWork工程', '', '', 'frame');



commit;