package com.intpfyqa.test_rail;

import com.intpfyqa.logging.LogManager;
import com.intpfyqa.test_rail.api.APIClient;
import com.intpfyqa.test_rail.api.APIException;
import com.intpfyqa.test_rail.api.URI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class TestRailClient {

    private static final String USERNAME = "ifqa@helmes.com";
    private static final String AUTH_KEY = "YlImved/VrMwQ3Pj/ToD-Yj3mCJbYIRoE8xusJn/o";
    private static final String SERVER_URL = "https://interprefy.testrail.io/";

    private static APIClient apiClient;

    private static JSONArray tests;

    private static synchronized APIClient getClient() {
        try {
            if (apiClient.equals(null)) {
                apiClient = new APIClient(SERVER_URL);
            }
        } catch (Exception e) {
            apiClient = new APIClient(SERVER_URL);
        }
        apiClient.setUser(USERNAME);
        apiClient.setPassword(AUTH_KEY);
        return apiClient;
    }

    @SuppressWarnings("unchecked")
    public static String getTestIdByAutomationRef(String runId, String automationRef) {
        tests = getTests(runId);

        Object obj = tests.stream()
                .filter(item -> ((JSONObject) item).get(TestRailConstant.CUSTOM_AUTOMATION_REF) != null &&
                        ((JSONObject) item).get(TestRailConstant.CUSTOM_AUTOMATION_REF).equals(automationRef))
                .findAny()
                .orElse(null);
        return obj == null ? null : ((JSONObject) obj).get(TestRailConstant.ID).toString();

    }

    public static JSONArray getTests(String runId) {

        if (tests == null) {

            String getTestsUri = URI.GET_TESTS + runId + (TestRailConfig.isRunForFailedTests() ? "&status_id=2,3,4,5" : "");
            JSONObject testsObject = getJSONObjectBySendGet(getTestsUri);

            tests = getJSONArrayFromObject(testsObject, "tests");

            if (tests == null) {
                throw new RuntimeException("Run " + runId + " doesn't have tests");
            }
        }

        return tests;
    }

    @SuppressWarnings("unchecked")
    private static List<String> getResultsId(String testId) {

        String getResultIdsUri = URI.GET_RESULTS + testId;
        JSONObject resultsObject = getJSONObjectBySendGet(getResultIdsUri);
        JSONArray resultsArray = getJSONArrayFromObject(resultsObject, "results");

        if (resultsArray == null) {
            LogManager.getCurrentTestLogger().fail("TestRail", String.format("Test id 'T%s' doesn't have results", testId));
        } else {
            List<String> resultsId = new ArrayList<>();
            resultsArray.forEach(value -> resultsId.add(((JSONObject) value).get(TestRailConstant.ID).toString()));
            return resultsId;
        }

        return null;
    }

    private static JSONArray getJSONArrayFromObject(JSONObject jsonObject, String field) {
        if (jsonObject == null) {
            return null;
        }
        return (JSONArray) jsonObject.get(field);
    }

    private static JSONObject getJSONObjectBySendGet(String uri) {
        try {
            return (JSONObject) getClient().sendGet(uri);
        } catch (IOException | APIException e) {
            LogManager.getCurrentTestLogger().fail("TestRail", e.getMessage());
        }
        return null;
    }

    private static boolean addResult(String testId, Object data) {
        try {
            getClient().sendPost(URI.ADD_RESULT + testId, data);
            return true;
        } catch (IOException | APIException e) {
            LogManager.getCurrentTestLogger().fail("TestRail", e.getMessage());
        }
        return false;
    }

    private static boolean addAttachments(String resultId, String file) {
        try {
            return getClient().uploadAttachment(URI.ADD_ATTACHMENT_TO_RESULT + resultId, file);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            LogManager.getCurrentTestLogger().fail("TestRail", e.getMessage());
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static void reportByAutomationRef(String runId, String automationRef, TestRailStatus testRailStatus, String comments, Set<String> defects, String screenShot) {
        String testId = getTestIdByAutomationRef(runId, automationRef);

        Map result = new HashMap();
        result.put(TestRailConstant.STATUS_ID, testRailStatus.getId());
        result.put(TestRailConstant.COMMENT, comments);
        if (testRailStatus.equals(TestRailStatus.FAILED))
            result.put(TestRailConstant.DEFECTS, String.join(",", defects));

        if (addResult(testId, result)) {
            LogManager.getCurrentTestLogger().info("TestRail", String.format("\n\n[TESTRAIL]:[RESULT ADDED]:[RUN ID: %s][AUTOMATION REF: %s][STATUS: %s]\n\n", runId, automationRef, testRailStatus));
        }

        List<String> resultsId = getResultsId(testId);
        if (null == resultsId || resultsId.isEmpty()) {
            LogManager.getCurrentTestLogger().fail("TestRail", String.format("Results for test '%s' not found.", testId));
        } else {
            if (addAttachments(resultsId.get(0), screenShot))
                LogManager.getCurrentTestLogger().info("TestRail", "\n\n[TESTRAIL]:[ATTACHMENT UPLOADED]\n\n");
        }
    }
}

