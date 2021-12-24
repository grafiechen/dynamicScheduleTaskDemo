package com.example.dynamicscheduledemo.schedule;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.dynamicscheduledemo.entity.WorkCronInfo;
import com.example.dynamicscheduledemo.service.WorkInfoService;

/**
 * @author grafie
 * @since 2021/12/24 9:03
 */
public class WorkTaskRunnable extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(WorkTaskRunnable.class);
    private final WorkCronInfo workCronInfo;
    private final WorkInfoService workInfoService;

    public WorkTaskRunnable(WorkCronInfo workCronInfo, WorkInfoService workInfoService) {
        this.workCronInfo = workCronInfo;
        this.workInfoService = workInfoService;
    }

    @Override
    public void run() {
        try {
            logger.info("start work, work info id =>{}, work cron id =>{},start date time=>{}",
                workCronInfo.getWorkInfoId(), workCronInfo.getId(), LocalDateTime.now());
            workInfoService.executeWork(workCronInfo.getWorkInfoId());
            logger.info("finish work, work info id =>{}, work cron id =>{},end date time=>{}",
                workCronInfo.getWorkInfoId(), workCronInfo.getId(), LocalDateTime.now());
        } catch (Exception e) {
            logger.error("执行任务出错，work info id =>{}, work cron id =>{}, date time=>{}", workCronInfo.getWorkInfoId(),
                workCronInfo.getId(), LocalDateTime.now());
        }
    }
}
