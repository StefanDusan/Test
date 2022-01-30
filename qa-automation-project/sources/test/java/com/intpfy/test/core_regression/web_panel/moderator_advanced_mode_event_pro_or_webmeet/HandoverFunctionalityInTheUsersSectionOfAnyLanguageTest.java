package com.intpfy.test.core_regression.web_panel.moderator_advanced_mode_event_pro_or_webmeet;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.dialogs.interpreter.HandoverDW;
import com.intpfy.gui.pages.event.AdvancedModeratorPage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.event.moderator.AdvancedModeratorVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class HandoverFunctionalityInTheUsersSectionOfAnyLanguageTest extends BaseWebTest implements EventTest {

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
            testName = "Check the handover functionality in the 'Users' section of any language",
            description = "Moderator can see that Interpreters use handover.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    INTERPRETER,
                    MODERATOR
            }
    )
    @TestRailCase("1555")
    @TestRailBugId("CORE-6941")
    public void test() {

        // Log in as Moderator with Advanced monitoring.

        String moderatorName = RandomUtil.createRandomModeratorName();
        AdvancedModeratorPage moderatorPage = authorizer.logInAsModeratorWithAdvancedMonitoring(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Log in as Interpreter 1 with Outgoing language.

        WebApplicationContext firstInterpreterContext = WebContextUtil.switchToNewContext();

        String firstInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage firstInterpreterPage = authorizer.logInAsInterpreter(event, firstInterpreterName, language);
        firstInterpreterPage.assertIsOpened();

        // Log in as Interpreter 2 with Outgoing language.

        WebApplicationContext secondInterpreterContext = WebContextUtil.switchToNewContext();

        String secondInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage secondInterpreterPage = authorizer.logInAsInterpreter(event, secondInterpreterName, language);
        secondInterpreterPage.assertIsOpened();

        // Switch to Interpreter 1 and Unmute.

        WebContextUtil.switchToContext(firstInterpreterContext);

        firstInterpreterPage.unmute();

        InterpreterVerifier firstInterpreterVerifier = new InterpreterVerifier(firstInterpreterPage);
        firstInterpreterVerifier.assertUnmuted();

        // Switch to Moderator and check that Interpreter 1 unmuted and can stream in Language session.

        WebContextUtil.switchToDefaultContext();

        AdvancedModeratorVerifier moderatorVerifier = new AdvancedModeratorVerifier(moderatorPage);

        moderatorVerifier.assertUserUnmutedInLanguageSession(firstInterpreterName, language);
        moderatorVerifier.assertUserCanStreamInLanguageSession(firstInterpreterName, language);

        // Switch to Interpreter 1 and request Handover NOW.

        WebContextUtil.switchToContext(firstInterpreterContext);

        firstInterpreterPage.requestHandoverNow();

        // Switch to Moderator and check that streaming is not Multiple in Language session.

        WebContextUtil.switchToDefaultContext();

        moderatorVerifier.assertStreamingIsNotMultipleInLanguageSession(language);

        // Switch to Interpreter 2 and Unmute from 'Handover' dialog window.

        WebContextUtil.switchToContext(secondInterpreterContext);

        HandoverDW handoverDW = new HandoverDW(secondInterpreterPage);
        handoverDW.assertIsOpened();

        handoverDW.unmute();
        handoverDW.assertNotVisible();

        InterpreterVerifier secondInterpreterVerifier = new InterpreterVerifier(secondInterpreterPage);
        secondInterpreterVerifier.assertUnmuted();

        // Switch to Moderator and check that Interpreter 2 Unmuted and can stream in Language session.

        WebContextUtil.switchToDefaultContext();

        moderatorVerifier.assertUserUnmutedInLanguageSession(secondInterpreterName, language);
        moderatorVerifier.assertUserCanStreamInLanguageSession(secondInterpreterName, language);

        // Check that streaming is Multiple in Language session.

        moderatorVerifier.assertStreamingIsMultipleInLanguageSession(language);

        // Switch to Interpreter 1 and Mute.

        WebContextUtil.switchToContext(firstInterpreterContext);

        firstInterpreterPage.mute();
        firstInterpreterVerifier.assertMuted();

        // Switch to Moderator and check that Interpreter 1 Muted and can stream in Language session.

        WebContextUtil.switchToDefaultContext();

        moderatorVerifier.assertUserMutedInLanguageSession(firstInterpreterName, language);
        moderatorVerifier.assertUserCanStreamInLanguageSession(firstInterpreterName, language);

        // Check that streaming is not Multiple in Language session.

        moderatorVerifier.assertStreamingIsNotMultipleInLanguageSession(language);

        // Check that Interpreter 2 placed 1-st in 'Users' list.

        moderatorVerifier.assertUserOrdinalPositionInLanguageSessionUsersList(secondInterpreterName, language, 1);
        moderatorVerifier.assertUserOrdinalPositionInLanguageSessionUsersList(firstInterpreterName, language, 2);

        throwErrorIfVerificationsFailed();
    }
}
