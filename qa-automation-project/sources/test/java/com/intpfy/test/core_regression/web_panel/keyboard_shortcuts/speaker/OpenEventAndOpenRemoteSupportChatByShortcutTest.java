package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.speaker;


import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.dialogs.common.MiniEventChatDW;
import com.intpfy.gui.pages.event.FullscreenInterfaceSpeakerPage;
import com.intpfy.gui.pages.event.ModeratorPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfyqa.test.Verify;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class OpenEventAndOpenRemoteSupportChatByShortcutTest extends BaseWebTest implements EventTest {

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
            testName = "Open Event and open Remote Support chat by shortcut",
            description = "Speaker can open Event and 'Remote support help' chats by shortcut.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SHORTCUTS,
                    SPEAKER,
                    MODERATOR
            }
    )
    @TestRailCase("2014")
    public void test() {

        // Log in as Moderator.

        String moderatorName = RandomUtil.createRandomModeratorName();
        ModeratorPage moderatorPage = authorizer.logInAsModerator(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Log in as Host with Meeting control only.

        WebContextUtil.switchToNewContext();

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Open 'Remote support help' chat by shortcut.

        ChatDW remoteSupportHelpChat = hostPage.openRemoteSupportHelpChatByShortcut();

        String remoteSupportHelpChatOpenedMessage = "'Remote support help' chat opened.";
        Verify.verifyTrue(remoteSupportHelpChat.visible(), remoteSupportHelpChatOpenedMessage);

        // Close 'Remote support help' chat by shortcut.

        hostPage.closeRemoteSupportHelpChatByShortcut();

        boolean remoteSupportHelpChatNotVisible = remoteSupportHelpChat.notVisible();
        String remoteSupportHelpChatClosedMessage = "'Remote support help' chat closed.";
        Verify.verifyTrue(remoteSupportHelpChatNotVisible, remoteSupportHelpChatClosedMessage);

        // Close 'Remote support help' chat by clicking 'X' button if it has not been closed by shortcut.

        if (!remoteSupportHelpChatNotVisible) {
            remoteSupportHelpChat.close();
            remoteSupportHelpChat.assertNotVisible();
        }

        // Enable Full screen interface.

        FullscreenInterfaceSpeakerPage fullscreenInterfaceHostPage = hostPage.enableFullscreenInterface();
        fullscreenInterfaceHostPage.assertIsOpened();

        // Open 'Remote support help' and Event chats by shortcut.

        fullscreenInterfaceHostPage.openEventAndRemoteSupportHelpChatsByShortcut();

        MiniEventChatDW miniEventChat = new MiniEventChatDW(fullscreenInterfaceHostPage);
        Verify.verifyTrue(miniEventChat.visible(), "Event chat opened.");

        ChatDW remoteSupportHelpChatInFullscreen = new ChatDW(fullscreenInterfaceHostPage);
        Verify.verifyTrue(remoteSupportHelpChatInFullscreen.visible(), remoteSupportHelpChatOpenedMessage);

        // Close 'Remote support help' and Event chats by shortcut.

        fullscreenInterfaceHostPage.closeEventAndRemoteSupportHelpChatsByShortcut();

        Verify.verifyTrue(miniEventChat.notVisible(), "Event chat closed.");
        Verify.verifyTrue(remoteSupportHelpChatInFullscreen.notVisible(), remoteSupportHelpChatClosedMessage);

        throwErrorIfVerificationsFailed();
    }
}
