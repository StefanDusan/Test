package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;

import com.intpfy.gui.dialogs.common.StreamingAllowedDW;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.util.WebRtcUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfy.verifiers.media.web_rtc.WebRtcVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class DisconnectAsASpeakerTest extends BaseWebTest implements EventTest {

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
            testName = "Check the disconnect as a Speaker",
            description = "Speaker can Disconnect from stream.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("1828")
    public void test() {

        // Log in as Host with Audio and Video.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithAudioAndVideo(event, hostName);
        hostPage.assertIsOpened();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);
        hostVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Speaker.

        WebApplicationContext speakerContext = WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Check that Audio and Video from Host present.

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertVideoContainerVisible(hostName);

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 1;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int hostStreamIndex = 1;
        webRtcVerifier.assertIncomingAudioAndVideoPresent(hostStreamIndex);

        // Switch to Host and allow Speaker to stream.

        WebContextUtil.switchToDefaultContext();

        hostPage.allowSpeakerToStreamInParticipants(speakerName);

        // Switch to Speaker and accept streaming with Audio and Video.

        WebContextUtil.switchToContext(speakerContext);

        StreamingAllowedDW streamingAllowedDW = new StreamingAllowedDW(speakerPage);
        streamingAllowedDW.assertIsOpened();

        streamingAllowedDW.selectAudioAndVideo();
        streamingAllowedDW.assertNotVisible();

        // Check that Outgoing Audio and Video present.

        speakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        expectedStreamsCount = 2;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int outgoingStreamIndex = 2;
        webRtcVerifier.assertOutgoingAudioAndVideoPresent(outgoingStreamIndex);

        // Stop streaming and check that Outgoing Audio and Video not present.

        speakerPage.stopStreaming();

        speakerVerifier.assertNoStreamGoingOnUI();

        expectedStreamsCount = 1;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        // Check that Audio and Video from Host present.

        speakerVerifier.verifyVideoContainerVisible(hostName);

        webRtcVerifier.verifyIncomingAudioAndVideoPresent(hostStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
