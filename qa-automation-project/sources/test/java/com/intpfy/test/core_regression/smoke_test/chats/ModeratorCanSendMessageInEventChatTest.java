package com.intpfy.test.core_regression.smoke_test.chats;

import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.ModeratorPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.moderator.ModeratorVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class ModeratorCanSendMessageInEventChatTest extends BaseWebTest implements EventTest {

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
            testName = "Check that Moderator can send messages in Event chat if it is Enabled in EMI",
            description = "Moderator can exchange messages in Event Chat with Speaker.",
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    CHATS,
                    SPEAKER,
                    MODERATOR
            }
    )
    @TestRailCase("789")
    @TestRailBugId(values = {"CORE-5933", "CORE-6485"})
    public void test() {

        // Log in as Moderator.

        ModeratorPage moderatorPage = authorizer.logInAsModerator(event, RandomUtil.createRandomModeratorName());
        moderatorPage.assertIsOpened();

        // Send message to Source session Event chat.

        String message = RandomUtil.createRandomChatMessage();
        moderatorPage.sendMessageToEventChat(message);

        // Check that message exists in Source session Event chat.

        ModeratorVerifier moderatorVerifier = new ModeratorVerifier(moderatorPage);
        moderatorVerifier.assertMessageExistsInEventChat(message);

        // Log out and Log in as Speaker.

        LoginPage loginPage = moderatorPage.logOut();
        loginPage.assertIsOpened();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Check that message exists in Event chat.

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertMessageExistsInEventChat(message);

        throwErrorIfVerificationsFailed();
    }
}
