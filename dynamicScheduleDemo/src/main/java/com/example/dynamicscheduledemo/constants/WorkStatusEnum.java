package com.example.dynamicscheduledemo.constants;

/**
 * workInfo状态枚举
 *
 * @author grafie
 * @since 2021/12/24 10:22
 */
public enum WorkStatusEnum {
    /** 0: 有效，1：失效，-1：删除 */
    VALID(0),
    INVALID(1),
    DELETED(-1);
    private final Integer code;

    WorkStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
