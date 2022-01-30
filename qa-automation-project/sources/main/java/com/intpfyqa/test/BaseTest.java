package com.intpfyqa.test;

import com.intpfyqa.logging.ITestLogger;
import com.intpfyqa.logging.LogManager;
import com.intpfyqa.logging.impl.log4j_html.*;
import com.intpfyqa.run.RunSession;
import com.intpfyqa.run.RunSessions;
import com.intpfyqa.test_rail.Reporter;
import com.intpfyqa.test_rail.TestFilterInterceptor;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.utils.TestUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Listeners({DefaultListener.class, TestFilterInterceptor.class})
public abstract class BaseTest {

    /**
     * Test logger
     */
    protected ThreadLocal<ITestLogger> logger = new ThreadLocal<>();
    protected final ThreadLocal<RunSession> runSession = new ThreadLocal<>();
    protected final ThreadLocal<TestObjectIdentifier> testObjectIdentifier = new ThreadLocal<>();

    /**
     * Checks if test has uncritical errors. If true throws Unlogging exception<br/>
     * This method should be called only from data driven tests at the end of test<br/>
     * Call this method as is without any try{}catch{} and only as last call of test method:<br/>
     * void myTestMethod(Object... params){<br/>
     * .... //execution<br/>
     * <br/>
     * throwErrorIfVerificationsFailed();<br/>
     * }
     */
    protected final void throwErrorIfVerificationsFailed() {
        StringBuilder builder = new StringBuilder();
        String message = "Errors occurred during execution (see log)";
        builder.append(message);
        TestLogger testLogger = (TestLogger) Logger.getInstance().getCurrentTestLogger();
        for (String s : testLogger.getErrorMessage()) {
            builder.append("\n");
            builder.append(s);
        }
        if (testObjectIdentifier.get().isFailed()) {
            throw new UnloggingException(builder.toString());
        }
    }

    /**
     * Runs once before suite
     *
     * @param context see TestNG docs
     * @throws Exception whatever
     */

    @BeforeSuite(alwaysRun = true)
    public synchronized void beforeSuite(ITestContext context) throws Exception {
        LogManager.getSuiteLogger().startSuite(createSuiteObject());
        RunSession runSession = createInitSuiteRunSession();
        RunSessions.registerSession(runSession, null);
        RunSessions.allocateAnyFreeSession(this.getClass().getMethod("beforeSuite", ITestContext.class));
        this.runSession.set(runSession);
        TestObjectIdentifier testObjectIdentifier = new TestObjectIdentifier();
        testObjectIdentifier.setTestClass(this.getClass());
        testObjectIdentifier.setTestName("Suite init");
        testObjectIdentifier.setRunSession(runSession);

        testObjectIdentifier.setIsConfiguration(true);

        ITestLogger testLogger = LogManager.getSuiteLogger().startTest(testObjectIdentifier);
        this.logger.set(testLogger);
        initSuite();

        LogManager.getSuiteLogger().endTest(testObjectIdentifier);
        this.runSession.get().free();
        RunSessions.close(this.runSession.get());
    }

    /**
     * customize before suite behavior
     */
    protected abstract void initSuite();

    /**
     * Init info about this suite
     *
     * @return Init info about this suite
     */
    protected abstract SuiteObjectIdentifier createSuiteObject();

    /**
     * Create test run session
     *
     * @return Test run session
     */
    protected abstract RunSession createRunSession();

    /**
     * Create a run session for Suite init actions (before any test being started)
     *
     * @return Run session for Suite init actions
     */
    protected abstract RunSession createInitSuiteRunSession();

    /**
     * Once a test session is registered (could be allocated)
     *
     * @param session New test session
     */
    protected abstract void onSessionRegistered(RunSession session);

    /**
     * Runs once on test suite stop
     */
    @AfterSuite(alwaysRun = true)
    public final void afterSuite() {
        RunSessions.releaseAll();
    }

    /**
     * Initializes test logger<br/>
     *
     * @param method Test method to execute
     */
    protected void initTestLogger(Method method, Object[] params) {

        String name;
        String description;

        Test testAnnotation = method.getAnnotation(Test.class);

        name = (testAnnotation.testName().equals("") ? method.getName() : testAnnotation.testName());
        description = testAnnotation.description();

        String tmsId = null;
        try {
            TmsId tmsIdAnnotation = method.getAnnotation(TmsId.class);
            tmsId = tmsIdAnnotation.value();
        } catch (NullPointerException ignore) {
        }

        Set<String> bugIds = Set.of();
        try {
            TestRailBugId bugsAnnotation = method.getAnnotation(TestRailBugId.class);
            bugIds = Reporter.getBugIds(bugsAnnotation);
        } catch (NullPointerException ignore) {
        }


        String paramsDescription = null;

        if (params != null && params.length > 0) {

            try {
                Parameters parametersAnnotation = method.getAnnotation(Parameters.class);
                if (parametersAnnotation != null && parametersAnnotation.value().length == params.length) {
                    List<String> paramsDescriptions = new ArrayList<>();
                    for (int i = 0; i < parametersAnnotation.value().length; i++) {
                        paramsDescriptions.add(parametersAnnotation.value()[i] + ": " + params[i]);
                    }
                    paramsDescription = StringUtils.join(paramsDescriptions, "\n");
                }
            } catch (NullPointerException ignore) {

            }

            if (paramsDescription == null) {
                paramsDescription = "With params: " + StringUtils.join(params, ", ");
            }

            description += "\n" + paramsDescription;
        }

        TestObjectIdentifier testDescription = new TestObjectIdentifier();
        testDescription.setTestName(name);
        testDescription.setTestClass(this.getClass());
        testDescription.setTmsId(tmsId);
        testDescription.setIsConfiguration(false);
        testDescription.addBugIds(bugIds);
        testDescription.addAttribute("Description", description);
        updateTestIdentifier(testDescription, method, params);
        testObjectIdentifier.set(testDescription);


        logger.set(LogManager.getSuiteLogger().startTest(testDescription));

    }

    /**
     * Inject custom params into test identifier for logging
     *
     * @param identifier Identifier
     * @param method     Test method
     * @param params     Params for test method
     */
    protected void updateTestIdentifier(TestObjectIdentifier identifier, Method method, Object[] params) {

    }

    /**
     * Custom actions to be executed before test execution (see TestNG docs)
     *
     * @param context Test context
     * @param method  Test method
     * @param params  Test method params
     */
    protected void customBeforeMethod(ITestContext context, Method method, Object[] params) {

    }
    protected void initRunSession(Method method) {
        Optional<RunSession> freeSession = RunSessions.allocateAnyFreeSession(method);
        RunSession session;
        if (!freeSession.isPresent()) {
            session = createRunSession();
            RunSessions.registerSession(session, method);
            onSessionRegistered(session);
        } else {
            session = freeSession.get();
        }

        if (null == session)
            throw new IllegalStateException("Can't find free run sessions");

        runSession.set(session);
        if (runSession.get() == null) throw new RuntimeException("Run Session is not initialized");
        testObjectIdentifier.get().setRunSession(runSession.get());

    }

    /**
     * Before method actions (initializing environment)
     *
     * @param method Method to execute
     */
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(ITestContext context, Method method, Object[] params) {
        initTestLogger(method, params);
        initRunSession(method);
        customBeforeMethod(context, method, params);
    }

    /**
     * Custom actions to be execute when test is completed (independently of test result)
     */
    protected void customAfterMethod() {

    }

    /**
     * empty, nothing to do
     */
    @AfterClass(alwaysRun = true)
    public final void afterClass() {
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(Method method, ITestResult iTestResult) {
        try {
            getRunSession().switchToDefaultContext();
            getRunSession().closeAllContextsExceptDefault();
            customAfterMethod();
        } catch (Throwable t) {
            if (!(t instanceof UnloggingException)) {
                logger.get().fail("Teardown", "Critical error occurred: " + t.getMessage());
                logger.get().debug("Teardown", "Critical error occurred: " + t.getMessage() + "\n" +
                        TestUtils.getThrowableFullDescription(t));
            }
        }

        LogManager.getSuiteLogger().endTest(testObjectIdentifier.get());
        runSession.get().free();
    }

    /**
     * Current test logger
     *
     * @return Current test's logger
     */
    protected ITestLogger getLogger() {
        return logger.get();
    }

    /**
     * Current test's run session
     *
     * @return Current test's run session
     */
    protected RunSession getRunSession() {
        return runSession.get();
    }
}
