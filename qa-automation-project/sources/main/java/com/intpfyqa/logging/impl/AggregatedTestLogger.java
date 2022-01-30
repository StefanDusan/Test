package com.intpfyqa.logging.impl;

import com.intpfyqa.logging.impl.base.BaseTestLogger;
import com.intpfyqa.logging.impl.log4j_html.UnloggingException;
import com.intpfyqa.logging.ISuiteLogger;
import com.intpfyqa.logging.ITakeSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AggregatedTestLogger extends BaseTestLogger {

    private final List<ISuiteLogger> loggers;

    private AggregatedTestLogger(List<ISuiteLogger> loggers) {
        this.loggers = loggers;
    }

    @Override
    protected void logMessage(Level level, String header, String message, ITakeSnapshot takeSnapshot) {
        final List<Throwable> throwables = new ArrayList<>();
        loggers.forEach(l -> {
            try {
                switch (level) {
                    case FAIL:
                        l.getCurrentTestLogger().fail(header, message, takeSnapshot);
                        break;
                    case DEBUG:
                        l.getCurrentTestLogger().debug(header, message, takeSnapshot);
                        break;
                    case INFO:
                        l.getCurrentTestLogger().info(header, message, takeSnapshot);
                        break;
                    case TRACE:
                        l.getCurrentTestLogger().trace(header, message, takeSnapshot);
                        break;
                    case PASS:
                        l.getCurrentTestLogger().pass(header, message, takeSnapshot);
                        break;
                    case ERROR:
                        l.getCurrentTestLogger().error(header, message, takeSnapshot);
                        break;
                }
            } catch (Throwable t) {
                throwables.add(t);
            }
        });
        if (!throwables.isEmpty()) {
            Throwable t = throwables.get(0);
            if (t instanceof RuntimeException)
                throw (RuntimeException) t;
            else if (t instanceof Error)
                throw (Error) t;
            else
                throw new UnloggingException(t);
        }
    }

    public static AggregatedTestLogger with(List<ISuiteLogger> loggers) {
        return new AggregatedTestLogger(loggers);
    }

    public static AggregatedTestLogger startTest(List<ISuiteLogger> loggers, Object test) {
        loggers.forEach(l -> l.startTest(test));
        return new AggregatedTestLogger(loggers);
    }
}
