-- ----------------------------
-- 用户服务数据库 user_db
-- ----------------------------
DROP DATABASE IF EXISTS `user_db`;
CREATE DATABASE IF NOT EXISTS `user_db` DEFAULT CHARACTER SET utf8mb4;
USE `user_db`;

CREATE TABLE `user` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(50) UNIQUE NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '加密密码',
    `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
    `id_card` VARCHAR(20) NOT NULL COMMENT '身份证号',
    `real_name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
    `balance` DECIMAL(10,2) NOT NULL COMMENT '余额',
    `status` TINYINT DEFAULT 1 COMMENT '状态(0禁用/1正常)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);

CREATE TABLE `passenger` (
     `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '乘车人ID',
#      `user_id` BIGINT NOT NULL COMMENT '关联用户ID',
     `name` VARCHAR(50) NOT NULL COMMENT '姓名',
     `id_card` VARCHAR(20) NOT NULL COMMENT '身份证号',
     `type` TINYINT DEFAULT 0 COMMENT '类型(0成人/1儿童)',
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


-- ----------------------------
-- 添加模拟用户数据
-- ----------------------------
INSERT INTO `user` (username, password, phone, id_card, real_name, balance, status) VALUES
    ('root', '$2a$10$mUFUwa9u9lscBoYOrx2zO.3GqeeUAYGEZzNHDTUdvGc/.yh1LCNp2', '13800138000', '110105199001011234', '张三', 1500.00, 1),
    ('lisi', '$2a$10$7K8K9Vz7G6c2d8d2Q2X2Vu', '13900139000', '310115199102022345', '李四', 2000.50, 1),
    ('wangwu', '$2a$10$9L0L1Vz7G6c2d8d2Q2X2Vu', '13700137000', '440305198503033456', '王五', 800.75, 1),
    ('zhaoliu', '$2a$10$3M4M5Vz7G6c2d8d2Q2X2Vu', '13600136000', '330106197712124567', '赵六', 3500.25, 0),  -- 禁用用户
    ('chenqi', '$2a$10$5N6N7Vz7G6c2d8d2Q2X2Vu', '13500135000', '510107200001015678', '陈七', 1200.00, 1);

-- ----------------------------
-- 添加模拟乘车人数据
-- ----------------------------
-- 张三的乘车人
INSERT INTO `passenger` (user_id, name, id_card, type) VALUES
   (1, '张三', '110105199001011234', 0),
   (1, '张小三', '110105201502021234', 1),  -- 儿童
   (1, '李芳', '110105198502028765', 0);

-- 李四的乘车人
INSERT INTO `passenger` (user_id, name, id_card, type) VALUES
   (2, '李四', '310115199102022345', 0),
   (2, '李思思', '310115201803033456', 1);  -- 儿童

-- 王五的乘车人
INSERT INTO `passenger` (user_id, name, id_card, type) VALUES
   (3, '王五', '440305198503033456', 0),
   (3, '王明', '440305196005044567', 0),
   (3, '王小红', '440305201304055678', 1);  -- 儿童

-- 赵六的乘车人（禁用用户）
INSERT INTO `passenger` (user_id, name, id_card, type) VALUES
    (4, '赵六', '330106197712124567', 0);

-- 陈七的乘车人
INSERT INTO `passenger` (user_id, name, id_card, type) VALUES
    (5, '陈七', '510107200001015678', 0);

-- ----------------------------
-- 验证数据
-- ----------------------------
SELECT * FROM `user`;
SELECT * FROM `passenger`;

-- 查询用户及其乘车人
SELECT u.id AS user_id, u.real_name, u.balance,
       p.id AS passenger_id, p.name AS passenger_name, p.type
FROM `user` u
         LEFT JOIN `passenger` p ON u.id = p.user_id
WHERE u.status = 1;