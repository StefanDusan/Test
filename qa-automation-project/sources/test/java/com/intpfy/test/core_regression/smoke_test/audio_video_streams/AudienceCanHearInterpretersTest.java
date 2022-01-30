package com.intpfy.test.core_regression.smoke_test.audio_video_streams;

import com.intpfy.gui.pages.event.AudiencePage;
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
import com.intpfy.verifiers.event.audience.AudienceVerifier;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.media.web_rtc.WebRtcVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class AudienceCanHearInterpretersTest extends BaseWebTest implements WebRtcTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create random event with 2 Languages.
    private final Event event = Event
            .createRandomBuilder()
            .withLanguages(Language.getRandomLanguages(2))
            .build();

    private final Language firstLanguage = event.getLanguages().get(0);
    private final Language secondLanguage = event.getLanguages().get(1);

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that audience can hear interpreters",
            description = "Audience can hear Interpreters.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    AUDIENCE,
                    INTERPRETER
            }
    )
    @TestRailCase("824")
    public void test() {

        // Log in as Interpreter 1 with Outgoing language 1.

        String firstInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage firstInterpreterPage = authorizer.logInAsInterpreter(event, firstInterpreterName, firstLanguage);
        firstInterpreterPage.assertIsOpened();

        // Start streaming.

        firstInterpreterPage.unmute();

        InterpreterVerifier firstInterpreterVerifier = new InterpreterVerifier(firstInterpreterPage);
        firstInterpreterVerifier.assertStreaming();

        // Log in as Interpreter 2 with Outgoing language 2.

        WebContextUtil.switchToNewContext();

        String secondInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage secondInterpreterPage = authorizer.logInAsInterpreter(event, secondInterpreterName, secondLanguage);
        secondInterpreterPage.assertIsOpened();

        // Start streaming.

        secondInterpreterPage.unmute();

        InterpreterVerifier secondInterpreterVerifier = new InterpreterVerifier(secondInterpreterPage);
        secondInterpreterVerifier.assertStreaming();

        // Log in as Audience.

        WebContextUtil.switchToNewContext();

        AudiencePage audiencePage = authorizer.logInAsAudience(event);
        audiencePage.assertIsOpened();

        // Check that no Language channel selected by default.

        AudienceVerifier audienceVerifier = new AudienceVerifier(audiencePage);
        audienceVerifier.verifyNoLanguageChannelSelected();

        // Check that no streams present.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);
        webRtcVerifier.verifyNoStreamsPresent();

        // Select Language channel of Interpreter 1.

        audiencePage.selectLanguageChannel(firstLanguage);
        audienceVerifier.assertConnectedToLanguageChannel(firstLanguage);

        // Check that Audio from Interpreter 1 present.

        int expectedStreamsCount = 1;

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int languageChannelStreamIndex = 1;

        webRtcVerifier.verifyIncomingAudioPresent(languageChannelStreamIndex);

        // Disconnect from Interpreter 1 Language channel.

        audiencePage.disconnect();
        audienceVerifier.assertDisconnected();

        // Check that no streams present.

        webRtcVerifier.assertNoStreamsPresent();

        // Select Language channel of Interpreter 2.

        audiencePage.selectLanguageChannel(secondLanguage);
        audienceVerifier.assertConnectedToLanguageChannel(secondLanguage);

        // Check that Audio from Interpreter 2 present.

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        webRtcVerifier.verifyIncomingAudioPresent(languageChannelStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
