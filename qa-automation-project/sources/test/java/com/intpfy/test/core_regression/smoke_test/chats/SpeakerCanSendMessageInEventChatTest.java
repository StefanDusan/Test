package com.intpfy.test.core_regression.smoke_test.chats;

import com.intpfy.gui.pages.LoginPage;
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

public class SpeakerCanSendMessageInEventChatTest extends BaseWebTest implements EventTest {

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
            testName = "Check that Speaker can send message in Event Chat",
            description = "Speakers can exchange messages in Event Chat.",
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    CHATS,
                    SPEAKER
            }
    )
    @TestRailCase("777")
    @TestRailBugId("CORE-5933")
    public void test() {

        // Log in as Speaker 1.

        String firstSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage firstSpeakerPage = authorizer.logInAsSpeaker(event, firstSpeakerName);
        firstSpeakerPage.assertIsOpened();

        // Send message to Event chat.

        String message = RandomUtil.createRandomChatMessage();
        firstSpeakerPage.sendMessageToEventChat(message);

        // Check that message exists in Event chat.

        SpeakerVerifier firstSpeakerVerifier = new SpeakerVerifier(firstSpeakerPage);
        firstSpeakerVerifier.assertMessageExistsInEventChat(message);

        // Log out and Log in as Speaker 2.

        LoginPage loginPage = firstSpeakerPage.logOut();
        loginPage.assertIsOpened();

        String secondSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage secondSpeakerPage = authorizer.logInAsSpeaker(event, secondSpeakerName);
        secondSpeakerPage.assertIsOpened();

        // Check that message from Speaker 1 exists in Event chat.

        SpeakerVerifier secondSpeakerVerifier = new SpeakerVerifier(firstSpeakerPage);
        secondSpeakerVerifier.assertMessageExistsInEventChat(message);

        throwErrorIfVerificationsFailed();
    }
}
