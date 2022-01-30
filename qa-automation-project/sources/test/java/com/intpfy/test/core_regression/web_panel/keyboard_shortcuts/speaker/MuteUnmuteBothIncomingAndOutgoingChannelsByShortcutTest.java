package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.speaker;


import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class MuteUnmuteBothIncomingAndOutgoingChannelsByShortcutTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Language language = Language.getRandomLanguage();

    // Create 'Connect Pro (Classroom)' event with 1 Language.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .withLanguage(language)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Mute/unmute both incoming and outgoing channels by shortcut",
            description = "Speaker can mute / unmute Source and Interpreting 'language' channels by shortcut.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SHORTCUTS,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("2026")
    public void test() {

        // Log in as Host with Audio only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithAudioOnly(event, hostName);
        hostPage.assertIsOpened();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);
        hostVerifier.assertStreamWithAudioOnlyGoingOnUI();

        // Log in as Interpreter with Outgoing language.

        WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        // Start streaming.

        interpreterPage.unmute();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertStreaming();

        // Log in as Speaker.

        WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Select Interpreting language.

        LanguageSettingsDW languageSettingsDW = speakerPage.selectInterpretingLanguage();
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.selectIncomingLanguage(language);

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);

        speakerVerifier.assertInterpretingLanguageSelected(language);

        // Check that Audio present in Source and Language channels.

        speakerVerifier.assertAudioPresentInSourceAndLanguageChannel();

        // Mute Source and Language channels by shortcut and check that Audio not present.

        speakerPage.muteSourceAndLanguageChannelsByShortcut();
        speakerVerifier.assertAudioNotPresentInSourceAndLanguageChannel();

        // Unmute Source and Language channels by shortcut and check that Audio present.

        speakerPage.unmuteSourceAndLanguageChannelsByShortcut();
        speakerVerifier.assertAudioPresentInSourceAndLanguageChannel();

        throwErrorIfVerificationsFailed();
    }
}
