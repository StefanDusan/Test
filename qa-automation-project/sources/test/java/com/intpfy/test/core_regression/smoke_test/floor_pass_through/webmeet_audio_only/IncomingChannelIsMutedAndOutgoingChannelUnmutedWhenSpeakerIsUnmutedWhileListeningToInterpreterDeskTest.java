package com.intpfy.test.core_regression.smoke_test.floor_pass_through.webmeet_audio_only;

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
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class IncomingChannelIsMutedAndOutgoingChannelUnmutedWhenSpeakerIsUnmutedWhileListeningToInterpreterDeskTest extends BaseWebTest implements WebRtcTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect (WebMeet)' event with 2 Languages
    // and enabled 'Interpreter desks: Floor is inserted by external hardware into language channels' setting.
    private final Event event = Event
            .createConnectWebMeetBuilder()
            .withLanguages(Language.getRandomLanguages(2))
            .withFloorToLanguageOnInterpreterSilence(false)
            .withPreventSourceDuplicate(true)
            .build();

    private final Language language = event.getLanguages().get(0);

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Incoming channel is muted & outgoing channel unmuted when speaker is unmuted while listening to interpreter desk",
            description = "Incoming channel is muted & Outgoing channel unmuted when Speaker is unmuted while listening to Interpreter desk.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("1995")
    public void test() {

        // Log in as Speaker 1 with Audio only.

        String firstSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage firstSpeakerPage = authorizer.logInAsSpeakerWithAudioOnly(event, firstSpeakerName);
        firstSpeakerPage.assertIsOpened();

        SpeakerVerifier firstSpeakerVerifier = new SpeakerVerifier(firstSpeakerPage);
        firstSpeakerVerifier.assertStreamWithAudioOnlyGoingOnUI();

        // Log in as Interpreter with Outgoing language 1 and start streaming.

        WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        interpreterPage.unmute();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertStreaming();

        // Log in as Speaker 2 with Audio only.

        WebContextUtil.switchToNewContext();

        String secondSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage secondSpeakerPage = authorizer.logInAsSpeakerWithAudioOnly(event, secondSpeakerName);
        secondSpeakerPage.assertIsOpened();

        SpeakerVerifier secondSpeakerVerifier = new SpeakerVerifier(secondSpeakerPage);

        secondSpeakerVerifier.assertStreamWithAudioOnlyGoingOnUI();

        // Select Language 1 as Interpreting.

        LanguageSettingsDW languageSettingsDW = secondSpeakerPage.selectInterpretingLanguage();
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.selectIncomingLanguage(language);

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        secondSpeakerVerifier.assertInterpretingLanguageSelected(language);

        // Check that Interpreting (Language) channel muted.

        secondSpeakerVerifier.verifyAudioNotPresentInLanguageChannel();

        // Check that Audio from Interpreter not present.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        // There should be only 2 streams:
        // - incoming stream from Speaker 1;
        // - outgoing stream from Speaker 2;
        // Interpreter stream should not be present.
        int expectedStreamsCount = 2;
        webRtcVerifier.verifyStreamsCount(expectedStreamsCount);

        // Check that Audio from Speaker 1 present.

        secondSpeakerVerifier.verifyAudioPresentInSourceChannel();

        int firstSpeakerStreamIndex = 1;
        webRtcVerifier.verifyIncomingAudioPresent(firstSpeakerStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}