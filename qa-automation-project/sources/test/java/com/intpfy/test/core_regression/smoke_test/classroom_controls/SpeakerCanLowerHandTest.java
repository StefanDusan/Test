package com.intpfy.test.core_regression.smoke_test.classroom_controls;


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

public class SpeakerCanLowerHandTest extends BaseWebTest implements EventTest {

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
            testName = "Check that speaker can lower hand",
            description = "Speaker can lower Hand.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("818")
    public void test() {

        // Log in as Host with Meeting control only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Log in as Speaker.

        WebApplicationContext speakerContext = WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Raise Hand.

        speakerPage.raiseHand();

        // Check that Speaker Hand raised in 'Participants' list.

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);

        speakerVerifier.assertSpeakerHandRaisedInParticipants(speakerName);

        // Switch to Host and check that Speaker Hand raised in 'Participants' list.

        WebContextUtil.switchToDefaultContext();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.assertSpeakerHandRaisedInParticipants(speakerName);

        // Switch to Speaker and lower Hand.

        WebContextUtil.switchToContext(speakerContext);

        speakerPage.lowerHand();

        // Check that Speaker Hand down in 'Participants' list.

        speakerVerifier.verifyHandDownInParticipants(speakerName);

        // Switch to Host and check that Speaker Hand down in 'Participants' list.

        WebContextUtil.switchToDefaultContext();

        hostVerifier.verifyHandDownInParticipants(speakerName);

        throwErrorIfVerificationsFailed();
    }
}
