package com.example.dynamicscheduledemo.service;

import com.example.dynamicscheduledemo.controller.request.AddWorkInfoRequest;

/**
 * @author grafie
 * @since 2021/12/24 9:20
 */
public interface WorkInfoService {
    /**
     * 根据id执行
     *
     * @param workInfoId t_work_info id
     */
    void executeWork(Integer workInfoId);

    /**
     * 增加workInfo
     *
     * @param request 新增参数
     */
    void addWorkInfo(AddWorkInfoRequest request);

    /**
     * 删除workInfo
     *
     * @param workInfoId id
     */
    void deleteWorkInfo(Integer workInfoId);

    /**
     * 设置失效
     *
     * @param workInfoId id
     */
    void invalidWorkInfo(Integer workInfoId);
}
