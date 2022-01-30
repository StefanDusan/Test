package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.interpreter;


import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
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

public class EnableDisableActiveSpeakerSettingByShortcutTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect Pro (Classroom)' event.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Enable/disable the ‘Active speaker’ setting by shortcut",
            description = "Interpreter can enable / disable 'Active speaker' setting by shortcut.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SHORTCUTS,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("2034")
    public void test() {

        // Log in as Host 1 with Audio and Video.

        String firstHostName = RandomUtil.createRandomHostName();
        SpeakerPage firstHostPage = authorizer.logInAsHostWithAudioAndVideo(event, firstHostName);
        firstHostPage.assertIsOpened();

        SpeakerVerifier firstHostVerifier = new SpeakerVerifier(firstHostPage);
        firstHostVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Host 2 with Audio and Video.

        WebContextUtil.switchToNewContext();

        String secondHostName = RandomUtil.createRandomHostName();
        SpeakerPage secondHostPage = authorizer.logInAsHostWithAudioAndVideo(event, secondHostName);
        secondHostPage.assertIsOpened();

        SpeakerVerifier secondHostVerifier = new SpeakerVerifier(secondHostPage);

        secondHostVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Turn Mic OFF.

        secondHostPage.turnMicOff();
        secondHostVerifier.assertMuted();

        // Log in as Interpreter.

        WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName);
        interpreterPage.assertIsOpened();

        // Check that video containers for Host 1 and Host 2 visible.

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);

        interpreterVerifier.assertVideoContainerVisible(firstHostName);
        interpreterVerifier.assertVideoContainerVisible(secondHostName);

        // Check that 'All Speakers' setting is enabled.

        interpreterVerifier.assertAllSpeakersSettingEnabled();

        // Check that only Host 1 video container active (placed in the center).

        interpreterVerifier.assertVideoContainerActive(firstHostName);
        interpreterVerifier.assertVideoContainerInactive(secondHostName);

        // Enable 'Active Speaker' setting by shortcut.

        interpreterPage.enableActiveSpeakerSettingByShortcut();
        interpreterVerifier.assertActiveSpeakerSettingEnabled();

        // Check that only Host 1 video container visible.

        interpreterVerifier.assertVideoContainerVisible(firstHostName);
        interpreterVerifier.assertVideoContainerNotVisible(secondHostName);

        // Enable 'All Speakers' setting by shortcut.

        interpreterPage.enableAllSpeakersSettingByShortcut();
        interpreterVerifier.assertAllSpeakersSettingEnabled();

        // Check that video containers for Host 1 and Host 2 visible.

        interpreterVerifier.assertVideoContainerVisible(firstHostName);
        interpreterVerifier.assertVideoContainerVisible(secondHostName);

        // Check that only Host 1 video container active (placed in the center).

        interpreterVerifier.assertVideoContainerActive(firstHostName);
        interpreterVerifier.assertVideoContainerInactive(secondHostName);

        throwErrorIfVerificationsFailed();
    }
}
