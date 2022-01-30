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

public class VolumeLevelWillNotChangeForBothChannelsWhenInterpreterIsSpeakingAndAutoVolumeIsOffTest extends BaseWebTest implements WebRtcTest {

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
            testName = "Check that volume level will not change for both channels (100%) when Interpreter is speaking and auto volume is OFF",
            description = "Volume level will not change for Source and Language channels (100%) when Interpreter is speaking and Auto volume OFF.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("802")
    public void test() {

        // Log in as Speaker 1 with Audio only.

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
        interpreterVerifier.assertUnmuted();

        // Log in as Speaker 2 with Audio only.

        WebContextUtil.switchToNewContext();

        String secondSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage secondSpeakerPage = authorizer.logInAsSpeakerWithAudioOnly(event, secondSpeakerName);
        secondSpeakerPage.assertIsOpened();

        // Check that Volume level is Max (100%) for Source channel.

        SpeakerVerifier secondSpeakerVerifier = new SpeakerVerifier(secondSpeakerPage);

        secondSpeakerVerifier.assertVolumeLevelIsMaxForSourceChannel();

        // Check that Speaker 2 can hear Speaker 1.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPageInReducedWindow();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 2;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int firstSpeakerStreamIndex = 1;
        webRtcVerifier.assertIncomingAudioPresent(firstSpeakerStreamIndex);

        // Measure Audio energy delta from Speaker 1.

        double sourceChannelAudioEnergyDelta = webRtcPage.getIncomingAudioTotalEnergyDelta(MEASUREMENT_TIMEOUT, firstSpeakerStreamIndex);

        // Select Interpreting language.

        LanguageSettingsDW languageSettingsDW = secondSpeakerPage.selectInterpretingLanguage();
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.selectIncomingLanguage(language);

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        secondSpeakerVerifier.assertInterpretingLanguageSelected(language);

        // Check that Auto-volume enabled.

        secondSpeakerVerifier.assertAutoVolumeEnabled();

        // Check that Volume level is Max (100%) for Language channel.

        secondSpeakerVerifier.assertVolumeLevelIsMaxForLanguageChannel();

        // Check that Speaker 2 can hear Interpreter in Language channel.

        expectedStreamsCount++;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int interpreterStreamIndex = 3;
        webRtcVerifier.assertIncomingAudioPresent(interpreterStreamIndex);

        // Measure Audio energy delta from Interpreter.

        double languageChannelAudioEnergyDelta = webRtcPage.getIncomingAudioTotalEnergyDelta(MEASUREMENT_TIMEOUT, interpreterStreamIndex);

        // Disable Auto-volume.

        secondSpeakerPage.disableAutoVolume();
        secondSpeakerVerifier.assertAutoVolumeDisabled();

        // Check that Speaker 2 can still hear Speaker 1 and Interpreter when Auto-volume OFF.

        webRtcVerifier.assertIncomingAudioPresent(firstSpeakerStreamIndex);
        webRtcVerifier.assertIncomingAudioPresent(interpreterStreamIndex);

        // Measure Audio energy delta from Speaker 1 and Interpreter.

        double sourceChannelAudioEnergyDeltaWithDisabledAutoVolume = webRtcPage.getIncomingAudioTotalEnergyDelta(MEASUREMENT_TIMEOUT, firstSpeakerStreamIndex);
        double languageChannelAudioEnergyDeltaWithDisabledAutoVolume = webRtcPage.getIncomingAudioTotalEnergyDelta(MEASUREMENT_TIMEOUT, interpreterStreamIndex);

        // Check that volume level is not changed (100%) for both channels when Auto-volume is OFF.

        secondSpeakerVerifier.verifyVolumeLevelIsMaxForSourceChannel();
        secondSpeakerVerifier.verifyVolumeLevelIsMaxForLanguageChannel();

        webRtcVerifier.verifyVolumeLevelEqual(sourceChannelAudioEnergyDelta, sourceChannelAudioEnergyDeltaWithDisabledAutoVolume,
                "Volume level in Source channel is not changed (100%) when Auto-volume is OFF.");
        webRtcVerifier.verifyVolumeLevelEqual(languageChannelAudioEnergyDelta, languageChannelAudioEnergyDeltaWithDisabledAutoVolume,
                "Volume level in Language channel is not changed (100%) when Auto-volume is OFF.");

        throwErrorIfVerificationsFailed();
    }
}
