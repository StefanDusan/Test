package com.intpfy.gui.components.chats;

import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class PartnerChatComponent extends BaseChatComponent {

    private static final String CHANGED_OUTGOING_LANGUAGE_MESSAGE = "--- Changed outgoing channel to %s---";

    @ElementInfo(name = "Hide chat", findBy =
    @FindBy(xpath = ".//a[contains(@class, 'chat-expand-header__expand') and text()='-']"))
    private Button hideChatButton;

    @ElementInfo(name = "Show chat", findBy =
    @FindBy(xpath = ".//a[contains(@class, 'chat-expand-header__expand') and text()='+']"))
    private Button showChatButton;

    @ElementInfo(name = "Open chat dialog window", findBy =
    @FindBy(css = "svg.chat-expand-header__icon"))
    private Button openButton;

    @ElementInfo(name = "Chat body", findBy =
    @FindBy(css = "div.chat-expand__body"))
    private Element chatBodyElement;

    public PartnerChatComponent(IParent parent) {
        super("Partner chat", parent, By.cssSelector("div.chat._partner-chat, div.chat-expand"));
    }

    public void show() {
        showChatButton.click();
    }

    public void hide() {
        hideChatButton.click();
    }

    public boolean isShown(Duration timeout) {
        return hideChatButton.visible(timeout) && chatBodyElement.visible(timeout);
    }

    public boolean isHidden(Duration timeout) {
        return showChatButton.visible(timeout) && chatBodyElement.notVisible(timeout);
    }

    public boolean isOutgoingLanguageChangedMessageDisplayed(Language language, Duration timeout) {
        return createChatLineComponent(String.format(CHANGED_OUTGOING_LANGUAGE_MESSAGE, language)).visible(timeout);
    }

    public ChatDW open() {
        openButton.click();
        return new ChatDW(getPage());
    }
}
