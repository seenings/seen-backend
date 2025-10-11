create table busi_register
(
    register_id    bigint auto_increment comment '自增ID'
        primary key,
    user_id       bigint                                not null comment '用户ID',
    register_time datetime default CURRENT_TIMESTAMP                                not null comment '注册时间',
    busi_id       bigint                                not null comment '业务ID',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间'
);
comment on table busi_register is '用户注册信息';
