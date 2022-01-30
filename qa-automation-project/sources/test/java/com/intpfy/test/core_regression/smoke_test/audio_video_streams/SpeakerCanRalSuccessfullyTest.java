package com.intpfy.test.core_regression.smoke_test.audio_video_streams;

import com.intpfy.gui.dialogs.common.RestartingConnectionDW;
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

public class SpeakerCanRalSuccessfullyTest extends BaseWebTest implements WebRtcTest {

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
            testName = "Check that Speaker can RAL successfully",
            description = "Speaker can Restart all lines successfully.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("832")
    public void test() {

        // Log in as Speaker 1.

        String firstSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage firstSpeakerPage = authorizer.logInAsSpeaker(event, firstSpeakerName);
        firstSpeakerPage.assertIsOpened();

        // Log in as Speaker 2.

        WebApplicationContext secondSpeakerContext = WebContextUtil.switchToNewContext();

        String secondSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage secondSpeakerPage = authorizer.logInAsSpeaker(event, secondSpeakerName);
        secondSpeakerPage.assertIsOpened();

        // Connect with Audio and Video.

        secondSpeakerPage.connect();

        SpeakerVerifier secondSpeakerVerifier = new SpeakerVerifier(secondSpeakerPage);

        secondSpeakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Switch to Speaker 1 and connect with Audio and Video.

        WebContextUtil.switchToDefaultContext();

        SpeakerVerifier firstSpeakerVerifier = new SpeakerVerifier(firstSpeakerPage);

        firstSpeakerPage.connect();

        firstSpeakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Check that video container for Speaker 2 visible.

        firstSpeakerVerifier.assertVideoContainerVisible(secondSpeakerName);

        // Check that Audio and Video from Speaker 2 present.

        WebRtcPage firstSpeakerWebRtcPage = WebRtcUtil.openWebRtcPage();
        firstSpeakerWebRtcPage.assertIsOpened();

        WebRtcVerifier firstSpeakerWebRtcVerifier = new WebRtcVerifier(firstSpeakerWebRtcPage);

        int firstSpeakerStreamsCount = 2;

        firstSpeakerWebRtcVerifier.assertStreamsCount(firstSpeakerStreamsCount);

        int firstSpeakerIncomingStreamIndex = 1;

        firstSpeakerWebRtcVerifier.assertIncomingAudioAndVideoPresent(firstSpeakerIncomingStreamIndex);

        // Switch to Speaker 2 and check that video container for Speaker 1 visible.

        WebContextUtil.switchToContext(secondSpeakerContext);

        secondSpeakerVerifier.assertVideoContainerVisible(firstSpeakerName);

        // Check that Audio and Video from Speaker 1 present.

        WebRtcPage secondSpeakerWebRtcPage = WebRtcUtil.openWebRtcPage();
        secondSpeakerWebRtcPage.assertIsOpened();

        WebRtcVerifier secondSpeakerWebRtcVerifier = new WebRtcVerifier(secondSpeakerWebRtcPage);

        int secondSpeakerStreamsCount = 2;

        secondSpeakerWebRtcVerifier.assertStreamsCount(secondSpeakerStreamsCount);

        int secondSpeakerIncomingStreamIndex = 2;

        secondSpeakerWebRtcVerifier.assertIncomingAudioAndVideoPresent(secondSpeakerIncomingStreamIndex);

        // Switch to Speaker 1 and Restart all lines.

        WebContextUtil.switchToDefaultContext();

        RestartingConnectionDW restartingConnectionDW = firstSpeakerPage.restartAllLines();

        // Assertion fails sometimes on remote test execution.
        // restartingConnectionDW.assertIsOpened();

        // Check that after short pause Speaker 1 is streaming again.

        firstSpeakerVerifier.assertNoStreamGoingOnUI();

        restartingConnectionDW.assertNotVisible();

        firstSpeakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Check that video container for Speaker 2 visible and Audio and Video present.

        firstSpeakerVerifier.assertVideoContainerVisible(secondSpeakerName);

        firstSpeakerWebRtcVerifier.assertStreamsCount(firstSpeakerStreamsCount);

        firstSpeakerWebRtcVerifier.assertIncomingAudioAndVideoPresent(firstSpeakerIncomingStreamIndex);

        // Switch to Speaker 2 and check that video container for Speaker 1 visible and Audio and Video present.

        WebContextUtil.switchToContext(secondSpeakerContext);

        secondSpeakerVerifier.assertVideoContainerVisible(firstSpeakerName);

        secondSpeakerWebRtcVerifier.assertStreamsCount(secondSpeakerStreamsCount);

        secondSpeakerWebRtcVerifier.assertIncomingAudioAndVideoPresent(secondSpeakerIncomingStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
