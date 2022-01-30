package com.intpfy.test.core_regression.smoke_test.chats;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.pages.event.FullscreenInterfaceSpeakerPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class SpeakerCanSendMessageInPrivateChatInFullscreenModeTest extends BaseWebTest implements EventTest {

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
            testName = "Check that Speaker can send message in Private Chat in Fullscreen mode",
            description = "Speakers can exchange messages in Private Chat in Full screen mode.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    CHATS,
                    SPEAKER
            }
    )
    @TestRailCase("780")
    @TestRailBugId("CORE-5933")
    public void test() {

        // Log in as Speaker 1.

        String firstSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage firstSpeakerPage = authorizer.logInAsSpeaker(event, firstSpeakerName);
        firstSpeakerPage.assertIsOpened();

        // Enable Full screen interface.

        FullscreenInterfaceSpeakerPage fullInterfaceFirstSpeakerPage = firstSpeakerPage.enableFullscreenInterface();
        fullInterfaceFirstSpeakerPage.assertIsOpened();

        // Log in as Speaker 2.

        WebContextUtil.switchToNewContext();

        String secondSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage secondSpeakerPage = authorizer.logInAsSpeaker(event, secondSpeakerName);
        secondSpeakerPage.assertIsOpened();

        // Enable Full screen interface.

        FullscreenInterfaceSpeakerPage fullInterfaceSecondSpeakerPage = secondSpeakerPage.enableFullscreenInterface();
        fullInterfaceSecondSpeakerPage.assertIsOpened();

        // Open Private chat dialog window.

        ChatDW secondSpeakerPrivateChat = fullInterfaceSecondSpeakerPage.openPrivateChat();
        secondSpeakerPrivateChat.assertIsOpened();

        // Send message to Private chat.

        String message = RandomUtil.createRandomChatMessage();
        secondSpeakerPrivateChat.sendMessage(message);

        // Check that message exists in Private chat.

        SpeakerVerifier secondSpeakerVerifier = new SpeakerVerifier(secondSpeakerPage);
        secondSpeakerVerifier.assertMessageExistsInPrivateChat(secondSpeakerPrivateChat, message);

        // Switch to Speaker 1 and open Private chat dialog window.

        WebContextUtil.switchToDefaultContext();

        ChatDW firstSpeakerPrivateChat = fullInterfaceFirstSpeakerPage.openPrivateChat();
        firstSpeakerPrivateChat.assertIsOpened();

        // Check that message from Speaker 2 exists in Private chat.

        SpeakerVerifier firstSpeakerVerifier = new SpeakerVerifier(firstSpeakerPage);
        firstSpeakerVerifier.assertMessageExistsInPrivateChat(firstSpeakerPrivateChat, message);

        throwErrorIfVerificationsFailed();
    }
}
