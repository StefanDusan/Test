package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class SpeakerInTheAllParticipantsListTest extends BaseWebTest implements EventTest {

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
            testName = "Check the Speaker in the All participants list",
            description = "Speaker present in 'Participants' list.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("361")
    public void test() {

        // Log in as Host with Meeting control only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Log in as Speaker.

        WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Check that Host and Speaker present in 'Participants' list.

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);

        speakerVerifier.verifySpeakerPresentInParticipants(hostName);
        speakerVerifier.verifySpeakerHasHostRoleInParticipants(hostName);

        speakerVerifier.verifySpeakerPresentInParticipants(speakerName);

        // Switch to Host and check that Host and Speaker present in 'Participants' list.

        WebContextUtil.switchToDefaultContext();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.verifySpeakerPresentInParticipants(hostName);
        hostVerifier.verifySpeakerHasHostRoleInParticipants(hostName);

        hostVerifier.verifySpeakerPresentInParticipants(speakerName);

        throwErrorIfVerificationsFailed();
    }
}
