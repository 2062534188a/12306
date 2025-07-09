-- ----------------------------
-- 支付服务数据库 payment_db
-- ----------------------------
DROP DATABASE IF EXISTS `payment_db`;
CREATE DATABASE IF NOT EXISTS `payment_db` DEFAULT CHARACTER SET utf8mb4;
USE `payment_db`;

CREATE TABLE `payment` (
       `id` VARCHAR(30) PRIMARY KEY COMMENT '支付流水号',
       `order_id` VARCHAR(30) NOT NULL COMMENT '关联订单号',
       `amount` DECIMAL(10,2) NOT NULL COMMENT '支付金额',
       `status` TINYINT NOT NULL COMMENT '状态(0支付中/1成功/2失败)',
       `pay_type` VARCHAR(10) COMMENT '支付方式(余额/支付宝/微信)',
       `expire_time` datetime NOT NULL COMMENT '支付截止时间',
       `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
       `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
       `pay_time` DATETIME COMMENT '支付时间',
       INDEX `idx_order_id` (`order_id`)
);

CREATE TABLE `refund` (
      `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
      `payment_id` VARCHAR(30) NOT NULL COMMENT '支付流水号',
      `refund_amount` DECIMAL(10,2) NOT NULL COMMENT '退款金额',
      `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);