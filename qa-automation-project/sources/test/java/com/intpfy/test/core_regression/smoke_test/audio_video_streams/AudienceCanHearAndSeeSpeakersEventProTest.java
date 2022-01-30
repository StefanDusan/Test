package com.intpfy.test.core_regression.smoke_test.audio_video_streams;

import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.WebRtcTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.util.WebRtcUtil;
import com.intpfy.verifiers.event.audience.AudienceVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfy.verifiers.media.web_rtc.WebRtcVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class AudienceCanHearAndSeeSpeakersEventProTest extends BaseWebTest implements WebRtcTest {

    private static final int SPEAKERS_COUNT = 2;

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Event Pro' event with enabled Audience access to Source Video on Web.
    private final Event event = Event
            .createEventProBuilder()
            .withAudienceAccessToFloor(true)
            .withAllowAudienceVideo(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that audience can hear and see speakers (eventpro)",
            description = "Audience can hear and see Speakers in 'Event Pro' event.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    AUDIENCE,
                    SPEAKER
            }
    )
    @TestRailCase("1903")
    public void test() {

        // Log in as Speaker 1 and connect with Audio and Video.

        String firstSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage firstSpeakerPage = authorizer.logInAsSpeaker(event, firstSpeakerName);
        firstSpeakerPage.assertIsOpened();

        firstSpeakerPage.connect();

        SpeakerVerifier firstSpeakerVerifier = new SpeakerVerifier(firstSpeakerPage);
        firstSpeakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Speaker 2 and connect with Audio and Video.

        WebContextUtil.switchToNewContext();

        String secondSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage secondSpeakerPage = authorizer.logInAsSpeaker(event, secondSpeakerName);
        secondSpeakerPage.assertIsOpened();

        secondSpeakerPage.connect();

        SpeakerVerifier secondSpeakerVerifier = new SpeakerVerifier(secondSpeakerPage);
        secondSpeakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Audience.

        WebContextUtil.switchToNewContext();

        AudiencePage audiencePage = authorizer.logInAsAudience(event);
        audiencePage.assertIsOpened();

        // Check that no Language channel selected.

        AudienceVerifier audienceVerifier = new AudienceVerifier(audiencePage);

        audienceVerifier.verifyNoLanguageChannelSelected();

        // Check that video containers for Speaker 1 and Speaker 2 not visible.

        audienceVerifier.verifyVideoContainerNotVisible(firstSpeakerName);
        audienceVerifier.verifyVideoContainerNotVisible(secondSpeakerName);

        // Check that no streams present.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        webRtcVerifier.verifyNoStreamsPresent();

        // Select Source language channel.

        audiencePage.selectLanguageChannel(Language.Source);

        // Check that video containers for Speaker 1 and Speaker 2 visible.

        audienceVerifier.assertVideoContainerVisible(firstSpeakerName);
        audienceVerifier.assertVideoContainerVisible(secondSpeakerName);

        // Check that there are 2 streams with Incoming Audio and Video.

        webRtcVerifier.assertStreamsCount(SPEAKERS_COUNT);

        int firstSpeakerStreamIndex = 1;
        int secondSpeakerStreamIndex = 2;

        webRtcVerifier.verifyIncomingAudioAndVideoPresentInStreams(firstSpeakerStreamIndex, secondSpeakerStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
