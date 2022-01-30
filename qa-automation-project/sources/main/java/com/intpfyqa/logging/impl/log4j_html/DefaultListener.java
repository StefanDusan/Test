package com.intpfyqa.logging.impl.log4j_html;

import com.intpfyqa.gui.web.selenium.browser.BrowserSession;
import com.intpfyqa.logging.ITakeSnapshot;
import com.intpfyqa.logging.ITestLogger;
import com.intpfyqa.logging.LogManager;
import com.intpfyqa.run.RunSession;
import com.intpfyqa.run.RunSessions;
import com.intpfyqa.test_rail.Reporter;
import com.intpfyqa.test_rail.TestRailStatus;
import com.intpfyqa.utils.TestUtils;
import org.testng.IConfigurationListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Default Selenium test TestNG listener
 */
public class DefaultListener implements ITestListener, IConfigurationListener {

    private Throwable lastKnownThrowable = null;

    public DefaultListener() {

    }


    public void onTestStart(ITestResult iTestResult) {
    }


    /**
     * On testISF34 success validate not critical errors
     *
     * @param iTestResult iTestResult in TestNG
     * @see org.testng.ITestListener
     */
    public void onTestSuccess(ITestResult iTestResult) {
        RunSession runSession = null;
        if (RunSessions.hasSession(iTestResult.getMethod()
                .getConstructorOrMethod().getMethod())) {
            runSession = RunSessions.getSession(iTestResult.getMethod()
                    .getConstructorOrMethod().getMethod());
        }
        ITakeSnapshot takeSnapshot = runSession == null || runSession.getCurrentContext() == null ? null  :
                runSession.getCurrentContext().getTakeSnapshot();
        String screenshot = null;
        if(takeSnapshot != null){
            BrowserSession session = (BrowserSession) takeSnapshot;
            screenshot = session.getActiveWindow().getScreenShot();
        }
        Reporter.reportTestRail(iTestResult, TestRailStatus.PASSED, screenshot);
    }

    /**
     * Places message about critical error in Logger with page screenshot
     *
     * @param iTestResult iTestResult in TestNG
     * @see org.testng.ITestListener
     */
    public void onTestFailure(ITestResult iTestResult) {
        if (iTestResult.getMethod().isTest()) {

            ITestLogger testLogger;
            try {
                testLogger = LogManager.getCurrentTestLogger();
            } catch (Throwable ignore) {
                return;
            }

            RunSession runSession = null;
            if (RunSessions.hasSession(iTestResult.getMethod()
                    .getConstructorOrMethod().getMethod())) {
                runSession = RunSessions.getSession(iTestResult.getMethod()
                        .getConstructorOrMethod().getMethod());
            }

            ITakeSnapshot takeSnapshot = runSession == null || runSession.getCurrentContext() == null ? null :
                    runSession.getCurrentContext().getTakeSnapshot();


            if (iTestResult.getThrowable() != null) {

                testLogger.debug("ERROR", "There is critical error in test" +
                        "\n" +
                        "Exception:\n" +
                        TestUtils.getThrowableFullDescription(iTestResult.getThrowable()));

                if (!(iTestResult.getThrowable() instanceof UnloggingException)) {
                    testLogger.fail("ERROR", "There is critical error in test",
                            iTestResult.getThrowable(),
                            takeSnapshot);
                }
                String screenshot = null;
                if(takeSnapshot != null){
                    BrowserSession session = (BrowserSession) takeSnapshot;
                    screenshot = session.getActiveWindow().getScreenShot();
                }
                Reporter.reportTestRail(iTestResult, TestRailStatus.FAILED, screenshot);
            }

            lastKnownThrowable = null;
        } else {
            lastKnownThrowable = iTestResult.getThrowable();
        }

    }

    /**
     * Places message about testISF34 skipped in Logger
     *
     * @param iTestResult iTestResult in TestNG
     * @see org.testng.ITestListener
     */
    public void onTestSkipped(ITestResult iTestResult) {

        ITestLogger testLogger = null;

        try {
            testLogger = LogManager.getCurrentTestLogger();
        } catch (IllegalStateException ignore) {

        }

        if (testLogger == null) {
            TestObjectIdentifier testDescription = new TestObjectIdentifier();
            testDescription.setTestName((iTestResult.getTestName() == null ?
                    iTestResult.getMethod().getConstructorOrMethod().getName() : iTestResult.getTestName()) + " SKIPPED");
            testDescription.setTestClass(iTestResult.getTestClass().getRealClass());
            testLogger = LogManager.getSuiteLogger().startTest(testDescription);
        }
        testLogger.fail("SKIPPED", "Test " + iTestResult.getMethod().toString() + " is skipped\n" +
                TestUtils.getThrowableFullDescription(
                        (iTestResult.getThrowable()) == null ? lastKnownThrowable : iTestResult.getThrowable())
        );
        //Logger.getLogger().endTestSession(testThread);

    }

    /**
     * Places message about errors in Logger with page screenshot
     *
     * @param iTestResult iTestResult in TestNG
     * @see org.testng.ITestListener
     */
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        ITestLogger testLogger = null;

        try {
            testLogger = LogManager.getCurrentTestLogger();
        } catch (IllegalStateException ignore) {
            return;
        }


        RunSession runSession = null;
        if (RunSessions.hasSession(iTestResult.getMethod()
                .getConstructorOrMethod().getMethod())) {
            runSession = RunSessions.getSession(iTestResult.getMethod()
                    .getConstructorOrMethod().getMethod());
        }

        ITakeSnapshot takeSnapshot = runSession == null || runSession.getCurrentContext() == null ? null :
                runSession.getCurrentContext().getTakeSnapshot();
        testLogger.fail("ERROR", "There is critical error in test:\n" +
                        TestUtils.getThrowableFullDescription(iTestResult.getThrowable()),
                takeSnapshot);

    }

    public void onStart(ITestContext iTestContext) {
    }

    public void onFinish(ITestContext iTestContext) {
    }

    public void onConfigurationSuccess(ITestResult iTestResult) {
    }

    public void onConfigurationFailure(ITestResult iTestResult) {
        iTestResult.setStatus(ITestResult.FAILURE);
        onTestFailure(iTestResult);
    }

    public void onConfigurationSkip(ITestResult iTestResult) {
    }
}