package com.intpfy.test.core_regression.smoke_test.classroom_controls;

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

public class ChairpersonCanAllowSpeakerToStreamTest extends BaseWebTest implements WebRtcTest {

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
            testName = "Check that Chairperson can allow speaker to stream",
            description = "Host can allow Speaker to stream.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("795")
    public void test() {

        // Log in as Host with Meeting control only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Log in as Speaker 1.

        WebApplicationContext firstSpeakerContext = WebContextUtil.switchToNewContext();

        String firstSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage firstSpeakerPage = authorizer.logInAsSpeaker(event, firstSpeakerName);
        firstSpeakerPage.assertIsOpened();

        // Log in as Speaker 2.

        WebApplicationContext secondSpeakerContext = WebContextUtil.switchToNewContext();

        String secondSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage secondSpeakerPage = authorizer.logInAsSpeaker(event, secondSpeakerName);
        secondSpeakerPage.assertIsOpened();

        // Switch to Host and allow Speaker 1 to stream.

        WebContextUtil.switchToDefaultContext();

        hostPage.allowSpeakerToStreamInParticipants(firstSpeakerName);

        // Switch to Speaker 1 and accept streaming with Audio and Video.

        WebContextUtil.switchToContext(firstSpeakerContext);

        StreamingAllowedDW firstSpeakerStreamingAllowedDW = new StreamingAllowedDW(firstSpeakerPage);
        firstSpeakerStreamingAllowedDW.assertIsOpened();

        firstSpeakerStreamingAllowedDW.selectAudioAndVideo();
        firstSpeakerStreamingAllowedDW.assertNotVisible();

        // Check that stream with Audio and Video going on UI.

        SpeakerVerifier firstSpeakerVerifier = new SpeakerVerifier(firstSpeakerPage);

        firstSpeakerVerifier.verifyStreamWithAudioAndVideoGoingOnUI();

        // Check that Outgoing Audio and Video present.

        WebRtcPage firstSpeakerWebRtcPage = WebRtcUtil.openWebRtcPage();
        firstSpeakerWebRtcPage.assertIsOpened();

        WebRtcVerifier firstSpeakerWebRtcVerifier = new WebRtcVerifier(firstSpeakerWebRtcPage);

        int firstSpeakerExpectedStreamsCount = 1;
        firstSpeakerWebRtcVerifier.assertStreamsCount(firstSpeakerExpectedStreamsCount);

        int firstSpeakerOutgoingStreamIndex = 1;

        firstSpeakerWebRtcVerifier.verifyOutgoingAudioAndVideoPresent(firstSpeakerOutgoingStreamIndex);

        // Turn Mic OFF and check that Outgoing Audio not present.

        firstSpeakerPage.turnMicOff();
        firstSpeakerVerifier.assertMicOff();

        firstSpeakerWebRtcVerifier.verifyOutgoingAudioNotPresent(firstSpeakerOutgoingStreamIndex);

        // Turn Mic ON and check that Outgoing Audio present.

        firstSpeakerPage.turnMicOn();
        firstSpeakerVerifier.assertMicOn();

        firstSpeakerWebRtcVerifier.verifyOutgoingAudioPresent(firstSpeakerOutgoingStreamIndex);

        // Switch to Host and allow Speaker 2 to stream.

        WebContextUtil.switchToDefaultContext();

        hostPage.allowSpeakerToStreamInParticipants(secondSpeakerName);

        // Switch to Speaker 2 and accept streaming with Audio and Video.

        WebContextUtil.switchToContext(secondSpeakerContext);

        StreamingAllowedDW secondSpeakerStreamingAllowedDW = new StreamingAllowedDW(secondSpeakerPage);
        secondSpeakerStreamingAllowedDW.assertIsOpened();

        secondSpeakerStreamingAllowedDW.selectAudioAndVideo();
        secondSpeakerStreamingAllowedDW.assertNotVisible();

        // Check that stream with Audio and Video going on UI.

        SpeakerVerifier secondSpeakerVerifier = new SpeakerVerifier(secondSpeakerPage);

        secondSpeakerVerifier.verifyStreamWithAudioAndVideoGoingOnUI();

        // Check that Outgoing Audio and Video present.

        WebRtcPage secondSpeakerWebRtcPage = WebRtcUtil.openWebRtcPage();
        secondSpeakerWebRtcPage.assertIsOpened();

        WebRtcVerifier secondSpeakerWebRtcVerifier = new WebRtcVerifier(secondSpeakerWebRtcPage);

        int secondSpeakerExpectedStreamsCount = 2;
        secondSpeakerWebRtcVerifier.assertStreamsCount(secondSpeakerExpectedStreamsCount);

        int secondSpeakerOutgoingStreamIndex = 2;

        secondSpeakerWebRtcVerifier.verifyOutgoingAudioAndVideoPresent(secondSpeakerOutgoingStreamIndex);

        // Turn Camera OFF and check that Outgoing Video not present.

        secondSpeakerPage.turnCameraOff();
        secondSpeakerVerifier.assertCameraOff();

        secondSpeakerWebRtcVerifier.verifyOutgoingVideoNotPresent(secondSpeakerOutgoingStreamIndex);

        // Turn Camera ON and check that Outgoing Video present.

        secondSpeakerPage.turnCameraOn();
        secondSpeakerVerifier.assertCameraOn();

        secondSpeakerWebRtcVerifier.verifyOutgoingVideoPresent(secondSpeakerOutgoingStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
