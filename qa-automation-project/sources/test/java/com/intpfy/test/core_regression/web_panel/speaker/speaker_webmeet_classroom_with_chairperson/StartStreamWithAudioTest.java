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

public class StartStreamWithAudioTest extends BaseWebTest implements WebRtcTest {

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
            testName = "Check the start stream with audio",
            description = "Host can stream with Audio only in 'Connect Pro (Classroom)' event.",
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("1523")
    public void test() {

        // Log in as Host with Audio only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithAudioOnly(event, hostName);
        hostPage.assertIsOpened();

        // Check that stream with Audio only going on UI.

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);
        hostVerifier.verifyStreamWithAudioOnlyGoingOnUI();

        // Check that Outgoing Audio present.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 1;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int outgoingStreamIndex = 1;
        webRtcVerifier.assertOutgoingAudioPresent(outgoingStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
