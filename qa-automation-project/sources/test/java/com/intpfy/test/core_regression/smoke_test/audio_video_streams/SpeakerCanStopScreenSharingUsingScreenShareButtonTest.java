package com.intpfy.test.core_regression.smoke_test.audio_video_streams;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class SpeakerCanStopScreenSharingUsingScreenShareButtonTest extends BaseWebTest implements EventTest {

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
            testName = "Check that Speaker can stop screen sharing using 'Screen share' button",
            description = "Speaker can stop screen sharing using 'Screen share' button.",
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("1648")
    public void test() {

        // Log in as Host with Audio only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithAudioOnly(event, hostName);
        hostPage.assertIsOpened();

        // Share screen and check that stream with Screen sharing going.

        hostPage.enableScreenSharing();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.assertStreamWithScreenSharingGoingOnUI();

        // Disable screen sharing and check that stream with Screen sharing not going.

        hostPage.disableScreenSharing();

        hostVerifier.assertStreamWithScreenSharingNotGoingOnUI();

        throwErrorIfVerificationsFailed();
    }
}
