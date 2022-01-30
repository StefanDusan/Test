package com.intpfy.test.core_regression.smoke_test.classroom_controls;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class YouCanSetWhoCanSeeSpeakersListTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect Pro (Classroom)' event with Hidden Delegates status.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .withHideParticipants(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that you can set who can see speakers list",
            description = "You can set who can see 'Participants' list",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("852")
    public void test() {

        // Log in as Speaker.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Check that Speakers is not visible.

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.verifySpeakersNotVisible();

        // Log in as Host with Meeting control only.

        WebApplicationContext hostContext = WebContextUtil.switchToNewContext();

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Check that Speakers is visible.

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.assertSpeakersVisible();

        // Check that Host and Speaker present in Participants.

        hostVerifier.verifySpeakerPresentInParticipants(hostName);
        hostVerifier.verifySpeakerHasHostRoleInParticipants(hostName);

        hostVerifier.verifySpeakerPresentInParticipants(speakerName);

        // Switch to Speaker and confirm 'Host joined meeting' and 'Host left meeting' dialog windows (otherwise they interfere 'customAfterMethod' execution).

        WebContextUtil.switchToDefaultContext();
        speakerPage.confirmHostJoinedMeetingDW();

        hostContext.close();
        speakerPage.confirmHostLeftMeetingDW();

        throwErrorIfVerificationsFailed();
    }
}
