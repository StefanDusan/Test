package com.intpfy.test.core_regression.smoke_test.auto_volume;

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
import static com.intpfyqa.settings.WebRtcSettings.MEASUREMENT_TIMEOUT;

public class SpeakerWillHearInterpreterWhenInterpreterIsSpeakingAndAutoVolumeIsOnTest extends BaseWebTest implements WebRtcTest {

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
            testName = "Check that speaker will hear Interpreter when Interpreter is speaking and auto volume is ON",
            description = "Speaker will hear Interpreter when Interpreter is speaking and Auto-volume ON.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("799")
    public void test() {

        // Log in as Speaker 1 with Audio Only.

        String firstSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage firstSpeakerPage = authorizer.logInAsSpeakerWithAudioOnly(event, firstSpeakerName);
        firstSpeakerPage.assertIsOpened();

        SpeakerVerifier firstSpeakerVerifier = new SpeakerVerifier(firstSpeakerPage);
        firstSpeakerVerifier.assertStreamWithAudioOnlyGoingOnUI();

        // Log in as Interpreter with Outgoing language and start streaming.

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

        // Check that Audio from Speaker 1 present.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPageInReducedWindow();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 2;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int firstSpeakerStreamIndex = 1;
        webRtcVerifier.assertIncomingAudioPresent(firstSpeakerStreamIndex);

        // Check that Auto-volume unavailable (toggle not visible).

        SpeakerVerifier secondSpeakerVerifier = new SpeakerVerifier(secondSpeakerPage);

        secondSpeakerVerifier.assertAutoVolumeUnavailable();

        // Measure Audio energy delta from Speaker 1 (Auto-volume disabled).

        double sourceAudioEnergyDeltaWithoutAutoVolume = webRtcPage.getIncomingAudioTotalEnergyDelta(MEASUREMENT_TIMEOUT, firstSpeakerStreamIndex);

        // Select Interpreting language.

        LanguageSettingsDW languageSettingsDW = secondSpeakerPage.selectInterpretingLanguage();
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.selectIncomingLanguage(language);

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        secondSpeakerVerifier.assertInterpretingLanguageSelected(language);

        // Check that Auto-volume enabled.

        secondSpeakerVerifier.assertAutoVolumeEnabled();

        // Check that Audio from Interpreter present.

        expectedStreamsCount = 3;

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int interpreterStreamIndex = 3;

        webRtcVerifier.assertIncomingAudioPresent(interpreterStreamIndex);

        // Measure Audio energy delta from Speaker 1 (Auto-volume enabled).

        double sourceAudioEnergyDeltaWithEnabledAutoVolume = webRtcPage.getIncomingAudioTotalEnergyDelta(MEASUREMENT_TIMEOUT, 1);

        // Check that Audio volume level in Source channel decreased.

        String verificationMessage = "Incoming audio volume level in Source channel decreased.";
        webRtcVerifier.assertVolumeLevelGreater(sourceAudioEnergyDeltaWithEnabledAutoVolume, sourceAudioEnergyDeltaWithoutAutoVolume, verificationMessage);

        throwErrorIfVerificationsFailed();
    }
}
