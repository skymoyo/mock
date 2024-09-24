alter table mock_rule
	add block_time bigint(20) null comment '堵塞时长单位秒' after rule_class;;

create table mock_record
(
	ID bigint auto_increment comment 'id',
	app_id varchar(32) null comment '项目id',
	app_name varchar(32) null comment '项目名称',
	thread_id int null comment '线程id',
  uuid varchar(64) null comment '请求流水',
  client varchar(32) null comment '请求客户端',
  route longtext null comment '请求路由',
	mock_req longtext null comment '请求数据',
	mock_resp longtext null comment '返回数据',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null comment '更新时间',
	constraint mock_record_pk
	primary key (ID)
)
	comment 'mock记录';

create index mock_record_app_id_index
	on mock_record (app_id);

INSERT INTO mock.mock_config (mock_code, name, route) VALUES ('admin000', 'mockui_logout', '/admin/logout');
INSERT INTO mock.mock_config (mock_code, name, route) VALUES ('admin010', 'mockui_login', '/admin/login');
INSERT INTO mock.mock_config (mock_code, name, route) VALUES ('admin020', 'mockui_getInfo', '/admin/getInfo');
INSERT INTO mock.mock_config (mock_code, name, route) VALUES ('admin030', 'mockui_getRouters', '/admin/getRouters');


INSERT INTO mock.mock_rule (mock_code, rule_code, rule_name, rule_type, rule_result) VALUES ('admin000', 'admin000', '退出', '10', '{"code":0,"msg":"成功"}');
INSERT INTO mock.mock_rule (mock_code, rule_code, rule_name, rule_type, rule_result) VALUES ('admin010', 'admin011', '登录成功', '10', '{"code":0,"msg":"成功"}');
INSERT INTO mock.mock_rule (mock_code, rule_code, rule_name, rule_type, rule_result) VALUES ('admin010', 'admin012', '登录失败', '10', '{"code":500,"msg":"失败"}');
INSERT INTO mock.mock_rule (mock_code, rule_code, rule_name, rule_type, rule_result) VALUES ('admin020', 'admin020', 'mockui登录用户信息', '10', '{
    "msg": "操作成功",
    "code": 200,
    "permissions": [
        "*:*:*"
    ],
    "roles": [
        "admin"
    ],
    "user": {
        "createBy": "admin",
        "createTime": "2023-04-23 16:11:38",
        "updateBy": null,
        "updateTime": null,
        "remark": "管理员",
        "userId": 1,
        "deptId": 103,
        "userName": "admin",
        "nickName": "mock",
        "email": "mock@163.com",
        "phonenumber": "15888888888",
        "sex": "1",
        "avatar": "",
        "password": "$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2",
        "status": "0",
        "delFlag": "0",
        "loginIp": "39.149.242.94",
        "loginDate": "2023-12-06T23:25:12.000+08:00",
        "dept": {
            "createBy": null,
            "createTime": null,
            "updateBy": null,
            "updateTime": null,
            "remark": null,
            "deptId": 103,
            "parentId": 101,
            "ancestors": "0,100,101",
            "deptName": "研发部门",
            "orderNum": 1,
            "leader": "mock",
            "phone": null,
            "email": null,
            "status": "0",
            "delFlag": null,
            "parentName": null,
            "children": []
        },
        "roles": [
            {
                "createBy": null,
                "createTime": null,
                "updateBy": null,
                "updateTime": null,
                "remark": null,
                "roleId": 1,
                "roleName": "超级管理员",
                "roleKey": "admin",
                "roleSort": 1,
                "dataScope": "1",
                "menuCheckStrictly": false,
                "deptCheckStrictly": false,
                "status": "0",
                "delFlag": null,
                "flag": false,
                "menuIds": null,
                "deptIds": null,
                "permissions": null,
                "admin": true
            }
        ],
        "roleIds": null,
        "postIds": null,
        "roleId": null,
        "admin": true
    }
}');
INSERT INTO mock.mock_rule (mock_code, rule_code, rule_name, rule_type, rule_result) VALUES ('admin030', 'admin030', 'mockui菜单信息', '10', '{
    "msg":"操作成功",
    "code":200,
    "data":[
        {
            "children":[
                {
                    "children":[

                    ],
                    "createTime":1702874838000,
                    "icon":"fa fa-pencil-square-o",
                    "isRefresh":"1",
                    "menuId":2003,
                    "menuName":"mock请求记录",
                    "menuType":"C",
                    "orderNum":"1",
                    "params":{

                    },
                    "parentId":2001,
                    "perms":"",
                    "target":"menuItem",
                    "url":"/admin/record/index",
                    "visible":"0"
                },
                {
                    "children":[

                    ],
                    "createTime":1702874838000,
                    "icon":"fa fa-pencil-square-o",
                    "isRefresh":"1",
                    "menuId":2003,
                    "menuName":"mock配置",
                    "menuType":"C",
                    "orderNum":"2",
                    "params":{

                    },
                    "parentId":2001,
                    "perms":"",
                    "target":"menuItem",
                    "url":"/admin/config/index",
                    "visible":"0"
                }
            ],
            "createTime":1702874720000,
            "icon":"fa fa-sticky-note-o",
            "isRefresh":"1",
            "menuId":2001,
            "menuName":"mock",
            "menuType":"M",
            "orderNum":"1",
            "params":{

            },
            "parentId":0,
            "perms":"",
            "target":"menuItem",
            "url":"#",
            "visible":"0"
        },
        {
            "children":[

            ],
            "createTime":1702875077000,
            "icon":"fa fa-cog",
            "isRefresh":"1",
            "menuId":2007,
            "menuName":"服务监控",
            "menuType":"C",
            "orderNum":"9999",
            "params":{

            },
            "parentId":0,
            "perms":"",
            "target":"menuItem",
            "url":"/admin/server/index",
            "visible":"0"
        }
    ]
}');


INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key) VALUES ('admin000', 'route', '#route.equals("/admin/logout")');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key) VALUES ('admin011', 'params', '(#username?:'''').equals("admin") and (#password?:'''').equals("admin123")');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key) VALUES ('admin012', 'params', '!(#username?:'''').equals("admin") or !(#password?:'''').equals("admin123")');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key) VALUES ('admin020', 'route', '#route.equals("/admin/getInfo")');
INSERT INTO mock.mock_condition (rule_code, condition_type, condition_key) VALUES ('admin030', 'route', '#route.equals("/admin/getRouters")');



