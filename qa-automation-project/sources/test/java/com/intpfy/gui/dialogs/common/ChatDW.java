package com.intpfy.gui.dialogs.common;

import com.intpfy.gui.components.chats.ChatLineComponent;
import com.intpfy.gui.dialogs.Closeable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.TextArea;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class ChatDW extends BaseComponent implements Closeable {

    @ElementInfo(name = "Send", findBy = @FindBy(css = "button[class*=send]"))
    private Button sendButton;

    @ElementInfo(name = "Type your message", findBy = @FindBy(css = "textarea"))
    private TextArea textArea;

    @ElementInfo(name = "Session title", findBy = @FindBy(css = "h4.modal-title"))
    private Element sessionTitleElement;

    public ChatDW(IParent parent) {
        super("Chat dialog window", parent, By.cssSelector("#privateChat, div.m-chat"));
    }

    public void sendMessage(String message) {
        info(String.format("Send message '%s'.", message));
        textArea.setText(message);
        sendButton.click();
    }

    public boolean messageExists(String message, Duration timeout) {
        return createChatLineComponent(message).visible(timeout);
    }

    public String getCurrentSessionTitle() {
        return sessionTitleElement.getText();
    }

    private ChatLineComponent createChatLineComponent(String message) {
        return new ChatLineComponent(this, message);
    }
}