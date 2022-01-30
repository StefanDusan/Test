package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;

import com.intpfy.gui.dialogs.common.StreamingAllowedDW;
import com.intpfy.gui.dialogs.speaker.DisableStreamingDW;
import com.intpfy.gui.dialogs.speaker.HostHasStoppedYourStreamingDW;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class WorkOfAllowStreamingButtonOnTheAllSpeakersListTest extends BaseWebTest implements EventTest {

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
            testName = "Check the work of allow streaming button on the all speakers list (start/stop streaming)",
            description = "Host can allow / disallow Speaker to stream.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("362")
    public void test() {

        // Log in as Speaker.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Log in as Host with Meeting control only.

        WebApplicationContext hostContext = WebContextUtil.switchToNewContext();

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Allow Speaker to Stream.

        hostPage.allowSpeakerToStreamInParticipants(speakerName);

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.verifySpeakerAskedToStreamInParticipants(speakerName);

        // Switch to Speaker and accept streaming with Audio and Video.

        WebContextUtil.switchToDefaultContext();

        StreamingAllowedDW streamingAllowedDW = new StreamingAllowedDW(speakerPage);
        streamingAllowedDW.assertIsOpened();

        streamingAllowedDW.selectAudioAndVideo();
        streamingAllowedDW.assertNotVisible();

        speakerPage.confirmHostJoinedMeetingDW();

        // Check that stream with Audio and Video going on UI.

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);

        speakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Switch to Host and check that video container for Speaker visible and Speaker marked as 'Streaming' in 'Participants' list.

        WebContextUtil.switchToContext(hostContext);

        hostVerifier.verifyVideoContainerVisible(speakerName);
        hostVerifier.verifySpeakerStreamingInParticipants(speakerName);

        // Disallow Speaker to stream.

        DisableStreamingDW disableStreamingDW = hostPage.disallowSpeakerToStreamInParticipants(speakerName);
        disableStreamingDW.assertIsOpened();

        disableStreamingDW.confirm();
        disableStreamingDW.assertNotVisible();

        // Check that video container for Speaker not visible and Speaker not marked as 'Streaming' in 'Participants' list.

        hostVerifier.verifyVideoContainerNotVisible(speakerName);
        hostVerifier.verifySpeakerNotStreamingInParticipants(speakerName);

        // Switch to Speaker and check that no stream going on UI.

        WebContextUtil.switchToDefaultContext();

        HostHasStoppedYourStreamingDW hostHasStoppedYourStreamingDW = new HostHasStoppedYourStreamingDW(speakerPage);
        hostHasStoppedYourStreamingDW.assertIsOpened();

        hostHasStoppedYourStreamingDW.confirm();
        hostHasStoppedYourStreamingDW.assertNotVisible();

        speakerVerifier.assertNoStreamGoingOnUI();

        throwErrorIfVerificationsFailed();
    }
}
