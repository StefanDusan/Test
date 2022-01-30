package com.intpfyqa.logging;

public interface ISuiteLogger {

    void startSuite(Object suiteInfo);

    void endSuite();

    ITestLogger startTest(Object test);

    void endTest(Object test);

    ITestLogger getCurrentTestLogger();
}
