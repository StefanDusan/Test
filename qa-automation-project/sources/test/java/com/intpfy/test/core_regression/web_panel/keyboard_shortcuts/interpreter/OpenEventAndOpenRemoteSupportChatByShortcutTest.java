package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.interpreter;


import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.dialogs.common.MiniEventChatDW;
import com.intpfy.gui.dialogs.common.MiniPartnerChatDW;
import com.intpfy.gui.dialogs.common.MiniRemoteSupportHelpChatDW;
import com.intpfy.gui.pages.event.FullscreenInterfaceInterpreterPage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.ModeratorPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfyqa.gui.web.selenium.VerifyPage;
import com.intpfyqa.test.Verify;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class OpenEventAndOpenRemoteSupportChatByShortcutTest extends BaseWebTest implements EventTest {

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
            testName = "Open Event and open Remote Support chat by shortcut",
            description = "Interpreter can open 'Event', 'Interpreting partner' and 'Remote support help' chats by shortcut.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SHORTCUTS,
                    INTERPRETER,
                    MODERATOR
            }
    )
    @TestRailCase("2033")
    public void test() {

        // Log in as Interpreter 1 with Outgoing language.

        String firstInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage firstInterpreterPage = authorizer.logInAsInterpreter(event, firstInterpreterName, language);
        firstInterpreterPage.assertIsOpened();

        // Log in as Moderator.

        WebContextUtil.switchToNewContext();

        String moderatorName = RandomUtil.createRandomModeratorName();
        ModeratorPage moderatorPage = authorizer.logInAsModerator(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Log in as Interpreter 2 with Outgoing language.

        WebContextUtil.switchToNewContext();

        String secondInterpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage secondInterpreterPage = authorizer.logInAsInterpreter(event, secondInterpreterName, language);
        secondInterpreterPage.assertIsOpened();

        // Open 'Remote support help' chat by shortcut.

        ChatDW remoteSupportHelpChat = secondInterpreterPage.openRemoteSupportHelpChatByShortcut();

        String remoteSupportHelpChatOpenedMessage = "Remote support help chat opened.";
        Verify.verifyTrue(remoteSupportHelpChat.visible(), remoteSupportHelpChatOpenedMessage);

        // Close 'Remote support help' chat by shortcut.

        secondInterpreterPage.closeRemoteSupportHelpChatByShortcut();

        boolean remoteSupportHelpChatNotVisible = remoteSupportHelpChat.notVisible();
        String remoteSupportHelpChatClosedMessage = "Remote support help chat closed.";
        Verify.verifyTrue(remoteSupportHelpChatNotVisible, remoteSupportHelpChatClosedMessage);

        // Close Remote support help chat by clicking 'X' button if it has not been closed by shortcut.

        if (!remoteSupportHelpChatNotVisible) {
            remoteSupportHelpChat.close();
            remoteSupportHelpChat.assertNotVisible();
        }

        // Enable Full screen interface.

        FullscreenInterfaceInterpreterPage fullscreenInterfaceInterpreterPage = secondInterpreterPage.enableFullscreenInterface();
        fullscreenInterfaceInterpreterPage.assertIsOpened();

        // Open 'Event', 'Interpreting partner' and 'Remote support help' chats by shortcut.

        fullscreenInterfaceInterpreterPage.openEventAndPartnerAndRemoteSupportHelpChatsByShortcut();

        MiniEventChatDW miniEventChat = new MiniEventChatDW(fullscreenInterfaceInterpreterPage);
        MiniPartnerChatDW miniPartnerChat = new MiniPartnerChatDW(fullscreenInterfaceInterpreterPage);
        MiniRemoteSupportHelpChatDW miniRemoteSupportHelpChat = new MiniRemoteSupportHelpChatDW(fullscreenInterfaceInterpreterPage);

        VerifyPage.onPage(fullscreenInterfaceInterpreterPage, "'Event', 'Interpreting partner' and 'Remote support help' chats opened.")
                .booleanValueShouldBeEqual(b -> miniEventChat.visible(), true, "'Event' chat opened.")
                .booleanValueShouldBeEqual(b -> miniPartnerChat.visible(), true, "'Partner' chat opened.")
                .booleanValueShouldBeEqual(b -> miniRemoteSupportHelpChat.visible(), true, remoteSupportHelpChatOpenedMessage)
                .completeVerify();

        // Close 'Event', 'Interpreting partner' and 'Remote support help' chats by shortcut.

        fullscreenInterfaceInterpreterPage.closeEventAndPartnerAndRemoteSupportHelpChatsByShortcut();

        VerifyPage.onPage(fullscreenInterfaceInterpreterPage, "'Event', 'Interpreting partner' and 'Remote support help' chats closed.")
                .booleanValueShouldBeEqual(b -> miniEventChat.notVisible(), true, "'Event' chat closed.")
                .booleanValueShouldBeEqual(b -> miniPartnerChat.notVisible(), true, "'Partner' chat closed.")
                .booleanValueShouldBeEqual(b -> miniRemoteSupportHelpChat.notVisible(), true, remoteSupportHelpChatClosedMessage)
                .completeVerify();

        throwErrorIfVerificationsFailed();
    }
}
