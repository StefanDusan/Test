package com.intpfy.test.core_regression.smoke_test.chats;

import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class InterpreterCanSendMessageInEventChatTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create random event with enabled Event chat for I-token.
    private final Event event = Event
            .createRandomBuilder()
            .withInterpreterBlockToTypeInChat(false)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that Interpreter can send messages in Event chat if it is Enabled in EMI",
            description = "Interpreters can exchange messages in Event Chat.",
            groups = {
                    ONE_USER,
                    EVENT,
                    CHATS,
                    INTERPRETER
            }
    )
    @TestRailCase("782")
    @TestRailBugId("CORE-5933")
    public void test() {

        // Log in as Interpreter 1.

        InterpreterPage firstInterpreterPage = authorizer.logInAsInterpreter(event, RandomUtil.createRandomInterpreterName());
        firstInterpreterPage.assertIsOpened();

        // Send message to Event chat.

        String message = RandomUtil.createRandomChatMessage();
        firstInterpreterPage.sendMessageToEventChat(message);

        // Check that message exists in Event chat.

        InterpreterVerifier firstInterpreterVerifier = new InterpreterVerifier(firstInterpreterPage);
        firstInterpreterVerifier.assertMessageExistsInEventChat(message);

        // Log out and Log in as Interpreter 2.

        LoginPage loginPage = firstInterpreterPage.logOut();
        loginPage.assertIsOpened();

        InterpreterPage secondInterpreterPage = authorizer.logInAsInterpreter(event, RandomUtil.createRandomInterpreterName());
        secondInterpreterPage.assertIsOpened();

        // Check that message exists in Event chat.

        InterpreterVerifier secondInterpreterVerifier = new InterpreterVerifier(secondInterpreterPage);
        secondInterpreterVerifier.assertMessageExistsInEventChat(message);

        throwErrorIfVerificationsFailed();
    }
}
