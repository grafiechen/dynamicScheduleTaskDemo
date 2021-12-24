package com.example.dynamicscheduledemo.dao;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.dynamicscheduledemo.entity.WorkInfo;

/**
 * @author grafie
 * @since 2021/12/24 9:22
 */
@Repository
public interface WorkInfoDao {
    /**
     * 根据id查询
     *
     * @param workInfoId id
     * @return WorkInfo
     */
    WorkInfo selectById(@Param("workInfoId") Integer workInfoId);

    /**
     * 查询状态
     *
     * @param workInfoId id
     * @return java.lang.Integer status
     */
    Integer queryWorkInfoStatus(@Param("workInfoId") Integer workInfoId);

    /**
     * 新增
     *
     * @param workInfo 新增参数
     * @return java.lang.Integer 自增id
     */
    void insert(WorkInfo workInfo);

    /**
     * 更新状态
     *
     * @param workInfoId id
     * @param status 状态
     */
    void updateStatus(@Param("workInfoId") Integer workInfoId, @Param("status") Integer status,
        @Param("updateTime") LocalDateTime updateTime);
}
