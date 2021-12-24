package com.example.dynamicscheduledemo.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dynamicscheduledemo.config.DynamicSchedulingConfigurer;
import com.example.dynamicscheduledemo.constants.WorkStatusEnum;
import com.example.dynamicscheduledemo.controller.request.AddWorkInfoRequest;
import com.example.dynamicscheduledemo.dao.WorkCronDao;
import com.example.dynamicscheduledemo.dao.WorkInfoDao;
import com.example.dynamicscheduledemo.entity.WorkCronInfo;
import com.example.dynamicscheduledemo.entity.WorkInfo;
import com.example.dynamicscheduledemo.service.WorkInfoService;

/**
 * @author grafie
 * @since 2021/12/24 9:21
 */
@Service
public class WorkInfoServiceImpl implements WorkInfoService {
    private static final Logger logger = LoggerFactory.getLogger(WorkInfoServiceImpl.class);
    @Autowired
    private WorkCronDao workCronDao;
    @Autowired
    private WorkInfoDao workInfoDao;
    @Autowired
    private DynamicSchedulingConfigurer dynamicSchedulingConfigurer;

    @Override
    public void executeWork(Integer workInfoId) {
        logger.info("work work work work，work info id =>{}", workInfoId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addWorkInfo(AddWorkInfoRequest request) {
        WorkInfo workInfo = new WorkInfo();
        workInfo.setWorkName(request.getWorkName());
        workInfo.setWorkContent(request.getWorkContent());
        workInfo.setStatus(WorkStatusEnum.VALID.getCode());
        workInfo.setStartDateTime(LocalDateTime.of(request.getStartDate(), LocalTime.MIN));
        workInfo.setEndDateTime(LocalDateTime.of(request.getEndDate(), LocalTime.of(23, 59, 59)));
        workInfo.setCreateTime(LocalDateTime.now());
        workInfoDao.insert(workInfo);
        WorkCronInfo workCronInfo = new WorkCronInfo();
        // TODO: 2021/12/24 校验cron，或由后端组装
        workCronInfo.setCron(request.getCron());
        workCronInfo.setStatus(WorkStatusEnum.VALID.getCode());
        workCronInfo.setEndDateTime(workInfo.getEndDateTime());
        workCronInfo.setWorkInfoId(workInfo.getId());
        workCronDao.insert(workCronInfo);
        dynamicSchedulingConfigurer.addTask(workCronInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteWorkInfo(Integer workInfoId) {
        workInfoDao.updateStatus(workInfoId, WorkStatusEnum.DELETED.getCode(), LocalDateTime.now());
        workCronDao.updateStatus(workInfoId, WorkStatusEnum.DELETED.getCode());
        dynamicSchedulingConfigurer.stopTask(workInfoId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void invalidWorkInfo(Integer workInfoId) {
        workInfoDao.updateStatus(workInfoId, WorkStatusEnum.INVALID.getCode(), LocalDateTime.now());
        workCronDao.updateStatus(workInfoId, WorkStatusEnum.INVALID.getCode());
        dynamicSchedulingConfigurer.stopTask(workInfoId);
    }
}
