/*==============================================================*/
/* Table: T1TRENDS                                              */
/*==============================================================*/
CREATE TABLE T1TRENDS 
(
   ID                   VARCHAR(20)                    NOT NULL,
   TRENDSNAME           VARCHAR(80)                    NOT NULL,
   STARTTIME            DATETIME                       NOT NULL,
   CONSTRAINT PK_T1TRENDS PRIMARY KEY (ID)
);


/*==============================================================*/
/* Table: T1TRENDSHOUR                                          */
/*==============================================================*/
CREATE TABLE T1TRENDSHOUR 
(
   ID                   VARCHAR(20)                    	NOT NULL,
   HIT                  INT                            	NOT NULL,
   STATTIME             DATETIME						NOT NULL,
   DELTA                INT,
   CONSTRAINT PK_T1TRENDSHOUR PRIMARY KEY (ID,STATTIME)
);


/*==============================================================*/
/* Table: T1TRENDSDAYLY                                         */
/*==============================================================*/
CREATE TABLE T1TRENDSDAYLY 
(
   ID                   VARCHAR(20)                     NOT NULL,
   HIT                  INT                             NOT NULL,
   STATTIME             DATETIME						NOT NULL,
   DELTA                INT,
   CONSTRAINT PK_T1TRENDSDAYLY PRIMARY KEY (ID,STATTIME)
);

/*==============================================================*/
/* Table: T1TRENDSWEEK                                          */
/*==============================================================*/
CREATE TABLE T1TRENDSWEEK 
(
   ID                   VARCHAR(20)                     NOT NULL,
   HIT                  INT                             NOT NULL,
   STATTIME             DATETIME						NOT NULL,
   DELTA                INT,
   CONSTRAINT PK_T1TRENDSWEEK PRIMARY KEY (ID,STATTIME)
);

/*==============================================================*/
/* Table: T1WEIBOUSER                                           */
/*==============================================================*/
CREATE TABLE T1WEIBOUSER 
(
   USERCODE             VARCHAR(20)                    NOT NULL,
   STATTIME             DATETIME,
   ACCESSTOKEN          VARCHAR(200),
   SECRET               VARCHAR(200),
   EXPIRED				INT
);

/*==============================================================*/