package com.aqr.tca.beans.sync;

import com.aqr.tca.utils.StatusWork;
import java.time.LocalDateTime;

public abstract class SyncWorker {

    private String topic;
    private String appName;
    private long threadId;
    private LocalDateTime dateTime;
    private Long elementsFromTopic;



    private volatile boolean shouldStop=false;

    public SyncWorker() {

    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getElementsFromTopic() {
        return elementsFromTopic;
    }

    public void setElementsFromTopic(Long elementsFromTopic) {
        this.elementsFromTopic = elementsFromTopic;
    }

    public boolean shouldContinue() {
        return !shouldStop;
    }

    public void stop() {
        this.shouldStop = true;
    }

    public abstract StatusWork call();
}
