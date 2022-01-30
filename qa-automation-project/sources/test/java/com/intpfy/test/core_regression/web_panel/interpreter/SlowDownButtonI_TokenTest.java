package com.intpfy.test.core_regression.web_panel.interpreter;


import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.intpfy.test.TestGroups.*;


public class SlowDownButtonI_TokenTest extends BaseWebTest implements EventTest {

    private static final Duration SLOW_DOWN_TIMEOUT = Duration.ofSeconds(8);

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect Pro (Classroom)' event.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "The button 'Slow-down' works correctly (I-token)",
            description = "'Slow-down' button and 'Request sent' dialog window work correctly for Interpreter.",
            groups = {
                    ONE_USER,
                    EVENT,
                    INTERPRETER
            }
    )
    @TestRailCase("1634")
    public void test() {

        // Log in as Interpreter.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName);
        interpreterPage.assertIsOpened();

        // Request Slow down.

        interpreterPage.requestSlowDown();

        // Check that 'Slow down' button active and 'Request sent' dialog window displayed.

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);

        interpreterVerifier.verifySlowDownButtonActive();
        interpreterVerifier.verifyRequestSentDWDisplayed();

        // Check that 'Slow down' button not active and 'Request sent' dialog window not displayed after timeout.

        interpreterVerifier.verifySlowDownButtonNotActive(SLOW_DOWN_TIMEOUT);
        interpreterVerifier.verifyRequestSentDWNotDisplayed(SLOW_DOWN_TIMEOUT);

        throwErrorIfVerificationsFailed();
    }
}
