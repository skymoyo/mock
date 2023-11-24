INSERT INTO mock.mock_config (mock_code, name, route, create_time, create_user, update_time, update_user) VALUES ('10000001', '返回list<pojo>', '/work/skymoyo/test/service/RemoteTestService/getPerson', '2023-10-25 14:48:21', 'sys', '2023-10-25 14:48:21', 'sys');
INSERT INTO mock.mock_config (mock_code, name, route, create_time, create_user, update_time, update_user) VALUES ('10000002', '获取请求参数返回数据', '/work/skymoyo/test/service/RemoteTestService/spelResp', '2023-11-04 07:37:18', 'sys', '2023-11-04 07:37:18', 'sys');

alter table mock_rule modify rule_type varchar(24) not null comment '结果生成类型 10 直接返回 20 动态生成  spel 解析参数';


INSERT INTO mock.mock_rule (mock_code, rule_code, rule_name, rule_type, rule_result, create_time, create_user, update_time, update_user) VALUES ('10000001', '11000001', '1', 'spel', '[{"name":"''+#encryptHex+''","age":12,"childs":[{"name":"''+#encryptHex+''","age":1,"childs":[{"name":"foo11","age":1}]}]},{"name":"bar","age":23,"childs":[{"name":"bar1","age":1,"childs":[{"name":"bar11","age":1}]},{"name":"bar2","age":2,"childs":[{"name":"bar21","age":412}]}]}]', '2023-10-25 14:48:56', 'sys', '2023-11-01 15:31:36', 'sys');
INSERT INTO mock.mock_rule (mock_code, rule_code, rule_name, rule_type, rule_result, create_time, create_user, update_time, update_user) VALUES ('10000002', '11000002', '1', 'spel', '{"name":"''+#person[childs][1][name]+''","age":"''+#person[childs][0][age]+''","childs":[{"name":"''+#person[name]+''Chaild","age":1}]}', '2023-11-04 07:39:38', 'sys', '2023-11-04 15:23:52', 'sys');


INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES ('11000001', 'route', null, '/work/skymoyo/test/service/RemoteTestService/getPerson', '2023-10-25 14:49:15', 'sys', '2023-10-25 14:49:15', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES ('11000002', 'route', '', '/work/skymoyo/test/service/RemoteTestService/spelResp', '2023-11-04 07:40:00', 'sys', '2023-11-04 07:40:00', 'sys');




alter table mock_condition modify condition_key varchar(255) null comment '条件EL表达式';


alter table mock_condition drop column create_user;

INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('10000001', 'route', '#route.endsWith("test/dubbo/provider/service/TestService/helloString")', '2022-07-26 03:23:37', 'sys', '2023-11-07 13:34:04', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('10000001', 'params', '#arg0.equals("foo")', '2022-07-26 03:24:51', 'sys', '2023-11-07 13:32:50', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('10000002', 'route', '#route.endsWith("test/dubbo/provider/service/TestService/helloInteger")', '2022-07-26 06:41:05', 'sys', '2023-11-07 13:34:04', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('10000002', 'params', '#arg0 == 18', '2022-07-26 07:01:05', 'sys', '2023-11-07 13:33:18', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('10000003', 'route', '#route.endsWith("test/dubbo/provider/service/TestService/helloInt")', '2022-07-26 10:48:31', 'sys', '2023-11-07 13:34:04', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('10000003', 'params', '#arg0 == 1', '2022-07-26 10:48:31', 'sys', '2023-11-07 13:34:30', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('10000004', 'route', '#route.endsWith("test/dubbo/provider/service/TestService/helloList")', '2022-07-26 10:48:31', 'sys', '2023-11-07 13:34:30', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('10000004', 'params', '#arg1 == 3', '2022-07-26 14:00:53', 'sys', '2023-11-07 13:34:30', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('10000005', 'route', '#route.endsWith("test/dubbo/provider/service/TestService/helloMap")', '2022-07-26 14:07:28', 'sys', '2023-11-07 13:34:47', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('10000005', 'params', '#arg1[name].equals("mock Map")', '2022-07-26 14:07:28', 'sys', '2023-11-07 13:35:02', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('10000006', 'route', '#route.endsWith("test/dubbo/provider/service/TestService/helloObject")', '2022-07-26 14:24:25', 'sys', '2023-11-07 13:35:48', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('10000006', 'params', '#arg1.equals("我是对象")', '2022-07-26 14:24:25', 'sys', '2023-11-07 13:35:24', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('10000007', 'route', '#route.endsWith("test/dubbo/provider/service/TestService/helloObject")', '2022-07-26 14:24:25', 'sys', '2023-11-07 13:35:58', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('10000007', 'params', '#arg2.equals("重载")', '2022-07-26 14:24:25', 'sys', '2023-11-07 13:32:42', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('10000008', 'route', '#route.endsWith("test/dubbo/provider/service/TestService/helloVoid")', '2022-07-26 14:36:27', 'sys', '2023-11-07 13:35:58', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('10000009', 'route', '#route.endsWith("/work/skymoyo/test/service/TestService/helloSpi")', '2022-08-06 14:10:14', 'sys', '2023-11-07 13:36:07', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('11000001', 'route', '#route.startsWith("/work/skymoyo/test/service/RemoteTestService")', '2023-10-25 14:49:15', 'sys', '2023-11-07 14:43:36', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, create_time, create_user, update_time, update_user) VALUES ('11000002', 'route', '#route.equals("/work/skymoyo/test/service/RemoteTestService/spelResp")', '2023-11-04 07:40:00', 'sys', '2023-11-07 14:43:36', 'sys');