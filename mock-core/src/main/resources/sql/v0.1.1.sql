alter table mock_rule
	add block_time bigint(20) null comment '堵塞时长单位秒' after rule_class;;

create table mock_record
(
	ID bigint auto_increment comment 'id',
	app_id varchar(32) null comment '项目id',
	app_name varchar(32) null comment '项目名称',
	thread_id int null comment '线程id',
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

