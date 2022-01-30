package com.intpfy.test.core_regression.smoke_test.floor_pass_through.webmeet;

import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
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
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfy.verifiers.media.web_rtc.WebRtcVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class AutoVolumeIsDisabledWhenSpeakerIsListeningToInterpreterDeskTest extends BaseWebTest implements WebRtcTest {

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
            testName = "Autovolume is disabled when speaker is listening to interpreter desk",
            description = "Auto-volume is disabled when Speaker is listening to Interpreter desk.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("2005")
    public void test() {

        // Log in as Speaker 1 with Audio and Video.

        String firstSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage firstSpeakerPage = authorizer.logInAsSpeakerWithAudioAndVideo(event, firstSpeakerName);
        firstSpeakerPage.assertIsOpened();

        SpeakerVerifier firstSpeakerVerifier = new SpeakerVerifier(firstSpeakerPage);
        firstSpeakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Interpreter with Outgoing language 1 and start streaming.

        WebApplicationContext interpreterContext = WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        interpreterPage.unmute();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);

        interpreterVerifier.assertStreaming();

        // Log in as Speaker 2 with Audio and Video.

        WebApplicationContext secondSpeakerContext = WebContextUtil.switchToNewContext();

        String secondSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage secondSpeakerPage = authorizer.logInAsSpeakerWithAudioAndVideo(event, secondSpeakerName);
        secondSpeakerPage.assertIsOpened();

        SpeakerVerifier secondSpeakerVerifier = new SpeakerVerifier(secondSpeakerPage);

        secondSpeakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Select Language 1 as Interpreting.

        LanguageSettingsDW languageSettingsDW = secondSpeakerPage.selectInterpretingLanguage();
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.selectIncomingLanguage(language);

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        secondSpeakerVerifier.assertInterpretingLanguageSelected(language);

        // Check that Auto-volume unavailable (toggle not visible).

        secondSpeakerVerifier.assertAutoVolumeUnavailable();

        // Turn Mic OFF.

        secondSpeakerPage.turnMicOff();
        secondSpeakerVerifier.assertMicOff();

        // Switch to Interpreter and turn Mic OFF.

        WebContextUtil.switchToContext(interpreterContext);

        interpreterPage.mute();
        interpreterVerifier.assertMuted();

        // Switch to Speaker 2 and check that Language channel Unmuted but Volume level in it not changing.

        WebContextUtil.switchToContext(secondSpeakerContext);

        secondSpeakerVerifier.verifyLanguageChannelUnmuted();
        secondSpeakerVerifier.verifyLanguageChannelVolumeLevelNotChanging();

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

        // Check that Audio from Speaker 1 not present.

        secondSpeakerVerifier.verifyAudioNotPresentInSourceChannel();

        int firstSpeakerStreamIndex = 1;
        webRtcVerifier.verifyIncomingAudioNotPresent(firstSpeakerStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
