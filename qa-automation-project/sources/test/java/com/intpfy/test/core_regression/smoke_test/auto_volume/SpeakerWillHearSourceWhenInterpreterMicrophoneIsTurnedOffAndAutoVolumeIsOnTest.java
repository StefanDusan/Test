package com.intpfy.test.core_regression.smoke_test.auto_volume;

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
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class SpeakerWillHearSourceWhenInterpreterMicrophoneIsTurnedOffAndAutoVolumeIsOnTest extends BaseWebTest implements WebRtcTest {

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
            testName = "Check that speaker will hear Source when Interpreter microphone is turned off and Auto volume is ON",
            description = "Speaker will hear Source when Interpreter Mic OFF and Auto-volume ON.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("800")
    public void test() {

        // Log in as Speaker 1 with Audio only.

        String firstSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage firstSpeakerPage = authorizer.logInAsSpeakerWithAudioOnly(event, firstSpeakerName);
        firstSpeakerPage.assertIsOpened();

        SpeakerVerifier firstSpeakerVerifier = new SpeakerVerifier(firstSpeakerPage);

        firstSpeakerVerifier.assertStreamWithAudioOnlyGoingOnUI();

        // Log in as Interpreter and start streaming.

        WebApplicationContext interpreterContext = WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        interpreterPage.unmute();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertStreaming();

        // Log in as Speaker 2 with Audio only.

        WebApplicationContext secondSpeakerContext = WebContextUtil.switchToNewContext();

        String secondSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage secondSpeakerPage = authorizer.logInAsSpeakerWithAudioOnly(event, secondSpeakerName);
        secondSpeakerPage.assertIsOpened();

        // Check that Speaker 2 can hear Speaker 1.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPageInReducedWindow();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 2;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int firstSpeakerStreamIndex = 1;

        webRtcVerifier.assertIncomingAudioPresent(firstSpeakerStreamIndex);

        // Select Interpreting language.

        LanguageSettingsDW languageSettingsDW = secondSpeakerPage.selectInterpretingLanguage();
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.selectIncomingLanguage(language);

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        SpeakerVerifier secondSpeakerVerifier = new SpeakerVerifier(secondSpeakerPage);

        secondSpeakerVerifier.assertInterpretingLanguageSelected(language);

        // Check that Auto-volume enabled.

        secondSpeakerVerifier.assertAutoVolumeEnabled();

        // Switch to Interpreter and turn Mic OFF.

        WebContextUtil.switchToContext(interpreterContext);

        interpreterPage.mute();
        interpreterVerifier.assertMuted();

        // Switch to Speaker 2 and check that Auto-volume is still enabled.

        WebContextUtil.switchToContext(secondSpeakerContext);

        secondSpeakerVerifier.assertAutoVolumeEnabled();

        // Check that Speaker 2 can still hear Speaker 1.

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        webRtcVerifier.assertIncomingAudioPresent(firstSpeakerStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
