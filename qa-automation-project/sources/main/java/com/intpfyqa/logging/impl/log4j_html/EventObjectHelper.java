package com.intpfyqa.logging.impl.log4j_html;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.intpfyqa.logging.ITestLogger;
import com.intpfyqa.logging.Snapshot;
import com.intpfyqa.utils.FileStorageItem;

import java.time.LocalDateTime;
import java.util.Map;

public class EventObjectHelper {

    private static final String START_TEST_TYPE = "test_start";
    private static final String END_TEST_TYPE = "test_end";

    private static final String levelName = "level";
    private static final String headerName = "header";
    private static final String messageName = "msg";
    private static final String snapshotName = "snapshot";
    private static final String snapshotFileName = "file";
    private static final String snapshotIsImage = "isImage";

    public static boolean isStartTest(String event) {
        try {
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(event);
            return jsonObject.get("eventType").getAsString().equalsIgnoreCase(START_TEST_TYPE);
        } catch (JsonSyntaxException ignore) {
            return false;
        }
    }

    public static boolean isEndTest(String event) {
        try {
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(event);
            return jsonObject.get("eventType").getAsString().equalsIgnoreCase(END_TEST_TYPE);
        } catch (JsonSyntaxException ignore) {
            return false;
        }
    }

    public static TestObjectIdentifier eventToTestObject(String eventObject) {
        try {
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(eventObject);
            TestObjectIdentifier identifier = new TestObjectIdentifier();
            try {
                identifier.setTestClass(Class.forName(jsonObject.get("testClassName").getAsString()));
            } catch (ClassNotFoundException ignore) {

            }

            identifier.setTestName(jsonObject.get("testName").getAsString());
            try {
                identifier.setTmsId(jsonObject.get("tmsId").getAsString());
            } catch (UnsupportedOperationException ignore) {

            }

            try {
                identifier.setFeature(jsonObject.get("feature").getAsString());
            } catch (UnsupportedOperationException ignore) {

            }

            if (jsonObject.get("isFailed").getAsBoolean()) {
                identifier.setTestFailed();
            } else {
                identifier.setTestPassed();
            }
            identifier.setIsConfiguration(jsonObject.get("isConfig").getAsBoolean());
            identifier.setStartTime(new Gson().fromJson(jsonObject.getAsJsonObject("startTime"), LocalDateTime.class));
            try {
                identifier.setEndTime(new Gson().fromJson(jsonObject.getAsJsonObject("endTime"), LocalDateTime.class));
            } catch (ClassCastException | UnsupportedOperationException ignore) {

            }
            identifier.setId(jsonObject.get("id").getAsString());
            Map attrs = new Gson().fromJson(jsonObject.getAsJsonObject("attrs"), Map.class);
            attrs.forEach((k, v) -> identifier.addAttribute(k.toString(), v));

            return identifier;
        } catch (JsonSyntaxException ignore) {
            return null;
        }
    }

    public static String testObjectToLogEventMessage(TestObjectIdentifier testObject, boolean isStart) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("testClassName", testObject.getTestClass().getName());
        jsonObject.addProperty("testName", testObject.getTestName());
        jsonObject.addProperty("tmsId", testObject.getTmsId());
        jsonObject.addProperty("isFailed", testObject.isFailed());
        jsonObject.addProperty("isConfig", testObject.isConfiguration());
        jsonObject.addProperty("isDone", testObject.isDone());
        jsonObject.addProperty("feature", testObject.getFeature());
        jsonObject.add("startTime", new Gson().toJsonTree(testObject.getStartTime()));
        jsonObject.add("endTime", new Gson().toJsonTree(testObject.getEndTime()));
        jsonObject.addProperty("eventType", isStart ? START_TEST_TYPE : END_TEST_TYPE);
        jsonObject.addProperty("id", testObject.getId());
        jsonObject.add("attrs", new Gson().toJsonTree(testObject.getAttributes()));

        return jsonObject.toString();
    }

    public static String toLog4jEventMessage(ITestLogger.Level level, String header, String message, Snapshot snapshot) {
        JsonObject object = new JsonObject();
        object.addProperty(levelName, level.toString());
        object.addProperty(headerName, header);
        object.addProperty(messageName, message);
        JsonObject snapshotObject = new JsonObject();
        if (null != snapshot) {
            FileStorageItem file = snapshot.getSavedFile();
            if (null != file) {
                snapshotObject.addProperty(snapshotFileName, file.getRealFile().getAbsolutePath());
                snapshotObject.addProperty(snapshotIsImage, snapshot.isImage());
            }
        }
        object.add(snapshotName, snapshotObject);
        return new Gson().toJson(object);
    }

    public static LogEventObject eventObjectToLogEvent(String logMessage) {
        try {
            JsonObject object = (JsonObject) new JsonParser().parse(logMessage);
            LogEventObject result = new LogEventObject();
            result.setLevel(levelFromString(object.get(levelName).getAsString()));
            result.setHeader(object.get(headerName).getAsString());
            result.setMessage(object.get(messageName).getAsString());
            if (object.has(snapshotName)) {
                JsonObject snapshotObject = object.getAsJsonObject(snapshotName);
                if (snapshotObject.has(snapshotFileName)) {
                    result.setSnapshotFilePath(snapshotObject.get(snapshotFileName).getAsString());
                    result.setSnapshotIsImage(snapshotObject.get(snapshotIsImage).getAsBoolean());
                }
            }

            return result;
        } catch (JsonSyntaxException ignore) {
            return null;
        }
    }

    private static ITestLogger.Level levelFromString(String levelString) {
        return ITestLogger.Level.valueOf(levelString);
    }
}
