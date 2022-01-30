package com.intpfy.test.core_regression.web_panel.moderator_advanced_mode_event_pro_or_webmeet;


import com.intpfy.gui.pages.event.ModeratorPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.moderator.ModeratorVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class RalButtonTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Language language = Language.getRandomLanguage();

    // Create random event with 1 Language.
    private final Event event = Event
            .createRandomBuilder()
            .withLanguage(language)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check RAL button",
            description = "'Restart all lines' button works correctly.",
            groups = {
                    ONE_USER,
                    EVENT,
                    MODERATOR
            }
    )
    @TestRailCase("1720")
    public void test() {

        // Log in as Moderator.

        String moderatorName = RandomUtil.createRandomModeratorName();
        ModeratorPage moderatorPage = authorizer.logInAsModerator(event, moderatorName);
        moderatorPage.assertIsOpened();

        // RAL and check that 'RAL' button inactive for some time.

        moderatorPage.restartAllLines();

        ModeratorVerifier moderatorVerifier = new ModeratorVerifier(moderatorPage);

        moderatorVerifier.verifyRalButtonInactive();
        moderatorVerifier.verifyRalButtonActive();

        // RAL and check that Language session not visible for some time.

        moderatorPage.restartAllLines();

        moderatorVerifier.verifyLanguageSessionNotVisible(language);
        moderatorVerifier.verifyLanguageSessionVisible(language);

        // RAL and check that Spinner displayed for some time.

        moderatorPage.restartAllLines();

        moderatorVerifier.verifySpinnerDisplayed();
        moderatorVerifier.verifySpinnerNotDisplayed();

        throwErrorIfVerificationsFailed();
    }
}
