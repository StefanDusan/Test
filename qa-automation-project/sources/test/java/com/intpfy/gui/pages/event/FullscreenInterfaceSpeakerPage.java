package com.intpfy.gui.pages.event;

import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.model.Shortcut;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.support.FindBy;

public class FullscreenInterfaceSpeakerPage extends BaseFullscreenInterfaceEventPage {

    @ElementInfo(name = "Private chat", findBy = @FindBy(css = "a.full-screen-panel-wr__private-chat"))
    private Button privateChatButton;

    public FullscreenInterfaceSpeakerPage(IPageContext pageContext) {
        super("Speaker Full screen interface page", pageContext);
    }

    @Override
    public LoginPage logOut() {
        info("Log out.");
        SpeakerPage speakerPage = disableFullscreenInterface();
        speakerPage.assertIsOpened();
        return speakerPage.logOut();
    }

    public ChatDW openPrivateChat() {
        info("Open Private chat.");
        showBottomPanel();
        privateChatButton.visible();
        privateChatButton.clickable(WebSettings.AJAX_TIMEOUT);
        privateChatButton.click();
        return new ChatDW(this);
    }

    public void openEventAndRemoteSupportHelpChatsByShortcut() {
        info("Open 'Event' and 'Remote support help' chats by shortcut.");
        openCloseEventAndRemoteSupportHelpChatsByShortcut();
    }

    public void closeEventAndRemoteSupportHelpChatsByShortcut() {
        info("Close 'Event' and 'Remote support help' chats by shortcut.");
        openCloseEventAndRemoteSupportHelpChatsByShortcut();
    }

    private void openCloseEventAndRemoteSupportHelpChatsByShortcut() {
        applyShortcut(Shortcut.OpenCloseEventAndRemoteSupportHelpChats);
    }

    private SpeakerPage disableFullscreenInterface() {
        applyShortcut(Shortcut.DisableFullscreenInterface);
        return new SpeakerPage(getPageContext());
    }
}
