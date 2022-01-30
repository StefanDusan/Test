package com.intpfy.test.perfomance;

import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfyqa.settings.WebSettings;
import com.intpfyqa.test.Verify;
import com.intpfyqa.utils.TestUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static com.intpfy.test.TestGroups.*;

public class PublishUnpublishDelayTest extends BaseWebTest {

    private static final String INTERPRETER_TOKEN = "I-PCAc96br";
    private static final Duration TEST_DURATION = Duration.of(6, ChronoUnit.HOURS).plus(1, ChronoUnit.MINUTES);
    private static final Duration PUBLISH_DURATION = Duration.of(15, ChronoUnit.MINUTES);
    private static final Duration UNPUBLISH_DURATION = Duration.of(5, ChronoUnit.MINUTES);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private static final Language MAIN_LANGUAGE = Language.English;
    private static final Language RELAY_LANGUAGE = Language.German;

    private final Authorizer authorizer = Authorizer.getInstance();

    private final StringBuilder resultBuilder = new StringBuilder();

    @Test(
            testName = "Measure Interpreter publish delay",
            description = "Measure Interpreter publish delay.",
            enabled = false,
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    INTERPRETER,
                    SPEAKER
            }
    )
    public void test() throws IOException {

        Event event = Event.createRandomBuilder()
                .withInterpreterToken(INTERPRETER_TOKEN)
                .withLanguage(MAIN_LANGUAGE)
                .withLanguage(RELAY_LANGUAGE)
                .build();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName);
        interpreterPage.assertIsOpened();

        Verify.assertTrue(interpreterPage.isMuted(WebSettings.AJAX_TIMEOUT), "Assert that Interpreter is muted after login.");

        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = startDateTime.plus(TEST_DURATION);
        LocalDateTime currentDateTime;
        LocalTime timeFromStart;
        LocalTime unmuteTime;
        String iterationResult;


        while ((currentDateTime = LocalDateTime.now()).isBefore(endDateTime)) {

            timeFromStart = LocalTime.ofNanoOfDay(startDateTime.until(currentDateTime, ChronoUnit.NANOS));

            interpreterPage.switchMute();
            unmuteTime = measureUnmuteTime(interpreterPage);
            Verify.assertTrue(interpreterPage.isUnmuted(Duration.ZERO), "Assert that Interpreter is unmuted.");

            iterationResult = formatResult(timeFromStart, unmuteTime);
            resultBuilder.append(iterationResult);
            getLogger().info("Measurement result", iterationResult);

            TestUtils.sleep(PUBLISH_DURATION.toMillis());

            interpreterPage.switchMute();
            Verify.assertTrue(interpreterPage.isMuted(WebSettings.AJAX_TIMEOUT), "Assert that Interpreter is muted.");

            TestUtils.sleep(UNPUBLISH_DURATION.toMillis());
        }

        throwErrorIfVerificationsFailed();
    }

    public LocalTime measureUnmuteTime(InterpreterPage interpreterPage) {
        LocalTime unmuteTime = LocalTime.of(0, 0);
        long startMeasureNanos = LocalTime.now().toNanoOfDay();
        while (interpreterPage.isMuted(Duration.ZERO)) {
            unmuteTime = LocalTime.ofNanoOfDay(LocalTime.now().toNanoOfDay() - startMeasureNanos);
            TestUtils.sleep(100);
        }
        return unmuteTime;
    }

    public String formatResult(LocalTime timeFromStart, LocalTime unmuteTime) {
        return String.format("Time from start: %s, unmute time: %s.%n", timeFromStart.format(TIME_FORMATTER), unmuteTime.format(TIME_FORMATTER));
    }

    @Override
    protected void customAfterMethod() {
        getLogger().info("Final result", resultBuilder.toString());
    }
}
