package com.intpfy.gui.pages.event;

import com.intpfy.gui.dialogs.common.MiniEventChatDW;
import com.intpfy.gui.dialogs.common.MiniPartnerChatDW;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.model.Shortcut;
import com.intpfyqa.gui.web.selenium.IPageContext;

import java.time.Duration;

public class FullscreenInterfaceInterpreterPage extends BaseFullscreenInterfaceEventPage {

    public FullscreenInterfaceInterpreterPage(IPageContext pageContext) {
        super("Interpreter Full screen interface page", pageContext);
    }

    @Override
    public LoginPage logOut() {
        info("Log out.");
        InterpreterPage interpreterPage = disableFullscreenInterface();
        interpreterPage.assertIsOpened();
        return interpreterPage.logOut();
    }

    @Override
    public MiniEventChatDW openEventChat() {
        info("Open Event chat.");
        MiniEventChatDW eventChat = new MiniEventChatDW(this);
        if (eventChat.notVisible(Duration.ZERO)) {
            openChat();
        }
        return eventChat;
    }

    public MiniPartnerChatDW openMiniPartnerChatDW() {
        info("Open Partner chat.");
        MiniPartnerChatDW partnerChat = new MiniPartnerChatDW(this);
        if (partnerChat.notVisible(Duration.ZERO)) {
            openChat();
        }
        return partnerChat;
    }

    public void openEventAndPartnerAndRemoteSupportHelpChatsByShortcut() {
        info("Open 'Event', 'Partner' and 'Remote support help' chats by shortcut.");
        openCloseEventAndPartnerAndRemoteSupportHelpChatsByShortcut();
    }

    public void closeEventAndPartnerAndRemoteSupportHelpChatsByShortcut() {
        info("Close 'Event', 'Partner' and 'Remote support help' chats by shortcut.");
        openCloseEventAndPartnerAndRemoteSupportHelpChatsByShortcut();
    }

    private void openCloseEventAndPartnerAndRemoteSupportHelpChatsByShortcut() {
        applyShortcut(Shortcut.OpenCloseEventAndPartnerAndRemoteSupportHelpChats);
    }

    private InterpreterPage disableFullscreenInterface() {
        applyShortcut(Shortcut.DisableFullscreenInterface);
        return new InterpreterPage(getPageContext());
    }
}
