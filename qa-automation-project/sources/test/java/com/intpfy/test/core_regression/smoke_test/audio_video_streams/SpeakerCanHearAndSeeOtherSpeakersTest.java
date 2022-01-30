package com.intpfy.test.core_regression.smoke_test.audio_video_streams;

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

public class SpeakerCanHearAndSeeOtherSpeakersTest extends BaseWebTest implements WebRtcTest {

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
            testName = "Check that speaker can hear and see other speakers",
            description = "Speaker can hear and see other Speakers.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("819")
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

        // Log in as Speaker 3.

        WebApplicationContext thirdSpeakerContext = WebContextUtil.switchToNewContext();

        String thirdSpeakerName = RandomUtil.createRandomHostName();
        SpeakerPage thirdSpeakerPage = authorizer.logInAsSpeaker(event, thirdSpeakerName);
        thirdSpeakerPage.assertIsOpened();

        // Switch to Speaker 1 and Connect with Audio and Video.

        WebContextUtil.switchToDefaultContext();

        firstSpeakerPage.connect();

        SpeakerVerifier firstSpeakerVerifier = new SpeakerVerifier(firstSpeakerPage);

        firstSpeakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Switch to Speaker 2 and check that video container for Speaker 1 visible.

        WebContextUtil.switchToContext(secondSpeakerContext);

        SpeakerVerifier secondSpeakerVerifier = new SpeakerVerifier(secondSpeakerPage);

        secondSpeakerVerifier.assertVideoContainerVisible(firstSpeakerName);

        // Check that Audio and Video from Speaker 1 present.

        WebRtcPage secondSpeakerWebRtcPage = WebRtcUtil.openWebRtcPage();
        secondSpeakerWebRtcPage.assertIsOpened();

        WebRtcVerifier secondSpeakerWebRtcVerifier = new WebRtcVerifier(secondSpeakerWebRtcPage);

        int secondSpeakerExpectedStreamsCount = 1;

        secondSpeakerWebRtcVerifier.assertStreamsCount(secondSpeakerExpectedStreamsCount);

        int secondSpeakerIncomingStreamFromFirstSpeakerIndex = 1;
        secondSpeakerWebRtcVerifier.verifyIncomingAudioAndVideoPresent(secondSpeakerIncomingStreamFromFirstSpeakerIndex);

        // Switch to Speaker 3 and check that video container for Speaker 1 visible.

        WebContextUtil.switchToContext(thirdSpeakerContext);

        SpeakerVerifier thirdSpeakerVerifier = new SpeakerVerifier(thirdSpeakerPage);

        thirdSpeakerVerifier.assertVideoContainerVisible(firstSpeakerName);

        // Check that Audio and Video from Speaker present.

        WebRtcPage thirdSpeakerWebRtcPage = WebRtcUtil.openWebRtcPage();
        thirdSpeakerWebRtcPage.assertIsOpened();

        WebRtcVerifier thirdSpeakerWebRtcVerifier = new WebRtcVerifier(thirdSpeakerWebRtcPage);

        int thirdSpeakerExpectedStreamsCount = 1;
        thirdSpeakerWebRtcVerifier.assertStreamsCount(thirdSpeakerExpectedStreamsCount);

        int thirdSpeakerIncomingStreamFromFirstSpeakerIndex = 1;
        thirdSpeakerWebRtcVerifier.verifyIncomingAudioAndVideoPresent(thirdSpeakerIncomingStreamFromFirstSpeakerIndex);

        // Switch to Speaker 2 and Connect with Audio and Video.

        WebContextUtil.switchToContext(secondSpeakerContext);

        secondSpeakerPage.connect();
        secondSpeakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Switch to Speaker 1 and check that video container for Speaker 2 visible.

        WebContextUtil.switchToDefaultContext();

        firstSpeakerVerifier.assertVideoContainerVisible(secondSpeakerName);

        // Check that Audio and Video from Speaker 2 present.

        WebRtcPage firstSpeakerWebRtcPage = WebRtcUtil.openWebRtcPage();
        firstSpeakerWebRtcPage.assertIsOpened();

        WebRtcVerifier firstSpeakerWebRtcVerifier = new WebRtcVerifier(firstSpeakerWebRtcPage);

        int firstSpeakerExpectedStreamsCount = 2;

        firstSpeakerWebRtcVerifier.assertStreamsCount(firstSpeakerExpectedStreamsCount);

        int firstSpeakerIncomingStreamFromSecondSpeakerIndex = 2;
        firstSpeakerWebRtcVerifier.verifyIncomingAudioAndVideoPresent(firstSpeakerIncomingStreamFromSecondSpeakerIndex);

        // Switch to Speaker 3 and check that video container for Speaker 2 visible.

        WebContextUtil.switchToContext(thirdSpeakerContext);

        thirdSpeakerVerifier.assertVideoContainerVisible(secondSpeakerName);

        thirdSpeakerExpectedStreamsCount = 2;
        thirdSpeakerWebRtcVerifier.assertStreamsCount(thirdSpeakerExpectedStreamsCount);

        int thirdSpeakerIncomingStreamIndexFromSecondSpeaker = 2;
        thirdSpeakerWebRtcVerifier.verifyIncomingAudioAndVideoPresent(thirdSpeakerIncomingStreamIndexFromSecondSpeaker);

        // Connect with Audio and Video.

        thirdSpeakerPage.connect();
        thirdSpeakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Switch to Speaker 1 and check that video container for Speaker 3 visible.

        WebContextUtil.switchToDefaultContext();

        firstSpeakerVerifier.assertVideoContainerVisible(thirdSpeakerName);

        // Check that Audio and Video from Speaker 3 present.

        firstSpeakerExpectedStreamsCount = 3;
        firstSpeakerWebRtcVerifier.assertStreamsCount(firstSpeakerExpectedStreamsCount);

        int firstSpeakerIncomingStreamFromThirdSpeakerIndex = 3;
        firstSpeakerWebRtcVerifier.verifyIncomingAudioAndVideoPresent(firstSpeakerIncomingStreamFromThirdSpeakerIndex);

        // Switch to Speaker 2 and check that video container for Speaker 3 visible.

        WebContextUtil.switchToContext(secondSpeakerContext);

        secondSpeakerVerifier.assertVideoContainerVisible(thirdSpeakerName);

        secondSpeakerExpectedStreamsCount = 3;
        secondSpeakerWebRtcVerifier.assertStreamsCount(secondSpeakerExpectedStreamsCount);

        int secondSpeakerIncomingStreamFromThirdSpeakerIndex = 3;
        secondSpeakerWebRtcVerifier.verifyIncomingAudioAndVideoPresent(secondSpeakerIncomingStreamFromThirdSpeakerIndex);

        throwErrorIfVerificationsFailed();
    }
}
