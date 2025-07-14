-- ----------------------------
-- 支付服务数据库 payment_db
-- ----------------------------
DROP DATABASE IF EXISTS `payment_db`;
CREATE DATABASE IF NOT EXISTS `payment_db` DEFAULT CHARACTER SET utf8mb4;
USE `payment_db`;

CREATE TABLE `payment` (
       `id` VARCHAR(50) PRIMARY KEY COMMENT '支付流水号',
       `order_id` VARCHAR(30) NOT NULL COMMENT '关联订单号',
       `amount` DECIMAL(10,2) NOT NULL COMMENT '支付金额',
       `status` TINYINT NOT NULL COMMENT '状态(0支付中/1成功/2失败)',
       `pay_type` Tinyint COMMENT '支付方式(余额/支付宝/微信)',
       `expire_time` datetime NOT NULL COMMENT '支付截止时间',
       `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
       `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
       INDEX `idx_order_id` (`order_id`)
);

CREATE TABLE `refund` (
      `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
      `payment_id` VARCHAR(30) NOT NULL COMMENT '支付流水号',
      `refund_amount` DECIMAL(10,2) NOT NULL COMMENT '退款金额',
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
