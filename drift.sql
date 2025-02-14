create table tbl_users
(
    id                bigint                             not null comment '用户ID'
        primary key,
    phone_number      varchar(15)                        not null comment '手机号',
    username          varchar(255)                       null comment '用户名称',
    password          varchar(255)                       null comment '用户密码',
    avatar_url        varchar(255)                       null comment '头像URL',
    bio               varchar(255)                       null comment '简介',
    birthday          date                               null comment '生日',
    gender            enum ('male', 'female')            null comment '性别',
    region            varchar(255)                       null comment '地区',
    occupation        varchar(255)                       null comment '职业',
    school            varchar(100)                       null comment '学校',
    enrollment_date   date                               null comment '入学时间',
    following_count   int      default 0                 not null comment '关注数',
    follower_count    int      default 0                 not null comment '粉丝数',
    likes_count       int      default 0                 not null comment '点赞数',
    collections_count int      default 0                 not null comment '收藏数',
    create_time       datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time       datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint tbl_users_unique
        unique (phone_number)
)
    comment '用户表';
create table tbl_posts
(
    id           bigint                             not null comment '动态ID'
        primary key,
    user_id      bigint                             not null comment '发布动态的用户ID',
    title        varchar(255)                       not null comment '主标题',
    content      text                               null comment '动态内容',
    image_url    varchar(255)                       null comment '图片URL',
    random_order varchar(255)                       not null comment '唯一的随机排序值',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '动态表';
create table tbl_likes
(
    id          bigint unsigned auto_increment comment '点赞ID'
        primary key,
    post_id     bigint                             not null comment '点赞的动态ID',
    author_id   bigint                             not null comment '动态的作者ID',
    user_id     bigint                             not null comment '点赞的用户ID',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '点赞表';
create table tbl_collections
(
    id          bigint unsigned auto_increment comment '收藏ID'
        primary key,
    post_id     bigint                             not null comment '收藏的动态ID',
    author_id   bigint                             not null comment '动态的作者ID',
    user_id     bigint                             not null comment '收藏的用户ID',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '收藏表';
create table tbl_follows
(
    id          bigint unsigned auto_increment comment '关注ID'
        primary key,
    follower_id bigint                             not null comment '关注者用户ID',
    followed_id bigint                             not null comment '被关注者用户ID',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '关注表';
create table tbl_comment
(
    id                bigint unsigned auto_increment comment '评论ID'
        primary key,
    post_id           bigint                             not null comment '评论的动态ID',
    user_id           bigint                             not null comment '评论的用户ID',
    parent_comment_id bigint unsigned                    null comment '父评论ID',
    reply_to_user_id  bigint                             null comment '回复的目标用户ID',
    content           varchar(255)                       not null comment '评论内容',
    random_order      varchar(255)                       not null comment '唯一的随机排序值',
    first_comment     tinyint(1) default 0               not null comment '首评标志',
    topped            tinyint(1) default 0               not null comment '置顶标志',
    create_time       datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '评论表';
create table tbl_comment_likes
(
    id          bigint unsigned auto_increment comment '评论点赞ID'
        primary key,
    comment_id  bigint unsigned                    not null comment '点赞的评论ID',
    user_id     bigint                             not null comment '点赞的用户ID',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '评论点赞表';
