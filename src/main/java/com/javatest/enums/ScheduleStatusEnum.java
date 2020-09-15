package com.javatest.enums;

public enum ScheduleStatusEnum {
    // 可运行的
    RUNNABLE(0),
    // 暂停
    PAUSE(1),
    // 已删除
    DELETED(2);

    private final Integer status;

    ScheduleStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }
}
