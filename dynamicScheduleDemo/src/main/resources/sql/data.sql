insert into t_work_info(id, work_name, start_date_time, work_content, create_time, status)
values (1, 'work_1', '2021-12-24 11:22:30', 'work work work', '2021-12-24 11:22:30', 0),
       (2, 'work_2', '2021-12-28 11:22:30', 'work2 work2 work2', '2021-12-24 11:22:30', 0);

insert into t_work_cron(id, cron, end_date_time, work_info_id, status)
values (1, '0 9 11 * * ?', '2021-12-31 12:00:00', 1, 0),
       (2, '* * * * * ?', '2021-12-31 12:00:00', 2, 0);