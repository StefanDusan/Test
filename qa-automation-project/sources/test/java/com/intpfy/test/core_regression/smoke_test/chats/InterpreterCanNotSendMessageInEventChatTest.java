package com.intpfy.test.core_regression.smoke_test.chats;

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

public class InterpreterCanNotSendMessageInEventChatTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create random event with disabled Event chat for I-token.
    private final Event event = Event
            .createRandomBuilder()
            .withInterpreterBlockToTypeInChat(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that Interpreter can't send messages in Event chat if it is Disabled in EMI",
            description = "Interpreter can't send messages in Event Chat if it is Disabled in EMI.",
            groups = {
                    ONE_USER,
                    EVENT,
                    CHATS,
                    INTERPRETER
            }
    )
    @TestRailCase("781")
    public void test() {

        // Log in as Interpreter.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName);
        interpreterPage.assertIsOpened();

        // Check that Event chat disabled.

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.verifyEventChatDisabled();

        throwErrorIfVerificationsFailed();
    }
}
