package com.intpfy.test.core_regression.smoke_test.chats;

import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
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

public class SpeakerCanSendMessageInPrivateChatTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create random event.
    private final Event event = Event
            .createRandomBuilder()
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that Speaker can send message in Private Chat",
            description = "Speakers can exchange messages in Private Chat.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    CHATS,
                    SPEAKER
            }
    )
    @TestRailCase("778")
    @TestRailBugId("CORE-5933")
    public void test() {

        // Log in as Speaker 1.

        String firstSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage firstSpeakerPage = authorizer.logInAsSpeaker(event, firstSpeakerName);
        firstSpeakerPage.assertIsOpened();

        // Log in as Speaker 2.

        WebContextUtil.switchToNewContext();

        String secondSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage secondSpeakerPage = authorizer.logInAsSpeaker(event, secondSpeakerName);
        secondSpeakerPage.assertIsOpened();

        // Open Private chat dialog window.

        ChatDW secondSpeakerPrivateChat = secondSpeakerPage.openPrivateChat();
        secondSpeakerPrivateChat.assertIsOpened();

        // Send message to Private chat.

        String message = RandomUtil.createRandomChatMessage();
        secondSpeakerPrivateChat.sendMessage(message);

        // Check that message exists in Private chat.

        SpeakerVerifier secondSpeakerVerifier = new SpeakerVerifier(secondSpeakerPage);
        secondSpeakerVerifier.assertMessageExistsInPrivateChat(secondSpeakerPrivateChat, message);

        // Switch to Speaker 2 and check that there are unread private messages.

        WebContextUtil.switchToDefaultContext();

        SpeakerVerifier firstSpeakerVerifier = new SpeakerVerifier(firstSpeakerPage);

        firstSpeakerVerifier.assertUnreadPrivateMessagesPresent();

        // Open Private chat dialog window.

        ChatDW firstSpeakerPrivateChat = firstSpeakerPage.openPrivateChat();
        firstSpeakerPrivateChat.assertIsOpened();

        // Check that message exists in Private chat.

        firstSpeakerVerifier.assertMessageExistsInPrivateChat(firstSpeakerPrivateChat, message);

        // Close Private chat dialog window.

        firstSpeakerPrivateChat.close();
        firstSpeakerPrivateChat.assertNotVisible();

        // Check that there are no unread private messages.

        firstSpeakerVerifier.assertUnreadPrivateMessagesNotPresent();

        throwErrorIfVerificationsFailed();
    }
}
