package com.sse.g4.proj.common.constant;

public enum TaskStatus {
    FUTURE_TASK(1, "将来任务"),
    DELAY_TASK(2, "延期任务"),
    CURRENT_TASK(3, "进行中任务"),
    FINISHED_TASK(4, "已完成任务");

    private int code;
    private String taskStatus;


    TaskStatus(int i, String taskStatus) {
        this.code = i;
        this.taskStatus = taskStatus;
    }

    public int getCode() {
        return code;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
