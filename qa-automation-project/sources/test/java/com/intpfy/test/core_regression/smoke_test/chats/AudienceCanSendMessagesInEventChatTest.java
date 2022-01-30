package com.intpfy.test.core_regression.smoke_test.chats;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.audience.AudienceVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class AudienceCanSendMessagesInEventChatTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create random event with enabled Event chat for Audience.
    private final Event event = Event
            .createRandomBuilder()
            .withAudienceAccessToChat(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that Audience can send messages in Event Chat if it is Enabled in EMI",
            description = "Audience can exchange messages in Event Chat.",
            groups = {
                    ONE_USER,
                    EVENT,
                    CHATS,
                    AUDIENCE
            }
    )
    @TestRailCase("785")
    @TestRailBugId("CORE-5933")
    public void test() {

        // Log in as Audience 1.

        String firstAudienceName = RandomUtil.createRandomAudienceName();
        AudiencePage firstAudiencePage = authorizer.logInAsAudienceWithUsername(event, firstAudienceName);
        firstAudiencePage.assertIsOpened();

        // Select Source language channel.

        firstAudiencePage.selectSourceChannel();

        AudienceVerifier firstAudienceVerifier = new AudienceVerifier(firstAudiencePage);

        firstAudienceVerifier.assertConnectedToSourceChannel();

        // Send message to Event chat.

        String message = RandomUtil.createRandomChatMessage();
        firstAudiencePage.sendMessageToEventChat(message);

        // Check that message exists in Event chat.

        firstAudienceVerifier.assertMessageExistsInEventChat(message);

        // Log out and Log in as Audience 2.

        LoginPage loginPage = firstAudiencePage.logOut();
        loginPage.assertIsOpened();

        String secondAudienceName = RandomUtil.createRandomAudienceName();
        AudiencePage secondAudiencePage = authorizer.logInAsAudienceWithUsername(event, secondAudienceName);
        secondAudiencePage.assertIsOpened();

        // Select Source language channel.

        secondAudiencePage.selectSourceChannel();

        AudienceVerifier secondAudienceVerifier = new AudienceVerifier(secondAudiencePage);

        secondAudienceVerifier.assertConnectedToSourceChannel();

        // Check that message exists in Event chat.

        secondAudienceVerifier.assertMessageExistsInEventChat(message);

        // Check that username color for message is Green.

        secondAudienceVerifier.assertUsernameColorGreenForMessage(message);

        throwErrorIfVerificationsFailed();
    }
}
