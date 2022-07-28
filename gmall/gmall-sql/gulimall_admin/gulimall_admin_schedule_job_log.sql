create table schedule_job_log
(
    log_id      bigint auto_increment comment '任务日志id'
        primary key,
    job_id      bigint        not null comment '任务id',
    bean_name   varchar(200)  null comment 'spring bean名称',
    params      varchar(2000) null comment '参数',
    status      tinyint       not null comment '任务状态    0：成功    1：失败',
    error       varchar(2000) null comment '失败信息',
    times       int           not null comment '耗时(单位：毫秒)',
    create_time datetime      null comment '创建时间'
)
    comment '定时任务日志';

create index job_id
    on schedule_job_log (job_id);

