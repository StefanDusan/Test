package com.intpfy.test.core_regression.smoke_test.chats;

import com.intpfy.gui.dialogs.common.MiniEventChatDW;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.FullscreenInterfaceSpeakerPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class SpeakerCanSendMessageInEventChatInFullscreenModeTest extends BaseWebTest implements EventTest {

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
            testName = "Check that Speaker can send message in Event Chat in Fullscreen mode",
            description = "Speakers can exchange messages in Event Chat in Fullscreen mode.",
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    CHATS,
                    SPEAKER
            }
    )
    @TestRailCase("779")
    @TestRailBugId("CORE-5933")
    public void test() {

        // Log in as Speaker 1.

        String firstSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage firstSpeakerPage = authorizer.logInAsSpeaker(event, firstSpeakerName);
        firstSpeakerPage.assertIsOpened();

        // Enable Full screen interface.

        FullscreenInterfaceSpeakerPage fullInterfaceFirstSpeakerPage = firstSpeakerPage.enableFullscreenInterface();
        fullInterfaceFirstSpeakerPage.assertIsOpened();

        // Open Event chat dialog window.

        MiniEventChatDW firstSpeakerEventChat = fullInterfaceFirstSpeakerPage.openEventChat();
        firstSpeakerEventChat.assertIsOpened();

        // Send message to Event chat.

        String message = RandomUtil.createRandomChatMessage();
        firstSpeakerEventChat.sendMessage(message);

        // Check that message exists in Event chat.

        SpeakerVerifier firstSpeakerVerifier = new SpeakerVerifier(firstSpeakerPage);
        firstSpeakerVerifier.assertMessageExistsInEventChat(firstSpeakerEventChat, message);

        // Log out and Log in as Speaker 2.

        LoginPage loginPage = fullInterfaceFirstSpeakerPage.logOut();
        loginPage.assertIsOpened();

        String secondSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage secondSpeakerPage = authorizer.logInAsSpeaker(event, secondSpeakerName);
        secondSpeakerPage.assertIsOpened();

        // Enable Full screen interface.

        FullscreenInterfaceSpeakerPage fullInterfaceSecondSpeakerPage = secondSpeakerPage.enableFullscreenInterface();
        fullInterfaceSecondSpeakerPage.assertIsOpened();

        // Open Event chat dialog window.

        MiniEventChatDW secondSpeakerEventChat = fullInterfaceSecondSpeakerPage.openEventChat();
        secondSpeakerEventChat.assertIsOpened();

        // Check that message from Speaker 1 exists in Event chat.

        SpeakerVerifier secondSpeakerVerifier = new SpeakerVerifier(secondSpeakerPage);
        secondSpeakerVerifier.assertMessageExistsInEventChat(secondSpeakerEventChat, message);

        throwErrorIfVerificationsFailed();
    }
}
