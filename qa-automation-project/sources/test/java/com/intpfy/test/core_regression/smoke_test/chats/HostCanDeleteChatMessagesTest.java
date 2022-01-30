package com.intpfy.test.core_regression.smoke_test.chats;

import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.run.IWebApplicationContext;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class HostCanDeleteChatMessagesTest extends BaseWebTest implements EventTest {

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
            testName = "Classroom: Check that Host can delete chat messages",
            description = "Host can delete Event chat messages forever.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    CHATS,
                    SPEAKER
            }
    )
    @TestRailCase("787")
    @TestRailBugId("CORE-5933")
    public void test() {

        // Log in as Speaker.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Send message to Event chat.

        String message = RandomUtil.createRandomChatMessage();
        speakerPage.sendMessageToEventChat(message);

        // Check that message exists in Event chat.

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);

        speakerVerifier.assertMessageExistsInEventChat(message);

        // Log in as Host with Meeting control only.

        IWebApplicationContext hostContext = WebContextUtil.switchToNewContext();

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Check that message from Speaker exists in Event chat.

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.assertMessageExistsInEventChat(message);

        // Delete the message forever.

        hostPage.deleteEventChatMessage(message);
        hostVerifier.assertEventChatMessageHidden(message);

        hostPage.deleteEventChatMessageForever(message);
        hostVerifier.assertMessageNotExistsInEventChat(message);

        // Switch to Speaker and check that message does not exist in Event chat.

        WebContextUtil.switchToDefaultContext();

        speakerPage.confirmHostJoinedMeetingDW();

        speakerVerifier.assertMessageNotExistsInEventChat(message);

        hostContext.close(); // Context closed explicitly because otherwise 'Host left meeting' pop-up interferes 'customAfterMethod' execution.

        speakerPage.confirmHostLeftMeetingDW();

        throwErrorIfVerificationsFailed();
    }
}
