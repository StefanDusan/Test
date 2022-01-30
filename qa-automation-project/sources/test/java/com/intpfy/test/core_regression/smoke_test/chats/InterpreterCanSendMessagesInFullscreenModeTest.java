package com.intpfy.test.core_regression.smoke_test.chats;

import com.intpfy.gui.dialogs.common.MiniEventChatDW;
import com.intpfy.gui.dialogs.common.MiniPartnerChatDW;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.FullscreenInterfaceInterpreterPage;
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

public class InterpreterCanSendMessagesInFullscreenModeTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Language language = Language.getRandomLanguage();

    // Create random event with 1 Language and enabled Event chat for I-token.
    private final Event event = Event
            .createRandomBuilder()
            .withLanguage(language)
            .withInterpreterBlockToTypeInChat(false)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that Interpreter can send messages in Fullscreen mode",
            description = "Interpreters can exchange messages in Full screen mode.",
            groups = {
                    ONE_USER,
                    EVENT,
                    CHATS,
                    INTERPRETER
            }
    )
    @TestRailCase("784")
    @TestRailBugId("CORE-5933")
    public void test() {

        // Log in as Interpreter 1 with Outgoing language.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage firstInterpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        firstInterpreterPage.assertIsOpened();

        // Enable Full screen interface.

        FullscreenInterfaceInterpreterPage firstInterpreterFullScreenPage = firstInterpreterPage.enableFullscreenInterface();
        firstInterpreterFullScreenPage.assertIsOpened();

        // Open Event chat dialog window.

        MiniEventChatDW firstInterpreterEventChat = firstInterpreterFullScreenPage.openEventChat();
        firstInterpreterEventChat.assertIsOpened();

        // Send message to Event chat.

        String eventChatMessage = RandomUtil.createRandomChatMessage();
        firstInterpreterEventChat.sendMessage(eventChatMessage);

        // Check that message exists in Event chat.

        InterpreterVerifier firstInterpreterVerifier = new InterpreterVerifier(firstInterpreterPage);

        firstInterpreterVerifier.assertMessageExistsInEventChat(firstInterpreterEventChat, eventChatMessage);

        // Open Partner chat dialog window.

        MiniPartnerChatDW firstInterpreterPartnerChat = firstInterpreterFullScreenPage.openMiniPartnerChatDW();
        firstInterpreterPartnerChat.assertIsOpened();

        // Send message to Partner chat.

        String partnerChatMessage = RandomUtil.createRandomChatMessage();
        firstInterpreterPartnerChat.sendMessage(partnerChatMessage);

        // Check that message exists in Partner chat.

        firstInterpreterVerifier.assertMessageExistsInPartnerChat(firstInterpreterPartnerChat, partnerChatMessage);

        // Log out and Log in as Interpreter 2 with Outgoing language.

        LoginPage loginPage = firstInterpreterFullScreenPage.logOut();
        loginPage.assertIsOpened();

        String secondInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage secondInterpreterPage = authorizer.logInAsInterpreter(event, secondInterpreterName, language);
        secondInterpreterPage.assertIsOpened();

        // Enable Full screen interface.

        FullscreenInterfaceInterpreterPage secondInterpreterFullScreenPage = secondInterpreterPage.enableFullscreenInterface();
        secondInterpreterFullScreenPage.assertIsOpened();

        // Open Event chat dialog window.

        MiniEventChatDW secondInterpreterEventChat = secondInterpreterFullScreenPage.openEventChat();
        secondInterpreterEventChat.assertIsOpened();

        // Check that message from Interpreter 1 exists in Event chat.

        InterpreterVerifier secondInterpreterVerifier = new InterpreterVerifier(secondInterpreterPage);

        secondInterpreterVerifier.assertMessageExistsInEventChat(secondInterpreterEventChat, eventChatMessage);

        // Open Partner chat dialog window.

        MiniPartnerChatDW secondInterpreterPartnerChat = secondInterpreterFullScreenPage.openMiniPartnerChatDW();
        secondInterpreterPartnerChat.assertIsOpened();

        // Check that message from Interpreter 1 exists in Partner chat.

        secondInterpreterVerifier.assertMessageExistsInPartnerChat(secondInterpreterPartnerChat, partnerChatMessage);

        throwErrorIfVerificationsFailed();
    }
}
