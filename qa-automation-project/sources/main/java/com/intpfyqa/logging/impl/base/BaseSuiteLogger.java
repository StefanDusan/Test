package com.intpfyqa.logging.impl.base;

import com.intpfyqa.logging.ISuiteLogger;
import com.intpfyqa.logging.ITestLogger;
import com.intpfyqa.logging.LogManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class BaseSuiteLogger implements ISuiteLogger {

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ISuiteLogger suiteLogger = LogManager.getSuiteLogger();
            if (!(suiteLogger instanceof BaseSuiteLogger)) return;
            for (Object test : ((BaseSuiteLogger) suiteLogger).getCreatedTests()) {
                try {
                    suiteLogger.endTest(test);
                } catch (Throwable ignore) {

                }
            }
        }));
    }

    private final Map<Object, ITestLogger> activeLoggers = new HashMap<>();

    protected synchronized Set<Object> getCreatedTests() {
        return activeLoggers.keySet();
    }

    @Override
    public void startSuite(Object suite) {
        handleStartSuite(suite);
        activeLoggers.clear();
    }

    protected abstract void handleStartSuite(Object suite);

    @Override
    public synchronized ITestLogger startTest(Object test) {
        Object identifier = testObjectToObjectIdentifier(test);
        ITestLogger logger = createTestLoggerForIdentifier(identifier);
        synchronized (activeLoggers) {
            activeLoggers.put(identifier, logger);
        }

        return logger;
    }

    protected abstract Object testObjectToObjectIdentifier(Object test);

    protected abstract ITestLogger createTestLoggerForIdentifier(Object test);

    protected abstract Object getCurrentTestIdentifier();

    @Override
    public ITestLogger getCurrentTestLogger() {
        Object test = getCurrentTestIdentifier();
        if (null == test)
            throw new RuntimeException("Current test has no active loggers!");

        if (!activeLoggers.containsKey(test))
            throw new RuntimeException("Current test logging was never started!");

        return activeLoggers.get(test);
    }
}
