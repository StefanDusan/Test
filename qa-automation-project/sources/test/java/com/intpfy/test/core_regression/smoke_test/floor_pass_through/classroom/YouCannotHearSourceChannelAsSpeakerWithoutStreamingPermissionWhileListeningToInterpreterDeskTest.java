package com.intpfy.test.core_regression.smoke_test.floor_pass_through.classroom;

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

public class YouCannotHearSourceChannelAsSpeakerWithoutStreamingPermissionWhileListeningToInterpreterDeskTest extends BaseWebTest implements WebRtcTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect Pro (Classroom)' event with 2 Languages
    // and enabled 'Interpreter desks: Floor is inserted by external hardware into language channels' setting.
    private final Event event = Event
            .createConnectProClassroomBuilder()
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
            testName = "You cannot hear source channel as speaker without streaming permission while listening to interpreter desk",
            description = "Speaker can hear only Incoming channel while listening to Interpreter desk.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("2001")
    public void test() {

        // Log in as Host with Audio and Video.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithAudioAndVideo(event, hostName);
        hostPage.assertIsOpened();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);
        hostVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Interpreter with Outgoing language 1 and start streaming.

        WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        interpreterPage.unmute();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertStreaming();

        // Log in as Speaker.

        WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Select Language 1 as Interpreting.

        LanguageSettingsDW languageSettingsDW = speakerPage.selectInterpretingLanguage();
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.selectIncomingLanguage(language);

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);

        speakerVerifier.assertInterpretingLanguageSelected(language);

        // Check that Audio from Interpreter present.

        speakerVerifier.verifyAudioPresentInLanguageChannel();

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 2;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int interpreterStreamIndex = 2;
        webRtcVerifier.verifyIncomingAudioPresent(interpreterStreamIndex);

        // Check that Audio from Host not present.

        speakerVerifier.verifyAudioNotPresentInSourceChannel();

        int hostStreamIndex = 1;
        webRtcVerifier.verifyIncomingAudioNotPresent(hostStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
