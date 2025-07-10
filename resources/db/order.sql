-- ----------------------------
-- 订单服务数据库 order_db
-- ----------------------------
DROP DATABASE IF EXISTS `order_db`;
CREATE DATABASE IF NOT EXISTS `order_db` DEFAULT CHARACTER SET utf8mb4;
USE `order_db`;

CREATE TABLE `order` (
     `id` VARCHAR(50) PRIMARY KEY COMMENT '订单号(业务生成)',
     `user_id` BIGINT NOT NULL COMMENT '用户ID',
     `amount` DECIMAL(10,2) NOT NULL COMMENT '总金额',
     `status` TINYINT NOT NULL COMMENT '状态(0待支付/1已支付/2已取消)',
     `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     `expire_time` DATETIME COMMENT '支付截止时间',
     INDEX `idx_user_id` (`user_id`),
     INDEX `idx_status` (`status`)
);

CREATE TABLE `order_passenger` (
       `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
       `order_id` VARCHAR(30) NOT NULL COMMENT '订单号',
       `passenger_id` BIGINT NOT NULL COMMENT '乘车人ID',
       `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
       `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);