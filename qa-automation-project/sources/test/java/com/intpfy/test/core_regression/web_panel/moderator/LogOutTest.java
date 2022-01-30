package com.intpfy.test.core_regression.web_panel.moderator;

import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.ModeratorPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class LogOutTest extends BaseWebTest implements EventTest {

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
            testName = "Check logout",
            description = "Moderator can Log out.",
            groups = {
                    ONE_USER,
                    EVENT,
                    MODERATOR
            }
    )
    @TestRailCase("1546")
    public void test() {

        // Log in as Moderator.

        String moderatorName = RandomUtil.createRandomModeratorName();
        ModeratorPage moderatorPage = authorizer.logInAsModerator(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Log out.

        LoginPage loginPage = moderatorPage.logOut();
        loginPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
