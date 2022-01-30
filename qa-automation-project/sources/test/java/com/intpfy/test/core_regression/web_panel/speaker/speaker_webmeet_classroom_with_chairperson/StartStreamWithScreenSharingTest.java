package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;

import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.WebRtcTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebRtcUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfy.verifiers.media.web_rtc.WebRtcVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class StartStreamWithScreenSharingTest extends BaseWebTest implements WebRtcTest {

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
            testName = "Check the start stream with screen sharing",
            description = "Host can start stream with Screen sharing.",
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("1525")
    public void test() {

        // Log in as Host with Audio only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithAudioOnly(event, hostName);
        hostPage.assertIsOpened();

        // Enable Screen sharing and check that stream with Screen sharing going on UI.

        hostPage.enableScreenSharing();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);
        hostVerifier.verifyStreamWithScreenSharingGoingOnUI();

        // Check that Outgoing Video present.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 2;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int screenSharingStreamIndex = 2;
        webRtcVerifier.assertOutgoingVideoPresent(screenSharingStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
