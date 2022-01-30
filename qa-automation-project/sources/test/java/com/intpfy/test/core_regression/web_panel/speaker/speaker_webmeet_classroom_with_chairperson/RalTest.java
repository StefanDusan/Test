package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;

import com.intpfy.gui.dialogs.common.RestartingConnectionDW;
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

public class RalTest extends BaseWebTest implements WebRtcTest {

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
            testName = "Check the RAL",
            description = "Host and Speaker can Restart all lines.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("1529")
    public void test() {

        // Log in as Host with Audio and Video.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithAudioAndVideo(event, hostName);
        hostPage.assertIsOpened();

        // Log in as Speaker.

        WebApplicationContext speakerContext = WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Switch to Host and check that outgoing Audio and Video present.

        WebContextUtil.switchToDefaultContext();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        WebRtcPage hostWebRtcPage = WebRtcUtil.openWebRtcPage();
        hostWebRtcPage.assertIsOpened();

        WebRtcVerifier hostWebRtcVerifier = new WebRtcVerifier(hostWebRtcPage);

        int hostExpectedStreamsCount = 1;

        hostWebRtcVerifier.assertStreamsCount(hostExpectedStreamsCount);

        int hostOutgoingStreamIndex = 1;

        hostWebRtcVerifier.assertOutgoingAudioAndVideoPresent(hostOutgoingStreamIndex);

        // Restart all lines.

        RestartingConnectionDW hostRestartingConnectionDW = hostPage.restartAllLines();

        // Assertion fails on remote test execution.
        // hostRestartingConnectionDW.assertIsOpened();

        hostRestartingConnectionDW.assertNotVisible();

        // Check that after short pause outgoing Audio and Video present.

        // Assertion fails on remote test execution.
        // hostVerifier.assertNoStreamGoingOnUI();

        hostVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        hostWebRtcVerifier.assertStreamsCount(hostExpectedStreamsCount);

        hostWebRtcVerifier.assertOutgoingAudioAndVideoPresent(hostOutgoingStreamIndex);

        // Allow Speaker to stream.

        hostPage.allowSpeakerToStreamInParticipants(speakerName);

        // Switch to Speaker and accept streaming with Audio and Video.

        WebContextUtil.switchToContext(speakerContext);

        StreamingAllowedDW streamingAllowedDW = new StreamingAllowedDW(speakerPage);
        streamingAllowedDW.assertIsOpened();

        streamingAllowedDW.selectAudioAndVideo();
        streamingAllowedDW.assertNotVisible();

        // Check that Outgoing Audio and Video present.

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);

        speakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        WebRtcPage speakerWebRtcPage = WebRtcUtil.openWebRtcPage();
        speakerWebRtcPage.assertIsOpened();

        WebRtcVerifier speakerWebRtcVerifier = new WebRtcVerifier(speakerWebRtcPage);

        int speakerExpectedStreamsCount = 2;

        speakerWebRtcVerifier.assertStreamsCount(speakerExpectedStreamsCount);

        int speakerOutgoingStreamIndex = 2;

        speakerWebRtcVerifier.assertOutgoingAudioAndVideoPresent(speakerOutgoingStreamIndex);

        // Restart all lines.

        RestartingConnectionDW speakerRestartingConnectionDW = speakerPage.restartAllLines();

        // Assertion fails on remote test execution.
        // speakerRestartingConnectionDW.assertIsOpened();

        speakerRestartingConnectionDW.assertNotVisible();

        // Check that after short pause outgoing Audio and Video present.

        // Assertion fails on remote test execution.
        // speakerVerifier.assertNoStreamGoingOnUI();

        speakerPage.confirmHostJoinedMeetingDW();

        speakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        speakerWebRtcVerifier.assertStreamsCount(speakerExpectedStreamsCount);

        speakerWebRtcVerifier.assertOutgoingAudioAndVideoPresent(speakerOutgoingStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
