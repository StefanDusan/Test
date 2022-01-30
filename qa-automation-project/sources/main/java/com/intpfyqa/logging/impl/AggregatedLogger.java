package com.intpfyqa.logging.impl;

import com.intpfyqa.logging.ISuiteLogger;
import com.intpfyqa.logging.ITestLogger;

import java.util.Arrays;
import java.util.List;

public class AggregatedLogger implements ISuiteLogger {

    private final List<ISuiteLogger> loggers;

    public AggregatedLogger(ISuiteLogger... loggers) {
        this.loggers = Arrays.asList(loggers);
    }


    @Override
    public void startSuite(Object suiteInfo) {
        loggers.forEach(l -> l.startSuite(suiteInfo));
    }

    @Override
    public void endSuite() {
        loggers.forEach(ISuiteLogger::endSuite);
    }

    @Override
    public ITestLogger startTest(Object test) {
        return AggregatedTestLogger.startTest(loggers, test);
    }

    @Override
    public void endTest(Object test) {
        loggers.forEach(l -> l.endTest(test));
    }

    @Override
    public ITestLogger getCurrentTestLogger() {
        return AggregatedTestLogger.with(loggers);
    }
}