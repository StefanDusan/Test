package com.intpfy.test.core_regression.smoke_test.chats;

import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.pages.event.ModeratorPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.moderator.ModeratorVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class ModeratorAndSpeakerCanExchangePrivateMessagesTest extends BaseWebTest implements EventTest {

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
            testName = "Check that Moderator and Speaker can exchange private messages",
            description = "Moderator and Speaker can exchange private messages via 'Remote support help' chat.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    CHATS,
                    SPEAKER,
                    MODERATOR
            }
    )
    @TestRailCase("790")
    @TestRailBugId("CORE-5933")
    public void test() {

        // Log in as Moderator.

        String moderatorName = RandomUtil.createRandomModeratorName();
        ModeratorPage moderatorPage = authorizer.logInAsModerator(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Log in as Speaker.

        WebApplicationContext speakerContext = WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Open 'Remote support help' chat dialog window.

        ChatDW speakerRemoteSupportHelpChat = speakerPage.openRemoteSupportHelpChat();
        speakerRemoteSupportHelpChat.assertIsOpened();

        // Send message to 'Remote support help' chat.

        String speakerMessage = RandomUtil.createRandomChatMessage();

        speakerRemoteSupportHelpChat.sendMessage(speakerMessage);

        // Check that message exists in 'Remote support help' chat.

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertMessageExistsInRemoteSupportHelpChat(speakerRemoteSupportHelpChat, speakerMessage);

        // Switch to Moderator and open 'Remote support help' chat dialog window.

        WebContextUtil.switchToDefaultContext();

        ChatDW moderatorRemoteSupportHelpChat = moderatorPage.openRemoteSupportHelpChat();
        moderatorRemoteSupportHelpChat.assertIsOpened();

        // Check that message from Speaker exists in 'Remote support help' chat.

        ModeratorVerifier moderatorVerifier = new ModeratorVerifier(moderatorPage);
        moderatorVerifier.assertMessageExistsInRemoteSupportHelpChat(moderatorRemoteSupportHelpChat, speakerMessage);

        // Send message to 'Remote support help' chat.

        String moderatorMessage = RandomUtil.createRandomChatMessage();

        moderatorRemoteSupportHelpChat.sendMessage(moderatorMessage);

        // Check that message exists in 'Remote support help' chat.

        moderatorVerifier.assertMessageExistsInRemoteSupportHelpChat(moderatorRemoteSupportHelpChat, moderatorMessage);

        // Switch to Speaker and check that Moderator message exists in 'Remote support help' chat.

        WebContextUtil.switchToContext(speakerContext);

        speakerVerifier.assertMessageExistsInRemoteSupportHelpChat(speakerRemoteSupportHelpChat, moderatorMessage);

        // Switch to Moderator and close 'Remote support help' chat dialog window.

        WebContextUtil.switchToDefaultContext();

        moderatorRemoteSupportHelpChat.close();
        moderatorRemoteSupportHelpChat.assertNotVisible();

        throwErrorIfVerificationsFailed();
    }
}
