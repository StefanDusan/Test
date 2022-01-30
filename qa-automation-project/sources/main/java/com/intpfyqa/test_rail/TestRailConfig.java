package com.intpfyqa.test_rail;


import com.intpfyqa.Environment;

public class TestRailConfig {

    public static String getTestRailUsername() {
        return Environment.instance().getProperty("integration.testrail.username");
    }

    public static String getTestRailPassword() {
        return Environment.instance().getProperty("integration.testrail.password");
    }

    public static String getTestRailRunId() {
        return Environment.instance().getProperty("integration.testrail.run_id");
    }

    public static boolean isReportTestRailAllowed() {
        return Boolean.valueOf(Environment.instance().getProperty("integration.testrail.enabled"));
    }

    public static boolean isRunForFailedTests() {
        return Boolean.valueOf(Environment.instance().getProperty("integration.testrail.run_failed"));
    }
}

