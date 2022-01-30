package com.intpfy.test.core_regression.smoke_test.audio_video_streams;

import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
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

public class InterpreterCanHearRelayLanguageTest extends BaseWebTest implements WebRtcTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create random event with 2 Languages.
    private final Event event = Event
            .createRandomBuilder()
            .withLanguages(Language.getRandomLanguages(2))
            .build();

    private final Language mainLanguage = event.getLanguages().get(0);
    private final Language relayLanguage = event.getLanguages().get(1);

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that Interpreter can hear relay language",
            description = "Interpreter can hear Relay language.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    INTERPRETER
            }
    )
    @TestRailCase("834")
    public void test() {

        // Log in as Interpreter 1 with Main and Relay language.

        String firstInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage firstInterpreterPage = authorizer.logInAsInterpreter(event, firstInterpreterName, mainLanguage, relayLanguage);
        firstInterpreterPage.assertIsOpened();

        // Change Outgoing language to Relay.

        firstInterpreterPage.changeOutgoingLanguageToRelay();

        InterpreterVerifier firstInterpreterVerifier = new InterpreterVerifier(firstInterpreterPage);

        firstInterpreterVerifier.assertOutgoingLanguageChanged(relayLanguage);

        // Start streaming.

        firstInterpreterPage.unmute();
        firstInterpreterVerifier.assertStreaming();

        // Log in as Interpreter 2 with same Main and Relay language as Interpreter 1.

        WebContextUtil.switchToNewContext();

        String secondInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage secondInterpreterPage = authorizer.logInAsInterpreter(event, secondInterpreterName, mainLanguage, relayLanguage);
        secondInterpreterPage.assertIsOpened();

        // Change Outgoing language to Relay.

        InterpreterVerifier secondInterpreterVerifier = new InterpreterVerifier(secondInterpreterPage);

        secondInterpreterPage.changeOutgoingLanguageToRelay();

        secondInterpreterVerifier.assertOutgoingLanguageChanged(relayLanguage);

        // Check that Audio from Interpreter 1 present.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 1;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int firstInterpreterStreamIndex = 1;
        webRtcVerifier.assertIncomingAudioPresent(firstInterpreterStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
