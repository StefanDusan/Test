package com.intpfyqa.logging.impl.log4j_html;


import com.intpfyqa.logging.ITakeSnapshot;
import com.intpfyqa.logging.Snapshot;
import com.intpfyqa.logging.impl.base.BaseTestLogger;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.varia.LevelRangeFilter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Test private logger
 */
public class TestLogger extends BaseTestLogger {

    private static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat(" MM-dd-yyyy HH:mm:ss");
    private final TestObjectIdentifier testDescription;
    private org.apache.log4j.Logger log4jLogger;
    private boolean hasFails = false;
    private ArrayList<String> errorMessage = new ArrayList<>();
    //private Dropdown<Pair<String, String>> failedQueue = new LinkedList<>();

    TestLogger(TestObjectIdentifier testDescription) {

        hasFails = false;
        log4jLogger = org.apache.log4j.Logger.getLogger("intpfy.tests." + testDescription.getId());

        log4jLogger.setLevel(org.apache.log4j.Level.TRACE);
        log4jLogger.removeAllAppenders();
        log4jLogger.setAdditivity(false);

        try {
            TestLogAppender appender = new TestLogAppender(testDescription);
            LevelRangeFilter testFilter = new LevelRangeFilter();
            testFilter.setLevelMin(org.apache.log4j.Level.TRACE);
            testFilter.setLevelMax(org.apache.log4j.Level.FATAL);
            appender.clearFilters();
            appender.addFilter(testFilter);
            log4jLogger.addAppender(appender);

            Appender consoleAppender = new ConsoleAppender(new PatternLayout());
            LevelRangeFilter filter = new LevelRangeFilter();
            filter.setLevelMin(org.apache.log4j.Level.INFO);
            filter.setLevelMax(org.apache.log4j.Level.ERROR);
            consoleAppender.clearFilters();
            consoleAppender.addFilter(filter);
            log4jLogger.addAppender(consoleAppender);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.testDescription = testDescription;
    }

    public boolean hasFails() {
        return hasFails;
    }

    @Override
    protected void logMessage(Level level, String header, String message, ITakeSnapshot takeSnapshot) {
        Snapshot snapshot = null;
        org.apache.log4j.Level log4jLevel = toLog4gLevel(level);
        if (log4jLevel.toInt() > log4jLogger.getLevel().toInt()) {
            if (null != takeSnapshot) snapshot = takeSnapshot.takeSnapshot();
            if (null != snapshot) snapshot.saveToStorage();
        }

        if (level == Level.FAIL || level == Level.ERROR) {
            testDescription.setTestFailed();
            hasFails = true;
            errorMessage.add(message);
        }

        log4jLogger.log(log4jLevel, EventObjectHelper.toLog4jEventMessage(level, header, message, snapshot));
    }

    private org.apache.log4j.Level toLog4gLevel(Level level) {
        switch (level) {
            case PASS:
            case INFO:
                return org.apache.log4j.Level.INFO;
            case DEBUG:
                return org.apache.log4j.Level.DEBUG;
            case TRACE:
                return org.apache.log4j.Level.TRACE;
            default:
                return org.apache.log4j.Level.FATAL;
        }
    }

    public ArrayList<String> getErrorMessage() {
        return errorMessage;
    }
}
