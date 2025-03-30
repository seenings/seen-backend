create schema if not exists seen;
use seen;
create table seen.educational
(
    id          int auto_increment comment '自增ID'
        primary key,
    user_id     int                                not null comment '用户ID',
    educational int                                not null comment '学历（0：其他，1：大专，2：本科，3：硕士，4：博士）',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '用户的学历';


create table seen.school_graduate
(
    id          int auto_increment comment '自增ID'
        primary key,
    user_id     int                                not null comment '用户ID',
    graduated   int                                not null comment '是否毕业0（否），1（是）',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '用户的毕业状态';


create table seen.school
(
    id          int auto_increment comment '自增ID'
        primary key,
    school_name varchar(50)                        not null comment '学校全名',
    area_id     varchar(6)                         not null comment '学校所属城市的id',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    update_user int      default 8888              not null comment '更新用户'
)
    comment '学校信息';

create table  seen.student_info
(
    id              int auto_increment comment '自增ID'
        primary key,
    user_id         int                                not null comment '用户id',
    school_id       int                                not null comment '学校id',
    create_time     datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time     datetime default CURRENT_TIMESTAMP not null,
    update_user     int      default 8888              not null,
    constraint student_info_user_id_uindex
        unique (user_id)
)
    comment '学生信息表';
