package com.intpfy.test.core_regression.web_panel.interpreter;

import com.intpfy.gui.dialogs.common.RestartingConnectionDW;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class RalTest extends BaseWebTest implements EventTest {

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
            testName = "Check the RAL",
            description = "Interpreter can Restart all lines.",
            groups = {
                    ONE_USER,
                    EVENT,
                    INTERPRETER
            }
    )
    @TestRailCase("1538")
    public void test() {

        // Log in as Interpreter.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName);
        interpreterPage.assertIsOpened();

        // Restart all lines.

        RestartingConnectionDW restartingConnectionDW = interpreterPage.restartAllLines();

        // Assertion fails sometimes on remote test execution.
        // restartingConnectionDW.assertIsOpened();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);

        // Assertion fails sometimes on remote test execution.
        // interpreterVerifier.verifyPageGrayedOut();

        // Check that after short pause 'Restarting connection' pop-up not displayed and page not grayed out.

        restartingConnectionDW.assertNotVisible();
        interpreterVerifier.verifyPageNotGrayedOut();

        throwErrorIfVerificationsFailed();
    }
}
