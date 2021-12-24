# dynamicScheduleTaskDemo
一个基于数据库动态管理定时任务的demo

基于MySQL作为持久化，管理定时任务。

对定时任务管理主要是实现``SchedulingConfigurer``接口，并在实现类中维护一个``ConcurrentHashMap<Integer, ScheduledFuture<?>>``,通过对map的操作，做到添加、停止定时任务的功能。

### DDL

```sql
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
```

