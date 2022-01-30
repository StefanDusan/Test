package com.intpfy.test.core_regression.web_panel.moderator.moderator_video;

import com.intpfy.gui.pages.event.ModeratorPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.WebRtcTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.util.WebRtcUtil;
import com.intpfy.verifiers.event.moderator.ModeratorVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfy.verifiers.media.web_rtc.WebRtcVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class ShowHideVideoTest extends BaseWebTest implements WebRtcTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect (WebMeet)' event.
    private final Event event = Event
            .createConnectWebMeetBuilder()
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check Show/hide video",
            description = "Moderator can turn Video ON / OFF for Source session.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    SPEAKER,
                    MODERATOR
            }
    )
    @TestRailCase("1552")
    public void test() {

        // Log in as Speaker with Audio and Video.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeakerWithAudioAndVideo(event, speakerName);
        speakerPage.assertIsOpened();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Moderator.

        WebContextUtil.switchToNewContext();

        String moderatorName = RandomUtil.createRandomModeratorName();
        ModeratorPage moderatorPage = authorizer.logInAsModerator(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Check that Moderator sees Speaker.

        ModeratorVerifier moderatorVerifier = new ModeratorVerifier(moderatorPage);

        moderatorVerifier.assertVideoContainerVisible(speakerName);

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 1;

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int speakerStreamIndex = 1;

        webRtcVerifier.assertIncomingVideoPresent(speakerStreamIndex);

        // Turn Video OFF for Source session.

        moderatorPage.closeBrowserTabInactiveErrorDW();

        moderatorPage.turnVideoOffForSourceSession();

        // Check that Moderator does not see Speaker.

        moderatorVerifier.assertVideoOffForSourceSession();
        moderatorVerifier.assertVideoContainerNotVisible(speakerName);

        webRtcVerifier.assertNoStreamsPresent();

        // Turn Video ON for Source session.

        moderatorPage.closeBrowserTabInactiveErrorDW();

        moderatorPage.turnVideoOnForSourceSession();

        // Check that Moderator sees Speaker.

        moderatorVerifier.assertVideoOnForSourceSession();
        moderatorVerifier.assertVideoContainerVisible(speakerName);

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        webRtcVerifier.assertIncomingVideoPresent(speakerStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
