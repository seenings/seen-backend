create table busi
(
    busi_id    bigint auto_increment comment '业务ID'
    primary key,
    busi_type_id int not null comment '业务类型ID',
    busi_time  datetime not null comment '业务时间'
);
comment on table busi is '业务';