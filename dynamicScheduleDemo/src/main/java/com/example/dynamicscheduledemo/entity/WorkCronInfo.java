package com.example.dynamicscheduledemo.entity;

import java.time.LocalDateTime;

import org.springframework.scheduling.support.CronTrigger;

import lombok.Data;

/**
 * @author grafie
 * @since 2021/12/24 8:58
 */
@Data
public class WorkCronInfo {
    /** t_work_cron id */
    private Integer id;
    /** t_work_info id */
    private Integer workInfoId;
    /** 定时任务表达式, {@link CronTrigger} expression */
    private String cron;
    /** 0:启用，1：停用，-1:删除 */
    private Integer status;
    /** 结束时间 */
    private LocalDateTime endDateTime;
}
