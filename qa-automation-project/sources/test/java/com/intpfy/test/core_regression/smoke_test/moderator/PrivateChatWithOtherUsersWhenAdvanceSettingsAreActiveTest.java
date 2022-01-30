package com.intpfy.test.core_regression.smoke_test.moderator;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.dialogs.moderator.UsersSessionDW;
import com.intpfy.gui.pages.event.AdvancedModeratorPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.moderator.AdvancedModeratorVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class PrivateChatWithOtherUsersWhenAdvanceSettingsAreActiveTest extends BaseWebTest implements EventTest {

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
            testName = "Check private chat with other users when advance settings are active",
            description = "Moderator with Advanced channel monitoring can send messages in Private chat.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    CHATS,
                    SPEAKER,
                    MODERATOR
            }
    )
    @TestRailCase("1653")
    @TestRailBugId("CORE-5933")
    public void test() {

        // Log in as Speaker.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Log in as Moderator with Advanced monitoring.

        WebContextUtil.switchToNewContext();

        String moderatorName = RandomUtil.createRandomModeratorName();
        AdvancedModeratorPage moderatorPage = authorizer.logInAsModeratorWithAdvancedMonitoring(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Open 'Users session' dialog window for Source session.

        UsersSessionDW usersSessionDW = moderatorPage.openUsersSessionDWForSourceSession();
        usersSessionDW.assertIsOpened();

        // Open 'Remote support help' chat for Speaker.

        ChatDW moderatorRemoteSupportHelpChat = usersSessionDW.openRemoteSupportHelpChatForUser(speakerName);
        moderatorRemoteSupportHelpChat.assertIsOpened();

        // Send message to chat.

        String message = RandomUtil.createRandomChatMessage();
        moderatorRemoteSupportHelpChat.sendMessage(message);

        // Check that message exists in chat.

        AdvancedModeratorVerifier moderatorVerifier = new AdvancedModeratorVerifier(moderatorPage);
        moderatorVerifier.assertMessageExistsInRemoteSupportHelpChat(moderatorRemoteSupportHelpChat, message);

        // Switch to Speaker and open 'Remote support help' chat for Speaker.

        WebContextUtil.switchToDefaultContext();

        ChatDW speakerRemoteSupportHelpChat = speakerPage.openRemoteSupportHelpChat();
        speakerRemoteSupportHelpChat.assertIsOpened();

        // Check that message from Moderator exists in chat.

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertMessageExistsInRemoteSupportHelpChat(speakerRemoteSupportHelpChat, message);

        throwErrorIfVerificationsFailed();
    }
}
