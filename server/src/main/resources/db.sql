CREATE TABLE `item`(
                       id INT(11) PRIMARY KEY AUTO_INCREMENT,
                       `code` VARCHAR(255) DEFAULT NULL COMMENT "商品编号",
                       `name` VARCHAR(255) DEFAULT NULL COMMENT "商品名称",
                       `create_time` DATETIME DEFAULT NULL
);

SELECT * FROM item;

CREATE TABLE `red_record`(
                             `id` INT(11) PRIMARY KEY AUTO_INCREMENT,
                             `user_id` INT(11) NOT NULL COMMENT "用户id",
                             `red_packet` VARCHAR(255) NOT NULL COMMENT "红包全局唯一标识",
                             `total` int(11) not null comment "人数",
                             `amount` decimal(10,2) default null comment "总金额（单位为分）",
                             `is_active` tinyint(4) default "1",
                             `create_time` datetime default null
)engine=InnoDB Auto_increment=11 default charset=utf8 comment="发红包记录";

CREATE TABLE `red_detail`(
                             `id` INT(11) PRIMARY KEY AUTO_INCREMENT,
                             `record_id` INT(11) NOT NULL COMMENT "红包记录id",
                             `amount` decimal(8,2) default null comment "金额（单位为分）",
                             `is_active` tinyint(4) default "1",
                             `create_time` datetime default null
)engine=InnoDB Auto_increment=83 default charset=utf8 comment="红包明细";

CREATE TABLE `red_rob_record`(
                             `id` INT(11) PRIMARY KEY AUTO_INCREMENT,
                             `user_id` INT(11) NOT NULL COMMENT "用户id",
                             `red_packet` VARCHAR(255) NOT NULL COMMENT "红包全局唯一标识",
                             `amount` decimal(8,2) default null comment "红包金额（单位为分）",
                             `is_active` tinyint(4) default "1",
                             `rob_time` datetime default null
)engine=InnoDB Auto_increment=11 default charset=utf8 comment="抢红包记录";
