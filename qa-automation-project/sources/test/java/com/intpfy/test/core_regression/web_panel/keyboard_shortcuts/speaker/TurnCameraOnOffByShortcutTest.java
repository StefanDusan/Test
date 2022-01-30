package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.speaker;


import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import com.intpfyqa.utils.TestUtils;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class TurnCameraOnOffByShortcutTest extends BaseWebTest implements EventTest {

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
            testName = "Turn camera on/off by shortcut",
            description = "Speaker can turn Camera ON / OFF by shortcut.",
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SHORTCUTS,
                    SPEAKER
            }
    )
    @TestRailCase("2011")
    public void test() {

        // Log in as Host with Audio and Video.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithAudioAndVideo(event, hostName);
        hostPage.assertIsOpened();

        // Check that Camera ON and Host video container is not 'Audio only'.

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.verifyCameraOn();
        hostVerifier.verifyPublisherVideoContainerIsNotAudioOnly();

        // Turn Camera OFF by shortcut.

        hostPage.turnCameraOffByShortcut();

        hostVerifier.verifyCameraOff();
        hostVerifier.verifyPublisherVideoContainerIsAudioOnly();

        // Sleep for 3 seconds because shortcuts are not working immediately one after another.

        TestUtils.sleep(3000);

        // Turn Camera ON by shortcut.

        hostPage.turnCameraOnByShortcut();

        hostVerifier.verifyCameraOn();
        hostVerifier.verifyPublisherVideoContainerIsNotAudioOnly();

        throwErrorIfVerificationsFailed();
    }
}
