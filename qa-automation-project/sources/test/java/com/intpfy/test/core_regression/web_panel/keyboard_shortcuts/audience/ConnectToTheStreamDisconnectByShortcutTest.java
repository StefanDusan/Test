package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.audience;

import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.verifiers.event.audience.AudienceVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class ConnectToTheStreamDisconnectByShortcutTest extends BaseWebTest implements EventTest {

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
            testName = "Connect to the stream / Disconnect by shortcut",
            description = "Audience can connect / disconnect to stream by shortcut.",
            groups = {
                    ONE_USER,
                    EVENT,
                    SHORTCUTS,
                    AUDIENCE
            }
    )
    @TestRailCase("2040")
    @TestRailBugId("CORE-5983")
    public void test() {

        // Log in as Audience.

        AudiencePage audiencePage = authorizer.logInAsAudience(event);
        audiencePage.assertIsOpened();

        // Select language channel (connect).

        audiencePage.selectLanguageChannel(language);

        AudienceVerifier audienceVerifier = new AudienceVerifier(audiencePage);

        audienceVerifier.assertConnected();

        // Disconnect by shortcut.

        audiencePage.disconnectByShortcut();
        audienceVerifier.assertDisconnected();

        // Connect by shortcut.

        audiencePage.connectByShortcut();
        audienceVerifier.assertConnected();

        throwErrorIfVerificationsFailed();
    }
}
