package com.demo.folder.worker.pojos;

public class WorkStatus {
    private String workName;
    private Long duration;
    private Integer statusCode;
    private String exceptionMsg;


    public WorkStatus() {
    }

    public WorkStatus(String workName){
        this.workName = workName;
    }

    public String getWorkName() {
        return workName;
    }

    public WorkStatus setWorkName(String workName) {
        this.workName = workName;
        return this;
    }

    public Long getDuration() {
        return duration;
    }

    public WorkStatus setDuration(Long duration) {
        this.duration = duration;
        return this;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public WorkStatus setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public WorkStatus setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
        return this;
    }

    @Override
    public String toString() {
        return "WorkStatus{" +
                "workName='" + workName + '\'' +
                ", duration=" + duration +
                ", statusCode=" + statusCode +
                ", exceptionMsg='" + exceptionMsg + '\'' +
                '}';
    }
}