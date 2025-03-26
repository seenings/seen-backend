create schema seen;
use seen;
create table if not exists chat_history
(
    id              int auto_increment comment 'ID'
        primary key,
    from_user_id    int                                  not null comment '发送者-聊天用户',
    to_user_id      int                                  not null comment '接收者-聊天用户',
    content_type_id int                                  not null comment '内容类型：1文本，2图片，3语音，4视频',
    content_id      int                                  not null comment '聊天内容ID，根据content_type_id决定是何种内容',
    sent            tinyint(1) default 1                 not null comment '已发送，默认是发送true',
    send_time       datetime   default CURRENT_TIMESTAMP not null comment '发送时间',
    update_time     datetime   default CURRENT_TIMESTAMP not null comment '更新时间'
);

create table if not exists chat_user
(
    id             int auto_increment comment '自增ID'
        primary key,
    user_id        int                                not null comment '本方用户',
    friend_user_id int                                not null comment '朋友方用户',
    update_time    datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '可以聊天的用户列表';

create table if not exists city
(
    id            int auto_increment comment '自增ID'
        primary key,
    code          varchar(6)                         not null comment '城市代码',
    name          varchar(20)                        not null comment '城市名',
    province_code varchar(6)                         not null comment '省份代码',
    update_time   datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    update_user   int      default 8888              not null comment '更新用户',
    constraint city_code_uindex
        unique (code)
)
    comment '城市';

create table if not exists photo
(
    id          int auto_increment comment '自增ID'
        primary key,
    path        varchar(400)                       not null comment '图像路径',
    deleted     tinyint  default 0                 not null comment '是否删除0未删除',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    update_user int      default 8888              not null comment '更新用户',
    constraint photo_path_uindex
        unique (path)
)
    comment '图像';

create table if not exists province
(
    id          int auto_increment comment '自增ID'
        primary key,
    code        varchar(6)                         not null comment '省份代码',
    name        varchar(10)                        not null comment '省份名称',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    update_user int      default 8888              not null comment '更新用户',
    constraint province_code_uindex
        unique (code),
    constraint province_name_uindex
        unique (name)
)
    comment '省份';

create table if not exists school
(
    id          int auto_increment comment '自增ID'
        primary key,
    school_name varchar(50)                        not null comment '学校全名',
    area_id     varchar(6)                         not null comment '学校所属城市的id',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    update_user int      default 8888              not null comment '更新用户'
)
    comment '学校信息';

create table if not exists sms_code
(
    id          int auto_increment comment '自增ID'
        primary key,
    phone       varchar(20)                        not null comment '手机号码',
    sms_id      int                                not null comment '短信验证码id',
    sms_code    int                                not null comment '短信验证码',
    update_time datetime default CURRENT_TIMESTAMP not null,
    update_user int      default 8888              not null,
    constraint sms_code_pk_2
        unique (phone, sms_id, sms_code)
)
    comment '短信验证码列表';

create table if not exists student_info
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

create table if not exists text
(
    id          int auto_increment comment '自增ID'
        primary key,
    text        varchar(2000)                       not null comment '文本内容',
    deleted     tinyint  default 0                 not null comment '是否删除：0=不删除',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    update_user int      default 8888              not null comment '更新用户'
)
    comment '文本';

create table if not exists user
(
    id          int auto_increment comment '自增ID'
        primary key,
    phone       varchar(20)                        not null comment '手机号码',
    update_time datetime default CURRENT_TIMESTAMP not null,
    update_user int      default 8888              not null,
    constraint user_phone_uindex
        unique (phone)
)
    comment '用户主表';

alter table user
    auto_increment = 10000000;

create table if not exists user_info
(
    id               int auto_increment comment '自增ID'
        primary key,
    user_id          int                                not null comment '用户ID',
    name             varchar(30)                        not null comment '用户姓名',
    sex              tinyint                            not null comment '1男2女',
    profile_photo_id int                                not null comment '头像照片id',
    create_time      datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time      datetime default CURRENT_TIMESTAMP not null,
    update_user      int      default 8888              not null,
    constraint user_info_user_id_uindex
        unique (user_id)
)
    comment '用户信息';


create table if not exists zone
(
    id          int auto_increment comment '自增ID'
        primary key,
    user_id     int                                not null comment '哪个用户发的空间',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '空间';

create table if not exists zone_content
(
    id              int auto_increment comment '自增ID'
        primary key,
    zone_id         int                                not null comment '空间ID',
    content_type_id int                                not null comment '内容类型：1文本，2图片，3语音，4视频',
    content_id      int                                null comment '内容ID',
    update_time     datetime default CURRENT_TIMESTAMP not null comment '修改时间',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '空间内容';

create table if not exists zone_reply
(
    id              int auto_increment comment '自增ID'
        primary key,
    zone_id         int                                not null comment '回复哪一条空间',
    user_id         int                                not null comment '发送用户',
    reply_id        int      default 0                 not null comment '回复哪一条空间回复，默认为0代表不是回复',
    zone_content_id int                                not null comment '回复的内容ID',
    update_time     datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '空间回复';

create table seen.tag
(
    id            int auto_increment comment '自增ID'
        primary key,
    parent_tag_id int          not null comment '父级标签ID',
    tag_name      varchar(100) not null comment '标签名'
)
    comment '标签';

create table seen.tag_need
(
    id          int auto_increment comment '自增ID'
        primary key,
    user_id     int not null comment '用户ID',
    need_tag_id int not null comment '用户需求的ID'
)
    comment '用户需求的标签类型';

create table seen.tag_parent
(
    id       int auto_increment comment '自增ID'
        primary key,
    tag_name varchar(100) not null comment '标签名'
)
    comment '父级标签';

create table seen.tag_user
(
    id      int auto_increment comment '自增ID'
        primary key,
    user_id int not null comment '用户ID',
    tag_id  int not null comment '用户的标签ID'
)
    comment '用户的标签';

create table seen.voice
(
    id          int auto_increment comment '自增ID'
        primary key,
    path        varchar(400)                       not null comment '语音',
    deleted     tinyint  default 0                 not null comment '是否删除0未删除',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    update_user int      default 8888              not null comment '更新用户',
    constraint voice_path_uindex
        unique (path)
)
    comment '语音';



create table seen.file_content
(
    id           int auto_increment comment '自增ID'
        primary key,
    file_name    varchar(100) not null comment '文件名',
    file_content longblob     not null comment '文件内容',
    create_time  datetime(3)  not null comment '创建时间'
)
    comment '文件内容';


create table seen.thumb_user
(
    id              int auto_increment comment '自增ID'
        primary key,
    thumbed_user_id int                                not null comment '被赞者',
    thumb_user_id   int                                not null comment '点赞者',
    deleted     tinyint  default 0                 not null comment '是否删除0未删除',
    update_time     datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint thumb_user_thumb_user_id_thumbed_user_id_uindex
        unique (thumbed_user_id, thumb_user_id)
)
    comment '点赞用户';

create table seen.focus_user
(
    id              int auto_increment comment '自增ID'
        primary key,
    focused_user_id int                                not null comment '被关注者',
    focus_user_id   int                                not null comment '关注者',
    deleted     tinyint  default 0                 not null comment '是否删除0未删除',
    update_time     datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint focus_user_focused_user_id_focus_user_id_uindex
        unique (focused_user_id, focus_user_id)
)
    comment '关注用户';
create table seen.user_sex
(
    id          int auto_increment comment '自增ID'
        primary key,
    user_id     int                                not null comment '用户ID',
    sex         tinyint                            not null comment '性别1（男），2（女）',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '用户性别';
create table seen.user_marital
(
    id          int auto_increment comment '自增ID'
        primary key,
    user_id     int                                not null comment '用户ID',
    marital_status         tinyint                 not null comment '婚姻状况，    ''0已婚'',
    ''1未婚'',
    ''2离异无孩'',
    ''3离异带孩'',
    ''4离异不带孩'',
    ''5丧偶'',',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '婚姻状况';

create table seen.user_birthday
(
    id          int auto_increment comment '自增ID'
        primary key,
    user_id     int                                not null comment '用户ID',
    year        int                                not null comment '出生年份',
    month       int                                null comment '出生月份',
    day         int                                null comment '出生日（所在月）',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '用户出生年月日';

create table seen.user_stature
(
    id          int auto_increment comment '自增ID'
        primary key,
    user_id     int                                not null comment '用户ID',
    stature_cm  int                                not null comment '身高（厘米）',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '用户身高';

create table seen.user_weight
(
    id          int auto_increment comment '自增ID'
        primary key,
    user_id     int                                not null comment '用户ID',
    weight_kg   int                                not null comment '体重（千克）',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '用户的体重';

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

create table seen.middle_user_recommend
(
    id                int auto_increment comment '自增ID'
        primary key,
    user_id           int                                not null comment '用户ID',
    date              varchar(8)                         not null comment '日期YYYYMMDD格式',
    recommend_user_id int                                not null comment '推荐的用户ID',
    update_time       datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '用户推荐列表';

create table seen.user_work_position
(
    id          int auto_increment comment '自增ID'
        primary key,
    user_id     int                                not null comment '用户ID',
    position    int                                not null comment '职位',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '用户的工作职位';
create table seen.user_work_company
(
    id           int auto_increment comment '自增ID'
        primary key,
    user_id      int                                not null comment '用户ID',
    company_name varchar(100)                       not null comment '公司名',
    update_time  datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '用户的工作公司';

create table seen.work_position
(
    id            int auto_increment comment '自增ID'
        primary key,
    position_name varchar(20)                        not null comment '职位名',
    update_time   datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '工作职位';


create table seen.user_income
(
    id          int auto_increment comment '自增ID'
        primary key,
    user_id     int                                not null comment '用户ID',
    annual_income         tinyint                 not null comment '年度收入
    YEAR_MIN_TO_5_W(0, "小于5W"),
    YEAR_5_TO_15_W(1, "5-15W"),
    YEAR_15_TO_30_W(2, "15-30W"),
    YEAR_30_TO_50_W(3, "30-50W"),
    YEAR_50_TO_100_W(4, "50-100W"),
    YEAR_100_TO_500_W(5, "100-500W"),
    YEAR_500_TO_MAX_W(6, "大于500W"),',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '年度收入';

create table seen.user_current_residence
(
    id          int auto_increment comment '自增ID'
        primary key,
    user_id     int                                not null comment '用户ID',
    province_id int                                not null comment '省ID',
    city_id     int                                not null comment '市ID',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '现居地';


create table seen.user_birth_place
(
    id          int auto_increment comment '自增ID'
        primary key,
    user_id     int                                not null comment '用户ID',
    province_id int                                not null comment '省ID',
    city_id     int                                not null comment '市ID',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '出生地';


create table seen.user_auth
(
    id          int auto_increment comment '自增ID'
        primary key,
    user_id     int                                not null comment '用户ID',
    auth_status int                                not null comment '用户认证状态',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '用户认证';

create table seen.user_alias_name
(
    id          int auto_increment comment '自增ID'
        primary key,
    user_id     int                                not null comment '用户ID',
    alias_name  varchar(40)                        not null comment '用户呢称',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '用户呢称';


create table if not exists seen.user_main_photo
(
    id          int auto_increment comment '自增ID'
        primary key,
    user_id     int                                not null comment '用户ID',
    photo_id    int                                not null comment '用户照片ID',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '用户头像主照片';

create table if not exists seen.user_introduce
(
    id             int auto_increment comment '自增ID'
        primary key,
    user_id        int                                not null comment '用户ID',
    introduce_type int                                not null comment '介绍类型，IntroduceTypeEnum',
    text_id        int                                not null comment '文本ID，对应text',
    update_time    datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '用户介绍';
create table if not exists seen.user_introduce_photo
(
    id             int auto_increment comment '自增ID'
        primary key,
    user_id        int                                not null comment '用户ID',
    introduce_type int                                not null comment '介绍类型，IntroduceTypeEnum',
    photo_id       int                                not null comment '照片ID，对应photo',
    order_num      int                                not null comment '顺序',
    update_time    datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '用户介绍';

create table if not exists seen.user_apply
(
    id              int auto_increment comment '自增ID，申请单ID'
        primary key,
    user_id         int                                not null comment '用户ID',
    text_id         int                                not null comment '申请消息，对应text表',
    applied_user_id int                                not null comment '被申请方用户ID',
    apply_time      datetime default CURRENT_TIMESTAMP not null comment '申请时间',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '用户申请';

create table if not exists seen.user_apply_trade
(
    id              int auto_increment comment '自增ID，申请单ID'
        primary key,
    apply_id    int                                not null comment '申请单ID',
    trade_id    int                                not null comment '交易ID',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '用户申请与交易';

create table if not exists seen.user_apply_agree
(
    id          int auto_increment comment '自增ID'
        primary key,
    apply_id    int                                not null comment '申请单ID',
    agree_time  datetime default CURRENT_TIMESTAMP not null comment '同意时间',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '用户申请同意';
create table if not exists seen.user_apply_refuse
(
    id          int auto_increment comment '自增ID'
        primary key,
    apply_id    int                                not null comment '申请单ID',
    text_id     int                                not null comment '拒绝消息，对应text表',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '用户申请拒绝';
create table if not exists seen.user_apply_look
(
    id          int auto_increment comment '自增ID'
        primary key,
    apply_id    int                                not null comment '申请单ID',
    look_time   datetime default CURRENT_TIMESTAMP not null comment '查看时间',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '用户申请查看过';



create table if not exists seen.coin_account
(
    id           int auto_increment comment '自增ID，账户ID'
        primary key,
    account_type int                                not null comment '账户类型',
    description  varchar(100)                       not null comment '账户描述，非结构化字段',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '币账户';


alter table seen.coin_account
    auto_increment = 100000;

create table if not exists seen.coin_account_user
(
    id          int auto_increment comment '自增ID'
        primary key,
    account_id  int                                not null comment '账户ID',
    user_id     int                                not null comment '用户ID',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '用户的账户';


create table if not exists seen.coin_trade
(
    id             int auto_increment comment '自增ID，币流水ID'
        primary key,
    in_account_id  int                                not null comment '进入账户ID，增加',
    out_account_id int                                not null comment '出去账户ID，减少',
    coin_amount    int                                not null comment '币个数',
    description    varchar(100)                       not null comment '交易描述，非结构化字段',
    trade_time     datetime default CURRENT_TIMESTAMP not null comment '交易时间',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '币交易流水';
create table if not exists seen.coin_account_balance
(
    id          int auto_increment comment '自增ID'
        primary key,
    account_id  int                                not null comment '账户ID',
    coin_amount int                                not null comment '账户余额',
    change_time datetime default CURRENT_TIMESTAMP not null comment '余额变动时间',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '币账户余额';
create table if not exists seen.coin_sys_account_balance
(
    id          int auto_increment comment '自增ID'
        primary key,
    account_id  int                                not null comment '账户ID',
    coin_amount int                                not null comment '账户余额',
    change_time datetime default CURRENT_TIMESTAMP not null comment '余额变动时间',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '系统币账户余额';

create table seen.coin_trade_type
(
    id            int auto_increment comment '自增ID'
        primary key,
    trade_id      int                                not null comment '交易ID，对应coin_trade',
    trade_type_id int                                not null comment '交易类型ID',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '交易类型';

create table seen.coin_transfer
(
    id              int auto_increment comment '自增ID，充值单ID'
        primary key,
    channel_type    int                                not null comment '渠道类型',
    channel_account varchar(100)                       not null comment '渠道账号',
    user_id         int                                not null comment '用户ID',
    money           int                                not null comment '充值金额',
    trade_time      datetime default CURRENT_TIMESTAMP not null comment '充值时间',
    trade_id        int                                null comment '交易ID',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '用户充值';

create table seen.trade_register
(
    id            int auto_increment comment '自增ID'
        primary key,
    user_id       int                                not null comment '用户ID',
    register_time int                                not null comment '注册时间',
    trade_id      int                                not null comment '交易ID',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '用户注册信息';

-- 照片
create table user_introduce_photo_to_photo
(
    user_introduce_photo_id int not null,
    photo_id                int not null,
    constraint user_introduce_photo_to_photo_pk
        primary key (user_introduce_photo_id)
)
comment '介绍的图片对应关系';

-- 照片
create table main_photo
(
    id      int auto_increment comment '照片ID'
        primary key,
    file_id int null comment '文件ID'
)
    comment '主要的照片';

-- 文件
create table file
(
    id           int auto_increment comment '文件ID'
        primary key,
    storage_type int           not null comment '文件存储类型',
    path         varchar(2000) not null comment '路径',
    name         varchar(2000) not null comment '文件名称'
)
    comment '文件';

-- 玫瑰币记账
create table coin_book
(
    trade_id         bigint auto_increment comment '交易ID'
        primary key,
    amount           bigint not null comment '数量',
    debit_id         bigint not null comment '借方',
    credit_id        bigint not null comment '贷方',
    transaction_time  datetime not null comment '成交时间'
)
    comment '玫瑰币记账';

-- 玫瑰币余额
create table coin_balance
(
    debit_or_credit_id         bigint not null comment '借方/贷方ID'
       primary key,
    balance           bigint not null comment '余额',
    transaction_time  datetime not null comment '成交时间'
)
    comment '玫瑰币余额';

-- 交易与业务关系
create table trade_and_busi
(
    trade_id    bigint not null comment '交易ID'
        primary key,
    busi_id     bigint not null comment '业务ID',
    trade_time  datetime not null comment '交易时间'
)
    comment '交易与业务关系';
