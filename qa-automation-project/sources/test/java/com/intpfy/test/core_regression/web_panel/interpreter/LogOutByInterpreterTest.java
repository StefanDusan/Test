package com.intpfy.test.core_regression.web_panel.interpreter;

import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class LogOutByInterpreterTest extends BaseWebTest implements EventTest {

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
            testName = "Check logout by Interpreter (I-token)",
            description = "Interpreter can Log out",
            groups = {
                    ONE_USER,
                    EVENT,
                    INTERPRETER
            }
    )
    @TestRailCase("1530")
    public void test() {

        // Log in as Interpreter.

        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, RandomUtil.createRandomInterpreterName());
        interpreterPage.assertIsOpened();

        // Log out.

        LoginPage loginPage = interpreterPage.logOut();
        loginPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
