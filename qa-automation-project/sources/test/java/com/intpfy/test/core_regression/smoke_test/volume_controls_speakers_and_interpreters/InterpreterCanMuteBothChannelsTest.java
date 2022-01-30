package com.intpfy.test.core_regression.smoke_test.volume_controls_speakers_and_interpreters;

import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
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
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfy.verifiers.media.web_rtc.WebRtcVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class InterpreterCanMuteBothChannelsTest extends BaseWebTest implements WebRtcTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Language language = Language.getRandomLanguage();

    // Create 'Connect (WebMeet)' event with 1 Language.
    private final Event event = Event
            .createConnectWebMeetBuilder()
            .withLanguage(language)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that interpreter can mute both channels",
            description = "Interpreter can mute Incoming and Outgoing channels.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("817")
    public void test() {

        // Log in as Speaker with Audio only.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeakerWithAudioOnly(event, speakerName);
        speakerPage.assertIsOpened();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertStreamWithAudioOnlyGoingOnUI();

        // Log in as Interpreter 1 with Outgoing language and start streaming.

        WebContextUtil.switchToNewContext();

        String firstInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage firstInterpreterPage = authorizer.logInAsInterpreter(event, firstInterpreterName, language);
        firstInterpreterPage.assertIsOpened();

        firstInterpreterPage.unmute();

        InterpreterVerifier firstInterpreterVerifier = new InterpreterVerifier(firstInterpreterPage);
        firstInterpreterVerifier.assertStreaming();

        // Log in as Interpreter 2.

        WebContextUtil.switchToNewContext();

        String secondInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage secondInterpreterPage = authorizer.logInAsInterpreter(event, secondInterpreterName);
        secondInterpreterPage.assertIsOpened();

        // Check that Audio from Speaker present.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 1;

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int speakerStreamIndex = 1;

        webRtcVerifier.assertIncomingAudioPresent(speakerStreamIndex);

        // Select Outgoing language.

        LanguageSettingsDW languageSettingsDW = secondInterpreterPage.openLanguageSettings();
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.selectOutgoingLanguage(language);

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        InterpreterVerifier secondInterpreterVerifier = new InterpreterVerifier(secondInterpreterPage);
        secondInterpreterVerifier.assertOutgoingLanguageChanged(language);

        // Check that Audio from Interpreter 1 present.

        expectedStreamsCount = 2;

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int firstInterpreterStreamIndex = 2;

        webRtcVerifier.assertIncomingAudioPresent(firstInterpreterStreamIndex);

        // Check that Incoming and Outgoing channels can be Muted / Unmuted.

        webRtcVerifier.assertInterpreterCanMuteIncomingAndOutgoingChannels(secondInterpreterPage, speakerStreamIndex, firstInterpreterStreamIndex);
        webRtcVerifier.assertInterpreterCanUnmuteIncomingAndOutgoingChannels(secondInterpreterPage, speakerStreamIndex, firstInterpreterStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
