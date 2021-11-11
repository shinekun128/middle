CREATE TABLE `item`
(
    id            INT(11) PRIMARY KEY AUTO_INCREMENT,
    `code`        VARCHAR(255) DEFAULT NULL COMMENT "商品编号",
    `name`        VARCHAR(255) DEFAULT NULL COMMENT "商品名称",
    `create_time` DATETIME     DEFAULT NULL
);

SELECT *
FROM item;

CREATE TABLE `red_record`
(
    `id`          INT(11) PRIMARY KEY AUTO_INCREMENT,
    `user_id`     INT(11) NOT NULL COMMENT "用户id",
    `red_packet`  VARCHAR(255) NOT NULL COMMENT "红包全局唯一标识",
    `total`       int(11) not null comment "人数",
    `amount`      decimal(10, 2) default null comment "总金额（单位为分）",
    `is_active`   tinyint(4) default "1",
    `create_time` datetime       default null
)engine=InnoDB Auto_increment=11 default charset=utf8 comment="发红包记录";

CREATE TABLE `red_detail`
(
    `id`          INT(11) PRIMARY KEY AUTO_INCREMENT,
    `record_id`   INT(11) NOT NULL COMMENT "红包记录id",
    `amount`      decimal(8, 2) default null comment "金额（单位为分）",
    `is_active`   tinyint(4) default "1",
    `create_time` datetime      default null
)engine=InnoDB Auto_increment=83 default charset=utf8 comment="红包明细";

CREATE TABLE `red_rob_record`
(
    `id`         INT(11) PRIMARY KEY AUTO_INCREMENT,
    `user_id`    INT(11) NOT NULL COMMENT "用户id",
    `red_packet` VARCHAR(255) NOT NULL COMMENT "红包全局唯一标识",
    `amount`     decimal(8, 2) default null comment "红包金额（单位为分）",
    `is_active`  tinyint(4) default "1",
    `rob_time`   datetime      default null
)engine=InnoDB Auto_increment=11 default charset=utf8 comment="抢红包记录";

create table `user`
(
    `id`          int(11) not null primary key auto_increment comment '用户Id',
    `user_name`   varchar(255) not null comment '用户名',
    `password`    varchar(255) not null comment '用户密码',
    `create_time` datetime default null comment '创建时间',
    unique key `idx_user_name`(`user_name`)
)engine=InnoDB Auto_increment=4 default charset=utf8 comment="用户信息表";

create table `sys_log`
(
    `id`          int(11) not null primary key auto_increment comment '用户Id',
    `user_id`     int(10) default '0' comment '用户id',
    `module`      varchar(255)                        default null comment '所属操作模块',
    `data`        varchar(5000) character set utf8mb4 default null comment '操作数据',
    `memo`        varchar(5000) character set utf8mb4 default null comment '备注',
    `create_time` datetime                            default null comment '创建时间',
)engine=InnoDB Auto_increment=11 default charset=utf8 comment="日志记录表";


create table `user_order`
(
    `id`          int(11) primary key auto_increment,
    `order_no`    varchar(255) not null comment '订单编号',
    `user_id`     int(11) default not null comment '用户id',
    `status`      int(11) default null comment '状态（1=已保存;2=已付款;3=已取消）',
    `is_active`   int(10) default '1' comment '是否有效(1=有效;0=失效)',
    `create_time` datetime default null comment '下单时间',
    `update_time` datetime default null
)engine=InnoDB Auto_increment=11 default charset=utf8 comment="日志记录表";

create table `mq_order`
(
    `id`            int(11) primary key auto_increment,
    `order_id`      int(11) not null comment '下单记录id',
    `business_time` datetime     default null comment '失效下单记录的时间',
    `memo`          varchar(255) default null comment '备注'
)engine=InnoDB Auto_increment=11 default charset=utf8 comment="日志记录表";

create table `user_account`
(
    `id`        int(11) primary key auto_increment,
    `user_id`   int(11) not null comment '用户名',
    `amount`    decimal(10, 4) not null comment '账户余额',
    `version`   int(11) default '1' commnet '乐观锁版本号',
    `is_active` tinyint(11) default '1' comment '是否有效(1=有效,0=无效)'
        unique key `idx_user_id`(`user_id`)
)engine=InnoDB Auto_increment=11 default charset=utf8 comment="用户账户记录表";

create table `user_account_record`
(
    `id`          int(11) primary key auto_increment,
    `account_id`  int(11) not null comment '账户表主键id',
    `money`       decimal(10, 4) not null comment '操作金额',
    `create_time` datetime default null,
    unique key `idx_account_id`(`account_id`)
)engine=InnoDB Auto_increment=11 default charset=utf8 comment="用户账户操作记录表";

create table `user`
(
    `id`          int(11) not null primary key auto_increment comment '用户Id',
    `user_name`   varchar(255) not null comment '用户名',
    `password`    varchar(255) not null comment '用户密码',
    `create_time` datetime default null comment '创建时间',
)engine=InnoDB Auto_increment=4 default charset=utf8 comment="用户信息表";

create table `book_stock`
(
    `id`          int(11) not null primary key auto_increment comment '书籍主键id',
    `book_no`     varchar(255) UNION not null comment '书籍编号',
    `stock`       int(11) not null comment '书籍库存',
    `is_active`   tinyint(4) not null default '1' comment '是否上架',
    `create_time` datetime default null comment '上架时间'
)engine=InnoDB Auto_increment=4 default charset=utf8 comment="书籍库存表";

create table `book_rob`
(
    `id` int(11) not null primary key auto_increment comment '主键id',
    `user_id` int(11) unique not null comment '用户编号',
    `book_no` varchar(255) not null commment '书籍编号',
    `rob_time` datetime default null comment '抢购时间'
)engine=InnoDB Auto_increment=22 default charset=utf8 comment="用户抢购记录";
