package com.intpfy.test.core_regression.web_panel.audience;

import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.verifiers.event.audience.AudienceVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class WorkOfChooseLanguageTest extends BaseWebTest implements EventTest {

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
            testName = "Check the work of choose language",
            description = "Audience can choose Language channel.",
            groups = {
                    ONE_USER,
                    EVENT,
                    AUDIENCE
            }
    )
    @TestRailCase("1541")
    public void test() {

        // Log in as Audience.

        AudiencePage audiencePage = authorizer.logInAsAudience(event);
        audiencePage.assertIsOpened();

        // Select language channel and check that user connected.

        audiencePage.selectLanguageChannel(language);

        AudienceVerifier audienceVerifier = new AudienceVerifier(audiencePage);
        audienceVerifier.assertConnectedToLanguageChannel(language);

        throwErrorIfVerificationsFailed();
    }
}
