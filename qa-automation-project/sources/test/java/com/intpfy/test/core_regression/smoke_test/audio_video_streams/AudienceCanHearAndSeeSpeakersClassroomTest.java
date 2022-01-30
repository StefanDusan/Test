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

public class AudienceCanHearAndSeeSpeakersClassroomTest extends BaseWebTest implements WebRtcTest {

    private static final int SPEAKERS_COUNT = 2;

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect Pro (Classroom)' event with enabled Audience access to Source Video on Web.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .withAudienceAccessToFloor(true)
            .withAllowAudienceVideo(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that audience can hear and see speakers (classroom)",
            description = "Audience can hear and see Speakers in 'Connect Pro (Classroom)' event.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    AUDIENCE,
                    SPEAKER
            }
    )
    @TestRailCase("823")
    public void test() {

        // Log in as Host 1 with Audio and Video.

        String firstHostName = RandomUtil.createRandomHostName();
        SpeakerPage firstHostPage = authorizer.logInAsHostWithAudioAndVideo(event, firstHostName);
        firstHostPage.assertIsOpened();

        SpeakerVerifier firstHostVerifier = new SpeakerVerifier(firstHostPage);
        firstHostVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Host 2 with Audio and Video.

        WebContextUtil.switchToNewContext();

        String secondHostName = RandomUtil.createRandomHostName();
        SpeakerPage secondHostPage = authorizer.logInAsHostWithAudioAndVideo(event, secondHostName);
        secondHostPage.assertIsOpened();

        SpeakerVerifier secondHostVerifier = new SpeakerVerifier(secondHostPage);
        secondHostVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Audience.

        WebContextUtil.switchToNewContext();

        AudiencePage audiencePage = authorizer.logInAsAudience(event);
        audiencePage.assertIsOpened();

        // Check that no Language channel selected.

        AudienceVerifier audienceVerifier = new AudienceVerifier(audiencePage);

        audienceVerifier.verifyNoLanguageChannelSelected();

        // Check that video containers for Host 1 and Host 2 not visible.

        audienceVerifier.verifyVideoContainerNotVisible(firstHostName);
        audienceVerifier.verifyVideoContainerNotVisible(secondHostName);

        // Check that no streams present.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        webRtcVerifier.verifyNoStreamsPresent();

        // Select Source language channel.

        audiencePage.selectLanguageChannel(Language.Source);

        // Check that video containers for Host 1 and Host 2 visible.

        audienceVerifier.assertVideoContainerVisible(firstHostName);
        audienceVerifier.assertVideoContainerVisible(secondHostName);

        // Check that there are 2 streams with Incoming Audio and Video.

        webRtcVerifier.assertStreamsCount(SPEAKERS_COUNT);

        int firstHostStreamIndex = 1;
        int secondHostStreamIndex = 2;

        webRtcVerifier.verifyIncomingAudioAndVideoPresentInStreams(firstHostStreamIndex, secondHostStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
