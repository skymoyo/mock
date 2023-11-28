alter table mock_rule
	add block_time bigint(20) null comment '堵塞时长单位秒' after rule_class;;

