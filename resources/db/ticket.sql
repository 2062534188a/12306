-- ----------------------------
-- 票务服务数据库 ticket_db
-- ----------------------------
DROP DATABASE IF EXISTS `ticket_db`;
CREATE DATABASE IF NOT EXISTS `ticket_db` DEFAULT CHARACTER SET utf8mb4;
USE `ticket_db`;

CREATE TABLE `ticket` (
      `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '票据ID',
      `order_id` VARCHAR(30) NOT NULL COMMENT '关联订单号',
      `train_id` BIGINT NOT NULL COMMENT '车次ID',
      `carriage_number` TINYINT COMMENT '车厢号',
      `seat_number` VARCHAR(10) COMMENT '座位号(5A)',
      `passenger_id` BIGINT NOT NULL COMMENT '乘车人ID',
      `ticket_status` TINYINT DEFAULT 0 COMMENT '票据状态(0未使用/1已使用)',
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