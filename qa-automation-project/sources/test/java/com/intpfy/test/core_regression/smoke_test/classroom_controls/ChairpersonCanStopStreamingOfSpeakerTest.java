package com.intpfy.test.core_regression.smoke_test.classroom_controls;

import com.intpfy.gui.dialogs.common.StreamingAllowedDW;
import com.intpfy.gui.dialogs.speaker.DisableStreamingDW;
import com.intpfy.gui.dialogs.speaker.HostHasStoppedYourStreamingDW;
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

public class ChairpersonCanStopStreamingOfSpeakerTest extends BaseWebTest implements WebRtcTest {

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
            testName = "Check that Chairperson can stop streaming of speaker",
            description = "Host can stop streaming of Speaker.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("797")
    public void test() {

        // Log in as Host with Meeting control only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Log in as Speaker.

        WebApplicationContext speakerContext = WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Switch to Host and allow Speaker to stream.

        WebContextUtil.switchToDefaultContext();

        hostPage.allowSpeakerToStreamInParticipants(speakerName);

        // Switch to Speaker and accept streaming with Audio and Video.

        WebContextUtil.switchToContext(speakerContext);

        StreamingAllowedDW streamingAllowedDW = new StreamingAllowedDW(speakerPage);
        streamingAllowedDW.assertIsOpened();

        streamingAllowedDW.selectAudioAndVideo();
        streamingAllowedDW.assertNotVisible();

        // Check that stream with Audio and Video going on UI.

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);

        speakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Check that Outgoing Audio and Video present.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int speakerExpectedStreamsCount = 1;
        webRtcVerifier.assertStreamsCount(speakerExpectedStreamsCount);

        int speakerOutgoingStreamIndex = 1;
        webRtcVerifier.assertOutgoingAudioAndVideoPresent(speakerOutgoingStreamIndex);

        // Switch to Host and disallow Speaker to stream.

        WebContextUtil.switchToDefaultContext();

        DisableStreamingDW disableStreamingDW = hostPage.disallowSpeakerToStreamInParticipants(speakerName);
        disableStreamingDW.assertIsOpened();

        disableStreamingDW.confirm();
        disableStreamingDW.assertNotVisible();

        // Switch to Speaker and confirm 'Host has stopped your streaming' dialog window.

        WebContextUtil.switchToContext(speakerContext);

        HostHasStoppedYourStreamingDW hostHasStoppedYourStreamingDW = new HostHasStoppedYourStreamingDW(speakerPage);
        hostHasStoppedYourStreamingDW.assertIsOpened();

        hostHasStoppedYourStreamingDW.confirm();
        hostHasStoppedYourStreamingDW.assertNotVisible();

        // Check that no streams present on both UI and WebRTC sides

        speakerVerifier.assertNoStreamGoingOnUI();

        webRtcVerifier.assertNoStreamsPresent();

        throwErrorIfVerificationsFailed();
    }
}
