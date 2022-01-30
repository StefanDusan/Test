package com.intpfyqa.test_rail;

import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.util.Set;


public class Reporter {

    public static final String JIRA_URL = "https://interprefy.atlassian.net/browse/";

    public static void reportTestRail(ITestResult result, TestRailStatus status, String screenshot) {
        if (!TestRailConfig.isReportTestRailAllowed()) {
            return;
        }

        Method method;
        try {
            if ((method = result.getMethod().getConstructorOrMethod().getMethod()) == null) {
                return;
            }
        } catch (NullPointerException ignore) {
            return;
        }

        TestRailCase testRailCase;
        TestRailBugId testRailBugId;
        try {
            if ((testRailCase = method.getAnnotation(TestRailCase.class)) == null) {
                return;
            }
            testRailBugId = method.getAnnotation(TestRailBugId.class);
        } catch (Exception ignore) {
            return;
        }

        String automationRef;
        if ((automationRef = testRailCase.value()).isEmpty()) {
            return;
        }
        Set<String> bugIds = getBugIds(testRailBugId);

        String comments = status == TestRailStatus.FAILED ? result.getThrowable().getLocalizedMessage() : "All assertions are passed.";
        TestRailClient.reportByAutomationRef(TestRailConfig.getTestRailRunId(), automationRef, status, comments, bugIds, screenshot);
    }

    public static Set<String> getBugIds(TestRailBugId id) {
        if (id != null) {
            String value;
            if (!(value = id.value()).isEmpty()) {
                return Set.of(value);
            }
            String[] values;
            if ((values = id.values()).length > 0) {
                return Set.of(values);
            }
        }
        return Set.of();
    }
}
