package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.speaker;


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

public class RaiseLowerHandByShortcutTest extends BaseWebTest implements EventTest {

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
            testName = "Raise/lower hand by shortcut",
            description = "Speaker can raise / lower Hand by shortcut.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SHORTCUTS,
                    SPEAKER
            }
    )
    @TestRailCase("2010")
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

        // Raise hand by shortcut.

        speakerPage.raiseHandByShortcut();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);

        speakerVerifier.verifyHandRaised();
        speakerVerifier.verifySpeakerHandRaisedInParticipants(speakerName);

        // Switch to Host and check that Speaker raised Hand.

        WebContextUtil.switchToDefaultContext();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.verifySpeakerHandRaisedInParticipants(speakerName);

        // Switch to Speaker and lower Hand by shortcut.

        WebContextUtil.switchToContext(speakerContext);

        speakerPage.lowerHandByShortcut();

        speakerVerifier.verifyHandDown();
        speakerVerifier.verifyHandDownInParticipants(speakerName);

        // Switch to Host and check that Speaker lowered Hand.

        WebContextUtil.switchToDefaultContext();

        hostVerifier.verifyHandDownInParticipants(speakerName);

        throwErrorIfVerificationsFailed();
    }
}
