CREATE DATABASE `mock` CHARACTER SET 'utf8'

DROP TABLE  IF EXISTS mock_config;
CREATE TABLE `mock_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `mock_code` char(8) DEFAULT NULL COMMENT '配置编码',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `route` varchar(100) NOT NULL COMMENT '路由',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(10) DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(10) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ1` (`mock_code`) USING BTREE COMMENT '配置编码唯一索引'
) ENGINE=InnoDB COMMENT 'mock配置表';

INSERT INTO mock.mock_config (id, mock_code, name, route, create_time, create_user, update_time, update_user) VALUES (1, '00000001', '返回String', '/mock/test/dubbo/provider/service/TestService/helloString', '2022-07-25 12:04:01', 'sys', '2022-08-11 14:26:42', 'sys');
INSERT INTO mock.mock_config (id, mock_code, name, route, create_time, create_user, update_time, update_user) VALUES (2, '00000002', '返回Integer', '/mock/test/dubbo/provider/service/TestService/helloInteger', '2022-07-25 12:04:40', 'sys', '2022-08-11 14:26:41', 'sys');
INSERT INTO mock.mock_config (id, mock_code, name, route, create_time, create_user, update_time, update_user) VALUES (3, '00000003', '返回int', '/mock/test/dubbo/provider/service/TestService/helloInt', '2022-07-26 13:28:03', 'sys', '2022-08-11 14:26:42', 'sys');
INSERT INTO mock.mock_config (id, mock_code, name, route, create_time, create_user, update_time, update_user) VALUES (4, '00000004', '返回list', '/mock/test/dubbo/provider/service/TestService/helloList', '2022-07-26 13:52:45', 'sys', '2022-08-11 14:26:41', 'sys');
INSERT INTO mock.mock_config (id, mock_code, name, route, create_time, create_user, update_time, update_user) VALUES (5, '00000005', '返回Map', '/mock/test/dubbo/provider/service/TestService/helloMap', '2022-07-26 14:03:47', 'sys', '2022-08-11 14:26:42', 'sys');
INSERT INTO mock.mock_config (id, mock_code, name, route, create_time, create_user, update_time, update_user) VALUES (6, '00000006', '返回对象', '/mock/test/dubbo/provider/service/TestService/helloObject', '2022-07-26 14:19:39', 'sys', '2022-08-11 14:26:41', 'sys');
INSERT INTO mock.mock_config (id, mock_code, name, route, create_time, create_user, update_time, update_user) VALUES (7, '00000007', '无返回', '/mock/test/dubbo/provider/service/TestService/helloVoid', '2022-07-26 14:35:07', 'sys', '2022-08-11 14:26:41', 'sys');
INSERT INTO mock.mock_config (id, mock_code, name, route, create_time, create_user, update_time, update_user) VALUES (8, '00000008', 'spi加载代理', '/work/skymoyo/test/service/TestService/helloSpi', '2022-08-06 14:09:22', 'sys', '2022-08-11 14:19:11', 'sys');


DROP TABLE  IF EXISTS mock_rule;
CREATE TABLE `mock_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `mock_code` char(8) DEFAULT NULL COMMENT '配置编码',
  `rule_code` char(8) DEFAULT NULL COMMENT '规则编码',
  `rule_name` varchar(50) DEFAULT NULL COMMENT '规则名称',
  `rule_type` tinyint(3) NOT NULL COMMENT '结果生成类型 10 直接返回 20 动态生成',
  `rule_result` text NOT NULL COMMENT '返回结果',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(10) DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(10) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  INDEX `IDX1`(`mock_code`) USING BTREE COMMENT '配置编码索引',
  UNIQUE KEY `UQ1` (`rule_code`) USING BTREE COMMENT '规则编码唯一索引'
) ENGINE=InnoDB COMMENT 'mock规则配置表';

INSERT INTO mock.mock_rule (id, mock_code, rule_code, rule_name, rule_type, rule_result, create_time, create_user, update_time, update_user) VALUES (1, '00000001', '10000001', '返回String', 10, 'hello mock', '2022-07-25 14:47:49', 'sys', '2022-07-26 14:05:37', 'sys');
INSERT INTO mock.mock_rule (id, mock_code, rule_code, rule_name, rule_type, rule_result, create_time, create_user, update_time, update_user) VALUES (2, '00000002', '10000002', '返回Integer', 10, '8848', '2022-07-25 14:48:16', 'sys', '2022-07-26 14:05:37', 'sys');
INSERT INTO mock.mock_rule (id, mock_code, rule_code, rule_name, rule_type, rule_result, create_time, create_user, update_time, update_user) VALUES (3, '00000003', '10000003', '返回int', 10, '8066', '2022-07-26 10:47:38', 'sys', '2022-07-26 14:05:37', 'sys');
INSERT INTO mock.mock_rule (id, mock_code, rule_code, rule_name, rule_type, rule_result, create_time, create_user, update_time, update_user) VALUES (4, '00000004', '10000004', '返回list', 10, '[1, 2, 3]', '2022-07-26 13:56:49', 'sys', '2022-07-26 14:05:37', 'sys');
INSERT INTO mock.mock_rule (id, mock_code, rule_code, rule_name, rule_type, rule_result, create_time, create_user, update_time, update_user) VALUES (5, '00000005', '10000005', '返回map', 10, '{"name":"mock Map","desc":"返回map","key":"这是一个key"}', '2022-07-26 14:05:07', 'sys', '2022-07-26 14:19:25', 'sys');
INSERT INTO mock.mock_rule (id, mock_code, rule_code, rule_name, rule_type, rule_result, create_time, create_user, update_time, update_user) VALUES (6, '00000006', '10000006', '返回Object', 10, '{"id":10086,"name":"mock Map","desc":"返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n返回对象,这个消息里超过1024字节!!!\\\\\\r\\\\\\n"}', '2022-07-26 14:23:11', 'sys', '2022-08-11 14:32:51', 'sys');
INSERT INTO mock.mock_rule (id, mock_code, rule_code, rule_name, rule_type, rule_result, create_time, create_user, update_time, update_user) VALUES (7, '00000006', '10000007', '返回重载Object', 10, '{"id":10000,"name":"mock Map","desc":"返回重载对象"}', '2022-07-26 14:23:11', 'sys', '2022-07-26 14:23:11', 'sys');
INSERT INTO mock.mock_rule (id, mock_code, rule_code, rule_name, rule_type, rule_result, create_time, create_user, update_time, update_user) VALUES (8, '00000007', '10000008', '无返回', 10, '""', '2022-07-26 14:36:04', 'sys', '2022-07-26 14:49:00', 'sys');
INSERT INTO mock.mock_rule (id, mock_code, rule_code, rule_name, rule_type, rule_result, create_time, create_user, update_time, update_user) VALUES (9, '00000008', '10000009', 'spi加载代理', 10, 'hello spi', '2022-08-06 14:09:56', 'sys', '2022-08-11 14:36:48', 'sys');


DROP TABLE  IF EXISTS mock_condition;
CREATE TABLE `mock_condition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `rule_code` char(8) DEFAULT NULL COMMENT '规则编码',
  `condition_type` char(8) DEFAULT NULL COMMENT '条件',
  `condition_key` varchar(20) DEFAULT NULL COMMENT '条件EL表达式',
  `condition_value` varchar(100) DEFAULT NULL COMMENT '条件匹配值',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(10) DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(10) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  INDEX `UQ1` (`rule_code`) USING BTREE COMMENT '规则编码唯一索引'
) ENGINE=InnoDB COMMENT 'mock规则条件表';

INSERT INTO mock.mock_condition (id, rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES (1, '10000001', 'route', null, '/mock/test/dubbo/provider/service/TestService/helloString', '2022-07-26 03:23:37', 'sys', '2022-08-11 14:27:09', 'sys');
INSERT INTO mock.mock_condition (id, rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES (2, '10000001', 'params', '[0]', 'foo', '2022-07-26 03:24:51', 'sys', '2022-07-26 14:06:18', 'sys');
INSERT INTO mock.mock_condition (id, rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES (3, '10000002', 'route', '', '/mock/test/dubbo/provider/service/TestService/helloInteger', '2022-07-26 06:41:05', 'sys', '2022-08-11 14:27:09', 'sys');
INSERT INTO mock.mock_condition (id, rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES (4, '10000002', 'params', '[0]', '18', '2022-07-26 07:01:05', 'sys', '2022-07-26 14:06:18', 'sys');
INSERT INTO mock.mock_condition (id, rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES (5, '10000003', 'route', null, '/mock/test/dubbo/provider/service/TestService/helloInt', '2022-07-26 10:48:31', 'sys', '2022-08-11 14:27:09', 'sys');
INSERT INTO mock.mock_condition (id, rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES (6, '10000003', 'params', '[0]', '1', '2022-07-26 10:48:31', 'sys', '2022-07-26 14:06:18', 'sys');
INSERT INTO mock.mock_condition (id, rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES (7, '10000004', 'route', '', '/mock/test/dubbo/provider/service/TestService/helloList', '2022-07-26 10:48:31', 'sys', '2022-08-11 14:27:09', 'sys');
INSERT INTO mock.mock_condition (id, rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES (8, '10000004', 'params', '[1]', '3', '2022-07-26 14:00:53', 'sys', '2022-07-26 14:07:28', 'sys');
INSERT INTO mock.mock_condition (id, rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES (9, '10000005', 'route', null, '/mock/test/dubbo/provider/service/TestService/helloMap', '2022-07-26 14:07:28', 'sys', '2022-08-11 14:27:09', 'sys');
INSERT INTO mock.mock_condition (id, rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES (10, '10000005', 'params', '[1][name]', 'mock Map', '2022-07-26 14:07:28', 'sys', '2022-07-26 14:16:07', 'sys');
INSERT INTO mock.mock_condition (id, rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES (11, '10000006', 'route', null, '/mock/test/dubbo/provider/service/TestService/helloObject', '2022-07-26 14:24:25', 'sys', '2022-08-11 14:27:09', 'sys');
INSERT INTO mock.mock_condition (id, rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES (12, '10000006', 'params', '[1]', '我是对象', '2022-07-26 14:24:25', 'sys', '2022-07-26 14:27:46', 'sys');
INSERT INTO mock.mock_condition (id, rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES (13, '10000007', 'route', null, '/mock/test/dubbo/provider/service/TestService/helloObject', '2022-07-26 14:24:25', 'sys', '2022-08-11 14:27:09', 'sys');
INSERT INTO mock.mock_condition (id, rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES (14, '10000007', 'params', '[2]', '重载', '2022-07-26 14:24:25', 'sys', '2022-07-26 14:28:20', 'sys');
INSERT INTO mock.mock_condition (id, rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES (15, '10000008', 'route', null, '/mock/test/dubbo/provider/service/TestService/helloVoid', '2022-07-26 14:36:27', 'sys', '2022-08-11 14:27:09', 'sys');
INSERT INTO mock.mock_condition (id, rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES (17, '10000009', 'route', null, '/work/skymoyo/test/service/TestService/helloSpi', '2022-08-06 14:10:14', 'sys', '2022-08-11 14:36:54', 'sys');


