/*==============================================================*/
/* Table: SA_ORG                                                */
/*==============================================================*/
CREATE TABLE SA_ORG  (
   ID                   VARCHAR(20)                    NOT NULL,
   PARENTID             VARCHAR(20),
   ORGNAME              VARCHAR(30)                    NOT NULL,
   ORGFULLNAME          VARCHAR(100)                   NOT NULL,
   CHIEF                VARCHAR(30),
   PHONE                VARCHAR(20),
   MOBILE               VARCHAR(20),
   EMAIL                VARCHAR(40),
   DESCRIPT             VARCHAR(2000),
   LEVELNO              INT                             NOT NULL,
   ORDERNO              VARCHAR(6)                     NOT NULL,
   ISSTOP               INT                             NOT NULL,
   CONSTRAINT PK_SA_ORG PRIMARY KEY (ID)
);
CREATE INDEX SA_ORG1 ON SA_ORG ( ID ASC);

/*==============================================================*/
/* Table: SA_SYS                                                */
/*==============================================================*/
CREATE TABLE SA_SYS  (
   ID                   VARCHAR(20)                     NOT NULL,
   SYSNAME              VARCHAR(80)                     NOT NULL,
   CONTEXT              VARCHAR(20)                     NOT NULL,
   ORDERNO              INT
);

insert into sa_sys (ID, SYSNAME, CONTEXT, ORDERNO)
values ('frame', 'FreeWork', 'project', 1);


/*==============================================================*/
/* Table: SYS_INDIVIDUATION                                     */
/*==============================================================*/
CREATE TABLE SYS_INDIVIDUATION  (
   SYSID                VARCHAR(20)                    NOT NULL,
   USERCODE             VARCHAR(20)                    NOT NULL,
   USERNAME             VARCHAR(20)                    NOT NULL,
   SETCODE              VARCHAR(50)                    NOT NULL,
   SETNAME              VARCHAR(200),
   KIND                 VARCHAR(200),
   SETTING              VARCHAR(4000),
   DEFAULT_VALUE        VARCHAR(4000),
   EXPRESSION           VARCHAR(200),
   DES                  VARCHAR(200),
   CONSTRAINT PK_SYS_INDIVIDUATION PRIMARY KEY (SYSID, USERCODE, SETCODE)
);



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
insert into sa_role (ROLECODE, ORGID, ROLENAME, ISSTOP, ROLEPROP, DESCRIPT)
values ('admin', '', '超级管理角色', 0, 3, ''); 


/*==============================================================*/
/* Table: SYS_ROLE_FUNCTION                                     */
/*==============================================================*/
CREATE TABLE SYS_ROLE_FUNCTION  (
   ROLECODE             VARCHAR(20)                    NOT NULL,
   FUNCCODE             VARCHAR(40)                    NOT NULL,
   CONSTRAINT PK_SYS_ROLE_FUNCTION PRIMARY KEY (ROLECODE, FUNCCODE)
);
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
values ('admin', 'frame');

/*==============================================================*/
/* Table: SA_USER                                               */
/*==============================================================*/
CREATE TABLE SA_USER  (
   USERCODE             VARCHAR(20)                    NOT NULL,
   USERNAME             VARCHAR(20)                    NOT NULL,
   ORGID                VARCHAR(20),
   PASSWD               VARCHAR(100)                   NOT NULL,
   TEL                  VARCHAR(40),
   MOBILE               VARCHAR(11),
   EMAIL                VARCHAR(40),
   ISSTOP               INT                             NOT NULL,
   USERPROP             INT                             NOT NULL,
   DESCRIPT             VARCHAR(200),
   TRUSTID              VARCHAR(50),
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


insert into sa_user (USERCODE, ORGID, USERNAME, PASSWD, TEL, MOBILE, EMAIL, ISSTOP, USERPROP, DESCRIPT, TRUSTID, LIMITTYPE, PERMITNUM, LOGINNUM, EFFECTTIME, DAYS)
values ('admin', null, 'admin', '21232F297A57A5A743894A0E4A801FC3', '', '', '', 0, 3, '管理员', '', 0, 5, 6, str_to_date('23-11-2010', '%d-%m-%Y'), 5);


/*==============================================================*/
/* Table: SYS_USER_ROLE                                         */
/*==============================================================*/
CREATE TABLE SYS_USER_ROLE  (
   USERCODE             VARCHAR(20)                    NOT NULL,
   ROLECODE             VARCHAR(20)                    NOT NULL,
   CONSTRAINT PK_SYS_USER_ROLE PRIMARY KEY (USERCODE, ROLECODE)
);


insert into sys_user_role (USERCODE, ROLECODE)
values ('admin', 'admin');



/*==============================================================*/
/* Table: SA_LOG                                                */
/*==============================================================*/
CREATE TABLE SA_LOG  (
   LEVNO                VARCHAR(100),
   USERCODE             VARCHAR(20) ,
   ADDR                 VARCHAR(100),
   OPERATETIME          DATETIME,
   MOUDLE               VARCHAR(200),
   MSG                  VARCHAR(2000)
);

/*==============================================================*/
/* Table: SA_ERROR_LOG                                          */
/*==============================================================*/
CREATE TABLE SA_ERROR_LOG  (
   ID                   VARCHAR(100)                   NOT NULL,
   MOUDLE               VARCHAR(200),
   ERROR_CLASS          VARCHAR(300),
   MSG                  VARCHAR(2000),
   USERNAME             VARCHAR(20)                    NOT NULL,
   USERCODE             VARCHAR(20)                    NOT NULL,
   OPERATETIME          DATETIME,
   CONSTRAINT PK_SA_ERROR_LOG PRIMARY KEY (ID)
);



/*==============================================================*/
/* Table: DATAMGR                                               */
/*==============================================================*/
CREATE TABLE DATAMGR  (
   TABCODE              VARCHAR(50)                    NOT NULL,
   TABNAME              VARCHAR(200),
   TOOLS                VARCHAR(50),
   ORGID                VARCHAR(20),
   USERCODE             VARCHAR(20),
   USERNAME             VARCHAR(20),
   CREATETIME           DATETIME,
   USERCODE2            VARCHAR(20),
   USERNAME2            VARCHAR(20),
   IMPOTTIME            DATETIME,
   CONSTRAINT PK_DATAMGR PRIMARY KEY (TABCODE)
);

/*==============================================================*/
/* Table: TASK                                                  */
/*==============================================================*/
CREATE TABLE TASK  (
   TID                  VARCHAR(20)                    NOT NULL,
   NAME                 VARCHAR(200),
   TYPE                 VARCHAR(50),
   STATE                VARCHAR(20),
   BATCH                VARCHAR(100),
   FILENAME             VARCHAR(500),
   FILESIZE             BIGINT,
   STARTTIME            TIMESTAMP,
   ENDTIME              TIMESTAMP,
   USE_TIME             BIGINT,
   DES                  VARCHAR(2000),
   COMPANY              VARCHAR(200),
   ORGID                VARCHAR(20),
   USERCODE             VARCHAR(20),
   TOOLCODE             VARCHAR(20),
   CONSTRAINT PK_TASK PRIMARY KEY (TID)
);


/*==============================================================*/
/* Table: MAP_CACHE_INFO                                        */
/*==============================================================*/
CREATE TABLE MAP_CACHE_INFO  (
   ORGID                VARCHAR(20)                    NOT NULL,
   LAYER_NAME           VARCHAR(100)                   NOT NULL,
   F_LEFT               FLOAT,
   BOTTOM               FLOAT,
   F_RIGHT              FLOAT,
   TOP                  FLOAT,
   TIMES                NUMERIC,
   LAYER_ORDER          NUMERIC                         DEFAULT 0,
   LAYER_NAME_CN        VARCHAR(500),
   VISIBILITY           NUMERIC                         DEFAULT 1,
   BASELAYER            NUMERIC                         DEFAULT 0,
   CREATE_DATE          DATETIME,
   TOOLCODE             VARCHAR(20)                    NOT NULL,
   CONSTRAINT PK_MAP_CACHE_INFO PRIMARY KEY (ORGID, LAYER_NAME, TOOLCODE)
);

commit;


/*==============================================================*/
/* Table: SA_FUNCTION                                           */
/*==============================================================*/
CREATE TABLE SA_FUNCTION  (
   FUNCCODE             VARCHAR(40)                    NOT NULL,
   PARENTCODE           VARCHAR(40),
   FUNCNAME             VARCHAR(80)                    NOT NULL,
   FUNCPROP             INT                             NOT NULL,
   LEVELNO              INT                             NOT NULL,
   ORDERNO              INT                             NOT NULL,
   ISSTOP               INT                             NOT NULL,
   LINKPAGE             VARCHAR(2000),
   TITLE                VARCHAR(100),
   ICON                 VARCHAR(100),
   DESCRIPT             VARCHAR(200),
   SYSID                VARCHAR(20)                    NOT NULL,
   CONSTRAINT PK_SA_FUNCTION PRIMARY KEY (FUNCCODE)
);


insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('01', 'frame', '系统管理', 1, 1, 18, 0, '', '系统管理', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('0101', '01', '工作区', 1, 2, 1, 0, '/org/orgManager.do?action=index', '工作区', 'tb2016', '', 'frame');

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
values ('0102', '01', '用户管理', 1, 2, 2, 0, '/user/userManager.do?action=queryUser', '用户管理', 'tb20userset', '', 'frame');

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
values ('0103', '01', '角色管理', 1, 2, 3, 0, '/role/roleManager.do?action=queryRole', '角色管理', 'tb20roleset', '', 'frame');

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
values ('0104', '01', '功能管理', 1, 2, 4, 0, '/func/funcManager.do?action=index', '功能管理', 'tb2010', '', 'frame');

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
values ('0109', '-01', '数据库初始化', 1, 2, 5, 1, '/init/dbInit.do?action=dbInitMain', '数据库初始化', 'tb2015', '', 'frame');


insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('WEIBO', 'frame', '微博应用系统', 1, 1, 0, 0, '', '微博应用系统', '', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('WEIBO1', 'WEIBO', '微话题跟踪系统', 1, 1,1, 0, '/weibo/trends.do?action=main', '', 'tb2042', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('WEIBO9', 'WEIBO', '数据库初始化', 1, 9, 9, 0, '/init/dbInit.do?action=dbInitMain&sysName=T1', '', 'tb2015', '', 'frame');



--insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
--values ('BDT', 'frame', '数据管理', 1, 1, 0, 0, '', '基础数据管理', '', '', 'frame');

--insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
--values ('BDT08', 'BDT', '地图展示', 1, 8, 8, 0, '/gis/layerCtrl.do?action=layerMain', '', 'tb2042', '', 'frame');

insert into sa_function (FUNCCODE, PARENTCODE, FUNCNAME, FUNCPROP, LEVELNO, ORDERNO, ISSTOP, LINKPAGE, TITLE, ICON, DESCRIPT, SYSID)
values ('frame', '', 'FreeWork工程', 1, 0, 0, 0, '', 'FreeWork工程', '', '', 'frame');

/*==============================================================*/
/* Table: SA_FUNCTION_STAT                                      */
/*==============================================================*/
CREATE TABLE SA_FUNCTION_STAT  (
   SYSID                VARCHAR(20)                    NOT NULL,
   PARENTCODE           VARCHAR(40)                    NOT NULL,
   FUNCCODE             VARCHAR(40)                    NOT NULL,
   CNT                  INT,
   CONSTRAINT PK_SA_FUNCTION_STAT PRIMARY KEY (SYSID, PARENTCODE, FUNCCODE)
);
