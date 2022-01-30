package com.intpfy.test.core_regression.smoke_test.audio_video_streams;

import com.intpfy.gui.dialogs.common.StreamingAllowedDW;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.WebRtcTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.util.WebRtcUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfy.verifiers.media.web_rtc.WebRtcVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class SpeakerCanShareScreenTest extends BaseWebTest implements WebRtcTest {

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
            testName = "Check that speaker can share a screen",
            description = "Speaker can start stream with screen sharing.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("829")
    public void test() {

        // Log in as Host with Audio only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithAudioOnly(event, hostName);
        hostPage.assertIsOpened();

        // Log in as Speaker.

        WebApplicationContext speakerContext = WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Switch to Host and share screen.

        WebContextUtil.switchToDefaultContext();

        hostPage.enableScreenSharing();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.assertStreamWithScreenSharingGoingOnUI();

        // Switch to Speaker and check that screen container for Host visible.

        WebContextUtil.switchToContext(speakerContext);

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertScreenContainerVisible(hostName);

        // Check that video from Host present.

        WebRtcPage speakerWebRtcPage = WebRtcUtil.openWebRtcPage();
        speakerWebRtcPage.assertIsOpened();

        WebRtcVerifier speakerWebRtcVerifier = new WebRtcVerifier(speakerWebRtcPage);

        int speakerExpectedStreamsCount = 2;
        speakerWebRtcVerifier.assertStreamsCount(speakerExpectedStreamsCount);

        int speakerIncomingStreamFromHostIndex = 2;
        speakerWebRtcVerifier.assertIncomingVideoPresent(speakerIncomingStreamFromHostIndex);

        // Switch to Host and allow Speaker to Stream.

        WebContextUtil.switchToDefaultContext();

        hostPage.allowSpeakerToStreamInParticipants(speakerName);

        // Switch to Speaker and accept streaming with Audio only.

        WebContextUtil.switchToContext(speakerContext);

        StreamingAllowedDW streamingAllowedDW = new StreamingAllowedDW(speakerPage);
        streamingAllowedDW.assertIsOpened();

        streamingAllowedDW.selectAudioOnly();
        streamingAllowedDW.assertNotVisible();

        // Share screen.

        speakerPage.enableScreenSharing();
        speakerVerifier.assertStreamWithScreenSharingGoingOnUI();

        // Switch to Host and check that screen container for Speaker visible.

        WebContextUtil.switchToDefaultContext();

        hostVerifier.assertScreenContainerVisible(speakerName);

        // Check that video from Speaker present.

        WebRtcPage hostWebRtcPage = WebRtcUtil.openWebRtcPage();
        hostWebRtcPage.assertIsOpened();

        WebRtcVerifier hostWebRtcVerifier = new WebRtcVerifier(hostWebRtcPage);

        int hostExpectedStreamsCount = 4;
        hostWebRtcVerifier.assertStreamsCount(hostExpectedStreamsCount);

        int hostIncomingStreamFromSpeakerIndex = 4;
        hostWebRtcVerifier.assertIncomingVideoPresent(hostIncomingStreamFromSpeakerIndex);

        throwErrorIfVerificationsFailed();
    }
}
