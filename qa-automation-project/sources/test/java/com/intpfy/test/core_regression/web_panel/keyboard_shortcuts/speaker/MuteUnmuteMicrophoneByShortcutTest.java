package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.speaker;


import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import com.intpfyqa.utils.TestUtils;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class MuteUnmuteMicrophoneByShortcutTest extends BaseWebTest implements EventTest {

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
            testName = "Mute/Unmute microphone by shortcut",
            description = "Speaker can turn Mic ON / OFF by shortcut.",
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SHORTCUTS,
                    SPEAKER
            }
    )
    @TestRailCase("2007")
    public void test() {

        // Log in as Host with Audio only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithAudioOnly(event, hostName);
        hostPage.assertIsOpened();

        // Check that Mic ON and Volume level changing.

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.verifyMicOn();
        hostVerifier.verifyVolumeLevelChanging();

        // Turn Mic OFF by shortcut.

        hostPage.turnMicOffByShortcut();

        hostVerifier.verifyMicOff();
        hostVerifier.verifyVolumeLevelNotChanging();

        // Sleep for 3 seconds because shortcuts are not working immediately one after another.

        TestUtils.sleep(3000);

        // Turn Mic ON by shortcut.

        hostPage.turnMicOnByShortcut();

        hostVerifier.verifyMicOn();
        hostVerifier.verifyVolumeLevelChanging();

        throwErrorIfVerificationsFailed();
    }
}
