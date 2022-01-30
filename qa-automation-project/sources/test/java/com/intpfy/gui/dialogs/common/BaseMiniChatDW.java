package com.intpfy.gui.dialogs.common;

import com.intpfy.gui.components.chats.MiniChatMessageComponent;
import com.intpfy.gui.dialogs.Closeable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.TextArea;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public abstract class BaseMiniChatDW extends BaseComponent implements Closeable {

    @ElementInfo(name = "Type your message", findBy =
    @FindBy(xpath = ".//textarea[contains(@class, 'single-chat__compose')]"))
    private TextArea textArea;

    @ElementInfo(name = "Send", findBy =
    @FindBy(xpath = ".//button[contains(@class, 'single-chat__send')]"))
    private Button sendMessageButton;

    @ElementInfo(name = "Close", findBy = @FindBy(css = "button.single-chat__close"))
    private Button closeButton;

    protected BaseMiniChatDW(String name, IParent parent, By componentLocator) {
        super(name, parent, componentLocator);
    }

    @Override
    public Button getCloseButton() {
        return closeButton;
    }

    public void sendMessage(String message) {
        info(String.format("Send message '%s' to %s.", message, getComponentElement().getLogicalName()));
        textArea.visible();
        textArea.setText(message);
        sendMessageButton.click();
    }

    public boolean messageExists(String message, Duration timeout){
        return createMessageComponent(message).visible(timeout);
    }

    private MiniChatMessageComponent createMessageComponent(String message) {
        return new MiniChatMessageComponent(this,
                By.xpath(".//div[contains(@class, 'single-chat__message') " +
                        "and .//p[contains(@class, 'chat-message__text') and text()='" + message + "']]"));
    }
}
