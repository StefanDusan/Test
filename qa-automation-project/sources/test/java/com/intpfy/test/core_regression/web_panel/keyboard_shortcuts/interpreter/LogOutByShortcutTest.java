package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.interpreter;


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

public class LogOutByShortcutTest extends BaseWebTest implements EventTest {

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
            testName = "Log out by shortcut",
            description = "Interpreter can Log out by shortcut.",
            groups = {
                    ONE_USER,
                    EVENT,
                    SHORTCUTS,
                    INTERPRETER
            }
    )
    @TestRailCase("2035")
    public void test() {

        // Log in as Interpreter.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName);
        interpreterPage.assertIsOpened();

        // Log out by shortcut.

        LoginPage loginPage = interpreterPage.logOutByShortcut();
        loginPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
