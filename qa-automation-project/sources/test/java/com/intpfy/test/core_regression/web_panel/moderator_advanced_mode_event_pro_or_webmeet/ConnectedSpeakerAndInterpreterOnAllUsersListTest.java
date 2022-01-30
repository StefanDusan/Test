package com.intpfy.test.core_regression.web_panel.moderator_advanced_mode_event_pro_or_webmeet;

import com.intpfy.gui.pages.event.AdvancedModeratorPage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.moderator.AdvancedModeratorVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class ConnectedSpeakerAndInterpreterOnAllUsersListTest extends BaseWebTest implements EventTest {

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
            testName = "Check connected Speaker / Interpreter on 'All users' list",
            description = "Connected Speaker / Interpreter displayed correctly in 'All users' list.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    INTERPRETER,
                    SPEAKER,
                    MODERATOR,
            }
    )
    @TestRailCase("1547")
    public void test() {

        // Log in as Interpreter with Outgoing language.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        // Log in as Speaker.

        WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Log in as Moderator with Advanced monitoring.

        WebContextUtil.switchToNewContext();

        String moderatorName = RandomUtil.createRandomModeratorName();
        AdvancedModeratorPage moderatorPage = authorizer.logInAsModeratorWithAdvancedMonitoring(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Switch to Moderator and check that Interpreter and Speaker displayed correctly in Source session 'All users' list.

        AdvancedModeratorVerifier moderatorVerifier = new AdvancedModeratorVerifier(moderatorPage);

        moderatorVerifier.verifyUserPresentInSourceSession(interpreterName);
        moderatorVerifier.verifyUserPresentInSourceSession(speakerName);

        moderatorVerifier.verifyUserCanListenInSourceSession(interpreterName);
        moderatorVerifier.verifyUserCanStreamInSourceSession(speakerName);

        Language sourceLanguage = Language.Source;
        moderatorVerifier.verifyUserIncomingLanguageInSourceSession(interpreterName, sourceLanguage);
        moderatorVerifier.verifyUserOutgoingLanguageInSourceSession(interpreterName, language);

        moderatorVerifier.verifyUserIncomingLanguageInSourceSession(speakerName, Language.Empty);
        moderatorVerifier.verifyUserOutgoingLanguageInSourceSession(speakerName, sourceLanguage);

        throwErrorIfVerificationsFailed();
    }
}
