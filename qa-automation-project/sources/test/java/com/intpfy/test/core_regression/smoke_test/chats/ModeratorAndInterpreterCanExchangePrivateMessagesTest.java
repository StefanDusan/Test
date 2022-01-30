package com.intpfy.test.core_regression.smoke_test.chats;

import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.ModeratorPage;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.event.moderator.ModeratorVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class ModeratorAndInterpreterCanExchangePrivateMessagesTest extends BaseWebTest implements EventTest {

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
            testName = "Check that Moderator and Interpreter can exchange private messages",
            description = "Moderator and Interpreter can exchange private messages via 'Remote support help' chat.",
            groups = {
                    TWO_USERS,
                    EVENT,
                    CHATS,
                    INTERPRETER,
                    MODERATOR
            }
    )
    @TestRailCase("792")
    @TestRailBugId("CORE-5933")
    public void test() {

        // Log in as Moderator.

        String moderatorName = RandomUtil.createRandomModeratorName();
        ModeratorPage moderatorPage = authorizer.logInAsModerator(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Log in as Interpreter.

        WebApplicationContext interpreterContext = WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName);
        interpreterPage.assertIsOpened();

        // Open 'Remote support help' chat dialog window.

        ChatDW interpreterRemoteSupportHelpChat = interpreterPage.openRemoteSupportHelpChat();
        interpreterRemoteSupportHelpChat.assertIsOpened();

        // Send message to 'Remote support help' chat.

        String interpreterMessage = RandomUtil.createRandomChatMessage();
        interpreterRemoteSupportHelpChat.sendMessage(interpreterMessage);

        // Check that message exists in 'Remote support help' chat.

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);

        interpreterVerifier.assertMessageExistsInRemoteSupportHelpChat(interpreterRemoteSupportHelpChat, interpreterMessage);

        // Switch to Moderator and open 'Remote support help' chat dialog window.

        WebContextUtil.switchToDefaultContext();

        ChatDW moderatorRemoteSupportHelpChat = moderatorPage.openRemoteSupportHelpChat();
        moderatorRemoteSupportHelpChat.assertIsOpened();

        // Check that message from Interpreter exists in 'Remote support help' chat.

        ModeratorVerifier moderatorVerifier = new ModeratorVerifier(moderatorPage);

        moderatorVerifier.assertMessageExistsInRemoteSupportHelpChat(moderatorRemoteSupportHelpChat, interpreterMessage);

        // Send message to 'Remote support help' chat.

        String moderatorMessage = RandomUtil.createRandomChatMessage();
        moderatorRemoteSupportHelpChat.sendMessage(moderatorMessage);

        // Check that message exists in 'Remote support help' chat.

        moderatorVerifier.assertMessageExistsInRemoteSupportHelpChat(moderatorRemoteSupportHelpChat, moderatorMessage);

        // Switch to Interpreter and check that message from Moderator exists in 'Remote support help' chat.

        WebContextUtil.switchToContext(interpreterContext);

        interpreterVerifier.assertMessageExistsInRemoteSupportHelpChat(interpreterRemoteSupportHelpChat, moderatorMessage);

        // Switch to Moderator and close 'Remote support help' chat dialog window.

        WebContextUtil.switchToDefaultContext();

        moderatorRemoteSupportHelpChat.close();
        moderatorRemoteSupportHelpChat.assertNotVisible();

        throwErrorIfVerificationsFailed();
    }
}
