package com.example.dynamicscheduledemo.entity;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author grafie
 * @since 2021/12/24 9:10
 */
@Data
public class WorkInfo {
    /** 主键 */
    private Integer id;
    /** 工作名称 */
    private String workName;
    /** 开始时间 */
    private LocalDateTime startDateTime;
    /** 工作内容 */
    private String workContent;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 更新时间 */
    private LocalDateTime updateTime;
    /** 结束时间 */
    private LocalDateTime endDateTime;
    /** 状态 0:启用, 1:禁用 -1:删除 */
    private Integer status;
}
