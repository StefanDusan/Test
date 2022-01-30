package com.intpfy.test.core_regression.web_panel.interpreter;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.dialogs.interpreter.HandoverDW;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class WorkOfNowHandoverFunctionalityTest extends BaseWebTest implements EventTest {

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
            testName = "Check the work of NOW handover functionality",
            description = "Interpreter can request handover NOW and other Interpreter can accept it.",
            groups = {
                    TWO_USERS,
                    EVENT,
                    INTERPRETER
            }
    )
    @TestRailCase("1534")
    @TestRailBugId("CORE-6941")
    public void test() {

        // Log in as Interpreter 1 with Outgoing language.

        String firstInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage firstInterpreterPage = authorizer.logInAsInterpreter(event, firstInterpreterName, language);
        firstInterpreterPage.assertIsOpened();

        // Log in as Interpreter 2 with Outgoing language.

        WebApplicationContext secondInterpreterContext = WebContextUtil.switchToNewContext();

        String secondInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage secondInterpreterPage = authorizer.logInAsInterpreter(event, secondInterpreterName, language);
        secondInterpreterPage.assertIsOpened();

        // Switch to Interpreter 1 and start Streaming.

        WebContextUtil.switchToDefaultContext();

        firstInterpreterPage.unmute();

        InterpreterVerifier firstInterpreterVerifier = new InterpreterVerifier(firstInterpreterPage);

        firstInterpreterVerifier.assertStreaming();

        // Request Handover NOW.

        firstInterpreterPage.requestHandoverNow();
        firstInterpreterVerifier.verifyHandoverWaitingForPartnerResponse();

        // Switch to Interpreter 2 and check that 'Handover' dialog window is in 'Muted' state.

        WebContextUtil.switchToContext(secondInterpreterContext);

        HandoverDW handoverDW = new HandoverDW(secondInterpreterPage);
        handoverDW.assertIsOpened();

        InterpreterVerifier secondInterpreterVerifier = new InterpreterVerifier(secondInterpreterPage);

        secondInterpreterVerifier.assertHandoverDWInMutedState(handoverDW);

        // Unmute from 'Handover' dialog window.

        handoverDW.unmute();
        handoverDW.assertNotVisible();

        secondInterpreterVerifier.assertUnmuted();

        // Check that Handover available.

        secondInterpreterVerifier.verifyHandoverAvailable();

        // Switch to Interpreter 1 and check that Handover available.

        WebContextUtil.switchToDefaultContext();

        firstInterpreterVerifier.verifyHandoverAvailable();

        // Mute and check that Handover unavailable.

        firstInterpreterPage.mute();
        firstInterpreterVerifier.assertMuted();

        firstInterpreterVerifier.verifyHandoverUnavailable();

        // Switch to Interpreter 2 and check that Handover unavailable.

        WebContextUtil.switchToContext(secondInterpreterContext);

        secondInterpreterVerifier.assertHandoverUnavailable();

        throwErrorIfVerificationsFailed();
    }
}
