package com.intpfy.test.core_regression.smoke_test.audio_video_streams;

import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.WebRtcTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.util.WebRtcUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.media.web_rtc.WebRtcVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class InterpreterCanRalSuccessfullyTest extends BaseWebTest implements WebRtcTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Language language = Language.getRandomLanguage();

    // Create random event with 1 Language.
    private final Event event = Event
            .createRandomBuilder()
            .withLanguage(language)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that Interpreter can RAL successfully",
            description = "Interpreter can Restart all lines successfully.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    INTERPRETER
            }
    )
    @TestRailCase("833")
    public void test() {

        // Log in as Interpreter 1 with Outgoing language and start streaming.

        String firstInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage firstInterpreterPage = authorizer.logInAsInterpreter(event, firstInterpreterName, language);
        firstInterpreterPage.assertIsOpened();

        firstInterpreterPage.unmute();

        InterpreterVerifier firstInterpreterVerifier = new InterpreterVerifier(firstInterpreterPage);

        firstInterpreterVerifier.assertStreaming();

        // Log in as Interpreter 2 with Outgoing language and start streaming.

        WebApplicationContext secondInterpreterContext = WebContextUtil.switchToNewContext();

        String secondInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage secondInterpreterPage = authorizer.logInAsInterpreter(event, secondInterpreterName, language);
        secondInterpreterPage.assertIsOpened();

        secondInterpreterPage.unmute();

        InterpreterVerifier secondInterpreterVerifier = new InterpreterVerifier(secondInterpreterPage);

        secondInterpreterVerifier.assertStreaming();

        // Check that Interpreter 2 can hear Interpreter 1.

        WebRtcPage secondInterpreterWebRtcPage = WebRtcUtil.openWebRtcPage();
        secondInterpreterWebRtcPage.assertIsOpened();

        WebRtcVerifier secondInterpreterWebRtcVerifier = new WebRtcVerifier(secondInterpreterWebRtcPage);

        int secondInterpreterExpectedStreamsCount = 2;

        secondInterpreterWebRtcVerifier.assertStreamsCount(secondInterpreterExpectedStreamsCount);

        int secondInterpreterIncomingStreamIndex = 1;

        secondInterpreterWebRtcVerifier.assertIncomingAudioPresent(secondInterpreterIncomingStreamIndex);

        // Check that Interpreter 1 can hear Interpreter 2.

        WebContextUtil.switchToDefaultContext();

        WebRtcPage firstInterpreterWebRtcPage = WebRtcUtil.openWebRtcPage();
        firstInterpreterWebRtcPage.assertIsOpened();

        WebRtcVerifier firstInterpreterWebRtcVerifier = new WebRtcVerifier(firstInterpreterWebRtcPage);

        int firstInterpreterExpectedStreamsCount = 2;

        firstInterpreterWebRtcVerifier.assertStreamsCount(firstInterpreterExpectedStreamsCount);

        int firstInterpreterIncomingStreamIndex = 2;

        firstInterpreterWebRtcVerifier.assertIncomingAudioPresent(firstInterpreterIncomingStreamIndex);

        // Restart all lines as Interpreter 1.

        firstInterpreterPage.restartAllLines();

        // Check that after short pause Interpreter 1 is streaming again and can hear Interpreter 2.

        firstInterpreterVerifier.assertStreaming();

        firstInterpreterWebRtcVerifier.assertStreamsCount(firstInterpreterExpectedStreamsCount);

        firstInterpreterIncomingStreamIndex = 1;

        firstInterpreterWebRtcVerifier.assertIncomingAudioPresent(firstInterpreterIncomingStreamIndex);

        // Check that Interpreter 2 can hear Interpreter 1.

        WebContextUtil.switchToContext(secondInterpreterContext);

        secondInterpreterWebRtcVerifier.assertStreamsCount(secondInterpreterExpectedStreamsCount);

        secondInterpreterIncomingStreamIndex = 2;

        secondInterpreterWebRtcVerifier.assertIncomingAudioPresent(secondInterpreterIncomingStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
