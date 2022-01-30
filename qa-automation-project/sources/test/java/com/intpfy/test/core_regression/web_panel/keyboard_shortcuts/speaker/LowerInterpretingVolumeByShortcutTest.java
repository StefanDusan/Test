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
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public class LowerInterpretingVolumeByShortcutTest extends BaseWebTest implements EventTest {

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
            testName = "Lower interpreting volume by shortcut",
            description = "Speaker can lower Interpreting (language) channel volume by shortcut.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SHORTCUTS,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("2083")
    public void test() {

        // Log in as Interpreter with Outgoing language and start streaming.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        interpreterPage.unmute();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertStreaming();

        // Log in as Host with Meeting control only.

        WebContextUtil.switchToNewContext();

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Select Interpreting language.

        LanguageSettingsDW languageSettingsDW = hostPage.selectInterpretingLanguage();
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.selectIncomingLanguage(language);

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.assertInterpretingLanguageSelected(language);

        // Disable Auto-volume.

        hostPage.disableAutoVolume();
        hostVerifier.assertAutoVolumeDisabled();

        // Set Max volume level for Interpreting (language) channel.

        hostPage.setMaxVolumeLevelForLanguageChannel();
        hostVerifier.assertVolumeLevelIsMaxForLanguageChannel();

        // Connect to remove focus from Volume control (shortcut should work without Volume control being focused, see CORE-5164).

        hostPage.connect();
        hostPage.isConnected(AJAX_TIMEOUT);

        // Set Min volume level for Interpreting (language) channel by shortcut.

        hostPage.setMinVolumeLevelForLanguageChannelByShortcut();
        hostVerifier.assertVolumeLevelIsMinForLanguageChannel();

        throwErrorIfVerificationsFailed();
    }
}
