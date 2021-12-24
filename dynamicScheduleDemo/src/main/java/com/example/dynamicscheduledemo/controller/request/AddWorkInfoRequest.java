package com.example.dynamicscheduledemo.controller.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author grafie
 * @since 2021/12/24 13:35
 */
@Data
public class AddWorkInfoRequest {
    /** 工作名称 */
    private String workName;
    /** 开始时间 */
    private LocalDate startDate;
    /** 结束时间 */
    private LocalDate endDate;
    /** 工作内容 */
    private String workContent;
    /** 定时任务表达式 */
    private String cron;
}
