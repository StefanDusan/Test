package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;

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

import java.util.List;

import static com.intpfy.test.TestGroups.*;

public class ChangeIncomingLanguageTest extends BaseWebTest implements WebRtcTest {

    private static final int LANGUAGES_COUNT = 3;

    private final Authorizer authorizer = Authorizer.getInstance();

    private final List<Language> languages = Language.getRandomLanguages(LANGUAGES_COUNT);

    // Create 'Connect Pro (Classroom)' event with 3 Languages.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .withLanguages(languages)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check the change incoming language",
            description = "Speaker can change Incoming language.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("1522")
    public void test() {

        // Log in as Interpreter with Language 1 as Outgoing and start streaming.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        Language interpreterLanguage = languages.get(0);
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, languages.get(0));
        interpreterPage.assertIsOpened();

        interpreterPage.unmute();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertStreaming();

        // Log in as Host with Meeting control only.

        WebContextUtil.switchToNewContext();

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Select Language 1 as Interpreting.

        LanguageSettingsDW languageSettingsDW = hostPage.selectInterpretingLanguage();
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.selectIncomingLanguage(interpreterLanguage);

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);
        hostVerifier.verifyInterpretingLanguageSelected(interpreterLanguage);

        // Check that Audio from Interpreter present.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 1;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int interpreterStreamIndex = 1;
        webRtcVerifier.assertIncomingAudioPresent(interpreterStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
