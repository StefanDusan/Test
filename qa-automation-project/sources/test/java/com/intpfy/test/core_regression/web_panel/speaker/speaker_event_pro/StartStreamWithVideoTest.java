package com.intpfy.test.core_regression.web_panel.speaker.speaker_event_pro;

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

public class StartStreamWithVideoTest extends BaseWebTest implements WebRtcTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Event Pro' event.
    private final Event event = Event
            .createEventProBuilder()
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check the start stream with video",
            description = "Speaker can stream with Audio and Video in 'Event Pro' event.",
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("357")
    public void test() {

        // Log in as Speaker.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Connect and check that stream with Audio and Video going on UI.

        speakerPage.connect();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.verifyStreamWithAudioAndVideoGoingOnUI();

        // Check that Outgoing Audio and Video present.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 1;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int outgoingStreamIndex = 1;
        webRtcVerifier.assertOutgoingAudioAndVideoPresent(outgoingStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
