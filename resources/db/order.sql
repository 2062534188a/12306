-- ----------------------------
-- 订单服务数据库 order_db
-- ----------------------------
DROP DATABASE IF EXISTS `order_db`;
CREATE DATABASE IF NOT EXISTS `order_db` DEFAULT CHARACTER SET utf8mb4;
USE `order_db`;

CREATE TABLE `order` (
     `id` VARCHAR(30) PRIMARY KEY COMMENT '订单号(业务生成)',
     `payment_id` VARCHAR(50)  COMMENT '支付流水号',
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
