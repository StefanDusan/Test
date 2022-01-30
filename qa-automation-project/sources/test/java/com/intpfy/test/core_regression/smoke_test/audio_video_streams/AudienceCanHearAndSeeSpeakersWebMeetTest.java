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

public class AudienceCanHearAndSeeSpeakersWebMeetTest extends BaseWebTest implements WebRtcTest {

    private static final int SPEAKERS_COUNT = 2;

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect (WebMeet)' event with enabled Audience access to Source Video on Web.
    private final Event event = Event
            .createConnectWebMeetBuilder()
            .withAudienceAccessToFloor(true)
            .withAllowAudienceVideo(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that audience can hear and see speakers (webmeet)",
            description = "Audience can hear and see Speakers in 'Connect (WebMeet)' event.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    AUDIENCE,
                    SPEAKER
            }
    )
    @TestRailCase("1902")
    public void test() {

        // Log in as Speaker 1 with Audio and Video.

        String firstSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage firstSpeakerPage = authorizer.logInAsSpeakerWithAudioAndVideo(event, firstSpeakerName);
        firstSpeakerPage.assertIsOpened();

        SpeakerVerifier firstSpeakerVerifier = new SpeakerVerifier(firstSpeakerPage);
        firstSpeakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Speaker 2 with Audio and Video.

        WebContextUtil.switchToNewContext();

        String secondSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage secondSpeakerPage = authorizer.logInAsSpeakerWithAudioAndVideo(event, secondSpeakerName);
        secondSpeakerPage.assertIsOpened();

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
