INSERT INTO mock.mock_config (mock_code, name, route, create_time, create_user, update_time, update_user) VALUES ('10000001', '返回list<pojo>', '/work/skymoyo/test/service/RemoteTestService/getPerson', '2023-10-25 14:48:21', 'sys', '2023-10-25 14:48:21', 'sys');
INSERT INTO mock.mock_config (mock_code, name, route, create_time, create_user, update_time, update_user) VALUES ('10000002', '获取请求参数返回数据', '/work/skymoyo/test/service/RemoteTestService/spelResp', '2023-11-04 07:37:18', 'sys', '2023-11-04 07:37:18', 'sys');

alter table mock_rule modify rule_type varchar(24) not null comment '结果生成类型 10 直接返回 20 动态生成  spel 解析参数';


INSERT INTO mock.mock_rule (mock_code, rule_code, rule_name, rule_type, rule_result, create_time, create_user, update_time, update_user) VALUES ('10000001', '11000001', '1', 'spel', '[{"name":"''+#encryptHex+''","age":12,"childs":[{"name":"''+#encryptHex+''","age":1,"childs":[{"name":"foo11","age":1}]}]},{"name":"bar","age":23,"childs":[{"name":"bar1","age":1,"childs":[{"name":"bar11","age":1}]},{"name":"bar2","age":2,"childs":[{"name":"bar21","age":412}]}]}]', '2023-10-25 14:48:56', 'sys', '2023-11-01 15:31:36', 'sys');
INSERT INTO mock.mock_rule (mock_code, rule_code, rule_name, rule_type, rule_result, create_time, create_user, update_time, update_user) VALUES ('10000002', '11000002', '1', 'spel', '{"name":"''+#person[childs][1][name]+''","age":"''+#person[childs][0][age]+''","childs":[{"name":"''+#person[name]+''Chaild","age":1}]}', '2023-11-04 07:39:38', 'sys', '2023-11-04 15:23:52', 'sys');


INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES ('11000001', 'route', null, '/work/skymoyo/test/service/RemoteTestService/getPerson', '2023-10-25 14:49:15', 'sys', '2023-10-25 14:49:15', 'sys');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key, condition_value, create_time, create_user, update_time, update_user) VALUES ('11000002', 'route', '', '/work/skymoyo/test/service/RemoteTestService/spelResp', '2023-11-04 07:40:00', 'sys', '2023-11-04 07:40:00', 'sys');