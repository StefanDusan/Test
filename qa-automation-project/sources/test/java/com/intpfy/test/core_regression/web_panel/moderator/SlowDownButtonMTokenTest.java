package com.intpfy.test.core_regression.web_panel.moderator;


import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.ModeratorPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.moderator.ModeratorVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class SlowDownButtonMTokenTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Language language = Language.getRandomLanguage();

    // Create 'Connect Pro (Classroom)' event with 1 Language.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .withLanguage(language)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "The button 'Slow-down' works correctly (M-token)",
            description = "Moderator sees 'Slow-down' dialog window if Interpreter requests Slow down.",
            groups = {
                    TWO_USERS,
                    EVENT,
                    INTERPRETER,
                    MODERATOR
            }
    )
    @TestRailCase("1636")
    public void test() {

        // Log in as Moderator.

        String moderatorName = RandomUtil.createRandomModeratorName();
        ModeratorPage moderatorPage = authorizer.logInAsModerator(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Log in as Interpreter with Outgoing language.

        WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        // Request Slow down.

        interpreterPage.requestSlowDown();

        // Switch to Moderator and check that 'Slow down' dialog window with expected message, language and initials displayed.

        WebContextUtil.switchToDefaultContext();

        ModeratorVerifier moderatorVerifier = new ModeratorVerifier(moderatorPage);

        moderatorVerifier.assertSlowDownDWDisplayed();
        moderatorVerifier.verifySlowDownDWContent("Please slow down", language, "I.");

        // Check that 'Slow down' dialog window not displayed after some time.

        moderatorVerifier.assertSlowDownDWNotDisplayed();

        throwErrorIfVerificationsFailed();
    }
}
