package com.intpfy.gui.components.chats;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;

public abstract class BaseChatComponent extends BaseComponent {

    final ChatFooterComponent footer;

    protected BaseChatComponent(String name, IParent parent, By componentLocator) {
        super(name, parent, componentLocator);
        footer = new ChatFooterComponent(this);
    }

    public void sendMessage(String message) {
        footer.sendMessage(message);
    }

    public boolean messageExists(String message, Duration timeout) {
        return createChatLineComponent(message).visible(timeout);
    }

    public boolean messageNotExists(String message, Duration timeout) {
        return createChatLineComponent(message).notVisible(timeout);
    }

    public boolean isEnabled(Duration timeout) {
        return footer.isEnabled(timeout);
    }

    public boolean isDisabled(Duration timeout) {
        return footer.isDisabled(timeout);
    }

    void moveMouseInFooter() {
        footer.getComponentElement().moveMouseIn();
    }

    ChatLineComponent createChatLineComponent(String message) {
        return new ChatLineComponent(this, message);
    }
}
