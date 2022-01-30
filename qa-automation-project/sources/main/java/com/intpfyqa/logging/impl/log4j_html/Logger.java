package com.intpfyqa.logging.impl.log4j_html;

import com.intpfyqa.logging.impl.base.BaseSuiteLogger;
import com.intpfyqa.logging.ISuiteLogger;
import com.intpfyqa.logging.ITestLogger;
import com.intpfyqa.run.RunSession;
import com.intpfyqa.run.RunSessions;
import com.intpfyqa.utils.FileStorage;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.varia.LevelRangeFilter;
import org.apache.log4j.varia.NullAppender;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

/**
 * Selenium tests logger
 */
public class Logger extends BaseSuiteLogger implements ISuiteLogger {


    /**
     * Folder where extended logs should be placed
     */
    public static final String LOGS_FOLDER_NAME = "selenium-logs";
    public static final String TEST_LOGS_FOLDER_NAME = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
    public static final String TEST_LOGS_SUB_FOLDER = LOGS_FOLDER_NAME + File.separator + TEST_LOGS_FOLDER_NAME;
    private static final String LOG_EXT_RES_FOLDER_NAME = "ext";

    private int idx = 1;
    private org.apache.log4j.Logger log4jLogger;
    private DefaultAppender appender;

    private static final Logger _instance = new Logger();

    /**
     * Constructor
     */
    private Logger() {
        FileStorage.createSubDir(LOGS_FOLDER_NAME);
        FileStorage.createSubDir(LOGS_FOLDER_NAME + File.separator + TEST_LOGS_FOLDER_NAME);

        org.apache.log4j.Logger.getRootLogger();
        log4jLogger = org.apache.log4j.Logger.getLogger("intpfy.tests");

        org.apache.log4j.Logger.getRootLogger().removeAllAppenders();
        org.apache.log4j.Logger.getRootLogger().addAppender(new NullAppender());
        log4jLogger.setLevel(Level.DEBUG);
        log4jLogger.removeAllAppenders();
        log4jLogger.setAdditivity(false);

        try {
            appender = new DefaultAppender();
            log4jLogger.addAppender(appender);
            Appender consoleAppender = new ConsoleAppender(new PatternLayout());
            LevelRangeFilter filter = new LevelRangeFilter();
            filter.setLevelMin(Level.INFO);
            consoleAppender.clearFilters();
            consoleAppender.addFilter(filter);
            log4jLogger.addAppender(consoleAppender);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initLogResources();
    }

    public static Logger getInstance() {
        return _instance;
    }

    static String toJSPath(String path) {
        String targetSeparator = "/";
        if (!File.separator.equals(targetSeparator))
            return path.replace(File.separator, targetSeparator);
        return path;
    }

    private void initLogResources() {

        String resSubDir = LOGS_FOLDER_NAME + File.separator + LOG_EXT_RES_FOLDER_NAME;
        FileStorage.createSubDir(resSubDir);

        FileStorage.putFileIfNotExist(getResourceStream("log/32px.png"),
                "32px.png", resSubDir);
        FileStorage.putFileIfNotExist(getResourceStream("log/jquery-1.12.0.min.js"),
                "jquery-1.12.0.min.js", resSubDir);
        FileStorage.putFileIfNotExist(getResourceStream("log/jstree.min.js"),
                "jstree.min.js", resSubDir);
        FileStorage.putFileIfNotExist(getResourceStream("log/style.css"),
                "style.css", resSubDir);
        FileStorage.putFileIfNotExist(getResourceStream("log/throbber.gif"),
                "throbber.gif", resSubDir);
        FileStorage.putFileIfNotExist(getResourceStream("log/suite.css"),
                "suite.css", resSubDir);
        FileStorage.putFileIfNotExist(getResourceStream("log/suite.js"),
                "suite.js", resSubDir);
        FileStorage.putFileIfNotExist(getResourceStream("log/tst.css"),
                "tst.css", resSubDir);
        FileStorage.putFileIfNotExist(getResourceStream("log/tst.js"),
                "tst.js", resSubDir);
    }

    @Override
    protected void handleStartSuite(Object suite) {
        if (!(suite instanceof SuiteObjectIdentifier))
            throw new IllegalArgumentException("Suite must be instance of " + SuiteObjectIdentifier.class);

        appender.createHtml((SuiteObjectIdentifier) suite);
    }

    private InputStream getResourceStream(String path) {
        String secondPath = path.startsWith("/") ? path.substring(1) : ("/" + path);
        LocalDateTime endTime = LocalDateTime.now().plusSeconds(5);

        do {
            for (String item : new String[]{path, secondPath}) {
                InputStream is = this.getClass().getClassLoader().getResourceAsStream(item);
                if (null == is) is = Logger.class.getResourceAsStream(item);
                if (null == is) is = ClassLoader.getSystemResourceAsStream(item);
                if (null != is) return is;
            }

        } while (LocalDateTime.now().isBefore(endTime));
        return null;
    }

    private synchronized String getNextTestId() {
        return idx++ + "";
    }

    @Override
    protected ITestLogger createTestLoggerForIdentifier(Object test) {

        try {
            Object currentTest = getCurrentTestIdentifier();
            if (null != currentTest) endTest(test);
        } catch (Throwable ignore) {

        }

        TestLogger testLogger = new TestLogger((TestObjectIdentifier) test);
        log4jLogger.info(EventObjectHelper.testObjectToLogEventMessage((TestObjectIdentifier) test, true));

        return testLogger;
    }

    protected Object testObjectToObjectIdentifier(Object test) {
        if (!(test instanceof TestObjectIdentifier))
            throw new IllegalArgumentException("Test must be instance of " + TestObjectIdentifier.class);
        ((TestObjectIdentifier) test).setThread(Thread.currentThread());
        ((TestObjectIdentifier) test).setId(getNextTestId());
        try {
            ((TestObjectIdentifier) test).setRunSession(RunSessions.current());
        } catch (IllegalStateException ignore) {

        }
        return test;
    }


    @Override
    public void endSuite() {

    }

    @Override
    public void endTest(Object test) {
        if (!(test instanceof TestObjectIdentifier))
            throw new IllegalArgumentException("Test must be instance of " + TestObjectIdentifier.class);

        TestObjectIdentifier started = (TestObjectIdentifier) getCreatedTests().stream()
                .filter(t -> t.equals(test)).findFirst().orElse(null);

        if (null == started)
            throw new IllegalStateException("Test " + test + " logger not found. Was it started?");

        if (started.isDone())
            throw new IllegalStateException("Test " + test + " is already ended");

        if (((TestObjectIdentifier) test).isFailed())
            started.setTestFailed();
        else
            started.setTestPassed();

        started.setEndTime(LocalDateTime.now());
        started.setDone(true);

        log4jLogger.info(EventObjectHelper.testObjectToLogEventMessage(started, false));
    }

    @Override
    protected Object getCurrentTestIdentifier() {
        Set<Object> allTests = getCreatedTests();

        RunSession currentSession;
        try {
            currentSession = RunSessions.current();
        } catch (IllegalStateException ignore) {
            currentSession = null;
        }

        for (Object test : allTests) {
            if (!(test instanceof TestObjectIdentifier)) continue;
            TestObjectIdentifier identifier = (TestObjectIdentifier) test;
            if (identifier.isDone()) continue;
            RunSession identifierRunSession = identifier.getRunSession();

            if (identifierRunSession != null && currentSession != null && identifierRunSession.equals(currentSession))
                return identifier;
            if (identifierRunSession != null && identifierRunSession.isOfThread(Thread.currentThread())) {
                return identifier;
            }

            if (identifier.getThread().equals(Thread.currentThread()))
                return identifier;
        }

        throw new IllegalStateException("Can't resolve current test logger! Was it started?");
    }
}
