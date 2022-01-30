package com.intpfy.test.core_regression.web_panel.moderator;

import com.intpfy.gui.pages.event.ModeratorPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.moderator.ModeratorVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class BasicLogInTest extends BaseWebTest implements EventTest {

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
            testName = "Check basic Login",
            description = "Moderator can Log in without Advanced channel monitoring.",
            groups = {
                    ONE_USER,
                    EVENT,
                    MODERATOR
            }
    )
    @TestRailCase("1782")
    public void test() {

        // Log in as Moderator.

        String moderatorName = RandomUtil.createRandomModeratorName();
        ModeratorPage moderatorPage = authorizer.logInAsModerator(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Check that username displayed.

        ModeratorVerifier moderatorVerifier = new ModeratorVerifier(moderatorPage);
        moderatorVerifier.assertUsernameDisplayed(moderatorName);

        throwErrorIfVerificationsFailed();
    }
}
