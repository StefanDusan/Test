package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.speaker;


import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
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
            description = "Speaker can enable / disable 'Active speaker' setting by shortcut.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SHORTCUTS,
                    SPEAKER
            }
    )
    @TestRailCase("2019")
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

        // Turn Mic OFF to let Host 1 be 'Active Speaker'.

        secondHostPage.turnMicOff();
        secondHostVerifier.assertMuted();

        // Log in as Host 3 with Meeting control only.

        WebContextUtil.switchToNewContext();

        String thirdHostName = RandomUtil.createRandomHostName();
        SpeakerPage thirdHostPage = authorizer.logInAsHostWithMeetingControlOnly(event, thirdHostName);
        thirdHostPage.assertIsOpened();

        // Check that video containers for Host 1 and Host 2 visible.

        SpeakerVerifier thirdHostVerifier = new SpeakerVerifier(thirdHostPage);

        thirdHostVerifier.assertVideoContainerVisible(firstHostName);
        thirdHostVerifier.assertVideoContainerVisible(secondHostName);

        // Check that 'All Speakers' setting enabled.

        thirdHostVerifier.assertAllSpeakersSettingEnabled();

        // Check that only Host 1 video container active (placed in the center).

        thirdHostVerifier.assertVideoContainerActive(firstHostName);
        thirdHostVerifier.assertVideoContainerInactive(secondHostName);

        // Enable 'Active Speaker' setting by shortcut.

        thirdHostPage.enableActiveSpeakerSettingByShortcut();
        thirdHostVerifier.assertActiveSpeakerSettingEnabled();

        // Check that only Host 1 video container visible.

        thirdHostVerifier.assertVideoContainerVisible(firstHostName);
        thirdHostVerifier.assertVideoContainerNotVisible(secondHostName);

        // Enable 'All Speakers' setting by shortcut.

        thirdHostPage.enableAllSpeakersSettingByShortcut();
        thirdHostVerifier.assertAllSpeakersSettingEnabled();

        // Check that video containers for Host 1 and Host 2 visible.

        thirdHostVerifier.assertVideoContainerVisible(firstHostName);
        thirdHostVerifier.assertVideoContainerVisible(secondHostName);

        // Check that only Host 1 video container active (placed in the center).

        thirdHostVerifier.assertVideoContainerActive(firstHostName);
        thirdHostVerifier.assertVideoContainerInactive(secondHostName);

        throwErrorIfVerificationsFailed();
    }
}
