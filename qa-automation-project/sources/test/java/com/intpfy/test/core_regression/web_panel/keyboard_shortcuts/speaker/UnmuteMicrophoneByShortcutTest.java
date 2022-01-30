package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.speaker;


import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.intpfy.test.TestGroups.*;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public class UnmuteMicrophoneByShortcutTest extends BaseWebTest implements EventTest {

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
            testName = "Unmute microphone by shortcut",
            description = "Speaker can turn Mic ON by shortcut.",
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SHORTCUTS,
                    SPEAKER
            }
    )
    @TestRailCase("2006")
    public void test() {

        // Log in as Host with Audio only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithAudioOnly(event, hostName);
        hostPage.assertIsOpened();

        // Turn Mic OFF.

        hostPage.turnMicOff();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.assertMicOff();
        hostVerifier.assertVolumeLevelNotChanging();

        // Turn Mic ON by shortcut for 3 seconds.

        Duration unmuteDuration = Duration.ofSeconds(3);
        hostPage.turnMicOnByShortcut(unmuteDuration);

        hostVerifier.verifyMicOn();
        hostVerifier.verifyVolumeLevelChanging();

        // Check that after unmute time passed Mic turned OFF.

        Duration micTurnedOffTimeout = AJAX_TIMEOUT.plus(unmuteDuration);

        hostVerifier.verifyMicOff(micTurnedOffTimeout);
        hostVerifier.verifyVolumeLevelNotChanging(micTurnedOffTimeout);

        throwErrorIfVerificationsFailed();
    }
}
