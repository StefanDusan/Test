package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;

import com.intpfy.gui.dialogs.common.StreamingAllowedDW;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.WebRtcTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.BrowserUtil;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.util.WebRtcUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfy.verifiers.media.web_rtc.WebRtcVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class DisconnectAsHostTest extends BaseWebTest implements WebRtcTest {

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
            testName = "Check the disconnect as Host",
            description = "Host can Disconnect and start streaming after Connecting again.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("1827")
    public void test() {

        // Log in as Host with Audio and Video.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithAudioAndVideo(event, hostName);
        hostPage.assertIsOpened();

        // Check that Outgoing Audio and Video present.

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 1;

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int outgoingStreamIndex = 1;

        webRtcVerifier.assertOutgoingAudioAndVideoPresent(outgoingStreamIndex);

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

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Switch to Host and check that Audio and Video from Speaker present.

        hostVerifier.assertVideoContainerVisible(speakerName);

        expectedStreamsCount = 2;

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int speakerStreamIndex = 2;

        webRtcVerifier.assertIncomingAudioAndVideoPresent(speakerStreamIndex);

        // Disconnect.

        hostPage.disconnect();

        // Check that Outgoing Audio and Video not present.

        hostVerifier.assertNoStreamGoingOnUI();

        expectedStreamsCount = 1;

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        // Check that Audio and Video from Speaker present.

        hostVerifier.assertVideoContainerVisible(speakerName);

        speakerStreamIndex = 1;

        webRtcVerifier.assertIncomingAudioAndVideoPresent(speakerStreamIndex);

        // Log Speaker out.

        hostPage.logOutSpeakerFromParticipants(speakerName);
        hostVerifier.assertSpeakerNotPresentInParticipants(speakerName);

        // Switch to Speaker and check that 'Login' page opened.

        WebContextUtil.switchToContext(speakerContext);

        LoginPage loginPage = new LoginPage(BrowserUtil.getActiveWindow());
        loginPage.assertIsOpened();

        // Switch to Host and Connect.

        WebContextUtil.switchToDefaultContext();

        hostPage.connect();

        // Check that Outgoing Audio and Video present.

        hostVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        webRtcVerifier.assertOutgoingAudioAndVideoPresent(outgoingStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
