package com.intpfyqa.logging.impl.allure;

import com.github.automatedowl.tools.AllureEnvironmentWriter;
import com.google.common.collect.ImmutableMap;
import com.intpfyqa.logging.ISuiteLogger;
import com.intpfyqa.logging.ITakeSnapshot;
import com.intpfyqa.logging.ITestLogger;
import com.intpfyqa.logging.Snapshot;
import com.intpfyqa.logging.impl.base.BaseTestLogger;
import com.intpfyqa.logging.impl.log4j_html.SuiteObjectIdentifier;
import com.intpfyqa.utils.TestUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.*;

public class AllureLogger extends BaseTestLogger implements ISuiteLogger, ITestLogger {

    @Override
    public void startSuite(Object suiteInfo) {
        if (suiteInfo instanceof SuiteObjectIdentifier) {
            AllureEnvironmentWriter
                    .allureEnvironmentWriter(ImmutableMap.copyOf(
                            toStringMap(((SuiteObjectIdentifier) suiteInfo).getProperties())));
        }
    }

    private static Map<String, String> toStringMap(Map map) {
        Map<String, String> out = new HashMap<>();
        map.forEach((k, v) -> out.put(k.toString(), v.toString()));
        return out;
    }

    @Override
    public void endSuite() {

    }

    @Override
    public ITestLogger startTest(Object test) {
        return this;
    }

    @Override
    public void endTest(Object test) {

    }

    @Override
    public ITestLogger getCurrentTestLogger() {
        return this;
    }

    @Override
    protected void logMessage(Level level, String header, String message, ITakeSnapshot takeSnapshot) {
        if (!ArrayUtils.contains(new Level[]{Level.FAIL, Level.INFO, Level.PASS, Level.ERROR}, level)) return;

        Status status = (level == Level.FAIL || level == Level.ERROR) ? Status.FAILED : Status.PASSED;

        try {

            List<String> currentStepNames = new ArrayList<>();

            Allure.getLifecycle().updateStep(x -> currentStepNames.add(x.getName()));

            String currentStepName = "";

            if (!currentStepNames.isEmpty())
                currentStepName = currentStepNames.get(0);

            if (currentStepName.equalsIgnoreCase(message)) return;

            StepResult res = new StepResult();
            res.setStatus(status);
            String uuid = UUID.randomUUID().toString();

            if (message.length() > 100) {
                res.setName(header + ": " + message.substring(0, 97) + "...");

            } else {
                res.setName(header + ": " + message);
            }

            Allure.getLifecycle().startStep(uuid, res);

            if (message.length() > 100)
                attachAsHTMLText(message);
            // res.setStatusDetails(new StatusDetails().setMessage(header + ": " + message));

            if (null != takeSnapshot) {
                Snapshot snapshot = takeSnapshot.takeSnapshot();
                if (null != snapshot && snapshot.isImage()) {
                    Allure.getLifecycle().addAttachment("screenshot", "image/png", ".png", snapshot.getBytes());
                }
            }
            Allure.getLifecycle().stopStep(uuid);
        } catch (IllegalStateException ignore) {
            System.out.println(TestUtils.getThrowableFullDescription(ignore));
        }
    }

    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] saveScreenshot(byte[] bytes) {
        return bytes;
    }

    @Attachment(value = "Full text", type = "text/html", fileExtension = "html")
    private String attachAsHTMLText(String notEscapedText) {
        String result = StringEscapeUtils.escapeHtml3(notEscapedText).replace("\n", "<br/>");
        return result.replace("\t", "&nbsp;&nbsp;&nbsp;").replace(" ", "&nbsp;");
    }
}
