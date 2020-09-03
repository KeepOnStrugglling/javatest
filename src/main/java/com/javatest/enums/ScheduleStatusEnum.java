package com.javatest.enums;

public enum ScheduleStatusEnum {
    RUNNABLE(0),PAUSE(1),DELETED(2);

    private final Integer status;

    ScheduleStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }
}
