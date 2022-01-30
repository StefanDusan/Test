package com.intpfyqa.logging;

import com.intpfyqa.logging.impl.allure.AllureLogger;
import com.intpfyqa.logging.impl.log4j_html.Logger;
import com.intpfyqa.logging.impl.AggregatedLogger;

public class LogManager {

    private static ISuiteLogger _instance = null;

    public static ISuiteLogger getSuiteLogger() {
        if (_instance == null) {
            synchronized (LogManager.class) {
                if (_instance == null) {
                    _instance = new AggregatedLogger(Logger.getInstance(), new AllureLogger());
                }
            }
        }
        return _instance;
    }

    public static ITestLogger getCurrentTestLogger() {
        return getSuiteLogger().getCurrentTestLogger();
    }
}
