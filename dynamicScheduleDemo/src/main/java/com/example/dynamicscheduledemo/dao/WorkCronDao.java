package com.example.dynamicscheduledemo.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.dynamicscheduledemo.entity.WorkCronInfo;

/**
 * @author grafie
 * @since 2021/12/24 10:42
 */
@Repository
public interface WorkCronDao {
    /**
     * 根据status查询定时任务信息
     *
     * @param status 状态
     * @return java.util.List<com.example.dynamicscheduledemo.entity.WorkCronInfo>
     */
    List<WorkCronInfo> queryCronByStatusAndEndDateTime(@Param("status") Integer status,
        @Param(("endDateTime")) LocalDateTime endDateTime);

    /**
     * 新增
     *
     * @param workCronInfo 新增信息
     * @return java.lang.Integer  id
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(WorkCronInfo workCronInfo);

    /**
     * 删除
     *
     * @param workInfoId work_info_id
     * @param status 状态码
     */

    void updateStatus(Integer workInfoId, Integer status);
}
