create table sys_config
(
    id          bigint auto_increment
        primary key,
    param_key   varchar(50)       null comment 'key',
    param_value varchar(2000)     null comment 'value',
    status      tinyint default 1 null comment '状态   0：隐藏   1：显示',
    remark      varchar(500)      null comment '备注',
    constraint param_key
        unique (param_key)
)
    comment '系统配置信息表';

INSERT INTO gulimall_admin.sys_config (id, param_key, param_value, status, remark) VALUES (1, 'CLOUD_STORAGE_CONFIG_KEY', '{"aliyunAccessKeyId":"","aliyunAccessKeySecret":"","aliyunBucketName":"","aliyunDomain":"","aliyunEndPoint":"","aliyunPrefix":"","qcloudBucketName":"","qcloudDomain":"","qcloudPrefix":"","qcloudSecretId":"","qcloudSecretKey":"","qiniuAccessKey":"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ","qiniuBucketName":"ios-app","qiniuDomain":"http://7xqbwh.dl1.z0.glb.clouddn.com","qiniuPrefix":"upload","qiniuSecretKey":"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV","type":1}', 0, '云存储配置信息');
