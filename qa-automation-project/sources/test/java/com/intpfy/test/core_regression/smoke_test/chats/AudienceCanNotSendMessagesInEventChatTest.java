package com.intpfy.test.core_regression.smoke_test.chats;

import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.verifiers.event.audience.AudienceVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class AudienceCanNotSendMessagesInEventChatTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create random event with disabled Event chat for Audience.
    private final Event event = Event
            .createRandomBuilder()
            .withAudienceAccessToChat(false)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that Audience can't send messages in Event Chat if it is Disabled in EMI",
            description = "Audience can't send messages in Event Chat if it is Disabled in EMI.",
            groups = {
                    ONE_USER,
                    EVENT,
                    CHATS,
                    AUDIENCE
            }
    )
    @TestRailCase("786")
    public void test() {

        // Log in as Audience.

        AudiencePage audiencePage = authorizer.logInAsAudience(event);
        audiencePage.assertIsOpened();

        // Check that Event chat disabled.

        AudienceVerifier audienceVerifier = new AudienceVerifier(audiencePage);
        audienceVerifier.assertEventChatDisabled();

        throwErrorIfVerificationsFailed();
    }
}
