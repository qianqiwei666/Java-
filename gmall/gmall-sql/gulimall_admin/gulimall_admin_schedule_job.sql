create table schedule_job
(
    job_id          bigint auto_increment comment '任务id'
        primary key,
    bean_name       varchar(200)  null comment 'spring bean名称',
    params          varchar(2000) null comment '参数',
    cron_expression varchar(100)  null comment 'cron表达式',
    status          tinyint       null comment '任务状态  0：正常  1：暂停',
    remark          varchar(255)  null comment '备注',
    create_time     datetime      null comment '创建时间'
)
    comment '定时任务';

INSERT INTO gulimall_admin.schedule_job (job_id, bean_name, params, cron_expression, status, remark, create_time) VALUES (1, 'testTask', 'renren', '0 0/30 * * * ?', 0, '参数测试', '2022-07-11 02:49:09');
