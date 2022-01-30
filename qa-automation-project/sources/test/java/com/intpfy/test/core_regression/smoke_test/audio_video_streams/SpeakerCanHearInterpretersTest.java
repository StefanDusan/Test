package com.intpfy.test.core_regression.smoke_test.audio_video_streams;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.WebRtcTest;
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

public class SpeakerCanHearInterpretersTest extends BaseWebTest implements WebRtcTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Event Pro' event with 2 Languages.
    private final Event event = Event
            .createEventProBuilder()
            .withLanguages(Language.getRandomLanguages(2))
            .build();

    private final Language firstLanguage = event.getLanguages().get(0);
    private final Language secondLanguage = event.getLanguages().get(1);

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that speaker can hear interpreters",
            description = "Speaker can hear Interpreters.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("820")
    @TestRailBugId("CORE-6687")
    public void test() {

        // Log in as Speaker.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Log in as Interpreter 1 with Outgoing language 1.

        WebApplicationContext firstInterpreterContext = WebContextUtil.switchToNewContext();

        String firstInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage firstInterpreterPage = authorizer.logInAsInterpreter(event, firstInterpreterName, firstLanguage);
        firstInterpreterPage.assertIsOpened();

        // Log in as Interpreter 2 with Outgoing language 2.

        WebApplicationContext secondInterpreterContext = WebContextUtil.switchToNewContext();

        String secondInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage secondInterpreterPage = authorizer.logInAsInterpreter(event, secondInterpreterName, secondLanguage);
        secondInterpreterPage.assertIsOpened();

        // Switch to Speaker and select Interpreting language 1.

        WebContextUtil.switchToDefaultContext();

        LanguageSettingsDW languageSettingsDW = speakerPage.openLanguageSettings();
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.selectIncomingLanguage(firstLanguage);

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertInterpretingLanguageSelected(firstLanguage);

        // Switch to Interpreter 1 and start streaming.

        WebContextUtil.switchToContext(firstInterpreterContext);

        firstInterpreterPage.unmute();

        InterpreterVerifier firstInterpreterVerifier = new InterpreterVerifier(firstInterpreterPage);
        firstInterpreterVerifier.assertStreaming();

        // Switch to Speaker and check that Audio from Interpreter 1 present.

        WebContextUtil.switchToDefaultContext();

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 1;

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int languageChannelStreamIndex = 1;

        webRtcVerifier.verifyIncomingAudioPresent(languageChannelStreamIndex);

        // Select 'None' Interpreting language 2.

        languageSettingsDW = speakerPage.openLanguageSettings();
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.selectNoneIncomingLanguage();

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        // Check that no streams present.

        webRtcVerifier.assertNoStreamsPresent();

        // Select Interpreting language 2.

        languageSettingsDW = speakerPage.openLanguageSettings();
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.selectIncomingLanguage(secondLanguage);

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        speakerVerifier.assertInterpretingLanguageSelected(secondLanguage);

        // Switch to Interpreter 2 and start streaming.

        WebContextUtil.switchToContext(secondInterpreterContext);

        secondInterpreterPage.unmute();

        InterpreterVerifier secondInterpreterVerifier = new InterpreterVerifier(secondInterpreterPage);
        secondInterpreterVerifier.assertStreaming();

        // Switch to Speaker and check that Audio from Interpreter 2 present.

        WebContextUtil.switchToDefaultContext();

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        webRtcVerifier.verifyIncomingAudioPresent(languageChannelStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
