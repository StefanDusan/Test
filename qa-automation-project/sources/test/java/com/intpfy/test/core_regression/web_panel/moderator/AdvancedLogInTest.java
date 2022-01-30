package com.intpfy.test.core_regression.web_panel.moderator;

import com.intpfy.gui.pages.event.AdvancedModeratorPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.moderator.AdvancedModeratorVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class AdvancedLogInTest extends BaseWebTest implements EventTest {

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
            testName = "Check advanced Login",
            description = "Moderator can Log in with Advanced channel monitoring.",
            groups = {
                    ONE_USER,
                    EVENT,
                    MODERATOR
            }
    )
    @TestRailCase("1545")
    public void test() {

        // Log in as Moderator with Advanced monitoring.

        String moderatorName = RandomUtil.createRandomModeratorName();
        AdvancedModeratorPage moderatorPage = authorizer.logInAsModeratorWithAdvancedMonitoring(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Check that username displayed.

        AdvancedModeratorVerifier moderatorVerifier = new AdvancedModeratorVerifier(moderatorPage);
        moderatorVerifier.assertUsernameDisplayed(moderatorName);

        throwErrorIfVerificationsFailed();
    }
}
