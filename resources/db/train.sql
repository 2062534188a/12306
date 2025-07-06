-- ----------------------------
-- 车次服务数据库 train_db
-- ----------------------------
DROP DATABASE IF EXISTS `train_db`;
CREATE DATABASE IF NOT EXISTS `train_db` DEFAULT CHARACTER SET utf8mb4;
USE `train_db`;

CREATE TABLE `train` (
     `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '车次ID',
     `train_number` VARCHAR(20) UNIQUE NOT NULL COMMENT '车次号(G101)',
     `start_city` VARCHAR(50) NOT NULL COMMENT '始发城市',
     `end_city` VARCHAR(50) NOT NULL COMMENT '终点城市',
     `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);

CREATE TABLE `train_station_1` (
       `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主站点ID',
       `station_name` VARCHAR(50) NOT NULL COMMENT '站点名称',
       `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
       `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);

CREATE TABLE `train_station` (
     `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分站点ID',
     `station_id` BIGINT NOT NULL COMMENT '主站点ID',
     `train_id` BIGINT NOT NULL COMMENT '关联车次ID',
     `station_name` VARCHAR(50) NOT NULL COMMENT '站点名称',
     `arrival_time` DATETIME COMMENT '到达时间',
     `departure_time` DATETIME COMMENT '发车时间',
     `station_order` INT NOT NULL COMMENT '站点顺序',
     `to_next_station_price` DECIMAL(10,2) COMMENT '到下一站点区间价格',
     `station_state` INT COMMENT '起始站点 0/终点站 1',
     `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     INDEX `idx_train_id` (`train_id`)
);

CREATE TABLE `train_seat` (
      `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '席别ID',
      `branch_station_id` BIGINT  COMMENT '分站点ID',
      `train_id` BIGINT NOT NULL COMMENT '关联车次ID',
      `seat_type` INT NOT NULL COMMENT '席别(特等座(商务座) 0/一等座 1/二等座 2/无座3)',
      `to_next_station_residue_count` INT COMMENT '到下一站点区间余座数',
      `total_count` INT NOT NULL COMMENT '总座位数',
      `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      INDEX `idx_branch_station` (`branch_station_id`)
);

-- 1. 先清空表（如果已有数据）
TRUNCATE TABLE `train_station`;
TRUNCATE TABLE `train_station_1`;
TRUNCATE TABLE `train_seat`;
TRUNCATE TABLE `train`;

-- 2. 插入车次数据
INSERT INTO `train` (`train_number`, `start_city`, `end_city`) VALUES
('G101', '北京', '上海'),
('G102', '上海', '北京'),
('D301', '北京', '南京'),
('D302', '南京', '北京'),
('K501', '北京', '广州'),
('K502', '广州', '北京'),
('G201', '北京', '深圳'),
('G202', '深圳', '北京');

-- 3. 插入主站点数据并获取ID
INSERT INTO `train_station_1` (`station_name`) VALUES
('北京南站'),
('北京西站'),
('北京站'),
('天津站'),
('上海虹桥站'),
('上海站'),
('南京南站'),
('南京站'),
('济南西站'),
('徐州东站'),
('杭州东站'),
('武汉站'),
('长沙南站'),
('广州南站'),
('广州站'),
('深圳北站'),
('深圳站'),
('石家庄站'),
('郑州东站');

-- 假设上面插入后生成的ID为1-19顺序对应

-- 4. 插入车次站点数据（确保station_id与train_station_1的id对应）

-- G101 北京-上海 车次站点
SET @train_id = (SELECT id FROM train WHERE train_number = 'G101');
INSERT INTO `train_station` (`station_id`, `train_id`, `station_name`, `arrival_time`, `departure_time`, `station_order`, `to_next_station_price`, `station_state`) VALUES
(1, @train_id, '北京南站', '2023-06-01 07:00:00', '2023-06-01 07:00:00', 1, 50.00, 0),
(4, @train_id, '天津站', '2023-06-01 07:30:00', '2023-06-01 07:32:00', 2, 80.00, NULL),
(9, @train_id, '济南西站', '2023-06-01 09:00:00', '2023-06-01 09:02:00', 3, 120.00, NULL),
(10, @train_id, '徐州东站', '2023-06-01 10:30:00', '2023-06-01 10:32:00', 4, 150.00, NULL),
(7, @train_id, '南京南站', '2023-06-01 12:00:00', '2023-06-01 12:02:00', 5, 100.00, NULL),
(5, @train_id, '上海虹桥站', '2023-06-01 14:00:00', '2023-06-01 14:00:00', 6, NULL, 1);

-- G102 上海-北京 车次站点
SET @train_id = (SELECT id FROM train WHERE train_number = 'G102');
INSERT INTO `train_station` (`station_id`, `train_id`, `station_name`, `arrival_time`, `departure_time`, `station_order`, `to_next_station_price`, `station_state`) VALUES
(5, @train_id, '上海虹桥站', '2023-06-01 15:00:00', '2023-06-01 15:00:00', 1, 100.00, 0),
(7, @train_id, '南京南站', '2023-06-01 17:00:00', '2023-06-01 17:02:00', 2, 150.00, NULL),
(10, @train_id, '徐州东站', '2023-06-01 18:30:00', '2023-06-01 18:32:00', 3, 120.00, NULL),
(9, @train_id, '济南西站', '2023-06-01 20:00:00', '2023-06-01 20:02:00', 4, 80.00, NULL),
(4, @train_id, '天津站', '2023-06-01 21:30:00', '2023-06-01 21:32:00', 5, 50.00, NULL),
(1, @train_id, '北京南站', '2023-06-01 22:00:00', '2023-06-01 22:00:00', 6, NULL, 1);

-- D301 北京-南京 车次站点
SET @train_id = (SELECT id FROM train WHERE train_number = 'D301');
INSERT INTO `train_station` (`station_id`, `train_id`, `station_name`, `arrival_time`, `departure_time`, `station_order`, `to_next_station_price`, `station_state`) VALUES
(1, @train_id, '北京南站', '2023-06-01 08:00:00', '2023-06-01 08:00:00', 1, 40.00, 0),
(4, @train_id, '天津站', '2023-06-01 08:30:00', '2023-06-01 08:32:00', 2, 60.00, NULL),
(18, @train_id, '石家庄站', '2023-06-01 10:00:00', '2023-06-01 10:02:00', 3, 80.00, NULL),
(19, @train_id, '郑州东站', '2023-06-01 12:00:00', '2023-06-01 12:02:00', 4, 100.00, NULL),
(12, @train_id, '武汉站', '2023-06-01 14:00:00', '2023-06-01 14:02:00', 5, 120.00, NULL),
(7, @train_id, '南京南站', '2023-06-01 16:00:00', '2023-06-01 16:00:00', 6, NULL, 1);

-- 5. 插入座位数据（保持不变）
-- G101 车次座位
SET @train_id = (SELECT id FROM train WHERE train_number = 'G101');
INSERT INTO `train_seat` (`branch_station_id`,`train_id`, `seat_type`, `total_count`, `to_next_station_residue_count`) VALUES
(1,@train_id, 0, 20, 20),
(1,@train_id, 1, 100, 100),
(1,@train_id, 2, 300, 300),
(1,@train_id, 3, 50, 50),
(2,@train_id, 0, 20, 20),
(2,@train_id, 1, 100, 100),
(2,@train_id, 2, 300, 300),
(2,@train_id, 3, 50, 50),
(3,@train_id, 0, 20, 20),
(3,@train_id, 1, 100, 100),
(3,@train_id, 2, 300, 300),
(3,@train_id, 3, 50, 50),
(4,@train_id, 0, 20, 20),
(4,@train_id, 1, 100, 100),
(4,@train_id, 2, 300, 300),
(4,@train_id, 3, 50, 50);


-- G102 车次座位
SET @train_id = (SELECT id FROM train WHERE train_number = 'G102');
INSERT INTO `train_seat` (`branch_station_id`,`train_id`, `seat_type`, `total_count`, `to_next_station_residue_count`) VALUES
(1,@train_id, 0, 20, 20),
(1,@train_id, 1, 100, 100),
(1,@train_id, 2, 300, 300),
(1,@train_id, 3, 50, 50),
(2,@train_id, 0, 20, 20),
(2,@train_id, 1, 100, 100),
(2,@train_id, 2, 300, 300),
(2,@train_id, 3, 50, 50),
(3,@train_id, 0, 20, 20),
(3,@train_id, 1, 100, 100),
(3,@train_id, 2, 300, 300),
(3,@train_id, 3, 50, 50),
(4,@train_id, 0, 20, 20),
(4,@train_id, 1, 100, 100),
(4,@train_id, 2, 300, 300),
(4,@train_id, 3, 50, 50);

-- D301 车次座位
SET @train_id = (SELECT id FROM train WHERE train_number = 'D301');
INSERT INTO `train_seat` (`branch_station_id`,`train_id`, `seat_type`, `total_count`, `to_next_station_residue_count`) VALUES
(1,@train_id, 0, 20, 20),
(1,@train_id, 1, 100, 100),
(1,@train_id, 2, 300, 300),
(1,@train_id, 3, 50, 50),
(2,@train_id, 0, 20, 20),
(2,@train_id, 1, 100, 100),
(2,@train_id, 2, 300, 300),
(2,@train_id, 3, 50, 50),
(3,@train_id, 0, 20, 20),
(3,@train_id, 1, 100, 100),
(3,@train_id, 2, 300, 300),
(3,@train_id, 3, 50, 50),
(4,@train_id, 0, 20, 20),
(4,@train_id, 1, 100, 100),
(4,@train_id, 2, 300, 300),
(4,@train_id, 3, 50, 50);

-- 其他车次座位数据类似...