-- ----------------------------
-- 票务服务数据库 ticket_db
-- ----------------------------
DROP DATABASE IF EXISTS `ticket_db`;
CREATE DATABASE IF NOT EXISTS `ticket_db` DEFAULT CHARACTER SET utf8mb4;
USE `ticket_db`;

CREATE TABLE `ticket` (
      `id` VARCHAR(30) PRIMARY KEY COMMENT '票据ID',
      `order_id` VARCHAR(30) NOT NULL COMMENT '关联订单号',
      `passenger_id` BIGINT NOT NULL COMMENT '乘车人ID',
      `train_id` BIGINT NOT NULL COMMENT '车次ID',
      `seat_type` INT  COMMENT '席别(特等座(商务座) 0/一等座 1/二等座 2/无座3)',
      `carriage_no` INT COMMENT '车厢号(商务座1车10个座位/一等座2-3车80个座位) ',
      `row_no` INT NOT NULL COMMENT '排号',
      `seat_code` NVARCHAR(1) NOT NULL COMMENT '座位字母',
      `start_station_id` BIGINT NOT NULL COMMENT '出发站点id',
      `start_station` VARCHAR(50) NOT NULL COMMENT '出发站点名称',
      `end_station_id` BIGINT NOT NULL COMMENT '目标站点id',
      `end_station` VARCHAR(50) NOT NULL COMMENT '目标站点名称',
      `departure_time` DATETIME COMMENT '发车时间',
      `arrival_time` DATETIME COMMENT '到达时间',
      `ticket_status` TINYINT DEFAULT 3 COMMENT '票据状态(0未使用/1已使用/3锁定状态)',
      `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      INDEX `idx_order_id` (`order_id`)
);

CREATE TABLE `inventory_log` (
     `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
     `train_id` BIGINT NOT NULL,
     `seat_type` VARCHAR(10) NOT NULL,
     `change_count` INT NOT NULL COMMENT '变动数量(正数增加/负数减少)',
     `biz_type` VARCHAR(20) COMMENT '业务类型(购票/退票)',
     `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);

CREATE TABLE IF NOT EXISTS `undo_log`
(
    `branch_id`     BIGINT       NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='AT transaction mode undo table';
