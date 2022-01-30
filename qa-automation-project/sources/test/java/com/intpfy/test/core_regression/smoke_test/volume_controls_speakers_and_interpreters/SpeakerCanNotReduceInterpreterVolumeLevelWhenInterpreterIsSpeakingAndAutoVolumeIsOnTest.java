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
import static com.intpfyqa.settings.WebRtcSettings.MEASUREMENT_TIMEOUT;


public class SpeakerCanNotReduceInterpreterVolumeLevelWhenInterpreterIsSpeakingAndAutoVolumeIsOnTest extends BaseWebTest implements WebRtcTest {

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
            testName = "Check that speaker can't reduce Interpreter volume level when Interpreter is speaking and Auto-Volume is ON",
            description = "Speaker can't reduce Interpreting (language) channel volume level when Interpreter is speaking and Auto-Volume ON.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("815")
    public void test() {

        // Log in as Interpreter with Outgoing language and start streaming.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        interpreterPage.unmute();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertStreaming();

        // Log in as Speaker with Audio only.

        WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeakerWithAudioOnly(event, speakerName);
        speakerPage.assertIsOpened();

        // Select Interpreting language.

        LanguageSettingsDW languageSettingsDW = speakerPage.selectInterpretingLanguage();
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.selectIncomingLanguage(language);

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);

        speakerVerifier.assertInterpretingLanguageSelected(language);

        // Check that Auto-volume enabled.

        speakerVerifier.assertAutoVolumeEnabled();

        // Check that Audio from Interpreter present.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPageInReducedWindow();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 2;

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int interpreterStreamIndex = 2;

        webRtcVerifier.assertIncomingAudioPresent(interpreterStreamIndex);

        // Measure Audio energy delta for Language channel for the 1-st time.

        double firstTotalAudioEnergyDelta = webRtcPage.getIncomingAudioTotalEnergyDelta(MEASUREMENT_TIMEOUT, interpreterStreamIndex);

        // Try to reduce Volume level for Language channel.

        speakerPage.setMinVolumeLevelForLanguageChannel();
        speakerVerifier.assertVolumeLevelIsMaxForLanguageChannel(); // Volume level is automatically reverted to Max.

        // Measure Audio energy delta for Language channel for the 2-nd time.

        double secondTotalAudioEnergyDelta = webRtcPage.getIncomingAudioTotalEnergyDelta(MEASUREMENT_TIMEOUT, interpreterStreamIndex);

        // Check that Volume level in Language channel not changed.

        String verificationMessage = "Volume level in Language channel not changed.";
        webRtcVerifier.assertVolumeLevelEqual(firstTotalAudioEnergyDelta, secondTotalAudioEnergyDelta, verificationMessage);

        throwErrorIfVerificationsFailed();
    }
}
