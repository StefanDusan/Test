package com.intpfyqa.logging.impl.log4j_html;

import com.intpfyqa.run.RunSession;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class TestObjectIdentifier {

    private Class testClass;
    private String testName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String id;
    private Thread thread;
    private Map<String, Object> attributes;
    private final Set<String> bugIds = new HashSet<>();
    private boolean isFailed = false;
    private String tmsId;
    private boolean isConfig = false;
    private RunSession runSession;
    private boolean isDone = false;
    private String feature;

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public TestObjectIdentifier() {
        attributes = new HashMap<>();
        thread = Thread.currentThread();
        startTime = LocalDateTime.now();
    }

    public Class getTestClass() {
        return testClass;
    }

    public void setTestClass(Class testClass) {
        this.testClass = testClass;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public void addAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setTestFailed() {
        isFailed = true;
    }

    public void setTestPassed() {
        isFailed = false;
    }

    public boolean isFailed() {
        return isFailed;
    }

    public String getDurationString() {
        endTime = getEndTime();
        if (null == endTime) endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        long seconds = duration.getSeconds();
        int minutes = (int) (seconds / 60);
        seconds -= minutes * 60;
        if (minutes > 0)
            return String.format("%s minute(s), %s second(s)", minutes, seconds);
        else
            return String.format("%s second(s)", seconds);
    }

    public String getTmsId() {
        return tmsId;
    }

    public void setTmsId(String tmsId) {
        this.tmsId = tmsId;
    }

    public boolean isConfiguration() {
        return isConfig;
    }

    public void setIsConfiguration(boolean config) {
        isConfig = config;
    }

    public String getClassName() {
        return getTestClass().getName();
    }

    public RunSession getRunSession() {
        return runSession;
    }

    public void setRunSession(RunSession runSession) {
        this.runSession = runSession;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Set<String> bugIds() {
        return bugIds;
    }

    public void addBugIds(Collection<String> bugIds) {
        this.bugIds.addAll(bugIds);
    }
}