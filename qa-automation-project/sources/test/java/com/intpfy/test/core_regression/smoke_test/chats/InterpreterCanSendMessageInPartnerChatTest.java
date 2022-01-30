package com.intpfy.test.core_regression.smoke_test.chats;

import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.model.Language;
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

public class InterpreterCanSendMessageInPartnerChatTest extends BaseWebTest implements EventTest {

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
            testName = "Check that Interpreter can send messages in Interpreting Partner Chat",
            description = "Interpreters can exchange messages in Partner Chat.",
            groups = {
                    ONE_USER,
                    EVENT,
                    CHATS,
                    INTERPRETER
            }
    )
    @TestRailCase("783")
    @TestRailBugId("CORE-5933")
    public void test() {

        // Log in as Interpreter 1 with Outgoing language.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage firstInterpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        firstInterpreterPage.assertIsOpened();

        // Send message to Partner chat.

        String message = RandomUtil.createRandomChatMessage();
        firstInterpreterPage.sendMessageToPartnerChat(message);

        // Check that message exists in Partner chat.

        InterpreterVerifier firstInterpreterVerifier = new InterpreterVerifier(firstInterpreterPage);
        firstInterpreterVerifier.assertMessageExistsInPartnerChat(message);

        // Log out and Log in as Interpreter 2 with Outgoing language.

        LoginPage loginPage = firstInterpreterPage.logOut();
        loginPage.assertIsOpened();

        InterpreterPage secondInterpreterPage = authorizer.logInAsInterpreter(event, RandomUtil.createRandomInterpreterName(), language);
        secondInterpreterPage.assertIsOpened();

        // Check that message from Interpreter exists in Partner chat.

        InterpreterVerifier secondInterpreterVerifier = new InterpreterVerifier(secondInterpreterPage);
        secondInterpreterVerifier.assertMessageExistsInPartnerChat(message);

        throwErrorIfVerificationsFailed();
    }
}
