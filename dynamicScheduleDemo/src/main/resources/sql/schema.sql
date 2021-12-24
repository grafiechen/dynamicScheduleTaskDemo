create table t_work_info
(
    id              int auto_increment not null primary key,
    work_name       varchar(20) not null COMMENT "工作名称",
    start_date_time timestamp,
    work_content    varchar(50) not null,
    create_time     timestamp   not null,
    update_time     timestamp,
    status          int(1) not null COMMENT "状态 -1删除，0 启用，1 停用"
) ;
create table t_work_cron
(
    id            int auto_increment not null primary key,
    cron          varchar(20) not null COMMENT "定时任务 ron 表达式",
    end_date_time timestamp,
    work_info_id  int         not null COMMENT "t_work_info表id",
    status        int(1) not null  COMMENT "状态 -1删除，0 启用，1 停用"
)