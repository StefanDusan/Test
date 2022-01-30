package com.intpfy.test.core_regression.smoke_test.chats;

import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class HostCanUndoDeletedChatMessagesTest extends BaseWebTest implements EventTest {

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
            testName = "Classroom: Check that Host can undo deleted chat messages",
            description = "Host can restore deleted Event chat messages.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    CHATS,
                    SPEAKER
            }
    )
    @TestRailCase("788")
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

        WebApplicationContext hostContext = WebContextUtil.switchToNewContext();

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Check that message from Speaker exists in Event chat.

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.assertMessageExistsInEventChat(message);

        // Delete the message.

        hostPage.deleteEventChatMessage(message);
        hostVerifier.assertEventChatMessageHidden(message);

        // Switch to Speaker and check that message does not exist in Event chat.

        WebContextUtil.switchToDefaultContext();

        speakerPage.confirmHostJoinedMeetingDW();

        speakerVerifier.assertMessageNotExistsInEventChat(message);

        // Switch to Host and restore the message.

        WebContextUtil.switchToContext(hostContext);

        hostPage.restoreEventChatMessage(message);

        // Switch to Speaker and check that message exists in Event chat.

        WebContextUtil.switchToDefaultContext();

        speakerVerifier.assertMessageExistsInEventChat(message);

        hostContext.close(); // Context closed explicitly because otherwise 'Host left meeting' pop-up interferes 'customAfterMethod'.

        speakerPage.confirmHostLeftMeetingDW();

        throwErrorIfVerificationsFailed();
    }
}
