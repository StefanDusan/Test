package com.intpfy.gui.pages.event;

import com.intpfy.gui.components.chats.EventChatComponent;
import com.intpfy.gui.dialogs.common.MiniEventChatDW;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.settings.WebSettings;
import org.apache.commons.lang.NotImplementedException;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public abstract class BaseFullscreenInterfaceEventPage extends BaseEventPage {

    @ElementInfo(name = "Chat with participants", findBy = @FindBy(css = "a.full-screen-panel-wr__chat"))
    private Button chatWithParticipantsButton;

    @ElementInfo(name = "To standard interface", findBy = @FindBy(css = "a.full-screen-panel-wr__toggle"))
    private Button toStandardInterfaceButton;

    BaseFullscreenInterfaceEventPage(String name, IPageContext pageContext) {
        super(name, pageContext);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        showBottomPanel(Duration.ZERO);
        return toStandardInterfaceButton.visible(timeout);
    }

    public boolean isNotOpened(Duration timeout) {
        showBottomPanel(Duration.ZERO);
        return toStandardInterfaceButton.notVisible(timeout);
    }

    @Override
    public EventChatComponent getEventChat() {
        throw new NotImplementedException();
    }

    @Override
    public boolean isFilePresent(String fileName, Duration timeout) {
        throw new NotImplementedException();
    }

    public MiniEventChatDW openEventChat() {
        info("Open Event chat.");
        openChat();
        return new MiniEventChatDW(this);
    }

    protected void openChat() {
        showBottomPanel();
        chatWithParticipantsButton.visible();
        chatWithParticipantsButton.click();
    }

    protected void showBottomPanel() {
        showBottomPanel(WebSettings.AJAX_TIMEOUT);
    }

    private void showBottomPanel(Duration timeout) {
        bodyElement.moveMouseIn();
        toStandardInterfaceButton.visible(timeout);
    }
}